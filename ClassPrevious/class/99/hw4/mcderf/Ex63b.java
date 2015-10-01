import vtk.*;
import java.awt.*;

public class Ex63b extends Frame  {

    vtkMarchingCubes iso = null;
    vtkPanel panel = null;
    
    public static void main( String args[] ) {

	Ex63b f = new Ex63b();

        f.panel = new vtkPanel();
	
	vtkRenderer renderer = new vtkRenderer();
	vtkVolume16Reader reader = new vtkVolume16Reader();
	reader.SetDataDimensions(128, 128);
	reader.GetOutput().SetOrigin(0.0F, 0.0F, 0.0F);
	reader.SetDataByteOrderToLittleEndian();
	reader.SetFilePrefix("g:/vtkdata/headsq/half");
	reader.SetImageRange(1, 93);
	reader.SetDataSpacing(1.6, 1.6, 1.5);

	f.iso = new vtkMarchingCubes();
	f.iso.SetInput(reader.GetOutput());
	f.iso.SetValue(0, 1150);
		
	vtkPolyDataMapper mapper = new vtkPolyDataMapper();
	mapper.SetInput(f.iso.GetOutput());
	mapper.ScalarVisibilityOff();

	vtkActor actor = new vtkActor();
	actor.SetMapper(mapper);
	actor.GetProperty().SetColor(1.0F, 0.8F, 0.8F);
	
	f.panel.resize(300,300);
	
	// Add the actor to the renderer
	f.panel.getRenderer().AddActor(actor);
	f.panel.getRenderer().GetActiveCamera().Elevation(90.0F);
	f.panel.getRenderer().GetActiveCamera().SetViewUp(0, 0, -1);
	f.panel.getRenderer().GetActiveCamera().Zoom(1.3F);
	
	// Standard Java stuff
	f.add("Center", f.panel);
	f.pack();
	
	// Set up the menu
	MenuBar mb = new MenuBar();
	Menu file = new Menu( "File" );
	file.add( new MenuItem( "Animate" ));
	file.add( new MenuItem( "Exit" ));
	mb.add( file );
	f.setMenuBar( mb );
	
	f.show();
	
    }
    
    /** Let capture menu events (and all others...) */
    public boolean action(Event evt, Object what) {
	if (evt.target instanceof MenuItem) {
	    // Since we didn't save references to each of the menu objects,
	    // we check which one was pressed by comparing labels.
	    if (((String)evt.arg).equals("Exit")) {
		System.exit(1);
	    }
	    if (((String)evt.arg).equals("Animate")) {
		for (int i=700;i<=1200;i+=100) { // Loop and animate
		    System.out.println("Contour value: " + i);
		    iso.SetValue(0, i);
		    panel.getRenderWindow().Render();
		}
	    }	    
	}
	System.out.println("Event: " + evt + " Object: " + what);
	return super.action(evt, what);
    }
}
