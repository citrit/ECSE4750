//Problem 6.3 b
//Skull

import vtk.*;
import java.awt.*;
import java.util.Vector;


class prob63b extends Frame
{

	public static void main( String args[] )
    {
         vtkPanel panel = new vtkPanel();
         prob63b f = new prob63b();

         vtkVolume16Reader v16 = new vtkVolume16Reader();
         v16.SetDataDimensions(128,128);
         v16.GetOutput().SetOrigin(0.0, 0.0, 0.0);
         v16.SetDataByteOrderToLittleEndian();
         v16.SetFilePrefix ("g:/vtkdata/headsq/half");
         v16.SetImageRange(20,40);
         v16.SetDataSpacing(1.6F, 1.6F, 1.5F);

         vtkMarchingCubes iso  = new vtkMarchingCubes();
         iso.SetInput(v16.GetOutput());
         iso.SetValue(0 ,1150);

         vtkPolyDataMapper isoMapper = new vtkPolyDataMapper();
         isoMapper.SetInput(iso.GetOutput());
         isoMapper.ScalarVisibilityOff();

         vtkActor isoActor = new vtkActor();
         isoActor.SetMapper(isoMapper);
         isoActor.GetProperty().SetColor(0.5F,0.5F,0.5F);

         vtkOutlineFilter outline = new vtkOutlineFilter();
         outline.SetInput(v16.GetOutput());

         vtkPolyDataMapper outlineMapper = new vtkPolyDataMapper();
         outlineMapper.SetInput(outline.GetOutput());

         vtkActor outlineActor = new vtkActor();
         outlineActor.SetMapper(outlineMapper);
         outlineActor.GetProperty().SetColor(0 ,0, 0);


         panel.getRenderer().AddActor(outlineActor);
         panel.getRenderer().AddActor(isoActor);
         panel.getRenderer().SetBackground(0.1F, 0.2F, 0.4F);

         vtkLight lgt = new vtkLight();
         panel.getRenderer().GetActiveCamera().Elevation(90);
         panel.getRenderer().GetActiveCamera().SetViewUp(0,0,-1);
         panel.getRenderer().GetActiveCamera().Zoom(1.3F);
         lgt.SetPosition(panel.getRenderer().GetActiveCamera().GetPosition());
         lgt.SetFocalPoint(panel.getRenderer().GetActiveCamera().GetFocalPoint());
         panel.getRenderer().AddLight(lgt);

         panel.resize(500,500);
         //ren1 SetBackground 0.1 0.2 0.4

          f.add("Center", panel);
    	  f.pack();
          f.show();
          /*for (int i = 6; i < 93; i++)
          {
               v16.SetImageRange(1,i);
             panel.Render();
          }*/
            //iso.SetValue(0 ,150);   panel.Render();
              //iso.SetValue(0 ,350);   panel.Render();
                //iso.SetValue(0 ,550);  panel.Render();
                  //iso.SetValue(0 ,750);  panel.Render();
                    ///iso.SetValue(0,950); panel.Render();
                   // iso.SetValue(0 ,1150);   panel.Render();
                     //iso.SetValue(0,1300); panel.Render();
                     //System.out.println("Done");

    }

}
