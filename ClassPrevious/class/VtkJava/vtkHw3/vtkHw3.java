/**
 * This class can take a variable number of parameters on the command
 * line. Program execution begins with the main() method. The class
 * constructor is not invoked unless an object of type 'Class1'
 * created in the main() method.
 */
import vtk.*;
import java.awt.*;

public class vtkHw3 extends Frame
{
	vtkPanel vPanel = null;
	vtkContourFilter cFilt = null;
	// Do we write out the image
	boolean savetofile = false;
	
	public vtkHw3(String title) { super(title); }

	public static void main( String args[] ) 
	{
		vtkStructuredPoints sPnts = null;
		vtkActor isoActor = null;
		vtkPolyDataMapper isoMapper = null;

		vtkHw3 f = new vtkHw3("vtkHw3");
		String jvmVendor = java.lang.System.getProperty("java.vendor");
		String jvmVersion = java.lang.System.getProperty("java.version");
		System.out.println("jvm vendor: "+jvmVendor); 
		System.out.println("jvm version: "+jvmVersion); 
		if (jvmVendor.startsWith("Microsoft",0)) {
			f.vPanel = new vtkMSPanel();
	   	} else {
			f.vPanel = new vtkPanel();
		} 
		//f.savetofile = true;

		System.out.println("Here we go");

		sPnts = new vtkStructuredPoints();
		// Open up a reader
		objectReader obr = new objectReader(args[0]);

		int dimx = obr.getInt();
		int dimy = obr.getInt();
		System.out.println("dimx: " + dimx + " dimy: " + dimy);
		sPnts.SetExtent(0, dimx, 0, dimy, 0, 0);
		sPnts.AllocateScalars();
		vtkScalars pSca = sPnts.GetPointData().GetScalars();
		for (int i=0;i<dimx*dimy;i++) {
			double val = obr.getDouble();
			pSca.SetScalar(i,val);
		}

		// Create the contour filter and set values
		int numContours = 25;
		f.cFilt = new vtkContourFilter();
		  f.cFilt.SetNumberOfContours(numContours);
		  f.cFilt.SetInput(sPnts);
		  double []range = pSca.GetRange();
		  double delta = (range[1]-range[0])/numContours;
		  for (int i=0;i<numContours;i++) {
			f.cFilt.SetValue(i, (delta*i)+range[0]);
		  }

		// Pass the filter to the mapper
		isoMapper = new vtkPolyDataMapper();
		  isoMapper.SetInput(f.cFilt.GetOutput());

		// Create the actor and pass it the mapper
		isoActor = new vtkActor();
		  isoActor.SetMapper(isoMapper);

		// Add the actor to the renderer
		f.vPanel.getRenderer().AddActor(isoActor);
      
		f.add("Center", f.vPanel);
		f.pack();

		// Set up the menu
		MenuBar mb = new MenuBar();
		Menu file =  new Menu( "File");
		file.add( new MenuItem( "Animate")); 
		file.add( new MenuItem( "Exit")); 
		mb.add(file); 
		f.setMenuBar(mb);
   		f.show();
	}
}
