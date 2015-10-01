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
	int num;
	boolean nu = true;

	/** Constructor that creates a Vector class for cells */
	public Actor() 
	{
		cells = new Vector();
		orientation = new float[3];
		position = new PointType();
		scale = new float[3];
		scale[0] = scale[1] = scale[2] = 1;
	}

	/** Method called by Renderer to signal an update is needed. */
	public void render(Renderer aren)
	{
		aren.pushModelMatrix();
		aren.setOrientation(orientation, scale, position);

		if(nu)
		{
			num = aren.generateDL();
			aren.beginDL(num);
			for (int i=0;i<cells.size();i++) {
				// If the cell is newer than the actor, render it
//				if(getTime()<((Cell)cells.elementAt(i)).getTime())
					((Cell)cells.elementAt(i)).render(aren);
			}
			aren.endDL(num);
			aren.callDL(num);
//			updateTime();
			nu = false;
		}
		else
			aren.callDL(num);
		aren.popModelMatrix();
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
	public void rotateZ(float angle)
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

	/** Add Cells to the actors collection. */
	public void addCell(Cell cell) { cells.addElement(cell); }

	public void update() { nu=true; }
}

