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
import jogl.*;

/**
 * Actor class, this class holds a collection of cells and properties.
 *
 * @author		Thomas D. Citrinit
 * @version		0.1
 */

// Display lists added by Bryan Whitehead
public class Actor extends ParentObject
{

	/**
	  * Collection of Cells that makes up an entity
	  */
	
	Vector cells;
	int DLnumber;
	/** Orientation as rotations around X,Y, and Z */
	float orientation[];
	PointType position; // Obvious
	float scale[]; // Obvious
	JoglCanvas gl;


	/** Constructor that creates a Vector class for cells */
	public Actor() 
	{
		cells = new Vector();
		orientation = new float[3];
		position = new PointType();
		scale = new float[3];
		scale[0] = scale[1] = scale[2] = 1;
		DLnumber=-1;
	}

	public void beginDL(int DL){
		int n;
		if(gl.isList(DL))
		{
			gl.deleteLists(DL, 1);
			n = generateDL();
			DL = n;
		}		
		gl.newList(DL, GL.COMPILE);
	}
	public void endDL(){
		gl.endList();
	}
					
	public void callDL(int DL){
		gl.callList(DL);
	}
	
	public int generateDL(){
		return gl.genLists(1000);
	}

	
	/** Method called by Renderer to signal an update is needed. */
	public void render(Renderer aren)
	{
		if(DLnumber>-1) {
			callDL(DLnumber);
		}
		else {
			DLnumber=generateDL();
			beginDL(DLnumber);
			aren.pushModelMatrix();
			aren.setOrientation(orientation, scale, position);
			for (int i=0;i<cells.size();i++) {
				((Cell)cells.elementAt(i)).render(aren);
			}
			aren.popModelMatrix();
			endDL();
		}
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
}

