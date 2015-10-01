//
//
// objectReader
//
//

import java.io.*;

public class objectReader
{
	PushbackReader	fin;
	char			buf[];
	int				pos;

	/** Constructor to create the pushbackreader and character buffer
	  * for all consequent reads */
	public objectReader(String fname)
	{
		try {
			fin = new PushbackReader(new FileReader(fname));
			buf = new char[8];
		}
		catch(FileNotFoundException fnfe)
		{
			System.out.println(fnfe);
		}

	}

	/** Simply reads in a single value, no real testing of anything */
	private void readVal()
	{
		int ret;
		char ch[] = new char[2];

		try {
			pos = 0;
			do {
				ret = fin.read(buf,pos, 1);
			} while ((! Character.isWhitespace(buf[pos++])) && ret != -1);
			do {
				ret = fin.read(ch,0, 1);
			} while (Character.isWhitespace(ch[0]) && ret != -1);
			fin.unread(ch, 0, 1);
		}

		catch(IOException ioe)
		{
			System.out.println(ioe);
		}
	}

	/** Conversion routines to convert our string to appropriate types */
	public int getInt()
	{
		readVal();
		return Integer.parseInt(new String(buf, 0, pos-1));
	}
	public double getDouble()
	{
		readVal();
		Double db = new Double(new String(buf, 0, pos-1));
		return db.doubleValue();
	}

}
