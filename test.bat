@echo off
setlocal enableextensions

cmd /c "TestConfig.bat"

cd ./cwd

for /L %%i in (0,1,2) do (
    start cmd /c "java --enable-preview ../src/App.java test.cfg %%i"
)

endlocal