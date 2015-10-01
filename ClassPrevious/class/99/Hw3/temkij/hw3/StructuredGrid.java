/*
* Structured Grid Object
* author: Joshua M. Temkin
*
*
*/


import java.util.Vector;

public class StructuredGrid extends Actor
{
    int Width;
    int Height;
    double dX;
    double dY;
    PointType Origin;
    PointSet ptSet = null;
    Vector WireFrameCells;
    Vector PlaneFrameCells;
    Vector ContourSet;
    Vector ContourSetCells;
    boolean translated = false;
    boolean DrawWire = true;
    boolean DrawContour = true;
    boolean DrawPlane = true;
    boolean CellsGenerated = false;
    boolean DrawZdirection = false;
    ScalarData sData = null;
    int lineCases[][];
    int edges[][];
    int caseMask[];
    int NumContours = 6;
    private void init()
    {
        WireFrameCells = new Vector();
        PlaneFrameCells = new Vector();
        ContourSet = new Vector();
        ContourSetCells = new Vector();
        //pts = new PointSet();
        sData = new ScalarData();

        lineCases= new int[16][5];
        caseMask = new int[4];
        caseMask[0]=1;caseMask[1]=2;caseMask[2]=4;caseMask[3]=8;

        lineCases[0][0]=-1;lineCases[0][1]=-1;lineCases[0][2]=-1;lineCases[0][3]=-1;lineCases[0][4]=-1;
        lineCases[1][0]=0;lineCases[1][1]=3;lineCases[1][2]=-1;lineCases[1][3]=-1;lineCases[1][4]=-1;
        lineCases[2][0]=1;lineCases[2][1]=0;lineCases[2][2]=-1;lineCases[2][3]=-1;lineCases[2][4]=-1;
        lineCases[3][0]=1;lineCases[3][1]=3;lineCases[3][2]=-1;lineCases[3][3]=-1;lineCases[3][4]=-1;
        lineCases[4][0]=2;lineCases[4][1]=1;lineCases[4][2]=-1;lineCases[4][3]=-1;lineCases[4][4]=-1;
        lineCases[5][0]=0;lineCases[5][1]=3;lineCases[5][2]=2;lineCases[5][3]=1;lineCases[5][4]=-1;
        lineCases[6][0]=2;lineCases[6][1]=0;lineCases[6][2]=-1;lineCases[6][3]=-1;lineCases[6][4]=-1;
        lineCases[7][0]=2;lineCases[7][1]=3;lineCases[7][2]=-1;lineCases[7][3]=-1;lineCases[7][4]=-1;
        lineCases[8][0]=3;lineCases[8][1]=2;lineCases[8][2]=-1;lineCases[8][3]=-1;lineCases[8][4]=-1;
        lineCases[9][0]=0;lineCases[9][1]=2;lineCases[9][2]=-1;lineCases[9][3]=-1;lineCases[9][4]=-1;
        lineCases[10][0]=1;lineCases[10][1]=0;lineCases[10][2]=3;lineCases[10][3]=2;lineCases[10][4]=-1;
        lineCases[11][0]=1;lineCases[11][1]=2;lineCases[11][2]=-1;lineCases[11][3]=-1;lineCases[11][4]=-1;
        lineCases[12][0]=3;lineCases[12][1]=1;lineCases[12][2]=-1;lineCases[12][3]=-1;lineCases[12][4]=-1;
        lineCases[13][0]=0;lineCases[13][1]=1;lineCases[13][2]=-1;lineCases[13][3]=-1;lineCases[13][4]=-1;
        lineCases[14][0]=3;lineCases[14][1]=0;lineCases[14][2]=-1;lineCases[14][3]=-1;lineCases[14][4]=-1;
        lineCases[15][0]=-1;lineCases[15][1]=-1;lineCases[15][2]=-1;lineCases[15][3]=-1;lineCases[15][4]=-1;

        edges = new int[4][2];
        edges[0][0]=0;edges[0][1]=1;
        edges[1][0]=1;edges[1][1]=2;
        edges[2][0]=2;edges[2][1]=3;
        edges[3][0]=3;edges[3][1]=0;

    }
    public void toggleWire()
    {
        DrawWire = !DrawWire;
        objectTime = 0;
    }
    public void toggleContour()
    {
        DrawContour = !DrawContour;
        objectTime = 0;
    }
    public void togglePlane()
    {
        DrawPlane = !DrawPlane;
        objectTime = 0;
    }
    public void toggleStrecthZ()
    {

        //CellsGenerated = false;
        //ptSet = null;
        DrawZdirection = !DrawZdirection;
        generatePtSet();
        objectTime = 0;
        //WireFrameCells.removeAllElements();
        //PlaneFrameCells.removeAllElements();
        for (int x = 0; x < WireFrameCells.size();x++)
        {
            ((Cell)WireFrameCells.elementAt(x)).setPoints(ptSet);
            ((Cell)PlaneFrameCells.elementAt(x)).setPoints(ptSet);
        }

        ContourSetCells.removeAllElements();
        contour(NumContours);
    }
    private void calcCenterBox()
    {
           double j[] = ptSet.getBBox();

           double midX = ( j[0] - j[3])/2;
           double midY = ( j[1] - j[4])/2;
           double midZ = ( j[2] - j[5])/2;

           if (!translated)
           {
             translate(midX,midY,midZ);
             translated = true;
           }
    }

    private void parseFile(String filename)
    {
        //Reads the example.grid file
       objectReader fileRead;
       fileRead = new objectReader(filename);
       //first two are ints for height and width
       //the rest are the scalar data values;
       Width = fileRead.getInt();
       Height = fileRead.getInt();

       //Rest is the h x w in floats for the scalar data

       while (!fileRead.eof())
       {
          sData.addValue(fileRead.getFloat());
       }

    }

    public StructuredGrid(String filename)
    {
       Width = 0;
       Height = 0;
       float t[] = new float[3];
       t[0] = 0.0F;t[1] = 0.0F;t[2] = 0.0F;
       Origin = new PointType(t);
       dX = 0.25F;
       dY = 0.25F;

       init();
       parseFile(filename);
    }

    public StructuredGrid(int w,int h,double dx,double dy,PointType a,String filename)
    {
       Width = w;
       Height = h;
       dX = dx;
       dY = dy;
       Origin = a;

       init();
       parseFile(filename);
    }

    public void SetSize(int w,int h)
    {
        Width = w;
        Height = h;
    }

    public void SetDeltas(double dx,double dy)
    {
        dX = dx;
        dY = dy;
    }

    public void SetStartPoint(PointType pt)
    {
        Origin = pt;
    }

    public void SetPoints(PointSet pt)
    {
        ptSet = pt;
        calcCenterBox();
    }

    public void SetData(ScalarData data)
    {
       sData = data;
    }

    public void SetContours(Vector v)
    {
        ContourSet = (Vector)v.clone();
    }

    //This generates the cells
    private void generatePtSet()
    {
        float x;
        float y;
        float z;
        float t[] = new float[3];
        int xx;
        int yy;

        ptSet = new PointSet();
        yy = 0;
        for (y = Origin.y; y < Origin.y + (Height * dY); y += dY)
        {
           xx =0;
           for(x = Origin.x; x < Origin.x + (Width * dX); x+=dX)
           {
               if(!DrawZdirection)
               {
                 t[0] = x;t[1] = y; t[2] = Origin.z;
               }
               else
               {
                 t[0] = x;t[1] = y; t[2] = sData.getValue(xx+ (yy*Width));
               }
                 ptSet.addPoint(new PointType(t));
               xx++;
           }
           yy++;
        }
        calcCenterBox();
    }

    private void generateCells()
    {
        float t[] = new float[4];

        t[0] = 1.0F;
        t[1] = 1.0F;
        t[2] = 1.0F;
        t[3] = 1.0F;

        CellsGenerated = true;
        if (ptSet == null)
           generatePtSet();

        Material mat = new Material(t);
    	MaterialSet matSet = new MaterialSet();
	    matSet.addMaterial(mat);
	    //WireFrameCells = new Vector();
	    //PlaneFrameCells = new Vector();
	    //ContourSetCells = new Vector();
        //Here we are going to use Polygons and create squares
        for (int y =0; y < Height-1; y ++)
        {


            for (int x = 0; x < Width-1; x++)
            {
                PolyLineCell pt = new PolyLineCell();
                PolygonCell dt = new PolygonCell();
                //THis needs to be expanded to account for scalar data and lookups
                pt.setPoints(ptSet);
                dt.setPoints(ptSet);
                //Material mat = new Material(t);
            	MaterialSet dmatSet = new MaterialSet();
	            //
  		        pt.setMaterials(matSet);


                pt.addVal(x + (y*Width));
                pt.addVal(x + 1 + (y * Width));
                pt.addVal(x + 1 + ((y+1) * Width));
                pt.addVal(x + ((y+1) * Width));
                pt.addVal(x + (y*Width));

                dt.addVal(x + (y*Width));

                dt.addVal(x + 1 + (y * Width));
                dt.addVal(x + 1 + ((y+1) * Width));
                dt.addVal(x + ((y+1) * Width));

                dmatSet.addMaterial(sData.getColor(sData.getValue(x +(y *Width))   ));
                dmatSet.addMaterial(sData.getColor(sData.getValue(x + 1 + (y *Width) )));
                dmatSet.addMaterial(sData.getColor(sData.getValue(x + 1 + ((y +1) *Width))));
                dmatSet.addMaterial(sData.getColor(sData.getValue(x +((y + 1) *Width))));

                dt.setMaterials(dmatSet);
                WireFrameCells.addElement(pt);
                PlaneFrameCells.addElement(dt);
            }


        }
        contour(NumContours);
        updateTime();
    }

    protected boolean checkRenderStat(Renderer aren)
    {

         if (DisplayListIndex == -1)
         {
             DisplayListIndex = aren.generateDL();
             return true;
         }
         //check times
         for (int i=0;i<WireFrameCells.size();i++)
         {
			if( ((Cell)WireFrameCells.elementAt(i)).checkRenderStat())
			{
		         //Means a cell has changed so we need to reBuild the display list
		         return true;
		    }
		    else if ( objectTime <= ((Cell)WireFrameCells.elementAt(i)).getTime() )
            {
                return true;
            }
         }
         for (int i =0; i <PlaneFrameCells.size();i++)
         {
		    if ( ((Cell)PlaneFrameCells.elementAt(i)).checkRenderStat())
		    {
		        return true;
		    }
		    else if ( objectTime <= ((Cell)PlaneFrameCells.elementAt(i)).getTime() )
            {
                return true;
            }
         }
         for (int i = 0; i < ContourSetCells.size();i++)
         {
		    if ( ((Cell)ContourSetCells.elementAt(i)).checkRenderStat() )
		    {
		        return true;
		    }
            else if ( objectTime <= ((Cell)ContourSetCells.elementAt(i)).getTime() )
            {
                return true;
            }
		 }

         return false;

    }

    /** Method called by Renderer to signal an update is needed. */
	public void render(Renderer aren)
	{
		boolean reRender = checkRenderStat(aren);
		aren.pushModelMatrix();

		if (!CellsGenerated)
		{
		    generateCells();
		    CellsGenerated = true;
		    reRender = true;
		}
		aren.setOrientation(orientation, scale, position);
		if (reRender)
		{
		    updateTime();

    		aren.beginDL(DisplayListIndex);
    	    if (DrawPlane)
		    {
		        for (int i=0;i<PlaneFrameCells.size();i++) {


	        		((Cell)PlaneFrameCells.elementAt(i)).render(aren);
		        }


		    }

    	    if (DrawWire)
    	    {
        		for (int i=0;i<WireFrameCells.size();i++) {


	        		((Cell)WireFrameCells.elementAt(i)).render(aren);
		        }
		    }
		    if (DrawContour)
		    {

		        for (int i=0;i<ContourSetCells.size();i++) {


	        		((Cell)ContourSetCells.elementAt(i)).render(aren);
		        }

		    }
		    aren.endDL(DisplayListIndex);
		}
		else
		{
		    aren.callDL(DisplayListIndex);
		}
		aren.popModelMatrix();
	}

    public void contour(int num)
    {
       int x,y,z;
       SetContours(sData.getContourSet(num));
       float f;
       LineCell lt;
       PointSet pts;
       float t[] = new float[4];

        t[0] = 1.0F;
        t[1] = 1.0F;
        t[2] = 1.0F;
        t[3] = 1.0F;
       Material mat = new Material(t);
    	//MaterialSet matSet = new MaterialSet();
	   // matSet.addMaterial(mat);

       for (z = 0; z < ContourSet.size();z++)
       {
          for (y = 0; y < Height - 1; y++)
          {
             for (x = 0; x < Width - 1; x++)
             {
                Float ft = (Float)ContourSet.elementAt(z);
                f = ft.floatValue();
                int tmt = x+(y * (Width -1));
                //System.out.println("float value = " + f + "cell # " + tmt);
                pts = marchSquare(f, (Cell)PlaneFrameCells.elementAt(x+(y * (Width -1))));

                //Create LineCells
                for (int r = 0; r < pts.size() - 1;r++)
                {
                    lt = new LineCell();
                     //Material mat = new Material(t);
                 	MaterialSet matSet = new MaterialSet();
	                matSet.addMaterial(sData.getColor(f));
                    lt.setMaterials(matSet);
                    lt.setPoints(pts);

                    lt.addVal(r);
                    lt.addVal(r+1);
                    ContourSetCells.addElement(lt);
                }
             }
          }


       }
    }

    public PointSet marchSquare(float value,Cell square)
    {
        PointSet pts = new PointSet();
        int i,index;
        int vert[];
        int linecas[];
        float t;
        float x1[] = new float[3];
        float x2[] = new float[3];
        float x[] = new float[3];

        for(i = 0,index = 0; i < 4; i++)
        {
            //System.out.println("square val = " + square.getVal(i));
            if (sData.getValue(square.getVal(i)) >= value)
               index |= caseMask[i];
        }

        linecas = lineCases[index];

        int tindex =0;
        for ( ; linecas[tindex] > -1; tindex +=2)
        {
            for (i = 0; i < 2; i++)
            {
                vert = edges[linecas[i]];
                t = (value - sData.getValue(square.getVal(vert[0])))/
                    (sData.getValue(square.getVal(vert[1])) - sData.getValue(square.getVal(vert[0])));
                PointType pt = ptSet.getPoint(square.getVal(vert[0]));
                x1[0] = pt.x; x1[1] = pt.y; x1[2] = pt.z;
                pt = ptSet.getPoint(square.getVal(vert[1]));
                x2[0] = pt.x; x2[1] = pt.y; x2[2] = pt.z;
                //System.out.println("t = " + t);
                for(int j = 0; j <3;j++)
                {
                    x[j] = x1[j] + t * (x2[j] - x1[j]);
                }
                pt = new PointType(x);
                pts.addPoint(pt);
            }
        }
        return pts;
   }

}