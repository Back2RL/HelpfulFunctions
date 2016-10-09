GCC_ARGS = -Wall -ansi -pedantic-errors -Wstrict-prototypes
OBJ = math.o input.o summe.o differenz.o
NAME = math

$(NAME): $(OBJ)
	gcc $(GCC_ARGS) -o $@ $(OBJ)

%.o: %.c
	gcc $(GCC_ARGS) -c $<

#main.o: math.h

clean:
	rm $(NAME) $(OBJ)
