import vtk.*;
import java.awt.*;
import java.util.Vector;


class prob63 extends Frame
{

	public static void main( String args[] )
    {
       vtkPanel panel = new vtkPanel();
       panel.resize(400,400);
       prob63 f = new prob63();

       vtkQuadric quadric = new vtkQuadric();
       quadric.SetCoefficients(.5F ,1F ,.2F ,0F ,.1F, 0F, 0F ,.2F,0F,0F);

       vtkSampleFunction sample = new vtkSampleFunction();
       sample.SetSampleDimensions(30, 30, 30);
       sample.SetImplicitFunction(quadric);
       sample.Update();
       sample.Print();

       // Create five surfaces F(x,y,z) = constant between range specified
       vtkThresholdPoints threshold = new vtkThresholdPoints();
       threshold.SetInput(sample.GetOutput());
       threshold.ThresholdByUpper(200);

       vtkContourFilter contours = new vtkContourFilter();
       contours.SetInput(sample.GetOutput());
       contours.GenerateValues(5, 0.0F, 1.2F);

       vtkPolyDataMapper contMapper = new vtkPolyDataMapper();
       contMapper.SetInput(contours.GetOutput());
       contMapper.SetScalarRange(0.0F, 1.2F);
       //vtkMaskPoints mask = new vtkMaskPoints();
       //mask.SetInput(threshold.GetOutput());
       //mask.SetOnRatio(10);

       /*vtkLineSource line = new vtkLineSource();
       line.SetResolution(1);
       vtkGlyph3D lines = new vtkGlyph3D();
       lines.SetInput(threshold.GetOutput());
       lines.SetSource(contours.GetOutput());
       lines.SetScaleFactor(0.005);
       lines.SetScaleModeToScaleByScalar();
       lines.Update();

       vtkPolyDataMapper vectorMapper = new vtkPolyDataMapper();
       vectorMapper.SetInput(lines.GetOutput());
       vectorMapper.SetScalarRange(lines.GetOutput().GetScalarRange());
       vectorMapper.ImmediateModeRenderingOn();

       vtkActor vectorActor = new vtkActor();
       vectorActor.SetMapper(vectorMapper);
       vectorActor.GetProperty().SetOpacity (0.99F);*/




       vtkActor contActor = new vtkActor();
       contActor.SetMapper(contMapper);

       // Create outline
       vtkOutlineFilter outline = new vtkOutlineFilter();
       outline.SetInput(sample.GetOutput());

      vtkPolyDataMapper outlineMapper = new vtkPolyDataMapper();
      outlineMapper.SetInput(outline.GetOutput());

      vtkActor outlineActor = new vtkActor();
      outlineActor.SetMapper(outlineMapper);
      outlineActor.GetProperty().SetColor(0.0F, 0.0F, 0.0F);

      panel.getRenderer().SetBackground( 1F, 1F, 1F);
       //panel.getRenderer().AddActor(vectorActor);
       panel.getRenderer().AddActor(contActor);
       panel.getRenderer().AddActor(outlineActor);
       //panel.getRenderer().GetActiveCamera().Zoom(1.5F);
       f.add("Center", panel);
	   f.pack();
       f.show();

       //for (int i = 0; i < 1; i++)
      // {
            contours.GenerateValues(5, 0.0F, 0.2F);

            panel.Render();
            contours.GenerateValues(5, 0.0F, 0.4F);

            panel.Render();
             contours.GenerateValues(5, 0.0F, 0.6F);

            panel.Render();
       System.out.println("done");
        contours.GenerateValues(5, 0.0F, 0.6F);

            panel.Render();  //panel.Render();
             contours.GenerateValues(5, 0.0F, 0.8F);

            panel.Render();
             contours.GenerateValues(5, 0.0F, 1.0F);

            panel.Render();
             contours.GenerateValues(5, 0.0F, 1.2F);

            panel.Render();
       f.pack();
       f.show();
    }

}