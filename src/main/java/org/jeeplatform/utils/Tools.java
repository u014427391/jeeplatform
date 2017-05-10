package org.jeeplatform.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description 项目的工具类
 * @author Nicky
 * @date 2017年3月13日
 */
public class Tools {

	/**
	 * 检测字符串是否不为空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		return str != null && !"".equals(str) ;
	}
	
	/**
	 * 检测字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		return str == null || "".equals(str) ;
	}
	
	
}
