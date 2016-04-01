#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>

int main(int argc, char *argv[])
{

	if(argc < 2) {
		printf("Zu wenig Argumente!\n");
		exit(1);
	}

	printf("meine PID: %d\n", getpid());
	printf("meine PPID: %d\n", getppid());

	printf("Ich starte jetzt %s\n",argv[1]);
	execlp(argv[1],argv[1],NULL);

	perror(argv[0]);

	return 1;  /* wir haben einen Fehler */
}
