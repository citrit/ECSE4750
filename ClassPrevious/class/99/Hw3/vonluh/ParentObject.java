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

//package oglr;

/**
  * ParentObject - Holds time marker for visualization pipeline
  *
  */
public class ParentObject 
{
	/** Holds the time of the object */
	long objectTime;


	/** Constructor  simply sets the time of creation, all derived
	  * classes automatically inherit this constructor
	  */
	public ParentObject()
	{
		objectTime = System.currentTimeMillis();
	}


	/** Methods to Get/Update time of pipeline objects, used when object
	  * is changed in some way.
	  */
	public long getTime() 
	{ 
		return objectTime; 
	}


	/** Methods to Get/Update time of pipeline objects, used when object
	  * is changed in some way.
	  */
	public void updateTime() 
	{ 
		objectTime = System.currentTimeMillis(); 
	}

	
	//
	//
	//
	public boolean doINeedUpdate(long objectTime)
	{
		
		System.out.println("PARENT OBJECT time:" + getTime());
		if (this.getTime() > objectTime ) 
		 	 return true; 
		return false; 
 
	}
	

	/** Methods to print out time of object
	  * 
	  */
	public void printTime() 
	{ 
		//System.out.println ("Print Time: " + objectTime);
	}

	

}

