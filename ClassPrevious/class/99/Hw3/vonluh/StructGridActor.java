//////////////////////////////////////////////////////////////////////////
// regular topology
// irregular geometry
//
/////////////////////////////////////////////////////////////////////////

import java.util.Vector;

//
//
//
public class StructGridActor extends ParentObject
{

	
	//
	// grid attributes and default values
	//
	int width = 0;
	int height= 0;
	int deltaX = 1;
	int deltaY = 1;
	int x1= 0;
	int y1= 0;
	int z1= 0;


	//
	// Collection of Cells and stuff that makes up an grid
	// separation between the wireFrame/Plane stuff and the Contour stuff. 
	//
	protected Vector quadCells;

	protected Vector contourPolyLineCells; 
	protected Vector contourValues;
	protected PointSet contourPtSet;

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
	// -HKVL
	//
	double transPosX;
	double transPosY;
	double transPosZ; 


	//
	// Constructor that creates a Vector class for cells 
	//
	public StructGridActor () 
	{
		orientation = new float[3];
		position = new PointType();
		scale = new float[3];
		scale[0] = scale[1] = scale[2] = 1;

		quadCells = new Vector();

		contourPolyLineCells = new Vector(); 
		contourValues= new Vector(); 
	 	contourPtSet = new PointSet();

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
		for (int i=0;i<quadCells.size();i++) 
		{
		
		// XXX 
		// if cell time > actor time -- needs update 		
		// so... 
		// how do we get the message passed back to the renderer
		// that the actor needs to be re-rendered
		//  

		((Cell)quadCells.elementAt(i)).render(aren);
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
	//  Add Cells to the wireframe/plane collection 
	// 
	public void addQuadCell(Cell cell) 
	{ 
		quadCells.addElement(cell); 
		updateTime(); 	
	}

	// 
	//  Add Cells to the contour's polyline collection
	// 
	public void addContourPolyLineCell(Cell cell) 
	{ 
		contourPolyLineCells.addElement(cell); 
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

	
		if (mtSet.doINeedUpdate(this.getTime())) return true; 
		
		if (ptSet.doINeedUpdate(this.getTime())) return true; 
	
		for (int i=0;i<quadCells.size();i++) 
		{
			if (((Cell)quadCells.elementAt(i)).doINeedUpdate(this.getTime())) return true;
		}
		return false; 
		
	}

	public void SetSize(int w, int h) {};          
	
	public void SetStartPoint(PointType pt) {};           
	
	public void SetDeltas(float deltax, float deltay) {};  

	public void SetPoints(PointSet pts) {};                

	public void SetData(ScalarFloatObject SFdata) {};              
	
	public void DisplayWireframe() {};

	public void DisplayPlane() {};
	
	public void SetContours(Vector vec) {};

	public void Contour() {};

	public void DisplayContours() {};

}