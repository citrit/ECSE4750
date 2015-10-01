/*#################################################################
# Copyright (c) 1991 AT&T Bell Laboratories, All Rights Reserved   
# Published in ``A C++ Primer''  by Stanley Lippman, Addison-Wesley
#################################################################*/
#include <iostream.h>

extern int isOdd( int );
extern void read2( int&, int& );

main() 
{
    int v1, v2;      // hold values from user

    read2( v1, v2 ); 

    cout << v1;
    if ( isOdd( v1 ) == 1 )
	cout << " is odd\n";
    else 
	cout << " is even\n";

    cout << v2; 
    if ( isOdd( v2 ) == 1 )
	cout << " is odd\n";
    else
	cout << " is even\n";

    return 0;
}

int isOdd( int val ) 
{ 
/* return 1 if val is odd; otherwise, return 0
 * % is the modulus operator: 3 % 2 yields 1. 
 */
     return(val % 2 != 0); 
}

void read2( int& v1, int& v2) 
{
     cout << "\nPlease enter two numeric values: ";
     cin >> v1 >> v2;
}

