> :warning: **License Notice**\
> By downloading or using any binary from this release, you agree to the terms of the [End User License Agreement (EULA)](EULA.MD).\
> The EULA prohibits reverse engineering, extraction, or redistribution of proprietary components or embedded credentials and secrets.

:question: **What file should you download?**
If you are on Windows, you can install the auto updater msi file
If you are on Linux, you can install the auto updater deb or rpm file. If you are on an unsupported linux distro, you can download the portable autoupdater zip file.

The auto updaters will check for a new version on launch.
The portable zip versions are used as update packages. You can run them standalone, but they are no longer truly portable as settings are stored on the local machine.

:page_facing_up: **Release notes for this version:**
```
Added AR fuzzy text matching configuration option
- If detection with exact matching does not work well for you, try enabling fuzzy matching (does increase the chance of false positives)
- The percentage a read text has to match to a material is configurable (default 90%)
- Application logs provide insight into what text was read and what materials were matched
Added an on-screen hint that you can rescan by moving the mouse cursor over the download arrow
Fixed swapped red and blue color channels for AR menu item detection
Handle warning offset in AR menu item detection
```