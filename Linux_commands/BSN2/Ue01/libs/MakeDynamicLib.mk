#Muster Makefile
GCC_ARGS = -Wall -ansi -pedantic-errors -Wstrict-prototypes
OBJ = file1.o file2.o 
LIB = libtest-dynamic


$(LIB): $(OBJ)
	gcc -shared -o $(LIB).so $^

%.o: %.c %.h
	gcc $(GCC_ARGS) -c -g -fpic $^

clean:
	rm $(OBJ) $(LIB).so
