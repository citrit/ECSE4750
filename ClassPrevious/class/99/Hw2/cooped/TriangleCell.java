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
  * TriangleCell
  */
public class TriangleCell extends Cell
{

	/** Render method is called by the Renderer and this Cell "tells"
	  * The renderer how to draw itself.
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


