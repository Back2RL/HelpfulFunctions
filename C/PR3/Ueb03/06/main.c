#include <stdio.h>
#include <string.h>

#define INT20_LEN 20

struct int20 {
	char zahl[INT20_LEN];
};

struct int20 create20(char val[]){
	int len = strlen(val);
	struct int20 a;
	int i = 0;

	int emptySpace = INT20_LEN - len;

	/* String mit führenden Nullen auffüllen */
	for(i = 0; i < emptySpace; ++i){
		a.zahl[i] = '0';
	}

	/* String mit den Ziffern auffüllen */
	for(i = 0; i+emptySpace < INT20_LEN; ++i){
		a.zahl[i+emptySpace] = val[i];
	}

	return a;
}

void print20(struct int20 a){
	int c,i = 0;

	/* führende Nullen überlesen */
	for (i = 0; i < INT20_LEN && a.zahl[i] == '0'; ++i);
	/* falls das Ende erreicht wurde ist die Zahl 0 -> Ausgabe */
	if(i == INT20_LEN){
		printf("0");
		return;
	}
	
	/* Ausgabe der Ziffern */
	for(c = 0; c+i < INT20_LEN;++c){
		printf("%c", a.zahl[c+i]);
	} 
}

struct int20 add20(struct int20 a, struct int20 b){
	struct int20 result;
	int i = 0;
	/* der Übertrag der beim Addieren entsteht */
	int over = 0;
	for(i = INT20_LEN - 1; i >= 0; --i){
		/* char in Integer umwandeln */
		int cAsIntA = a.zahl[i] - '0';
		int cAsIntB = b.zahl[i] - '0';
		
		/* Zahlen addieren und vorherigen Übertrag aufaddieren */
		int sum = cAsIntA + cAsIntB + over; 
		
		/* Summe mod 10 und in char konvertieren */ 
		result.zahl[i] = (sum % 10) + '0';
		
		/* neuen Übertrag berechnen */
		over = sum / 10; 
	}

	return result;
}

int main(void){
	{
		struct int20 a= create20("12345678901234567890");
		struct int20 b= create20("100");
		struct int20 sum= add20(a, b);
		print20(a); printf("\n");
		print20(b); printf("\n");
		print20(sum); printf("\n");
	}
	{
		struct int20 a= create20("9700");
		struct int20 b= create20("422");
		struct int20 sum= add20(a, b);
		print20(a); printf("\n");
		print20(b); printf("\n");
		print20(sum); printf("\n");
	}

	return 0;
}
