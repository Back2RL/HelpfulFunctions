#!/bin/bash


if [ `uname -m` = "x86_64" ] # äquivalent mit if test $# -lt 1
 then
	echo "64-Bit System"
	export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:./lib64
else 
	echo "32-Bit System"
	export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:./lib
fi
#export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/usr/local/lib
exit 0
