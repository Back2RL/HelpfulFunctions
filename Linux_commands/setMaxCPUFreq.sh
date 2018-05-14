#!/bin/bash

if [ $# -ne 1 ]
then
	echo "arguments missing: [max freq] (in Mhz)"
	exit
fi

sudo cpufreq-set -r -u "$1"Mhz
