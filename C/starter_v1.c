#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>

int main(int argc, char *argv[])
{

	
	
	
	
	
	
	printf("meine PID: %d\n", getpid());
	printf("meine PPID: %d\n", getppid());

	
	printf("Ich starte jetzt ./collatz\n");
	execlp("./collatz","./collatz",NULL);


	perror(argv[0]);




	return 1;  /* wir haben einen Fehler */
}
