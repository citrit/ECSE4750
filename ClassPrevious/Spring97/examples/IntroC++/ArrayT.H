/*#################################################################
# Copyright (c) 1991 AT&T Bell Laboratories, All Rights Reserved   
# Published in ``A C++ Primer''  by Stanley Lippman, Addison-Wesley
#################################################################*/

#ifndef ARRAY_H
#define ARRAY_H

const int ArraySize = 12; 

template <class Type>
class Array {
public: 
    Array(int sz=ArraySize) 
        { size=sz; ia=new Type[ size ]; }
    virtual ~Array() { delete [] ia; }

    int getSize() { return size; }	
    virtual Type& 
        operator[](int index) { return ia[index]; }
protected: 
    int size;  
    Type *ia;
};

#include <assert.h>

template <class Type>
class ArrayRC : public Array<Type> {
public:
   ArrayRC(int sz = ArraySize) : Array<Type>( sz ){};
   Type& operator[](int index) { 
         assert( index >= 0 && index < size );
         return ia[ index ];
    }
};

#endif
