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
echo "Müller kommt vor:"
echo `cat $1 | grep -ne "Müller"`

#c
echo "Müller ohne Doppelnamen:"
echo `cat $1 | grep -ne "Müller "`

#d
echo "Müller groß/klein:"
echo `cat $1 | grep -ie "müller"`

#e
echo `cat $1 | grep -n "Sch"*"r "*`
