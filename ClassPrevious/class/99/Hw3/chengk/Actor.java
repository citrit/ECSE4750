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
public class Actor extends ParentObject
{

	/**
	  * Collection of Cells that makes up an entity
	  */
	Vector cells;

	/** Orientation as rotations around X,Y, and Z */
	float orientation[];
	PointType position; // Obvious
	float scale[]; // Obvious
	char draw;

	Vector gcells;
    	MaterialSet gmat;
    	int dimX, dimY;
        float deltaX, deltaY;
        PointType begin;
        PointSet setpt;
        ScalarData datamap = new ScalarData();



	/** Constructor that creates a Vector class for cells */
	public Actor() 
	{
		cells = new Vector();
		orientation = new float[3];
		position = new PointType();
		scale = new float[3];
		scale[0] = scale[1] = scale[2] = 1;
		draw='w';
		deltaX=deltaY=1;
		dimX = dimY = 0;
		setpt = new PointSet();
		datamap = null;
		gcells = new Vector();
		begin = new PointType();
		gmat = new MaterialSet();
	}

	/** Method called by Renderer to signal an update is needed. */
	public void render(Renderer aren)
	{
		int i;
		
		if(draw == 'w')
		  for (i=0;i<gcells.size();i+=2)
		    	 ((Cell)gcells.elementAt(i)).render(aren);
		if(draw == 's')
		  for (i=1;i<gcells.size();i+=2)
		       	 ((Cell)gcells.elementAt(i)).render(aren);
		
	}

	/** Change orientation */
	public void rotateX(float angle)
	{
		orientation[0] += angle;
	}
	/** Change orientation */
	public void rotateY(float angle)
	{
		orientation[1] += angle;
	}
	/** Change orientation */
	public void rotatez(float angle)
	{
		orientation[2] += angle;
	}
	public void scale(float sc[])
	{
		scale[0] = sc[0]; scale[1] = sc[1]; scale[2] = sc[2];
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

	/** Add Cells to the actors collection. */
	public void addCell(Cell cell) { cells.addElement(cell); }

	public void makegrid(PolylineCell pcell, PolygonCell goncell){
		int pt[]=new int[4], j, i,k;
		
		for (i=0;i<4;i++)pt[i]=0;		
		for (j=1;j<dimY;j++)  //make a grid of polylines and polygons,store them alternatively in vector gcells
			for (i=0;i<dimX;i++,pt[0]++){ 
			        pt[1]=pt[0]+1;pt[2]=pt[1]+dimX;pt[3]=pt[0]+dimX;
				if (i!=dimX-1) {
				    pcell = new PolylineCell();
				    goncell = new PolygonCell();	
				    for(k=0;k<4;k++){
					pcell.addVal(pt[k]);
					goncell.addVal(pt[k]);
				    }
			  	    if (i==0)pcell.addVal(pt[0]);
				    pcell.setMaterials(gmat);
				    goncell.setMaterials(gmat);
				    pcell.setPoints(setpt);
				    goncell.setPoints(setpt);
				    gcells.addElement(pcell);
				    gcells.addElement(goncell);
				}
			}
				
			    	        
 	}
        
}

