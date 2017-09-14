package com.bxt.sptask.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTimeUtil {
  final private String Date_Format_full = "'YYYY-MM-DD HH24:MI:SS'";
  final private String Date_Format_short = "'YYYY-MM-DD'";
  private SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
  private SimpleDateFormat formatter_full = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private SimpleDateFormat formatter_compact = new SimpleDateFormat("yyyyMMdd");
  
  
  public String toDateStr(Date md) {
		String DateStr = "";
		try {
			DateStr = formatter.format(md);
		}catch(Exception ex) {
			System.out.println("misc:toDateStr:" + ex.getMessage());
		}
		
		return DateStr;
	}
  
   public String toTimeMillisStr(long millis){
	   String dateStr = "";
	   SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMddHHmmss");//将毫秒级long值转换成日期格式
	   SimpleDateFormat dateformatMillis = new SimpleDateFormat("yyyyMMddHHmmssSSS");//将毫秒级long值转换成日期格式
	   GregorianCalendar gc = new GregorianCalendar(); 
	   gc.setTimeInMillis(millis);
	   dateStr = dateformatMillis.format(gc.getTime());
	   //dateStr = dateformat.format(gc.getTime());
	   return dateStr;
   }
   
   
   public static void main(String[] args) {
	   DateTimeUtil dtu = new DateTimeUtil();
	   String datestr = dtu.toTimeMillisStr(System.currentTimeMillis());
	   System.out.println(datestr);
   }
   
}
