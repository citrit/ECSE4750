/*#################################################################
# Copyright (c) 1991 AT&T Bell Laboratories, All Rights Reserved   
# Published in ``A C++ Primer''  by Stanley Lippman, Addison-Wesley
#################################################################*/

const int array_size = 7;
int ia1[] = { 0, 1, 2, 3, 4, 5, 6 };

main() {
    int ia2[ array_size ];
    for (int ix = 0; ix < array_size; ++ix )
	ia2[ ix ] = ia1[ ix ];
    return 0;
} 
