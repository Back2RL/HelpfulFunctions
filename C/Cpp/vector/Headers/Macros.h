//
// Created by Leo on 24.01.2017.
//

#ifndef VECTOR_MACROS_H
#define VECTOR_MACROS_H

#define delete_safe(a) delete a; a = nullptr;
#define delete_safe_array(a) delete[] a; a = nullptr; printf("Memory deallocated\n");


#endif //VECTOR_MACROS_H
