GCC_ARGS = -Wall -ansi -pedantic-errors -Wstrict-prototypes
OBJ = testprog.o 
NAME = testprogStatic
LIB = -L./libs -ltest-static

$(NAME): $(OBJ)
	gcc $(GCC_ARGS) -static -g -o $@ $^  $(LIB) 

%.o: %.c
	gcc $(GCC_ARGS) -g -c $^ 

clean:
	rm $(NAME) $(OBJ)
