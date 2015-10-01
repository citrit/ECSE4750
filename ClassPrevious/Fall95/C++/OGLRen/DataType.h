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
//  Date:    October 3, 1995
//
//////////////////////////////////////////////////////////////////////////


#ifndef _DATA_TYPE.h_
#define _DATA_TYPE.h_

#ifndef MIN
#define MIN(a,b) (a<b?a:b)
#define MAX(a,b) (a>b?a:b)
#endif

#include "VectorT.h"
#include "MatSet.h"

template <class T> class DataObject : public VectorType<T> {
  private:
  protected:
    double BBox[2];
  public:
    DataObject() { BBox[0] = 10000000.0; BBox[1] = -10000000.0; }
    ~DataObject() { }
    char* ObjectType() { return "DataObject"; }
    double* GetBBox() { return BBox; }

    DataObject<T>& operator+=(T datum) {
      Reserve(Used+1);
      Data[Used]=datum;
      Used++;
      BBox[0] = MIN(BBox[0], datum);
      BBox[1] = MAX(BBox[1], datum);
      UpdateTime();
      return *this;
    }
    Material* LookupValue(T val) {
      static Material Mat;
      float rgba[4], normv;

      normv = float(val - BBox[0]) / float(BBox[1] - BBox[0]);
      if (normv < 0.0) normv = 0.0;
      if (normv > 1.0) normv = 1.0;
      rgba[0] = normv*0.8;
      rgba[1] = ((normv<0.5)?(2*normv):((1.0-normv)*2));
      rgba[2] = 0.8 - normv;
      rgba[3] = 1.0;
      Mat.SetAmbient(rgba);
      Mat.SetDiffuse(rgba);
      Mat.SetSpecular(rgba);
      return &Mat;
    }
  };

class ScalarInt : public DataObject<int> {
  private:
  protected:
  public:

  };
class ScalarFloat : public DataObject<float> {
  private:
  protected:
  public:

  };

#endif

