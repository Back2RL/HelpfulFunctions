#!/bin/bash
#sudo cpufreq-set -g userspace

if [ $# -ne 2 ]
then
	echo "arguments missing: [min freq] [max freq] (in Mhz)"
	exit
fi

# read possible frequencies from cpu
possibleMin=`cpufreq-info -l | sed 's/ \(.*\)//'`
possibleMax=`cpufreq-info -l | sed 's/\(.*\) //'`

echo "min possible Freq = $possibleMin Khz"
echo "max possible Freq = $possibleMax Khz"

# check if min is valid value

if [ "$possibleMin" -ge "${1}000" ]
then
	if [ "$possibleMax" -lt "${1}000" ]
	then 
		min=$possibleMax
	else
		min=$possibleMin
	fi
else
	if [ "$possibleMax" -lt "${1}000" ]
	then 
		min=$possibleMax
	else
		min="${1}000"
	fi
fi

# check if max is valid value

if [ "$possibleMax" -le "${2}000" ]
then
	if [ "$possibleMin" -gt "${2}000" ]
	then
		max=$possibleMin
	else
		max=$possibleMax
	fi	
else
	if [ "$possibleMin" -gt "${2}000" ]
	then
		max=$possibleMin
	else
		max="${2}000"
	fi	
fi

if [ "$min" -gt "$max" ]
then
	tmp=$max
	max=$min
	min=$tmp
fi

echo "To set min = $min Khz"
echo "To set max = $max Khz"

#TODO: make corecount dynamic
for CORE in 0 1 2 3
do
	#sudo cpufreq-set --cpu "$CORE" -g powersave && echo "set governor for core $CORE"
	sudo cpufreq-set --cpu "$CORE" --min "$min"Khz # && echo "set min freq for core $CORE"
	sudo cpufreq-set --cpu "$CORE" --max "$max"Khz # && echo "set max freq for core $CORE"

done

#sudo cpufreq-set -f 480Mhz

#sudo cpufreq-info 
#sudo cpufreq-info -l 
sudo cpufreq-info -p
