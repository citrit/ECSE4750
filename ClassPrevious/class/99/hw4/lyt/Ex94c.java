/* Decompiled by Mocha from Ex94c.class */
/* Originally compiled from Ex94c.java */

import java.awt.*;
import vtk.*;
import java.io.PrintStream;

public class Ex94c extends Frame
{
    static final int density = 15;
    static final float warpscale = 0.0040F;

    public Ex94c()
    {
    }

    public boolean action(Event event, Object object)
    {
        if (event.target instanceof MenuItem && ((String)event.arg).equals("Exit"))
            System.exit(1);
        System.out.println(event + " Object: " + object);
        return super.action(event, object);
    }

    public static void main(String astring[])
    {
        Ex94c ex94c = new Ex94c();
        vtkPanel VtkPanel = new vtkPanel();
        VtkPanel.setSize(500, 500);
        VtkPanel.getRenderer().SetBackground(1.0, 1.0, 1.0);
        System.out.println("Here we go");
        vtkPLOT3DReader VtkPLOT3DReader = new vtkPLOT3DReader();
        VtkPLOT3DReader.SetXYZFileName("g:/vtkdata/combxyz.bin");
        VtkPLOT3DReader.SetQFileName("g:/vtkdata/combq.bin");
        VtkPLOT3DReader.SetScalarFunctionNumber(100);
        VtkPLOT3DReader.SetVectorFunctionNumber(202);
        VtkPLOT3DReader.Update();
        vtkPlaneSource VtkPlaneSource = new vtkPlaneSource();
        VtkPlaneSource.SetResolution(15, 15);
        vtkTransform VtkTransform1 = new vtkTransform();
        VtkTransform1.Translate(3.7, 0.0, 28.37);
        VtkTransform1.Scale(5.0, 5.0, 5.0);
        VtkTransform1.RotateY(90.0);
        vtkTransformPolyDataFilter VtkTransformPolyDataFilter1 = new vtkTransformPolyDataFilter();
        VtkTransformPolyDataFilter1.SetInput(VtkPlaneSource.GetOutput());
        VtkTransformPolyDataFilter1.SetTransform(VtkTransform1);
        vtkOutlineFilter VtkOutlineFilter1 = new vtkOutlineFilter();
        VtkOutlineFilter1.SetInput(VtkTransformPolyDataFilter1.GetOutput());
        vtkPolyDataMapper VtkPolyDataMapper1 = new vtkPolyDataMapper();
        VtkPolyDataMapper1.SetInput(VtkOutlineFilter1.GetOutput());
        vtkActor VtkActor1 = new vtkActor();
        VtkActor1.SetMapper(VtkPolyDataMapper1);
        VtkActor1.GetProperty().SetColor(0.0, 0.0, 0.0);
        vtkTransform VtkTransform2 = new vtkTransform();
        VtkTransform2.Translate(9.2, 0.0, 31.2);
        VtkTransform2.Scale(5.0, 5.0, 5.0);
        VtkTransform2.RotateY(90.0);
        vtkTransformPolyDataFilter VtkTransformPolyDataFilter2 = new vtkTransformPolyDataFilter();
        VtkTransformPolyDataFilter2.SetInput(VtkPlaneSource.GetOutput());
        VtkTransformPolyDataFilter2.SetTransform(VtkTransform2);
        vtkOutlineFilter VtkOutlineFilter2 = new vtkOutlineFilter();
        VtkOutlineFilter2.SetInput(VtkTransformPolyDataFilter2.GetOutput());
        vtkPolyDataMapper VtkPolyDataMapper2 = new vtkPolyDataMapper();
        VtkPolyDataMapper2.SetInput(VtkOutlineFilter2.GetOutput());
        vtkActor VtkActor2 = new vtkActor();
        VtkActor2.SetMapper(VtkPolyDataMapper2);
        VtkActor2.GetProperty().SetColor(0.0, 0.0, 0.0);
        vtkTransform VtkTransform3 = new vtkTransform();
        VtkTransform3.Translate(13.27, 0.0, 33.3);
        VtkTransform3.Scale(5.0, 5.0, 5.0);
        VtkTransform3.RotateY(90.0);
        vtkTransformPolyDataFilter VtkTransformPolyDataFilter3 = new vtkTransformPolyDataFilter();
        VtkTransformPolyDataFilter3.SetInput(VtkPlaneSource.GetOutput());
        VtkTransformPolyDataFilter3.SetTransform(VtkTransform3);
        vtkOutlineFilter VtkOutlineFilter3 = new vtkOutlineFilter();
        VtkOutlineFilter3.SetInput(VtkTransformPolyDataFilter3.GetOutput());
        vtkPolyDataMapper VtkPolyDataMapper3 = new vtkPolyDataMapper();
        VtkPolyDataMapper3.SetInput(VtkOutlineFilter3.GetOutput());
        vtkActor VtkActor3 = new vtkActor();
        VtkActor3.SetMapper(VtkPolyDataMapper3);
        VtkActor3.GetProperty().SetColor(0.0, 0.0, 0.0);
        vtkAppendPolyData VtkAppendPolyData = new vtkAppendPolyData();
        VtkAppendPolyData.AddInput(VtkTransformPolyDataFilter1.GetOutput());
        VtkAppendPolyData.AddInput(VtkTransformPolyDataFilter2.GetOutput());
        VtkAppendPolyData.AddInput(VtkTransformPolyDataFilter3.GetOutput());
        vtkProbeFilter VtkProbeFilter = new vtkProbeFilter();
        VtkProbeFilter.SetInput(VtkAppendPolyData.GetOutput());
        VtkProbeFilter.SetSource(VtkPLOT3DReader.GetOutput());
        vtkWarpVector VtkWarpVector = new vtkWarpVector();
        VtkWarpVector.SetInput(VtkProbeFilter.GetPolyDataOutput());
        VtkWarpVector.SetScaleFactor(0.004000000189989805);
        vtkCastToConcrete VtkCastToConcrete = new vtkCastToConcrete();
        VtkCastToConcrete.SetInput(VtkWarpVector.GetOutput());
        vtkPolyDataNormals VtkPolyDataNormals = new vtkPolyDataNormals();
        VtkPolyDataNormals.SetInput(VtkCastToConcrete.GetPolyDataOutput());
        VtkPolyDataNormals.SetFeatureAngle(60.0);
        vtkDataSetMapper VtkDataSetMapper = new vtkDataSetMapper();
        VtkDataSetMapper.SetInput(VtkPolyDataNormals.GetOutput());
        VtkDataSetMapper.SetScalarRange(VtkPLOT3DReader.GetOutput().GetScalarRange());
        vtkActor VtkActor4 = new vtkActor();
        VtkActor4.SetMapper(VtkDataSetMapper);
        vtkStructuredGridOutlineFilter VtkStructuredGridOutlineFilter = new vtkStructuredGridOutlineFilter();
        VtkStructuredGridOutlineFilter.SetInput(VtkPLOT3DReader.GetOutput());
        vtkPolyDataMapper VtkPolyDataMapper4 = new vtkPolyDataMapper();
        VtkPolyDataMapper4.SetInput(VtkStructuredGridOutlineFilter.GetOutput());
        vtkActor VtkActor5 = new vtkActor();
        VtkActor5.SetMapper(VtkPolyDataMapper4);
        VtkActor5.GetProperty().SetColor(0.0, 0.0, 0.0);
        VtkPanel.getRenderer().AddActor(VtkActor4);
        VtkPanel.getRenderer().AddActor(VtkActor5);
        VtkPanel.getRenderer().AddActor(VtkActor1);
        VtkPanel.getRenderer().AddActor(VtkActor2);
        VtkPanel.getRenderer().AddActor(VtkActor3);
        ex94c.add("Center", VtkPanel);
        ex94c.pack();
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("File");
        menu.add(new MenuItem("Exit"));
        menuBar.add(menu);
        ex94c.setMenuBar(menuBar);
        ex94c.show();
    }
}

