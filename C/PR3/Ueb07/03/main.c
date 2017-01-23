
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

int main(int argc, char *args[]) {
    int rc = 0;

    if (argc != 2) {
        printf("Übergabeparamter erwartet: [-] (Konsolen Eingabe) / [Dateipfad] (einzulesende Datei)\n");
        exit(1);
    }

    if (strcmp(args[1], "-") == 0) {

        printf("Konsole gewaehlt.\n");
        rc = getchar();
        while (rc != EOF) {
            if(rc == 'ä'){
                putchar('a');
                putchar('e');
            }
            putchar(rc);
            rc = getchar();
        }
    }


    return 0;
}