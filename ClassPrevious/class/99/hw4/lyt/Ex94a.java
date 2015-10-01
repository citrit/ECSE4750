/* Decompiled by Mocha from Ex94a.class */
/* Originally compiled from Ex94a.java */

import java.awt.*;
import vtk.*;
import java.io.PrintStream;

public class Ex94a extends Frame
{
    static final int density = 15;
    static final float hedgescale = 0.0030F;

    public Ex94a()
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
        Ex94a ex94a = new Ex94a();
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
        vtkHedgeHog VtkHedgeHog = new vtkHedgeHog();
        VtkHedgeHog.SetInput(VtkProbeFilter.GetOutput());
        VtkHedgeHog.SetScaleFactor(0.003000000026077032);
        vtkPolyDataMapper VtkPolyDataMapper4 = new vtkPolyDataMapper();
        VtkPolyDataMapper4.SetInput(VtkHedgeHog.GetOutput());        
        VtkPolyDataMapper4.SetScalarRange(VtkPLOT3DReader.GetOutput().GetScalarRange());
        vtkActor VtkActor4 = new vtkActor();
        VtkActor4.SetMapper(VtkPolyDataMapper4);
        vtkStructuredGridOutlineFilter VtkStructuredGridOutlineFilter = new vtkStructuredGridOutlineFilter();
        VtkStructuredGridOutlineFilter.SetInput(VtkPLOT3DReader.GetOutput());
        vtkPolyDataMapper VtkPolyDataMapper5 = new vtkPolyDataMapper();
        VtkPolyDataMapper5.SetInput(VtkStructuredGridOutlineFilter.GetOutput());
        vtkActor VtkActor5 = new vtkActor();
        VtkActor5.SetMapper(VtkPolyDataMapper5);
        VtkActor5.GetProperty().SetColor(0.0, 0.0, 0.0);
        VtkPanel.getRenderer().AddActor(VtkActor4);
        VtkPanel.getRenderer().AddActor(VtkActor5);
        VtkPanel.getRenderer().AddActor(VtkActor1);
        VtkPanel.getRenderer().AddActor(VtkActor2);
        VtkPanel.getRenderer().AddActor(VtkActor3);
        ex94a.add("Center", VtkPanel);
        ex94a.pack();
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("File");
        menu.add(new MenuItem("Exit"));
        menuBar.add(menu);
        ex94a.setMenuBar(menuBar);
        ex94a.show();
    }
}

