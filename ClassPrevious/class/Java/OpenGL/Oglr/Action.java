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
/////////////////////////////////////////////////////////////////////////

import java.util.Vector;

public abstract class Action extends ParentObject
{
	Vector Actors;
	
	public Action()
	{
		Actors = new Vector();
	}
	
	public void addActor(Actor act)
	{
		Actors.addElement(act);
	}
	
	/** Method to handle whenever the SceneClock ticks.
	 * This will be over-ridden by the derived class for a specific 
	 * action.
	 */
	abstract public void tick(int tickTime);
	
}
