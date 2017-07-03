#!/bin/bash
if [ "$#" -ne "1" ]
then
echo "Number of arguments does not match: $#"
echo "use \"-m\" to mount or \"-u\" to unmount"
exit
fi

DIR="/home/$USER/googleDrive"

if [ ! -d "$DIR" ]
then
	echo "creating directory \"$DIR\""
	mkdir "$DIR"
fi

if [ "$1" == "-m" ]
then
	google-drive-ocamlfuse "$DIR"
	echo "mounted"
elif [ "$1" == "-u" ]
then
	fusermount -u "$DIR"
	echo "unmounted"
else 
	echo "unknown argument"
	echo "use \"-m\" to mount or \"-u\" to unmount"
fi
