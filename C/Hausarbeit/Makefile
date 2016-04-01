CFLAGS = -Wall -Wdeclaration-after-statement -g -I include
#CFLAGS = -Wall -Wdeclaration-after-statement -Wconversion -g -I include
LDLIBS = -lm #-lsnp
LDFLAGS = -L lib

Aclient: Aclient.c include/snp.h include/readline.o
readline.o: include/readline.c include/snp.h
clean:
	rm -f *.o include/*.o Aclient
