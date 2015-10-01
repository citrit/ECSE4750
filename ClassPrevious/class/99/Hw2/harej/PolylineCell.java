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
  * PolylineCell class is a concrete implementation of the Cell class
  * It draws line segments in a continuous "piece" between points in
  * the internal array, i.e.
  *   [ 1 2 3 4 5 ] will draw a line from 1-2-3-4-5
  *   The start and end points (1 and 5) will not be connected
  */
public class PolylineCell extends Cell
{

	/**
	  * Nothing to do here (yet)
	  */
        public PolylineCell() { }

	/**
	  * Sent by the Renderer to start the drawing process, kind of neat
          * in that the PolylineCell sends commands back to the Renderer
	  * on how to draw itself (Remember the Renderer only has a vector
	  * of cells).
	  */
	public void render(Renderer aren)
	{
		int cnt;

		cnt = mtSet.size();
                aren.beginDraw(aren.POLYLINE);
		for (int i=0;i<intVals.size();i++) {
			aren.setMaterial(mtSet.getMaterial(getVal(i)%cnt));
			aren.vertex(ptSet.getPoint(getVal(i))); 
		}
		aren.endDraw();
	}
}

