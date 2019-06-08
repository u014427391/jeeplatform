package org.muses.jeeplatform.util;



import org.muses.jeeplatform.core.CommonConsts;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DateUtils {
	
	public static String formatDate(Date date) throws ParseException{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}
	
	/**
	 * 日期对象转字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date,String format){
		String result="";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		if(date!=null){
			result=sdf.format(date);
		}
		return result;
	}
	
	 /**
	   * 解析日期，注:此处为严格模式解析，即20151809这样的日期会解析错误
	   * 
	   * @param pattern
	   * @param date
	   * @return
	   */
	  public static Date parse(String pattern, String date){
	    return parse(pattern, date, CommonConsts.LOCALE_CHINA);
	  }

	  /**
	   * 解析日期，注:此处为严格模式解析，即20151809这样的日期会解析错误
	   * 
	   * @param pattern
	   * @param date
	   * @param locale
	   * @return
	   */
	  public static Date parse(String pattern, String date, Locale locale){
	    SimpleDateFormat format = new SimpleDateFormat(pattern, locale);
	    format.setLenient(false);
	    Date result = null;
	    try{
	      result = format.parse(date);
	    }catch(Exception e){
	      e.printStackTrace();
	    }

	    return result;
	  }

}
