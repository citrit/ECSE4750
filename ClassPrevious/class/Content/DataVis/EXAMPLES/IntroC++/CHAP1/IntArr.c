/*#################################################################
# Copyright (c) 1991 AT&T Bell Laboratories, All Rights Reserved   
# Published in ``A C++ Primer''  by Stanley Lippman, Addison-Wesley
#################################################################*/

#include <stdlib.h>
#include <stream.h>
#include "IntArr.h"

#include <assert.h>

void IntArray::init(const int *array, int sz)
{
    ia = new int[size=sz]; 
    assert( ia != 0 );

    for (int ix = 0; ix < size; ++ix)
         ia[ix] = (array!=0) ? array[ix] : 0;
}

IntArray& IntArray::operator=( const IntArray &iA )
{
    // test for assignment to itself: iA = iA
    if (this == &iA) return *this;

    delete ia; // free up existing memory
    init( iA.ia, iA.size );

    return *this;
}

int& 
IntArray::operator[](int index) { 
    return ia[ index ]; 
}
