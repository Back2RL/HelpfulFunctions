#include <unistd.h>
#include <errno.h>
#include <string.h>
#include "include/snp.h"

int readline( int fd, char *buffer, unsigned int maxlen) 
{
	int rc;
	int len = 0;

	if(maxlen < 1) {
		errno = ENOMEM; /*Fehler: no memory */
		return -1;
	}

	memset(buffer,0,maxlen);

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

void writeline(int fd, char buffer[MAXLEN])
{
	int rc;
	rc = (int) write( fd, buffer, strlen(buffer));
	/* Auf Fehler überprüfen */
	if (rc < 0) {
		perror("write");
		exit(1);
	}
}
