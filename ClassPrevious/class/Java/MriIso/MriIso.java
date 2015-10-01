//
//
// ConeExample
//
//

import vtk.*;
import java.awt.*;

public class MriIso extends Frame
{
	vtkPanel2 vPanel = null;
	vtkContourFilter cFilt = null;
	boolean savetofile;

	public static void main( String args[] ) 
	{
		vtkStructuredPointsReader reader = null;
		vtkActor isoActor = null;
		vtkPolyDataMapper isoMapper = null;

		MriIso f = new MriIso();
		f.vPanel = new vtkPanel2();
		f.vPanel.resize(400,400);
		//f.savetofile = true;

		System.out.println("Here we go");

		reader = new vtkStructuredPointsReader();
		  reader.SetFileName("MRIdata.vtk");

		f.cFilt = new vtkContourFilter();
		  f.cFilt.SetValue(0, 225);
		  f.cFilt.SetInput(reader.GetOutput());

		isoMapper = new vtkPolyDataMapper();
		  isoMapper.SetInput(f.cFilt.GetOutput());

		isoActor = new vtkActor();
		  isoActor.SetMapper(isoMapper);
	  
		f.vPanel.GetRenderer().AddActor(isoActor);
      
		f.add("Center", f.vPanel);
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
	public boolean action(Event evt, Object what)
	{
		if (evt.target instanceof MenuItem) 
		{
		// Since we didn't save references to each of the menu objects,
		// we check which one was pressed by comparing labels.
			if (((String)evt.arg).equals("Animate")) {
				for (float i=10;i<1000;i+=200) {
					System.out.println("Contour value: " + i);
					cFilt.SetValue(0,i);
					vPanel.GetRenderWindow().Render();
					if (savetofile) {
						vPanel.GetRenderWindow().SetFileName("image." + (int)i + ".ppm");
						vPanel.GetRenderWindow().SaveImageAsPPM();
					}
				}
			}
			if (((String)evt.arg).equals("Exit")) {
				System.exit(1);
			}
		}
		System.out.println("Event: " + evt + " Object: " + what);
		return super.action(evt, what);
  }

}

