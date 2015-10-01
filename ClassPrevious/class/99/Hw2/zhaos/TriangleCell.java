// Author:	Shuo William Zhao	zhaos@rpi.edu
// Class:	Advanced Computer Graphics and Data Visualization
//			Rensselaer Polytechnic Institute
// Date:	Feb. 27th, 1999
//
////////////////////////////////////////////////////////////////////

/*	TriangleCell */

public class TriangleCell extends Cell
{
	public TriangleCell()
	{}

	public void render(Renderer area)
	{
		int cnt;
		
		cnt = mtSet.size();
		area.beginDraw(area.TRIANGLE);
		for(int i=0; i<intVals.size(); i++)
		{
			area.setMaterial(mtSet.getMaterial(getVal(i)%cnt));
			area.vertex(ptSet.getPoint(getVal(i)));
		}
		area.endDraw();
	}
}
