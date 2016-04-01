CFLAGS = -Wall -Wdeclaration-after-statement -Wconversion -I  ../include -fPIC
LDLIBS = -lsnp

libsnp.so: readline.o
	$(CC) -shared -o $@ $(filter %.o,$^)

readline.o: readline.c ../include/snp.h

#hinter clean MUSS jede Zeile immer mit einem Tabulator beginnen
clean:
	rm -f *.o *.so

