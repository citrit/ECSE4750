///////////////////////////////////////////////////
// Dave Cooper
// Advanced Computer Graphics & Data Visualization
// 4/21/99
// Ex 9.4b
///////////////////////////////////////////////////
import vtk.*;
import java.awt.*;

//////////////////////////////////////
// class: Ex94b
//////////////////////////////////////
public class Ex94b extends Frame{
	
	//////////////////////////////////////
	// function: main
	// purpose: main body of processing
	//////////////////////////////////////
	public static void main(String args[]){
		
		vtkPanel panel = new vtkPanel();
      Ex94b ex94b = new Ex94b();
      panel.setSize(500, 500);
      panel.getRenderer().SetBackground(1.0, 1.0, 1.0);

		//READER
		vtkPLOT3DReader reader = new vtkPLOT3DReader();
		reader.SetXYZFileName("g:/vtkdata/combxyz.bin");
		reader.SetQFileName("g:/vtkdata/combq.bin");
		reader.SetScalarFunctionNumber(100);
		reader.SetVectorFunctionNumber(202);
		reader.Update();

		//OUTLINE
		vtkStructuredGridOutlineFilter outlineF = new vtkStructuredGridOutlineFilter();
		outlineF.SetInput(reader.GetOutput());
		vtkPolyDataMapper outlineMapper = new vtkPolyDataMapper();
		outlineMapper.SetInput(outlineF.GetOutput());
		vtkActor outline = new vtkActor();
		outline.SetMapper(outlineMapper);
		outline.GetProperty().SetColor(0,0,0);

		//SOURCE PLANE
		vtkPlaneSource plane = new vtkPlaneSource();
		plane.SetResolution(8,8);    //fix

		//TRANSFORM1
		vtkTransform tran1 = new vtkTransform();
		tran1.Translate(4.0,0.0,30.0);
		tran1.Scale(5.0,5.0,5.0);
		tran1.RotateY(90);
		vtkTransformPolyDataFilter tran1PolyDataFilter = new vtkTransformPolyDataFilter();
		tran1PolyDataFilter.SetInput(plane.GetOutput());
		tran1PolyDataFilter.SetTransform(tran1);

		vtkOutlineFilter outline1 = new vtkOutlineFilter();
		outline1.SetInput(tran1PolyDataFilter.GetOutput());
		vtkPolyDataMapper mapper1 = new vtkPolyDataMapper();
		mapper1.SetInput(outline1.GetOutput());
		vtkActor outline1Actor = new vtkActor();
		outline1Actor.SetMapper(mapper1);
		outline1Actor.GetProperty().SetColor(0.0,0.0,0.0);

		//TRANSFORM2
		vtkTransform tran2 = new vtkTransform();
		tran2.Translate(9.0, 0.0, 31.0);
		tran2.Scale(5.0,5.0,5.0);
		tran2.RotateY(90.0);
		vtkTransformPolyDataFilter tran2PolyDataFilter = new vtkTransformPolyDataFilter();
		tran2PolyDataFilter.SetInput(plane.GetOutput());
		tran2PolyDataFilter.SetTransform(tran2);

		vtkOutlineFilter outline2 = new vtkOutlineFilter();
		outline2.SetInput(tran2PolyDataFilter.GetOutput());
		vtkPolyDataMapper mapper2 = new vtkPolyDataMapper();
		mapper2.SetInput(outline2.GetOutput());
		vtkActor outline2Actor = new vtkActor();
		outline2Actor.SetMapper(mapper2);
		outline2Actor.GetProperty().SetColor(0.0,0.0,0.0);

		//TRANSFORM3
		vtkTransform tran3 = new vtkTransform();
		tran3.Translate(13.0,0.0,33.0);
		tran3.Scale(5.0,5.0,5.0);
		tran3.RotateY(90.0);
		vtkTransformPolyDataFilter tran3PolyDataFilter = new vtkTransformPolyDataFilter();
		tran3PolyDataFilter.SetInput(plane.GetOutput());
		tran3PolyDataFilter.SetTransform(tran3);

		vtkOutlineFilter outline3 = new vtkOutlineFilter();
		outline3.SetInput(tran3PolyDataFilter.GetOutput());
		vtkPolyDataMapper mapper3 = new vtkPolyDataMapper();
		mapper3.SetInput(outline2.GetOutput());
		vtkActor outline3Actor = new vtkActor();
		outline3Actor.SetMapper(mapper3);
		outline3Actor.GetProperty().SetColor(0.0,0.0,0.0);

		//PROBE FILTER
		vtkProbeFilter probe = new vtkProbeFilter();
		probe.SetInput(tran1PolyDataFilter.GetOutput());
		probe.SetSource(reader.GetOutput());

		vtkDataSetMapper dataSetMapper = new vtkDataSetMapper();
		dataSetMapper.SetInput(probe.GetOutput());

		vtkActor probeActor = new vtkActor();
		probeActor.SetMapper(dataSetMapper);
		probeActor.GetProperty().SetColor(1.0,0.0,0.0);

		//STREAMLINE
		vtkStreamLine streamers = new vtkStreamLine();
		streamers.SetInput(reader.GetOutput());
		streamers.SetSource(probe.GetOutput());
      double maxNorm = reader.GetOutput().GetPointData().GetVectors().GetMaxNorm();
      double propTime = 5.0 * reader.GetOutput().GetLength() / maxNorm;
      streamers.SetMaximumPropagationTime(propTime); //fix
		streamers.SetStepLength(propTime / 100.0); //fix


		vtkPolyDataMapper streamerMapper = new vtkPolyDataMapper();
		streamerMapper.SetInput(streamers.GetOutput());
		streamerMapper.SetScalarRange(reader.GetOutput().GetScalarRange());
		vtkActor streamerActor = new vtkActor();
		streamerActor.SetMapper(streamerMapper);
		streamerActor.GetProperty().SetColor(0.0,0.0,0.0);

		panel.getRenderer().AddActor(outline);
		panel.getRenderer().AddActor(outline1Actor);
		panel.getRenderer().AddActor(outline2Actor);
		panel.getRenderer().AddActor(outline3Actor);
		panel.getRenderer().AddActor(streamerActor);
		panel.getRenderer().SetBackground(1.0F,1.0F,1.0F);
	
		ex94b.add("Center", panel);
		ex94b.pack();
		ex94b.show();
	}
}

