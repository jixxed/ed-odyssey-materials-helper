> :warning: **License Notice**\
> By downloading or using any binary from this release, you agree to the terms of the [End User License Agreement (EULA)](EULA.MD).\
> The EULA prohibits reverse engineering, extraction, or redistribution of proprietary components or embedded credentials and secrets.

:question: **What file should you download?**
If you are on Windows, you can install the auto updater msi file
If you are on Linux, you can install the auto updater deb or rpm file. If you are on an unsupported linux distro, you can download the portable autoupdater zip file.
If you are on MacOs, you can install the auto updater dmg file.

The auto updaters will check for a new version on launch.
The portable zip versions are used as update packages. You can run them standalone, but they are no longer truly portable as settings are stored on the local machine.

For MacOs the packages are unsigned and require you to run the following commands:
```shell
# for portable
$ xattr -d com.apple.quarantine "/Applications/Elite Dangerous Odyssey Materials Helper.app"
# for launcher
$ xattr -d com.apple.quarantine "/Applications/Elite Dangerous Odyssey Materials Helper Launcher.app"
# or via settings: Open app -> Privacy & Security -> Allow
```

:page_facing_up: **Release notes for this version:**
```
Fix ship list refresh to old behavior, behavior added in 3.9.5 is now only for delete actions
Fix pinned blueprint showing grade < 1 for invited state at start
Fix Increased FSD Range - Optimised Mass start value to 0% instead of 5%
Added error notification on DB error, for example on disk full
Handle empty carrier names gracefully
Added unique styleclass to ship material cards to allow for css override
```