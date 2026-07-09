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
Several Merc module related fixes:
Added ability to map merc modules from loadout events
Prevent swapping blueprints in the wishlist to unsupported combinations
Previously you could put a G5 blueprint Lightweight Sink Launcher on the wishlist and then swap to Ammo Capacity that only allowed G1. It then still allowed to set G2-G5, which was invalid.
Prevent quickswitch size/class in ship editor for merc modules. Each merc module has its own blueprint, so there are no compatible other modules to swap to. 

* Early access:
* More modules with stats. G1 should be available on all, but for some weapons, higher grades are still missing and require in game testing.
```