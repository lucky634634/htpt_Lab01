@echo off
setlocal enableextensions

cmd /c "GenerateConfig.bat"

cd ./cwd || exit

for /L %%i in (0,1,11) do (
    start cmd /c "title Process %%i & java --enable-preview ../src/App.java config.cfg %%i"
)

endlocal