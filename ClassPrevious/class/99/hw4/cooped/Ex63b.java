///////////////////////////////////////////////////
// Dave Cooper
// Advanced Computer Graphics & Data Visualization
// 4/21/99
// Ex 6.3b
///////////////////////////////////////////////////

import vtk.*;
import java.awt.*;
import java.awt.event.*;

////////////////////////////////////
// class - Ex63b
// 	   Example 6.3b from text:
//         The Visualization Toolkit
////////////////////////////////////
public class Ex63b extends Frame{

	static myVTKPanel2 panel;
	vtkVolume16Reader v16;
	vtkMarchingCubes iso;
	vtkPolyDataMapper isoMapper;
	vtkActor isoActor;
	vtkProperty isoProp;
	vtkOutlineFilter outline;
	vtkPolyDataMapper outlineMapper;
	vtkActor outlineActor;
	vtkProperty outlineProp;
	float cValue;

	//////////////////////////////////////
	// function: Ex63b Constructor
	// purpose: main body of processing
	//////////////////////////////////////
	public Ex63b(){
      super();

		cValue = 0.0F;
		/////////////////////////
		v16 = new vtkVolume16Reader();
		v16.SetDataDimensions(128,128);
		v16.GetOutput().SetOrigin(0.0F,0.0F,0.0F);
		v16.SetDataByteOrderToLittleEndian();
		v16.SetFilePrefix("g:/vtkdata/headsq/half");
		v16.SetImageRange(20,40);
		v16.SetDataSpacing(1.6,1.6,1.5);

		iso = new vtkMarchingCubes();
		iso.SetInput(v16.GetOutput());
		iso.SetValue(1,1150);
      
		isoMapper = new vtkPolyDataMapper();
		isoMapper.SetInput(iso.GetOutput());
		isoMapper.ScalarVisibilityOff();

		isoActor = new vtkActor();
		isoActor.SetMapper(isoMapper);
		isoProp = new vtkProperty();
		isoProp = isoActor.GetProperty();
		isoProp.SetColor(1.0F,1.0F,1.0F);

		outline = new vtkOutlineFilter();
		outline.SetInput(v16.GetOutput());
		outlineMapper = new vtkPolyDataMapper();
		outlineMapper.SetInput(outline.GetOutput());
		outlineActor = new vtkActor();
		outlineActor.SetMapper(outlineMapper);
		outlineProp = new vtkProperty();
		outlineProp = outlineActor.GetProperty();

		/////////////////////////
		panel.getRenderer().AddActor(outlineActor);
		panel.getRenderer().AddActor(isoActor);
		panel.getRenderer().SetBackground(1,1,1);
		panel.getRenderer().SetBackground(0.1F,0.2F,0.4F);
	}

	//////////////////////////////////////
	// function: updateContour
	// purpose: changes the cValue 
	//////////////////////////////////////
   public void updateContour(){
		if(cValue == 1150)
			cValue = 500;
		else
			cValue = 1150;

		iso.SetValue(1,cValue);
		isoMapper.SetInput(iso.GetOutput());
		isoActor.SetMapper(isoMapper);
	}
   
	//////////////////////////////////////
	// function: main
	// purpose: sets up the gui
	//////////////////////////////////////
   public static void main(String args[]){
		System.out.println("Press 'a' to switch between skinned and skinless contours");
		panel = new myVTKPanel2();
		panel.resize(500,500);
		Ex63b frame = new Ex63b();
   	panel.setHook(frame);

		frame.add("Center", panel);
		frame.pack();
		frame.show();

	}
}


////////////////////////////////////
// class - myVTKPanel2
//	  panel class to assist in
//         catching events
////////////////////////////////////
class myVTKPanel2 extends vtkPanel {

	Ex63b handle;

	public myVTKPanel2()
	{
		super();
	}

	public void keyPressed(KeyEvent e)
	{
		vtkActorCollection collection = super.getRenderer().GetActors();
		vtkActor tempActor = null;
		switch(e.getKeyChar()) {
		case 'a':
			handle.updateContour();
			rw.Render();
			break;
		}
		super.keyPressed(e);
	}

	public void setHook(Ex63b appHook){
		handle = appHook;
	}
}

