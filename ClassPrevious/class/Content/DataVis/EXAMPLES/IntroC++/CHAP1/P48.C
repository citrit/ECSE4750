/*#################################################################
# Copyright (c) 1991 AT&T Bell Laboratories, All Rights Reserved   
# Published in ``A C++ Primer''  by Stanley Lippman, Addison-Wesley
#################################################################*/

char buf[] = "fiddleferns";

main() {
    char *ptr = 0;
    for (int i=0; buf[i] != '\0'; ++i)
          ptr[i] = buf[i];
    return 0;
}
