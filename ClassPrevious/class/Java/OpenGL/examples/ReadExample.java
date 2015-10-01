/*
 *	Simple example of the objectReader class
 *
 *
 *  
 *
 *	For demo purposes only, used in:
 *		Advanced Computer Graphics and Data Visualization  35-6961
 *		ECSE, Rensselaer Polytechnic Institute
 *
 *	January 2001
 */


class ReadExample 
{
	
	// This is where things get started
	public static void main( String args[] ) 
    {
    	int iMode, iCnt, i;
    	double r, g, b;
    	double x,y,z;
		objectReader objR = new objectReader("example.dat");
		
		do {
			iMode = objR.getInt();
			iCnt = objR.getInt();
			r = objR.getDouble();
			g = objR.getDouble();
			b = objR.getDouble();
			switch (iMode) {
				case 0:
					System.out.println("Coords["+r+","+g+","+b+"]: \n");
					for (i=0;i<iCnt;i++) {
						x = objR.getDouble();y = objR.getDouble();z = objR.getDouble();
						System.out.println(x+", "+y+", "+z+"\n");
					}
					break;
			}
		} while (!objR.eof());
    }
	
	
}