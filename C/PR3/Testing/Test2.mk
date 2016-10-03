GCC_ARGS = -Wall -ansi -pedantic-errors -Wstrict-prototypes
OBJ = main.o math.o
NAME = Test

$(NAME): $(OBJ)
	gcc $(GCC_ARGS) -o $(NAME) $(OBJ)

main.o: main.c math.o
	gcc $(GCC_ARGS) -c main.c

math.o: math.c
	gcc $(GCC_ARGS) -c math.c

clean:
	rm $(NAME) *.o
