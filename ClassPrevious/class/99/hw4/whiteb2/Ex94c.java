//Bryan Whitehead hw4
//modified from code found on VTK disk

import vtk.*;
import java.awt.*;

public class Ex94c extends Frame
{

	public static void main ( String args[] )
	{
		
		vtkPanel panel = new vtkPanel();
		Ex94c s = new Ex94c();

		vtkPLOT3DReader pl3d = new vtkPLOT3DReader();
		pl3d.SetXYZFileName("g:/vtkdata/combxyz.bin");
		pl3d.SetQFileName("g:/vtkdata/combq.bin");

		vtkPlaneSource plane = new vtkPlaneSource();
		plane.SetResolution(50, 50);

		vtkTransform transP1 = new vtkTransform();
		transP1.Translate(3.7, 0.0, 28.37);
		transP1.Scale(5, 5, 5);
		transP1.RotateY(90);

		vtkTransformPolyDataFilter tpd1 = new vtkTransformPolyDataFilter();
		tpd1.SetInput(plane.GetOutput());
		tpd1.SetTransform(transP1);

		vtkOutlineFilter outTpd1 = new vtkOutlineFilter();
		outTpd1.SetInput(tpd1.GetOutput());

		vtkPolyDataMapper mapTpd1 = new vtkPolyDataMapper();
		mapTpd1.SetInput(outTpd1.GetOutput());

		vtkActor tpd1Actor = new vtkActor();
		tpd1Actor.SetMapper(mapTpd1);
		tpd1Actor.GetProperty().SetColor(0,0,0);

		vtkTransform transP2 = new vtkTransform();
		transP2.Translate(9.2, 0.0, 31.20);
		transP2.Scale(5,5,5);
		transP2.RotateY(90);

		vtkTransformPolyDataFilter tpd2 = new vtkTransformPolyDataFilter();
		tpd2.SetInput(plane.GetOutput());
		tpd2.SetTransform(transP2);

		vtkOutlineFilter outTpd2 = new vtkOutlineFilter();
		outTpd2.SetInput(tpd2.GetOutput());

		vtkPolyDataMapper mapTpd2 =  new vtkPolyDataMapper();
		mapTpd2.SetInput(outTpd2.GetOutput());

		vtkActor tpd2Actor = new vtkActor();
		tpd2Actor.SetMapper(mapTpd2);
		tpd2Actor.GetProperty().SetColor(0,0,0);

		vtkTransform transP3 = new vtkTransform();
		transP3.Translate(13.27,0.0,33.30);
		transP3.Scale(5,5,5);
		transP3.RotateY(90);

		vtkTransformPolyDataFilter tpd3 = new vtkTransformPolyDataFilter();
		tpd3.SetInput(plane.GetOutput());
		tpd3.SetTransform(transP3);

		vtkOutlineFilter outTpd3 = new vtkOutlineFilter();
		outTpd3.SetInput(tpd3.GetOutput());

		vtkPolyDataMapper mapTpd3 = new vtkPolyDataMapper();
		mapTpd3.SetInput(outTpd3.GetOutput());

		vtkActor tpd3Actor = new vtkActor();
		tpd3Actor.SetMapper(mapTpd3);
		tpd3Actor.GetProperty().SetColor(0,0,0);

		vtkAppendPolyData appendF = new vtkAppendPolyData();
		appendF.AddInput(tpd1.GetOutput());
		appendF.AddInput(tpd2.GetOutput());
		appendF.AddInput(tpd3.GetOutput());

		vtkProbeFilter probe = new vtkProbeFilter();
		probe.SetInput(appendF.GetOutput());
		probe.SetSource(pl3d.GetOutput());

		vtkConnectivityFilter connectivityfilter = new vtkConnectivityFilter();
    		connectivityfilter.SetInput(probe.GetOutput());
    		connectivityfilter.SetExtractionModeToSpecifiedRegions();
		for(int i=0;i<3;i++) connectivityfilter.AddSpecifiedRegion(i);

		vtkWarpVector warpvector = new vtkWarpVector();
    		warpvector.SetInput(connectivityfilter.GetOutput());
    		warpvector.SetScaleFactor(0.005);

		vtkDataSetMapper warpvectormapper = new vtkDataSetMapper();
    		warpvectormapper.SetInput(warpvector.GetOutput());

		vtkActor planeActor = new vtkActor();
	        planeActor.SetMapper(warpvectormapper);

		vtkStructuredGridOutlineFilter outline = new vtkStructuredGridOutlineFilter();
		outline.SetInput(pl3d.GetOutput());

		vtkPolyDataMapper outlineMapper = new vtkPolyDataMapper();
		outlineMapper.SetInput(outline.GetOutput());
	
		vtkActor outlineActor = new vtkActor();
		outlineActor.SetMapper(outlineMapper);
		outlineActor.GetProperty().SetColor(0,0,0);

		panel.getRenderer().AddActor(outlineActor);
		panel.getRenderer().AddActor(planeActor);
		panel.getRenderer().AddActor(tpd1Actor);
		panel.getRenderer().AddActor(tpd2Actor);
		panel.getRenderer().AddActor(tpd3Actor);
		panel.getRenderer().SetBackground(0.5,0.5,0.5);
		panel.setSize(400,400);

		s.add("Center", panel);
                s.pack();

		MenuBar mb = new MenuBar();
		Menu file = new Menu( "Exit" );
		file.add( new MenuItem( "Exit" ));
		mb.add( file );
		s.setMenuBar( mb );
		s.show();
	}

	public boolean action(Event evt, Object what)
	{
		if (evt.target instanceof MenuItem) 
			if (((String)evt.arg).equals("Exit"))
				System.exit(1);
		return super.action(evt, what);
  	}
}		



