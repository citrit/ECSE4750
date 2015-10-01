/*Modified from Sample.cxx from Visualization Toolkit CD
  The idea of the animation part was from the MriIso example
  from the course webpage*/
//Kent Cheng hw4
import vtk.*;
import java.awt.*;

public class Ex63a extends Frame
{

	vtkPanel panel =null;
	vtkContourFilter contour;
	vtkPolyDataMapper contourMapper;
	
	
	public static void main ( String args[] )
	{
	  
	  float range[]=new float[2];
	  
	  Ex63a f = new Ex63a(); 
	  f.panel = new vtkPanel();
	  f.panel.resize(400,400);
	  f.contour = new vtkContourFilter();
	  f.contourMapper = new vtkPolyDataMapper();
	 
	
	  // Create surface of implicit function
	
	  // Sample quadric function
	  vtkQuadric quadric = new vtkQuadric();
	  quadric.SetCoefficients(1,2,3,0,1,0,0,0,0,0);
	
	  vtkSampleFunction sample = new vtkSampleFunction();
	  sample.SetSampleDimensions(25,25,25);
	  sample.SetImplicitFunction(quadric);
	  sample.DebugOn();
	
	  // Generate implicit surface
	  f.contour.SetInput(sample.GetOutput());
	  range[0] = 1.0F; range[1] = 6.0F;
	  f.contour.GenerateValues(3,range[0],range[1]);
	  f.contour.DebugOn();
	
	  // Map contour
	  f.contourMapper.SetInput(f.contour.GetOutput());
	  f.contourMapper.SetScalarRange(0,7);
	
	  vtkActor contourActor = new vtkActor();
	  contourActor.SetMapper(f.contourMapper);
	  
	
	  // Create outline around data
	
	  vtkOutlineFilter outline = new vtkOutlineFilter();
	  outline.SetInput(sample.GetOutput());
	
	  vtkPolyDataMapper outlineMapper = new vtkPolyDataMapper();
	  outlineMapper.SetInput(outline.GetOutput());
	
	  vtkActor outlineActor = new vtkActor();
	  outlineActor.SetMapper(outlineMapper);
	  outlineActor.GetProperty().SetColor(1,1,1);
          
	  vtkLight light = new vtkLight();
	  f.panel.getRenderer().AddLight(light);
	  f.panel.getRenderer().AddActor(contourActor);
	  f.panel.getRenderer().AddActor(outlineActor);

	  	
	  f.add("Center", f.panel);
          f.pack();

          // Set up the menu
          MenuBar mb = new MenuBar();
          Menu file = new Menu( "File" );
	  file.add(new MenuItem("Animate"));
          file.add( new MenuItem( "Exit" ));
          mb.add( file );
          f.setMenuBar( mb );
          f.show();
	  System.out.println("Here we go");
	  	  
	 }
/** Let capture menu events (and all others...) */
	public boolean action(Event evt, Object what)
	{
		int num=1;  //number of times to run the animation sequence
		float contVal;
		if (evt.target instanceof MenuItem) 
		{
		// Since we didn't save references to each of the menu objects,
		// we check which one was pressed by comparing labels.
			if (((String)evt.arg).equals("Animate")) {
				for(int k=0;k<num;k++){
				 for(contVal=8.0F;contVal>1.0F;contVal-=0.1F) { //shrink
					contour.SetValue(0, contVal);
				        contour.SetValue(1, contVal+1);
					contour.SetValue(2, contVal+2);
					contourMapper.SetInput(contour.GetOutput());
					panel.getRenderWindow().Render();			
				  }
				 for(contVal=1.0F;contVal<8.0F;contVal+=0.1F) {  //expand	
					contour.SetValue(0, contVal);
					contour.SetValue(1, contVal+1);
					contour.SetValue(2, contVal+2);
					contourMapper.SetInput(contour.GetOutput());
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