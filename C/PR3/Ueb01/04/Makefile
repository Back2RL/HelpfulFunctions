GCC_ARGS = -Wall -ansi -pedantic-errors -Wstrict-prototypes
OBJ = main.o quadrat.o
NAME = main_exec

$(NAME): $(OBJ)
	gcc $(GCC_ARGS) -o $@ $(OBJ)

%.o: %.c
	gcc $(GCC_ARGS) -c $<

#main.o: math.h

clean:
	rm $(NAME) $(OBJ)
