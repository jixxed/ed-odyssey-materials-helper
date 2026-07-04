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
from xml.etree import ElementTree as xml
from os import PathLike
from pathlib import Path
from io import TextIOBase
from subprocess import Popen, CompletedProcess, CalledProcessError, check_output as invoke2, PIPE
from typing import Generator, Mapping, defaultdict, List, overload, Sequence, TypedDict, NotRequired, Deque


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


def fmt[T](value: T, args: Mapping[str, str]) -> T:
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
    Generate a AppStream releases.xml file (as specified by https://www.freedesktop.org/software/appstream/docs/sect-Metadata-Releases.html)
    from all tags in the current git repository up until the given tag name
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
    metainfo: xml.ElementTree = copy.deepcopy(template)
    root = metainfo.getroot()

    if inc:
        queue: Deque[tuple[xml.Element, YamlXmlElement]] = Deque([(root, it) for it in inc])
        while queue:
            e, yxe = queue.popleft()

            new_e = xml.Element(yxe["tag"], yxe.get("attrib", {}))
            if text := yxe.get("text", None):
                new_e.text = text

            e.append(new_e)

            queue.extend((new_e, it) for it in yxe.get("children", []))

    # format metainfo
    for e in metainfo.iter():
        e: xml.Element
        for name in ["attrib", "text", "tail"]:
            setattr(e, name, fmt(getattr(e, name), format_args))

    return metainfo


def generate_manifest_file(
    manifest_template: Mapping,
    metainfo_template: xml.ElementTree,
    inc_file: PathLike,
    format_args: Mapping[str, str],
) -> PathLike:
    manifest: dict = copy.deepcopy(manifest_template)
    inc: dict

    # load variant inc
    with path(inc_file).open("r") as f:
        inc = defaultdict(lambda: None, yaml.safe_load(f))

    metainfo_out: tuple[int, str] = mkstemp("metainfo", ".xml")

    format_args: dict = {
        "id": manifest["id"],
        "binfiles": "",
        "desktop-file-edit": " ".join(inc["desktop-file-edit"] or []),
        "metainfo-path": metainfo_out[1],
        **format_args,
    }

    # populate variant manifest
    if s := inc["command"]:
        s: str
        manifest["command"] = s

    if s := inc["finish-args"]:
        s: List[str]
        manifest["finish-args"].extend(s)

    module: dict = manifest["modules"][0]
    post_install: List[str] = module["post-install"]
    sources: List[dict] = module["sources"]

    scripts: List[str] = []
    for s in inc["sources"] or []:
        s: dict
        sources.append(s)

        dest: Path = Path(s.get("dest-filename", None) or s["path"])

        match s["type"]:
            case "script":
                scripts.append(dest.name)
            case "file":
                if dest.name.endswith(".desktop"):
                    post_install.append(f'install -Dm644 "{dest.name}"t "$FLATPAK_DEST/share/applications/$FLATPAK_ID-{dest.stem}.desktop"')

    # set binfiles from declared scripts
    format_args["binfiles"] = " ".join(f'"{it}"' for it in scripts)

    # generate metainfo
    metainfo = generate_metainfo(metainfo_template, inc["metainfo"], format_args)
    with os.fdopen(metainfo_out[0], "w") as out:
        metainfo.write(out, "unicode", True)

    # write out
    manifest_out = mkstemp("manifest", ".yml")

    with os.fdopen(manifest_out[0], "w") as out:
        yaml.safe_dump(fmt(manifest, format_args), out, sort_keys=False, width=0x7FFFFFFF)

    return manifest_out[1]


if __name__ == "__main__":
    import sys, copy

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
            _path = generate_manifest_file(manifest, metainfo, f"inc.{variant}.yml", format_args)
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

            exit(x.errno or 1)

        if isinstance(x, KeyboardInterrupt):
            exit(130)

        raise
