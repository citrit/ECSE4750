catch { load vtktcl; }
# user interface command widget
source vtkInt.tcl

# Set my defaults
set max 1.2
set min 0.0
set delta .1
set current $max

proc main {} {
    make_vtkpipeline
    make_gui
    # fall off into the event loop
}

proc make_vtkpipeline {} {
    global renWin vtk_datafile max;
    # create a rendering window and renderer
    vtkRenderMaster rm;
    set renWin [rm MakeRenderWindow];
    set ren1 [$renWin MakeRenderer];
    set iren [$renWin MakeRenderWindowInteractor];
    
    vtkQuadric quad;
    quad SetCoefficients 0.5 1 .2 0 .1 0 0 .2 0 0;
    vtkSampleFunction sample;
    sample SetSampleDimensions 50 50 50;
    sample SetImplicitFunction quad;
    vtkContourFilter isosurface;
    isosurface SetInput [sample GetOutput];
    isosurface SetNumberOfContours 1;
    isosurface SetValue 0 $max;
    vtkPolyMapper contMapper;
    contMapper SetInput [isosurface GetOutput];
    contMapper SetScalarRange 0.0 1.2;
    vtkActor contActor;
    contActor SetMapper contMapper;
    
    # assign our actor to the renderer
    $ren1 SetBackground 1 1 1;
    $ren1 AddActors contActor;
    
    # enable user interface interactor
    $iren SetUserMethod {wm deiconify .vtkInteract};
    $iren Initialize;
}

source 6.3main.tcl

main;

