//////////////////////////////////////////////////////////////////////////
//
//	Name: Shuo Zhao  
//	Homework 3
//	StructureGrid.java
//////////////////////////////////////////////////////////////////////////

import java.util.Vector;

public class StructuredGrid extends Actor
{

	
   Vector Plane;	//Hold polyline 
   Vector Grid;		//Hold polygon	
   Vector ContourCell; //Hold Contour display, line
   MaterialSet material;
   ScalarData scal_data;
   final int Wireframe = 1, Solid = 2, Contours = 3;
   int current_Mode;
   Vector interval;
   int   width, height;
   PointType start_point;
   PointSet point_set;
   final int edges[][] = { {0,1}, {1,2}, {2,3}, {3,0} };
   boolean Create_Wireframe;
   boolean Create_Plane;
   boolean Create_Contour;
   int deltaX, deltaY;
	
   final static int lineCases[][] = 
	{ 
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

	public StructuredGrid() 
	{
		width = height = 0;
		Grid = new Vector();
		deltaX=1;
		deltaY=1;
		Plane = new Vector();
		ContourCell = new Vector();
		start_point = new PointType();
		Create_Wireframe = true;
		Create_Plane = true;
		Create_Contour = true;
		interval = new Vector();
	}

	public void render(Renderer aren) 
	{
		int i;
		
		switch (current_Mode) 
		{
			case Solid:
				for (i=0;i<(width-1)*(height-1);i++)
					((Cell)Plane.elementAt(i)).render(aren);
				break;
			case Wireframe:
				for (i=0;i<(width-1)*(height-1);i++)
					((Cell)Grid.elementAt(i)).render(aren);
				break;
			case Contours:
				for (i=0;i<ContourCell.size();i++)
					((Cell)ContourCell.elementAt(i)).render(aren);
				break;
			default:
				break;
		}
	
	}

	public void SetMaterial(MaterialSet mat)
	{
		material = mat;
	}

	public void SetDelta(int x, int y)
	{
		deltaX=x;
		deltaY=y;
	}

	public void SetData(ScalarData data) {
		scal_data = data;
		updateTime();
	}

	public void SetSize(int w, int h) {
		width = w;
		height = h;
		updateTime();
	}

	public void SetStartPoint(PointType pt) 
	{
		float new_point[] = new float[3];
	
		start_point = pt;
		point_set = new PointSet();
	
		for(int i=0; i<(height*width); i++)
		{
			for(int j=0; j<width; j++)
			{	
				
				new_point[0] = start_point.x + j;
				new_point[1] = start_point.y + i;
				new_point[2] = start_point.z;

				point_set.addPoint(new PointType(new_point));
			}
		}

		updateTime();
	}

	public void SetPoints(PointSet pts) {
		point_set = pts;
		updateTime();
	}

	public void DisplayWireframe() 
	{
		PolylineCell polyline_cell = new PolylineCell();
		current_Mode = Wireframe;
		if(Create_Wireframe)
		{
			for(int i=0; i<height; i++)
			{
				for(int j=0; j<width-1; j++)
				{
					polyline_cell = new PolylineCell();
				
						/* Form the Suare */
					if((i*width)%width==0)
							polyline_cell.addVal(j+(i*width));
					polyline_cell.addVal(j+width+(i*width)); /* lower left-hand corner */
					polyline_cell.addVal(j+1+width+(i*width)); /* lower right-hand corner */
					polyline_cell.addVal(j+1+(i*width)); /*upper right-hand corner */
					polyline_cell.addVal(j+(i*width)); /* upper left-hand corner */
				
					polyline_cell.setMaterials(material);
					polyline_cell.setPoints(point_set);
					Grid.addElement(polyline_cell);
				}
				
			}
	
			Create_Wireframe = false;
		}
	}

	public void DisplayPlane() 
	{
		current_Mode = Solid;
		
		if(Create_Plane)
		{
			for(int i=0; i<height; i++)
			{
				for(int j=0; j<width-1; j++)
				{
					PolygonCell polygon_cell = new PolygonCell();

					/* Form the Suare */
					polygon_cell.addVal(j+(i*width)); /* upper left-hand corner */
					polygon_cell.addVal(j+1+(i*width)); /*upper right-hand corner */
					polygon_cell.addVal(j+1+width+(i*width)); /* lower left-hand corner */
					polygon_cell.addVal(j+width+(i*width)); /* lower right-hand corner */

					polygon_cell.setMaterials(material);
					polygon_cell.setPoints(point_set);
					Plane.addElement(polygon_cell);

				}
			}

			Create_Plane = false;
		}

	}

	public void DisplayContours()
	{
		current_Mode = Contours;
		if(Create_Contour)
		{
			Contour();
			Create_Contour=false;
		}
				
	}

	public void SetContours(float range[]) 
	{
		float i;
		for (i = range[0];i < range[1];i+=1.0F)
				interval.addElement(new Float(i));
		updateTime();
	}


	public void Contour() 
	{ 
		int current_count, current_quad;
		Material material;
		MaterialSet color_material;
		float value;
		int a, i, j;
		int square_edge[] =  new int[4];

		for (a=0;a<interval.size();a++) 
		{
			value = ((Float)interval.elementAt(a)).floatValue();
			material = scal_data.colorValue(value);
			color_material = new MaterialSet();
			color_material.addMaterial(material);
		
			for(i=0; i<height-1; i++)
			{
				for (j=0;j<(width-1);j++) 
				{

					square_edge[0]=(j+(i*width));
					square_edge[1]=(j+1+(i*width)); 
					square_edge[2]=(j+1+width+(i*width)); 
					square_edge[3]=(j+width+(i*width)); 

					if(square_edge[0] >(height-1)*(width-1))
						break;
				
					/*The marching square algorithm is only for one suare or quad, so
					  I have to apply this algorithm to each square by calling the following
					  function each time 
					*/
					marching_square(square_edge,value, color_material);
				}			
			}
		}
	}

	public void marching_square(int square_edge[], float value, MaterialSet color_material)
	{
		int Case_Mask[] = {1, 2, 4, 8};
		int  edge[];
		int index, vert[][]=new int[2][2];
		PointSet point = new PointSet();
		float t, val_1;
		PointType x, x1, x2;
		LineCell line;
		int i, j, range1, range2, num_point=0;
		float w[] = new float[3];
		index = 0;
		j =0;

		for(i=0; i<4; i++)
		{
			if(scal_data.getScalar(square_edge[i]) >= value)
				index |= Case_Mask[i];
		}
		edge = lineCases[index];
		
		while(edge[j] != -1)	
		{
			line = new LineCell();
			line.setMaterials(color_material);
			vert[0] = edges[edge[0]];
			vert[1] = edges[edge[1]];
			for(int q=0; q<2; q++)
			{
				for(int r = 0; r<2; r++)
				{
					if(vert[q][r]==2)
						vert[q][r] = width+1;
					if(vert[q][r]==3)
						vert[q][r] = width;
				}
			}
			for(int q =0; q<2; q++)
			{
				range1 = square_edge[0]+vert[q][0];
				range2 = square_edge[0]+vert[q][1];
				val_1 = value - scal_data.getScalar(range1);
				
				t = val_1/(scal_data.getScalar(range2)-scal_data.getScalar(range1));
				x1 = point_set.getPoint(range1);
				x2 = point_set.getPoint(range2);
				w[0] = x1.x + t * (x2.x - x1.x);
				w[1] = x1.y + t * (x2.y - x1.y);
				w[2] = x1.z + t * (x2.z - x1.z);
				x = new PointType(w);
				point.addPoint(x);
				line.addVal(num_point++);
			}
			line.setPoints(point);
			ContourCell.addElement(line);

			j = j+2;
		}
	
	
	}

}

