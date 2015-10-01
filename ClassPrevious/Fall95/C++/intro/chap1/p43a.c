/*#################################################################
# Copyright (c) 1991 AT&T Bell Laboratories, All Rights Reserved   
# Published in ``A C++ Primer''  by Stanley Lippman, Addison-Wesley
#################################################################*/

const int array_size = 3;
int ix, jx, kx;
int &iar[] = { ix, jx, kx }; // error

int ia[] = { 0, 1, 2 }; // ok
int ia2[] = ia; // error

main() {
    int ia3[ array_size ]; // ok
    ia3 = ia; // error
    return 0;
}
