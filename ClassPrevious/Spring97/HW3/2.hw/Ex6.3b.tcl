# If you are using Window95 and vtk 1.3 uncomment the line below
catch { load vtktcl; }

# get the interactor ui
source vtkInt.tcl

# Set my defaults
set max 1100
set min 500
set delta 50
set current $max
set vtk_datafile "MRIdata.vtk"

proc main {} {
    make_vtkpipeline
    make_gui
    # fall off into the event loop
}

proc make_vtkpipeline {} {
    global renWin vtk_datafile max;

    # First create the render master
    vtkRenderMaster rm;
    
    # Now create the RenderWindow, Renderer
    #
    set renWin [rm MakeRenderWindow];
    set ren1   [$renWin MakeRenderer];
    set iren   [$renWin MakeRenderWindowInteractor];
    
    #
    # Load up the data
    vtkStructuredPointsReader vol;
    vol SetFilename $vtk_datafile
    
    vtkMarchingCubes isosurface;
    isosurface SetInput [vol GetOutput];
    isosurface SetValue 0 $max;
    
    vtkPolyMapper volMapper;
    volMapper SetInput [isosurface GetOutput];
    volMapper ScalarsVisibleOff;
    
    vtkActor volActor;
    volActor SetMapper volMapper;
    
    vtkOutlineFilter outLine;
    outLine SetInput [vol GetOutput];
    
    vtkPolyMapper outMapper;
    outMapper SetInput [outLine GetOutput];

    vtkActor outActor;
    outActor SetMapper outMapper;
    
    # Add the actors to the renderer, set the background and size
    #
    $ren1 AddActors outActor;
    $ren1 AddActors volActor;
    $ren1 SetBackground 0 0 0;
    $renWin SetSize 500 400;
    $iren Initialize;

    $iren SetUserMethod {wm deiconify .vtkInteract};
    $renWin Render;
}


source 6.3main.tcl

main;

