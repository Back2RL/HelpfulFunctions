#include <errno.h>
#include <unistd.h>
#include <stdio.h>
#include <wait.h>

void checkChildExitStatus(int status, int childPID);

int main(int argc, char *args[]) {

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

	return 0;
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