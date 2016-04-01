#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <string.h>
//#include "readline.c"

#define MAXLEN 80

#include "snp.h"

void usage(char *pname);

int main(int argc, char *argv[])
{
	int sd;
	struct sockaddr_in server;
	struct hostent *host; /* Zeiger! */
	char buffer[MAXLEN];
	int rc;
	int optchar;
	char opt_hostname[MAXLEN] = "NONE\0";
	int hostname;
	int opt_portnumber;
	int opt_http_header;	
	int can_write;
	char url[MAXLEN];

	hostname = 0;
	opt_portnumber = 0;
	opt_http_header = 0;	
	opterr = 0;
	while(optchar = getopt(argc, argv, "h:p:H"), optchar != -1) {
		switch(optchar) {
			case 'h':
				rc = sscanf(optarg, "%s", &opt_hostname[0]);
				if (rc != 1 || strlen(opt_hostname) > MAXLEN-1)
					usage(argv[0]);
				hostname = 1;
				break;
			case 'p':
				rc = sscanf(optarg, "%d", &opt_portnumber);
				if (rc != 1 || opt_portnumber <= 0)
					usage(argv[0]);
				break;
			case 'H':
				opt_http_header = 1;
				break;
			default:
				usage(argv[0]);
				exit(1);
		}
	}

	if(hostname != 1){
		usage(argv[0]);
		exit(1);
	}
	/* optind: erstes Argument welches keine Option ist */
	if (argv[optind] == NULL) {
		strncpy(url, "/index.html", MAXLEN-1);
	}
	else {
		strncpy(url, argv[optind], MAXLEN-1);
	}

	/* Kontrolle
	printf("%s:%d -H = %d %s\n", opt_hostname, opt_portnumber, opt_http_header, url);
	*/
	
	sd = socket(AF_INET, SOCK_STREAM, 0);
	if (sd < 0) {
		perror("socket");
		exit(1);
	}

	bzero(&server, sizeof(server));
	server.sin_family = AF_INET;
	if( opt_portnumber == 0) {
		server.sin_port = htons(80);
	} 
	else {
		server.sin_port = htons((unsigned short)opt_portnumber);
	}
	/*
	   host = gethostbyname("www.google.de");
	   if (host == NULL) {
	   perror("www.google.de");
	   exit(1);
	   }*/
	host = gethostbyname(opt_hostname);
	if (host == NULL) {
		perror(opt_hostname);
		exit(1);
	}

	server.sin_addr = *(struct in_addr *)host->h_addr;

	if (connect(sd, (struct sockaddr *) &server, sizeof(server)) < 0) {
		perror("connect");
		exit(1);
	}

	/* Kommunikation mit dem Server */
//	sprintf(buffer, "GET /index.html HTTP/1.0\r\n\r\n");
	sprintf(buffer, "GET %s HTTP/1.0\r\n\r\n", url);
	rc = write(sd, buffer, strlen(buffer));
	if (rc < 0) {
		perror("write");
		exit(1);
	}

	printf("Ergebnis:\n");
	can_write = 0;
	while (1) {
		rc = readline(sd, buffer, MAXLEN-1);
		if (rc < 0) {
			perror("read");
			exit(1);
		}
		if (rc == 0)
			break;
		buffer[rc] = '\0';
		
		if(!opt_http_header) {	
			if (strcmp(buffer, "\n") == 0 || strcmp(buffer, "\r\n") == 0)
				can_write = 1;
		} 
		else {
			can_write = 1;
		}

		if (can_write) 
			write(1, buffer, (unsigned) rc);


	}
	printf("\n--- ENDE ---\n");

	close(sd);
	return 0;
}

void usage(char *pname)
{
	char *slash;

	slash = strrchr(pname, '/');
	if (slash != NULL)
		pname = slash + 1;
	fprintf(stderr, "%s: Ausgabeprogramm\n", pname);
	fprintf(stderr, "\t -h hostname\n");
	fprintf(stderr, "\t[-p portnumber]\n");
	fprintf(stderr, "\t -H: HTTP-Header ausgeben\n");
	exit(1);
}

