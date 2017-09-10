@ECHO OFF
::copy /y nul donald.txt

SET dummiesDir=.\dummies
SET originalsDir=.\originals

FOR /F "tokens=*" %%a IN ('DIR /ON /A-D /B %originalsDir%') DO (
	echo creating dummy for: "%%a"
	copy /y nul "%dummiesDir%/%%a"
)

pause