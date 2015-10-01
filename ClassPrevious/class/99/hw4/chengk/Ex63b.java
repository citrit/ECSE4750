/*Modified from HeadBone.tcl from Visualization Toolkit CD
  The idea of the animation part was from the MriIso example
  from the course webpage
*/
//Kent Cheng hw4
/*it took a while to load the image and it takes forever to animate*/
import vtk.*;
import java.awt.*;

public class Ex63b extends Frame
{
	vtkPanel panel = null;
	vtkMarchingCubes iso;
	vtkPolyDataMapper isoMapper;
	float value;

			
	public static void main ( String args[] )
	{
					
		Ex63b f = new Ex63b();
		f.panel = new vtkPanel();
		f.iso = new vtkMarchingCubes();
		f.isoMapper = new vtkPolyDataMapper();
		
		vtkVolume16Reader V16 = new vtkVolume16Reader();
		V16.SetDataDimensions(128, 128);
		V16.GetOutput().SetOrigin(0.0, 0.0, 0.0);
		V16.SetDataByteOrderToLittleEndian();	

		V16.SetFilePrefix("g:/vtkdata/headsq/half");    //place where data files are contained
		V16.SetImageRange(20, 40);
		V16.SetDataSpacing(1.6, 1.6, 1.5);

		
		f.iso.SetInput(V16.GetOutput());
		f.iso.SetValue(0, 1150);
		
		f.isoMapper.SetInput(f.iso.GetOutput());
		f.isoMapper.SetScalarRange(0,7);

		vtkActor isoActor = new vtkActor();
		isoActor.SetMapper(f.isoMapper);
		isoActor.GetProperty().SetColor(0,0,1);

		vtkOutlineFilter outline = new vtkOutlineFilter();
		outline.SetInput(V16.GetOutput());

		vtkPolyDataMapper outlineMapper =  new vtkPolyDataMapper();
		outlineMapper.SetInput(outline.GetOutput());

		vtkActor outlineActor = new vtkActor();
		outlineActor.SetMapper(outlineMapper);
		outlineActor.GetProperty().SetColor(0,0,0);
		
		f.panel.getRenderer().AddActor(outlineActor);
		f.panel.getRenderer().AddActor(isoActor);
		f.panel.getRenderer().SetBackground(1, 1, 1);
		f.panel.setSize(500, 500);

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
	        System.out.println("Here we go");
	}
	
	

/** Let capture menu events (and all others...) */
	public boolean action(Event evt, Object what)
	{
		int num=2;  //number of times to run the animation sequence
		float isoVal;
		if (evt.target instanceof MenuItem) 
		{
		// Since we didn't save references to each of the menu objects,
		// we check which one was pressed by comparing labels.
			if (((String)evt.arg).equals("Animate")) {
				for(int k=0;k<num;k++){
				 for(isoVal=1300F;isoVal>1100F;isoVal-=100F) { //shrink
					iso.SetValue(0, isoVal);
				        isoMapper.SetInput(iso.GetOutput());
					panel.getRenderWindow().Render();			
				  }
				 for(isoVal=1100F;isoVal<1300F;isoVal+=100F) {  //expand	
					iso.SetValue(0, isoVal);
					isoMapper.SetInput(iso.GetOutput());
					panel.getRenderWindow().Render();			
				  }
			       }
			}
			if (((String)evt.arg).equals("Exit")) {
				System.exit(1);
			}
		}
		
		return super.action(evt, what);
  	}
}					
					


	
