CFLAGS = -Wall -Wdeclaration-after-statement
CXXFLAGS = -Wall
LDLIBS = -lm

termine: termine.cpp Zeit.o Termin.o
zeiten: zeiten.cpp Zeit.o Zeit.h
Zeit.o: Zeit.cpp Zeit.h
Termin.o: Termin.cpp Termin.h Zeit.h

clean:
	rm -f *.o zeiten termine

