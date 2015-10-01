import vtk.*;
import java.awt.*;

public class Ex94c extends Frame  {

    vtkPanel panel = null;
    
    public static void main( String args[] ) {

	Ex94c f = new Ex94c();

        f.panel = new vtkPanel();
	
	vtkPLOT3DReader r = new vtkPLOT3DReader();
	r.SetXYZFileName("g:/vtkdata/combxyz.bin");
	r.SetQFileName("g:/vtkdata/combq.bin");
	r.SetScalarFunctionNumber(100);
	r.SetVectorFunctionNumber(202);
	r.Update();

	vtkPlaneSource plane = new vtkPlaneSource();
	plane.SetResolution(50,50);

	vtkTransform t = new vtkTransform();
	t.Translate(3.7, 0.0, 28.37);
	t.Scale(5,5,5);
	t.RotateY(90);

	vtkTransformPolyDataFilter tpd = new vtkTransformPolyDataFilter();
	tpd.SetInput(plane.GetOutput());
	tpd.SetTransform(t);

	vtkOutlineFilter outline = new vtkOutlineFilter();
	outline.SetInput(tpd.GetOutput());

	vtkPolyDataMapper mapper = new vtkPolyDataMapper();
	mapper.SetInput(outline.GetOutput());

	vtkActor actor = new vtkActor();
	actor.SetMapper(mapper);
	actor.GetProperty().SetColor(0.0F, 0.0F, 0.0F);

	vtkTransform t2 = new vtkTransform();
	t2.Translate(9.2, 0.0, 31.20);
	t2.Scale(5,5,5);
	t2.RotateY(90);

	vtkTransformPolyDataFilter tpd2 = new vtkTransformPolyDataFilter();
	tpd2.SetInput(plane.GetOutput());
	tpd2.SetTransform(t2);

	vtkOutlineFilter outline2 = new vtkOutlineFilter();
	outline2.SetInput(tpd2.GetOutput());

	vtkPolyDataMapper mapper2 = new vtkPolyDataMapper();
	mapper2.SetInput(outline2.GetOutput());

	vtkActor actor2 = new vtkActor();
	actor2.SetMapper(mapper2);
	actor2.GetProperty().SetColor(0.0F, 0.0F, 0.0F);
	
	vtkTransform t3 = new vtkTransform();
	t3.Translate(13.27, 0.0, 33.30);
	t3.Scale(5,5,5);
	t3.RotateY(90);

	vtkTransformPolyDataFilter tpd3 = new vtkTransformPolyDataFilter();
	tpd3.SetInput(plane.GetOutput());
	tpd3.SetTransform(t3);

	vtkOutlineFilter outline3 = new vtkOutlineFilter();
	outline3.SetInput(tpd3.GetOutput());

	vtkPolyDataMapper mapper3 = new vtkPolyDataMapper();
	mapper3.SetInput(outline3.GetOutput());

	vtkActor actor3 = new vtkActor();
	actor3.SetMapper(mapper3);
	actor3.GetProperty().SetColor(0.0F, 0.0F, 0.0F);

	vtkAppendPolyData append = new vtkAppendPolyData();
	append.AddInput(tpd.GetOutput());
	append.AddInput(tpd2.GetOutput());
	append.AddInput(tpd3.GetOutput());

	vtkProbeFilter probe = new vtkProbeFilter();
	probe.SetInput(append.GetOutput());
	probe.SetSource(r.GetOutput());

	vtkWarpVector warp = new vtkWarpVector();
	warp.SetInput(probe.GetPolyDataOutput());
	warp.SetScaleFactor(0.009);
	
	vtkDataSetMapper cMapper = new vtkDataSetMapper();
	cMapper.SetInput(warp.GetOutput());
	cMapper.SetScalarRange(r.GetOutput().GetScalarRange());

	vtkActor pActor = new vtkActor();
	pActor.SetMapper(cMapper);
	pActor.GetProperty().SetColor(0.5, 0.5, 0.5);
	
	vtkStructuredGridOutlineFilter gridOutline = new vtkStructuredGridOutlineFilter();
	gridOutline.SetInput(r.GetOutput());

	vtkPolyDataMapper oMapper = new vtkPolyDataMapper();
	oMapper.SetInput(gridOutline.GetOutput());

	vtkActor oActor = new vtkActor();
	oActor.SetMapper(oMapper);
	oActor.GetProperty().SetColor(0.6F, 0.6F, 0.6F);

	f.panel.getRenderer().AddActor(oActor);
	f.panel.getRenderer().AddActor(pActor);
	f.panel.getRenderer().AddActor(actor);
	f.panel.getRenderer().AddActor(actor2);
	f.panel.getRenderer().AddActor(actor3);

	//	f.panel.getRenderer().SetBackground(1.0, 1.0, 1.0);
	
	f.panel.resize(500, 500);

//  	vtkCamera cam = f.panel.getRenderer().GetActiveCamera();
//  	cam.SetClippingRange(3.95297, 50);
//  	cam.SetFocalPoint( 8.88908, 0.595038, 29.3342);
//  	cam.SetPosition(-12.3332, 31.7479, 41.2387);
//  	cam.ComputeViewPlaneNormal();
//  	cam.SetViewUp(0.060772, -0.319905, 0.945498);
	
	// Standard Java stuff
	f.add("Center", f.panel);
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
    public boolean action(Event evt, Object what) {
	if (evt.target instanceof MenuItem) {
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
