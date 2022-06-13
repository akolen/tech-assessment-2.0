# 🍎 Oepfelbaum Tech Assessment

## To get started

###Client
#####Install dependencies
* Run `npm install`
  
#####Development
* Run `npm run dev`
- Runs the app in the development mode. Open http://localhost:3000 to view it in the browser.
  
###Server
1. File -> Open... -> tech-assessment-2.0
2. Accept to import the project as a `gradle project`
#### Building with Gradle
You can use the local Gradle Wrapper to build the application.

Platform-Prefix:

-   MAC OS X: `./gradlew`
-   Linux: `./gradlew`
-   Windows: `./gradlew.bat`

### Build

```bash
./gradlew build
```

### Run

```bash
./gradlew bootRun
```

### Test

```bash
./gradlew test
```

### Development Mode

You can start the server in development mode, this will automatically trigger a new build and reload the application
once the content of a file has been changed and you save the file.

Start two terminal windows and run:

`./gradlew build --continuous`

and in the other one:

`./gradlew bootRun`

If you want to avoid running all tests with every change, use the following command instead:

`./gradlew build --continuous -xtest`

