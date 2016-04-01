#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#define MAXLEN 80


int main(int argc, char *argv[])
{

	int pd[2][2];
	int pid;
	int rc;
	char buffer[MAXLEN];

	/*erzeugen einer Pipe */
	if( pipe(pd[0]) < 0) {
		perror("pipe");
		exit(1);
	}
	if( pipe(pd[1]) < 0) {
		perror("pipe");
		exit(1);
	}


	pid = fork();
	if ( pid < 0 ) {
		perror("\nfork");
		exit(1);
	}

	if( pid == 0 ) { /*Kindprozess */
		/*ungenutzten Deskriptor schließen*/	
		close(pd[0][1]);	/*schreibd.*/	
		close(pd[1][0]);	/*lesend.*/	


		/*umlegen der pipe auf stdin(0)/stdout(1)*/
		close(0);
		dup(pd[0][0]);
		close(pd[0][0]);

		close(1);
		dup(pd[1][1]);
		close(pd[1][1]);

		execlp("bc", "bc", "-q", "-l", NULL);
		perror("bc");
		exit(1);

	
	}

	/*Vaterprozess*/
	/*ungenutzten Deskriptor schließen*/	
	close(pd[0][0]);	/*lesend.*/	
	close(pd[1][1]);	/*schreibd.*/	

	write(1, "Eingabe: ", 9);

	while( rc = read(0, buffer, MAXLEN), rc > 0) {
		write(pd[0][1], buffer, (unsigned int) rc);	
		rc = read(pd[1][0], buffer, MAXLEN);
		write(1, buffer, (unsigned int) rc);	/* 1 standardausgabe*/
		write(1, "Eingabe: ", 9);
	}
	if (rc <= 0) {
		perror("read");
		exit(1);
	}

	return 0;
}

