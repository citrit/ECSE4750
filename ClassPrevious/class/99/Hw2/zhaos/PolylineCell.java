// Author:	Shuo William Zhao	zhaos@rpi.edu
// Class:	Advanced Computer Graphics and Data Visualization
//			Rensselaer Polytechnic Institute
// Date:	Feb. 27th, 1999
//
////////////////////////////////////////////////////////////////////

/*	PolylineCell */

public class PolylineCell extends Cell
{
	public PolylineCell()
	{}

	public void render(Renderer aren)
	{
		int cnt;
		
		cnt = mtSet.size();
		aren.beginDraw(aren.POLYLINE);
		for(int i=0; i<intVals.size(); i++)
		{
			aren.setMaterial(mtSet.getMaterial(getVal(i)%cnt));
			aren.vertex(ptSet.getPoint(getVal(i)));
		}
		aren.endDraw();
	}
}
