#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <string.h>
#include <sys/utsname.h>
#include <sys/sysinfo.h>
#include <time.h>
#include <signal.h>
#include <sys/wait.h>
#include <ctype.h>
#include <errno.h> /* immer am Ende */


#define MAXLEN	80

//#include "snp.h"
int readline( int fd, char *buffer, unsigned int maxlen); 
void abfangen(int signal);
void doService(int sd);
void ende(int sig);

int main(int argc, char *argv[])
{
	int sd, td;
	struct sockaddr_in adresse;
	struct sockaddr_in client;
	socklen_t len;
	u_short port = 33330; 
	int pid;
	int rc;
	int status;
	int flag;

	/* Was soll passieren wenn SIGINT eintrifft? */
	if (signal(SIGINT, abfangen) == SIG_ERR) {
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

	flag = 1;
	/* In diesem Fall kann ein Fehler ignoriert werden */
	setsockopt(sd, SOL_SOCKET, SO_REUSEADDR, &flag, sizeof(flag));

	sd = socket(AF_INET, SOCK_STREAM, 0);
	if (sd < 0) {
		perror("socket");
		exit(1);
	}

	bzero(&adresse, sizeof(adresse));
	adresse.sin_family = AF_INET;
	adresse.sin_port = htons(port);
	adresse.sin_addr.s_addr = INADDR_ANY; /* Von allen Schnittstellen Clients in Empfang nehmen */

	if (bind(sd, (struct sockaddr *) &adresse, sizeof(adresse)) < 0) {
		perror("bind");
		exit(1);
	}

	if (listen(sd, 5) < 0) {
		perror("listen");
		exit(1);
	}

	printf("Warten auf Clients auf Port %hu\n", port);
	while(1) {
		len = sizeof(client);
		td = accept(sd, (struct sockaddr *) &client, &len);
		if( td < 0) {
			perror("accept");
			continue;
		}
		/* Client ist connected */
		pid = fork();
		if(pid < 0) {
			perror("fork");
			exit(1);
		}

		if(pid == 0) { 
			/* Kindprozess */
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

			close(sd);
			doService(td);
			close(td);
			exit(0);
		}
		/* Vater */
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
		printf("Client %d connected\n", pid);
		close(td);

	}
	close(sd);
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

void doService(int sd)
{
	char buffer[MAXLEN];
	int rc;
	char timestr[MAXLEN];
	struct utsname ubuf;
	time_t rawtime;
	struct tm *timeinfo;

	if (signal(SIGPIPE, SIG_IGN) == SIG_ERR) {
		perror("SIGPIPE");
		exit(12);
	}

	/* Schreib-LeseSchleife? */
	write(sd, "Hello\n", 6);
	while (1) {
		rc = readline(sd, buffer, MAXLEN-1);
		if (rc < 0) {
			perror("read");
			exit(1);
		}
		if (rc == 0)
			break;
		buffer[rc] = '\0';

		if( strcasecmp(buffer, "uname\n") == 0) {
			rc = uname(&ubuf);
			//if( rc < 1 )
			write(sd, ubuf.nodename, strlen(ubuf.nodename));
			write(sd, "\n", 1);

		}

		if( strcasecmp(buffer, "release\n") == 0) {
			rc = uname(&ubuf);
			//if( rc < 1 )
			write(sd, ubuf.release, strlen(ubuf.release));
			write(sd, "\n", 1);
			write(sd, ubuf.version, strlen(ubuf.version));
			write(sd, "\n", 1);
		}

		if( strcasecmp(buffer, "time\n") == 0) {
			time(&rawtime);
			timeinfo = localtime( &rawtime );
			rc = sprintf( timestr,"%02d:%02d", timeinfo->tm_hour, timeinfo->tm_min);
			write(sd, timestr, strlen(timestr));
			write(sd, "\n", 1);
		}

		if( strcasecmp(buffer, "date\n") == 0) {
			time(&rawtime);
			timeinfo = localtime( &rawtime );
			rc = sprintf( timestr,"%02d.%02d.%d", timeinfo->tm_mday, 1 + timeinfo->tm_mon,1900 + timeinfo->tm_year);
			write(sd, timestr, strlen(timestr));
			write(sd, "\n", 1);
		}

		if( strcasecmp(buffer, "quit\n") == 0) {
			strncpy(buffer, "Closing Connection", MAXLEN-1);
			write(sd, buffer , strlen(buffer));
			write(sd, "\n", 1);
			break;
		}


		//	write(sd, buffer,strlen(buffer));
	}

}


int readline( int fd, char *buffer, unsigned int maxlen) 
{
	int rc;
	int len = 0;

	if(maxlen < 1) {
		errno = ENOMEM; /*Fehler: no memory */
		return -1;
	}


	while ( rc = (int) read(fd, &buffer[len], 1), rc > 0) {
		len++;
		if (len >= maxlen)
			break;
		if(buffer[len-1] == '\n')
			break;
	}

	if(rc <= 0)
		return rc;
	return len;
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
}

void ende(int sig)
{
	printf("\nENDE\n");
	fflush(stdout);
	exit(SIGTERM);
}

