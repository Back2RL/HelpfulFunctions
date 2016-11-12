exec: main.o
	gcc -o exec main.o
main.o: main.c
	gcc -ansi -pedantic-errors -Wall -c main.c
	
clean:
	rm *.o exec
