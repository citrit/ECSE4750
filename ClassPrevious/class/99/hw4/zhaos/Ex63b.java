/*	Name: Shuo Zhao
	Advanced Computer Graphics and Data Visualization
	Homework 4
*/
import vtk.*;
import java.awt.*;

public class Ex63b extends Frame
{
	vtkPanel panel;
	vtkMarchingCubes iso;
	vtkPolyDataMapper isoMapper;
	float value;

	public Ex63b()
	{
		panel = new vtkPanel();
		iso = new vtkMarchingCubes();
		isoMapper = new vtkPolyDataMapper();
	}
		
	public static void main ( String args[] )
	{
		vtkCamera camera;

		
		Ex63b h = new Ex63b();
		
		/* Setup the reader object */
		vtkVolume16Reader V16 = new vtkVolume16Reader();

		V16.SetDataDimensions(128, 128);
		V16.GetOutput().SetOrigin(0.0, 0.0, 0.0);
		V16.SetDataByteOrderToLittleEndian();
		/* all the data files have "half" as the prefix */

		V16.SetFilePrefix("g:/vtkdata/headsq/half");
		V16.SetImageRange(20,40);
		V16.SetDataSpacing(1.6, 1.6, 1.5);

		/* Using MarchingCubes object to store the data */
		/* it performs as if it is a contourfilter object */

		h.iso.SetInput(V16.GetOutput());
		h.iso.SetValue(0, 1400);
		
		h.isoMapper.SetInput(h.iso.GetOutput());
		h.isoMapper.SetScalarRange(0,7);

		vtkActor isoActor = new vtkActor();
		isoActor.SetMapper(h.isoMapper);
		isoActor.GetProperty().SetColor(0,0,1);

		vtkOutlineFilter outline = new vtkOutlineFilter();
		outline.SetInput(V16.GetOutput());

		vtkPolyDataMapper outlineMapper =  new vtkPolyDataMapper();
		outlineMapper.SetInput(outline.GetOutput());

		vtkActor outlineActor = new vtkActor();
		outlineActor.SetMapper(outlineMapper);
		outlineActor.GetProperty().SetColor(1,1,1);
		
		h.panel.getRenderer().AddActor(outlineActor);
		h.panel.getRenderer().AddActor(isoActor);
		h.panel.getRenderer().SetBackground(0, 0, 0);
		h.panel.setSize(400, 400);

		h.add("Center", h.panel);
        h.pack();

		 // Set up the menu
		MenuBar mb = new MenuBar();
		Menu file = new Menu( "File" );
		file.add( new MenuItem( "Animate" ));
		file.add( new MenuItem( "Exit" ));
		mb.add( file );
		h.setMenuBar( mb );
		
		h.show();
	}
	
	public boolean action(Event evt, Object what)
    {
		if (evt.target instanceof MenuItem) 
        {
         
          if (((String)evt.arg).equals("Animate")) 
		  {
				for(value=1600F; value>=1200F; )
				{
					animate();
					value=value-50F;
				}
		  }
			 
          if (((String)evt.arg).equals("Exit"))
		  {
				System.exit(1);
		  }
		}
		return true;
     }
	
	public void animate()
	{
		iso.SetValue(0, value);
		isoMapper.SetInput(iso.GetOutput());
		panel.getRenderer().Render();
	}
}					


	
