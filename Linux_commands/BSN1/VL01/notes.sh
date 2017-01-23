#!/bin/bash

#intern command to check type 
type #command check if command is intern(built-in shell) or not

#extern command
which #command check where program is installed

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

# Argumente auswerten
while getopts ":v::f:" opt; do
  case $opt in
    v)
      echo "-v was triggered with $OPTARG!"
      ;;
    f)
      echo "-f was triggered with $OPTARG!"
      ;;
    \?)
      echo "Invalid option: -$OPTARG"
      ;;
	:)
	echo "option -$OPTARG requires an argument!"
  esac
done

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

>  # replace content
>> # append output

#z.B.:
ll > result.txt
ll 1> result.txt #dasselbe
ll 2>> errors.log #Fehler umleiten und anhängen
ll 2>&1 alles.txt #leitet 1 und 2 um

free	#zeigt RAM-Informationen des Systems an

du -ch ~	#gibt den Speicherverbrauch des Homeverzeichnisses an
du -ca --block-size MB ~ #all
du -s --block-size MB ~ #summarize

du -ah ~ | sort -hr | less #sortiert nach Größe
du -ah ~ | sort -hr | head -n 10 #nur 10 Ergebnisse

sort -fk4 schausteller.txt #Sortiert die Datei nach dem Stadtnamen (ignorecase)


touch example.file
ln example.file hardlink_2_example.file 	#ist danach die selbe Datei, funktioniert nur innerhalb der selben Partition
ln -s example.file softlink_2_example.file #verweist nur auf die Datei

ls -li 	#zeigt Link informationen an
du -a | sort -hr #der Hardlink auf example taucht hier nicht auf (selbe Inode-Nr.)

#löschen von hardlinks:
rm hardlink_2_example.file
# Datei ist erst dann vollständig gelöscht wenn ALLE Hardlinks gelöscht sind

mkfifo #name #eine neue Pipe mit Namen erstellen, Name ist z.B. ein Verzeichnis


newgrp #gruppenname #erstellt eine neu Gruppe
chown #neuerBesitzer Datei
chgrp #neueGruppe Datei

#Rechte rwx = 4+2+1
chmod a+x file	#all
chmod u+x file	#user
chmod go-x file	#group and other
chmod 664 file

ifconfig 	# shows the network-interface information
arp 	# shows the content of the ARP-cache (Adress Resolution Protocol)
route 		# shows the IP routing table 

traceroute #windows tracert, nachverfolgen des Pfades eines IP-Pakets

nc -l portno # start netcat using a listening port
nc address port #start netcat trying to connect to an address with target port
