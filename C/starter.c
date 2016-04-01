#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>

int main(int argc, char *argv[])
{

	if(argc < 2) {
		fprintf(stderr, "Zu wenig Argumente!\n");
		exit(1);
	}
	setbuf(stdout, NULL);

	printf("meine PID: %d\n", getpid());
	printf("meine PPID: %d\n", getppid());

	printf("Ich starte jetzt %s ...",argv[1]);
	//fflush(stdout);
	execlp(argv[1],argv[1],NULL);

	perror(argv[1]);

	return 1;  /* wir haben einen Fehler */
}
