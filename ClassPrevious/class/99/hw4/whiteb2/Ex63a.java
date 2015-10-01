// Bryan Whitehead Hw4
// Code modified from code found on VTK disk

import vtk.*;
import java.awt.*;

public class Ex63a extends Frame
{

	vtkPanel panel =null;
	vtkContourFilter contour;
	vtkPolyDataMapper contourMapper;
	float value;	
	public Ex63a()
	{
		
		panel = new vtkPanel();
		panel.resize(400,400);
		contour = new vtkContourFilter();
		contourMapper = new vtkPolyDataMapper();
		value = 1.0F;
	}
	public static void main ( String args[] )
	{
	  	float range[]=new float[2];
	  	vtkPanel panel;
		
		Ex63a s = new Ex63a(); 

		vtkQuadric quadric = new vtkQuadric();
		quadric.SetCoefficients(1,2,3,0,1,0,0,0,0,0);
	
		vtkSampleFunction sample = new vtkSampleFunction();
	    	sample.SetSampleDimensions(25,25,25);
	    	sample.SetImplicitFunction(quadric);
		sample.DebugOn();
	
		s.contour.SetInput(sample.GetOutput());
	  
	  	range[0] = 1.0F; range[1] = 6.0F;
	    
	    	s.contour.GenerateValues(1,range[0],range[1]);
	    	s.contour.DebugOn();
	
		s.contourMapper.SetInput(s.contour.GetOutput());
		s.contourMapper.SetScalarRange(0,7);
	
		vtkActor contourActor = new vtkActor();
	    	contourActor.SetMapper(s.contourMapper);
	    	s.panel.getRenderer().AddActor(contourActor);
	
		vtkOutlineFilter outline = new vtkOutlineFilter();
	    	outline.SetInput(sample.GetOutput());
	
		vtkPolyDataMapper outlineMapper = new vtkPolyDataMapper();
	    	outlineMapper.SetInput(outline.GetOutput());
	
		vtkActor outlineActor = new vtkActor();
	    	outlineActor.SetMapper(outlineMapper);
		outlineActor.GetProperty().SetColor(1,1,1);
            	s.panel.getRenderer().AddActor(outlineActor);
	
		vtkLight light = new vtkLight();
		s.panel.getRenderer().AddLight(light);

		s.add("Center", s.panel);
        	s.pack();

		MenuBar mb = new MenuBar();
		Menu file = new Menu( "Menu" );
		file.add(new MenuItem("Animate"));
		file.add( new MenuItem( "Exit" ));
		mb.add( file );
		s.setMenuBar( mb );
		s.show();
		System.out.println("Use menu to start animation sequence");
		System.out.println("Drag mouse to rotate");
		System.out.println("Use menu to exit");
	}
	
	public boolean action(Event evt, Object what)
	{
		if (evt.target instanceof MenuItem) 
		{
			if (((String)evt.arg).equals("Animate")) {
				 for(value=0.1F;value<8.0F;value+=0.1F) {	
					contour.SetValue(0, value);
					contourMapper.SetInput(contour.GetOutput());
					panel.getRenderWindow().Render();			
				  }
			}
			if (((String)evt.arg).equals("Exit")) 
				System.exit(1);
		}
		return super.action(evt, what);
  	}
}					