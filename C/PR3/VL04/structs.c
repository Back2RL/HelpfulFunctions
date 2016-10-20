/* Deklaration */
struct angestellte {
	char name[NAME_LEN+1];
	int personalsummer;
	float gehalt;
};

/* Definition */

struct angestellte schmitz;
strcpy(schmitz.name, "Schmitz");

/* oder */
struct angestellte hans = {"Schmitz", 1234, 2752.44};


typedef struct test{
	int zahl1;
	int zahl2;
} zahlen;
