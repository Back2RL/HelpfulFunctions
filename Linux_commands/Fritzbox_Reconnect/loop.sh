#!/bin/bash

if [ $# -ne 1 ]
then
	echo "argument >seconds< is missing"
	exit
fi

interval=$1

if [ $interval -lt 30 ]
then
	echo setting interval to 30 seconds
	interval=30
fi

while [ 1 -ne 0 ]
do
	echo reconnecting...
	./reconnect.sh
	echo waiting $interval seconds...
	sleep $interval
done
