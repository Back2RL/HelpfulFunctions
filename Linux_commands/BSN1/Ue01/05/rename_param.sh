#!/bin/bash
# ändert die Dateiendung aller Dateien die mit .jpeg enden 
# im aktuellen Verzeichnis in .jpg um
# Leo 13.10.2016
####
#
FORCE=0
WARNINGS=0

echo "Number of parameters = $#"

while getopts ":vf" opt; do
  case $opt in
    v)
      	echo "-v Warnings will be displayed"
	WARNINGS=1
      ;;
    f)
      	echo "-f Already existing files will be overwritten!"
	FORCE=1
      ;;
    \?)
      	echo "Invalid option: -$OPTARG"
      ;;
  esac
done

if [ $# -eq 2 ]
then
OLD=$1
NEW=$2
elif [ $# -eq 3 ]
then
OLD=$2
NEW=$3
elif [ $# -eq 4 ]
then
OLD=$3
NEW=$4
else 
	OLD=""
	NEW=""
fi


if [ "$OLD" == "" -o "$NEW" == "" ]
then
	echo "Missing Parameters";
	echo "parameters: [old extension] [new extension]";
	exit;
fi
echo "Replacing all extensions \"$OLD\" with \"$NEW\":"

for file in *."$OLD"
do
	basename=`basename $file ".$OLD"`
	result=$basename.$NEW

	if [ ! -f "$result" -o $FORCE -eq 1 ]
	then 
		cp $file $result
		echo "renamed \"$file\" -> \"$result\""
	elif [ $WARNINGS -eq 1 ]
	then		
		echo "\"$result\" already exists"
	fi
done
echo Fertig!
