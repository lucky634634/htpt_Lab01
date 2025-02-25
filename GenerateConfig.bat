@echo off
setlocal
break > .\cwd\config.cfg

for /L %%i in (0,1,11) do (
    echo [process] >> .\cwd\config.cfg
    echo id = %%i >> .\cwd\config.cfg
    echo addr = 127.0.0.1 >> .\cwd\config.cfg
    if /i %%i LSS 10 (
        echo port = 500%%i >> .\cwd\config.cfg
    ) else (
        echo port = 50%%i >> .\cwd\config.cfg
    )
    echo: >> .\cwd\config.cfg
)
endlocal