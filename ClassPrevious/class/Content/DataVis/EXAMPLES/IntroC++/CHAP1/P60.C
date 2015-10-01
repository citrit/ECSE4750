/*#################################################################
# Copyright (c) 1991 AT&T Bell Laboratories, All Rights Reserved   
# Published in ``A C++ Primer''  by Stanley Lippman, Addison-Wesley
#################################################################*/

#include <iostream.h>
#include "IntArr.h"
#include "IARC.h"

void swap( IntArray &ia, int i, int j )
{ 
     int tmp = ia[ i ];
     ia[ i ] = ia[ j ];
     ia[ j ] = tmp;
}

main() {
    IntArray ia1;
    IntArrayRC ia2;

    // error: should be size-1
    cout << "swap() with IntArray ia1\n";
    swap( ia1, 1, ia1.getSize() );

    cout << "swap() with IntArrayRC ia2\n";
    swap( ia2, 1, ia2.getSize() );

    return 0;
}
