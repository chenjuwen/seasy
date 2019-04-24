package com.seasy.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Calendar;

public class FileUtil {
	/**
	 * 输入流输出到文件
	 */
	public static boolean writeFile(InputStream in, String destFile){
	    BufferedInputStream inStream = null;
	    BufferedOutputStream outStream = null;
	    try {
	    	inStream = new BufferedInputStream(in);
	    	outStream = new BufferedOutputStream(new FileOutputStream(destFile));
	    	
	    	byte[] buf = new byte[4096];
	    	int i;
	    	while ((i = inStream.read(buf)) != -1) {
	    		outStream.write(buf, 0, i);
	    	}

			outStream.flush();
	    	outStream.close();
	    	outStream = null;

			inStream.close();
			inStream = null;
	    	
	    	return true;
	    } catch (IOException ex) {
	    	ex.printStackTrace();
	    } finally {
	    	if (inStream != null) {
	    		try {
	    			inStream.close();
	    		} catch (IOException ex) {
	    			
	    		}
	    	}
	      
	      	if (outStream != null) {
	      		try {
	      			outStream.close();
	      		} catch (IOException ex) {
	      			
	      		}
	      	}
	    }
	    return false;
	}

	/**
	 * 字符串内容输出到文件
	 */
	public static boolean writeFile(String data, String filePath){
		OutputStreamWriter writer = null;
		try{
			writer = new OutputStreamWriter(new FileOutputStream(new File(filePath)), Charset.forName("utf-8"));
			writer.write(data);
			writer.flush();
			writer.close();
			writer = null;
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(writer!=null) {
				try {
					writer.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * 字节数组输出到文件
	 */
	public static boolean writeFile(byte[] byteData, String filePath){
		FileOutputStream out = null;
		try{
			out = new FileOutputStream(new File(filePath));
			out.write(byteData);
			out.flush();
			out.close();
			out = null;
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(out!=null) {
				try {
					out.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * 读取文本型文件内容
	 * @param filePath 文件路径
	 */
	public static String readTextFile(String filePath){
		StringBuffer sb = new StringBuffer();
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath))));
			String line = null;
			while((line=reader.readLine())!=null){
				sb.append(line + "\n");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
	
	public static byte[] readFile(String filePath){
		BufferedInputStream inStream = null;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] result = null;
	    try {
	    	inStream = new BufferedInputStream(new FileInputStream(new File(filePath)));
	    	
	    	byte[] buf = new byte[4096];
	    	int i;
	    	while ((i = inStream.read(buf)) != -1) {
	    		outStream.write(buf, 0, i);
	    	}
	    	
	    	result = outStream.toByteArray();

			inStream.close();
			inStream = null;
			
			outStream.close();
			outStream = null;
			
	    } catch (IOException ex) {
	    	ex.printStackTrace();
	    } finally {
	    	if (inStream != null) {
	    		try {
	    			inStream.close();
	    		} catch (IOException ex) {
	    			
	    		}
	    	}
	      
	      	if (outStream != null) {
	      		try {
	      			outStream.close();
	      		} catch (IOException ex) {
	      			
	      		}
	      	}
	    }
	    
	    return result;
	}

	/**
	 * 删除N天前的文件
	 */
	public static void deleteFiles(String absolutePath, int days){
		try{
			File fileDir = new File(absolutePath);
			if(fileDir.isDirectory()){
				File[] files = fileDir.listFiles();
				if(files != null){
					for(int i=0; i<files.length; i++){
						File file = (File)files[i];
						if((Calendar.getInstance().getTimeInMillis() - file.lastModified())/1000/60/60/24 > days){
							file.delete();
						}
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * 创建目录及其子目录
	 */
	public static void createPath(String filePath){
		File file = new File(filePath);
		if(!file.exists()){
			file.mkdirs();
		}
	}
	
}
