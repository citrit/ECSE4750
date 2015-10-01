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

#include "PlyGCell.H"

void
PolygonCell::Render(Renderer *aren)
{
  int cnt;

  cnt = this->MtSet->Count();  
  aren->BeginDraw(Renderer::POLYGON);
  for (int i=0;i<this->Count();i++) {
    aren->SetMaterial((*(this->MtSet))[(*this)[i]%cnt]);
    aren->Vertex((*PtSet)[(*this)[i]]->x, 
		   (*PtSet)[(*this)[i]]->y, 
		   (*PtSet)[(*this)[i]]->z);
  }
  aren->EndDraw();

}

void
PolygonCell::PrintSelf(ostream& os) {

  Cell::PrintSelf(os);
  os << "   Vertices: " << endl;;
  for (int i=0;i<this->Count();i++) {
    os <<  "\t[" << (*PtSet)[(*this)[i]]->x << "," << 
                 (*PtSet)[(*this)[i]]->y << "," <<
		 (*PtSet)[(*this)[i]]->z << "] " << endl;
  }
  os << endl;
}
