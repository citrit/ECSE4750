/*
*   Face Object...Implementing this class creates an actor that draws a face
*   Author : Joshua M. Temkin
*   Simple Idea for the face object is to create an Actor. Since an Actor is a collection
*   of Cells we just specify a specific type of Actor with specific cells and we can instantiate
*   the actor which will draw the face ....
*
*/


public class FaceObject extends Actor
{
    PointSet ptSet;

    public FaceObject()
    {
        //Here we will create our various Shapes that make up the Face
        ptSet = new PointSet();
        //Create polyLine
        PolyLineCell pCell = new PolyLineCell();
        PointCell ptCell = new PointCell();
        PointCell ptCellB = new PointCell();
        PolygonCell pgCell = new PolygonCell();
        TriangleCell tCell = new TriangleCell();

        float tmp[] = new float[3];
        //Define the points for the face
        tmp[0]= 0.0F;tmp[1]= 0.0F;tmp[2]= 0.0F;ptSet.addPoint(new PointType(tmp));
        tmp[0]= 2.13347F;tmp[1]=2.64008F;tmp[2]=0.0F;ptSet.addPoint(new PointType(tmp));
        tmp[0]= 2.69611F;tmp[1]=2.63786F;tmp[2]=0.0F;ptSet.addPoint(new PointType(tmp));
        tmp[0]= 2.97551F;tmp[1]=2.14949F;tmp[2]=0.0F;ptSet.addPoint(new PointType(tmp));
        tmp[0]= 2.69227F;tmp[1]=1.66334F;tmp[2]=0.0F;ptSet.addPoint(new PointType(tmp));
        tmp[0]= 2.12963F;tmp[1]=1.66556F;tmp[2]=0.0F;ptSet.addPoint(new PointType(tmp));
        tmp[0]= 1.85023F;tmp[1]=2.15393F;tmp[2]=0.0F;ptSet.addPoint(new PointType(tmp));
        tmp[0]= 2.09756F;tmp[1]=2.03418F;tmp[2]=0.1F;ptSet.addPoint(new PointType(tmp));
        tmp[0]=2.16169F;tmp[1]= 1.96473F;tmp[2]= 0.1F;ptSet.addPoint(new PointType(tmp));
        tmp[0]=2.2472F;tmp[1]=  1.92199F;tmp[2]= 0.1F;ptSet.addPoint(new PointType(tmp));
        tmp[0]=2.39683F;tmp[1]=  1.9113F;tmp[2]= 0.1F; ptSet.addPoint(new PointType(tmp));
        tmp[0]=2.59457F;tmp[1]=  1.92199F;tmp[2]= 0.1F;ptSet.addPoint(new PointType(tmp));
        tmp[0]=2.70145F;tmp[1]=  1.96473F;tmp[2]= 0.1F; ptSet.addPoint(new PointType(tmp));
        tmp[0]=2.77093F;tmp[1]=  2.03418F;tmp[2]= 0.1F;ptSet.addPoint(new PointType(tmp));
        tmp[0]=2.41821F;tmp[1]=  2.27992F;tmp[2]= 0.1F;ptSet.addPoint(new PointType(tmp));
        tmp[0]=2.48769F;tmp[1]=  2.11431F;tmp[2]= 0.1F; ptSet.addPoint(new PointType(tmp));
        tmp[0]=2.34874F;tmp[1]=  2.11431F;tmp[2]= 0.1F;  ptSet.addPoint(new PointType(tmp));
        tmp[0]=2.20979F;tmp[1]=  2.39211F;tmp[2]= 0.1F;ptSet.addPoint(new PointType(tmp));
        tmp[0]=2.62663F;tmp[1]=  2.39211F;tmp[2]= 0.1F;    ptSet.addPoint(new PointType(tmp));

        float rgba[] = { 1.0F, 0.0F, 0.0F, 0.1F };
        Material mat;
		MaterialSet matSet;

		matSet = new MaterialSet();
        //Define the Polygon
        rgba[0]=0.0F;rgba[1]=1.0F;rgba[2]= 1.0F;
        mat = new Material(rgba[0],rgba[1],rgba[2],rgba[3]);
        matSet.addMaterial(mat);
        pgCell.setPoints(ptSet);
  	    pgCell.setMaterials(matSet);
        pgCell.addVal(1);  pgCell.addVal(2);  pgCell.addVal(3);
        pgCell.addVal(4);  pgCell.addVal(5);  pgCell.addVal(6);


        matSet = new MaterialSet();
        //Define the Polyline
        rgba[0]=0.0F;rgba[1]=0.0F;rgba[2]= 1.0F;
        mat = new Material(rgba[0],rgba[1],rgba[2],rgba[3]);
        matSet.addMaterial(mat);
        pCell.setPoints(ptSet);
  	    pCell.setMaterials(matSet);
        pCell.addVal(7);  pCell.addVal(8);  pCell.addVal(9);
        pCell.addVal(10);  pCell.addVal(11);  pCell.addVal(12);
        pCell.addVal(13);

        matSet = new MaterialSet();
        //Define the Triangle
        rgba[0]=1.0F;rgba[1]=0.0F;rgba[2]= 1.0F;
        mat = new Material(rgba[0],rgba[1],rgba[2],rgba[3]);
        matSet.addMaterial(mat);
        tCell.setPoints(ptSet);
  	    tCell.setMaterials(matSet);
        tCell.addVal(14);  tCell.addVal(15);  tCell.addVal(16);

        matSet = new MaterialSet();
        //Define a point
        rgba[0]=1.0F;rgba[1]=0.0F;rgba[2]= 0.0F;
        mat = new Material(rgba[0],rgba[1],rgba[2],rgba[3]);
        matSet.addMaterial(mat);
        ptCell.setPoints(ptSet);
  	    ptCell.setMaterials(matSet);
        ptCell.addVal(17);


        matSet = new MaterialSet();
        //Define a point
        rgba[0]=1.0F;rgba[1]=0.0F;rgba[2]= 0.0F;
        mat = new Material(rgba[0],rgba[1],rgba[2],rgba[3]);
        matSet.addMaterial(mat);
        ptCellB.setPoints(ptSet);
  	    ptCellB.setMaterials(matSet);
        ptCellB.addVal(18);

        cells.addElement(pCell);
        cells.addElement(ptCellB);
        cells.addElement(ptCell);
        cells.addElement(tCell);
        cells.addElement(pgCell);

        double j[] = ptSet.getBBox();
        double midX = ( j[0] - j[3])/2;
        double midY = ( j[1] - j[4])/2;
        double midZ = ( j[2] - j[5])/2;

        //center the actor
        translate(midX,midY,midZ);

    }


}