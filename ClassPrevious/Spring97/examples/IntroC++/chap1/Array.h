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

#endif
