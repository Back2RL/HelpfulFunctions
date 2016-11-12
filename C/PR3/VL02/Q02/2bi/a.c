/* Datei a.c */

#include "b.h"
#include <stdio.h>

void a1(void) {
	printf("%s\n", "a1");
}
int a2(int x) {
	printf("%s %d\n", "a2", b());
	return -x;
}

