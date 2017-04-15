#include <stdio.h>
#include <errno.h>
#include <sys/stat.h>
#include <dirent.h>
#include <fcntl.h>
#include <unistd.h>

int readDirectory(int fileDescriptor) {
	DIR *verzeichnis;
	struct dirent *dirEntry;
	char buffer[1024];
	int ok = 0;

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
					struct stat fileStat;
					printf("\nAttribute von %s:\n", dirEntry->d_name);

					errno = 0;
					// Attribute auslesen und in fileStat schreiben
					if (stat(dirEntry->d_name, &fileStat) == -1 || errno != 0) {
						perror("Fehler beim Lesen der Dateiattribute");
						continue;
					}

					if (S_ISREG(fileStat.st_mode)) {
						printf("%s ist eine normale Datei\n", dirEntry->d_name);
					} else if (S_ISDIR(fileStat.st_mode)) {
						printf("%s ist ein Verzeichnis\n", dirEntry->d_name);
					}
				}
			} while (dirEntry != NULL);

			errno = 0;
			if (chdir(buffer) == -1 || errno != 0) {
				perror("Fehler beim zurücksetzten des Arbeitsverzeichnisses auf den vorherigen Pfad");
				ok = -1;
			}
		}
	}

	errno = 0;
	if (!closedir(verzeichnis) && errno != 0) {
		perror("Fehler beim Schließen des Verzeichnisses");
		ok = -1;
	}
	return ok;
}

int main(int argc, char *args[]) {

	int fileDescriptor;


	if (argc < 2) {
		printf("Bitte Verzeichnis angeben\n");
		return -1;
	}


	errno = 0;
	fileDescriptor = open(args[1], O_RDONLY);
	if (fileDescriptor < 0) {
		if (errno != 0) {
			perror("Fehler beim öffnen");
		}
	} else {

		if (readDirectory(fileDescriptor) == -1) {
			printf("Die angegebene Datei ist eine Datei\n");

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
