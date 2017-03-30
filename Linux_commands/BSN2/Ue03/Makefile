#Muster Makefile

#$<	die erste Abhängigkeit
#$@	Name des Targets
#$+	eine Liste aller Abhängigkeiten
#$^	eine Liste aller Abhängigkeiten, wobei allerdings doppelt vorkommende Abhängigkeiten eliminiert wurden.

GCC_ARGS = -Wall -ansi -pedantic-errors -Wstrict-prototypes 
OBJ = main.o 
NAME = Main

$(NAME): $(OBJ)
	gcc $(GCC_ARGS) -o $@ $(OBJ)

%.o: %.c %.h
	gcc $(GCC_ARGS) -c $^

clean:
	rm $(NAME) $(OBJ) *.gch
