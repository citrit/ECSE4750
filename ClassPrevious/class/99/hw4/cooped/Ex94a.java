///////////////////////////////////////////////////
// Dave Cooper
// Advanced Computer Graphics & Data Visualization
// 4/21/99
// Ex 9.4a
///////////////////////////////////////////////////

import vtk.*;
import java.awt.*;
import java.awt.event.*;


//////////////////////////////////////
// class: Ex94a
//////////////////////////////////////
public class Ex94a extends vtkPanel{

	vtkPLOT3DReader reader;
   vtkStructuredGridOutlineFilter outlineF;
   vtkPolyDataMapper outlineMapper;
	vtkActor outline;
	vtkProbeFilter probe;
	vtkHedgeHog hhog;
	vtkLookupTable lut;
	vtkPolyDataMapper hhogMapper;
	vtkActor hhogActor;
   vtkTransform tran;
	vtkTransformPolyDataFilter tranPDF;
	vtkOutlineFilter outlineF2;
	vtkPolyDataMapper transMapper;
	vtkActor transActor;
	vtkPlaneSource plane;


	//////////////////////////////////////
	// function: Ex94a Constructor
	// purpose: main body of processing
	//////////////////////////////////////
	public Ex94a(){
		super();

		//READER
		reader = new vtkPLOT3DReader();
		reader.SetXYZFileName("g:/vtkdata/combxyz.bin");
		reader.SetQFileName("g:/vtkdata/combq.bin");
		reader.SetScalarFunctionNumber(100);
		reader.SetVectorFunctionNumber(202);
		reader.Update();

		//OUTLINE
		outlineF = new vtkStructuredGridOutlineFilter();
		outlineF.SetInput(reader.GetOutput());
		outlineMapper = new vtkPolyDataMapper();
		outlineMapper.SetInput(outlineF.GetOutput());
      outline = new vtkActor();
		outline.SetMapper(outlineMapper);
		outline.GetProperty().SetColor(0,0,0);

		//SOURCE PLANE
		plane = new vtkPlaneSource();
		plane.SetResolution(30,30);

		//TRANSFORM
		tran = new vtkTransform();
		tran.Translate(4.0,-1.0,30);
		tran.Scale(2,2,2);
		tranPDF = new vtkTransformPolyDataFilter();
		tranPDF.SetInput(plane.GetOutput());
		tranPDF.SetTransform(tran);
		outlineF2 = new vtkOutlineFilter();
		outlineF2.SetInput(tranPDF.GetOutput());
		transMapper = new vtkPolyDataMapper();
		transMapper.SetInput(outlineF2.GetOutput());
		transActor = new vtkActor();
		transActor.SetMapper(transMapper);
		transActor.GetProperty().SetColor(0,0,0);
      
		//PROBE FILTER
		probe = new vtkProbeFilter();
		probe.SetSource(reader.GetOutput());
		probe.SetInput(tranPDF.GetOutput());

		//HEDGEHOG
		hhog = new vtkHedgeHog();
		hhog.SetInput(probe.GetOutput());
		hhog.SetScaleFactor(0.02);
		lut = new vtkLookupTable();
		lut.Build();
		hhogMapper = new vtkPolyDataMapper();
		hhogMapper.SetInput(hhog.GetOutput());
		hhogMapper.SetScalarRange(50, 500);
		hhogMapper.SetLookupTable(lut);
		hhogActor = new vtkActor();
		hhogActor.SetMapper(hhogMapper);

		getRenderer().AddActor(outline);
		getRenderer().AddActor(hhogActor);
		getRenderer().AddActor(transActor);
		getRenderer().SetBackground(0.0F,0.0F,1.0F);
	}

	//////////////////////////////////////
	// function: main
	// purpose: sets up the gui
	//////////////////////////////////////
	public static void main(String args[]){
		Ex94a panel = new Ex94a();
		panel.resize(752,752);
		Frame f = new Frame();

		f.add("Center", panel);
		f.pack();
		f.show();
	}
}
