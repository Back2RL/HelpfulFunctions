#!/bin/bash
gcc -c -Wall -ansi -pedantic-errors -Wstrict-prototypes quadrat.c quadrat.h
gcc -c -Wall -ansi -pedantic-errors -Wstrict-prototypes main.c quadrat.h
gcc -o main_exec main.o quadrat.o
