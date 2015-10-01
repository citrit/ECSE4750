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

#include "TriCell.h"

void
TriangleCell::Render(Renderer *aren)
{
  int cnt;

	  cnt = this->MtSet->Count();
	  aren->BeginDraw(Renderer::TRIANGLE);
	  for (int i=0;i<3;i++) {
		aren->SetMaterial(this->MtSet->Get(this->Get(i)%cnt));
		aren->Vertex(PtSet->Get(this->Get(i))->x, 
					 PtSet->Get(this->Get(i))->y, 
					 PtSet->Get(this->Get(i))->z);
	  }
	  aren->EndDraw();
}

void
TriangleCell::PrintSelf(ostream& os) {

  Cell::PrintSelf(os);
  os << "  Corners: " << endl;
  for (int i=0;i<3;i++) {
    os << "\t[" << PtSet->Get(this->Get(i))->x << "," << 
                   PtSet->Get(this->Get(i))->y << "," <<
				   PtSet->Get(this->Get(i))->z << "] " << endl;
  }
}