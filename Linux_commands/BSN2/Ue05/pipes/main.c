#include <errno.h>
#include <unistd.h>
#include <stdio.h>
#include <wait.h>
#include <memory.h>
#include <ctype.h>

#define READ 0
#define WRITE 1

// Ue04 Aufg. 3
void simpleFork();

void checkChildExitStatus(int status, int childPID);


int main(int argc, char *args[]) {

	simpleFork();

	return 0;
}

void simpleFork() {
	pid_t pid;

	// pipes
	int pipeParent2Child[2];
	int pipeChild2Parent[2];

	// create 1. pipe
	errno = 0;
	if (pipe(pipeParent2Child) == -1 || errno != 0) {
		perror("Fehler beim Erzeugen der 1. Pipe");
	}
	// create 2. pipe
	errno = 0;
	if (pipe(pipeChild2Parent) == -1 || errno != 0) {
		perror("Fehler beim Erzeugen der 2. Pipe");
		return;
	}

	errno = 0;
	pid = fork();
	if (pid == -1 || errno != 0) {
		perror("Fehler beim forking");
		printf("return code of fork: %d\n", pid);
		printf("Child pid: %d\n", getpid());
		printf("Childs ppid: %d\n", getppid());
	} else if (pid == 0) {
		// Child
		printf("Kind:\n");
		printf("Kind: return code of fork: %d\n", pid);
		printf("Kind: pid: %d\n", getpid());
		printf("Kind: ppid: %d\n", getppid());

		errno = 0;
		if (close(pipeParent2Child[WRITE]) == -1 || errno != 0) {
			perror("Fehler beim Schließen der Schreiböffnung der Lese-Pipe im Child;");
		}
		errno = 0;
		if (close(pipeChild2Parent[READ]) == -1 || errno != 0) {
			perror("Fehler beim Schließen der Leseöffnung der Schreib-Pipe im Child;");
		}

		{
			char zeile[1024];
			do {
				int n = (int) read(pipeParent2Child[READ], zeile, 1024);
				printf("Kind hat %d Zeichen empfangen\n", n);
				zeile[n] = '\0';
				printf("Kind Stringlen = %d\n", (int) strlen(zeile));
				if (n > 10) {
					for (int i = 0; i < n; i++) {
						if (zeile[i] == '\0' || zeile[i] == '\n') continue;
						zeile[i] = (char) toupper(zeile[i]); // in Großbuchstabe wandeln
					}
					printf("Hier Kind, Zeile in GROSS: %s", zeile);
					write(pipeChild2Parent[WRITE], zeile, strlen(zeile));
				} else if (errno != 0) {
					perror("Fehler beim Lesen aus der Pipe");
					break;
				} else break;
			} while (1);
		}
		printf("Child Ende...\n");

		errno = 0;
		if (close(pipeParent2Child[READ]) == -1 || errno != 0) {
			perror("Fehler beim Schließen der Leseböffnung im Parent;");
		}
		errno = 0;
		if (close(pipeChild2Parent[WRITE]) == -1 || errno != 0) {
			perror("Fehler beim Schließen der Schreiböffnung im Parent;");
		}

	} else {
		// Parent
		int childPID = pid;
		int status;
		printf("Parent:\n");
		printf("Parent: return code of fork: %d\n", pid);
		printf("Parent: pid: %d\n", getpid());
		printf("Parent: ppid: %d\n", getppid());

		errno = 0;
		if (close(pipeChild2Parent[WRITE]) == -1 || errno != 0) {
			perror("Fehler beim Schließen der Schreiböffnung der Lese-Pipe im Parent;");
		}
		errno = 0;
		if (close(pipeParent2Child[READ]) == -1 || errno != 0) {
			perror("Fehler beim Schließen der Leseöffnung der Schreib-Pipe im Parent;");
		}

		{
			char zeile[1024];
			size_t textLenRead;
			ssize_t textLenSend;
			size_t textLenReceived;
			do {
				printf("Hier Vater, bitte Zeile eingeben: ");
				fgets(zeile, 1024, stdin);
				textLenRead = strlen(zeile);
				errno = 0;
				textLenSend = write(pipeParent2Child[WRITE], zeile, textLenRead);
				if (textLenSend == -1 || errno != 0) {
					perror("Fehler beim Screiben im Parent");
				}

				if (read(pipeChild2Parent[READ], zeile, 1024) > 0) {
					textLenReceived = strlen(zeile);
					printf("Eingelesen %d; Gesendet %d; Empfangen %d\n", (int) textLenRead, (int) textLenSend,
						   (int) textLenReceived);
					printf("Hier Vater, gelesen: %s", zeile);
				} else if (errno != 0) {
					perror("Fehler beim Lesen aus der Pipe");
					break;
				} else break;
			} while (1);
		}

		errno = 0;
		if (close(pipeChild2Parent[READ]) == -1 || errno != 0) {
			perror("Fehler beim Schließen der Leseböffnung im Parent;");
		}
		errno = 0;
		if (close(pipeParent2Child[WRITE]) == -1 || errno != 0) {
			perror("Fehler beim Schließen der Schreiböffnung im Parent;");
		}


		errno = 0;
		pid = waitpid(childPID, &status, 0);
		if (pid == -1 || errno != 0) {
			perror("Fehler beim Warten auf Kindprozess");
		} else {
			checkChildExitStatus(status, childPID);
		}
	}

}

void checkChildExitStatus(int status, int childPID) {
	printf("Parent (PID = %d): Child (PID = %d) is no longer being waited for:\n", getpid(), childPID);
	if (WIFEXITED(status)) {
		printf("child terminated normally\n");
		printf("Exit status of child = %d\n", WEXITSTATUS(status));
	}
	if (WIFSIGNALED(status)) {
		printf("child process was terminated by a signal.\n");
		printf("number of the signal that caused the child process to terminate = %d\n", WTERMSIG(status));
#ifdef WCOREDUMP
		if (WCOREDUMP(status)) {
			printf("child  produced  a core dump\n");
		}
#endif
	}
	if (WIFSTOPPED(status)) {
		printf("child process was stopped by delivery of a signal\n");
		printf("number of the signal which caused the child to stop = %d\n", WSTOPSIG(status));
	}
	if (WIFCONTINUED(status)) {
		printf("child  process  was	resumed by delivery of SIGCONT\n");
	}
}
