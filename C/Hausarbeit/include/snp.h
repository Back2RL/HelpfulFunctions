#define MAX(a,b)	((a) > (b)) ? (a) : (b)
#define	MAXLEN 256
#define	TIMEOUT 30

/*---Hilfe-------------------------*/
void usage(char *progname);

/*---Spielfunktionen---------------*/
void mastermind(int sd);
int runde(int sd); 

/*---Fehler/abfangen---------------*/
int checknumber(char *buffer);				
void ende(int signal);
void abfangen(int signal);

/*---lesen/empfangen---------------*/
int readline( int fd, char *buffer, unsigned int maxlen); 
int empfangen(int sd, char *buffer, int maxlen);
void waitforserverreply(int sd, int spieler, char *buffer);

/*---schreiben/senden--------------*/
void writeline(int fd, char *buffer);
int senden(int sd, char *buffer);
void str_senden(int sd, char *topic, char *data, int rc );
void ok(int sd);
