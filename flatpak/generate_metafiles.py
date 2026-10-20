#!/usr/bin/env python3
#
# Copyright (c) 2026 Alyx/flusedev <alyx@fluse.cc>
#
# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documentation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
# copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:
#
# The above copyright notice and this permission notice shall be included in all
# copies or substantial portions of the Software.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
# SOFTWARE.

import os
import yaml
import sys
import copy
from typing import *
from xml.etree import ElementTree as xml
from os import PathLike
from pathlib import Path
from io import TextIOBase
from subprocess import Popen, CompletedProcess, CalledProcessError, check_output as invoke2, PIPE
from urllib.parse import urlparse


class Env(Mapping[str, str]):
    """
    Wrapper for os.environ raising EnvironmentError instead of KeyError
    """

    def __init__(self):
        import os

        self._env = os.environ

    def __iter__(self):
        return iter(self._env)

    def __len__(self):
        return len(self._env)

    def __getitem__(self, key: str):
        try:
            return self._env[key]
        except KeyError:
            raise EnvironmentError(f"Environment variable {key} is not set")


class YamlXmlElement(TypedDict):
    tag: str
    attrib: NotRequired[Mapping[str, str]]
    text: NotRequired[str]
    children: NotRequired["YamlXmlElement"]


type TagName = str
env = Env()

TMP_FILES: List[tuple[int, str]] = []


def mkstemp(prefix, suffix) -> tuple[int, str]:
    import tempfile

    tmp = tempfile.mkstemp(suffix, prefix)
    TMP_FILES.append(tmp)
    return tmp


@overload
def fmt(value: str, args: Mapping[str, str]) -> str: ...
@overload
def fmt[K, V](value: Mapping[K, V], args: Mapping[str, str]) -> MutableMapping[K, V]: ...
@overload
def fmt[T](value: Sequence[T], args: Mapping[str, str]) -> MutableSequence[T]: ...
@overload
def fmt[T](value: T, args: Mapping[str, str]) -> T: ...


def fmt(value, args: Mapping[str, str]):
    if not value:
        return value

    if isinstance(value, str):
        for k, v in args.items():
            value = value.replace(f"{{{{{k}}}}}", v)
        return value

    if isinstance(value, Mapping):
        return {k: fmt(v, args) for k, v in value.items()}

    if isinstance(value, Sequence):
        return [fmt(it, args) for it in value]

    return value


def path(s: PathLike, relative_to_self: bool = True) -> Path:
    """
    Resolves PathLike objects to pathlib.Path
    """
    if relative_to_self:
        return Path(__file__).parent.joinpath(s)
    return s if isinstance(s, Path) else Path(s)


def invoke(*args, splitby: Optional[tuple[Optional[str], int]], check: bool = True) -> Generator[str | List[str], None, CompletedProcess]:
    """
    Invokes a subprocess and yields stdout lines, optionally split into chunks.
    """

    with Popen(args=args, stdin=None, stdout=PIPE, text=True) as proc:
        stdout: TextIOBase = proc.stdout

        rc: Optional[int]
        while (rc := proc.poll()) is None:
            if not (line := stdout.readline()):
                continue

            if splitby:
                yield line.split(splitby[0], splitby[1])
            else:
                yield line

        if check and rc:
            raise CalledProcessError(rc, args, proc.stdout, proc.stderr)

        return CompletedProcess(args, rc, proc.stdout, proc.stderr)


def generate_releases_file(tag_thres: TagName, repo_base_url: str, fd_out: int):
    """
    Generate an AppStream releases.xml file  from all tags in the current git repository up until the given tag name
    (see https://www.freedesktop.org/software/appstream/docs/sect-Metadata-Releases.html)
    """

    date_thres: str = invoke2(["git", "log", "-1", "--format=%cI", tag_thres, "--"])  # ISO 8601

    root = xml.Element("releases")
    for tag, date in invoke("git", "log", "--tags", "--format=%S %cI", "--no-walk", "--until", date_thres, "--", splitby=(None, 2)):

        url = xml.Element("url")
        url.text = f"{repo_base_url}/releases/tag/{tag}"

        e = xml.Element("release", version=tag, date=date)
        e.append(url)
        root.append(e)

    with os.fdopen(fd_out, "w") as out:
        xml.ElementTree(root).write(out, "unicode", True)


def generate_metainfo(template: xml.ElementTree, inc: Optional[Sequence[YamlXmlElement]], format_args: Mapping[str, str]) -> xml.ElementTree:
    """
    Generate an AppStream Component Metainfo XML ElementTree
    (see https://www.freedesktop.org/software/appstream/docs/index.html)
    """
    metainfo: xml.ElementTree = copy.deepcopy(template)
    root = metainfo.getroot()

    if inc:
        queue: Deque[tuple[xml.Element, YamlXmlElement]] = Deque([(root, it) for it in inc])
        while queue:
            parent, yxe = queue.popleft()

            tag: str = yxe["tag"]
            attrib: dict = yxe.get("attrib", {})

            if (text := yxe.get("text", None)) or (e := parent.find(tag)) is None:
                # new element
                e = xml.Element(tag, attrib)
                e.text = text or None
                parent.append(e)
            # else: existing element

            queue.extend((e, it) for it in yxe.get("children", []))

    # format metainfo
    for e in metainfo.iter():
        e: xml.Element
        for name in ["attrib", "text", "tail"]:
            setattr(e, name, fmt(getattr(e, name), format_args))

    return metainfo


def generate_manifest_file(
    manifest_template: Mapping,
    metainfo_template: xml.ElementTree,
    manifest_inc: Mapping,
    format_args: Mapping[str, str],
) -> PathLike:
    manifest: dict = copy.deepcopy(manifest_template)

    ## populate variant manifest ##
    module: dict = manifest["modules"][0]
    post_install: List[str] = module["post-install"]
    build_commands: List[str] = module["build-commands"]
    sources: List[dict] = module["sources"]

    # append commands
    manifest["command"] = manifest_inc["command"]
    manifest["finish-args"].extend(manifest_inc["finish-args"] or [])
    build_commands.extend(manifest_inc["build-commands"] or [])
    post_install.extend(manifest_inc["post-install"] or [])

    # add entrypoint commands to entrypoint
    entrypoint: dict = sources[-1]
    assert entrypoint["type"] == "script" and entrypoint["dest-filename"] == "entrypoint"
    entrypoint["commands"].extend((manifest_inc["entrypoint"] or []) + ['exec "$@"'])

    # add sources
    scripts: List[str] = []
    for s in manifest_inc["sources"] or []:
        s: dict
        sources.append(s)

        dest: Path = Path(s.get("dest-filename", None) or s.get("path", None) or urlparse(s.get("url", "")).path)
        if not dest.name:
            continue

        match s["type"]:
            case "script":
                scripts.append(dest.name)
            case "file":
                if dest.name.endswith(".desktop"):
                    post_install.append(f'install -Dm644 "{dest.name}" "$FLATPAK_DEST/share/applications/$FLATPAK_ID-{dest.stem}.desktop"')

    # generate metainfo
    metainfo_out: tuple[int, str] = mkstemp("metainfo", ".xml")
    metainfo = generate_metainfo(metainfo_template, manifest_inc["metainfo"], {"id": manifest["id"], **format_args})

    # normalize manifest
    format_args: dict = {
        "id": manifest["id"],
        "name": metainfo.findtext("name"),
        "command": manifest["command"],
        "binfiles": " ".join(f'"{it}"' for it in scripts),
        "metainfo-path": metainfo_out[1],
    }
    manifest: dict = fmt(manifest, format_args)
    for s in manifest["modules"][0]["sources"]:
        s: dict
        if _path := s.get("path", None):
            s["path"] = path(_path).resolve().as_posix()

    # write metainfo
    with os.fdopen(metainfo_out[0], "w") as out:
        metainfo.write(out, "unicode", True)

    # write manifest
    manifest_out = mkstemp("manifest", ".yml")
    with os.fdopen(manifest_out[0], "w") as out:
        yaml.safe_dump(manifest, out, sort_keys=False, width=0x7FFFFFFF)

    return manifest_out[1]


def main() -> int:
    try:
        variants = sys.argv[1:]

        if not variants:
            x = OSError("Missing input arguments")
            x.errno = 2
            raise x

        # releases.xml
        releases_out: tuple[int, str] = mkstemp("releases", ".xml")
        generate_releases_file(env["TAG_NAME"], env["REPOSITORY_URL_BASE"], releases_out[0])

        # load manifest template
        manifest: dict
        with path("manifest.template.yml").open("r") as f:
            manifest = yaml.safe_load(f)

        # load metainfo template
        metainfo = xml.parse("metainfo.template.xml")

        format_args = {
            "releases-path": releases_out[1],
        }

        for variant in variants:
            # load variant inc
            with path(f"inc.{variant}.yml").open("r") as f:
                inc = DefaultDict(lambda: None, yaml.safe_load(f))

            # generate
            _path = generate_manifest_file(manifest, metainfo, inc, format_args)
            print(f"{variant}={_path}")

    except BaseException as x:
        for fd, name in TMP_FILES:
            try:
                os.close(fd)
            except:
                pass
            try:
                os.unlink(name)
            except:
                pass

        if isinstance(x, OSError):
            progname = Path(__file__).name
            err = x.strerror or ":".join(x.args)
            filename = x.filename

            if filename:
                filename = Path(filename).relative_to(os.getcwd())
                print(f"{progname}: {filename}: {err}", file=sys.stderr)
            else:
                print(f"{progname}: {err}", file=sys.stderr)

            return x.errno or 1

        if isinstance(x, KeyboardInterrupt):
            return 130

        raise


if __name__ == "__main__":
    exit(main())
