#!/bin/bash
for file in *;
	do  unzip "$file" -d `basename "$file" ".zip"`;
done
