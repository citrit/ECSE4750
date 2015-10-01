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


#ifndef _STRUCT_GRID_H_
#define _STRUCT_GRID_H_

#include "LineCell.h"
#include "Actor.h"
#include "PointSet.h"
#include "DataType.h"
#include "Renderer.h"

typedef int EDGE_LIST;

class StructuredGrid : public ActorBC {

  protected:
    int   DimX, DimY;
    float DeltaX, DeltaY;
    PointType StartPt;
    PointSet *PtSet;
    ScalarFloat *Data;
    enum DrawMode { GRID, PLANE, CONTOURS };
    enum DrawMode CurMode;
    VectorType<float> *ContInterval;
    int edges[4][2];
    int ScalarIsZ;
    void DrawWhiteGrid(Renderer *aren);
    void DrawColoredGrid(Renderer *aren);
    void DrawPlane(Renderer *aren);
    PointType& GetPoint(int i, int j, int k);
    PointType& GetPoint(int pt);
    void SetEdges();

  public:
    StructuredGrid();
    ~StructuredGrid() { }
    void SetSize(int x = 0, int y = 0) { DimX = x;DimY = y;SetEdges(); }
    void SetStartPoint(PointType& pt) { StartPt = pt; }
    void SetDeltas(float deltax, float deltay) {
      DeltaX = deltax; DeltaY = deltay; 
    }
    void SetPoints(PointSet *pts) { PtSet = pts; }
    void SetData(ScalarFloat *data) { Data = data; }
    void Contour();
    void SetScalarIsZ(int onoff) { ScalarIsZ = onoff; dList = 0; }
    void DisplayWireFrame() { CurMode = GRID; dList = 0; }
    void DisplayPlane() { CurMode = PLANE; dList = 0; }
    void DisplayContours() { CurMode = CONTOURS; dList = 0; }
    void SetContours(VectorType<float> *vecs) { ContInterval = vecs; }
    void Render(Renderer *aren);

  };

#endif
