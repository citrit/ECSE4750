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

#include "OGLLight.h"

OGLLight::OGLLight() { 

  From.z = 1.0;
  Num = 0;
  On = Light::Yes; 

}

OGLLight::OGLLight(short num) { 

  From.z = 1.0;
  Num = num;
  On = Light::Yes; 

}

void
OGLLight::Render(Renderer *aren) {
  GLenum lightnum;

  static float lightPos[4] = {From.x, From.y, From.z, 0.0};
  static float lightDir[4] = {To.x, To.y, To.z, 0.0};

  if (this->On == Light::Yes) {
    glEnable(GL_LI.hTING);
    switch (Num % 8) {
    case 0: lightnum = GL_LI.hT0; break;
    case 1: lightnum = GL_LI.hT1; break;
    case 2: lightnum = GL_LI.hT2; break;
    case 3: lightnum = GL_LI.hT3; break;
    case 4: lightnum = GL_LI.hT4; break;
    case 5: lightnum = GL_LI.hT5; break;
    case 6: lightnum = GL_LI.hT6; break;
    case 7: lightnum = GL_LI.hT7; break;
    }
    glEnable(lightnum);
    glLightfv(lightnum, GL_POSITION, lightPos);
    glLightfv(lightnum, GL_SPOT_DIRECTION, lightDir);
//    glLightfv(lightnum, GL_AMBIENT, Color.Ambient.RGBA);
    glLightfv(lightnum, GL_DIFFUSE, Color.Diffuse.RGBA);
    glLightfv(lightnum, GL_SPECULAR, Color.Specular.RGBA);
  }
}

void 
OGLLight::SetMaterial(Material *mat) {

  GLenum lightnum;

  Color = *mat;
  switch (Num % 8) {
    case 0: lightnum = GL_LI.hT0; break;
    case 1: lightnum = GL_LI.hT1; break;
    case 2: lightnum = GL_LI.hT2; break;
    case 3: lightnum = GL_LI.hT3; break;
    case 4: lightnum = GL_LI.hT4; break;
    case 5: lightnum = GL_LI.hT5; break;
    case 6: lightnum = GL_LI.hT6; break;
    case 7: lightnum = GL_LI.hT7; break;
  }
  glLightfv(lightnum, GL_DIFFUSE, Color.Diffuse.RGBA);
  glLightfv(lightnum, GL_SPECULAR, Color.Specular.RGBA);

}
