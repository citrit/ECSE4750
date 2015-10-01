/* Decompiled by Mocha from Ex63a.class */
/* Originally compiled from Ex63a.java */

import java.awt.*;
import vtk.*;
import java.io.PrintStream;

public class Ex63a extends Frame
{
    public Ex63a()
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
        float f1 = 0.4F;
        float f2 = 0.4F;
        Ex63a ex63a = new Ex63a();
        vtkPanel VtkPanel = new vtkPanel();
        VtkPanel.setSize(400, 400);
        System.out.println("Here we go");
        vtkQuadric VtkQuadric = new vtkQuadric();
        VtkQuadric.SetCoefficients(1.0, 2.0, 3.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        vtkSampleFunction VtkSampleFunction = new vtkSampleFunction();
        VtkSampleFunction.SetSampleDimensions(25, 25, 25);
        VtkSampleFunction.SetImplicitFunction(VtkQuadric);
        VtkSampleFunction.DebugOn();
        vtkContourFilter VtkContourFilter = new vtkContourFilter();
        VtkContourFilter.SetInput(VtkSampleFunction.GetOutput());
        VtkContourFilter.UseScalarTreeOn();
        VtkContourFilter.DebugOn();
        vtkPolyDataMapper VtkPolyDataMapper1 = new vtkPolyDataMapper();
        VtkPolyDataMapper1.SetInput(VtkContourFilter.GetOutput());
        VtkPolyDataMapper1.SetScalarRange(0.0, 7.0);
        vtkActor VtkActor1 = new vtkActor();
        VtkActor1.SetMapper(VtkPolyDataMapper1);
        VtkPanel.getRenderer().AddActor(VtkActor1);
        vtkOutlineFilter VtkOutlineFilter = new vtkOutlineFilter();
        VtkOutlineFilter.SetInput(VtkSampleFunction.GetOutput());
        vtkPolyDataMapper VtkPolyDataMapper2 = new vtkPolyDataMapper();
        VtkPolyDataMapper2.SetInput(VtkOutlineFilter.GetOutput());
        vtkActor VtkActor2 = new vtkActor();
        VtkActor2.SetMapper(VtkPolyDataMapper2);
        VtkActor2.GetProperty().SetColor(1.0, 1.0, 1.0);
        VtkPanel.getRenderer().AddActor(VtkActor2);
        ex63a.add("Center", VtkPanel);
        ex63a.pack();
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("File");
        menu.add(new MenuItem("Exit"));
        menuBar.add(menu);
        ex63a.setMenuBar(menuBar);
        ex63a.show();
        for (float f3 = 0.0F; f3 < 12.0; f3++)
        {
            VtkContourFilter.SetValue(1, (double)(f1 + f3 * f2));
            VtkPanel.Render();
        }
    }
}

