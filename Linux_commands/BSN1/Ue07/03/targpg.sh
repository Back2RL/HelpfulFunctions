#!/bin/bash
if [ $# -ne 1 ]
then
	echo "Argument [directory] is missing]"
	exit
fi

if [ ! -d "$1" ]
then 
	echo "\"$1\" is not a directory"
	exit
fi

echo "creating tar-file and encrypting with password:"
tar -c "$1" | gpg -c > "$1.tar.gpg"
ls -ltr
