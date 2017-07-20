#!/bin/bash

# set priority of this process, arguments: [-20 to 19]
function setProcessPriority {
	renice $1 -p $$
}

# call like this: "checkIsInteger $varToCheck"
# prints 0 for true, 1 for false
function checkIsInteger {
	re='^[0-9]+$'
	if ! [[ $1 =~ $re ]] ; then
		echo 1
	else	
		echo 0
	fi
}

# call like this: "checkIsFloat $varToCheck"
# prints 0 for true, 1 for false
function checkIsFloat {
	re='^-?[0-9]+([.][0-9]+)?$'
	if ! [[ $1 =~ $re ]] ; then
		echo 1
	else	
		echo 0
	fi
}

# call like this: "checkIsPosFloat $varToCheck"
# prints 0 for true, 1 for false
function checkIsPosFloat {
	re='^[0-9]+([.][0-9]+)?$'
	if ! [[ $1 =~ $re ]] ; then
		echo 1
	else	
		echo 0
	fi
}

# call like this: "loadOption filePath optionName delemiterChar"
# prints the loaded option
function loadOption {
	# TODO: check for valid arguments
	# TODO: reg exp: make sure line starts followed by optionname e.g. ^optionname=*$
	local READ=`grep -a -m 1 -h -r "^$2$3.*$" "$1" | head -1`
	local VALUE=`echo $READ | cut -d'=' -f2 `
	echo "$VALUE"
}


echo "Option opt is Integer and has the value: $VALUE"

function loadSettings {
	echo "Settings-file exists"
	
#	VALUE=`loadOption ./settings.txt opt =`
#	RC=`checkIsInteger $VALUE` 
#	if [ "$RC" -ne 0 ]; then
#		echo "Error reading Option \"$OPT\""
#		exit 1
#	fi

	TEMPS=`sed -n '1p' "$CONFIG_FILE"`
	echo "Temperature entries $TEMPS will be used"
	echo "Do you want to change the settings?"

	OPTIONS="Change Continue"
        select opt in $OPTIONS; do
        	if [ "$opt" = "Change" ]; then
			rm "$CONFIG_FILE"
			echo "Please restart the script"
			exit 0
        	elif [ "$opt" = "Continue" ]; then
			break
        	else
                   	echo "Bad Option"
		fi
	done
}

setProcessPriority 19

CONFIG_FILE=".auto_CPU_Freq.conf"

MEASUREMENTS=10
DEFAULTINTERVAL=5
URGENTINTERVAL=1
SLEEPINTERVAL=10
INTERVAL=$ACTIVEINTERVAL
P=-50
I=-15
D=0
TARGET_TEMP=60
MAX_DELTA_WARMER=5
MIN_FREQ=480
MAX_FREQ=2560

if [ $# -ne 1 ]
then
	echo "arguments missing: [targetTemp] (in 40-80°C)"
	echo "using default targetTemp: 60°C"
	TARGET_TEMP=60
else
	TARGET_TEMP=$1
fi


if [ $TARGET_TEMP -gt 80 ]; then TARGET_TEMP=80; fi
if [ $TARGET_TEMP -lt 30 ]; then TARGET_TEMP=30; fi

echo "Target Temperature = $TARGET_TEMP°C"

let MAX_ALLOWED_TEMP=$TARGET_TEMP+$MAX_DELTA_WARMER
let MIN_ALLOWED_TEMP=$TARGET_TEMP-$MAX_DELTA_WARMER



AVG_ERROR=1
OLD_AVG_ERROR=1
CUML_ERROR=1600

FREQ=1600
PREV_FREQ=1600

# initialize CPU-Frequency
bash ./setCPUFreqs.sh $FREQ $FREQ

# check if Config-File exists
# TODO: write all settings to config file and reimport them on next start
if [ -f "$CONFIG_FILE" ]
then
	loadSettings
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

echo "Starting to measure Temparatures..."

while [ 0 ]
do
	OK=0
	CNT=0
	SUM=0
	INDEX=0	
	OVERHEATED_TEMP=0
	
	while [ $INDEX -lt $MEASUREMENTS ]
	do
		sleep $INTERVAL
 		clear
		let INDEX+=1

		READ_TEMPS=`sensors -u | grep temp._input | sed -n "$TEMPS"p | cut -d' ' -f4 | cut -d'.' -f1`

		for T in $READ_TEMPS
		do
			TEMP_DIFFERENCE=`bc <<< "$T - $TARGET_TEMP"`
			echo "Temp $T°C; Difference = $TEMP_DIFFERENCE°C"
			if [ $T -gt $MAX_ALLOWED_TEMP ]
			then
				echo "Temp above $MAX_ALLOWED_TEMP°C!"
				OK=-1
				if [ $T -gt $OVERHEATED_TEMP ]; then
					OVERHEATED_TEMP=$T
				fi
				
			fi
			if [ $T -lt $MIN_ALLOWED_TEMP ]
			then
				echo "Temp below $MIN_ALLOWED_TEMP°C!"
				OVERHEATED_TEMP=$T
				OK=-1
			fi

			let CNT+=1
			let SUM+=$T	
		done
		if [ $OK -eq -1 ]
		then
			AVG=$OVERHEATED_TEMP
			AVG_ERROR=`bc <<< "scale=3;$AVG - $TARGET_TEMP"`
			echo "MAX: $AVG°C; Difference = $AVG_ERROR°C"
			#abort measuring loop due to overheating
			break
		fi
		
		# average temperature from measuring-loop	
		AVG=`bc <<< "scale=3; $SUM / $CNT"`
		# average difference to target temperature
		AVG_ERROR=`bc <<< "scale=3;$AVG - $TARGET_TEMP"`
		echo
		echo "$INDEX/$MEASUREMENTS   CPU-Freq = $FREQ Mhz"
		echo "Average: $AVG°C; Difference = $AVG_ERROR°C"
		
	
	done
		# PID-loop for tempature-control using CPU-Frequency
		OLD_AVG_ERROR=$AVG_ERROR

		# proportional component of PID-loop
		PTerm=`bc <<< "scale=3;$P * $AVG_ERROR"`
		echo "P Result = $PTerm"

		# integral change in this iteration
		CUML_ADD=`bc <<<"scale=3;$I * $AVG_ERROR"`
		
		# integral component of PID-loop
		CUML_ERROR=`bc <<<"scale=3;$CUML_ADD + $CUML_ERROR"`
		
		# value to integer
		CUML_ERROR=`cut -d'.' -f1 <<< "$CUML_ERROR"`
		
		# clamp the value to min-max-CPU-Frequency
		if [ $CUML_ERROR -gt $MAX_FREQ ]
		then
			CUML_ERROR=$MAX_FREQ
		fi
		if [ $CUML_ERROR -lt $MIN_FREQ ]
		then
			CUML_ERROR=$MIN_FREQ
		fi

		echo "I Result = $CUML_ERROR; increased by $CUML_ADD"

		# convert value to integer for further use in bash
		TMP=`cut -d'.' -f1 <<< "$OLD_AVG_ERROR"`
		
		# check for valid value and zero to prevent div-by-0 errors
		if [ "$TMP" != "-" ]; then
			if [ "$TMP" != "" ]; then
				if [ $TMP -ne 0 ]; then
					# calculate derivate component of PID-loop
					DTerm=`bc <<< "scale=3;($AVG_ERROR / $OLD_AVG_ERROR) * $D"`
				else
					DTerm=0
				fi
			else
				DTerm=0
			fi
		else
			DTerm=0
		fi
		
		# derivative component of PID-loop
		echo "D Result = $DTerm"

		# combine components of PID-loop
		RESULT=`bc <<< "$PTerm + $CUML_ERROR + $DTerm"`

		# to integer
		RESULT=`cut -d'.' -f1 <<< "$RESULT"`
		echo "Result of PID: $RESULT"
		
		# check if RESULT is valid value in range of min-/max-CPU-Frequency
		if [ $RESULT -gt $MAX_FREQ ]
		then 
			RESULT=$MAX_FREQ
		elif [ $RESULT -lt $MIN_FREQ ]
		then 
			RESULT=$MIN_FREQ
		fi

		PREV_FREQ=$FREQ
		FREQ=$RESULT

		echo "Old Freq = $PREV_FREQ Mhz"
		echo "New Freq = $FREQ Mhz"

		# check if Frequency has changed from the currently set one
		if [ $FREQ -ne $PREV_FREQ ]
		then
			# set new CPU-Frequency
			#bash ./setCPUFreqs.sh $FREQ $FREQ
			bash ./setCPUFreqs.sh 0 $FREQ
			if [ $? -ne 0 ]; then 
				FREQ=$PREV_FREQ
				echo "ERROR: Frequency was not set!"				
			fi

			if [ $OK -eq -1 ]
			then
				INTERVAL=$URGENTINTERVAL
			else
				INTERVAL=$DEFAULTINTERVAL
			fi
		else 
			echo "Nothing to do... everything OK"
			INTERVAL=$SLEEPINTERVAL
		fi
done 
