# Nothing Launcher

A completely blank Android launcher.

- **Home screen**: solid black — nothing else
- **Any swipe**: opens the app menu (list of all installed apps)
- No widgets, no icons, no wallpaper, no features

## Download

Grab the latest APK from the [Releases](../../releases) page, or from the
[Actions](../../actions) artifacts after a successful build.

## Install

1. Download `app-debug.apk` to your Android device
2. Enable **Install from unknown sources** if prompted
3. Open the APK file and install it
4. When prompted to choose a default launcher, select **Nothing Launcher**

## Build from source

```bash
git clone https://github.com/ZackaryW/nothing-launcher.git
cd nothing-launcher
./gradlew assembleDebug
# APK: app/build/outputs/apk/debug/app-debug.apk
```

Requires JDK 17+. The Gradle wrapper downloads the Android build tools automatically on first run.
