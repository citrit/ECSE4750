#include <iostream.h>
#include <stdlib.h>

#include "VectorT.H"

typedef VectorType<float> floatVecType;

void main(int argc, char *argv[])
{

  VectorType<floatVecType> floatArr;

  for (int i=0;i<10;i++) {
    for (int j=0;j<(rand()%10);j++) {
      floatArr[i] += float(i+j);
    }
  }

  cout << "Float Array" << endl;
  for (i=0;i<floatArr.Count();i++) {
    for (int j=0;j<floatArr[i].Count();j++) {
      cout << floatArr[i][j] << "\t";
    }
    cout << endl;
  }
}

/* Output:

a.out
Float Array
0	1	2	3	
1	
2	3	4	5	6	

4	5	6	
5	6	7	8	9	
6	7	
7	8	9	10	11	
8	

*/
