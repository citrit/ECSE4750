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

//
//Actor class, this class holds a collection of cells and properties.
//
public class Actor extends ParentObject
{

	//
	// Collection of Cells and stuff that makes up an entity
	//
	Vector cells;
	protected PointSet ptSet;
	protected MaterialSet mtSet;

	//
	// display list associated with this actor
	// initialized to something impossible. 
	int DList = -1;


	//
	// Orientation as rotations around X,Y, and Z 
	//
	float orientation[];
	PointType position; 
	float scale[]; 

	//
	// translation position of actor for viewing 
	// originally created to support Parser.java and need
	// to determine bounding box
	// useful because we have only one actor
	// -HKVL
	//
	double transPosX;
	double transPosY;
	double transPosZ; 


	//
	// Constructor that creates a Vector class for cells 
	//
	public Actor() 
	{
		cells = new Vector();
		orientation = new float[3];
		position = new PointType();
		scale = new float[3];
		scale[0] = scale[1] = scale[2] = 1;
		ptSet = new PointSet();
		mtSet = new MaterialSet();
	}

	//
	// Simply set the internal pointer to the Material Set
	//
	public void setMaterials(MaterialSet mat) 
	{ 
		mtSet = mat; 
	}


	//
	// Set the internal pointer to the current PointSet
	//
	public void setPoints(PointSet pts) 
	{ 
		ptSet = pts; 
	}

	//
	// Method called by Renderer to signal an update is needed. 
	//
	public void render(Renderer aren)
	{
		aren.pushModelMatrix();
		aren.setOrientation(orientation, scale, position);
		for (int i=0;i<cells.size();i++) 
		{
		
		// XXX 
		// if cell time > actor time -- needs update 		
		// so... 
		// how do we get the message passed back to the renderer
		// that the actor needs to be re-rendered
		//  

		((Cell)cells.elementAt(i)).render(aren);
		}
		aren.popModelMatrix();

	}

	
	// 
	//  Change orientation 
	// 
	public void rotateX(float angle)
	{
		orientation[0] += angle;
	}
	
	// 
	//  Change orientation 
	// 
	public void rotateY(float angle)
	{
		orientation[1] += angle;
	}

	// 
	//  Change orientation 
	// 
	public void rotatez(float angle)
	{
		orientation[2] += angle;
	}


	public void scale(float sc[])
	{
		scale[0] = sc[0]; 
		scale[1] = sc[1]; 
		scale[2] = sc[2];
	}

	public void translate(float x, float y, float z)
	{
		position.x += x;
		position.y += y;
		position.z += z;
	}

	public void translate(double x, double y, double z)
	{
		position.x += x;
		position.y += y;
		position.z += z;
	}

	// 
	//  Add Cells to the actors collection. 
	// 
	public void addCell(Cell cell) 
	{ 
		cells.addElement(cell); 
		updateTime(); 	
	}

	public  boolean doINeedUpdate(long objectTime)
	{

		//
		// compares time with time of calling object
		// then checks time of mtSet and ptSet
		// 
		
		System.out.println("ACTOR time:" + getTime());
		if (this.getTime() > objectTime ) 
		 	 return true;

		// XXX 
		// right now, we don't use mtSets or ptSets in the actor
		//
		//if (mtSet != null)
			if (mtSet.doINeedUpdate(this.getTime())) return true; 
		//if (ptSet != NULL)
			if (ptSet.doINeedUpdate(this.getTime())) return true; 
	
		for (int i=0;i<cells.size();i++) 
		{
			if (((Cell)cells.elementAt(i)).doINeedUpdate(this.getTime())) return true;
		}
		return false; 
		
	}


}

