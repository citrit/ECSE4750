/* Decompiled by Mocha from Ex63b.class */
/* Originally compiled from Ex63b.java */

import java.awt.*;
import vtk.*;

public class Ex63b extends Frame
{
    static final float anim_init = 750.0F;
    static final float anim_growth = 200.0F;

    public Ex63b()
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
        Ex63b ex63b = new Ex63b();
        vtkPanel VtkPanel = new vtkPanel();
        VtkPanel.setSize(200, 200);
        System.out.println("Here we go");
        vtkVolume16Reader VtkVolume16Reader = new vtkVolume16Reader();
        VtkVolume16Reader.SetDataDimensions(128, 128);
        VtkVolume16Reader.GetOutput().SetOrigin(0.0, 0.0, 0.0);
        VtkVolume16Reader.SetDataByteOrderToLittleEndian();
        VtkVolume16Reader.SetFilePrefix("g:/vtkdata/headsq/half");
        VtkVolume16Reader.SetImageRange(1, 93);
        VtkVolume16Reader.SetDataSpacing(1.6, 1.6, 1.5);
        vtkMarchingCubes VtkMarchingCubes = new vtkMarchingCubes();
        VtkMarchingCubes.SetInput(VtkVolume16Reader.GetOutput());
        vtkDecimatePro VtkDecimatePro = new vtkDecimatePro();
        VtkDecimatePro.SetInput(VtkMarchingCubes.GetOutput());
        VtkDecimatePro.SetTargetReduction(0.75);
        vtkPolyDataMapper VtkPolyDataMapper1 = new vtkPolyDataMapper();
        VtkPolyDataMapper1.SetInput(VtkDecimatePro.GetOutput());
        VtkPolyDataMapper1.ScalarVisibilityOff();
        vtkActor VtkActor1 = new vtkActor();
        VtkActor1.SetMapper(VtkPolyDataMapper1);
        vtkOutlineFilter VtkOutlineFilter = new vtkOutlineFilter();
        VtkOutlineFilter.SetInput(VtkVolume16Reader.GetOutput());
        vtkPolyDataMapper VtkPolyDataMapper2 = new vtkPolyDataMapper();
        VtkPolyDataMapper2.SetInput(VtkOutlineFilter.GetOutput());
        vtkActor VtkActor2 = new vtkActor();
        VtkActor2.SetMapper(VtkPolyDataMapper2);
        VtkPanel.getRenderer().AddActor(VtkActor2);
        VtkPanel.getRenderer().AddActor(VtkActor1);
        ex63b.add("Center", VtkPanel);
        ex63b.pack();
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("File");
        menu.add(new MenuItem("Exit"));
        menuBar.add(menu);
        ex63b.setMenuBar(menuBar);
        ex63b.show();
        for (int i = 0; i < 3; i++)
        {
            VtkMarchingCubes.SetValue(0, (double)(650.0 + (float)i * 100.0));
            VtkPanel.Render();
        }
    }
}

