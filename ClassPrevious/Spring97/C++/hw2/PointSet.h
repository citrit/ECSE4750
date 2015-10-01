//////////////////////////////////////////////////////////////////////////
//
//  This code is for instructional purposes only. It was generated for
//  use in a graduate level course to show certain aspects of data
//  storage algorithms. It has problems and should not be used
//  outside the class environment.
//
//  Author:  Thomas D. Citriniti     citrit@rpi.edu
//  Class:   Advanced Computer Graphics and Data visualization
//           Rensselaer Polytechnic Institute
//  Date:    December 1996
//
//////////////////////////////////////////////////////////////////////////


#ifndef _POINT_SET_H_
#define _POINT_SET_H_

#include "PObject.h"
#include "VectorT.h"

#ifndef MIN
#define MIN(a,b) (a<b?a:b)
#define MAX(a,b) (a>b?a:b)
#endif

class PointType : public ParentObject {
  public:
    double x, y, z;

    char *ObjectType() { return "PointType"; }
    PointType() { x = y = z = 0; }
    PointType(float a, float b, float c = 0.0) { x = a; y = b; z = c; }
    PointType(float pt[3])
      { x = pt[0]; y = pt[1]; z = pt[2]; }
    PointType& operator=(float pt[3])
      { x = pt[0]; y = pt[1]; z = pt[2]; return *this; }
};

class PointSet : public VectorType<PointType *> {
  private:
  protected:
    double BBox[6];
  public:
    PointSet() {
      BBox[0] = BBox[1] = BBox[2] = 10000000.0;
      BBox[3] = BBox[4] = BBox[5] = -10000000.0;
    }
    char *ObjectType() { return "PointSet"; }
    PointSet& operator+=(PointType *datum) {
      Reserve(Used+1);
      Data[Used]=datum;
      Used++;
      BBox[0] = MIN(BBox[0],datum->x);
      BBox[1] = MIN(BBox[1],datum->y);
      BBox[2] = MIN(BBox[2],datum->z);
      BBox[3] = MAX(BBox[3],datum->x);
      BBox[4] = MAX(BBox[4],datum->y);
      BBox[5] = MAX(BBox[5],datum->z);
      UpdateTime();
      return *this;
    }
    double *GetBBox() { return BBox; }
};
#endif
