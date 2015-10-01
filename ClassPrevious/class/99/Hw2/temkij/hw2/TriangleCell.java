/*
* TriangleCell java file extends Cell
* author: Joshua M. Temkin
* File used to tell the system how to render a Triangle

*/


public class TriangleCell extends Cell
{

    //Constructor
    public TriangleCell()
    {
    }


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