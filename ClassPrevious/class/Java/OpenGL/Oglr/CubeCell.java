//////////////////////////////////////////////////////////////////////////
//
//  This code is for instructional purposes only. It was generated for
//  use in a graduate level course to show certain aspects of data
//  storage algorithms. It has problems and should not be used
//  outside the class environment.
//
//  Author:  Thomas D. Citriniti     citrit@rpi.edu
//  Class:   Advanced Computer Graphics and Data visualization
//           Rensselaer Polytechnic Institute
//  Date:    January 1998
//
/////////////////////////////////////////////////////////////////////////

/**
  * CubeCell class is a concrete implementation of the Cell class
  * It draws a cube centered at (0,0,0) and one unit length.
  */
public class CubeCell extends Cell
{

	final float boxCors[][] = { {-0.5F, -0.5F, -0.5F},
								{0.5F, -0.5F, -0.5F},
								{0.5F,  0.5F, -0.5F},
								{-0.5F,  0.5F, -0.5F},
								{-0.5F, -0.5F,  0.5F},
								{0.5F, -0.5F,  0.5F},
								{0.5F,  0.5F,  0.5F},
								{-0.5F,  0.5F,  0.5F} };
	final int  boxIndex[] = {   0, 1, 2, 3, // Face 1
								4, 5, 6, 7, // Face 2
								0, 1, 5, 4, // Face 3
								2, 6, 7, 3, // Face 4
								1, 2, 6, 5, // Face 5
								0, 3, 7, 4 };  // Face 6
	final float boxNorms[][] = {{0, 0, -1},
								{0, 0, 1},
								{0, 1, 0},
								{0, -1, 0},
								{1, 0, 0},
								{-1, 0, 0} };
	final float boxTexCoords[][] = {{0,0},
									{0,1},
									{1,1},
									{1,0}};

	/**
	  * Nothing to do here (yet)
	  */
	public CubeCell() { }

	/**
	  * Sent by the Renderer to start the drawing process, kind of neat
	  * in that the LineCell sends commands back to the Renderer
	  * on how to draw itself (Remember the Renderer only has a vector
	  * of cells).
	  */
	public void render(Renderer aren)
	{
		int cnt, i;
		TexCoordType texCoord = new TexCoordType();
		PointType pt = new PointType();

		cnt = mtSet.size();
		// Turn lighting on for normals.
		aren.lighting(true);

		for (int j=0;j<6;j++) {
			pt.x = boxNorms[j][0];
			pt.y = boxNorms[j][1];
			pt.z = boxNorms[j][2];
			aren.normal(pt);
			// Load the texture.
			if (imTex != null) {
				imTex.render(aren);
			}
			aren.beginDraw(aren.POLYGON);
			if (cnt == 1) { // we have only one material set it outside the loop
				aren.setMaterial(mtSet.getMaterial(0));
				for (i=0;i<4;i++) {
					texCoord.x = boxTexCoords[i][0];
					texCoord.y = boxTexCoords[i][1];
					aren.texCoord(texCoord);
					pt.x = boxCors[boxIndex[j*4+i]][0];
					pt.y = boxCors[boxIndex[j*4+i]][1];
					pt.z = boxCors[boxIndex[j*4+i]][2];
					aren.vertex(pt); 
				}
			}
			else {
				for (i=0;i<intVals.size();i++) {
					aren.setMaterial(mtSet.getMaterial(getVal(i)%cnt));
					texCoord.x = boxTexCoords[i][0];
					texCoord.y = boxTexCoords[i][1];
					aren.texCoord(texCoord);
					pt.x = boxCors[boxIndex[j*4+i]][0];
					pt.y = boxCors[boxIndex[j*4+i]][1];
					pt.z = boxCors[boxIndex[j*4+i]][2];
					aren.vertex(pt); 
				}
			}
			aren.endDraw();
		}
	}
}

