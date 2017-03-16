GCC_ARGS = -Wall -ansi -pedantic-errors -Wstrict-prototypes
OBJ = testprog.o 
NAME = testprogDynamic
LIB = -L./libs -ltest-dynamic

$(NAME): $(OBJ)
	gcc $(GCC_ARGS) -dynamic -g -o $@ $^  $(LIB) 

%.o: %.c
	gcc $(GCC_ARGS) -g -c $^ 

clean:
	rm $(NAME) $(OBJ)
