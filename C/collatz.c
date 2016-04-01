#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h> 
#include <unistd.h>


int main(int argc, char *argv[])
{
	int x, n, rc;

	printf("meine PID: %d\n", getpid());
	printf("meine PPID: %d\n", getppid());
	while(1) {
		/* Ganzzahl > 0 einlesen (0 für Ende zulassen!) */
		do {
			printf("x (0: Exit): ");
			rc = scanf("%d", &x);

			while (getchar() != '\n')
				continue;

		}
		while (rc != 1 || x < 0);

		if (x == 0) /* Ende gewünscht */
			exit(0);

		n = 0;

		while (x != 1) {
			if (x % 2 == 0)
				x /= 2;
			else
				x = 3*x + 1;

			n++;
			printf("\t%d\n", x);
		}
		printf("1 erreicht in %d Schritten\n", n);
	}
	return 0;
}
