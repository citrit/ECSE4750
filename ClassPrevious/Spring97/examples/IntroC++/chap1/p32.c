/*#################################################################
# Copyright (c) 1991 AT&T Bell Laboratories, All Rights Reserved   
# Published in ``A C++ Primer''  by Stanley Lippman, Addison-Wesley
#################################################################*/
#include <iostream.h>

void stringLength( char *st ) { 
    int len = 0;
    char *p = st;

    while ( *p++ ) ++len;
    cout << len << ": " << st ;
}

char *st = "The expense of spirit\n";
main() {
    stringLength( st );
    return 0;
}
