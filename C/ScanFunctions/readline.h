
#ifndef CLIENT_READLINE_H
#define CLIENT_READLINE_H

#include <unistd.h>
#include <errno.h>

int readline(int fd, char *buffer, unsigned int maxlen);

#endif //CLIENT_READLINE_H
