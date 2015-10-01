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

/**
  * LineCell class is a concrete implementation of the Cell class
  * It draws lines between point in the internal array, i.e.
  *   [ 1 2 2 3 3 4 4 5 ] will draw a line from 1-2-3-4-5
  *   [ 1 2 3 4 5 ] will draw a line from 1-2 then 3-4 skipping 5
  */
public class TriangleCell extends Cell
{

	/**
	  * Nothing to do here (yet)
	  */
	public TriangleCell() { }

	/**
	  * Sent by the Renderer to start the drawing process, kind of neat
	  * in that the LineCell sends commands back to the Renderer
	  * on how to draw itself (Remember the Renderer only has a vector
	  * of cells).
	  */
	public void render(Renderer aren)
	{
		int cnt;
		
		cnt = mtSet.size();
		if (cnt == 1) {
			aren.setMaterial(mtSet.getMaterial(0));
		}
		aren.beginDraw(aren.TRIANGLE);
		if (nNormal != null)
			aren.normal(nNormal);
		else
			aren.lighting(false);
			
		for (int i=0;i<intVals.size();i++) {
			if (cnt > 1) {
				aren.setMaterial(mtSet.getMaterial(getVal(i)%cnt));
			}
			aren.vertex(ptSet.getPoint(getVal(i))); 
		}
		aren.endDraw();
	}
}

