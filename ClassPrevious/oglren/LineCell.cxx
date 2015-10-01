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

#include "LineCell.h"

//
// Need to decide weather the display list needs to be created again
// If the mtset or ptset has been updated
void
LineCell::Render(Renderer *aren)
{
  int cnt;

	  cnt = this->MtSet->Count();
	  aren->BeginDraw(Renderer::LINE);
	  for (int i=0;i<this->Count();i++) {
	    aren->SetMaterial(this->MtSet->Get((this->Get(i))%cnt));
	    aren->Vertex(PtSet->Get(this->Get(i))->x, 
			PtSet->Get(this->Get(i))->y, 
			PtSet->Get(this->Get(i))->z);
	  }
	  aren->EndDraw();
}

void
LineCell::PrintSelf(ostream& os) {

  Cell::PrintSelf(os);
  os << "   Vertices: " << endl;
  for (int i=0;i<this->Count();i++) {
    os <<  "\t[" << PtSet->Get(this->Get(i))->x << "," << 
					PtSet->Get(this->Get(i))->y << "," <<
					PtSet->Get(this->Get(i))->z << "] " << endl;
  }
  os << endl;
}
