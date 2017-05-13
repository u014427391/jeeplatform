package org.muses.jeeplatform.common.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileUtils {
	
	public static BufferedWriter fileInit(File file){
		BufferedWriter bw = null;
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		try{
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdir();
			}
			if(!file.exists()){
				file.createNewFile();
			}
			fos = new FileOutputStream(file);
			osw = new OutputStreamWriter(fos);
			bw = new BufferedWriter(osw);
		}catch(IOException e){
			throw new RuntimeException("create file error...");
		}finally{
			try {
				fos.close();
				osw.close();
				bw.close();
				bw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bw;
	}

}
