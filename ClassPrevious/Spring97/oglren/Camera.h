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

#ifndef _CAMERA_H_
#define _CAMERA_H_

#include "PObject.h"
#include "PointSet.h"
#include "Matrix.h"

class Renderer;

class CameraBC : public ParentObject {
  public:
    CameraBC() { Mat.Translate(0.0, 0.0, -10.0);
	        ClipRange[0] = 0.1; ClipRange[1] = 10000.0;
	        eyeAngle = 45; }
    char *ObjectType() { return "Camera"; }
    void SetEyeAngle(float angle) { eyeAngle = angle; }
    virtual void Initialize() = 0;
    virtual void Render(Renderer *aren) = 0;
    virtual void Rotate(float angle, float x, float y, float z) = 0;
    virtual void Translate(float x, float y, float z) = 0;
    virtual void Zoom(float amount) = 0;
    virtual void SetPosition(float x, float y, float z,
			     float cx, float cy, float cz,
			     float ux, float uy, float uz) = 0;
    virtual void SetROI(double *roi) = 0;
    virtual void Reshape(Renderer *aren) = 0;

  protected:
    Matrix Mat;
    float ClipRange[2];
    float eyeAngle;
  };

#endif
