
@echo off
setlocal
break > .\cwd\test.cfg

for /L %%i in (0,1,1) do (
    echo [process] >> .\cwd\test.cfg
    echo id = %%i >> .\cwd\test.cfg
    echo addr = 127.0.0.1 >> .\cwd\test.cfg
    if /i %%i LSS 10 (
        echo port = 500%%i >> .\cwd\test.cfg
    ) else (
        echo port = 50%%i >> .\cwd\ test.cfg
    )
    echo: >> .\cwd\test.cfg
)
endlocal