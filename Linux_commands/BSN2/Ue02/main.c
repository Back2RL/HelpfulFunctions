#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <errno.h>
#include <assert.h>

#define LOOPS 30000
#define BUFFERSIZE 1024

int main(int argc, char* args){
	int rc;	
	int fileDescriptor;
	int index;
	char buffer[BUFFERSIZE+1];

	printf("1\n");
	errno = 0;
	fileDescriptor = open("./file.txt", O_RDONLY);
	if(fileDescriptor < 0){
		if(errno != 0){
			perror("Datei file.txt konnte nicht geöffnet werden");
		}
	}
	close(fileDescriptor);
	printf("Der Dateideskriptor ist %d\n", fileDescriptor);


	sleep(1);

	/* in Datei schreiben */
	printf("2\n");
	errno = 0;
	fileDescriptor = open("./1234.txt", O_RDONLY /*O_RDWR*/);
	if(fileDescriptor < 0){
		if(errno != 0){
			perror("Datei 1234.txt konnte nicht geöffnet werden");
		}
	}

	errno = 0;
	if(write(fileDescriptor, "Hallo",5) != 5){
		if(errno != 0){
			perror("Fehler beim schreiben in 1234.txt");
		}
	}	
	close(fileDescriptor);

	/* 30000 mal Hallo */
	printf("3\n");

	errno = 0;
	fileDescriptor = open("./hallo.txt", O_RDWR/* | O_SYNC*/);
	if(fileDescriptor < 0){
		if(errno != 0){
			perror("Datei hallo.txt konnte nicht geöffnet werden");
		}
	}

	for(index = 0; index < LOOPS; ++index) {
		errno = 0;
		printf("%d\n", index);
		if(write(fileDescriptor, "Hallo",5) != 5){
			if(errno != 0){
				perror("Fehler beim schreiben in hallo.txt");
				break;
			}
		}	
	}
	close(fileDescriptor);

	/* Datei erstellen */
	printf("4\n");
	errno = 0;
	fileDescriptor = open("./created.txt", O_RDWR | O_CREAT | O_EXCL, 0644);
	if(fileDescriptor < 0){
		if(errno != 0){
			perror("Datei created.txt konnte nicht geöffnet werden");
		}
	}
	errno = 0;
	if(write(fileDescriptor, "Hallo",5) != 5){
		if(errno != 0){
			perror("Fehler beim schreiben in created.txt");
		}
	}

	close(fileDescriptor);
	printf("5\n");

	errno = 0;
	fileDescriptor = open("./created.txt", O_RDWR);
	if(fileDescriptor < 0){
		if(errno != 0){
			perror("Datei created.txt konnte nicht geöffnet werden");
		}
	}

	printf("Dateiinhalt von created:\n");
	while(read(fileDescriptor,buffer,1) == 1){
		printf("%s", buffer);
	}
	printf("\n");

	close(fileDescriptor);

	/* 8 Seeking */
	errno = 0;
	fileDescriptor = open("./hallo.txt", O_RDONLY);
	if(fileDescriptor < 0){
		if(errno != 0){
			perror("Datei hallo.txt konnte nicht geöffnet werden");
		}
	}

	errno = 0;
	rc = lseek(fileDescriptor, -1600,SEEK_END);
	if(rc < 0){
		if(errno != 0){
			perror("Seek in Datei hallo.txt fehlgeschlagen");
		}
	}
	printf("Cursor-Position in der Datei nach dem seek: %d\n", rc); 

	printf("Dateiinhalt von created:\n");
	errno = 0;
	while(rc = read(fileDescriptor,buffer,BUFFERSIZE), rc > 0){
		buffer[rc] = '\0';		
		printf("%s", buffer);
	}
	if(errno != 0){
		perror("Fehler beim lesen von hallo.txt");	
	}
	printf("\n");

	close(fileDescriptor);


	return 0;
}
