@echo off
echo Downloading MySQL Connector J...
echo.

REM Check if curl is available
curl --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: curl is not available. Please download MySQL Connector manually.
    echo Visit: https://dev.mysql.com/downloads/connector/j/
    echo Download mysql-connector-j-9.6.0.jar and place it in this directory.
    pause
    exit /b 1
)

echo Downloading mysql-connector-j-9.6.0.jar...
curl -L -o mysql-connector-j-9.6.0.jar "https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/9.6.0/mysql-connector-j-9.6.0.jar"

if %errorlevel% neq 0 (
    echo ERROR: Failed to download MySQL Connector.
    echo Please download manually from: https://dev.mysql.com/downloads/connector/j/
    pause
    exit /b 1
)

echo MySQL Connector downloaded successfully!
echo.
pause
