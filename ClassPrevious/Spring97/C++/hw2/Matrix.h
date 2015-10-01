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


#ifndef _MATRIX_H_
#define _MATRIX_H_

#include "PObject.h"

class Matrix : public ParentObject {
 public:
  Matrix () { Identity();PreMultiplyFlag = 1; }
  ~Matrix () { }
  void Identity ();
  void RotateX ( float angle);
  void RotateY ( float angle);
  void RotateZ (float angle);
  void RotateWXYZ ( float angle, float x, float y, float z);
  void Scale ( float x, float y, float z);
  void Translate ( float x, float y, float z);
  void GetPosition (float& x, float& y, float& z);
  void Concatenate(Matrix &matrix);
  void Multiply(Matrix &a, Matrix &b, Matrix &c);
  float *GetRow(const unsigned int i) {return &(Mat[i][0]);};
  float *GetMatrix() { return (float *)&Mat;  }
  Matrix& operator=(float num);
 private:
  float Mat[4][4];
  int   PreMultiplyFlag;

};

#endif
