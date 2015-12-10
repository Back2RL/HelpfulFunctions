::
::	PR1, WS2015/16
::
::	Leonard Oertelt
::	Matrikelnummer 1276156
::	leonard.oertelt@stud.hs-hannover.de
:: 
::	So 06. Dez 16:25:00 CEST 2015
::	-----------------------------------------
::	Diese Programm erleichtert das Kompilieren unter Windows ohne IDE
@echo off

SET ARGUMENTS=exampletext.txt

:START
cls
echo removing old program
	del %~n1.class
echo compiling java code 
	javac -encoding UTF-8 %1
::	javac %1
echo executing new program
	java %~n1 %ARGUMENTS%
pause
goto START
