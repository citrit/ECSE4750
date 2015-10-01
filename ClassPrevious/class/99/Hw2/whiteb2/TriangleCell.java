// By: Bryan Whitehead		whiteb2@rpi.edu

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
