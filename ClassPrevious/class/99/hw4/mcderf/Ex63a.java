import vtk.*;
import java.awt.*;

public class Ex63a extends Frame  {

    vtkContourFilter contour = null;
    vtkPanel panel = null;
    
    public static void main( String args[] ) {

	Ex63a f = new Ex63a();

        f.panel = new vtkPanel();
	
	vtkRenderer renderer = new vtkRenderer();
	vtkQuadric quad = new vtkQuadric();
	quad.SetCoefficients(1,2,3,0,1,0,0,0,0,0);

	vtkSampleFunction sample = new vtkSampleFunction();
	sample.SetSampleDimensions(25,25,25);
	sample.SetImplicitFunction(quad);
	
	f.contour = new vtkContourFilter();
	f.contour.SetInput(sample.GetOutput());
	f.contour.GenerateValues(3, 1.0F, 2.0F);

	vtkPolyDataMapper mapper = new vtkPolyDataMapper();
	mapper.SetInput(f.contour.GetOutput());
	mapper.SetScalarRange(0,70);

	vtkActor actor = new vtkActor();
	actor.SetMapper(mapper);
	
	f.panel.resize(400,400);
	
	// Add the actor to the renderer
	f.panel.getRenderer().AddActor(actor);

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
		for (float i=1.0F;i<7.0;i+=1.0) { // Loop and animate
		    System.out.println("Contour value: " + i);
		    contour.SetValue(0,i);
		    panel.getRenderWindow().Render();
		}
	    }	    
	}
	System.out.println("Event: " + evt + " Object: " + what);
	return super.action(evt, what);
    }
}
