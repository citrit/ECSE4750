/*#################################################################
# Copyright (c) 1991 AT&T Bell Laboratories, All Rights Reserved   
# Published in ``A C++ Primer''  by Stanley Lippman, Addison-Wesley
#################################################################*/

// declares two distinct Enumeration types
enum TestStatus { not_run=-1, fail, pass };
enum Boolean { false, true };

main() 
{
    const testSize = 100;
    TestStatus testSuite[ testSize ];
    Boolean found = false;

    for ( int i = 0; i < testSize; ++i )
          testSuite[ i ] = not_run;

    return 0;
}
