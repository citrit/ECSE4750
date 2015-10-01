import vtk.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class vtkHw1 extends Frame implements KeyListener
{

	vtkActor [] Acts;
	int objMode;
	vtkPanel renPanel;

	public vtkHw1(String title)
	{ 
		super(title);
		objMode = 0;
		Acts = new vtkActor[3];
	}

	public static void main (String[] args)
	{
	  //this constructor must go all the way to 
	  // addNotify must be called on a Frame before you attempt
	  // to add a vtkPanel to it, otherwise the peer information
	  // doesn't get constructed, causing nasty seg-faults in GetWindowID
	  vtkHw1 hw1 = new vtkHw1("vtkHw1");
	  hw1.addNotify();
	
	  String jvmVendor = java.lang.System.getProperty("java.vendor");
      String jvmVersion = java.lang.System.getProperty("java.version");

	  System.out.println("jvm vendor: "+jvmVendor);
	  System.out.println("jvm version: "+jvmVersion);

	  if (jvmVendor.startsWith("Microsoft",0)) {
		hw1.renPanel = new vtkMSPanel();
	  }
	  else {
		hw1.renPanel = new vtkPanel();
	  }
	  hw1.renPanel.setSize(400,400);
	  hw1.renPanel.addKeyListener(hw1);
	  hw1.removeAll();
	  hw1.add(hw1.renPanel);
	  vtkRenderer ren1 = hw1.renPanel.getRenderer();
	
	  // create cone geometry
	  vtkConeSource cone = new vtkConeSource();
	  cone.SetResolution(18);
	  vtkPolyDataMapper map = new vtkPolyDataMapper();
	  map.SetInput(cone.GetOutput());
	  hw1.Acts[0] = new vtkActor();
	  hw1.Acts[0].SetMapper(map);
	  hw1.Acts[0].AddPosition(1,0,0);
	  hw1.Acts[0].GetProperty().SetColor(0,0,1); // cone color blue
	  ren1.AddActor(hw1.Acts[0]);

	  // create sphere geometry
	  vtkSphereSource sphere = new vtkSphereSource();
	  sphere.SetThetaResolution(18);
	  vtkPolyDataMapper spheremap = new vtkPolyDataMapper();
	  spheremap.SetInput(sphere.GetOutput());
	  hw1.Acts[2] = new vtkActor();
	  hw1.Acts[2].SetMapper(spheremap);
	  hw1.Acts[2].GetProperty().SetColor(1,1,0); // Sphere color yellow
	  hw1.Acts[2].GetProperty().SetOpacity(0.35);
	  hw1.Acts[2].AddPosition(-1,0,0);
	  ren1.AddActor(hw1.Acts[2]);

	  // create cube geometry
	  vtkCubeSource cube = new vtkCubeSource();
	  vtkPolyDataMapper cubemap = new vtkPolyDataMapper();
	  cubemap.SetInput(cube.GetOutput());
	  hw1.Acts[1] = new vtkActor();
	  hw1.Acts[1].SetMapper(cubemap);
	  hw1.Acts[1].GetProperty().SetColor(1,0,0); // Cube color red
	  ren1.AddActor(hw1.Acts[1]);


	  ren1.SetBackground(0.5,0.5,0.5); // Background color grey
	  
	  hw1.pack();
	  hw1.show();
	}
	
	// Handle the keystrokes and modify appropriate object
	public void keyTyped(KeyEvent e) {

		switch (e.getKeyChar()) {
		case '0':
			objMode = 0;
			break;
		case '1':
			objMode = 1;
			break;
		case '2':
			objMode = 2;
			break;
		case 'h':
		case 'H':
			Acts[objMode].RotateY(5);
			break;
		case 'j':
		case 'J':
			Acts[objMode].RotateX(5);
			break;
		case 'k':
		case 'K':
			Acts[objMode].RotateX(-5);
			break;
		case 'l':
		case 'L':
			Acts[objMode].RotateY(-5);
			break;
		case '<':
			Acts[objMode].SetScale(Acts[objMode].GetScale()[0] * 0.8);
			break;
		case '>':
			Acts[objMode].SetScale(Acts[objMode].GetScale()[0] * 1.2);
			break;
		}
		renPanel.Render();
		
	}
	public void keyPressed(KeyEvent e) {

	}
	public void keyReleased(KeyEvent e) {

	}

}

