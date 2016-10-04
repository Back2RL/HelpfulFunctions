#!/bin/bash
dir="./test/*"
path="./test/"
ext=".jpeg"
for file in ${dir}
do
	echo "$file"
	basefilename=$(basename $file)
	echo "$basefilename"
	name=$(basename $file $ext)
	echo "$name"
	if [ "${basefilename}" != "${name}" ]
	then
		echo "renaming extention:"
		newfn="${path}${name}.jpg"
		echo "new name = $newfn"
		cp "${file}" "${newfn}"
	fi
done
