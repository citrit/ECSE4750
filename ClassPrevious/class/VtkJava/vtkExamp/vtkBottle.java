import vtk.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class vtkBottle extends Frame implements KeyListener
{
	vtkPanel renPanel;

	public vtkBottle(String title)
	{ 
		super(title);
	}

	public static void main (String[] args)
	{
	  //this constructor must go all the way to 
	  // addNotify must be called on a Frame before you attempt
	  // to add a vtkPanel to it, otherwise the peer information
	  // doesn't get constructed, causing nasty seg-faults in GetWindowID
	  vtkBottle cone = new vtkBottle("vtkBottle");
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
		// create bottle profile
		//
		vtkPoints points = new vtkPoints();
		  points.InsertPoint(0, 0.01, 0.0, 0.0);
		  points.InsertPoint(1, 1.5, 0.0, 0.0);
		  points.InsertPoint(2, 1.5, 0.0, 3.5);
		  points.InsertPoint(3, 1.25, 0.0, 3.75);
		  points.InsertPoint(4, 0.75, 0.0, 4.00);
		  points.InsertPoint(5, 0.6, 0.0, 4.35);
		  points.InsertPoint(6, 0.7, 0.0, 4.65);
		  points.InsertPoint(7, 1.0, 0.0, 4.75);
		  points.InsertPoint(8, 1.0, 0.0, 5.0);
		  points.InsertPoint(9, 0.01, 0.0, 5.0);
		vtkCellArray lines = new vtkCellArray();
		  lines.InsertNextCell(10); //number of points
		  lines.InsertCellPoint(0);
		  lines.InsertCellPoint(1);
		  lines.InsertCellPoint(2);
		  lines.InsertCellPoint(3);
		  lines.InsertCellPoint(4);
		  lines.InsertCellPoint(5);
		  lines.InsertCellPoint(6);
		  lines.InsertCellPoint(7);
		  lines.InsertCellPoint(8);
		  lines.InsertCellPoint(9);
		vtkPolyData profile= new vtkPolyData();
		    profile.SetPoints(points);
		    profile.SetLines(lines);

		// extrude profile to make bottle
		//
		vtkRotationalExtrusionFilter extrude = new vtkRotationalExtrusionFilter();
		  extrude.SetInput(profile);
		  extrude.SetResolution(60);
		    
		vtkPolyDataMapper map = new vtkPolyDataMapper();
		    map.SetInput(extrude.GetOutput());

		vtkActor bottle = new vtkActor();
		    bottle.SetMapper(map);
		    bottle.GetProperty().SetColor(0.38, 0.7, 0.16);
			
			aren.AddActor(bottle);
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

