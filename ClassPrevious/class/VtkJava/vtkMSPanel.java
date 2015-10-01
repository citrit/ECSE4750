package vtk;

import vtk.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Math;
import sun.awt.*;
import java.beans.*;
import com.ms.awt.GraphicsX;

public class vtkMSPanel extends vtkPanel implements MouseListener, MouseMotionListener, KeyListener
{
  protected vtkRenderWindow rw = new vtkRenderWindow();     
  protected vtkRenderer ren = new vtkRenderer();
  protected vtkCamera cam = null;
  protected vtkLight lgt = new vtkLight();
  protected int lastX;
  protected int lastY;
  int windowset = 0;
  int LightFollowCamera = 1;
  int InteractionMode = 1;
  boolean rendering = false;
  
  static { System.loadLibrary("vtkJava"); }


public vtkMSPanel()
  {
    rw.AddRenderer(ren);
    addMouseListener(this);
    addMouseMotionListener(this);
    addKeyListener(this);
    super.setSize(200,200);
    rw.SetSize(200,200);
  }
/**
 * @dll.import("USER32",auto)
 */
private static native int WindowFromDC(int hdc);

public int getWindowID() 
  {
	int hWnd;
// MS Support for MS JavaVM
	hWnd = WindowFromDC(((GraphicsX)this.getGraphics()).gdc.pGetDC());
    return hWnd;
  }
 
}