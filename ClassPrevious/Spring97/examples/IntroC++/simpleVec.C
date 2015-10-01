#include <iostream.h>
#include <stdlib.h>

#include "VectorT.H"


void main(int argc, char *argv[])
{

  VectorType<float> floatVec;
  VectorType<int>   intVec;

  for (int i=0;i<10;i++) {
    floatVec += rand();
    intVec += i;
  }

  cout << "Floats\t\tInts" << endl;
  for (i=0;i<floatVec.Count();i++) {
    cout << "floatVec[" << i << "]: " << floatVec[i] << "\t" 
         << "intVec[" << i << "]: " << intVec[i] << endl;
  }
}
    
/* Output:


a.out
Floats		Ints
floatVec[0]: 16838	intVec[0]: 0
floatVec[1]: 5758	intVec[1]: 1
floatVec[2]: 10113	intVec[2]: 2
floatVec[3]: 17515	intVec[3]: 3
floatVec[4]: 31051	intVec[4]: 4
floatVec[5]: 5627	intVec[5]: 5
floatVec[6]: 23010	intVec[6]: 6
floatVec[7]: 7419	intVec[7]: 7
floatVec[8]: 16212	intVec[8]: 8
floatVec[9]: 4086	intVec[9]: 9

*/
