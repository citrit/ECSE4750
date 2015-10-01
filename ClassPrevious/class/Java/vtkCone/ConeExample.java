//
//
// ConeExample
//
//

import vtk.*;
import java.awt.*;

public class ConeExample extends Frame
{

	public static void main( String args[] ) 
    {
		vtkConeSource cone = null;
		vtkPanel panel = null;
		vtkActor coneActor = null;
		vtkPolyDataMapper coneMapper = null;

		ConeExample f = new ConeExample();
		panel = new vtkPanel();
		panel.resize(400,400);

		System.out.println("Here we go");

		cone = new vtkConeSource();
		cone.SetResolution(8);
		coneMapper = new vtkPolyDataMapper();
		coneMapper.SetInput(cone.GetOutput());

		coneActor = new vtkActor();
		coneActor.SetMapper(coneMapper);
	  
		panel.getRenderer().AddActor(coneActor);
      
		f.add("Center", panel);
		f.pack();

		// Set up the menu
		MenuBar mb = new MenuBar();
		Menu file = new Menu( "File" );
		file.add( new MenuItem( "Exit" ));
		mb.add( file );
		f.setMenuBar( mb );

		f.show();
    }

	/** Let capture menu events (and all others...) */
	public boolean action(Event evt, Object what)
	{
		if (evt.target instanceof MenuItem) 
		{
		// Since we didn't save references to each of the menu objects,
		// we check which one was pressed by comparing labels.
			if (((String)evt.arg).equals("Exit")) {
				System.exit(1);
			}
		}
		System.out.println("Event: " + evt + " Object: " + what);
		return super.action(evt, what);
  }

}

