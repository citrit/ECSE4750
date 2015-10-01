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

#include <math.h>
#include "Matrix.h"

#ifndef M_PI
#define M_PI 3.14159265358979323846264338327950288
#endif

#define DEGTORADS M_PI / 180.0

void Matrix::Identity () {
  for (int i=0;i<4;i++)
    for (int j=0;j<4;j++) 
      this->Mat[i][j] = ((i==j)?1.0:0.0);
}

void Matrix::RotateX ( float angle) {
  float radians = angle * DEGTORADS;
  float cosAngle, sinAngle;

  if (angle != 0.0) {
    cosAngle = cos (radians);
    sinAngle = sin (radians);

    Mat[0][0] = 1.0;
    Mat[1][1] =  cosAngle;
    Mat[2][1] =  sinAngle;
    Mat[1][2] = -sinAngle;
    Mat[2][2] =  cosAngle;
    Mat[3][3] = 1.0;
  }
}
  
void Matrix::RotateY ( float angle) {
  float radians = angle * DEGTORADS;
  float cosAngle, sinAngle;

  if (angle != 0.0) {
    cosAngle = cos (radians);
    sinAngle = sin (radians);

    Mat[0][0] = cosAngle;
    Mat[1][1] = 1.0;
    Mat[2][0] = -sinAngle;
    Mat[0][2] = sinAngle;
    Mat[2][2] = cosAngle;
    Mat[3][3] = 1.0;
  }
}
  
void Matrix::RotateZ (float angle) {
  float radians = angle * DEGTORADS;
  float cosAngle, sinAngle;

  if (angle != 0.0) {
    cosAngle = cos (radians);
    sinAngle = sin (radians);

    Mat[0][0] =  cosAngle;
    Mat[1][0] =  sinAngle;
    Mat[0][1] = -sinAngle;
    Mat[1][1] =  cosAngle;
    Mat[2][2] = 1.0;
    Mat[3][3] = 1.0;
  }
}

#define NORM(x) sqrt(x[0]*x[0] + x[1]*x[1] + x[2]*x[2])

void Matrix::RotateWXYZ ( float angle, float x, float y, float z) {
  float   radians;
  float   w;
  float   vec[4];
  float   sinAngle;
  float   cosAngle;
  float   den;

  // build a rotation matrix and concatenate it
  vec[0] = x;
  vec[1] = y;
  vec[2] = z;

  // convert degrees to radians
  radians = angle * DEGTORADS / 2.0;

  cosAngle = cos (radians);
  sinAngle = sin (radians);

  // normalize x, y, z
  if ( (den = NORM(vec)) != 0.0 ) {
    for (int i=0;i<3;i++)
      vec[i] /= den;
  }
  else {
    cerr << "Trying to rotate around zero length axis" << endl;
    return;
  }

  w = cosAngle;
  x = vec[0] * sinAngle;
  y = vec[1] * sinAngle;
  z = vec[2] * sinAngle;

  // matrix calculation is taken from Ken Shoemake's
  // "Animation Rotation with Quaternion Curves",
  // Comput. Graphics, vol. 19, No. 3 , p. 253

  Mat[0][0] = 1 - 2 * y * y - 2 * z * z;
  Mat[1][1] = 1 - 2 * x * x - 2 * z * z;
  Mat[2][2] = 1 - 2 * x * x - 2 * y * y;
  Mat[1][0] =  2 * x * y + 2 * w * z;
  Mat[2][0] =  2 * x * z - 2 * w * y;
  Mat[0][1] =  2 * x * y - 2 * w * z;
  Mat[2][1] =  2 * y * z + 2 * w * x;
  Mat[0][2] =  2 * x * z + 2 * w * y;
  Mat[1][2] =  2 * y * z - 2 * w * x;

}

void Matrix::Scale ( float x, float y, float z) {

  if (x != 1.0 || y != 1.0 || z != 1.0)
    {
    Mat[0][0] = x;
    if (Mat[0][0] == 0.0)
      {
      Mat[0][0] = 1.0;
      }

    Mat[1][1] = y;
    if (Mat[1][1] == 0.0)
      {
      Mat[1][1] = 1.0;
      }

    Mat[2][2] = z;
    if (Mat[2][2] == 0.0)
      {
      Mat[2][2] = 1.0;
      }

    Mat[3][3] = 1.0;
  }

}

void Matrix::Translate ( float x, float y, float z) {

  if (x != 0.0 || y != 0.0 || z != 0.0) {
    Mat[0][0] = 1.0;
    Mat[1][1] = 1.0;
    Mat[2][2] = 1.0;
    Mat[3][3] = 1.0;

    Mat[0][3] = x;
    Mat[1][3] = y;
    Mat[2][3] = z;
  }
}


void Matrix::GetPosition (float& x, float& y, float& z) {

  x = Mat[0][3];
  y = Mat[1][3];
  z = Mat[2][3];
}

void Matrix::Concatenate (Matrix &matrix)
{
  if (this->PreMultiplyFlag) 
    {
    this->Multiply (*this, matrix, *this);
    }
  else 
    {
    this->Multiply (matrix, *this, *this);
    }
  this->UpdateTime ();
}

// Description:
// Multiplies matrices a and b and stores the result in c.
void Matrix::Multiply ( Matrix &a, Matrix &b, Matrix &c)
{
  int i, j, k;
  Matrix result;

  result = 0.0;
  for (i = 0; i < 4; i++) 
    {
    for (k = 0; k < 4; k++) 
      {
      for (j = 0; j < 4; j++) 
        {
        result.Mat[i][k] += a.Mat[i][j] * b.Mat[j][k];
        }
      }
    }
  c = result;
}

Matrix& Matrix::operator=(float num) {
  for (int i=0;i<4;i++)
    for (int j=0;j<4;j++)
      this->Mat[i][j] = num;
  return *this;
}

