Test: main.o
	gcc -Wall -ansi -pedantic-errors -Wstrict-prototypes -o Test main.o

main.o:	main.c
	gcc -Wall -ansi -pedantic-errors -Wstrict-prototypes -c main.o

clean:
	rm Test *.o
