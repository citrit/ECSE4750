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
	StringBuffer		m_Str;
	int			pos;
	boolean			eof;
        char ch[] = new char[2];

	/** Constructor to create the pushbackreader and character buffer
	  * for all consequent reads */
	public objectReader(String fname)
	{
		try {
			fin = new PushbackReader(new FileReader(fname));
			buf = new char[128];
			m_Str = new StringBuffer();
		}
		catch(FileNotFoundException fnfe)
		{
			System.out.println(fnfe);
			eof = true;
		}
		eof = false;
	}

	/** Simply reads in a single value, no real testing of anything */
	private void readVal()
	{
		int ret;


		try {
			pos = 0;
			do {
				ret = fin.read(ch, 0, 1);
			} while (Character.isWhitespace(ch[0]) && ret != -1);
			while ((!Character.isWhitespace(ch[0])) && ret != -1) {
			        buf[pos] = ch[0];
				ret = fin.read(ch, 0, 1);
				pos++;
			};
			if (ret == -1) 
				eof = true;
		}
		catch(IOException ioe)
		{
			System.out.println(ioe);
		}
		m_Str.insert(0, buf);
		m_Str.setLength(pos);
	}

	/** simply check to see if anything left to read */
	public boolean eof() { return eof; }

	/** Conversion routines to convert our string to appropriate types */
	public String getString()
	{
		readVal();
		return m_Str.toString();
	}
	public int getInt()
	{
		readVal();
		try {
		    return Integer.parseInt(m_Str.toString());
		} catch (NumberFormatException e){
		    return 0;
		}
	}
	public double getDouble()
	{
		readVal();
		try {
		    return Double.valueOf(m_Str.toString()).doubleValue();
		} catch (NumberFormatException e){
		    return 0;
		}
	}
	public float getFloat()
	{
		readVal();
		try {
		    return (float)Float.valueOf(m_Str.toString()).doubleValue();
		} catch (NumberFormatException e){
		    return 0;
		}
	}

}

