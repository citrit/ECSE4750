/////////////////////////////////////////////////////////////////////////
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

#include "StructG.h"

StructuredGrid::StructuredGrid() {
  DimX = DimY = 0;
  DeltaX = DeltaY = 1.0;
  PtSet = NULL;
  Data = NULL;
  CurMode = GRID;
  ContInterval = NULL;
  ScalarIsZ = 1;
}
/*
void 
StructuredGrid::LoadLineCases() {

  for (int i=0;i<16;i++)
    for (int j=0;j<5;j++)
      lineCases[i].edges[j] = -1;
  lineCases[1].edges[0] = lineCases[2].edges[1] = lineCases[5].edges[0] = 
    lineCases[6].edges[1] = lineCases[9].edges[0] = lineCases[10].edges[1] = 
    lineCases[13].edges[0] = lineCases[14].edges[1] = 0;
  lineCases[1].edges[1] = lineCases[3].edges[1] = lineCases[5].edges[1] = 
    lineCases[7].edges[1] = lineCases[8].edges[0] = lineCases[10].edges[2] = 
    lineCases[12].edges[0] = lineCases[14].edges[0] = DimX;
  lineCases[4].edges[0] = lineCases[6].edges[0] = lineCases[7].edges[0] = 
    lineCases[8].edges[1] = lineCases[9].edges[1] = lineCases[10].edges[3] = 
    lineCases[11].edges[1] = DimX+1;
}
*/
void 
StructuredGrid::Render(Renderer *aren) {
  int i,j;

  if (Data == NULL)
	  return;
  // If something is new regenerate display list
  if ( dList == 0 || (Data->GetTime() > this->GetTime()) || 
	   (PtSet && (PtSet->GetTime() > this->GetTime()))
	   ) {
	  aren->BeginDL(dList);
	  aren->PushMatrix();
	  aren->SetOrientation(orientation, scale, &position);
	  if(Data == NULL) {
		  DrawWhiteGrid(aren);
	  }
	  else {
		switch (CurMode) {
		case PLANE:
			DrawPlane(aren);
			break;
		case GRID:
			DrawColoredGrid(aren);
			break;
		case CONTOURS:
			Contour();
			for (i=0;i<this->Count();i++) {
				this->Get(i)->Render(aren);
			}
			aren->PopMatrix();
			break;
		default:
			for (i=0;i<this->Count();i++) {
				this->Get(i)->Render(aren);
			}
			break;
		}
	  }
	  aren->PopMatrix();
	  aren->EndDL();
  }
  else
	  aren->DrawDisplayList(dList);

}

void 
StructuredGrid::DrawWhiteGrid(Renderer *aren) {
  static Material *mat = new Material;
  PointType _stpt, _endpt;
  int i;

  aren->SetMaterial(mat);
  aren->BeginDraw(Renderer::LINE);
  for (i=0;i<DimX;i++) {
    _stpt = GetPoint(i,0,0);
    _endpt = GetPoint(i,DimY-1,0);
    aren->Vertex(_stpt.x, _stpt.y, _stpt.z); 
    aren->Vertex(_endpt.x, _endpt.y, _endpt.z); 
  }
  for (i=0;i<DimY;i++) {
    _stpt = GetPoint(0,i,0);
    _endpt = GetPoint(DimX-1,i,0);
    aren->Vertex(_stpt.x, _stpt.y, _stpt.z); 
    aren->Vertex(_endpt.x, _endpt.y, _endpt.z); 
  }
  aren->EndDraw();
}

void 
StructuredGrid::DrawColoredGrid(Renderer *aren) {
  int cube = 0;
  PointType _pt;
  Material *mat;

  for (int i = 0;i < DimY-1; i++) {
    for (int j = 0;j < DimX;j++) {
      if (cube%DimX != (DimX-1)) {
	aren->BeginDraw(Renderer::LINE_LOOP);
	   mat = Data->LookupValue(Data->Get(cube));
	   _pt = GetPoint(cube);if (ScalarIsZ) _pt.z = Data->Get(cube);
	   aren->SetMaterial(mat);
	   aren->Vertex(_pt.x, _pt.y, _pt.z); 
	   mat = Data->LookupValue(Data->Get(cube+1));
	   _pt = GetPoint(cube+1);if (ScalarIsZ) _pt.z = Data->Get(cube+1);
	   aren->SetMaterial(mat);
	   aren->Vertex(_pt.x, _pt.y, _pt.z); 
	   mat = Data->LookupValue(Data->Get(cube+DimX+1));
	   _pt = GetPoint(cube+DimX+1);if (ScalarIsZ) 
	                                 _pt.z = Data->Get(cube+DimX+1);
	   aren->SetMaterial(mat);
	   aren->Vertex(_pt.x, _pt.y, _pt.z); 
	   mat = Data->LookupValue(Data->Get(cube+DimX));
	   _pt = GetPoint(cube+DimX);if (ScalarIsZ) _pt.z = Data->Get(cube+DimX);
	   aren->SetMaterial(mat);
	   aren->Vertex(_pt.x, _pt.y, _pt.z); 
	aren->EndDraw();
      }
      cube++;
    }
  }

}

void 
StructuredGrid::DrawPlane(Renderer *aren) {
  int cube = 0;
  PointType _pt;
  Material *mat;

  for (int i = 0;i < DimY-1; i++) {
    for (int j = 0;j < DimX;j++) {
      if (cube%DimX != (DimX-1)) {
	aren->BeginDraw(Renderer::POLYGON);
	   mat = Data->LookupValue(Data->Get(cube));
	   _pt = GetPoint(cube);if (ScalarIsZ) _pt.z = Data->Get(cube);
	   aren->SetMaterial(mat);
	   aren->Vertex(_pt.x, _pt.y, _pt.z); 
	   mat = Data->LookupValue(Data->Get(cube+1));
	   _pt = GetPoint(cube+1);if (ScalarIsZ) _pt.z = Data->Get(cube+1);
	   aren->SetMaterial(mat);
	   aren->Vertex(_pt.x, _pt.y, _pt.z); 
	   mat = Data->LookupValue(Data->Get(cube+DimX+1));
	   _pt = GetPoint(cube+DimX+1);if (ScalarIsZ) 
	                                  _pt.z = Data->Get(cube+DimX+1);
	   aren->SetMaterial(mat);
	   aren->Vertex(_pt.x, _pt.y, _pt.z); 
	   mat = Data->LookupValue(Data->Get(cube+DimX));
	   _pt = GetPoint(cube+DimX);if (ScalarIsZ) _pt.z = Data->Get(cube+DimX);
	   aren->SetMaterial(mat);
	   aren->Vertex(_pt.x, _pt.y, _pt.z); 
	aren->EndDraw();
      }
      cube++;
    }
  }
}


PointType&
StructuredGrid::GetPoint(int i, int j, int k) {
  static PointType _pt;

  if (PtSet == NULL) {
    _pt.x = (i * DeltaX) + StartPt.x;
    _pt.y = (j * DeltaY) + StartPt.y;
    _pt.z = 0;
  }
  else
    _pt = *(PtSet->Get((j*DimX)+i));

  return _pt;
}

PointType& 
StructuredGrid::GetPoint(int pt) {
  static PointType _pt;

  if (PtSet == NULL) {
    _pt =  GetPoint(pt%DimX, pt/DimX, 0);
  }
  else
    _pt = *(PtSet->Get(pt));

  return _pt;
}

void
StructuredGrid::SetEdges() {

  edges[0][0] = edges[3][1] = 0;
  edges[0][1] = edges[1][0] = 1;
  edges[1][1] = edges[2][0] = DimX+1;
  edges[2][1] = edges[3][0] = DimX;

}

//
// Marching (convex) quadrilaterals
//
// static int edges[4][2] = { {0,1}, {1,2}, {2,3}, {3,0} };

typedef int EDGE_LIST;
typedef struct {
       EDGE_LIST edges[5];
} LINE_CASES;

static LINE_CASES lineCases[] = { 
  {-1, -1, -1, -1, -1},
  {0, 3, -1, -1, -1},
  {1, 0, -1, -1, -1},
  {1, 3, -1, -1, -1},
  {2, 1, -1, -1, -1},
  {0, 3, 2, 1, -1},
  {2, 0, -1, -1, -1},
  {2, 3, -1, -1, -1},
  {3, 2, -1, -1, -1},
  {0, 2, -1, -1, -1},
  {1, 0, 3, 2, -1},
  {1, 2, -1, -1, -1},
  {3, 1, -1, -1, -1},
  {0, 1, -1, -1, -1},
  {3, 0, -1, -1, -1},
  {-1, -1, -1, -1, -1}
};

void 
StructuredGrid::Contour() {

  int curCont, curQuad, totQuad, i, j, pntCnt;
  LINE_CASES *lineCase;
  EDGE_LIST  *edge;
  int index, *vert;
  PointType *pts[2], *_pt;
  PointSet *ptset = new PointSet;
  float t, x[3];
  PointType x1, x2;
  LineCell *line;
  Material *mat;
  MaterialSet *mtset;

  if ( (Data->GetTime() < this->GetTime()) && PtSet &&
       (PtSet->GetTime() < this->GetTime()) )
    return;

  pntCnt = 0;
  totQuad = (DimX-1)*(DimY-1);
  //
  //  For each contour
  //
  for (curCont=0;curCont<ContInterval->Count();curCont++) {
    mat = new Material;
    *mat = *(Data->LookupValue(ContInterval->Get(curCont)));
    mtset = new MaterialSet;
    *mtset += mat;
    //
    //  For each Quad.
    //
    for (curQuad=0;curQuad<totQuad;curQuad++) {
      if (curQuad%DimX == (DimX-1)) curQuad++;
      // Build Case table for each vertex
      index = 0;
      if (Data->Get(curQuad) >= ContInterval->Get(curCont)) index |= 1;
      if (Data->Get(curQuad+1) >= ContInterval->Get(curCont)) index |= 2;
      if (Data->Get(curQuad+DimX+1) >= ContInterval->Get(curCont)) index |= 4;
      if (Data->Get(curQuad+DimX) >= ContInterval->Get(curCont)) index |= 8;
      
      lineCase = (LINE_CASES *)&lineCases + index;
      edge = lineCase->edges;

      for ( ; edge[0] > -1; edge += 2 )	{
	line = new LineCell;
	line->SetMaterials(mtset);
	for (i=0; i<2; i++) {   // insert line 
	  vert = edges[edge[i]];
	  t = (ContInterval->Get(curCont) - Data->Get(curQuad+vert[0])) /
	    (Data->Get(curQuad+vert[1]) - Data->Get(curQuad+vert[0]));
	  x1 = GetPoint(curQuad+vert[0]);
	  x2 = GetPoint(curQuad+vert[1]);
	  x[0] = x1.x + t * (x2.x - x1.x);
	  x[1] = x1.y + t * (x2.y - x1.y);
	  if (ScalarIsZ)
	    x[2] = ContInterval->Get(curCont);
	  else
	    x[2] = x1.z + t * (x2.z - x1.z);
	  *ptset += new PointType(x);
	  *line += pntCnt++;
	}
	line->SetPoints(ptset);
	*this += line;
      }
    }
  }
}


