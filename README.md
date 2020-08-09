# Build App

## Working Video Link [https://youtu.be/2kbwjiQrrns](https://youtu.be/2kbwjiQrrns)

## Step 1: Clone the repository, download dependencies

### Clone repository

Execute the following commands to download the AntennaPod repository and the repositories of its library projects:
	
	git clone --recursive https://github.com/sankalpchauhan-me/FlipkartGrid2.git  
	
### Set up the Android SDK
In order to be able to build the app, you should download the following SDK components:
  
- [Android SDK tools](https://developer.android.com/tools/sdk/tools-notes.html), Android SDK platform-tools and [Android SDK Build-tools](https://developer.android.com/tools/revisions/build-tools.html)
- The latest version of the [Android SDK platform](https://developer.android.com/tools/revisions/platforms.html)
- Android support repository
- Android support library


## Step 2: Set up build system

There are several different tools available for building AntennaPod. You only have to choose one of them.

### Building in Android Studio
- In [Android Studio](https://developer.android.com/sdk/index.html), go to File → Import Project…
- Select the root directory of the AntennaPod repository and click "OK".
- Select "import project from external model"
- Select "gradle" in the list below "import project from external model" and click "Next"
- Select "Use default gradle wrapper". You can also configure Android Studio to use the version of gradle that is installed on your system, but you have to make sure that it is compatible with the project's setup. See `Building on the command-line with gradle` for a detailed description of the wrapper script. 
- Click "Finish".


### Building in Eclipse

- In Eclipse, go to File → Import... → Android → Existing Android Code Into Workspace
- Select the root directory of the AntennaPod repository as the root directory in the import dialog.
- Deselect all projects except the one located in the root directory of AntennaPod (it should be called "MainActivity"). Click "Finish".
- Next, you have to import two library projects. Open the same import dialog and select the same root directory as before. Deselect all projects except the one located in `submodules/dslv/library` (it should be called "library"). Click "Finish" and rename the project, for example to "dslv".
- Repeat the previous step to import the appcompat-v7 library. It should be located in `Android SDK home/extras/android/support/v7/appcompat`.
- In eclipse, right-click the AntennaPod project, then go to Properties → Android. Make sure that both library projects are referenced by AntennaPod.
- If you want to run tests, you can also import the test project that is located in the "test" directory. This step is optional.
- Eclipse might refuse to build the app because of different versions of the support library. To solve this issue, right-click the AntennaPod project and go to Android Tools → Add Support Library... . Repeat this step with every one of the two library projects.
- Wait until Eclipse has finished building the workspace. If no errors are displayed, you can start developing. Congratulations!

### Building on the command-line with gradle
- You can either use the version of gradle that is installed on your system or the wrapper script `gradlew` in the project's repository. It is recommended to use the wrapper script because it will always use a version of gradle that is compatible with the project setup and it will update itself automatically if necessary.
- If you use the version of gradle that is installed on your system, make sure to use the latest version of gradle.
- You may need to set ANDROID_HOME to point to your Android SDK before running Gradle
```
export ANDROID_HOME=/Users/username/development/android/sdk
gradle foo
```

- The wrapper script can be found in the project's root folder. On Windows, you should use `gradlew.bat`, on all other operating system you should `gradlew`. You can execute the usual gradle commands by replacing the `gradle` command with `./gradlew`. For example  

		gradle assembleDebug
becomes 

		./gradlew assembleDebug

- Gradle will download the project's dependencies automatically when building the project.

#### Useful commands for building with gradle

Build debug APK:

	gradle assembleDebug
	
Install debug APK on device:
	
	gradle installDebug			
	
Print a list of all available commands:
	
	gradle tasks
	
More commands can be found in the [Gradle Plugin User Guide](http://tools.android.com/tech-docs/new-build-system/user-guide)

### Building a release APK with gradle

- If you want the release APK to be signed by gradle automatically, you have to create a file called `gradle.properties` in `app` sub-directory.
- Add the following lines to the `gradle.properties` and replace the value to the right of the `=` with information about your keystore:

		releaseStoreFile=keystore
		releaseStorePassword=password
		releaseKeyAlias=alias
		releaseKeyPassword=password
- Tips on [generating keystore](http://stackoverflow.com/questions/3997748/how-can-i-create-a-keystore)	
- Note: the release APK you created **CANNOT** install over the APK from official distribution. Android's security 
	check forbids so (your APK has a signature generated above, different from the one signed in the official one).

#### Useful commands for building release versions with gradle

Build release APK:

	gradle assembleRelease
	
Install release APK on device:
	
	gradle installRelease
	
	
