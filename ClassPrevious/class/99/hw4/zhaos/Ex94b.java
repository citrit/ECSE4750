/* Name: Shuo Zhao
   Advanced Computer Graphics and Data Visulization
   Homework 4

   Modified from probecomb in the VTK CD 
*/

import vtk.*;
import java.awt.*;

public class Ex94b extends Frame
{

	public static void main ( String args[] )
	{
		vtkCamera camera;
		vtkPanel panel = new vtkPanel();
		Ex94b h = new Ex94b();

		vtkPLOT3DReader pl3d = new vtkPLOT3DReader();
		pl3d.SetXYZFileName("g:/vtkdata/combxyz.bin");
		pl3d.SetQFileName("g:/vtkdata/combq.bin");
		pl3d.SetScalarFunctionNumber(100);
		pl3d.SetVectorFunctionNumber(202);
        pl3d.DebugOn();

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

		vtkContourFilter contour = new vtkContourFilter();
		contour.SetInput(probe.GetOutput());
		double range[] = pl3d.GetOutput().GetScalarRange();
		contour.GenerateValues(50, range[0], range[1]);

		vtkPointSource points=new vtkPointSource();
		points.SetNumberOfPoints(200);
		points.SetCenter(0.8F,-12.2F,0.03F);
		points.SetRadius(2.0);

		vtkStreamLine stream  = new vtkStreamLine();
		stream.SetInput(pl3d.GetOutput());
		stream.SetSource(points.GetOutput());
		stream.SetMaximumPropagationTime(100);
		stream.SetIntegrationStepLength(0.02F);
		stream.SetStepLength(0.02);
		stream.SpeedScalarsOn();
		stream.SetTerminalSpeed(.1);
		stream.Update();

		vtkPolyDataMapper contourMapper = new vtkPolyDataMapper();
		contourMapper.SetInput(stream.GetOutput());
		contourMapper.ScalarVisibilityOn();
		contourMapper.SetScalarRange(range[0], range[1]);
		
		vtkActor planeActor = new vtkActor();
	        planeActor.SetMapper(contourMapper);

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
		panel.getRenderer().SetBackground(1,1,1);
		panel.setSize(500,500);

		camera = panel.getRenderer().GetActiveCamera();
		camera.SetClippingRange(3.95297,50);
		camera.SetFocalPoint(8.88908,0.595038,29.3342);
		camera.SetPosition(-12.3332,31.7479,41.2387);
		camera.ComputeViewPlaneNormal();
		camera.SetViewUp(0.060772,-0.319905,0.945498);

		h.add("Center", panel);
        h.pack();

		h.show();
	}
	
}		



