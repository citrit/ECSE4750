/*#################################################################
# Copyright (c) 1991 AT&T Bell Laboratories, All Rights Reserved   
# Published in ``A C++ Primer''  by Stanley Lippman, Addison-Wesley
#################################################################*/
#include <iostream.h>

main() {
    // a second more general solution
    int value = 2;
    int pow = 10;

    cout << value 
         << " raised to the power of "
         << pow << ": \t";

    for ( int i = 1, res = 1; i <= pow; ++i ) {
	 res = res * value;
    }

    cout << res << endl;
    return 0;
}
