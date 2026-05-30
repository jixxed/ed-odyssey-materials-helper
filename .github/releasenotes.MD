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
MacOs on Apple Silicon support has been added thanks to @stefanlight8
Added ship links V2. when you share a ship build on discord, it will preview a lot of ship data.
Fixed notifications not showing after import, while still making a sound 
```