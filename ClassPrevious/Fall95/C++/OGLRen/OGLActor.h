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

#ifndef _OGLACTOR_H_
#define _OGLACTOR_H_

#include "Actor.h"

class Actor : public ActorBC {
public:
    Actor() { }
    ~Actor() { }
    void Render(Renderer *aren);

private:
    // Keep track of current orientation
    float Matrix[16];

};


#endif
