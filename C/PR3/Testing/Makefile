GCC_ARGS = -Wall -ansi -pedantic-errors -Wstrict-prototypes
OBJ = main.o math.o
NAME = Test

$(NAME): $(OBJ)
	gcc $(GCC_ARGS) -o $@ $(OBJ)

%.o: %.c
	gcc $(GCC_ARGS) -c $<

#main.o: math.h

clean:
	rm $(NAME) $(OBJ)
