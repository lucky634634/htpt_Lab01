@echo off
setlocal enableextensions

mkdir ./bin 2> nul

javac --enable-preview --release 23 -d ./bin ./src/*.java

endlocal