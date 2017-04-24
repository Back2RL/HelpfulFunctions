#!/bin/bash

if [ $# -ne 1 ]
then
	echo "arguments missing: [targetTemp] (in 40-80°C)"
	exit
fi

CONFIG_FILE="auto_CPU_Freq.conf"

TARGET_TEMP=$1

if [ $TARGET_TEMP -gt 80 ]; then TARGET_TEMP=80; fi
if [ $TARGET_TEMP -lt 40 ]; then TARGET_TEMP=40; fi

echo "Target Temperature = $TARGET_TEMP°C"

MAX_ALLOWED_TEMP=72

MEASUREMENTS=10
INTERVAL=3

P=-50
I=-25
D=-100

AVG_ERROR=1
OLD_AVG_ERROR=1
CUML_ERROR=1600

if [ -f "$CONFIG_FILE" ]
then
	echo "Settings-file exists"
	TEMPS=`sed -n '1p' "$CONFIG_FILE"`
	echo "Temperature entries $TEMPS will be used"
	echo "Do you want to change this (y/n)?"
	read CHANGE
	if [ "$CHANGE" == "y" ]
	then
		rm "$CONFIG_FILE"
		echo "Please restart the script"
		exit 0
	fi
else
	echo "Settings-file does not exist"
	echo "You will be shown a list of sensors now:"
	sensors
	echo "Please enter the lines you want to use for measuring"
	echo " (e.g. 4,7 to use the temperatures no. 4 to 7)"
	echo " (e.g. 5   to use only temperature no. 5)"
	read TEMPS	
#TODO check if input was valid

	echo "Temperature entries $TEMPS will be used"
	echo $TEMPS > "$CONFIG_FILE"
fi

while [ 0 ]
do
	clear

	READ_TEMPS=`sensors -u | grep temp._input | sed -n "$TEMPS"p | cut -d' ' -f4 | cut -d'.' -f1`
	
	OK=0
	CNT=0
	SUM=0
	I=0	
	
	while [ $I -lt $MEASUREMENTS ]
	do
 		clear
		let I+=1
		echo "$I/$MEASUREMENTS"

		for T in $READ_TEMPS
		do
			TEMP_DIFFERENCE=`bc <<< "$T - $TARGET_TEMP"`
			echo "Temp $T°C; Difference = $TEMP_DIFFERENCE°C"
			if [ $T -le $MAX_ALLOWED_TEMP ]
			then
				echo "OK"
			else
				echo "Temp above $MAX_ALLOWED_TEMP°C!"
				OK=-1
			fi
			let CNT+=1
			let SUM+=$T	
		done
		
		sleep $INTERVAL
	done
	

	if [ $OK -eq -1 ]
	then
		bash ./setCPUFreqs.sh 0 0
	else
		clear
		OLD_AVG_ERROR=$AVG_ERROR
		AVG=`bc <<< "scale=3; $SUM / $CNT"`
		AVG_ERROR=`bc <<< "scale=3;$AVG - $TARGET_TEMP"`
		echo "Average: $AVG°C; Difference = $AVG_ERROR°C"

		PTerm=`bc <<< "scale=3;$P * $AVG_ERROR"`
		echo "P Result = $PTerm"

		CUML_ADD=`bc <<<"scale=3;$I * $AVG_ERROR"`
		CUML_ERROR=`bc <<<"scale=3;$CUML_ADD + $CUML_ERROR"`
		echo "I Result = $CUML_ERROR; increased by $CUML_ADD"

		TMP=`cut -d'.' -f1 <<< "$OLD_AVG_ERROR"`
		if [ $TMP != 0 ]
		then
			DTerm=`bc <<< "scale=3;($AVG_ERROR / $OLD_AVG_ERROR) * $D"`
		else
			DTerm=0
		fi
		
		echo "D Result = $DTerm"

		RESULT=`bc <<< "$PTerm + $CUML_ERROR + $DTerm"`
		RESULT=`cut -d'.' -f1 <<< "$RESULT"`
		echo "Result of PID: $RESULT"

		bash ./setCPUFreqs.sh 0 $RESULT
	
		CUML_ERROR=`cut -d'.' -f1 <<< "$CUML_ERROR"`
		if [ $CUML_ERROR -gt 2560 ]
		then
			CUML_ERROR=2560
		fi
		if [ $CUML_ERROR -lt 1 ]
		then
			CUML_ERROR=1
		fi

		sleep 5
	fi
done 
