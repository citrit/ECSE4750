/*#################################################################
# Copyright (c) 1991 AT&T Bell Laboratories, All Rights Reserved   
# Published in ``A C++ Primer''  by Stanley Lippman, Addison-Wesley
#################################################################*/

#include <iostream.h>
#include <stdlib.h>
#include "IARC.h"

#include <assert.h>

int& 
IntArrayRC::operator[]( int index )
{
    assert( index >= 0 && index < size );
    return ia[ index ];
}

void 
IntArrayRC::rangeCheck( int index ) { 
    if ( index < 0 || index >= size ) { 
        cerr << "Index out of bounds for IntArrayRC: "
             << "\n\tsize: " << size
             << "\tindex: " << index << endl;
        exit( -1 );
    }
}

IntArrayRC::IntArrayRC(int sz) 
    : IntArray( sz ) {} 

IntArrayRC::IntArrayRC(const int *iar, int sz) 
    : IntArray( iar, sz ) {} 
