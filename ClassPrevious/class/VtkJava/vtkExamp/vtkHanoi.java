import vtk.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.Stack;

public class vtkHanoi extends Frame implements KeyListener
{
	vtkPanel renPanel;
	int NumberOfPucks; //number of pucks to move
	int NumberOfSteps; // number of steps as puck moves

	float L;    //height of puck
	float H;    //height of peg
	float R;    //radius of peg
	float rMin; //min allowable radius of disks
	float rMax; //max allowable radius of disks
	float D;    //distance between pegs
	Stack [] pegStack; //keep track of which pucks are on which pegs

	int NumberOfMoves; //number of times a puck is moved
	int CurrentFrame; // Current frame number.
	boolean WritePPM;

	public vtkHanoi(String title)
	{ 
		super(title);
		pegStack = new  Stack[3];
		pegStack[0] = new Stack();
		pegStack[1] = new Stack();
		pegStack[2] = new Stack();
		WritePPM = false;
	}

	public static void main (String[] args)
	{
	  //this constructor must go all the way to 
	  // addNotify must be called on a Frame before you attempt
	  // to add a vtkPanel to it, otherwise the peer information
	  // doesn't get constructed, causing nasty seg-faults in GetWindowID
	  vtkHanoi cone = new vtkHanoi("vtkHanoi");
	  cone.addNotify();
	
	  String jvmVendor = java.lang.System.getProperty("java.vendor");
	  if (jvmVendor.startsWith("Microsoft",0)) {
		cone.renPanel = new vtkMSPanel();
	  }
	  else {
		cone.renPanel = new vtkPanel();
	  }
	  cone.renPanel.setSize(400,400);
	  cone.renPanel.addKeyListener(cone);
	  cone.removeAll();
	  cone.add(cone.renPanel);
	  vtkRenderer ren1 = cone.renPanel.getRenderer();

	  cone.createScene(ren1);

	  ren1.SetBackground(0.5,0.5,0.5); // Background color grey
	  
	  cone.pack();
	  cone.show();
	}
	
	void RenderScene()
	{
		renPanel.Render();
		if (WritePPM) {
			renPanel.getRenderWindow().SetFileName("hanoi."+CurrentFrame+".ppm");
			renPanel.getRenderWindow().WritePPMImageFile();
			CurrentFrame++;
		}
	}
	
	void createScene(vtkRenderer aren) 
	{
		vtkActor peg[] = new vtkActor[3];
		vtkActor puck[] = new vtkActor[20];
		int i, c;
		float scale;
		vtkMath math = new vtkMath();
		int puckResolution=48;
		double red, green, blue;
		
		NumberOfPucks = 5;
		NumberOfSteps = 5;
		L = 1.0F;
		H = 1.1F * NumberOfPucks * L;
		R = 0.5F;
		rMin = 4.0F * R;
		rMax = 12.0F * R;
		D = 1.1F * 1.25F * rMax;
		NumberOfMoves = 0;
		//
		// Create geometry: table, pegs, and pucks
		//
		vtkCylinderSource pegGeometry = new vtkCylinderSource();
		  pegGeometry.SetResolution(8);
		vtkPolyDataMapper pegMapper = new vtkPolyDataMapper();
		  pegMapper.SetInput(pegGeometry.GetOutput());

		vtkCylinderSource puckGeometry = new vtkCylinderSource();
		  puckGeometry.SetResolution(puckResolution);
		vtkPolyDataMapper puckMapper = new vtkPolyDataMapper();
		  puckMapper.SetInput(puckGeometry.GetOutput());

		vtkPlaneSource tableGeometry = new vtkPlaneSource();
		  tableGeometry.SetResolution(10,10);
		vtkPolyDataMapper tableMapper = new vtkPolyDataMapper();
		  tableMapper.SetInput(tableGeometry.GetOutput());
		//
		// Create the actors: table top, pegs, and pucks
		//
		// The table
		vtkActor table = new vtkActor();
		renPanel.getRenderer().AddActor(table);
		table.SetMapper(tableMapper);
		table.GetProperty().SetColor(0.9569,0.6431,0.3765);
		table.AddPosition(D,0,0);
		table.SetScale(4*D,2*D,3*D);
		table.RotateX(90);

		//The pegs (using cylinder geometry).  Note that the pegs have to translated 
		//in the  y-direction because the cylinder is centered about the origin.
		for (i=0; i<3; i++)
		{
		  peg[i] = new vtkActor();
		  aren.AddActor(peg[i]);
		  peg[i].SetMapper(pegMapper);
		  peg[i].GetProperty().SetColor(1,1,1);
		  peg[i].AddPosition(i*D, H/2, 0);
		  peg[i].SetScale(1,H,1);
		}

		// Initialize the random seed
		math.RandomSeed( 6 );

		//The pucks (using cylinder geometry). Always loaded on peg# 0.
		for (i=0; i<NumberOfPucks; i++)
		{
		  puck[i] = new vtkActor();
		  puck[i].SetMapper(puckMapper);
		  red = math.Random (); green = math.Random (); blue = math.Random ();
		  puck[i].GetProperty().SetColor(red, green, blue);
		  puck[i].AddPosition(0,i*L+L/2, 0);
		  scale = rMax - i*(rMax-rMin) / (NumberOfPucks-1);
		  puck[i].SetScale(scale,1,scale);
		  renPanel.getRenderer().AddActor(puck[i]);
		  pegStack[0].push(puck[i]);
		}

	}
	
	// Routine is responsible for moving pucks from one peg to the next.
	void MovePuck (int peg1, int peg2)
	{
	  float distance;
	  float flipAngle;
	  int i;
	  vtkActor movingActor;
	  long sleepInt = 100;
	  
	  NumberOfMoves++;

	  // get the actor to move
	  movingActor = (vtkActor)pegStack[peg1].pop();

	  // get the distance to move up
	  distance = (H - (L * (pegStack[peg1].size() - 1)) + rMax) / 
	             NumberOfSteps;

	  for (i=0; i<NumberOfSteps; i++)
	  {
	    movingActor.AddPosition(0,distance,0);
	    RenderScene();
		try {
			Thread.currentThread().sleep(sleepInt);
		} catch (InterruptedException e) {
			  System.out.println("Exception: "+e);
		}
	  }

	  // get the distance to move across
	  distance = (peg2 - peg1) * D / NumberOfSteps;
	  flipAngle = 180.0F / NumberOfSteps;
	  for (i=0; i<NumberOfSteps; i++)
	  {
	    movingActor.AddPosition(distance,0,0);
	    movingActor.RotateX(flipAngle);
	    RenderScene();
		try {
			Thread.currentThread().sleep(sleepInt);
		} catch (InterruptedException e) {
			  System.out.println("Exception: "+e);
		}
	    //if ( NumberOfMoves == 13 && i == 3 ) //for making book image
	     // {
	//      Renwin.Render();
	//      Renwin.SetFileName("hanoi1.ppm");
	//      Renwin.SaveImageAsPPM();
	     // }
	    }

	  // get the distance to move down
	  distance = ((L * (pegStack[peg2].size() - 1)) - H - rMax) / 
	             NumberOfSteps;

	  for (i=0; i<NumberOfSteps; i++)
	  {
	    movingActor.AddPosition(0,distance,0);
	    RenderScene();
		try {
			Thread.currentThread().sleep(sleepInt);
		} catch (InterruptedException e) {
			  System.out.println("Exception: "+e);
		}
	  }

	  pegStack[peg2].push(movingActor);
	}

	void Hanoi (int n, int peg1, int peg2, int peg3)
	{
	  if ( n != 1 )
	  {
	    Hanoi (n-1, peg1, peg3, peg2);
	    Hanoi (1, peg1, peg2, peg3);
	    Hanoi (n-1, peg3, peg2, peg1);
	  }
	  else
	  {
	    MovePuck (peg1, peg2);
	  }
	}
	
	
	// Handle the keystrokes and modify appropriate object
	public void keyTyped(KeyEvent e) 
	{
		System.out.println("keyTyped: "+e.getKeyChar());
		switch (e.getKeyChar()) {
		case 'H':
		case 'h':
			CurrentFrame = 0;
			Hanoi (NumberOfPucks-1, 0, 2, 1);
			Hanoi (1, 0, 1, 2);
			Hanoi (NumberOfPucks-1, 2, 1, 0);
			break;
		}
		renPanel.Render();
	}
	public void keyPressed(KeyEvent e) {

	}
	public void keyReleased(KeyEvent e) {

	}

}

