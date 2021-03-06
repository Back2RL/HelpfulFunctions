#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <signal.h>
#include <ctype.h>
#include <errno.h> /* immer am Ende */

#define MAXLEN 80

double summe;
int n;

void abfangen(int signal);

int main(int argc, char *argv[])
{
	int pd[2][2];
	int pid;
	int rc;
	int rc2;
	int i;
	int leer;
	char buffer[MAXLEN];
	int status;

	/* Was soll passieren wenn SIGINT eintrifft? */
	if (signal(SIGINT, SIG_IGN) == SIG_ERR) {
		perror("SIGINT");
		exit(10);
	}
	if (signal(SIGQUIT, SIG_IGN) == SIG_ERR) {
		perror("SIGQUIT");
		exit(11);
	}
	if (signal(SIGPIPE, SIG_IGN) == SIG_ERR) {
		perror("SIGPIPE");
		exit(12);
	}
	if (signal(SIGCHLD, abfangen) == SIG_ERR) {
		perror("SIGCHLD");
		exit(12);
	}

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

	if( pid == 0 ) { /*ist jetzt Kindprozess */
		/* Signale wieder auf standard stellen */
		if(signal(SIGINT, SIG_DFL) == SIG_ERR) {
			perror("SIGINT");
			exit(1);
		}
		if(signal(SIGQUIT, SIG_DFL) == SIG_ERR) {
			perror("SIGQUIT");
			exit(1);
		}
		if(signal(SIGPIPE, SIG_DFL) == SIG_ERR) {
			perror("SIGPIPE");
			exit(1);
		}
		if (signal(SIGCHLD, SIG_DFL) == SIG_ERR) {
			perror("SIGCHLD");
			exit(12);
		}
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
		/* Im Fall eines Fehlers beim Erstellen */
		perror("bc");
		exit(1); 


	}

	/*Vaterprozess*/
	/*ungenutzten Deskriptor schließen*/	
	close(pd[0][0]);	/*lesend.*/	
	close(pd[1][1]);	/*schreibd.*/	

	write(1, "Eingabe: ", 9);
	while( rc = read(0, buffer, MAXLEN), rc > 0) {

		/* prüfen ob min. ein Zeichen angegeben wurde */
		leer = 1;
		for (i = 0; i < rc; i++ ) {
			if(!isspace(buffer[i])) {
				leer = 0;
				break;
			}
		}
		if(leer) {
			write(1, "Eingabe: ", 9);
			continue;
		}

		rc2 = write(pd[0][1], buffer, (unsigned int) rc);	
		if (rc2 < 0) {
			if(errno != EPIPE) 
				perror("write");
			/* Pipe ist geschlossen.*/
			break;
		}

		rc = read(pd[1][0], buffer, MAXLEN);
		write(1, buffer, (unsigned int) rc);	/* 1 standardausgabe*/
		write(1, "Eingabe: ", 9);
	}
	if (rc < 0) {
		perror("read");
	}
	/*schließt pipe zu bc.*/	
	close(pd[0][1]);

	/*wartet bis alle Kindprozesse beendet sind */
	pid = wait(&status);
	if (pid < 0 && errno != ECHILD ) {
		perror("wait");
		exit(1);
	}

	if(WIFEXITED(status)) {
		printf("Prozess %d normal beendet mit Exitcode %d\n", pid, WEXITSTATUS(status));
	}
	else {
		printf("Prozess %d wurde durch das Signal %d beendet\n", pid, WTERMSIG(status));
	}
	printf("ENDE\n");

	return rc < 0 ? 1 : 0;
}

void abfangen(int signal)
{
	int pid;
	int status;

	printf("\n");
	pid = wait(&status);
	if (pid < 0 && errno != ECHILD ) {
		perror("wait");
		exit(1);
	}

	if(WIFEXITED(status)) {
		printf("Prozess %d normal beendet mit Exitcode %d\n", pid, WEXITSTATUS(status));
	}
	else {
		printf("Prozess %d wurde durch das Signal %d beendet\n", pid, WTERMSIG(status));
	}
	printf("ENDE\n");
	exit(2);
}
