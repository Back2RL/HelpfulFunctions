#!/bin/bash
gcc -E world.c -o world.E
#gcc -S world.E -x cpp-output world.E
 gcc -c world.E -x cpp-output world.E
#gcc -o world world.s
 gcc -o world world.o
