exec: main.o
	gcc -o exec main.o
%.o: %.c
	gcc -ansi -Wall -pedantic-errors -c $<
clean:
	rm exec *.o
