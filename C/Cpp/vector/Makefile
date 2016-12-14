CFLAGS = -Wall -Wdeclaration-after-statement -Ddebug -pedantic-errors
CXXFLAGS = -Wall -std=c++11 -pedantic-errors #-Ddebug 
LDLIBS = -lm
OBJ= Vektor.o CPUtime.o vektoren.o

vektoren: $(OBJ) 
	g++ -o $@ $(OBJ)
%.o: %.cpp
	g++ $(CXXFLAGS) -c $< 

clean:
	rm *.o
