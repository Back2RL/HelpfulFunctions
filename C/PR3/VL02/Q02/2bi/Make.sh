#!/bin/bash
rm *.o
gcc -c -ansi -Wall -pedantic-errors a.c
gcc -c -ansi -Wall -pedantic-errors b.c
gcc -c -ansi -Wall -pedantic-errors m.c
gcc -o programm m.o b.o a.o
./programm
