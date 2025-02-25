@echo off
setlocal enableextensions

cmd /c "TestConfig.bat"

cd ./cwd || exit

for /L %%i in (0,1,2) do (
    start cmd /c "title Process %%i & java --enable-preview ../src/App.java test.cfg %%i 20"
)

endlocal