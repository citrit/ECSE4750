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
//  Date:    January 1998
//
//////////////////////////////////////////////////////////////////////////

//package oglr;
import java.util.Vector;

/**
  * StructuredGrid object - Represents a grid for display and contouring
  *
  */
public class StructuredGrid extends Actor
{

	/** This holds the Grid cells while the actual class holds the contours*/
	Vector gridCells;
	/** Also need some Materials */
	MaterialSet gridMtSet;
	/** Addin a colormap object */
	ColorMap cMap;
	/** Values to define behavior */
    int   dimX, dimY;
    float deltaX, deltaY;
    PointType startPt;
    PointSet ptSet;
    ScalarFloat scalarData;
    final int GRID = 1, PLANE = 2, CONTOURS = 3;
    int curMode;
    Vector contInterval;
    int edges[][];
    boolean scalarIsZ;

	public StructuredGrid() {
		dimX = dimY = 0;
		deltaX = deltaY = 1;
		ptSet = null;
		scalarData = null;
		curMode = GRID;
		contInterval = null;
		scalarIsZ = true;
		gridCells = new Vector();
		cMap = new ColorMap();
		edges = new int[4][2];
		startPt = new PointType();
	}

	public void render(Renderer aren) {
		int i,j;

		if (gridCells.size() == 0)
			generateGrid();
		if(scalarData == null) {
			DrawGrid(aren);
		}
		else {
			switch (curMode) {
			case PLANE:
				DrawPlane(aren);
				break;
			case GRID:
				DrawGrid(aren);
				break;
			case CONTOURS:
				Contour();
				super.render(aren);
				break;
			default:
				super.render(aren);
				break;
			}
		}
	}

	/** Draw the grid */
	void DrawGrid(Renderer aren) {
		int i;

		for (i=0;i<(dimX-1)*(dimY-1);i++)
			((Cell)gridCells.elementAt(i)).render(aren);
	}
	/** Draw the plane */
	void DrawPlane(Renderer aren) {
		int i;

		for (i=(dimX-1)*(dimY-1);i<(2*(dimX-1)*(dimY-1));i++)
			((Cell)gridCells.elementAt(i)).render(aren);
	}

	void generateGrid()
	{
		int i, j, quad;
		float tmp[] = new float[3];
		PolygonCell pgcell;
		PolylineCell plcell;

		// Load up  the grid material set with either one white
		// entry or a scalar color for each point
		if (gridMtSet == null)
			gridMtSet = new MaterialSet();
		if (scalarData == null) {
			// Create a matSet with a single color, white
			gridMtSet.addMaterial(new Material());
		}
		else { // There is scalar data, lets fill it in with colors
			cMap.setRange(scalarData.getScalarRange());
			for (i=0;i<dimX*dimY;i++) {
				gridMtSet.addMaterial(cMap.lookupValue(scalarData.getScalar(i)));
			}
		}
		// We will also create the pointset if one does not exist
		tmp[2] = 0;
		if (ptSet == null) {
			ptSet = new PointSet();
			for (j=0;j<dimY;j++) {
				for (i=0;i<dimX;i++) {
					tmp[0] = startPt.x + (i*deltaX);
					tmp[1] = startPt.y + (j*deltaY);
					if (scalarData != null && scalarIsZ)
						tmp[2] = scalarData.getScalar((j*dimX)+i);
					ptSet.addPoint(new PointType(tmp));
				}
			}
		}

		// Now create the grid, we will cheat and first create a grid of
		// polylines and then a grid of polygons.
		quad = 0; // current quad
		for (j=0;j<dimY-1;j++) {
			for (i=0;i<dimX;i++) {
				if (quad%dimX != (dimX-1)) {
					plcell = new PolylineCell();
					plcell.addVal(quad);
					plcell.addVal(quad+1);
					plcell.addVal(quad+dimX+1);
					plcell.addVal(quad+dimX);
					if (i==0) plcell.addVal(quad);
					plcell.setMaterials(gridMtSet);
					plcell.setPoints(ptSet);
					gridCells.addElement(plcell);
				}
				quad++;
			}
		}
		quad = 0; // current quad
		for (j=0;j<dimY;j++) {
			for (i=0;i<dimX;i++) {
				if (quad%dimX != (dimX-1)) {
					pgcell = new PolygonCell();
					pgcell.addVal(quad);
					pgcell.addVal(quad+1);
					pgcell.addVal(quad+dimX+1);
					pgcell.addVal(quad+dimX);
					pgcell.setMaterials(gridMtSet);
					pgcell.setPoints(ptSet);
					gridCells.addElement(pgcell);
				}
				quad++;
			}
		}
	}


	public void SetSize(int w, int h) {
		dimX = w;
		dimY = h;
		edges[0][0] = edges[3][1] = 0;
		edges[0][1] = edges[1][0] = 1;
		edges[1][1] = edges[2][0] = dimX+1;
		edges[2][1] = edges[3][0] = dimX;
		updateTime();
	}

	public void SetStartPoint(PointType pt) {
		startPt = pt;
		updateTime();
	}

	public void SetDeltas(float deltax, float deltay) {
		deltaX = deltax;
		deltaY = deltay;
		updateTime();
	}

	public void SetPoints(PointSet pts) {
		ptSet = pts;
		updateTime();
	}

	public void SetData(ScalarFloat data) {
		scalarData = data;
		updateTime();
	}

	public void DisplayWireframe() {
		curMode = GRID;
	}

	public void DisplayPlane() {
		curMode = PLANE;
	}

	public void DisplayContours() {
		curMode = CONTOURS;
	}

	public void SetContours(Vector vec) {
		contInterval = vec;
		updateTime();
	}

	final static int lineCases[][] = { 
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
	public void Contour() 
	{ 

		/*for (int i=0;i<16;i++){
			System.out.print("[");
			for (int j=0;j<5;j++) {
				System.out.print(lineCases[i][j] + " ");
			}
			System.out.println("]");
		}*/

		// Check to see if the scalar data has been chganged since
		// the contours were generated.
		if (cells.size() > 0)
			if (scalarData.getTime() < ((Cell)cells.elementAt(0)).getTime())
				return;

		int curCont, curQuad, totQuad, i, j, pntCnt;
		int lineCase[];
		int  edge[];
		int index, vert[];
		PointType pts1, pts2, _pt;
		PointSet ptset = new PointSet();
		float t, x[] = new float[3], cval;
		PointType x1, x2;
		LineCell line;
		Material mat;
		MaterialSet mtset;

		pntCnt = 0;
		totQuad = (dimX-1)*(dimY-1);
		//
		//  For each contour
		//
		for (curCont=0;curCont<contInterval.size();curCont++) {
			cval = ((Float)contInterval.elementAt(curCont)).floatValue();
			mat = cMap.lookupValue(cval);
			mtset = new MaterialSet();
			mtset.addMaterial(mat);
			//
			//  For each Quad.
			//
			for (curQuad=0;curQuad<totQuad;curQuad++) {
				if (curQuad%dimX == (dimX-1)) curQuad++;
				// Build Case table for each vertex
				index = 0;
				if (scalarData.getScalar(curQuad) >= cval) index |= 1;
				if (scalarData.getScalar(curQuad+1) >= cval) index |= 2;
				if (scalarData.getScalar(curQuad+dimX+1) >= cval) index |= 4;
				if (scalarData.getScalar(curQuad+dimX) >= cval) index |= 8;
      
				edge = lineCases[index];
				//edge = lineCase;

				for (j=0 ; edge[j] > -1;j += 2 )	{
					line = new LineCell();
					line.setMaterials(mtset);
					for (i=0; i<2; i++) {   // insert line 
						vert = edges[edge[i]];
						t = (cval - scalarData.getScalar(curQuad+vert[0])) /
							(scalarData.getScalar(curQuad+vert[1]) - 
							scalarData.getScalar(curQuad+vert[0]));
						x1 = ptSet.getPoint(curQuad+vert[0]);
						x2 = ptSet.getPoint(curQuad+vert[1]);
						x[0] = x1.x + t * (x2.x - x1.x);
						x[1] = x1.y + t * (x2.y - x1.y);
						if (scalarIsZ)
							x[2] = cval;
						else
							x[2] = x1.z + t * (x2.z - x1.z);
						ptset.addPoint(new PointType(x));
						line.addVal(pntCnt++);
					}
					line.setPoints(ptset);
					this.addCell(line);
				}
			}
		}
	}

}

