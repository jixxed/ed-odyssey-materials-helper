#!/usr/bin/env python3
import os
import sys
import json
import re
import subprocess
from glin_profanity import Filter

# ---------------- CONFIG ----------------
SUPPORTED_LANGS = ["en", "de", "es", "fr", "pt", "ru", "zh", "ka"]

# Initialize the profanity filter
pf = Filter({
    "languages": SUPPORTED_LANGS,
    "allow_obfuscated_match": True,
    "fuzzy_tolerance_level": 0.8,
})

# ---------------- HELPERS ----------------
def run(cmd):
    """Run a shell command and return its stdout as text."""
    return subprocess.check_output(cmd, text=True).strip()


def get_changed_lines(file_path, base_sha, head_sha):
    """
    Return a list of (line_no, text) for lines added/modified in `file_path`
    between base_sha and head_sha.
    """
    cmd = ["git", "diff", f"{base_sha}..{head_sha}", "--", file_path]
    diff = run(cmd)
    lines = []
    current_line = None
    for line in diff.splitlines():
        if line.startswith("@@"):
            # Parse hunk header like: @@ -a,b +c,d @@
            m = re.match(r"@@ -\d+(?:,\d+)? \+(\d+)(?:,(\d+))? @@", line)
            if m:
                current_line = int(m.group(1))
        elif line.startswith("+") and not line.startswith("+++"):
            text = line[1:]
            lines.append((current_line, text))
            current_line += 1
        elif not line.startswith("-") and current_line is not None:
            current_line += 1
    return lines


def post_pr_comment(pr_number: str, message: str):
    """Post a comment to the given PR."""
    repo = os.getenv("GITHUB_REPOSITORY")
    print(f"Posting comment to PR #{pr_number} in {repo}")
    subprocess.run(
        [
            "gh",
            "api",
            f"repos/{repo}/issues/{pr_number}/comments",
            "-f",
            f"body={message}",
        ],
        check=True,
    )


# ---------------- MAIN ----------------
def main():
    if len(sys.argv) < 2:
        print("Usage: check_translations_profanity.py --json changed_files.json")
        sys.exit(0)

    # --- Read changed files from input JSON ---
    json_path = sys.argv[1]
    with open(json_path, "r", encoding="utf-8") as f:
        files = json.load(f)

    if not files:
        print("No translation files changed. Exiting.")
        return

    base_sha = os.getenv("GITHUB_BASE_SHA")
    head_sha = os.getenv("GITHUB_HEAD_SHA")
    pr_number = os.getenv("GITHUB_REF", "").split("/")[-1]

    if not (base_sha and head_sha):
        print("Missing base/head SHA environment variables.")
        sys.exit(0)

    all_violations = []

    for file_path in files:
        if not os.path.exists(file_path):
            continue

        print(f"Scanning {file_path} for profanity...")
        changed_lines = get_changed_lines(file_path, base_sha, head_sha)

        for line_no, text in changed_lines:
            result = pf.check_profanity(text)
            if result["contains_profanity"]:
                bad_words = ", ".join(result["profane_words"])
                all_violations.append(
                    f"**{file_path}** line {line_no}: `{bad_words}`"
                )

    if not all_violations:
        print("✅ No profanity detected in modified translation lines.")
        return

    # --- Build PR comment body ---
    comment_body = (
        "⚠️ **Potential profanity detected in translation changes**\n\n"
        "The following lines may contain flagged words. "
        "Please review to confirm if they are intentional translations.\n\n"
        + "\n".join(f"- {v}" for v in all_violations)
    )

    print(comment_body)

    # Get PR number more reliably if available from Crowdin step
    event_path = os.getenv("GITHUB_EVENT_PATH")
    if not pr_number and event_path and os.path.exists(event_path):
        with open(event_path, "r") as f:
            event = json.load(f)
            pr_number = str(event.get("pull_request", {}).get("number", ""))

    if pr_number:
        try:
            post_pr_comment(pr_number, comment_body)
        except Exception as e:
            print(f"⚠️ Failed to post comment: {e}")
    else:
        print("⚠️ No PR number detected; printing output only.")


if __name__ == "__main__":
    main()
