#!/bin/bash

#add the following line to the file /etc/fstab and replace address and mountpoint and remove the leading # to uncomment it
#address mountpoint davfs user,noauto 0 0

#add the current user to the group davfs2 and perform a logout and login afterwards for the changes to take effect
#sudo usermod -aG davfs2 <benutzername>

#sudo apt-get update
#sudo apt-get install davfs2


if [ "$#" -ne "1" ]
then
echo "Number of arguments does not match: $#"
echo "use \"-m\" to mount or \"-u\" to unmount"
exit
fi

DIR=`pwd`"/MountPoints/davPrivate"

if [ ! -d "$DIR" ]
then
	echo "creating directory \"$DIR\""
	mkdir -v "$DIR"
fi

if [ "$1" == "-m" ]
then
	#sudo mount -t davfs http://localhost:42427/CBJ9At4GSgBi/Private  "$DIR"
	mount "$DIR"
	echo "mounted"
elif [ "$1" == "-u" ]
then
	sudo umount "$DIR"
	echo "unmounted"
else 
	echo "unknown argument"
	echo "use \"-m\" to mount or \"-u\" to unmount"
fi
