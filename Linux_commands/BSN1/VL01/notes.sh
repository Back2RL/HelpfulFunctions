#!/bin/bash

echo 'abc $uvw'	#wird als ein Wort betrachtet
uvw=stefan
echo "abc $uvw" #wird als ein Wort betrachtet aber Variablen werden ersetzt

echo `ls -la && echo $uvw` #Variablen werden ersetzt, wird als Kommando interpretiert und ausgeführt
erg=`ls *.pdf`	#speichert die Namen aller PDF-Dateien in der Variable erg
echo "$erg"
$#				#Anzahl der Parameter
$				#Alle Parameter hintereinander
$?				#Beendigungsstatus des letzten Kommandos

echo "$0"
# $0 - $9 sind Parameter des Shellskripts

#Zahlen
test 7 -eq 8
test 7 -ne 8
test 7 -gt 8
test 7 -ge 8
test 7 -lt 8
test 7 -le 8

#Strings
test "a" == "b"
test "a" != "b"
test -z "b"		#ist "b" leer?
test "a" < "b"	#alphabetisch kleiner?

#Dateien
test -f $0 #ist es eine Datei?
test -d $0 #ist es ein Verzeichnis?
test -w $0 #ist beschreibbar?
test -r $0 #ist lesbar?

# -a AND, -o OR, ! NOT

if [ -f "text.pdf" ]
then
	echo "text.pdf ist eine Datei"
elif [ 1 -eq 1 ]
then 
	echo "ja 1 ist gleich 1"
else
	echo "nothing"
fi


for number in 1 2 3 4 5 6 7 8 9 0
do
	echo "$number"
done

for file in `ls`
do 
	echo "$file"
done

ps	#existierende Prozesse
ps -e	#every (alle)
ps -ef	#full (ausfürlich)
ps -efl	#long (vollständig)

pstree	#Prozessbaum

kill -9 #1234 (PID)

at 2130 -f meinSkript #startet meinSkript nach Ablauf der Zeit
at -l 			#zeigt Prozess in der Warteschlange
at -d 12		#löscht Prozess an der Position 12 aus der Warteschlange 

jobs	#zeigt laufende Prozesse des Nutzers
killall #Prozessname (killt alle mit dem Namen)

top	#zeigt Systemasulastung an

nice #option process (Prozessen andere Prioritäten geben)

#Stdin  0
#stdout 1
#sterr  2

#z.B.:
ll > result.txt
ll 1> result.txt #dasselbe
ll 2>> errors.log #Fehler umleiten und anhängen
ll 2>&1 alles.txt #leitet 1 und 2 um
