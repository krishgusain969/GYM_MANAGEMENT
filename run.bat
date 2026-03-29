@echo off
echo =================================
echo   SMART GYM MANAGEMENT SYSTEM
echo =================================
echo.

REM Check if SQLite connector exists
if not exist "sqlite-jdbc-3.45.1.0.jar" (
    echo SQLite Connector not found!
    echo Please download sqlite-jdbc-3.45.1.0.jar
    echo Or run: curl -L -o sqlite-jdbc-3.45.1.0.jar "https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.45.1.0/sqlite-jdbc-3.45.1.0.jar"
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

echo Compiling Java files with SQLite...
javac -cp ".;sqlite-jdbc-3.45.1.0.jar" src\*.java

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
echo Using SQLite database (gym_management.db)
echo Press Ctrl+C to stop the server
echo.

java -cp ".;sqlite-jdbc-3.45.1.0.jar" src.Main

pause
