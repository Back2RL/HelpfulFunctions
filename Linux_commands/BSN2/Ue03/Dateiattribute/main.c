#include <stdio.h>
#include <errno.h>
#include <sys/stat.h>
#include <dirent.h>
#include <fcntl.h>
#include <unistd.h>

#ifndef TRUE
#define TRUE 1
#endif
#ifndef FALSE
#define FALSE 0
#endif


int readDirectory(int fileDescriptor, char *fileName, int bFollowLinks);

int printFileType(char *filename, int bFollowLinks);

int main(int argc, char *args[]) {

	int fileDescriptor;
	int bFollowLinks = FALSE;

	if (argc < 2) {
		printf("Bitte Verzeichnis-/Dateipfad angeben\n");
		return -1;
	}

	errno = 0;
	fileDescriptor = open(args[1], O_RDONLY);
	if (fileDescriptor == -1 || errno != 0) {
		perror("Fehler beim Öffnen");
	} else {
		if (readDirectory(fileDescriptor, args[1], bFollowLinks) == -1) {

			// Datei ist kein Verzeichnis
			if (printFileType(args[1], bFollowLinks) == -1) {
				printf("Fehler beim Ausgeben des Dateityps");
			}

			errno = 0;
			if (close(fileDescriptor) == -1) {
				if (errno != 0) {
					perror("Fehler beim Schließen der Datei");
				}
			}
		}
	}

	return 0;
}

int readDirectory(int fileDescriptor, char *fileName, int bFollowLinks) {
	DIR *verzeichnis;
	struct dirent *dirEntry;
	char buffer[1024];
	struct stat fileStat;
	int ok = 0;

	// check if filedescriptor is a symbolic link
	errno = 0;
	if (lstat(fileName, &fileStat) == -1 || errno != 0) {
		perror("Fehler beim Lesen der Dateiattribute");
		return -1;
	}
	if (S_ISLNK(fileStat.st_mode) && !bFollowLinks) {
		printf("%s ist ein Link\n", fileName);
		return 0;
	}


	errno = 0;
	verzeichnis = fdopendir(fileDescriptor);
	if (verzeichnis == NULL || errno != 0) {
		perror("Fehler beim Öffnen als Verzeichnis");
		return -1;
	}

	printf("Die angegebene Datei ist ein Verzeichnis\n");

	errno = 0;
	if (getcwd(buffer, 1024) == NULL || errno != 0) {
		perror("Fehler beim Auslesen des current-working-directory");
		ok = -1;
	} else {
		printf("cwd = %s\n", buffer);

		errno = 0;
		if (fchdir(fileDescriptor) == -1 || errno != 0) {
			perror("Fehler beim Wechseln des current-working-directory");
			ok = -1;
		} else {
			do {
				errno = 0;
				dirEntry = readdir(verzeichnis);
				if (dirEntry == NULL) {
					if (errno != 0) {
						perror("Fehler beim Einlesen des Verzeichniseintrags");
					} else {
						// keine weiteren Einträge im Verzeichnis
						break;
					}
				} else {
					if (printFileType(dirEntry->d_name, bFollowLinks) == -1) {
						printf("Fehler beim Ausgeben des Dateityps\n");
					}
				}
			} while (dirEntry != NULL);

			errno = 0;
			if (chdir(buffer) == -1 || errno != 0) {
				perror("Fehler beim Zurücksetzten des Arbeitsverzeichnisses auf den vorherigen Pfad");
				ok = -1;
			}
		}
	}

	errno = 0;
	if (closedir(verzeichnis) == -1 || errno != 0) {
		perror("Fehler beim Schließen des Verzeichnisses");
		ok = -1;
	}
	return ok;
}

int printFileType(char *filename, int bFollowLinks) {

	struct stat fileStat;

	if (filename == NULL) return -1;

	printf("\nAttribute von %s:\n", filename);

	errno = 0;
	// Attribute auslesen und in fileStat schreiben
	if (bFollowLinks) {
		if (stat(filename, &fileStat) == -1 || errno != 0) {
			perror("Fehler beim Lesen der Dateiattribute");
			return -1;
		}
	} else {
		if (lstat(filename, &fileStat) == -1 || errno != 0) {
			perror("Fehler beim Lesen der Dateiattribute");
			return -1;
		}
	}

	if (S_ISREG(fileStat.st_mode)) {
		printf("%s ist eine normale Datei\n", filename);
	}
	if (S_ISDIR(fileStat.st_mode)) {
		printf("%s ist ein Verzeichnis\n", filename);
	}
	if (S_ISCHR(fileStat.st_mode)) {
		printf("%s ist ein Character-Device\n", filename);
	}
	if (S_ISBLK(fileStat.st_mode)) {
		printf("%s ist ein Block-Device\n", filename);
	}
	if (S_ISFIFO(fileStat.st_mode)) {
		printf("%s ist ein FIFI (named pipe)\n", filename);
	}
	if (S_ISLNK(fileStat.st_mode)) {
		printf("%s ist ein symbolischer Link\n", filename);
	}
	if (S_ISSOCK(fileStat.st_mode)) {
		printf("%s ist ein Socket\n", filename);
	}

}
