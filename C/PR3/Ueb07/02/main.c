#include <stdio.h>
#include <errno.h>
#include <stdlib.h>

#define NAME_LEN 20


struct angestellter {
    char name[NAME_LEN + 1];
    int personalnummer;
    float gehalt;
};

typedef struct angestellter angestellter;


void binaer_speichern(angestellter arr[], int size) {
    FILE *fp;
    int i = 0;

    errno = 0;
    fp = fopen("angestellte.dat", "wb");
    if (errno != 0 || fp == NULL) {
        perror("Error File open");
    } else {

        fwrite(&size, sizeof(size), 1, fp);

        for (i = 0; i < size; ++i) {

            fwrite(&arr[i], sizeof(angestellter), 1, fp);
            /*
               fprintf(fp,"%s", arr[i].name);
               fprintf(fp,"\n");
             */
        }
    }

    errno = 0;
    fclose(fp);
    fp = NULL;

    if (errno != 0) {
        perror("Error File close");
    }
}

void binaer_laden_und_ausgeben(void) {
    angestellter *angest = NULL;
    int len, i = 0;
    int rc = 0;
    FILE *fp = NULL;

    errno = 0;
    fp = fopen("angestellte.dat", "rb");

    if (errno != 0 || fp == NULL) {
        perror("Error File open");
    } else {
        fread(&len, sizeof(len), 1, fp);
        printf("Einträge = %d\n", len);

        angest = (angestellter*) malloc(len * sizeof(angestellter));
        if(angest == NULL){
            printf("Failed to allocate Memory\n");
            exit(-1);
        }

        rc = fread(angest, len * sizeof(angestellter), 1, fp);
        printf("Num of Elems read = %d\n", rc);

        errno = 0;
        fclose(fp);

        if (errno != 0) {
            perror("Error File close");
            exit(-1);
        }
        fp = NULL;

        for (i = 0; i < len; ++i) {
            printf("%20s %10d %10.2f\n", angest[i].name, angest[i].personalnummer, angest[i].gehalt);
        }
    }

    errno = 0;
    free(angest);
    angest = NULL;
}

int main(void) {

    angestellter angestellte[] = {
            {"Hans", 1, 1},
            {"Dieter",  5, 34.5},
            {"Haensel",  23, 324},
            {"Gretel",  1, 234},
            {"Max",  92, 24}};

    binaer_speichern(angestellte, 5);
    binaer_laden_und_ausgeben();


    return 0;
}
