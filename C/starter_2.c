#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <signal.h>
#include <errno.h> /* immer am Ende */


void abfangen(int signal);

int main(int argc, char *argv[])
{
	int pid;
	int status;

	if(argc < 2) {
		fprintf(stderr, "Zu wenig Argumente!\n");
		exit(1);
	}

	/* Was soll passieren wenn SIGINT eintrifft? */
	if (signal(SIGINT, abfangen) == SIG_ERR) {
		perror("SIGINT");
		exit(1);
	}

	printf("meine PID: %d\n", getpid());
	printf("meine PPID: %d\n", getppid());
	printf("Ich starte jetzt %s ...",argv[1]);

	pid = fork();
	if ( pid < 0 ) {
		perror("\nfork");
		exit(1);
	}




	if ( pid == 0) { /* Kindprozess */
		/* Signale wieder auf standard stellen */
		if(signal(SIGINT, SIG_DFL) == SIG_ERR) {
			perror("SIGINT");
			exit(1);
		}
		execlp(argv[1],argv[1],NULL);
		/* Should never happen! */
		perror(argv[1]);
		exit(1);
	}




	/* Vaterprozess */
	printf("\n");
	pid = wait(&status);	/*wartet bis alle Kindprozesse beendet sind */
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

	return 0;
}


void abfangen(int sig)
{
	printf("Signal %d erhalten\n", sig);
	return;
}
