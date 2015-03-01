@echo off
echo before-pause
pause
force-a-error-to-stdout
dir force-a-error-to-stdout
echo after-pause
rem #hang in here
pause