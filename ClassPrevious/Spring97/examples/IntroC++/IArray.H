/*#################################################################
# Copyright (c) 1991 AT&T Bell Laboratories, All Rights Reserved   
# Published in ``A C++ Primer''  by Stanley Lippman, Addison-Wesley
#################################################################*/

#ifndef INT_ARRAY_H
#define INT_ARRAY_H
#include <stdlib.h>
#include <stream.h>

const int ArraySize = 12; // default size

class IntArray {
public: 
// operations performed on arrays
    IntArray(int sz = ArraySize) { init(0,sz); }
    IntArray(const int *iar, int sz) { init(iar,sz); }
    IntArray( const IntArray &iA) { init(iA.ia,iA.size); }
    ~IntArray() { delete ia; }
    IntArray& operator=(const IntArray&);
    virtual int& operator[](int ix); 
    int getSize() { return size; }
protected: 
    void init( const int*, int );
// internal data representation
    int size;  
    int *ia;
};

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
#endif
