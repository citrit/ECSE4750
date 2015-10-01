/* Copyright 1997 Free Software Foundation, Inc.
 *
 * JoglRenderer - a glut like display engine.
 *
 */

package jogl;

/**
   JoglRenderer is a thread that repeatedly calls display after calling
   init(), once.  To stop and start it use resume/suspend.   
*/
public abstract class JoglRenderer extends Thread
{
  /* this information tells us if our threads are time-slicing (win32)
     or not (green_threads).  This will undoubtedly not work forever */
  boolean callYield;
  
  /* Canvas I'm rendering into */
  protected JoglCanvas gl;
  
  /**
     The constructor doesn't do anything the user needs to know about
  */
  public JoglRenderer()
    {
      String os = System.getProperty( "os.name" );

      if( os.startsWith( "Wind" ) )
        callYield = false;
      else        
        callYield = true;
    }
  
  public void run()
    {
      gl.use();
      init();   

      while( true )
        {
          display();

          if( callYield )
            yield();
        }
    }     

  /** 
      Put all your drawing routines in this method 
  */
  public abstract void display();

  /** 
      Put in this method all your init code,  for instance,
      this is where you would set up lights, materials, textures etc.
  */
  public abstract void init();
}
