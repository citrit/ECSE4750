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
    int DisplayListIndex = -1;

	/** Constructor that creates a Vector class for cells */
	public Actor()
	{
		cells = new Vector();
		orientation = new float[3];
		position = new PointType();
		scale = new float[3];
		scale[0] = scale[1] = scale[2] = 1;
	}
    public void toggleWire()
    {
        //DrawWire = !DrawWire;
       // objectTime = 0;
    }
    public void toggleStrecthZ()
    {
    }
    public void toggleContour()
    {
        //DrawContour = !DrawContour;
       // objectTime = 0;
    }
    public void togglePlane()
    {
        //DrawPlane = !DrawPlane;
       // objectTime = 0;
    }
    protected boolean checkRenderStat(Renderer aren)
    {

         if (DisplayListIndex == -1)
         {
             DisplayListIndex = aren.generateDL();
             return true;
         }
         //check times
         for (int i=0;i<cells.size();i++)
         {
			if( ((Cell)cells.elementAt(i)).checkRenderStat())
			{
		         //Means a cell has changed so we need to reBuild the display list
		         return true;
		    }
            else if ( objectTime <= ((Cell)cells.elementAt(i)).getTime() )
            {
                return true;
            }
		 }

         return false;

    }
	/** Method called by Renderer to signal an update is needed. */
	public void render(Renderer aren)
	{
		boolean reRender = checkRenderStat(aren);
		aren.pushModelMatrix();
		aren.setOrientation(orientation, scale, position);
		if (reRender)
		{
		    updateTime();

    		aren.beginDL(DisplayListIndex);
    		for (int i=0;i<cells.size();i++) {


	    		((Cell)cells.elementAt(i)).render(aren);
		    }
		    aren.endDL(DisplayListIndex);
		}
		else
		{
		    aren.callDL(DisplayListIndex);
		}
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

