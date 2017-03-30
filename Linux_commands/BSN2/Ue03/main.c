#include <errno.h>
#include <stdio.h>
#include <string.h>

#define A4
#define A4A

void printAllErrorCodes();
void printfSingleChars();


int main(int argc, char* args){
#ifdef A3
	printAllErrorCodes();
#endif
#ifdef A4
	printfSingleChars();
#endif


	return 0;
}

// Uebung 3-3
void printAllErrorCodes(){
	int code;	
	for(code = 0; code < 100; ++code){
		printf("Code %02d: %s\n", code, strerror(code));
	}

}

// Uebung 3-4
void printfSingleChars(){
	char c;
	char buffer[256];
	FILE* file;
	char* rc;
#ifdef A4A
	errno = 0;
	file = fopen("./etwa20MB.txt","r");
	if(file < 0 || errno != 0){
		perror("Fehler beim öffnen");
	}	
	errno = 0;
	do {
	errno = 0;
		c = fgetc(file);
		if(c == EOF){
			if(errno != 0)	
				perror("Fehler beim lesen");
			break;
		}		
		fputc(c,stdout);
	}
	while(1);

	errno = 0;
	fclose(file);	
	if(errno != 0){
		perror("Fehler beim schließen");
	}
	/* ---------------------------------*/
#endif
#ifdef A4B
	errno = 0;
	file = fopen("./etwa20MB.txt","r");
	if(file < 0 || errno != 0){
		perror("Fehler beim öffnen");
	}	
	errno = 0;
	while(1) {
		errno = 0;
		rc = fgets(buffer, 256, file);
		if(rc == 0){
			if(errno != 0)	
				perror("Fehler beim lesen");
			break;
		}		
		{
			int returnCode;
			errno = 0;
			returnCode = fputs(buffer,stdout);
			if(returnCode < 0 ){
				if(errno != 0)	
					perror("Fehler beim ausgeben");
			}
		}
	}

	errno = 0;
	fclose(file);	
	if(errno != 0){
		perror("Fehler beim schließen");
	}

#endif
}



