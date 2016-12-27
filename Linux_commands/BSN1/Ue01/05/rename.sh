#!/bin/bash
# ändert die Dateiendung aller Dateien die mit .jpeg enden 
# im aktuellen Verzeichnis in .jpg um
# Leo 12.10.2016
####
#

dir="./*.jpeg"
path="./"
ext=".jpeg"
newext=".jpg"
#JPEG-Dateien durchlaufen
for file in ${dir}
do
	echo "$file"
	basefilename=$(basename $file)
	echo "$basefilename"
	name=$(basename $file $ext)
	echo "$name"
	if [ "${basefilename}" != "${name}" ]
	then
		echo "renaming ext:"
		newfn="${path}${name}${newext}"
		echo "new name = $newfn"
		cp "${file}" "${newfn}"
	fi
done
