/*#################################################################
# Copyright (c) 1991 AT&T Bell Laboratories, All Rights Reserved   
# Published in ``A C++ Primer''  by Stanley Lippman, Addison-Wesley
#################################################################*/
#include <iostream.h>
char *st = "The expense of spirit\n";

main() 
{
    int len = 0;
    while (*st++ != '\0') ++len;

    cout << len << ": " << st;
    return 0;
}
