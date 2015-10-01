/****
* Data Parser
* author: Joshua M. Temkin
* Parses the example dat file
*
*
******/
import jogl.*;
import java.util.Vector;

public class DataParser
{
    objectReader fileRead;
    PointSet ptSet;
    Actor actor;
    boolean translated = false;
    public DataParser()
    {



    }

    public void parseData(String filename,Vector CellData,Actor acr)
    {
         fileRead = new objectReader(filename);
         actor = acr;
         while (!fileRead.eof())
         {
            //Here we read the first int and expect that is an int
            int typeVal = fileRead.getInt();
            //System.out.println(typeVal);
            switch(typeVal)
            {
                case 0: //Means set of coords
                    parseCoords();
                break;
                case 1: //Means Points
                    parsePoints(CellData);
                break;
                case 2: //means Lines
                    parseLines(CellData);
                break;
                case 3: //Polygons
                    parsePolygons(CellData);
                break;
                case 4: //Triangles
                    parseTriangles(CellData);
                break;
                case 5: //Polylines
                    parsePolyLines(CellData);
                break;
                default:
                   System.out.println("Invalid index for type");
                   break;
            }

          }
       }
        //Parse out the points into pointcell object and store in the Cell vector
        protected void parsePoints(Vector CellData)
        {
            PointCell pt;
            Vector tmp;
            int x;
            float t[] = new float[4];
            int numShapes = fileRead.getInt();
            //Read the material properties
            tmp = readRGBVals();
            convFtoArray(t,tmp);
            // System.out.println("RGB: " + t[0] + "," + t[1] + "," + t[2]);
            Material mat = new Material(t);
    		MaterialSet matSet = new MaterialSet();
	    	matSet.addMaterial(mat);

	    	for (x = 0; x < numShapes;x++)
            {
                pt = new PointCell();
                pt.setPoints(ptSet);
  		        pt.setMaterials(matSet);
                tmp = readSetInt(1); //one point
                int y;
                for (y = 0; y < tmp.size(); y++)
                {

                   Integer ts = (Integer)tmp.elementAt(y);
                   pt.addVal(ts.intValue());
                }
                CellData.addElement(pt);
                actor.addCell(pt);
            }

        }

        //parse the set of points that are used by the various shapes
        protected void parseCoords()
        {
            int numSets = fileRead.getInt(); //Number of sets to read
            int x;
            Vector tmp;
            float t[] = new float[3];
            ptSet = new PointSet(); //makes a new pointset to work with
            //Read the RGB values in this case we can ignore as specified by specs
            //but we need to parse them out
            tmp = readRGBVals();
            for (x = 0; x < numSets; x++)
            {
                tmp = readCoordSet();


                convFtoArray(t,tmp);

                //System.out.println("Points: " + t[0] + "," + t[1] + "," + t[2]);
                ptSet.addPoint(new PointType(t));


            }
            calcCenterBox();


        }

        //TO center the scene
        private void calcCenterBox()
        {
           double j[] = ptSet.getBBox();

           double midX = ( j[0] - j[3])/2;
           double midY = ( j[1] - j[4])/2;
           double midZ = ( j[2] - j[5])/2;

           if (!translated)
           {
             actor.translate(midX,midY,midZ);
             translated = true;
           }
        }



        //To be consistent with the original code which needs arrays rather than vectors
        private void convFtoArray(float t[],Vector tmp)
        {
            int x;
            for (x = 0; x < tmp.size();x++)
            {
                Float td = (Float)tmp.elementAt(x);
                t[x] = td.floatValue();

            }
        }

        //Parse the polyLines into polyLine cell objects
        protected void parsePolyLines(Vector CellData)
        {
            PolyLineCell pt;
            Vector tmp;
            int x;
            float t[]= new float[4];
            int numShapes = fileRead.getInt();

            //Read the material properties
            tmp = readRGBVals();
            convFtoArray(t,tmp);
              //System.out.println("RGB: " + t[0] + "," + t[1] + "," + t[2]);
            Material mat = new Material(t);
    		MaterialSet matSet = new MaterialSet();
	    	matSet.addMaterial(mat);

	    	//set the points
            for (x = 0; x < numShapes;x++)
            {
                pt = new PolyLineCell();
                pt.setPoints(ptSet);
  		        pt.setMaterials(matSet);
                tmp = readSetTillNegOne();
                int y;
                for (y = 0; y < tmp.size(); y++)
                {
                   Integer ts = (Integer)tmp.elementAt(y);
                   pt.addVal(ts.intValue());
                }
                CellData.addElement(pt);
                actor.addCell(pt);
            }

        }

        //Parse triangles into TriangleCell objects
        protected void parseTriangles(Vector CellData)
        {
            TriangleCell pt;
            Vector tmp;
            int x;
            int numShapes = fileRead.getInt();
            float t[] = new float[4];
            //Read the material properties
            tmp = readRGBVals();
            convFtoArray(t,tmp);
              //System.out.println("RGB: " + t[0] + "," + t[1] + "," + t[2]);
            Material mat = new Material(t);
    		MaterialSet matSet = new MaterialSet();
	    	matSet.addMaterial(mat);


	    	//set the points

            for (x = 0; x < numShapes;x++)
            {
                pt = new TriangleCell();
                pt.setPoints(ptSet);
  		        pt.setMaterials(matSet);
                tmp = readSetInt(3); //3 point sets for triangles
                int y;
                for (y = 0; y < tmp.size(); y++)
                {
                   Integer ts = (Integer)tmp.elementAt(y);
                   pt.addVal(ts.intValue());
                }
                CellData.addElement(pt);
                actor.addCell(pt);
            }

        }

        //parse lines into LineCell objects
        protected void parseLines(Vector CellData)
        {
            LineCell pt;
            Vector tmp;
            int x;
            float t[] = new float[4];
            int numShapes = fileRead.getInt();
            //Read the material properties
            tmp = readRGBVals();
            convFtoArray(t,tmp);
              //System.out.println("RGB: " + t[0] + "," + t[1] + "," + t[2] + "," + t[3]);
            Material mat = new Material(t);
    		MaterialSet matSet = new MaterialSet();
	    	matSet.addMaterial(mat);



            for (x = 0; x < numShapes;x++)
            {
                pt = new LineCell();
                pt.setPoints(ptSet);
  		        pt.setMaterials(matSet);
                tmp = readSetInt(2); //3 point sets for triangles
                int y;
                for (y = 0; y < tmp.size(); y++)
                {

                   Integer ts = (Integer)tmp.elementAt(y);
                   pt.addVal(ts.intValue());
                }
                CellData.addElement(pt);
                actor.addCell(pt);
            }
        }

        //Parse Polygons into PolygonCell objects
        protected void parsePolygons(Vector CellData)
        {
            PolygonCell pt;
            Vector tmp;
            int x;
            float t[] = new float[4];
            int numShapes = fileRead.getInt();
            //Read the material properties
            tmp = readRGBVals();
            convFtoArray(t,tmp);
              //System.out.println("RGB: " + t[0] + "," + t[1] + "," + t[2]);
            Material mat = new Material(t);
    		MaterialSet matSet = new MaterialSet();
	    	matSet.addMaterial(mat);

            for (x = 0; x < numShapes;x++)
            {
                pt = new PolygonCell();
                pt.setPoints(ptSet);
  		        pt.setMaterials(matSet);
                tmp = readSetTillNegOne();
                int y;
                for (y = 0; y < tmp.size(); y++)
                {
                   Integer ts = (Integer)tmp.elementAt(y);
                   pt.addVal(ts.intValue());
                }
                CellData.addElement(pt);
                actor.addCell(pt);
            }

        }

        //Get the RGBValues for a given line of data
        protected Vector readRGBVals()
        {
           int x;
           Vector tmp = new Vector();

           for (x = 0; x < 3; x++)
           {
              tmp.addElement(new Float(fileRead.getFloat()));
           }
           //for the Ambient value since not specified in file
           float t= 1.0F;
           tmp.addElement(new Float(t));

           return (Vector)tmp.clone();


        }

         //Reads a set of coordinates
        protected Vector readCoordSet()
        {
            int x;
            Vector tmp = new Vector();

            for (x = 0; x < 3; x++)
            {
                tmp.addElement(new Float(fileRead.getFloat()));
            }
            return (Vector)tmp.clone();
        }

        //Reads ints with a given number to set per set
        protected Vector readSetInt(int numPoints)
        {
            int x;

            Vector tmp = new Vector();

            for(x = 0; x < numPoints;x++)
            {
                tmp.addElement(new Integer(fileRead.getInt()));
            }
            return (Vector)tmp.clone();
        }

        //Reads until a negative one is reached
        protected Vector readSetTillNegOne()
        {
            int x;
            int tempVal = -2;
            Vector tmp = new Vector();

            while(tempVal != -1)
            {
                tempVal = fileRead.getInt();
                //System.out.print(tempVal + " ");
                if (tempVal != -1)
                {
                    tmp.addElement(new Integer(tempVal));
                }
                else
                {
                    //System.out.println(" ");
                }
            }
            return (Vector)tmp.clone();
        }
}//Endclass






