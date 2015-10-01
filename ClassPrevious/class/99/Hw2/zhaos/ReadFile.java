import java.io.*;

public class ReadFile
{
	String line;
	String filename;
	DataInputStream dataInput;
	boolean end;
	static boolean end_of_file;

	public ReadFile(String name) throws Exception
	{
		filename = name;
		end = false;
		set(false);

		try
		{
			dataInput = new DataInputStream( new FileInputStream(filename));
		}
		catch (Exception e)
		{
			System.out.println("Exception: " + e.toString());
		}
	}
	
	public static void set(boolean a)
	{
		end_of_file = a;
	}

	public void readLine()		
	{	
		Integer y;
		int type;
		int num;
		double r, g, b;
		try
		{
			line = dataInput.readLine();
		}
		catch (EOFException eof)
		{ 
			set(true);
		}
		catch (IOException e)
		{
			System.out.println("Exception: " + e.toString());
		}
		
	}

	public int readInt()
	{
		int i;
		String sub;
		int num;

		i = line.indexOf((int) ' ', 0);
		if(i == -1)
			i = line.length();

		sub = line.substring(0,i);
		num = (new Integer(sub)).intValue();
		for(; i<line.length(); i++)
		{
			if(line.charAt(i)!=' ')
			{
				line = line.substring(i);
				break;
			}
		}
		return num;
	}

	public float readFloat()
	{
		int i;
		String sub;
		float num;

		i = line.indexOf((int) ' ', 0);
		if(i == -1)
			i=line.length();

		sub = line.substring(0, i);
		num = (new Float(sub)).floatValue();
		for(; i<line.length(); i++)
		{
			if(line.charAt(i)!=' ')
			{
				line = line.substring(i);
				break;
			}
		}
		return num;
	}
}
	