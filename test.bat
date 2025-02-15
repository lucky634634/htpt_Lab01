@echo off
setlocal enableextensions

cd ./src

for /L %%i in (0,1,2) do (
    start cmd /c "java --enable-preview ./App.java %%i"
)

endlocal