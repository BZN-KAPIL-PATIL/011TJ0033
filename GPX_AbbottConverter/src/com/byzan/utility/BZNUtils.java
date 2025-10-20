package com.byzan.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.regex.Pattern;

public class BZNUtils 
{
	public static RandomAccessFile raFileT = null;
	public static FileChannel fileChannelT = null;
	public static FileLock fileLockT = null;
	public static boolean isFileLockedT = false;

	public static boolean islocked (String inputFile)
	{
		RandomAccessFile raFile = null;
		FileChannel fileChannel = null;
		FileLock fileLock = null;
		boolean isFileLocked = false;

		try 
		{
			if( inputFile.trim().length() > 0 )
			{
				raFile = new RandomAccessFile(inputFile, "rw");
				fileChannel = raFile.getChannel();

				fileLock = fileChannel.tryLock();

				if (fileLock == null) 
				{
					isFileLocked = true;
				}
				else 
				{
					//BaseLogger.debug("Lock can be acquired on File " + inputFile + " ... " );
				}
			}
		} 
		catch (OverlappingFileLockException e1)  
		{
			isFileLocked = true;
			//			BaseLogger.info("### File " + inputFile + " is Locked ... " );

		} 
		catch(java.io.FileNotFoundException e2)
		{
			isFileLocked = true;
			//BaseLogger.info("### File " + inputFile + " is Locked ... " );
		}
		catch (IOException ex) 
		{
			//BaseLogger.error("Error in accessing " + inputFile  + " -->  " + ex.getMessage());
		}
		finally 
		{
			try {
				if (fileLock != null)
				{
					fileLock.release();
					fileLock = null;
				}
				if (fileChannel != null)
				{
					fileChannel.close();
					fileChannel = null;
				}

				if (raFile != null)
				{
					raFile.close();
					raFile = null;
				}
			} 
			catch (Exception ex) 
			{
				ex.printStackTrace();
				//				throw new Exception("Error while checking file-lock.");
			}
		}
		return isFileLocked;
	} 

	public static boolean isNum(String str)
	{
		try
		{
			new Double(str);
			return true;
		}
		catch (Exception e) {}
		return false;
	}

	public static boolean moveFile(String source, String destination) throws IOException
	{
		File srce = new File(source);
		srce.setExecutable(true);
		srce.setReadable(true);
		srce.setWritable(true);
		
		File destination1 = new File(destination);
	    destination1.setExecutable(true);
		destination1.setReadable(true);
		destination1.setWritable(true);
		
		if( destination1.exists() )
			destination1.delete();
		
		if ( srce.renameTo(destination1) )
		{
		  srce.delete();
		  
		  return true;
		}			
		return false;
	}

	public static boolean isEmpty(String str)
	{
		return (str == null) || (str.isEmpty()) || (str.equalsIgnoreCase("")) || (str.trim().equalsIgnoreCase(""));
	}

	public static String lpad(String s, int n, char c)
	{
		s = s == null ? "" : s;
		String lps = s;
		for (int i = 0; i < n - s.length(); i++) {
			lps = c + lps;
		}
		return lps;
	}

	public static String rpad(String s, int n, char c)
	{
		String rps = s;
		for (int i = 0; i < n - s.length(); i++) {
			rps = rps + c;
		}
		return rps;
	}

	public static boolean deleteFile(String filePath)
	{
		try
		{
			File fl=null;
			fl = new File(filePath);
			if ((fl.exists())) 
			{
				fl.delete();
			}
			return !fl.exists();
		}
		catch (SecurityException  e)
		{
			e.printStackTrace();
			e.getMessage();
			return false;
		}
	}

	public static boolean isAlphanumeric (String str) 
	{
		for (int i = 0; i < str.length(); i++) 
		{
			char c = str.charAt(i);
			if (c < 0x30 || (c >= 0x3a && c <= 0x40) || (c > 0x5a && c <= 0x60) || c > 0x7a)
				return false;
		}
		return true;
	}

	public static String removeSplChar(String str)
	{
		/*Pattern pt = Pattern.compile("[^ a-zA-Z0-9]");
		while(pt.matcher(str).find())
		{
//			String s= pt.matcher(str).group();
			str=str.replaceAll("\\"+str, "");
		}*/
		str = str.replaceAll("[^a-zA-Z0-9\\s+]", "");
		return str;
	}
	
	public static String beneNameRemoveSplChar(String str)
	{
		/*Pattern pt = Pattern.compile("[^ a-zA-Z0-9]");
		while(pt.matcher(str).find())
		{
//			String s= pt.matcher(str).group();
			str=str.replaceAll("\\"+str, "");
		}*/
		str = str.replaceAll("[^a-zA-Z0-9&\\s+]", "");
		return str;
	}
	

	public static boolean SplCharCheck(String checkData){
		Pattern pattern = Pattern.compile("^[ A-Za-z0-9]*$");
		if(pattern.matcher(checkData).find())
		{
			return true;
		}
		return false;
	}

	public static boolean copyFile(String source, String destination)
			throws IOException
	{
		File src = new File(source);
		File dest = new File(destination);
		FileInputStream fis = null;
		FileOutputStream fos = null;
		Writer wr = new StringWriter();
		try
		{
			if ((src.exists()) && (src.canRead()) && ((!dest.exists()) || (dest.canWrite())))
			{
				fis = new FileInputStream(src);
				fos = new FileOutputStream(dest);

				byte[] b = new byte[1024];
				int read = 0;
				while ((read = fis.read(b)) != -1) {
					fos.write(b, 0, read);
				}
				return true;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace(new PrintWriter(wr));
			return false;
		}
		finally
		{
			if (fos != null) {
				fos.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
		return false;
	}

	public static boolean isAlpha (String str) {
		int c = 0;
		for (int i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			if (((c < 65) || (c > 122)) && (c != 32)) {
				return false;
			}
		}
		return true;
	}

}