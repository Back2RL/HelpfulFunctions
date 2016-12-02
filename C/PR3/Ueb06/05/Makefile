#Muster Makefile
GCC_ARGS = -Wall -ansi -pedantic-errors -Wstrict-prototypes
OBJ = Vektor_test.o Vektor.o
NAME = Main

$(NAME): $(OBJ)
	gcc $(GCC_ARGS) -o $@ $(OBJ)

%.o: %.c
	gcc $(GCC_ARGS) -c $<


clean:
	rm $(NAME) $(OBJ)
