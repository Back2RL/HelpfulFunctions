#!/bin/bash
if [ "$#" -ne "1" ]
then
echo "Number of arguments does not match: $#"
echo "use \"-m\" to mount or \"-u\" to unmount"
exit
fi

path="/home/$USER/googleDrive"

if [ ! -d "$path" ]
then
	echo "creating directory \"$path\""
	mkdir "$path"
fi

if [ "$1" == "-m" ]
then
	google-drive-ocamlfuse "$path"
	echo "mounted"
elif [ "$1" == "-u" ]
then
	fusermount -u "$path"
	echo "unmounted"
else 
	echo "unknown argument"
	echo "use \"-m\" to mount or \"-u\" to unmount"
fi
