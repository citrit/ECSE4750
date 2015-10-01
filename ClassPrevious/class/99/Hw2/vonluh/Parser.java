

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;


class Parser 
{

	// this needs to be global but allocation needs to be local 
	// because every cell needs to point to a copy !don't delete this!
	// 
	PointSet currentPts;
	boolean firstPtSet = true;

	//Actor actor; 
	double ambiance = 1.0; 

	//
	//
	//
	/////////////////////////////////////////////////////
	public Parser(String fileName, Actor actor) throws IOException
	{
	
	// 
	//global actor object to add cells 
	//

	//System.out.println ("Beginning Parsing");

	try
      	{
	
		File inputfile = new File(fileName);
		FileInputStream myInputStream = new FileInputStream(inputfile);

		int val, i;
		
		StreamTokenizer theTokenizer = new StreamTokenizer(myInputStream);
		int nextToken = theTokenizer.nextToken();	

		while (nextToken != StreamTokenizer.TT_EOF)
		{


			if (nextToken == StreamTokenizer.TT_NUMBER)
			{
				//System.out.println("token is: " + theTokenizer.nval); 


				switch ((int)theTokenizer.nval){

				case 0: //coordinates
					{
					//System.out.println("The value is COORDINATES");
					handleCoordinates(theTokenizer, actor);
					break;
					}
			
				case 1: 
					{
					//System.out.println("The value is POINTS");
					handlePoints(theTokenizer, actor);
					break;
					}

				case 2: 
					{
					//System.out.println("The value is LINES");
					handleLines(theTokenizer, actor);
					break;
					}

				case 3: 
					{
					//System.out.println("The value is POLYGONS");
					handlePolygons(theTokenizer, actor);
					break;
					}
	
				case 4: 
					{
					//System.out.println("The value is TRIANGLES");
					handleTriangles(theTokenizer, actor);
					break;
					}

				case 5: 
					{
					//System.out.println("The value is POLYLINES");
					handlePolylines(theTokenizer, actor);
					break;
					}

				default: //System.out.println("VALUE IS WHATEVER");


			} //end switch



			}

			else {
				System.out.println ("Input file contains incorrect format.");
				System.out.println ("Cannot create object...skipping.");
			}

      			nextToken = theTokenizer.nextToken();
    			}
		  
	}//end try

	catch(Exception e)
     	{
		System.out.println("File Parser Error: "+e);
		System.exit(1);
     	}

	

       }//end constructor



	//
	//
	//
	/////////////////////////////////////////////////////
	public void handleCoordinates(StreamTokenizer theTokenizer, Actor actor)
	{

	try
      	{
		//System.out.println ("Ignoring RGB Values");
		int nextToken = theTokenizer.nextToken();	

		//
		// get the items count
		// 
		int count = (int) theTokenizer.nval;
		
		
		//
		//ignore the RGB values
		//
		nextToken = theTokenizer.nextToken();
		nextToken = theTokenizer.nextToken();	
		nextToken = theTokenizer.nextToken();	

		currentPts = new PointSet();

		//
		// collect the points
		//
		for (int i=1; i<= count; i++)
		{	

			float tmp[] = new float[3];
			//System.out.println("POINT:" +i);
			nextToken = theTokenizer.nextToken();
			tmp[0] = (float) theTokenizer.nval;
			//System.out.println("x: " + theTokenizer.nval);
			
			nextToken = theTokenizer.nextToken();
			tmp[1] = (float) theTokenizer.nval;
			//System.out.println("y: " + theTokenizer.nval);
			
			nextToken = theTokenizer.nextToken();
			tmp[2] = (float) theTokenizer.nval;
			//System.out.println("z: " + theTokenizer.nval);

			currentPts.addPoint(new PointType(tmp));

			//System.out.println();
		}

		//
		// get bounding box for camera position and store in actor
		// 
		if (firstPtSet)
		{
			double boxPoints[] = currentPts.getBBox();

			//for (int i=0; i<6; i++)
			//System.out.println(" bounding box element " + i + " =" + boxPoints[i]);
			firstPtSet = false; 
		
			//
			// set the translation thingy in the actor
			// has to be negative of center of bounding box
			// because....? uh...? duh.....? it works that way!
			// 
			// X points
			 actor.transPosX =  -(((boxPoints[3] - boxPoints[0])+1.2 ) *.5 ) ; 

			// Y points
			 actor.transPosY =  -(((boxPoints[4] - boxPoints[1])+1) *.5 ); 

			// Z points
			 actor.transPosZ =  -((boxPoints[5] - boxPoints[2]) *.5 ); 
			
		}

	}//end try

	catch(Exception e)
     	{
		System.out.println("File Parser Error [reading Coordinates]: "+e);
		System.exit(1);
     	}
	}





	//
	//
	//
	/////////////////////////////////////////////////////
	public void handlePoints(StreamTokenizer theTokenizer, Actor actor)
	{

	try
      	{
		int nextToken = theTokenizer.nextToken();	

		//
		// get the items count
		// 
		int count = (int) theTokenizer.nval;
		//System.out.println("Count:" +count);
		
		//
		//store the RGB values
		//
		nextToken = theTokenizer.nextToken();	
		double R = theTokenizer.nval;
		//System.out.println("R:" +R);
		nextToken = theTokenizer.nextToken();	
		double G = theTokenizer.nval;
		//System.out.println("G:" +G);
		nextToken = theTokenizer.nextToken();	
		double B = theTokenizer.nval;
		//System.out.println("B:" +B);

		//do material stuff
		Material mat;
		MaterialSet matSet;
		mat = new Material((float)R, (float)G, (float)B, (float)ambiance);
		matSet = new MaterialSet();
		matSet.addMaterial(mat);
		
		PointCell ptCell = new PointCell();


		//
		// collect the points
		//
		for (int i=1; i<= count; i++)
		{	
			
			//System.out.println("POINT:" +i);
			nextToken = theTokenizer.nextToken();
			//System.out.println("VALUE: " + (int) theTokenizer.nval);

			ptCell.addVal((int)theTokenizer.nval);


		}

		ptCell.setPoints(currentPts);
		ptCell.setMaterials(matSet);
		actor.addCell(ptCell);


	}//end try

	catch(Exception e)
     	{
		System.out.println("File Parser Error [reading Points]: "+e);
		System.exit(1);
     	}
	}





	//
	//
	//
	/////////////////////////////////////////////////////
	public void handleLines(StreamTokenizer theTokenizer, Actor actor)
	{

	try
      	{
		int nextToken = theTokenizer.nextToken();	

		//
		// get the items count
		// 
		int count = (int) theTokenizer.nval;
		//System.out.println("Count:" +count);
		
		//
		//store the RGB values
		//
		nextToken = theTokenizer.nextToken();	
		double R = theTokenizer.nval;
		//System.out.println("R:" +R);
		nextToken = theTokenizer.nextToken();	
		double G = theTokenizer.nval;
		//System.out.println("G:" +G);
		nextToken = theTokenizer.nextToken();	
		double B = theTokenizer.nval;
		//System.out.println("B:" +B);

		//do material stuff
		Material mat;
		MaterialSet matSet;
		mat = new Material((float)R, (float)G, (float)B, (float)ambiance);
		matSet = new MaterialSet();
		matSet.addMaterial(mat);
		
		LineCell lCell = new LineCell();
	

		//
		// collect the points for line 
		// XXX
		// 
 		for (int i=1; i<= count; i++)
		{				
			// point #1 of set
			//System.out.println("Data Point Set #:" +i);
			nextToken = theTokenizer.nextToken();
			//System.out.println("POINT VALUE 1: " + (int) theTokenizer.nval);
			
			lCell.addVal((int)theTokenizer.nval);

			// point #2 of set
			nextToken = theTokenizer.nextToken();
			//System.out.println("POINT VALUE 2: " + (int) theTokenizer.nval);

			lCell.addVal((int)theTokenizer.nval);
		}

		lCell.setPoints(currentPts);
		lCell.setMaterials(matSet);
		actor.addCell(lCell);

		//System.out.println();

	}//end try

	catch(Exception e)
     	{
		System.out.println("File Parser Error [reading Lines]: "+e);
		System.exit(1);
     	}
	}





	//
	//
	//
	/////////////////////////////////////////////////////
	public void handlePolygons(StreamTokenizer theTokenizer, Actor actor)
	{

	try
      	{
		int nextToken = theTokenizer.nextToken();	

		//
		// get the items count
		// 
		int count = (int) theTokenizer.nval;
		//System.out.println("Count:" +count);
		
		//
		//store the RGB values
		//
		nextToken = theTokenizer.nextToken();	
		double R = theTokenizer.nval;
		//System.out.println("R:" +R);
		nextToken = theTokenizer.nextToken();	
		double G = theTokenizer.nval;
		//System.out.println("G:" +G);
		nextToken = theTokenizer.nextToken();	
		double B = theTokenizer.nval;
		//System.out.println("B:" +B);

		//do material stuff
		Material mat;
		MaterialSet matSet;
		mat = new Material((float)R, (float)G, (float)B, (float)ambiance);
		matSet = new MaterialSet();
		matSet.addMaterial(mat);
		
		PolygonCell pCell = new PolygonCell();

		//
		// collect the points
		//
		
		nextToken = theTokenizer.nextToken();
		while (theTokenizer.nval != -1)
		{	
			//create vector - add element 
			//System.out.println("pointVALUE: " +  (int) theTokenizer.nval);
			pCell.addVal((int)theTokenizer.nval);
			nextToken = theTokenizer.nextToken();
		}

		pCell.setPoints(currentPts);
		pCell.setMaterials(matSet);
		actor.addCell(pCell);

	}//end try

	catch(Exception e)
     	{
		System.out.println("File Parser Error [reading Polygons]: "+e);
		System.exit(1);
     	}
	}





	//
	//
	//
	/////////////////////////////////////////////////////
	public void handleTriangles(StreamTokenizer theTokenizer, Actor actor)
	{

	try
      	{
		int nextToken = theTokenizer.nextToken();	

		//
		// get the items count
		// 
		int count = (int) theTokenizer.nval;
		//System.out.println("Count:" +count);
		
		//
		//store the RGB values
		//
		nextToken = theTokenizer.nextToken();	
		double R = theTokenizer.nval;
		//System.out.println("R:" +R);
		nextToken = theTokenizer.nextToken();	
		double G = theTokenizer.nval;
		//System.out.println("G:" +G);
		nextToken = theTokenizer.nextToken();	
		double B = theTokenizer.nval;
		//System.out.println("B:" +B);

		//do material stuff
		Material mat;
		MaterialSet matSet;
		mat = new Material((float)R, (float)G, (float)B, (float)ambiance);
		matSet = new MaterialSet();
		matSet.addMaterial(mat);
		
		TriangleCell tCell = new TriangleCell();

		//
		// collect the points for triangle
		// 
 		for (int i=1; i<= count; i++)
		{				
			// point #1 of set
			//System.out.println("Data Point Set #:" +i);
			nextToken = theTokenizer.nextToken();
			//System.out.println("POINT VALUE 1: " + (int) theTokenizer.nval);
			tCell.addVal((int)theTokenizer.nval);

			// point #2 of set
			nextToken = theTokenizer.nextToken();
			//System.out.println("POINT VALUE 2: " + (int) theTokenizer.nval);
			tCell.addVal((int)theTokenizer.nval);

			// point #3 of set
			nextToken = theTokenizer.nextToken();
			//System.out.println("POINT VALUE 3: " + (int) theTokenizer.nval);
			tCell.addVal((int)theTokenizer.nval);
		}
			

		tCell.setPoints(currentPts);
		tCell.setMaterials(matSet);
		actor.addCell(tCell);


	}//end try

	catch(Exception e)
     	{
		System.out.println("File Parser Error [reading Triangles]: "+e);
		System.exit(1);
     	}
	}









	//
	//
	//
	/////////////////////////////////////////////////////
	public void handlePolylines(StreamTokenizer theTokenizer, Actor actor)
	{

	try
      	{
		int nextToken = theTokenizer.nextToken();	

		//
		// get the items count
		// 
		int count = (int) theTokenizer.nval;
		//System.out.println("Count:" +count);
		
		//
		//store the RGB values
		//
		nextToken = theTokenizer.nextToken();	
		double R = theTokenizer.nval;
		//System.out.println("R:" +R);
		nextToken = theTokenizer.nextToken();	
		double G = theTokenizer.nval;
		//System.out.println("G:" +G);
		nextToken = theTokenizer.nextToken();	
		double B = theTokenizer.nval;
		//System.out.println("B:" +B);


		//do material stuff
		Material mat;
		MaterialSet matSet;
		mat = new Material((float)R, (float)G, (float)B, (float)ambiance);
		matSet = new MaterialSet();
		matSet.addMaterial(mat);
		
		PolylineCell plCell = new PolylineCell();

		//
		// collect the points for polyline into vector
		//
		for (int i=1; i<= count; i++)
		{	
			// create vector
			//System.out.println("Data Point Set #:" +i);
			nextToken = theTokenizer.nextToken();
			while ((int)theTokenizer.nval != -1)
			{
				//System.out.println("POINT VALUE: " +  (int) theTokenizer.nval);
				//System.out.println();
				plCell.addVal((int)theTokenizer.nval);
				nextToken = theTokenizer.nextToken();
			}
		}


		plCell.setPoints(currentPts);
		plCell.setMaterials(matSet);
		actor.addCell(plCell);


	}//end try

	catch(Exception e)
     	{
		System.out.println("File Parser Error [reading PolyLines]: "+e);
		System.exit(1);
     	}
	}

}