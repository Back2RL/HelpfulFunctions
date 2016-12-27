#!/bin/bash

if [ "$1" == "" ] 
then
	echo "Bitte Dateipfad mit angeben!"
	exit 1
fi
if [ ! -f $1 ]
then 
	echo $1" ist keine Datei!"
	exit 1
fi


#a)
echo `cat $1 | wc -l`" Zeilen"
echo `cat $1 | wc -w`" Wörter"
echo `cat $1 | wc -m`" Buchstaben"

#b)
echo `cat $1 | grep *"Müller "*`
echo `cat $1 | grep -nie müller`
