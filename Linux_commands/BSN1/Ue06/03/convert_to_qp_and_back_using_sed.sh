#!/bin/bash
cat quotedPrintable.sed
sed -f quotedPrintable.sed quotedPrintable.sed > quotedP.txt
cat quotedP.txt

#and back
sed -f umlaute.sed quotedP.txt 
