///////////////////////////////////////////////////
// Dave Cooper
// Advanced Computer Graphics & Data Visualization
// 4/21/99
// Ex 6.3a
///////////////////////////////////////////////////

import vtk.*;
import java.awt.*;
import java.awt.event.*;

////////////////////////////////////
// class - Ex63a
// 	   Example 6.3a from text:
//         The Visualization Toolkit
////////////////////////////////////
public class Ex63a extends Frame {

	static myVTKPanel1 panel;

	vtkQuadric quadric;
	vtkSampleFunction sample;

	vtkContourFilter contour;
	vtkPolyDataMapper contourMapper;
	vtkActor contourActor;

	vtkOutlineFilter outline;
	vtkPolyDataMapper outlineMapper;
	vtkActor outlineActor;

	float cValue;


	//////////////////////////////////////
	// function: Ex63a Constructor
	// purpose: main body of processing
	//////////////////////////////////////
	public Ex63a(){
		super();

		cValue = 0.0F;
		quadric = new vtkQuadric();
		quadric.SetCoefficients(1,2,3,0,1,0,0,0,0,0);

		sample = new vtkSampleFunction();
		sample.SetSampleDimensions(25,25,25);
		sample.SetImplicitFunction(quadric);

		contour = new vtkContourFilter();
		contour.SetInput(sample.GetOutput());
//		contour.GenerateValues(4,1.0F,6.0F);   //uncomment for multiple values & comment out next line
		contour.SetValue(1,6.0F);	       //comment out for multiple values & uncomment out previous line

		contourMapper = new vtkPolyDataMapper();
		contourMapper.SetInput(contour.GetOutput());
		contourMapper.SetScalarRange(0,7);

		contourActor = new vtkActor();
		contourActor.SetMapper(contourMapper);

		outline = new vtkOutlineFilter();
		outline.SetInput(sample.GetOutput());

		outlineMapper = new vtkPolyDataMapper();
		outlineMapper.SetInput(outline.GetOutput());

		outlineActor = new vtkActor();
		outlineActor.SetMapper(outlineMapper);
		outlineActor.GetProperty().SetColor(1,1,1);

		panel.getRenderer().AddActor(outlineActor);
		panel.getRenderer().AddActor(contourActor);

	}

	//////////////////////////////////////
	// function: updateContour
	// purpose: changes the cValue 
	//////////////////////////////////////
	public void updateContour(){
		if(cValue > 6.0F)
			cValue = 0.0F;
		else
			cValue = cValue + 0.1F;

		contour.SetValue(1,cValue);
		contourMapper.SetInput(contour.GetOutput());
		contourMapper.SetScalarRange(0,7);
		contourActor.SetMapper(contourMapper);
	}

	//////////////////////////////////////
	// function: main
	// purpose: sets up the gui
	//////////////////////////////////////
	public static void main(String args[]){
		System.out.println("Press 'a' to itterate through a series of contours");
		panel = new myVTKPanel1();
		panel.resize(200,200);
		Ex63a frame = new Ex63a();
		panel.setHook(frame);

		frame.add("Center", panel);
		frame.pack();
		frame.show();

	}
}


////////////////////////////////////
// class - myVTKPanel1
//	  panel class to assist in
//         catching events
////////////////////////////////////
class myVTKPanel1 extends vtkPanel {

	Ex63a handle;

	public myVTKPanel1()
	{
		super();
	}

	public void keyPressed(KeyEvent e)
	{
		vtkActorCollection collection = super.getRenderer().GetActors();
		vtkActor tempActor = null;
		switch(e.getKeyChar()) {
		case 'a':
			for(int i=0; i<60; i++) {
				handle.updateContour();
				rw.Render();
			}        
			break;
		}
		super.keyPressed(e);
	}

	public void setHook(Ex63a appHook){
		handle = appHook;
	}
}


