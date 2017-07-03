#!/bin/bash

if [ "$#" -ne "1" ]
then
echo "Number of arguments does not match: $#"
echo "use \"-m\" to mount or \"-u\" to unmount"
exit
fi

USER=nas
ADDRESS=//192.168.188.123/PiExtern 
DIR=`pwd`"/Pi_Samba_Mount"

if [ ! -d "$DIR" ]
then
	echo "creating directory \"$DIR\""
	mkdir "$DIR"
fi

if [ "$1" == "-m" ]
then
	#sudo mount -o user="$USER" "$ADDRESS" "$DIR"
	sudo mount -t cifs -o user="$USER" "$ADDRESS" "$DIR"
	echo "mounted"
elif [ "$1" == "-u" ]
then
	sudo fusermount -u "$DIR"
	echo "unmounted"
else 
	echo "unknown argument"
	echo "use \"-m\" to mount or \"-u\" to unmount"
fi
