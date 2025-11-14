@echo off
echo Building MadalyonMC Launcher Windows Installer...
echo.

REM Check prerequisites
echo Checking prerequisites...
java -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Java not found! Please install Java 17 or higher.
    exit /b 1
)

REM Build the launcher
echo Building launcher with Gradle...
echo.
echo [1/4] Cleaning previous builds...
call gradlew.bat clean

if errorlevel 1 (
    echo ERROR: Clean failed!
    exit /b 1
)

echo.
echo [2/4] Building project...
call gradlew.bat build --info

if errorlevel 1 (
    echo ERROR: Build failed!
    echo.
    echo Common solutions:
    echo 1. Make sure you have Java 17 or higher installed
    echo 2. Check your internet connection
    echo 3. Run: gradlew.bat --refresh-dependencies
    echo 4. Delete .gradle folder and try again
    exit /b 1
)

echo.
echo [3/4] Creating shadow JAR...
call gradlew.bat shadowJar

if errorlevel 1 (
    echo ERROR: Shadow JAR creation failed!
    exit /b 1
)

echo.
echo [4/4] Build completed successfully!
echo.

REM Check if JAR was created
if exist HMCL\build\libs\HMCL-*.jar (
    echo JAR file created successfully:
    dir HMCL\build\libs\
    echo.
    
    REM Create EXE using launch4j if available
    where launch4j >nul 2>nul
    if %errorlevel% equ 0 (
        echo Creating Windows executable with launch4j...
        
        REM Create launch4j configuration
        echo ^<?xml version="1.0" encoding="UTF-8"?^> > launch4j-config.xml
        echo ^<launch4jConfig^> >> launch4j-config.xml
        echo   ^<dontWrapJar^>false^</dontWrapJar^> >> launch4j-config.xml
        echo   ^<headerType^>gui^</headerType^> >> launch4j-config.xml
        echo   ^<jar^>HMCL/build/libs/HMCL-3.0-SNAPSHOT.jar^</jar^> >> launch4j-config.xml
        echo   ^<outfile^>MadalyonMC-Launcher.exe^</outfile^> >> launch4j-config.xml
        echo   ^<errTitle^>MadalyonMC Launcher Error^</errTitle^> >> launch4j-config.xml
        echo   ^<cmdLine^>^</cmdLine^> >> launch4j-config.xml
        echo   ^<chdir^>.^</chdir^> >> launch4j-config.xml
        echo   ^<priority^>normal^</priority^> >> launch4j-config.xml
        echo   ^<downloadUrl^>https://adoptium.net/^</downloadUrl^> >> launch4j-config.xml
        echo   ^<supportUrl^>https://madalyonmc.com^</supportUrl^> >> launch4j-config.xml
        echo   ^<stayAlive^>false^</stayAlive^> >> launch4j-config.xml
        echo   ^<restartOnCrash^>false^</restartOnCrash^> >> launch4j-config.xml
        echo   ^<manifest^>^</manifest^> >> launch4j-config.xml
        echo   ^<classPath^> >> launch4j-config.xml
        echo     ^<mainClass^>com.madalyonmc.launcher.EntryPoint^</mainClass^> >> launch4j-config.xml
        echo   ^</classPath^> >> launch4j-config.xml
        echo   ^<jre^> >> launch4j-config.xml
        echo     ^<path^>^</path^> >> launch4j-config.xml
        echo     ^<bundledJre64Bit^>false^</bundledJre64Bit^> >> launch4j-config.xml
        echo     ^<bundledJreAsFallback^>false^</bundledJreAsFallback^> >> launch4j-config.xml
        echo     ^<minVersion^>17^</minVersion^> >> launch4j-config.xml
        echo     ^<maxVersion^>^</maxVersion^> >> launch4j-config.xml
        echo     ^<jdkPreference^>preferJre^</jdkPreference^> >> launch4j-config.xml
        echo     ^<runtimeBits^>64/32^</runtimeBits^> >> launch4j-config.xml
        echo   ^</jre^> >> launch4j-config.xml
        echo ^</launch4jConfig^> >> launch4j-config.xml
        
        launch4j launch4j-config.xml
        
        if exist MadalyonMC-Launcher.exe (
            echo SUCCESS: EXE file created!
            echo.
        ) else (
            echo WARNING: EXE creation failed.
        )
    ) else (
        echo.
        echo NOTE: launch4j not found. Skipping EXE creation.
        echo To create EXE, install launch4j from: http://launch4j.sourceforge.net/
    )
) else (
    echo ERROR: JAR file not found!
    exit /b 1
)

echo.
echo Creating Windows installer...
echo.
echo Installer Options:
echo =================
echo.
echo Option 1: Inno Setup (Recommended)
echo   1. Install Inno Setup from: https://jrsoftware.org/isinfo.php
echo   2. Run: iscc madalyonmc-installer.iss
echo.
echo Option 2: WiX Toolset
echo   1. Install WiX Toolset from: https://wixtoolset.org/
echo   2. Run: candle madalyonmc-installer.wxs
echo   3. Run: light madalyonmc-installer.wixobj
echo.
echo Option 3: jpackage (Java 14+)
echo   jpackage --name "MadalyonMC Launcher" --main-jar HMCL/build/libs/HMCL-*.jar --main-class com.madalyonmc.launcher.EntryPoint --type exe

echo.
echo Code signing (optional, after building installer):
echo   signtool sign /fd SHA256 /a /t http://timestamp.digicert.com MadalyonMC-Launcher-Setup.exe

echo.
echo Build completed successfully!
echo Files created:
echo   - JAR: HMCL/build/libs/HMCL-*.jar
if exist MadalyonMC-Launcher.exe echo   - EXE: MadalyonMC-Launcher.exe
echo.
echo You can now distribute your launcher!
echo.
pause