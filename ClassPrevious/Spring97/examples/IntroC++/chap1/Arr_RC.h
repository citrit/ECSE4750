/*#################################################################
# Copyright (c) 1991 AT&T Bell Laboratories, All Rights Reserved   
# Published in ``A C++ Primer''  by Stanley Lippman, Addison-Wesley
#################################################################*/

#ifndef ARRAYRC_H
#define ARRAYRC_H
#include <assert.h>
#include "Array.h"

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
