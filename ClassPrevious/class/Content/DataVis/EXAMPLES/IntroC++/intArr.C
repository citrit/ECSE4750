#include <iostream.h>

#include "IArray.H"

int main(int argc, char *argv[])
{

  IntArray intArr(10);

  for (int i=0;i<10;i++) {
    intArr[i] = int(rand());
  }

  for (i=0;i<10;i++) {
    cout << "intArr[" << i << "]: " << intArr[i] << endl;
  }
}


/*  Output:

a.out
intArr[0]: 16838
intArr[1]: 5758
intArr[2]: 10113
intArr[3]: 17515
intArr[4]: 31051
intArr[5]: 5627
intArr[6]: 23010
intArr[7]: 7419
intArr[8]: 16212
intArr[9]: 4086

*/
