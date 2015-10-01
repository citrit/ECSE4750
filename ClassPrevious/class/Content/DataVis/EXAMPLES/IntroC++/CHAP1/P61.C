/*#################################################################
# Copyright (c) 1991 AT&T Bell Laboratories, All Rights Reserved   
# Published in ``A C++ Primer''  by Stanley Lippman, Addison-Wesley
#################################################################*/

#include <iostream.h>
#include "Array.h"

main() {
    // instantiate particular template instances
    Array<int> ia(4);     // integer instance	
    Array<double> da(4);  // double instance
    Array<char> ca(4);    // char instance

    int ix;
    for ( ix = 0; ix < ia.getSize(); ++ix ) {
	ia[ix] = ix;	
	da[ix] = ix * 1.75;
	ca[ix] = ia[ix] + 97;
    }

    for ( ix = 0; ix < ia.getSize(); ++ix ) 
	cout << "[ " << ix << " ]  ia: "  << ia[ix] 
	     << "\tca: " << ca[ix] 
	     << "\tda: " << da[ix] << endl;
    
    return 0;
}
