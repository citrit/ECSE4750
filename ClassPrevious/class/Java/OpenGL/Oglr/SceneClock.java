//////////////////////////////////////////////////////////////////////////
//
//  This code is for instructional purposes only. It was generated for
//  use in a graduate level course to show certain aspects of data
//  storage algorithms. It has problems and should not be used
//  outside the class environment.
//
//  Author:  Thomas D. Citriniti     citrit@rpi.edu
//  Class:   Advanced Computer Graphics and Data visualization
//           Rensselaer Polytechnic Institute
//  Date:    January 1998
//
//////////////////////////////////////////////////////////////////////////

import java.util.Vector;

public class SceneClock extends ParentObject implements Runnable
{
	Vector Actions;
	Renderer aRen;
	int currTime;
	
	public SceneClock(Renderer aren) {
		Actions = new Vector();
		aRen = aren;
		currTime = 0;
	}
	
	public void addAction(Action action)
	{ 
		Actions.addElement(action); 
	}
	
	/** Thread thingy to run at start thread */
	public void run()
	{
		int i;
		try {
			while(true) {
				for (i=0;i<Actions.size();i++) {
					((Action)Actions.elementAt(i)).tick(currTime++);
				}
				aRen.render(true);
				Thread.sleep(10);
			}
		}
        catch (InterruptedException e){
            // the user sent an interupt,
            // So lets exit...
		}
	}

}
