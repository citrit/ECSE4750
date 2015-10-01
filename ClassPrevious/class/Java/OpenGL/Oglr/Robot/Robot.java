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
//  Date:    January 2001
//
/////////////////////////////////////////////////////////////////////////


import java.awt.*;
import java.awt.event.*;


class RobotArm extends Frame
{
	Actor BaseActor;
	Actor ElbowActor;
	Actor WristActor;
	
	public RobotArm() 
	{
		BaseActor = null;
		ElbowActor = null;
		WristActor = null;
	}
	
	public static void main(String argv[])
	{
		
		OGLRenderer aren = new OGLRenderer();
	
		aren.addCamera(new OGLCamera());
		aren.addLight(new OGLLight(0));
		
		// Make it visible and set size
		aren.setVisible(true);
		aren.setSize(300, 300);
		aren.getCamera().setFrom(0.0F, 0.0F, 10.0F);
		System.out.println("Here we go");

		RobotArm rob = new RobotArm();
		aren.addKeyCallback(new MyKeyhandler(rob));
		rob.createScene(aren);
		// Add the canvas to the frame and make it show
  		rob.add("Center", aren);
  		rob.addMouseListener(aren);
  		rob.addMouseMotionListener(aren);
		rob.pack();

		rob.show();
	}
	
	public void createScene(Renderer ren)
	{
		float boxCors[][] = {   {-0.5F, -0.5F, -0.5F},
								{0.5F, -0.5F, -0.5F},
								{0.5F,  0.5F, -0.5F},
								{-0.5F,  0.5F, -0.5F},
								{-0.5F, -0.5F,  0.5F},
								{0.5F, -0.5F,  0.5F},
								{0.5F,  0.5F,  0.5F},
								{-0.5F,  0.5F,  0.5F} };
		int  boxIndex[] = { 0, 1, 2, 3, // Face 1
							4, 5, 6, 7, // Face 2
							0, 1, 5, 4, // Face 3
							2, 6, 7, 3, // Face 4
							1, 2, 6, 5, // Face 5
							0, 3, 7, 4 };  // Face 6
		float boxNorms[][] = {  {0, 0, -1},
								{0, 0, 1},
								{0, 1, 0},
								{0, -1, 0},
								{1, 0, 0},
								{-1, 0, 0} };
		float[] sc = new float[3];
		
		BaseActor = new Actor();
		ElbowActor = new Actor();
		WristActor = new Actor();
		Material mat = new Material(0.8F,0,0,1);
		MaterialSet matSet = new MaterialSet();
		matSet.addMaterial(mat);
		PointSet ptSet = new PointSet();
		for (int i=0;i<8;i++) {
			ptSet.addPoint(new PointType(boxCors[i]));
		}
		for (int j=0;j<6;j++) {
			PolygonCell pCell = new PolygonCell();
			pCell.setPoints(ptSet);
			pCell.setNormal(new PointType(boxNorms[j]));
			pCell.setMaterials(matSet);
			for (int k=0;k<4;k++)
				pCell.addVal(boxIndex[j*4+k]);
			BaseActor.addCell(pCell);
			ElbowActor.addCell(pCell);
			WristActor.addCell(pCell);
		}
		sc[0] = 1; sc[1] = 2.5F;sc[2] = 1;
		BaseActor.scale(sc);
		sc[0] = 0.75F; sc[1] = 1.5F;sc[2] = 0.75F;
		ElbowActor.scale(sc);
		ElbowActor.translate(0,1.75F,0);
		sc[0] = 0.5F; sc[1] = 1;sc[2] = 0.5F;
		WristActor.scale(sc);	
		WristActor.translate(0,	1F,0);
		
		BaseActor.addActor(ElbowActor);
		ElbowActor.addActor(WristActor);

		ren.addActor(BaseActor);
	}
	
}

class MyKeyhandler implements KeyCallback 
{
	RobotArm RobArm;
	
	public MyKeyhandler(RobotArm rb)
	{
		RobArm = rb;
	}
	
	public void keyCallback(KeyEvent e)
	{
		switch (e.getKeyChar()) {
		case '1':
			RobArm.BaseActor.rotateY(15.0F);
			break;
		case '2':
			RobArm.BaseActor.rotateZ(15.0F);
			break;
		case '3':
			RobArm.ElbowActor.rotateY(15.0F);
			break;
		case '4':
			RobArm.ElbowActor.rotateZ(15.0F);
			break;
		case '5':
			RobArm.WristActor.rotateY(15.0F);
			break;
		case '6':
			RobArm.WristActor.rotateZ(15.0F);
			break;
		}

		System.out.println("KeyCallback: " + e);
	}
	
}