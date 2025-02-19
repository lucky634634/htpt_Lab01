
@echo off
setlocal enableextensions

cd ./cwd

for /L %%i in (0,1,15) do (
    start cmd /c "java --enable-preview ../src/App.java config.cfg %%i"
)

endlocal