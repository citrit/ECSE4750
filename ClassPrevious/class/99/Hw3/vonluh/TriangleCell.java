//////////////////////////////////////////////////////////////////////////
//
//
//  Author:  Heidi K. von Ludewig
//  Homework #2
//  Date:    January 1998
//
/////////////////////////////////////////////////////////////////////////

/**
  * TriangleCell class is a concrete implementation of the Cell class
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
		aren.beginDraw(aren.TRIANGLE);
		for (int i=0;i<intVals.size();i++) {
			aren.setMaterial(mtSet.getMaterial(getVal(i)%cnt));
			aren.vertex(ptSet.getPoint(getVal(i))); 
		}
		aren.endDraw();
	}
}