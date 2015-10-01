/**
  * PolygonCell class is a concrete implementation of the Cell class
  * It draws lines between point in the internal array, i.e.
  *   [ 1 2 3 4 5 ] will draw a polygon thorugh the points and close 
  *   from 1 to 5.
  */
public class PolylineCell extends Cell
{

	/**
	  * Nothing to do here (yet)
	  */
	public PolylineCell() { }


	public void render(Renderer aren)
	{
		int cnt;

		cnt = mtSet.size();
		aren.beginDraw(aren.LINE);
		for (int i=0;i<intVals.size();i++) {
			aren.setMaterial(mtSet.getMaterial(getVal(i)%cnt));
			aren.vertex(ptSet.getPoint(getVal(i))); 
		}
		aren.endDraw();
	}
}
