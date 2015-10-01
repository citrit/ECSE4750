#include <iostream.h>
#include <stdlib.h>

main(int argc, char *argv[]) {
  int dimx = 2, dimy = 4; 
  int cnt = 0;

  if (argc > 1) {
    dimx = atoi(argv[1]);
    dimy = atoi(argv[2]);
  }
  for (int i = 0;i < dimy-1; i++) {
    for (int j = 0;j < dimx;j++) {
      if (cnt%dimx != (dimx-1))
	cout << "[" << cnt << "," << cnt+1 << "," << cnt+dimx+1 << ","
	     << cnt+dimx << "] ";
      cnt++;
    }
    cout << endl;
  }
  for (i = 0;i < dimy*dimx; i++) {
    cout << "Pt: " << i << " [" << i%dimx << "," << i/dimx << "]" << endl;
  }


  return(0);
}
