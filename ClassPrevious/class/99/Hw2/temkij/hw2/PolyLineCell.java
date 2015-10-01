/*
* PolyLine Java File
* author: Joshua M. Temkin
*   File extends a cell overriden the render method for telling the system how to draw
*/


public class PolyLineCell extends Cell
{
    //Simple constructor
    public PolyLineCell()
    {

    }

    //Override the cell method. Trick of OOP to be able to draw any type of object
    public void render(Renderer aren)
	{
		int cnt;

        cnt = mtSet.size();
       // System.out.println("Poly Render: " + cnt);
		aren.beginDraw(aren.POLYLINE);
        for (int i=0;i<intVals.size();i++) {
			aren.setMaterial(mtSet.getMaterial(getVal(i)%cnt));
			aren.vertex(ptSet.getPoint(getVal(i)));
			//(ptSet.getPoint(getVal(i))).display();
		}
		aren.endDraw();

	}

}