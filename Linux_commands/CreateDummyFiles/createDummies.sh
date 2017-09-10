#!/bin/bash

for file in ./originals/*
do	
	filename=`echo "$file" | cut -d'/' -f3`
	echo "creating dummy: ./dummies/${filename}"
	touch "./dummies/${filename}"
done
