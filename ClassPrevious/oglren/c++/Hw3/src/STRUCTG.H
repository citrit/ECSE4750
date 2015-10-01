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

#include "LineCell.H"
#include "CellSet.H"
#include "PointSet.H"
#include "DataType.H"
#include "Renderer.H"

typedef int EDGE_LIST;

class StructuredGrid : public CellSet {

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
    void DisplayWireFrame() { CurMode = GRID; }
    void DisplayPlane() { CurMode = PLANE; }
    void DisplayContours() { CurMode = CONTOURS; }
    void SetContours(VectorType<float> *vecs) { ContInterval = vecs; }
    void Render(Renderer *aren);

  };

#endif
