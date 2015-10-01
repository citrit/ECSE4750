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

#ifndef _PROPERTIES_H_
#define _PROPERTIES_H_

#include "PObject.h"

#define ASSIGNRGB(a,b)  for (int i=0;i<4;i++) a[i] = b[i];

class ColorComponent : public ParentObject {
  public:
    float RGBA[4];

    ColorComponent() { RGBA[0] = RGBA[1] = RGBA[2] = 0.8; RGBA[3] = 1; }
    ~ColorComponent() { }
    char *ObjectType() { return "ColorComponent"; }
    void PrintSelf(ostream &os) {
           os << "Red: " << RGBA[0] << "\tBlue: " << RGBA[1]
              << "\tGreen: " << RGBA[2] << "\tAlpha: " << RGBA[3] << endl;
    }
    ColorComponent(float r, float g, float b, float a)
      { RGBA[0] = r; RGBA[1] = g; RGBA[2] = b; RGBA[3] = a; }
    ColorComponent(float rgba[4]) { ASSIGNRGB(RGBA, rgba); }
  };

class Material : public ParentObject {
  public:
    ColorComponent Ambient, Diffuse, Specular;

    Material() { }
    Material(float rgba[4]) { SetAmbient(rgba);SetDiffuse(rgba);SetSpecular(rgba); }
    ~Material() { }
    char *ObjectType() { return "Material"; }
    void PrintSelf(ostream &os) {
        os << "Ambient: "; Ambient.PrintSelf(os); os << endl
           << "Diffuse: "; Diffuse.PrintSelf(os); os << endl
           << "Specular: "; Specular.PrintSelf(os);os << endl;
    }
    void SetAmbient(float rgba[4]) { ASSIGNRGB(Ambient.RGBA, rgba); }
    void SetDiffuse(float rgba[4]) { ASSIGNRGB(Diffuse.RGBA, rgba); }
    void SetSpecular(float rgba[4]) { ASSIGNRGB(Specular.RGBA, rgba); }

  };

typedef VectorType<Material *> MaterialSet;

#endif
