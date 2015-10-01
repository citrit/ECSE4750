/*#################################################################
# Copyright (c) 1991 AT&T Bell Laboratories, All Rights Reserved   
# Published in ``A C++ Primer''  by Stanley Lippman, Addison-Wesley
#################################################################*/
#include <stream.h>

int stringLength( char *st ) { 
    int len = 0;
    while ( *st++ ) ++len;
    return len;
}

char *st = "The expense of spirit\n";
main() {
    int len = stringLength( st );
    cout << len << ": " << st;
    return 0;
}
