/*#################################################################
# Copyright (c) 1991 AT&T Bell Laboratories, All Rights Reserved   
# Published in ``A C++ Primer''  by Stanley Lippman, Addison-Wesley
#################################################################*/

#include <iostream.h>
#include "Array.h"
#include "Arr_RC.h"

template <class Type>
void swap( Array<Type> &array, int i, int j ) { 
     int tmp = array[ i ];
     array[ i ] = array[ j ];
     array[ j ] = tmp;
}

main() {
    Array<int> ia1;
    ArrayRC<int> ia2;

    cout << "swap() with IntArray<int> ia1\n";
    int size = ia1.getSize();
    swap( ia1, 1, size );

    cout << "swap() with IntArrayRC<int> ia2\n";
    size = ia2.getSize();
    swap( ia2, 1, size );

    return 0;
}
