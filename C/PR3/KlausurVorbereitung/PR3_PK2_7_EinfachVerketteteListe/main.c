#include <stdio.h>
#include <malloc.h>

struct node {
    int value;
    struct node *next;
};

struct node *create(int value) {
    struct node *newNodePtr = (struct node *) malloc(sizeof(struct node));
    newNodePtr->next = NULL;
    newNodePtr->value = value;
    return newNodePtr;
}

int insert_head(struct node **head, struct node *toInsert) {
    if (head == NULL || toInsert == NULL) return -1;
    if (*head == NULL) {
        *head = toInsert;
    } else {
        struct node *temp = *head;
        *head = toInsert;
        toInsert->next = temp;
    }
    return 0;
}

void print(struct node *head) {

    struct node *it = head;
    if (it != NULL) {
        printf("%d", it->value);
        it = it->next;
    }

    while (it != NULL) {
        printf(" %d", it->value);
        it = it->next;
    }
    printf("\n");
}

int main(void) {
    struct node *head = NULL; /* leere Liste */
    int error = insert_head(&head, create(5));
    error |= insert_head(&head, create(3));
    error |= insert_head(&head, create(1));
    if (error) {
        printf("Fehler\n");
    } else {
        print(head); /* Gewünschte Ausgabe: 1 3 5 */
    }
    return 0;
}

