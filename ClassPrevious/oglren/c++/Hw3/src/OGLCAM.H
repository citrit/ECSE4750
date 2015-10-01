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

#ifndef _OGLRCAMERA_H_
#define _OGLRCAMERA_H_

#include <GL/gl.h>
#include <GL/glu.h>
#include "Camera.H"
#include "Renderer.H"

class OGLCamera : public Camera {
  public:

    OGLCamera(); 
    void Initialize();
    void Render(Renderer *aren);
    void Rotate(float angle, float x, float y, float z);
    void Translate(float x, float y, float z);
    void Zoom(float amount);
    void SetPosition(float x, float y, float z,
		     float cx, float cy, float cz,
		     float ux, float uy, float uz);
    void Reshape(Renderer *aren);
  };


#endif
