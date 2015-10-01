/*#################################################################
# Copyright (c) 1991 AT&T Bell Laboratories, All Rights Reserved   
# Published in ``A C++ Primer''  by Stanley Lippman, Addison-Wesley
#################################################################*/
#include <iostream.h>

void read2( int&, int& );
int max( int, int );
void writeMax( int ); 

main() {
  int val1, val2;

  read2( val1, val2 );
  int maxVal = max( val1, val2 );
  writeMax( maxVal );
  return 0;
}

void read2( int& v1, int& v2) {
  cout << "\nPlease enter two numeric values: ";
  cin >> v1 >> v2;
}

int max( int v1, int v2) {
    if ( v1 > v2 )
         return v1;
    else
         return v2;
}

void writeMax( int val ) {
  cout << val << " is the largest value.\n";
}
