import vtk.*;
import java.awt.*;

public class prob94d extends Frame
{

	public static void main( String args[] )
    {
       vtkPanel panel = new vtkPanel();
       panel.resize(500,500);
       prob94d f = new prob94d();

       vtkPLOT3DReader pl3d = new vtkPLOT3DReader();
       pl3d.SetXYZFileName("g:/vtkdata/combxyz.bin");
       pl3d.SetQFileName ("g:/vtkdata/combq.bin");
       pl3d.SetScalarFunctionNumber (100);
       pl3d.SetVectorFunctionNumber (202);
       pl3d.Update();



       vtkPlaneSource plane = new vtkPlaneSource();
       plane.SetResolution(50,50);

       vtkTransform transP1 = new vtkTransform();
       transP1.Translate(3.7F, 0.0F, 28.37F);
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
        tpd1Actor.GetProperty().SetColor( 0F, 0F, 0F);

        vtkTransform transP2  = new vtkTransform();
        transP2.Translate( 9.2F, 0.0F, 31.20F);
        transP2.Scale(5, 5, 5);
        transP2.RotateY (90);

        vtkTransformPolyDataFilter tpd2 = new vtkTransformPolyDataFilter();
        tpd2.SetInput(plane.GetOutput());
        tpd2.SetTransform(transP2);

        vtkOutlineFilter outTpd2 = new vtkOutlineFilter();
        outTpd2.SetInput(tpd2.GetOutput());

        vtkPolyDataMapper mapTpd2 = new vtkPolyDataMapper();
        mapTpd2.SetInput(outTpd2.GetOutput());

        vtkActor tpd2Actor = new vtkActor();
        tpd2Actor.SetMapper( mapTpd2);
        tpd2Actor.GetProperty().SetColor(0F, 0F, 0F);

        vtkTransform transP3 = new vtkTransform();
        transP3.Translate(13.27F, 0.0F, 33.30F);
        transP3.Scale( 5, 5, 5);
        transP3.RotateY (90);

        vtkTransformPolyDataFilter tpd3 = new vtkTransformPolyDataFilter();
        tpd3.SetInput(plane.GetOutput());
        tpd3.SetTransform(transP3);

        vtkOutlineFilter outTpd3 = new vtkOutlineFilter();
        outTpd3.SetInput(tpd3.GetOutput());

        vtkPolyDataMapper mapTpd3 = new vtkPolyDataMapper();
        mapTpd3.SetInput(outTpd3.GetOutput());

        vtkActor tpd3Actor  = new vtkActor();
        tpd3Actor.SetMapper(mapTpd3);
        tpd3Actor.GetProperty().SetColor(0F, 0F, 0F);

        vtkAppendPolyData appendF = new vtkAppendPolyData();
        appendF.AddInput(tpd1.GetOutput());
        appendF.AddInput(tpd2.GetOutput());
        appendF.AddInput(tpd3.GetOutput());

        vtkProbeFilter probe = new vtkProbeFilter();
        probe.SetInput(appendF.GetOutput());
        probe.SetSource(pl3d.GetOutput());



        System.out.println("here");
        vtkVectorNorm sonic = new vtkVectorNorm();


         //sonic.SetSource(pl3d.GetOutput());
         sonic.SetInput(probe.GetOutput());
         sonic.SetNormalize(10);


        //vtkTubeFilter tubes = new vtkTubeFilter();
        //tubes.SetInput(sonic.GetOutput());
        //tubes.SetRadius(0.3F);
        //tubes.SetNumberOfSides(6);
        //tubes.SetVaryRadiusToVaryRadiusOff();

        //vtkPolyDataMapper sonicMapper = new vtkPolyDataMapper();
        //sonicMapper.SetInput(sonic.GetPolyDataOutput());
        //sonicMapper.SetScalarRange(pl3d.GetOutput().GetScalarRange());

        //vtkActor sonicActor = new vtkActor();
        //sonicActor.SetMapper(sonicMapper);

        vtkContourFilter contour = new vtkContourFilter();
        contour.SetInput(sonic.GetOutput());
        double ff[] = new double[2];
        ff =pl3d.GetOutput().GetScalarRange();
        contour.GenerateValues(50,ff[0],ff[1]);

        vtkPolyDataMapper contourMapper = new vtkPolyDataMapper();
        contourMapper.SetInput(contour.GetOutput());
        contourMapper.SetScalarRange(pl3d.GetOutput().GetScalarRange());

        vtkActor planeActor = new vtkActor();
        planeActor.SetMapper(contourMapper);

        vtkStructuredGridOutlineFilter outline = new vtkStructuredGridOutlineFilter();
        outline.SetInput(pl3d.GetOutput());

        vtkPolyDataMapper outlineMapper = new vtkPolyDataMapper();
        outlineMapper.SetInput(outline.GetOutput());

        vtkActor outlineActor = new vtkActor();
        outlineActor.SetMapper(outlineMapper);
        outlineActor.GetProperty().SetColor( 0F, 0F, 0F);

        panel.getRenderer().AddActor(outlineActor);
        panel.getRenderer().AddActor(planeActor);
        panel.getRenderer().AddActor(tpd1Actor);
        panel.getRenderer().AddActor(tpd2Actor);
        panel.getRenderer().AddActor (tpd3Actor);
        panel.getRenderer().SetBackground (1F, 1F, 1F);



        panel.getRenderer().GetActiveCamera().SetClippingRange (3.95297F, 50);
        panel.getRenderer().GetActiveCamera().SetFocalPoint( 8.88908F, 0.595038F, 29.3342F);
        panel.getRenderer().GetActiveCamera().SetPosition( -12.3332F, 31.7479F, 41.2387F);
        panel.getRenderer().GetActiveCamera().ComputeViewPlaneNormal();
        panel.getRenderer().GetActiveCamera().SetViewUp (0.060772F, -0.319905F, 0.945498F);

         f.add("Center", panel);
	     f.pack();
         f.show();



    }
}