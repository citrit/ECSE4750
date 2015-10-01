/*#################################################################
# Copyright (c) 1991 AT&T Bell Laboratories, All Rights Reserved   
# Published in ``A C++ Primer''  by Stanley Lippman, Addison-Wesley
#################################################################*/
#include <iostream.h>

main() {
    char ch;
    int lineCnt=0, charCnt=0;
     
    while ( cin.get(ch) ) {
       switch ( ch ) {
         case '\t':
         case ' ':
              break;
         case '\n':
              ++lineCnt; 
              break;
         default:
              ++charCnt; 
              break;
         }
     }    
    cout << lineCnt << " " << charCnt << "\n";
    return 0;
}
