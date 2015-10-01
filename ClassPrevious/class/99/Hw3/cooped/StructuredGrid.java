//////////////////////////////////////////////////////////////////////////
//
//  This code is for instructional purposes only. It was generated for
//  use in a graduate level course to show certain aspects of data
//  storage algorithms. It has problems and should not be used
//  outside the class environment.
//
//  Author:  Dave Cooper     cooped@rpi.edu
//  Class:   Advanced Computer Graphics and Data visualization
//           Rensselaer Polytechnic Institute
//  Date:    March 1999
//
/////////////////////////////////////////////////////////////////////////

import java.util.Vector;                      
import java.util.Enumeration;

public class StructuredGrid extends Actor {

	int width, height;
	PointType origin;
	float deltaX, deltaY;
	ScalarData sData;
	PointSet ptSet;
	Vector cVector;	//Contour Vector
	Renderer aren;
	int renderMode;	//1=wire 2=shaded 3=contour
	Material mt;
	int pt;

	/////////////////////////////////////////
	// function: StructuredGrid
	//
	/////////////////////////////////////////
	public StructuredGrid(){
		width = 0;						//initialize initial width cell number to 0
		height = 0;						//initialize initial height cell number to 0
		origin = new PointType();	//initialize initial start point to (0,0,0)
		deltaX = (float)1.0;
		deltaY = (float)1.0;
		renderMode = 1;
		cVector = new Vector();
	}


	/////////////////////////////////////////
	// function: render
	//
	/////////////////////////////////////////
	public void render(Renderer aRenderer){

		aren = aRenderer;

		aren.pushModelMatrix();
		aren.setOrientation(orientation, scale, position);

		switch(renderMode) {
		case 1: 
			DisplayWireframe();
			break;
		case 2: 
			DisplayPlane();
			break;
		case 3: 
			DisplayContours();
			break;
		}


		aren.popModelMatrix();
	}


	/////////////////////////////////////////
	// function: SetSize
	// purpose: defines the number of cells
	//				wide and high for the grid
	/////////////////////////////////////////
	public void SetSize(int aWidth, int aHeight){
		width = aWidth;
		height = aHeight;
	}

	/////////////////////////////////////////
	// function: SetStartPoint
	// purpose:  defines the start position
	/////////////////////////////////////////
	public void SetStartPoint(PointType aPointType){
		origin.x = aPointType.x;
		origin.y = aPointType.y;
		origin.z = aPointType.z;
	}

	/////////////////////////////////////////
	// function: SetDeltas
	// purpose: defines the grid cell deltas
	/////////////////////////////////////////
	public void SetDeltas(float deltax, float deltay){
		deltaX = deltax;
		deltaY = deltay;
	}

	/////////////////////////////////////////
	// function: SetPoints
	// purpose: 
	/////////////////////////////////////////
	public void SetPoints(PointSet pts){
		ptSet = pts;
	}

	/////////////////////////////////////////
	// function: SetData
	// purpose: 
	/////////////////////////////////////////
	public void SetData(ScalarData data){
		sData = data;
	}

	/////////////////////////////////////////
	// function: DisplayWireframe
	// purpose: displays the wireframe version
	//				of the grid
	/////////////////////////////////////////
	public void DisplayWireframe(){

		for(int i=0; i < height; i++) {
			for(int j=0; j < width; j++) {
				aren.beginDraw(aren.POLYLINE);
				pt = ((i*(width+1))+j);

				aren.setMaterial(sData.getMaterial(sData.getScalar(pt)));
				aren.vertex(ptSet.getPoint(pt));
				aren.setMaterial(sData.getMaterial(sData.getScalar(pt+1)));
				aren.vertex(ptSet.getPoint(pt+1));
				aren.setMaterial(sData.getMaterial(sData.getScalar(pt+2+width)));
				aren.vertex(ptSet.getPoint(pt+2+width));
				aren.setMaterial(sData.getMaterial(sData.getScalar(pt+1+width)));
				aren.vertex(ptSet.getPoint(pt+1+width));
				aren.setMaterial(sData.getMaterial(sData.getScalar(pt)));
				aren.vertex(ptSet.getPoint(pt));
				aren.endDraw();

			}
		}


	}

	/////////////////////////////////////////
	// function: DisplayPlane
	// purpose: displays the solid-plane 
	//				version of the grid
	/////////////////////////////////////////
	public void DisplayPlane(){

		for(int i=0; i < height; i++) {
			for(int j=0; j < width; j++) {
				aren.beginDraw(aren.POLYGON);
				pt = ((i*(width+1))+j);

				aren.setMaterial(sData.getMaterial(sData.getScalar(pt)));			//bottom-left
				aren.vertex(ptSet.getPoint(pt));
				aren.setMaterial(sData.getMaterial(sData.getScalar(pt+1)));			//bottom-right
				aren.vertex(ptSet.getPoint(pt+1));
				aren.setMaterial(sData.getMaterial(sData.getScalar(pt+2+width)));	//top-right
				aren.vertex(ptSet.getPoint(pt+2+width));
				aren.setMaterial(sData.getMaterial(sData.getScalar(pt+1+width)));	//top-left
				aren.vertex(ptSet.getPoint(pt+1+width));
				aren.setMaterial(sData.getMaterial(sData.getScalar(pt)));
				aren.vertex(ptSet.getPoint(pt));
				aren.endDraw();

			}
		}

	}

	/////////////////////////////////////////
	// function: DisplayContours
	// purpose: displays the contours of the
	//				grid
	/////////////////////////////////////////
	public void DisplayContours(){
		Float f1 = new Float(2.0);
		Float f2 = new Float(1.0);
		Float f3 = new Float(1.4);
		cVector.addElement(f1);
		cVector.addElement(f2);
		cVector.addElement(f3);
		
		
		this.Contour();
	}

	/////////////////////////////////////////
	// function: SetContours
	// purpose: sets the contours to be found
	/////////////////////////////////////////
	public void SetContours(Vector vec){
		
		cVector = vec;

	}


	/////////////////////////////////////////
	// function: Contour
	// purpose: performs contouring on grid
	/////////////////////////////////////////
	public void Contour(){
		int edges[][] = {{0,1},{1,2},{2,3},{3,0}};
		float LINE_CASES[][] = {
			{-1,-1,-1,-1,-1},
			{ 0, 3,-1,-1,-1},
			{ 1, 0,-1,-1,-1},
			{ 1, 3,-1,-1,-1},
			{ 2, 1,-1,-1,-1},
			{ 0, 3, 2, 1,-1},
			{ 2, 0,-1,-1,-1},
			{ 2, 3,-1,-1,-1},
			{ 3, 2,-1,-1,-1},
			{ 0, 2,-1,-1,-1},
			{ 1, 0, 3, 2,-1},
			{ 1, 2,-1,-1,-1},
			{ 3, 1,-1,-1,-1},
			{ 0, 1,-1,-1,-1},
			{ 3, 0,-1,-1,-1},
			{-1,-1,-1,-1,-1}
		};
		int CASE_MASK[] = {1,2,4,8};

		int i, j, index;
		int vert[]={0,0};
		float cellScalar[]={0,0,0,0};
		float lineCase[]={0,0,0,0,0};
		float edge[]={0,0,0,0,0};
		float value;
		float t;
		float x[]={0,0,0};	//new point
		float x1[]={0,0,0};	//old point #1
		float x2[]={0,0,0};	//old point #2
//		Vector finalVec = new Vector();
		PointType pointT;
		
		aren.setMaterial(new Material());
		aren.beginDraw(aren.LINE);

		for(Enumeration e = cVector.elements(); e.hasMoreElements() ;) {			  //for each of the contours

			value = ((Float)e.nextElement()).floatValue();

			for(int y=0; y < height; y++) {
				for(int z=0; z < width; z++) {
					pt = ((y*(width+1))+z);

					cellScalar[0]=sData.getScalar(pt+1+width);	//top-left vertex scalar
					cellScalar[1]=sData.getScalar(pt);				//bottom-left vertex scalar
					cellScalar[2]=sData.getScalar(pt+1);			//bottom-right vertex scalar
					cellScalar[3]=sData.getScalar(pt+2+width);	//top-right vertex scalar

					float cellPoints[][] = {
						{ptSet.getPoint(pt+1+width).x,ptSet.getPoint(pt+1+width).y,ptSet.getPoint(pt+1+width).z},
						{ptSet.getPoint(pt).x,ptSet.getPoint(pt).y,ptSet.getPoint(pt).z},
						{ptSet.getPoint(pt+1).x,ptSet.getPoint(pt+1).y,ptSet.getPoint(pt+1).z},
						{ptSet.getPoint(pt+2+width).x,ptSet.getPoint(pt+2+width).y,ptSet.getPoint(pt+2+width).z}
					};

					for(i=0, index=0; i<4; i++)
						if(cellScalar[i] >= value)
							index |= CASE_MASK[i];				  //determine which set of 5 matches

					lineCase[0]=LINE_CASES[index][0];	  //pull out digits from set of 5
					lineCase[1]=LINE_CASES[index][1];	  //and put them into lineCase
					lineCase[2]=LINE_CASES[index][2];	  //
					lineCase[3]=LINE_CASES[index][3];	  //
					edge = lineCase;							  //assign lineCase to edge

					for(int q=0; edge[q] > -1; q += 2) {
						for(i=0; i<2; i++) {						//insert line
							vert[0] = edges[(int)edge[i]][0];
							vert[1] = edges[(int)edge[i]][1];
							t=((value - cellScalar[vert[0]]) / (cellScalar[vert[1]] - cellScalar[vert[0]]));
							x1 = cellPoints[vert[0]];
							x2 = cellPoints[vert[1]];


							for(j=0; j<3; j++)
								x[j] = x1[j] + t * (x2[j] - x1[j]);

  							pointT = new PointType(x[0], x[1], x[2]);
//							finalVec.addElement(pt);
							aren.vertex(pointT);


						}
					}



				}	//end outside grid for loop (x)
			}	//end outside grid for loop (y)
		}
		aren.endDraw();




	}
}
