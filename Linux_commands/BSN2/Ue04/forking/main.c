#include <errno.h>
#include <unistd.h>
#include <stdio.h>
#include <wait.h>
#include <stdlib.h>
#include <fcntl.h>

void simpleFork();

void checkChildExitStatus(int status, int childPID);

void chain(int numChilds);

void singleParent(int numChilds);

void childParentWriting();

int main(int argc, char *args[]) {

	//simpleFork();
	//chain(50);
	//singleParent(50);
	childParentWriting();

	return 0;
}

void simpleFork() {
	pid_t pid;
	errno = 0;
	pid = fork();
	if (pid == -1 || errno != 0) {
		perror("Fehler beim forking");
		printf("return code of fork: %d\n", pid);
		printf("Child pid: %d\n", getpid());
		printf("Childs ppid: %d\n", getppid());
	}
	if (pid == 0) {
		// Child
		printf("Kind:\n");
		printf("Kind: return code of fork: %d\n", pid);
		printf("Kind: pid: %d\n", getpid());
		printf("Kind: ppid: %d\n", getppid());
	} else {
		// Parent
		int childPID = pid;
		int status;
		printf("Parent:\n");
		printf("Parent: return code of fork: %d\n", pid);
		printf("Parent: pid: %d\n", getpid());
		printf("Parent: ppid: %d\n", getppid());

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

void chain(int numChilds) {
	int index;
	for (index = 0; index < numChilds; ++index) {
		pid_t pid;
		errno = 0;
		pid = fork();
		if (pid == -1 || errno != 0) {
			perror("Fehler beim forking");
			printf("return code of fork: %d\n", pid);
			printf("Child pid: %d\n", getpid());
			printf("Childs ppid: %d\n", getppid());
		}
		if (pid == 0) {
			// Child
			printf("Kind:\n");
			printf("Kind: chain number: %d\n", index + 1);
			printf("Kind: return code of fork: %d\n", pid);
			printf("Kind: pid: %d\n", getpid());
			printf("Kind: ppid: %d\n", getppid());

			if (index + 1 == numChilds) {
				printf("Press ENTER to continue: ");
				while (getchar() != '\n');
			}
		} else {
			// Parent
			int childPID = pid;
			int status;

			errno = 0;
			pid = waitpid(childPID, &status, 0);
			if (pid == -1 || errno != 0) {
				perror("Fehler beim Warten auf Kindprozess");
			} else {
				checkChildExitStatus(status, childPID);
			}

			printf("Parent:\n");
			printf("Parent: chain number: %d\n", index);
			printf("Parent: return code of fork: %d\n", pid);
			printf("Parent: pid: %d\n", getpid());
			printf("Parent: ppid: %d\n", getppid());

			break;
		}
	}
}

void singleParent(int numChilds) {
	int index;
	int *childs;

	// Speicher holen für die List von Child-PIDs
	errno = 0;
	childs = (int *) malloc(numChilds * sizeof(int));
	if (childs == NULL || errno != 0) {
		perror("Fehler beim Allokieren von Speicher");
		return;
	}
	for (index = 0; index < numChilds; ++index) {
		pid_t pid;
		errno = 0;
		pid = fork();
		if (pid == -1 || errno != 0) {
			perror("Fehler beim forking");
			printf("return code of fork: %d\n", pid);
			printf("Child pid: %d\n", getpid());
			printf("Childs ppid: %d\n", getppid());
		}
		if (pid == 0) {
			// Child
			printf("Kind:\n");
			printf("Kind: chain number: %d\n", index + 1);
			printf("Kind: return code of fork: %d\n", pid);
			printf("Kind: pid: %d\n", getpid());
			printf("Kind: ppid: %d\n", getppid());
			break;
		} else {
			// Parent
			int childPID = pid;
			childs[index] = childPID;

			printf("Parent:\n");
			printf("Parent: chain number: %d\n", index);
			printf("Parent: return code of fork: %d\n", pid);
			printf("Parent: pid: %d\n", getpid());
			printf("Parent: ppid: %d\n", getppid());

			// wenn parent alle Kinder erzeugt hat wartet er auf deren Beendigung
			if (index + 1 == numChilds) {
				sleep(3);
				for (int i = 0; i < numChilds; ++i) {
					int pid;
					int status;

					errno = 0;
					pid = waitpid(childs[i], &status, 0);
					if (pid == -1 || errno != 0) {
						perror("Fehler beim Warten auf Kindprozess");
					} else {
						checkChildExitStatus(status, childs[i]);
					}
				}
			}
		}
	}

	// alle Parent/Childs löschen den allokierten Speicher
	free(childs);
}

void childParentWriting() {
	int fileDescriptor;
	int index;
	pid_t pid;

	errno = 0;
	if (access("./abc.txt", F_OK) == 0) {
		// Datei existiert bereits: löschen
		errno = 0;
		if (remove("./abc.txt") == -1 || errno != 0) {
			perror("Fehler beim Löschen von abc.txt");
		}
	} else if (errno != 0) {
		perror("Fehler beim Prüfen auf Existenz der Datei abc.txt");
		if (errno == ENOENT) {
			printf("Datei abc.txt wird erzeugt.\n");
		}
	}

	errno = 0;
	fileDescriptor = open("./abc.txt", O_RDWR | O_CREAT, S_IRUSR | S_IWUSR);
	if (fileDescriptor == -1 || errno != 0) {
		perror("Datei abc.txt konnte nicht geöffnet werden");
		return;
	}


	errno = 0;
	pid = fork();
	if (pid == -1 || errno != 0) {
		perror("Fehler beim forking");
		printf("return code of fork: %d\n", pid);
		printf("Child pid: %d\n", getpid());
		printf("Childs ppid: %d\n", getppid());
	}
	if (pid == 0) {
		// Child
		printf("Kind:\n");
		printf("Kind: return code of fork: %d\n", pid);
		printf("Kind: pid: %d\n", getpid());
		printf("Kind: ppid: %d\n", getppid());

		for (index = 0; index < 10000; ++index) {
			errno = 0;
			if (write(fileDescriptor, "Ich bin Kind\n", 13) != 13 || errno != 0) {
				perror("Fehler beim schreiben in abc.txt");
				break;
			}
		}

	} else {
		// Parent
		int childPID = pid;
		int status;
		printf("Parent:\n");
		printf("Parent: return code of fork: %d\n", pid);
		printf("Parent: pid: %d\n", getpid());
		printf("Parent: ppid: %d\n", getppid());

		for (index = 0; index < 10000; ++index) {
			errno = 0;
			if (write(fileDescriptor, "Ich bin Parent\n", 15) != 15 || errno != 0) {
				perror("Fehler beim schreiben in abc.txt");
				break;
			}
		}

		errno = 0;
		pid = waitpid(childPID, &status, 0);
		if (pid == -1 || errno != 0) {
			perror("Fehler beim Warten auf Kindprozess");
		} else {
			checkChildExitStatus(status, childPID);
		}
	}

	errno = 0;
	if (close(fileDescriptor) == -1 || errno != 0) {
		perror("Fehler beim Schließen von abc.txt");
	}
}