#include <iostream.h>
#include <math.h>

typedef struct Point {
  float x, y, z;
};


Point& GetPoint (Point Pt1, Point Pt2,
		 float Sc1, float Sc2,
		 float val) {

  static Point res;
  float mins, maxs, normv;

  if (Sc1 < Sc2) {
    mins = Sc1;
    maxs = Sc2;
  }
  else {
    mins = Sc2;
    maxs = Sc1;
  }

  val = ((val<mins)?mins:val);
  val = ((val>maxs)?maxs:val);

  normv = (val - Sc1) / (Sc2 - Sc1);

//  if (Sc1 < Sc2) {
    res.x = ((Pt2.x - Pt1.x)* normv) + Pt1.x;
    res.y = ((Pt2.y - Pt1.y)* normv) + Pt1.y;
    res.z = ((Pt2.z - Pt1.z)* normv) + Pt1.z;
//  }
//  else {
//    res.x = ((Pt1.x - Pt2.x)* normv) + Pt2.x;
//    res.y = ((Pt1.y - Pt2.y)* normv) + Pt2.y;
//    res.z = ((Pt1.z - Pt2.z)* normv) + Pt2.z;
//  }

  return res;
}

int
main(int argc, char *argv[]) {

  Point from, to, res;
  float val, sc1, sc2;

  while (1) {
    cout << "Input From X Y Z Scalar: ";
    cin >> from.x >> from.y >> from.z >> sc1;
    cout << "Input To   X Y Z Scalar: ";
    cin >> to.x >> to.y >> to.z >> sc2;
    cout << "Value to look for: ";
    cin >> val;
    res = GetPoint(from, to, sc1, sc2, val);
    cout << "Result: (" << res.x << "," << res.y << "," << res.z << ") " 
      << endl;
  }

  return 0;
}
