#Muster Makefile
GCC_ARGS = -Wall -ansi -pedantic-errors -Wstrict-prototypes
OBJ = file1.o file2.o 
LIB = libtest-static


$(LIB): $(OBJ)
	ar rcs $(LIB).a $^

%.o: %.c %.h
	gcc $(GCC_ARGS) -c -g  $^

clean:
	rm $(OBJ) $(LIB).a
