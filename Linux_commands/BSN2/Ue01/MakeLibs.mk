#Muster Makefile
GCC_ARGS = -Wall -ansi -pedantic-errors -Wstrict-prototypes
OBJ = file1.o file2.o 
LIB = libtest-static


$(LIB): $(OBJ)
	ar rcs $(LIB).a file1.o file2.o

%.o: %.c
	gcc $(GCC_ARGS) -c -g  $<

clean:
	rm $(OBJ)
