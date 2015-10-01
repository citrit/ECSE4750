/*#################################################################
# Copyright (c) 1991 AT&T Bell Laboratories, All Rights Reserved   
# Published in ``A C++ Primer''  by Stanley Lippman, Addison-Wesley
#################################################################*/
#include <iostream.h>

unsigned int 
pow( int val, int exp ) 
{
    // compute val raised to exp power    
    for ( unsigned int res = 1; exp > 0; --exp ) 
          res = res * val;
    return res;
}

main() {
    int val = 2;
    int exp = 16;

    cout << "\nThe Powers of 2\n";
    for ( int i = 0; i <= exp; ++i )
	  cout << i << ": " 
	       << pow( val, i ) << endl;
    return 0;
}
