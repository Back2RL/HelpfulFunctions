#!/bin/bash
dir="./test/*"
path="./test/"
ext=".jpeg"
newext=".jpg"
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
		newfn="${path}${name}${newext}"
		echo "new name = $newfn"
		cp "${file}" "${newfn}"
	fi
done
