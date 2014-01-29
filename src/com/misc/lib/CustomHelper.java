package com.misc.lib;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class CustomHelper {
	
	public boolean checkMemberSession(HttpSession sess){

		if((sess.getAttribute("username")==null) || (sess.getAttribute("username")=="") || (sess.getAttribute("username")=="null")){
			return false;
		}else{
			return true;
		}
		
	}
	
	public void loadSess(HttpSession sess){
		
		System.out.println("UserID "+sess.getAttribute("userid"));
		System.out.println("LoggedHash "+sess.getAttribute("loggedhash"));
		System.out.println("Username "+sess.getAttribute("username"));
		System.out.println("Logged "+sess.getAttribute("logged"));
	}
	
	public Map loadSession(HttpSession sess){
		 Map usrDetails=new HashMap();
		 usrDetails.put("userid",sess.getAttribute("userid"));
		 usrDetails.put("username",sess.getAttribute("username"));
		 usrDetails.put("loggedhash",sess.getAttribute("loggedhash"));
		 return usrDetails;
	}
	
	
	public String generateHash(String raw){
		
		 MessageDigest m = null;
		try {
			m = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 m.update(raw.getBytes(),0,raw.length());
		 return new BigInteger(1,m.digest()).toString(16);
	}
	
	
	public String loadDateNow(){
			
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Date date = new Date();
		 return dateFormat.format(date);
	}
	
	
	
	public String formatDate(String iDate){
		 DateFormat dateFormat = new SimpleDateFormat(iDate);
		 Date date = new Date();
		 return dateFormat.format(date);
	}
	
	
	public String loadDateSentenceNow(){
		
		 DateFormat dateFormat = new SimpleDateFormat("MMMMM dd, yyyy  HH:mm:ss");
		 Date date = new Date();
		 return dateFormat.format(date);
	}
	
	
	public String getAddDate(int yearAdd){
		
		Calendar date = Calendar.getInstance();  
	    date.setTime(new Date());  
	    Format f = new SimpleDateFormat("yyyy");  
	    //System.out.println(f.format(date.getTime()));  
	    date.add(Calendar.YEAR,yearAdd);  
	    return ""+f.format(date.getTime());  
	}
	
	
	public String[] loadMonths(){
		
		String strMonth[]=new String[13];
		strMonth[0]="";
		strMonth[1]="January";
		strMonth[2]="Febuary";
		strMonth[3]="March";
		strMonth[4]="April";
		strMonth[5]="May";
		strMonth[6]="June";
		strMonth[7]="July";
		strMonth[8]="August";
		strMonth[9]="September";
		strMonth[10]="October";
		strMonth[11]="November";
		strMonth[12]="December";
			
		return strMonth;
	}
	
	
public String[] loadInitMonths(){
		
		String strMonth[]=new String[13];
		strMonth[0]="";
		strMonth[1]="J";
		strMonth[2]="F";
		strMonth[3]="M";
		strMonth[4]="A";
		strMonth[5]="M";
		strMonth[6]="J";
		strMonth[7]="J";
		strMonth[8]="A";
		strMonth[9]="S";
		strMonth[10]="O";
		strMonth[11]="N";
		strMonth[12]="D";
			
		return strMonth;
}


public String[] loadShortMonths(){
	
	String strMonth[]=new String[13];
	strMonth[0]="";
	strMonth[1]="Jan";
	strMonth[2]="Feb";
	strMonth[3]="Mar";
	strMonth[4]="Apr";
	strMonth[5]="May";
	strMonth[6]="Jun";
	strMonth[7]="Jul";
	strMonth[8]="Aug";
	strMonth[9]="Sep";
	strMonth[10]="Oct";
	strMonth[11]="Nov";
	strMonth[12]="Dec";
		
	return strMonth;
}
	
	public String fullDate(String rawDate){
		String finalDate="";		
		  
      
		  if(rawDate.equals(null)){
			  finalDate="  ";
	        }else{
        	  	String str = rawDate;
		        String[] dateRaw = str.split("-"); 
	        	String[] monthName=this.loadMonths();
	          	finalDate=monthName[Integer.parseInt(dateRaw[1])]+" "+dateRaw[2]+", "+dateRaw[0];
          }
          
          return finalDate;
	}
	
	
	
	public String UpperCaseWords(String line)
	{
	    line = line.trim().toLowerCase();
	    String data[] = line.split("\\s");
	    
	    line = "";
	    for(int i =0;i< data.length;i++)
	    {
	        if(data[i].length()>1)
	            line = line + data[i].substring(0,1).toUpperCase()+data[i].substring(1)+" ";
	        else
	            line = line + data[i].toUpperCase();
	    }
	    return line.trim();
	}
	
	
	public String UpperCaseAllWords(String line)
	{
	    line =line.toUpperCase();
	    return line;
	}
	
	public String transposeDate(String rawDate){
		String finalDate="";	
		
		/*
		 *Null=fulldate with time
		 *1=full date no time
		 *2=short date with time
		 *3= short data no time 
		 *02/13/2013
		 * */
		
		String str = rawDate;
        String[] dateRaw = str.split("/"); 
        finalDate=dateRaw[2]+"-"+dateRaw[0]+"-"+dateRaw[1];
		  
          return finalDate;
	}

	
	public String aDate(String rawDate,String strShort){
		String finalDate="";	
		
		/*
		 *Null=fulldate with time
		 *1=full date no time
		 *2=short date with time
		 *3= short data no time 
		 * */
		
		  if(rawDate.equals(null)){
			  finalDate="  ";
	        }else{
        	  	String str = rawDate;
		        String[] dateRaw = str.split("-"); 
	        	String[] monthName=this.loadMonths();
	        	
	         if(strShort.equals("")){
	        	 String ext=dateRaw[2];
	        	 String[] a=ext.split(" ");
	        	finalDate=monthName[Integer.parseInt(dateRaw[1])]+" "+a[0]+", "+dateRaw[0]+" "+a[1];
	         }else if(strShort.equals("1")){
	        	 String ext=dateRaw[2];
	        	 String[] a=ext.split(" ");
	        	 finalDate=monthName[Integer.parseInt(dateRaw[1])]+" "+a[0]+", "+dateRaw[0];
	         }else if(strShort.equals("2")){
	        	
	        	 String[] shortMonthName=this.loadShortMonths();
	        	 String ext=dateRaw[2];
	        	 String[] a=ext.split(" ");
	        	 finalDate=shortMonthName[Integer.parseInt(dateRaw[1])]+" "+a[0]+", "+dateRaw[0]+" "+a[1];
	        
	         }else if(strShort.equals("3")){
	        	 
	        	 String[] shortMonthName=this.loadShortMonths();
	        	 String ext=dateRaw[2];
	        	 String[] a=ext.split(" ");
	        	 finalDate=shortMonthName[Integer.parseInt(dateRaw[1])]+" "+a[0]+", "+dateRaw[0];
	        	 
	         }
	      }
          
          return finalDate;
	}
	
	
	
	public String trimDate(String rawDate){
		String finalDate="";	
		
		String str = rawDate;
        String[] dateRaw = str.split(" "); 
        return dateRaw[0];
	}

	
	public Integer computePercentage(String target, String actual){
		double dPercentage=0;
		if(target==null){
			target="0.000";
		}
		//System.out.println(target + " " + actual);
		dPercentage = (Double.parseDouble(actual) / Double.parseDouble(target)) * 100;
		//dPercentage=(Integer.parseInt(target) / Integer.parseInt(actual)) * 100;
		
		
		return (int)dPercentage;
	}

}
