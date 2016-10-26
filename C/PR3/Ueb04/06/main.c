#include <time.h>
#include <stdio.h>

int main(void){
time_t times[] = {645703200, 49888800};

printf("%s\n", asctime(localtime(&times[0])));
printf("%s\n", asctime(localtime(&times[1])));

return 0;
}
