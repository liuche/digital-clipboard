# CLPY app

A clipboard for handling links from the web.

<img src="media/clpy-screenshot.png?raw=true" height="400">

## V1

Focus on the Hold/Push stage of the task continuity cycle, give users an experience that allows them to quickly share, dismiss, or snooze items on their clipboard.

## V2 (for later)

Expand scope to add support for more Movie sites (Rotten Tomatoes, Metacritic, etc), include more types of links (not just Movies?), add a "Discover Mode" for related content, show suggestions (who else reviewed this? what else would I be interested in?), and others.

## Android Prototype 
The base Android app is forked from Fathom Android Experiments ( https://github.com/mcomella/fathom-android-experiments ) which is a series of experiments to get Mozilla's [fathom][] running in the context of
native Android apps.

### Try it
Install the npm dependencies:

    npm install

Build the fathom code:

    npm run build

Build and install via gradle (assuming you have an Android device or emulator
connected):

    ./gradlew assembleDebug installDebug

Launch the application `FathomTest` on device! Alternatively, build and run via
Android Studio or another IDE.

[fathom]: https://github.com/mozilla/fathom
