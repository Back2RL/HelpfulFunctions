#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#define MAXLEN 80
#define THREADS 1

void kindService(int in);

int main(int argc, char *argv[])
{

	int pd[2];
	int pid;
	int rc;
	int n;
	char buffer[MAXLEN];

	/*erzeugen einer Pipe */
	if( pipe(pd) < 0) {
		perror("pipe");
		exit(1);
	}

	for(n = 0; n<THREADS; ++n){
		pid = fork();
		if ( pid < 0 ) {
			perror("\nfork");
			exit(1);
		}

		if( pid == 0 ) { /*Kindprozess */
			/*ungenutzten Deskriptor schließen*/	
			close(pd[1]);	/*schreibd.*/	
			printf("Kindprozess PID = %5d\n" , pid);
			kindService(pd[0]);

			exit(0);
		}
	}

	/*Vaterprozess*/
	/*ungenutzten Deskriptor schließen*/	
	close(pd[0]);	/*lesed.*/

	printf("Vaterprozess PID = %d\n",pid);

	write(1, "Eingabe: ", 9);
	while( rc = read(0, buffer, MAXLEN), rc > 0) {
		write(pd[1], buffer, (unsigned int) rc);	/* 1 standardausgabe*/
		write(1, "Eingabe: ", 9);
	}
	if (rc <= 0) {
		perror("read");
		exit(1);
	}

	return 0;
}

void kindService(int in)
{
	char buffer[MAXLEN];
	int rc;

	while( rc = read(in, buffer, MAXLEN), rc > 0) {
		write(1, buffer, (unsigned int) rc);	/* 1 standardausgabe*/
	}
	if (rc <= 0) {
		perror("read");
		exit(1);
	}

}
