// Name: Shuo Zhao
// Advanced Computer Graphics and Data Visualization
// Homework 4

//--------------------------------------------------------

import vtk.*;
import java.awt.*;

public class Ex63a extends Frame
{

	vtkPanel panel;
	vtkContourFilter contour;
	vtkPolyDataMapper contourMapper;
	float value;	
	int i;
	vtkRenderWindow rw;

	public Ex63a()
	{
	   panel = new vtkPanel();
       contour = new vtkContourFilter();
       contourMapper = new vtkPolyDataMapper();
	   value = 0.0F;
	   i=0;
	}

	public static void main ( String args[] )
	{
	  vtkCamera camera;
	  float range[]=new float[2];
	
	  Ex63a s = new Ex63a();

	// Create surface of implicit function

	// Sample quadric function
      s.panel.setSize(400, 400);
	  vtkQuadric quadric = new vtkQuadric();
	  quadric.SetCoefficients(.5, 1, .2, 0, .1, 0, 0, .2, 0, 0);

	  vtkSampleFunction sample = new vtkSampleFunction();
	  sample.SetSampleDimensions(30, 30, 30);
	  sample.SetImplicitFunction(quadric);
	  sample.DebugOn();
	
	  // Generate implicit surface
	  s.contour.SetInput(sample.GetOutput());
	 
	  s.contour.GenerateValues(4, 0.0, 1.2);
	  s.contour.DebugOn();
	
	  // Map contour
	  s.contourMapper.SetInput(s.contour.GetOutput());
	  s.contourMapper.SetScalarRange(0.0, 1.2);
	
	  vtkActor contourActor = new vtkActor();
	  contourActor.SetMapper(s.contourMapper);
	  s.panel.getRenderer().AddActor(contourActor);

	 //
	 // Create outline around data
	 //
	  vtkOutlineFilter outline = new vtkOutlineFilter();
	  outline.SetInput(sample.GetOutput());
	 
	  vtkPolyDataMapper outlineMapper = new vtkPolyDataMapper();
	  outlineMapper.SetInput(outline.GetOutput());
	
	  vtkActor outlineActor = new vtkActor();
	  outlineActor.SetMapper(outlineMapper);
	  outlineActor.GetProperty().SetColor(0,0,0);
      s.panel.getRenderer().AddActor(outlineActor);
	  s.panel.getRenderer().SetBackground(1, 1, 1);
	  vtkLight light = new vtkLight();
	  s.panel.getRenderer().AddLight(light);

	  s.rw = s.panel.getRenderWindow();

	  s.add("Center", s.panel);
      s.pack();

	   // Set up the menu
      MenuBar mb = new MenuBar();
      Menu file = new Menu( "File" );
      file.add( new MenuItem( "Animate" ));
      file.add( new MenuItem( "Exit" ));
      mb.add( file );
      s.setMenuBar( mb );

	  s.show();

	}

	public boolean action(Event evt, Object what)
    {
		if (evt.target instanceof MenuItem) 
        {
         
          if (((String)evt.arg).equals("Animate")) 
		  {
				for(value=0.0F; value<1.2F; )
				{
					animate(i);

					if(i==3) 
						i=0;
					else
						i++;
					value=value+0.1F;
				}
		  }
			 
          if (((String)evt.arg).equals("Exit"))
		  {
				System.exit(1);
		  }
		}
		return true;
     }
	
	public void animate(int i)
	{
		contour.SetValue(i, value);
		contourMapper.SetInput(contour.GetOutput());
		rw.Render();
	}
}					