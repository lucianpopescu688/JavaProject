@echo off
set output=merged_project.txt
echo. > %output%

for /R . %%f in (*.java) do (
    echo // File: %%~nxf >> %output%
    type "%%f" >> %output%
    echo. >> %output%
)
echo All Java files have been merged into %output%
pause
