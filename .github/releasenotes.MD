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
Added databases to the app
- Journals will be scraped for relevant events when loading a commander. This can take a while the first time.
- Known star systems are stored in the database for distance calculations
- Community goal progress is stored in the database for tracking personal progress (no UI yet)
- A future version will allow for recording owned ships and modules, but this is not implemented yet
```