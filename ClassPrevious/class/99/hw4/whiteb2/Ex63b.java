// Bryan Whitehead Hw4
import vtk.*;
import java.awt.*;

public class Ex63b extends Frame
{

	vtkPanel panel =null;
	vtkContourFilter contour;
	vtkPolyDataMapper contourMapper;
	float value;	
	public Ex63b()
	{
		
		panel = new vtkPanel();
		panel.resize(400,400);
		contour = new vtkContourFilter();
		contourMapper = new vtkPolyDataMapper();
		value = 1.0F;
	}
	public static void main ( String args[] )
	{
		vtkPanel panel;
	
		Ex63b s = new Ex63b(); 
	
		vtkVolume16Reader reader = new vtkVolume16Reader();
		reader.SetDataDimensions(128, 128);
		reader.GetOutput().SetOrigin(0.0, 0.0, 0.0);
		reader.SetDataByteOrderToLittleEndian();

		//'half' files should be in the same director that 
		// you execute from
		reader.SetFilePrefix("g:/vtkdata/headsq/half");
		reader.SetImageRange(1, 20);
		reader.SetDataSpacing(1.6, 1.6, 1.5);
   
		s.contour.SetInput(reader.GetOutput());
	  	s.contour.SetValue(0,1400);
		s.contourMapper.SetInput(s.contour.GetOutput());
		s.contourMapper.SetScalarRange(0,7);
	
		vtkActor contourActor = new vtkActor();
		contourActor.SetMapper(s.contourMapper);
		s.panel.getRenderer().AddActor(contourActor);

		vtkOutlineFilter outline = new vtkOutlineFilter();
		outline.SetInput(reader.GetOutput());
	
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
				 for(value=1300F;value<=1500F;value+=50F) {	
					contour.SetValue(0, value);
					contourMapper.SetInput(contour.GetOutput());
					panel.getRenderWindow().Render();	
					System.out.println("Contour value: " + value);							
				  }
				
			}
			if (((String)evt.arg).equals("Exit")) {
				System.exit(1);
			}
		}
		return super.action(evt, what);
  	}
}					