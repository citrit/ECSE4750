/*#################################################################
# Copyright (c) 1991 AT&T Bell Laboratories, All Rights Reserved   
# Published in ``A C++ Primer''  by Stanley Lippman, Addison-Wesley
#################################################################*/

#ifndef INT_ARRAY_RC_H
#define INT_ARRAY_RC_H

#include "IntArr.h"

class IntArrayRC : public IntArray {
public:
   IntArrayRC( int = ArraySize );
   IntArrayRC( const int*, int );
   int& operator[]( int );
protected:
   void rangeCheck(int);
};

#endif
