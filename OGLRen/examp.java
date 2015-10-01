//
//
// examp
//
// run this by using:
//
//		java examp example.dat

public class examp 
{

	static public void main(String args[])
	{
		objectReader or = new objectReader(args[0]);
		System.out.println(or.getInt());
		System.out.println(or.getInt());
		System.out.println(or.getDouble());
		System.out.println(or.getDouble());
		System.out.println(or.getDouble());
	}

}

