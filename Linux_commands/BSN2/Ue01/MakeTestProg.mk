#Muster Makefile
GCC_ARGS = -Wall -ansi -pedantic-errors -Wstrict-prototypes
OBJ = testprog.o 
NAME = testprog
LIB = -L./ -ltest-static

$(NAME): $(OBJ)
	gcc $(GCC_ARGS) -g -o $@ $(OBJ)  $(LIB) 


%.o: %.c
	gcc $(GCC_ARGS) -g -c $< 

clean:
	rm $(NAME) $(OBJ)
