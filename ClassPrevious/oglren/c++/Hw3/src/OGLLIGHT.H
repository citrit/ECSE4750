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


#ifndef _OGLRLIGHT_H_
#define _OGLRLIGHT_H_

#include "Light.H"
#include <GL/gl.h>
#include "Renderer.H"

class OGLLight : public Light {
  public:

    OGLLight();
    OGLLight(short num);
    void Render(Renderer *aren);
    void SetMaterial(Material *mat);
  };

#endif
