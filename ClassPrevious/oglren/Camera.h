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
    CameraBC() { ClipRange[0] = 0.1; ClipRange[1] = 10000.0;
	        eyeAngle = 35;
			From.z = 50;Up.y = 1;
			InitLess = true;
	}
    char *ObjectType() { return "Camera"; }
    void SetEyeAngle(float angle) { eyeAngle = angle; }
    virtual void Initialize() = 0;

    virtual void Render(Renderer *aren) = 0;

    virtual void Rotate(float angle, float x, float y, float z) = 0;
    virtual void Translate(float x, float y, float z) = 0;
    virtual void Zoom(float amount) = 0;

    virtual void SetPosition(float x, float y, float z,
			     float cx, float cy, float cz,
				 float ux, float uy, float uz) {
			From.x = x; From.y = y;From.z = z;
			To.x = cx; To.y = cy; To.z = cz;
			Up.x = ux; Up.y = uy; Up.z = uz;
			InitLess = true;
	}

	virtual void PrintSelf(ostream &os) {
			ParentObject::PrintSelf(os);
			os << "Clipping Range: " << ClipRange[0] << ", " << ClipRange[1] << endl
			<< "Eye ange: " << eyeAngle << endl;
	}

    virtual void SetROI(double *roi) = 0;
    virtual void Reshape(Renderer *aren) = 0;

  protected:
	Renderer *renderer;
	int Width, Height;
    float ClipRange[2];
    float eyeAngle;
	PointType From, To, Up;
	bool InitLess;

    void SetPosition();
 };

#endif
