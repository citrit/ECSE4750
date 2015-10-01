import vtk.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class vtkCone extends Frame implements KeyListener
{
	vtkPanel renPanel;

	public vtkCone(String title)
	{ 
		super(title);
	}

	public static void main (String[] args)
	{
	  //this constructor must go all the way to 
	  // addNotify must be called on a Frame before you attempt
	  // to add a vtkPanel to it, otherwise the peer information
	  // doesn't get constructed, causing nasty seg-faults in GetWindowID
	  vtkCone cone = new vtkCone("vtkCone");
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
	
	void createScene(vtkRenderer aren) 
	{
	  // create cone geometry
	  vtkConeSource cSource = new vtkConeSource();
	  cSource.SetResolution(18);
	  vtkPolyDataMapper map = new vtkPolyDataMapper();
	  map.SetInput(cSource.GetOutput());
	  vtkActor act = new vtkActor();
	  act.SetMapper(map);
	  aren.AddActor(act);		
	}
	
	// Handle the keystrokes and modify appropriate object
	public void keyTyped(KeyEvent e) 
	{
		System.out.println("keyTyped: "+e.getKeyChar());
		switch (e.getKeyChar()) {
		case '0':
			break;
		}
		renPanel.Render();
	}
	public void keyPressed(KeyEvent e) {

	}
	public void keyReleased(KeyEvent e) {

	}

}

