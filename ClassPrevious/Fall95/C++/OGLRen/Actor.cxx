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

#include "Actor.h"

void
Actor::Render(Renderer *aren) {

  for (int i=0;i<this->Count();i++) {
    (*this)[i]->Render(aren);
  }
}
