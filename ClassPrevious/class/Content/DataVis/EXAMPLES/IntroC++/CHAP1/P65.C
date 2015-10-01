/*#################################################################
# Copyright (c) 1991 AT&T Bell Laboratories, All Rights Reserved   
# Published in ``A C++ Primer''  by Stanley Lippman, Addison-Wesley
#################################################################*/
#include <iostream.h>

char *winter[ 3 ];
char *spring[] = { "March", "April", "May" };
char *cruellestMonth = spring[ 1 ];

main() {
  cout << "Lilacs breed in " << spring[ 1 ];
  cout << "Lilacs breed in " 
       << cruellestMonth;

  return 0;
}
