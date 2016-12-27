#!/bin/bash
# ändert die Dateiendung aller Dateien die mit .jpeg enden 
# im aktuellen Verzeichnis in .jpg um
# Leo 13.10.2016
####
#
for file in *.jpeg
do
	echo $file
	basename=`basename $file .jpeg`
	echo $basename
	cp $file $basename.jpg
done
echo Fertig!
