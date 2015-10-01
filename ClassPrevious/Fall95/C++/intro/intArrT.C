#include <iostream.h>
#include <stdlib.h>

#include "ArrayT.H"

int main(int argc, char *argv[])
{

  Array<int> intArr(10);
  ArrayRC<int> intArrRC(10);

  for (int i=0;i<10;i++) {
    intArr[i] = int(rand());
    intArrRC[i] = int(rand());
  }

  for (i=0;i<=13;i++) {
    cout << "intArr[" << i << "]: " << intArr[i] << endl;
    cout << "intArrRC[" << i << "]: " << intArrRC[i] << endl;
  }
}

/*  Output:

a.out
intArr[0]: 16838
intArrRC[0]: 5758
intArr[1]: 10113
intArrRC[1]: 17515
intArr[2]: 31051
intArrRC[2]: 5627
intArr[3]: 23010
intArrRC[3]: 7419
intArr[4]: 16212
intArrRC[4]: 4086
intArr[5]: 2749
intArrRC[5]: 12767
intArr[6]: 9084
intArrRC[6]: 12060
intArr[7]: 32225
intArrRC[7]: 17543
intArr[8]: 25089
intArrRC[8]: 21183
intArr[9]: 25137
intArrRC[9]: 25566
intArr[10]: 0
Assertion failed: index >= 0 && index < size, file  ArrayT.H, line 33
IOT/Abort trap (core dumped)

*/
