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
/////////////////////////////////////////////////////////////////////////

import java.util.Vector;

/**
 * Actor class, this class holds a collection of cells and properties.
 *
 * @author		Thomas D. Citrinit
 * @version		0.1
 */
public class StructuredGrid extends ParentObject
{
	PointSet sPoints ;
	ScalarData sData;
	int		dimX,dimY;
	int		width,height;
	float	deltax,deltay;
	PointType startPoint;
	int displayMode;
	float contourValue;

	float orientation[];
	PointType position; // Obvious
	float scale[]; // Obvious

	Vector edgeList;
    int edges[][] = { {0,1}, {1,2}, {2,3}, {3,0} };
	
    int lineCases[][] = {
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
	
	/** Constructor that creates a Vector class for cells */
	public StructuredGrid() 
	{
		sPoints = null;
		sData = null;
		deltax = deltay = 1.0F;
		startPoint = new PointType();
		width = height = 0;
		dimX = dimY = 0;
		displayMode = 0;
		contourValue = 0.0F;
				
		orientation = new float[3];
		position = new PointType();
		scale = new float[3];
		scale[0] = scale[1] = scale[2] = 1;	
	}

	public void SetDimXY(int dx, int dy)
	{
		dimX = dx;
		dimY = dy;
	}

	public void SetSize(int w, int h)
	{
		width = w;
		height = h;
	}

	public void SetStartPoint(PointType pnt)
	{
		startPoint = pnt;
	}

	public void SetDelta(float dx, float dy)
	{
		deltax = dx;
		deltay = dy;
	}

	public void SetPoints(PointSet pts)
	{
		sPoints = pts;
	}

	public void SetData(ScalarData data)
	{
		sData = data;
	}

	public void SetDisplayMode(int mode)
	{
		displayMode = mode;
	}

	public void SetContour(float value)
	{
		contourValue = value;
	}
	/** Method called by Renderer to signal an update is needed. */
	public void render(Renderer aren)
	{
//		System.out.println("Here, render() in StucturedGrid");
		aren.pushMatrix();
//		aren.setOrientation(orientation, scale, position);
		if(displayMode == 0) 
		{
				DisplayWireframe(aren);
		}
		else if(displayMode == 1)
		{
				DisplayPlane(aren);
		}
		else if(displayMode == 2)
		{
				DisplayColorMap(aren);
		}	
		else if(displayMode == 3)
		{
				DisplayAllContours(aren);
		}	
		else 
		{
				DisplayPlane(aren);
				DisplayContour(aren,contourValue);
		}	


		aren.popMatrix();
	}

	public void DisplayWireframe(Renderer aren)
	{
		PointType xyz = new PointType();
		MaterialSet matSet = new MaterialSet();
		matSet.addMaterial(new Material(1.0F, 0.0F, 0.0F, 1.0F));
//		System.out.println("Here, DisplayWireframe() in StucturedGrid");
		for(int j=0;j<dimY;j++)
		{
			aren.beginDraw(aren.POLYLINE);
			for(int i=0;i<dimX;i++)
			{
				xyz = sPoints.getPoint(i+j*dimY);
				aren.setMaterial(matSet.getMaterial(0));
				aren.vertex(xyz);
			}
			aren.endDraw();
		}
		for(int i=0;i<dimX;i++)
		{
			aren.beginDraw(aren.POLYLINE);
			for(int j=0;j<dimY;j++)
			{
				xyz = sPoints.getPoint(i+j*dimY);
				aren.setMaterial(matSet.getMaterial(0));
				aren.vertex(xyz);
			}
			aren.endDraw();
		}
	}

	public void DisplayPlane(Renderer aren)
	{
		float amb[] =  {0.8F, 0.8F, 0.0F, 0.0F};
		float diff[] = {0.5F, 0.5F, 0.1F, 0.0F};
		float spec[] = {0.5F, 0.5F, 0.5F, 0.0F};

		int vertex[] = new int[4];
		PointType xyz = new PointType();
		Material mat = new Material();
		mat.setAmbient(amb[0],amb[1],amb[2],amb[3]);
		mat.setDiffuse(diff[0],diff[1],diff[2],diff[3]);
		mat.setSpecular(spec[0],spec[1],spec[2],spec[3]);


		aren.setMaterial(mat);
//		System.out.println("Here, DisplayPlane() in StucturedGrid");
		for(int j=0;j<dimY-1;j++)
			for(int i=0;i<dimX-1;i++)
			{				
				aren.beginDraw(aren.POLYGON);
				vertex[0] = i+j*dimY;
				vertex[1] = i+j*dimY+dimX;
				vertex[2] = i+j*dimY+dimX+1;
				vertex[3] = i+j*dimY+1;
				for(int k=0;k<4;k++)
				{
					xyz = sPoints.getPoint(vertex[k]);				
					aren.vertex(xyz);
				}
				aren.endDraw();
			}
	}

	public void DisplayColorMap(Renderer aren)
	{

		int[] vertex = new int[4];
		PointType xyz = new PointType();

//		System.out.println("Here, DisplayColorMap() in StucturedGrid");
		for(int j=0;j<dimY-1;j++)
			for(int i=0;i<dimX-1;i++)
			{				
				aren.beginDraw(aren.POLYGON);
				vertex[0] = i+j*dimY;
				vertex[1] = i+j*dimY+dimX;
				vertex[2] = i+j*dimY+dimX+1;
				vertex[3] = i+j*dimY+1;
				
				xyz.x = (float)i*deltax;
				xyz.y = (float)j*deltay;
				xyz.z = 0.0F;
				aren.setMaterial(sData.getMaterial(sData.getScalar(vertex[0])));
				aren.vertex(xyz);
				xyz.x = (float)i*deltax;
				xyz.y = (float)(j+1)*deltay;
				xyz.z = 0.0F;
				aren.setMaterial(sData.getMaterial(sData.getScalar(vertex[1])));
				aren.vertex(xyz);
				xyz.x = (float)(i+1)*deltax;
				xyz.y = (float)(j+1)*deltay;
				xyz.z = 0.0F;
				aren.setMaterial(sData.getMaterial(sData.getScalar(vertex[2])));
				aren.vertex(xyz);
				xyz.x = (float)(i+1)*deltax;
				xyz.y = (float)j*deltay;
				xyz.z = 0.0F;
				aren.setMaterial(sData.getMaterial(sData.getScalar(vertex[3])));
				aren.vertex(xyz);

				aren.endDraw();
			}
	}


	public void Contour(float value,int cell[],Renderer aren)
	{
		int index=0;
		int case_mask[] = {1,2,4,8};
		int linecase[] = new int[5];
		int edge[] = new int[5];
		int vert[] = new int[2];
		float t;
		PointType x1 = new PointType();
		PointType x2 = new PointType();
		PointType from = new PointType();
		PointType to = new PointType();
		
		MaterialSet matSet = new MaterialSet();
		matSet.addMaterial(new Material(1.0F, 0.0F, 0.0F, 1.0F));

		
		for(int i=0; i<4;i++)
			if( sPoints.getPoint(cell[i]).z >= value)
				index |= case_mask[i];
		linecase = lineCases[index];
		edge = lineCases[index];
		if(edge[0] >= 0)
		for(int k=0;edge[k]>-1;k=k+2)
		{
			vert = edges[edge[k]];
			t = (value - sPoints.getPoint(cell[vert[0]]).z)/(sPoints.getPoint(cell[vert[1]]).z - sPoints.getPoint(cell[vert[0]]).z );

			x1 = sPoints.getPoint(cell[vert[0]]);
			x2 = sPoints.getPoint(cell[vert[1]]);

			from.x = x1.x + t*(x2.x - x1.x);
			from.y = x1.y + t*(x2.y - x1.y);
			from.z = value;
			vert = edges[edge[k+1]];
			t = (value - sPoints.getPoint(cell[vert[0]]).z)/(sPoints.getPoint(cell[vert[1]]).z - sPoints.getPoint(cell[vert[0]]).z );
			x1 = sPoints.getPoint(cell[vert[0]]);
			x2 = sPoints.getPoint(cell[vert[1]]);
			to.x = x1.x + t*(x2.x - x1.x);
			to.y = x1.y + t*(x2.y - x1.y);			
			to.z = value;
			aren.beginDraw(aren.POLYLINE);
			aren.setMaterial(matSet.getMaterial(0));
			aren.vertex(from);
			aren.vertex(to);
			aren.endDraw();

		}

	}


	public void DisplayContour(Renderer aren,float value)
	{
		int vertex[] = new int[4];
//		System.out.println("Here, DisplayContour() in StucturedGrid");
		for(int j=0;j<dimY-1;j++)
			for(int i=0;i<dimX-1;i++)
			{				
				vertex[0] = i+j*dimY;
				vertex[1] = i+j*dimY+dimX;
				vertex[2] = i+j*dimY+dimX+1;
				vertex[3] = i+j*dimY+1;
				Contour(value, vertex,aren);
			}
	}

	public void DisplayAllContours(Renderer aren)
	{
		int vertex[] = new int[4];
		float value;

		for(value=sData.MinMax[0];value<sData.MinMax[1];value+=0.5F) // This is for just demo
		{
			for(int j=0;j<dimY-1;j++)
				for(int i=0;i<dimX-1;i++)
				{				
					vertex[0] = i+j*dimY;
					vertex[1] = i+j*dimY+dimX;
					vertex[2] = i+j*dimY+dimX+1;
					vertex[3] = i+j*dimY+1;
					Contour(value, vertex,aren);
				}
		}
	}

}

