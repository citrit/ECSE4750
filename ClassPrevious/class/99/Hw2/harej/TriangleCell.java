//////////////////////////////////////////////////////////////////////////
//
//  This code is for high grade purposes only. It was generated for
//  use in a graduate level course to show that I have some idea that
//  I know what I'm doing and so I could get a good grade.
//
//  Author:  John R. Hare     harej@rpi.edu
//  Class:   Advanced Computer Graphics and Data visualization
//           Rensselaer Polytechnic Institute
//  Date:    March 1999
//
/////////////////////////////////////////////////////////////////////////

/**
  * TriangleCell class is a concrete implementation of the Cell class
  * It draws line segments connecting three points in the internal 
  * internal array to form a triangle, i.e.
  *   [ 1 2 3 4 5 6] will draw 2 triangles with vertices 1, 2, 3
  *   and 4, 5, 6 respectively.  In other words lines 1-2, 2-3, 3-1
  *   form the first triangle and lines 4-5, 5-6, 6-4 form the second
  */
public class TriangleCell extends Cell
{

	/**
	  * Nothing to do here (yet)
	  */
        public TriangleCell() { }

	/**
	  * Sent by the Renderer to start the drawing process, kind of neat
          * in that the TriangleCell sends commands back to the Renderer
	  * on how to draw itself (Remember the Renderer only has a vector
	  * of cells).
	  */
	public void render(Renderer aren)
	{
		int cnt;

		cnt = mtSet.size();
                aren.beginDraw(aren.TRIANGLE);
		for (int i=0;i<intVals.size();i++) {
			aren.setMaterial(mtSet.getMaterial(getVal(i)%cnt));
			aren.vertex(ptSet.getPoint(getVal(i))); 
		}
		aren.endDraw();
	}
}

