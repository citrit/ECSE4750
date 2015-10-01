/*#################################################################
# Copyright (c) 1991 AT&T Bell Laboratories, All Rights Reserved   
# Published in ``A C++ Primer''  by Stanley Lippman, Addison-Wesley
#################################################################*/

#include "IARC.h"

main() 
{
    IntArrayRC ia;

    // subscript error: 1 .. size
    for ( int ix = 1; ix <= ia.getSize(); ++ix )
          ia[ ix ] = ix;
    return 0;
}
