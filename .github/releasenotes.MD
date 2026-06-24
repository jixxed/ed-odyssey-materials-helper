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
Added Sponsor Early Access
- Sponsors on Github will get early access to new features through different tiers. Patron first, followed by Supporter, then Backer and finally everyone.
- Periods can differ per feature.
- This is to give back to the people that have supported me throughout the years. Thank you!
- You can link your account under Settings > General

- Removed PayPal donation link, stopped working for whatever reason. Will be Github only for now.

* Early Access feature(7 day period): Ships
* - Added ship rotational profiles for all ships, except Cobra Mk IV. Asked Fdev for the ship, but they said no.
* - Updated some other ship related metrics

Rechecked all the ships and applied some corrections
- Type-7 has borked slot names, that did not import properly (Slot09_Size2, Slot09_Size1)
- Now instead of trying to map the slot number 09 to a slot using some complex logic, the entire name hardcoded in the slot and used instead.
- This also simplifies the SLEF generation for the Spansh Plotter.
- Corrected default layout for Type-8. Default layout was the one from Arx, which has a Cargo rack 7E, while the store has 6E.

- Added the new Vessel Hangars Mk II and renamed the Fighter Bays to Vessel Hangars
- Updated the price for laser Odyssey blueprints: Greater range, Headshot Damage, Hip Fire Accuracy. (kinetic and plasma are still the old double price ingame)
- Added processing of MassModuleStore events, so the current ship should update with it
```