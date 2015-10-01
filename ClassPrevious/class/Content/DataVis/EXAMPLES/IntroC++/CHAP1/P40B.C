/*#################################################################
# Copyright (c) 1991 AT&T Bell Laboratories, All Rights Reserved   
# Published in ``A C++ Primer''  by Stanley Lippman, Addison-Wesley
#################################################################*/

main() 
{
    TestStatus test = not_run;
    Boolean found = false;

    test = -1; // error: TestStatus = int
    test = 10; // error: TestStatus = int

    test = found; // error: TestStatus = Boolean
    test = false; // error: TestStatus = const Boolean

    int st = test; // ok: implicit conversion
    return 0;
}
