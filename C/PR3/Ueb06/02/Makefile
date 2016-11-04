#Muster Makefile
#GCC_ARGS = -Wall -ansi -pedantic-errors -Wstrict-prototypes
GCC_ARGS =  -ansi -pedantic-errors -Wstrict-prototypes
OBJ = implicit.o 
NAME = Main

$(NAME): $(OBJ)
	gcc $(GCC_ARGS) -o $@ $(OBJ)

%.o: %.c
	gcc $(GCC_ARGS) -c $<


clean:
	rm $(NAME) $(OBJ)
