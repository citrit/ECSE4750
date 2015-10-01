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
  * PolygonCell class is a concrete implementation of the Cell class
  * It draws lines between point in the internal array, i.e.
  *   [ 1 2 3 4 5 ] will draw a polygon thorugh the points and close 
  *   from 1 to 5.
  */
public class PolygonCell extends Cell
{

	/**
	  * Nothing to do here (yet)
	  */
	public PolygonCell() { }

	/**
	  * Sent by the Renderer to start the drawing process, kind of neat
	  * in that the LineCell sends commands back to the Renderer
	  * on how to draw itself (Remember the Renderer only has a vector
	  * of cells).
	  */
	public void render(Renderer aren)
	{
		int cnt, i;

		cnt = mtSet.size();
		// Load the texture first.
		if (imTex != null) {
			imTex.render(aren);
		}
		if (nNormal != null)
			aren.normal(nNormal);
		else
			aren.lighting(false);

		aren.beginDraw(aren.POLYGON);
		if (cnt == 1) { // we have only one material set it outside the loop
			aren.setMaterial(mtSet.getMaterial(0));
			for (i=0;i<intVals.size();i++) {
				if (tCoords != null)
					aren.texCoord(tCoords.getTexCoord(i));
				aren.vertex(ptSet.getPoint(getVal(i))); 
			}
		}
		else {
			for (i=0;i<intVals.size();i++) {
				aren.setMaterial(mtSet.getMaterial(getVal(i)%cnt));
				if (tCoords != null)
					aren.texCoord(tCoords.getTexCoord(i));
				aren.vertex(ptSet.getPoint(getVal(i))); 
			}
		}
		aren.endDraw();
	}
}

