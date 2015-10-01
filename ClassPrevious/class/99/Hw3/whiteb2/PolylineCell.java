// By:	Bryan Whitehead 	whiteb2@rpi.edu

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
