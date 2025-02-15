@echo off
setlocal
break > .\cwd\config.txt
for /L %%i in (0,1,15) do (
    echo [process] >> .\cwd\config.txt
    echo id = %%i >> .\cwd\config.txt
    echo addr = 127.0.0.1 >> .\cwd\config.txt
    if /i %%i LSS 10 (
        echo port = 500%%i >> .\cwd\config.txt
    ) else (
        echo port = 50%%i >> .\cwd\config.txt 
    )
    echo: >> .\cwd\config.txt
)
endlocal