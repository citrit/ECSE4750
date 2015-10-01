//////////////////////////////////////////////////////////////////////////
//
//  This code is for instructional purposes only. It was generated for
//  use in a graduate level course to show certain aspects of data
//  storage algorithms. It has problems and should not be used
//  outside the class environment.
//
//  Author:  Trung Ly		lyt@rpi.edu
//  Class:   Advanced Computer Graphics and Data visualization
//           Rensselaer Polytechnic Institute
//  Date:    March 1999
//
/////////////////////////////////////////////////////////////////////////

import jogl.*;
import java.util.Vector;

/**
  * The StructuredGrid object holds all data and methods for the structured grid
  */

public class StructuredGrid extends Actor
{
	float deltax, deltay;
	PointType startpt;
	int width, height;
	PointSet ptset;
	PointSet ctset = new PointSet();
	ScalarData data;
	float tmp[];
	MaterialSet matSet;
	float BBox[];
	Vector valueVector;
	Vector contourVector;

	public StructuredGrid()
	{
		deltax = deltay = 1;
		startpt = new PointType();
		width = height = 0;
		tmp = new float[3];
		ptset = new PointSet();
	}

	public void setSize(int w, int h) { width=w; height=h; }

	public void setStartPoint(PointType pt) { startpt=pt; }

	public void setDeltas(float dx, float dy) { deltax=dx; deltay=dy; }

	public void setPoints(PointSet pts) { ptset=pts; }

	public void setData(ScalarData dat) { data=dat; }

	// Distributes the contours values evenly depending on how many contours you want
	public void setContours(int num)
	{
		float spacing;
		Float f = new Float(0.0F);
		valueVector = new Vector();

		spacing = (data.getMax()-data.getMin())/(float)(num+1);
		for(int i=1; i<=num; i++)
		{
			f = new Float( data.getMin()+((float)i*spacing) );
			valueVector.addElement( f );
		}
	}

	public void clearGrid()
	{
		this.cells.removeAllElements();
		update();
	}

	public void generatePointSet()
	{
		ptset = new PointSet();
		for(int j=0; j<height; j++)  // Create point set
		{
			for(int i=0; i<width; i++)
			{
				tmp[0] = deltax*i;
				tmp[1] = deltay*j;
				tmp[2] = 0;
				ptset.addPoint(new PointType(tmp));
			}
		}
		BBox = ptset.getBBox();
	}

	public void displayWireFrame()
	{
		PolyLineCell plCell;

		for(int j=0; j<(height-1); j++)  // Create PolyLine Cells
		{
			for(int i=0; i<(width-1); i++)
			{
				matSet = new MaterialSet();
				plCell = new PolyLineCell();
				matSet.addMaterial(1.0F, 1.0F, 1.0F, 1.0F);
				plCell.addVal(j*width+i);
				plCell.addVal(j*width+i+1);
				plCell.addVal((j+1)*width+i+1);
				plCell.addVal((j+1)*width+i);
				plCell.addVal(j*width+i);
				plCell.setMaterials(matSet);
				plCell.setPoints(ptset);
				addCell(plCell);
			}
		}
		update();
	}

	public void displayPlane()
	{
		PointCell pCell;

		for(int i=0; i<ptset.size(); i++)
		{
			matSet = new MaterialSet();
			pCell = new PointCell();
			matSet.addMaterial( data.getColor(i) );
			pCell.addVal(i);
			pCell.setMaterials(matSet);
			pCell.setPoints(ptset);
			addCell(pCell);
		}
		update();
	}

	public void displayContours()
	{
		LineCell lCell;
		contourVector = new Vector();

		for(int i=0; i<valueVector.size(); i++)
		{
			Contour( ((Float)valueVector.elementAt(i)).floatValue() );
			contourVector.addElement(ctset);
		}

		for(int j=0; j<contourVector.size(); j++)
		{
			for(int i=0; i<((PointSet)contourVector.elementAt(j)).size(); i+=2)
			{
				matSet = new MaterialSet();
				lCell = new LineCell();
				matSet.addMaterial(1.0F, 1.0F, 1.0F, 1.0F);
				lCell.addVal(i);
				lCell.addVal(i+1);
				lCell.setMaterials(matSet);
				lCell.setPoints((PointSet)contourVector.elementAt(j));
				addCell(lCell);
			}
		}
		update();
	}

	public void Contour( float value )
	{
		int CASE_MASK[] = {1,2,4,8};
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
		int edges[][] = new int[4][2];
		int edge[] = new int[5];
		int index = 0;
		int vert[] = new int[2];
		int pts[] = new int[2];
		float t;
		PointType p1 = new PointType();
		PointType p2 = new PointType();
		PointType p3 = new PointType();
		ctset = new PointSet();

		for(int j=0; j<(height-1); j++)
		{
		for(int i=0; i<(width-1); i++)
		{
			index=0;
			// Build the case table
			if (data.getScalar(j*width+i) >= value)
				index |= CASE_MASK[0];
			if (data.getScalar(j*width+i+1) >= value)
				index |= CASE_MASK[1];
			if (data.getScalar((j+1)*width+i+1) >= value)
				index |= CASE_MASK[2];
			if (data.getScalar((j+1)*width+i) >= value)
				index |= CASE_MASK[3];

			edges[0][0] = edges[3][1] = j*width+i;
			edges[1][0] = edges[0][1] = j*width+i+1;
			edges[2][0] = edges[1][1] = (j+1)*width+i+1;
			edges[3][0] = edges[2][1] = (j+1)*width+i;

			edge = lineCases[index];

			for ( int n=0; edge[n] > -1; n += 2 )
			{
			for (int m=0; m<2; m++) // insert line
			{

				vert = edges[edge[m]];

				t = (float)((value - data.getScalar(vert[0])) /
					(data.getScalar(vert[1]) - data.getScalar(vert[0])));
				p1 = ptset.getPoint(vert[0]);
				p2 = ptset.getPoint(vert[1]);
				p3 = new PointType(p1.x + t * (p2.x - p1.x),
					p1.y + t * (p2.y - p1.y),
					p1.z + t * (p2.z - p1.z));
				ctset.addPoint(p3);
			}
			}

		}
		}
	}

	/** return a float[] of the bounding box of the points */
	public float[] getBBox() { return BBox; }

}