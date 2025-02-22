@echo off
setlocal enableextensions

cmd /c "GenerateConfig.bat"

cd ./cwd

for /L %%i in (0,1,11) do (
    start cmd /c "java --enable-preview ../src/App.java config.cfg %%i"
)

endlocal