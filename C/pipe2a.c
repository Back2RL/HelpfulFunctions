#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>
#include <signal.h>
#include <errno.h>

#define MAXLEN	80

void abfangen(int sig);

int main(int argc, char *argv[])
{
	int pd[2][2];
	int pid;
	char buffer[MAXLEN];
	int rc, rc2;
	int status;
	int i, leer;

	/* Was soll passieren, wenn SIGINT eintrifft! */
	if (signal(SIGINT, SIG_IGN) == SIG_ERR) {
		perror("SIGINT");
		exit(1);
	}
	if (signal(SIGQUIT, SIG_IGN) == SIG_ERR) {
		perror("SIGQUIT");
		exit(1);
	}
	if (signal(SIGPIPE, SIG_IGN) == SIG_ERR) {
		perror("SIGPIPE");
		exit(1);
	}
	if (signal(SIGCHLD, abfangen) == SIG_ERR) {
		perror("SIGCHLD");
		exit(1);
	}

	/* Erzeugen von zwei Pipe */
	if(pipe(pd[0]) < 0) {
		perror("pipe");
		exit(1);
	}
	if(pipe(pd[1]) < 0) {
		perror("pipe");
		exit(1);
	}

	pid = fork();
	if (pid < 0) {
		perror("fork");
		exit(1);
	}

	if ( pid == 0) { /* Kindprozess */
		/* ungenutzten Pipe-Deskr. schließen */
		close(pd[0][1]);
		close(pd[1][0]);

		/* Umlegen der Pipe auf stdin(0)/stdout(1) */
		close(0);
		dup(pd[0][0]);
		close(pd[0][0]);

		close(1);
		dup(pd[1][1]);
		close(pd[1][1]);

		/* Signale wieder auf Standard stellen */
		if (signal(SIGINT, SIG_DFL) == SIG_ERR) {
			perror("SIGINT");
			exit(1);
		}
		if (signal(SIGQUIT, SIG_DFL) == SIG_ERR) {
			perror("SIGQUIT");
			exit(1);
		}
		if (signal(SIGPIPE, SIG_DFL) == SIG_ERR) {
			perror("SIGPIPE");
			exit(1);
		}
		if (signal(SIGCHLD, SIG_DFL) == SIG_ERR) {
			perror("SIGCHLD");
			exit(1);
		}

		execlp("bc", "bc", "-q", "-l", NULL);
		perror("bc");
		exit(1);
	}
	/* Vaterprozess */
	/* ungenutzten Pipe-Deskr. schließen */
	close(pd[0][0]);
	close(pd[1][1]);

	write(1, "Eingabe: ", 9);
	while( rc = read(0, buffer, MAXLEN), rc > 0) {
		/* Prüfen, ob mindestens ein Zeichen angegeben wurde */
		leer = 1;
		for (i = 0; i < rc; i++) {
			if (!isspace(buffer[i])) {
				leer = 0;
				break;
			}
		}
		if (leer) {
			write(1, "Eingabe: ", 9);
			continue;
		}

		rc2 = write(pd[0][1], buffer, (unsigned int)rc);
		if (rc2 < 0) {
			if (errno != EPIPE)
				perror("write");
			break; /* Pipe ist geschlossen */
		}
		rc = read(pd[1][0], buffer, MAXLEN);
		write(1, buffer, (unsigned int)rc);
		write(1, "Eingabe: ", 9);
	}

	if (rc < 0) {
		perror("read");
	}

	close(pd[0][1]);

	printf("\n");

	pid = wait(&status);
	if (pid < 0 && errno != ECHILD) {
		perror("wait");
		exit(1);
	}
	if (WIFEXITED(status)) {
		printf("Prozess %d normal beendet mit Exitcode %d\n", pid, WEXITSTATUS(status));
	}
	else {
		printf("Prozess %d wurde dirch das Signal %d beendet\n", pid, WTERMSIG(status));
	}
	printf("ENDE\n");
	return rc < 0 ? 1 : 0;
}

void abfangen(int sig)
{
	int pid, status;

	printf("\n");

	pid = wait(&status);
	if (pid < 0 && errno != ECHILD) {
		perror("wait");
		exit(1);
	}
	if (WIFEXITED(status)) {
		printf("Prozess %d normal beendet mit Exitcode %d\n", pid, WEXITSTATUS(status));
	}
	else {
		printf("Prozess %d wurde dirch das Signal %d beendet\n", pid, WTERMSIG(status));
	}
	printf("ENDE\n");
	exit(2);
}
