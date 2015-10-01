import vtk.*;
import java.awt.*;

public class vtkExamp {


	public static void main (String[] args)
	{
	  //this constructor must go all the way to 
	  // addNotify must be called on a Frame before you attempt
	  // to add a vtkPanel to it, otherwise the peer information
	  // doesn't get constructed, causing nasty seg-faults in GetWindowID
	  Frame window = new Frame("vtkExamp");
	  window.addNotify();
	
	
	  vtkPanel renPanel = new vtkPanel();
	  renPanel.setSize(400,400);
	  window.removeAll();
	  window.add(renPanel);
	
	  // create sphere geometry
	  vtkConeSource cone = new vtkConeSource();
	  cone.SetResolution(18);
	
	  // map to graphics library
	  vtkPolyDataMapper map = new vtkPolyDataMapper();
	  map.SetInput(cone.GetOutput());
	
	  // actor coordinates geometry, properties, transformation
	  vtkActor aSphere = new vtkActor();
	  aSphere.SetMapper(map);
	  aSphere.GetProperty().SetColor(0,0,1); // sphere color blue
	
	  vtkRenderer ren1 = renPanel.getRenderer();
	  ren1.AddActor(aSphere);
	  ren1.SetBackground(1,1,1); // Background color white
	
	
	
	  window.pack();
	  window.show();
	}
}

