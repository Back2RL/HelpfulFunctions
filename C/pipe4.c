#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <signal.h>
#include <ctype.h>
#include "readline.c"
#include <errno.h> /* immer am Ende */

#include "snp.h"

#define MAXLEN 80

int n;


void abfangen(int signal);

int pids[3];

int main(int argc, char *argv[])
{
	int pd[4][2];	/* 0 - Worte / 1 - sort / 2 - uniq / 3 pipe || 0 - lesen / 1 - schreiben */
	int pid;
	int rc;
	int rc2;
	int i;
	int leer;
	char buffer[MAXLEN];
	int status;
	int worteGesamt, worte;


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
	/*
	   if (signal(SIGCHLD, SIG_IGN) == SIG_ERR) {
	   perror("SIGCHLD");
	   exit(12);
	   }
	   */
	if (signal(SIGCHLD, abfangen) == SIG_ERR) {
		perror("SIGCHLD");
		exit(12);
	}


	if( pipe(pd[0]) < 0) {
		perror("pipe");
		exit(1);
	}
	if( pipe(pd[1]) < 0) {
		perror("pipe");
		exit(1);
	}
	if( pipe(pd[2]) < 0) {
		perror("pipe");
		exit(1);
	}
	if( pipe(pd[3]) < 0) {
		perror("pipe");
		exit(1);
	}
	/* der erste fork für ./worte */
	pids[0] = pid = fork();
	if ( pid < 0 ) {
		perror("fork");
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

		close(pd[2][1]);	/*schreibd.*/	
		close(pd[2][0]);	/*lesend.*/	

		close(pd[3][1]);	/*schreibd.*/	
		close(pd[3][0]);	/*lesend.*/	

		/*umlegen der pipe auf stdin(0)/stdout(1)*/
		close(0);
		dup(pd[0][0]);
		close(pd[0][0]);

		close(1);
		dup(pd[1][1]);
		close(pd[1][1]);

		execlp("./worte", "./worte", NULL);
		/* Im Fall eines Fehlers beim Erstellen */
		perror("./worte");
		exit(1); 


	}

	/* der zweite fork für sort */
	pids[1] = pid = fork();
	if ( pid < 0 ) {
		perror("fork");
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
		close(pd[0][0]);	/*lesend.*/	

		close(pd[1][1]);	/*schreibd.*/	
		close(pd[2][0]);	/*lesend.*/	

		close(pd[3][1]);	/*schreibd.*/	
		close(pd[3][0]);	/*lesend.*/	


		/*umlegen der pipe auf stdin(0)/stdout(1)*/
		close(0);
		dup(pd[1][0]);
		close(pd[1][0]);

		close(1);
		dup(pd[2][1]);
		close(pd[2][1]);

		execlp("sort", "sort", NULL);
		/* Im Fall eines Fehlers beim Erstellen */
		perror("sort");
		exit(1); 


	}

	/* der dritte fork für uniq */
	pids[2] = pid = fork();
	if ( pid < 0 ) {
		perror("fork");
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
		close(pd[0][0]);	/*lesend.*/	

		close(pd[1][1]);	/*schreibd.*/	
		close(pd[1][0]);	/*lesend.*/	

		close(pd[2][1]);	/*schreibd.*/	
		close(pd[3][0]);	/*lesend.*/	

		/*umlegen der pipe auf stdin(0)/stdout(1)*/
		close(0);
		dup(pd[2][0]);
		close(pd[2][0]);

		close(1);
		dup(pd[3][1]);
		close(pd[3][1]);

		execlp("uniq", "uniq","-c", NULL);
		/* Im Fall eines Fehlers beim Erstellen */
		perror("uniq");
		exit(1); 
	}

	/*Vaterprozess*/
	/*ungenutzten Deskriptor schließen*/	
	close(pd[0][0]);	/*lesend.*/	

	close(pd[1][0]);	/*lesend.*/	
	close(pd[1][1]);	/*schreibd.*/	

	close(pd[2][0]);	/*lesend.*/	
	close(pd[2][1]);	/*schreibd.*/	

	close(pd[3][1]);	/*schreibd.*/	


	write(1, "Eingabe: ", 9);
	//write(1, "Eingabe: ", 9);
	while( rc = read(0, buffer, MAXLEN), rc > 0) {

		write(1, "Eingabe: ", 9);
		/* prüfen ob min. ein Zeichen angegeben wurde */
		leer = 1;
		for (i = 0; i < rc; i++ ) {
			if(!isspace(buffer[i])) {
				leer = 0;
				break;
			}
		}
		if(leer) {
			continue;
		}

		rc2 = write(pd[0][1], buffer, (unsigned int) rc);	
		if (rc2 < 0) {
			if(errno != EPIPE) 
				perror("write");
			/* Pipe ist geschlossen.*/
			break;
		}

	}
	if (rc < 0) {
		perror("read");
	}

	/*schließt pipe zu worte*/	
	close(pd[0][1]);

	write(1, "\n\nErgebnis:\n", 12);
	n = 0;	
	worte = worteGesamt = 0;

	while( rc = readline(pd[3][0], buffer, MAXLEN-1), rc > 0) {

		buffer[rc] = '\0';
		printf("%s", buffer);
		worte++;
		rc = sscanf( buffer, "%d" , &n);
		if (rc == 1) {
			worteGesamt += n;
		}
	
	}

	printf("\n%d Woerter, %d unterschiedliche\n", worteGesamt, worte);

	/*schließt pipe zu uniq*/	
	close(pd[3][0]);

	while(1) {
		/*wartet bis alle Kindprozesse beendet sind */
		pid = wait(&status);
		if (pid < 0 && errno != ECHILD ) {
			perror("wait");
			exit(1);
		}

		if(pid < 0)	/* keine Kinder mehr */ 
			break;

		if(WIFEXITED(status) && WEXITSTATUS(status) != 0) {
			printf("Prozess %d normal beendet mit Exitcode %d\n", pid, WEXITSTATUS(status));
		}
		else if (WIFSIGNALED(status)) { 
			printf("Prozess %d wurde durch das Signal %d beendet\n", pid, WTERMSIG(status));
		}
	}

	printf("ENDE\n");
	return rc < 0 ? 1 : 0;
}

void abfangen(int signal)
{
	int pid;
	int status;

/*	printf("\n");*/
	pid = wait(&status);
	if (pid < 0 && errno != ECHILD ) {
		perror("wait");
		exit(1);
	}

	if(WIFEXITED(status) && WEXITSTATUS(status) != 0) {
		printf("Prozess %d normal beendet mit Exitcode %d\n", pid, WEXITSTATUS(status));
	}

	else if (WIFSIGNALED(status)) { 
		printf("Prozess %d wurde durch das Signal %d beendet\n", pid, WTERMSIG(status));
	}


	if (pid == pids[2]) { /* uniq -c */
		pids[2] = 0;
		if (pids[1] != 0) {
			kill(pids[1], SIGTERM);
			if (pids[0] != 0) {
				kill(pids[0], SIGTERM);
			}
			kill(getpid(), SIGTERM);
		}
	}
	else if (pid == pids[1]) { /* sort */
		pids[1] = 0;
		if (pids[0] != 0) {
			kill(pids[0], SIGTERM);
			kill(getpid(), SIGTERM);
		}
	}
	else if (pid == pids[0]) { /* worte */
		pids[0] = 0;
	}

}
