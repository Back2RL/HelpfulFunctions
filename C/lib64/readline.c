#include <unistd.h>
#include <errno.h>
#include "snp.h"

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
