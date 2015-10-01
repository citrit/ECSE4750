import vtk.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class vtkMotBlur extends Frame implements KeyListener
{
	vtkPanel renPanel;
	vtkActor spikeActor2;
	vtkActor sphereActor2;
	
	public vtkMotBlur(String title)
	{ 
		super(title);
	}

	public static void main (String[] args)
	{
	  //this constructor must go all the way to 
	  // addNotify must be called on a Frame before you attempt
	  // to add a vtkPanel to it, otherwise the peer information
	  // doesn't get constructed, causing nasty seg-faults in GetWindowID
	  vtkMotBlur cone = new vtkMotBlur("vtkMotBlur");
	  cone.addNotify();
	
	  String jvmVendor = java.lang.System.getProperty("java.vendor");
	  if (jvmVendor.startsWith("Microsoft",0)) {
		cone.renPanel = new vtkMSPanel();
	  }
	  else {
		cone.renPanel = new vtkPanel();
	  }
	  cone.renPanel.setSize(400,400);
	  cone.renPanel.addKeyListener(cone);
	  cone.removeAll();
	  cone.add(cone.renPanel);
	  vtkRenderer ren1 = cone.renPanel.getRenderer();

	  cone.createScene(ren1);

	  ren1.SetBackground(0.5,0.5,0.5); // Background color grey
	  
	  cone.pack();
	  cone.show();
	}
	
	void createScene(vtkRenderer aren) 
	{
		// create the pipline, ball and spikes
		vtkSphereSource sphere = new vtkSphereSource();
		  sphere.SetThetaResolution(7); sphere.SetPhiResolution(7);
		vtkPolyDataMapper sphereMapper = new vtkPolyDataMapper();
		  sphereMapper.SetInput(sphere.GetOutput());
		vtkActor sphereActor = new vtkActor();
		  sphereActor.SetMapper(sphereMapper);
		sphereActor2 = new vtkActor();
		  sphereActor2.SetMapper(sphereMapper);
		vtkConeSource cone = new vtkConeSource();
		  cone.SetResolution(5);

		vtkGlyph3D glyph = new vtkGlyph3D();
		  glyph.SetInput(sphere.GetOutput());
		  glyph.SetSource(cone.GetOutput());
		  glyph.SetVectorModeToUseNormal();
		  glyph.SetScaleModeToScaleByVector();
		  glyph.SetScaleFactor(0.25);
		vtkPolyDataMapper spikeMapper = new vtkPolyDataMapper();
		  spikeMapper.SetInput(glyph.GetOutput());
		vtkActor spikeActor = new vtkActor();
		  spikeActor.SetMapper(spikeMapper);
		spikeActor2 = new vtkActor();
		  spikeActor2.SetMapper(spikeMapper);

		spikeActor.SetPosition(0,0.7,0);
		sphereActor.SetPosition(0,0.7,0);
		spikeActor2.SetPosition(0,-0.7,0);
		sphereActor2.SetPosition(0,-0.7,0);

		aren.AddActor(sphereActor);
		aren.AddActor(spikeActor);
		aren.AddActor(sphereActor2);
		aren.AddActor(spikeActor2);
	}
	
	// Handle the keystrokes and modify appropriate object
	public void keyTyped(KeyEvent e) 
	{
		System.out.println("keyTyped: "+e.getKeyChar());
		switch (e.getKeyChar()) {
		case '0':
			break;
		case 'B':
		case 'b':
			renPanel.getRenderWindow().SetSubFrames(21);

			for (double  i = 0; i <= 1.0; i = i + 0.05)
			{
			  spikeActor2.RotateY(2);
			  sphereActor2.RotateY(2);
			  renPanel.Render();
			}

			this.renPanel.Render();
			break;
		}
		renPanel.Render();
	}
	public void keyPressed(KeyEvent e) {

	}
	public void keyReleased(KeyEvent e) {

	}

}

