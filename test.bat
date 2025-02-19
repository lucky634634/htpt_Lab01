@echo off
setlocal enableextensions

cd ./cwd

for /L %%i in (0,1,1) do (
    start cmd /c "java --enable-preview ../src/App.java test.cfg %%i"
)

endlocal