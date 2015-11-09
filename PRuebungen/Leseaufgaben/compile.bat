::
::	PR1, WS2015/16
::
::	Leonard Oertelt
::	Matrikelnummer 1276156
::	leonard.oertelt@stud.hs-hannover.de
:: 
::	Mo 5. Okt 12:30:08 CEST 2015
::	-----------------------------------------
::	Diese Programm erleichtert das Kompilieren unter Windows
@echo off

:START
cls
echo removing old program
	del %~n1.class
echo compiling java code 
	javac -encoding UTF-8 %1
::	javac %1
echo executing new program
	java %~n1
pause
goto START
