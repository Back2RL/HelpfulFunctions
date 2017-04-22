#include <errno.h>
#include <unistd.h>
#include <stdio.h>
#include <wait.h>
#include <memory.h>
#include <stdlib.h>
#include <ctype.h>

#define READ 0
#define WRITE 1
#define BUFFER_LEN 12

// Ue04 Aufg. 3
void childParentCommunicationWithPipes();

void checkChildExitStatus(int status, int childPID);

int main(int argc, char *args[]) {

	childParentCommunicationWithPipes();

	return EXIT_SUCCESS;
}

void childParentCommunicationWithPipes() {
	// pipes
	int pipeParent2Child[2];
	int pipeChild2Parent[2];

	pid_t pid;

	// create pipe for writing to child-process
	errno = 0;
	if (pipe(pipeParent2Child) == -1 || errno != 0) {
		perror("Fehler beim Erzeugen der 1. Pipe");
		exit(EXIT_FAILURE);
	}
	// create pipe for reading from child-process
	errno = 0;
	if (pipe(pipeChild2Parent) == -1 || errno != 0) {
		perror("Fehler beim Erzeugen der 2. Pipe");
		exit(EXIT_FAILURE);
	}

	// create child process
	errno = 0;
	pid = fork();
	if (pid == -1 || errno != 0) {
		perror("Fehler beim forking");
		printf("return code of fork: %d\n", pid);
		printf("Child pid: %d\n", getpid());
		printf("Childs ppid: %d\n", getppid());
		exit(EXIT_FAILURE);
	} else if (pid == 0) {
		// Child
		printf("Kind:\n");
		printf("Kind: return code of fork: %d\n", pid);
		printf("Kind: pid: %d\n", getpid());
		printf("Kind: ppid: %d\n", getppid());

		// close unused pipe end for reading
		errno = 0;
		if (close(pipeParent2Child[WRITE]) == -1 || errno != 0) {
			perror("Fehler beim Schließen der Schreiböffnung der Lese-Pipe im Child;");
			exit(EXIT_FAILURE);
		}
		// close unused pipe end for writing
		errno = 0;
		if (close(pipeChild2Parent[READ]) == -1 || errno != 0) {
			perror("Fehler beim Schließen der Leseöffnung der Schreib-Pipe im Child;");
			exit(EXIT_FAILURE);
		}

		do {
			char line[BUFFER_LEN];
			int bytesReceived;

			// read message from parent
			errno = 0;
			bytesReceived = (int) read(pipeParent2Child[READ], line, BUFFER_LEN);
			if (bytesReceived == -1 || errno != 0) {
				perror("Fehler beim Lesen aus der Pipe im Child");
				exit(EXIT_FAILURE);
			} else if (bytesReceived == 0) {
				break;
			}

			// append 0 to turn line into valid string
			line[bytesReceived] = '\0';
			printf("Hier Kind, empfangen %d\n", bytesReceived);

			// turn string to uppercase
			if (bytesReceived > 10) {
				for (int i = 0; i < bytesReceived; ++i) {
					line[i] = (char) toupper(line[i]); // in Großbuchstabe wandeln
				}
			} else {
				printf("Hier Kind, Nachricht ist zu kurz\n");
				break;
			} // abort if to short messages were received

			printf("Hier Kind, Zeile in GROSS: %s\n", line);

			// send String to parent
			errno = 0;
			if (write(pipeChild2Parent[WRITE], line, strlen(line)) == -1 || errno != 0) {
				perror("Fehler beim Schreiben in die Pipe im Child");
				exit(EXIT_FAILURE);
			}
		} while (1);

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

		// close unused pipe end for writing
		errno = 0;
		if (close(pipeChild2Parent[WRITE]) == -1 || errno != 0) {
			perror("Fehler beim Schließen der Schreiböffnung der Lese-Pipe im Parent;");
		}
		errno = 0;
		// close unused pipe end for reading
		if (close(pipeParent2Child[READ]) == -1 || errno != 0) {
			perror("Fehler beim Schließen der Leseöffnung der Schreib-Pipe im Parent;");
		}

		do {
			char line[BUFFER_LEN];
			int bytesRead;
			int bytesSend;
			int bytesReceived;

			printf("Hier Vater, bitte Zeile eingeben: ");

			// read user input from console
			errno = 0;
			if (fgets(line, BUFFER_LEN, stdin) == NULL || errno != 0) {
				perror("Fehler beim Lesen von stdin im Parent");
				exit(EXIT_FAILURE);
			}

			bytesRead = (int) strlen(line);
			// make sure stdin is empty in case buffer has been filled completely
			if(bytesRead == BUFFER_LEN-1){
				while( getchar() != '\n');
			}
			printf("Hier Vater, Eingelesen %d;\n", bytesRead);

			errno = 0;
			bytesSend = (int) write(pipeParent2Child[WRITE], line, (size_t) bytesRead);
			if (bytesSend == -1 || errno != 0) {
				perror("Fehler beim Schreiben in die Pipe im Parent");
				exit(EXIT_FAILURE);
			}

			printf("Hier Vater, Gesendet %d\n", bytesSend);

			// read message from child
			errno = 0;
			bytesReceived = (int) read(pipeChild2Parent[READ], line, BUFFER_LEN);
			if (bytesReceived == -1 || errno != 0) {
				perror("Fehler beim Lesen aus der Pipe im Parent");
				exit(EXIT_FAILURE);
			} else if (bytesReceived == 0) {
				break;
			}
			// append 0 to turn line into valid string
			line[bytesReceived] = '\0';

			printf("Hier Vater, Empfangen %d\n", bytesReceived);
			printf("Hier Vater, gelesen: %s\n", line);

		} while (1);

		printf("Parent Ende...\n");

		errno = 0;
		if (close(pipeChild2Parent[READ]) == -1 || errno != 0) {
			perror("Fehler beim Schließen der Leseböffnung im Parent;");
		}
		errno = 0;
		if (close(pipeParent2Child[WRITE]) == -1 || errno != 0) {
			perror("Fehler beim Schließen der Schreiböffnung im Parent;");
		}

		// wait for child to disappear
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
