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

#ifndef _PROPERTIES_H_
#define _PROPERTIES_H_

#define ASSIGNRGB(a,b)  for (int i=0;i<4;i++) a[i] = b[i];

class ColorComponent {
  public:
    float RGBA[4];

    ColorComponent() { RGBA[0] = RGBA[1] = RGBA[2] = 0.8; RGBA[3] = 1.0; }
    ~ColorComponent() { }
    ColorComponent(float r, float g, float b, float a) 
      { RGBA[0] = r; RGBA[1] = g; RGBA[2] = b; RGBA[3] = a; }
    ColorComponent(float rgba[4]) { ASSIGNRGB(RGBA, rgba); }
  };

class Material {
  public:
    ColorComponent Ambient, Diffuse, Specular;

    Material() { }
    Material(float rgba[4]) { SetAmbient(rgba);SetDiffuse(rgba);SetSpecular(rgba); }
    ~Material() { }
    void SetAmbient(float rgba[4]) { ASSIGNRGB(Ambient.RGBA, rgba); }
    void SetDiffuse(float rgba[4]) { ASSIGNRGB(Diffuse.RGBA, rgba); }
    void SetSpecular(float rgba[4]) { ASSIGNRGB(Specular.RGBA, rgba); }
    
  };

typedef VectorType<Material *> MaterialSet;

#endif
