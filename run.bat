@echo off
echo =================================
echo   SMART GYM MANAGEMENT SYSTEM
echo =================================
echo.

REM Check if MySQL connector exists
if not exist "mysql-connector-j-9.6.0.jar" (
    echo MySQL Connector not found!
    echo Please run download-mysql.bat first or download manually.
    echo.
    pause
    exit /b 1
)

REM Check if src directory exists
if not exist "src" (
    echo Source directory not found!
    echo Please ensure the backend is properly set up.
    echo.
    pause
    exit /b 1
)

echo Compiling Java files...
javac -cp ".;mysql-connector-j-9.6.0.jar" src\*.java

if %errorlevel% neq 0 (
    echo.
    echo Compilation failed! Please check the errors above.
    pause
    exit /b 1
)

echo.
echo Compilation successful!
echo.
echo Starting server on http://localhost:8080
echo Press Ctrl+C to stop the server
echo.

java -cp ".;mysql-connector-j-9.6.0.jar" src.Main

pause
