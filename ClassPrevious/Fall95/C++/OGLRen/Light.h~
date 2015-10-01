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


#ifndef _LIGHT_H_
#define _LIGHT_H_

#include "PObject.h"
#include "PointSet.h"
#include "MatSet.h"

class Renderer;

class LightBC : public ParentObject {
  public:
    PointType From, To;
    short     Num;
    Material  Color;

    LightBC() { From.z = 1.0; Num = 0; }
    LightBC(short num) { From.z = 1.0; Num = num; }
    char *ObjectType() { return "Light"; }
    void TurnOn() { On = Yes; }
    void TurnOff() { On = No; }
    virtual void Render(Renderer *aren) = 0;
    virtual void SetMaterial(Material *mat) = 0;
  protected:
    enum LightOnOff { No, Yes };
    LightOnOff   On;
};

#endif
