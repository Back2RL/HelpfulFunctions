OBJ= main.o
ARGS= -ansi -pedantic-errors -Wall

exec: main.o
	gcc -o exec $(OBJ) 

main.o: main.c
	gcc -c $(ARGS) main.c

clean:
	rm *.o exec
