@echo off
setlocal EnableExtensions
chcp 65001 >nul
title Open Zio 2.4.4 - Windows Builder

cd /d "%~dp0"

echo ==========================================
echo   Open Zio 2.4.4 - Minecraft 1.21.11
echo   Windows one-click build
echo ==========================================
echo.

where java >nul 2>nul
if errorlevel 1 (
    echo [ERROR] Java was not found.
    echo Install a 64-bit JDK 21, then reopen this script.
    echo Download: https://adoptium.net/temurin/releases/?version=21
    echo.
    pause
    exit /b 1
)

for /f tokens^=2^ delims^=^" %%V in ('java -version 2^>^&1 ^| findstr /i "version"') do set "JAVA_VERSION=%%V"
echo Detected Java: %JAVA_VERSION%
echo.

java -version 2>&1 | findstr /c:"21." >nul
if errorlevel 1 (
    echo [WARNING] This project targets Java 21.
    echo If the build fails, install and select a 64-bit JDK 21.
    echo.
)

if not exist "gradlew.bat" (
    echo [ERROR] gradlew.bat was not found.
    echo Run this script from the extracted project folder.
    pause
    exit /b 1
)

echo [INFO] Starting Gradle build. The first run needs an internet connection
echo        to download Gradle and project dependencies.
echo.
call gradlew.bat build --no-daemon
set "BUILD_EXIT=%ERRORLEVEL%"

echo.
if not "%BUILD_EXIT%"=="0" (
    echo [FAILED] Gradle build failed with exit code %BUILD_EXIT%.
    echo Check the error output above. Common causes:
    echo   - No internet connection / dependency download failure
    echo   - Java version is not 21
    echo   - A dependency repository is temporarily unavailable
    echo.
    pause
    exit /b %BUILD_EXIT%
)

echo [SUCCESS] Gradle build completed.
echo.
if exist "build\libs\zio-2.4.4.jar" (
    echo Mod JAR:
    echo "%CD%\build\libs\zio-2.4.4.jar"
    echo.
    if not exist "mods" mkdir "mods"
    copy /Y "build\libs\zio-2.4.4.jar" "mods\zio-2.4.4.jar" >nul
    echo A copy was placed in:
    echo "%CD%\mods\zio-2.4.4.jar"
) else (
    echo [INFO] JAR files produced in build\libs:
    if exist "build\libs\*.jar" (
        dir /b "build\libs\*.jar"
    ) else (
        echo No JAR found. Check Gradle output above.
    )
    echo.
    echo Do not install a *sources.jar or *-dev.jar file as the mod.
)

echo.
echo Done. Press any key to close.
pause >nul
exit /b 0
