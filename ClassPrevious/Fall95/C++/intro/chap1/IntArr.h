/*#################################################################
# Copyright (c) 1991 AT&T Bell Laboratories, All Rights Reserved   
# Published in ``A C++ Primer''  by Stanley Lippman, Addison-Wesley
#################################################################*/

#ifndef INT_ARRAY_H
#define INT_ARRAY_H
#include <stdlib.h>

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
#endif
