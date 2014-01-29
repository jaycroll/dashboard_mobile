package com.misc.lib;


import java.util.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;



public class RolePermission{
	
	//For Database Connection
	 private String connectionURL = "";
	 private String dbUser="";
	 private String dbPassword="";
	 public String projectFile="";
	 public String salesDB="";
	 public String dashboardDB="";
	 public String settingUrl="";
	 
	 public void fetchProperties() throws IOException{
		 
		 String relativeWebPath = this.settingUrl+"WEB-INF/app.properties";
		
		 
		//Load  this.settingUrl using request.getRealPath("/") on servlet
		 
		 Properties p = new Properties();
		 p.load(new FileInputStream(projectFile+relativeWebPath));
		 
		 this.connectionURL=p.getProperty("connectionURL");
		 this.dbUser=p.getProperty("dbUser");
		 this.dbPassword=p.getProperty("dbPassword");
		 this.salesDB=p.getProperty("salesDB");
		 this.dashboardDB=p.getProperty("dashboardDB");
	 }
	 
	 
	 
	public void gAppProperties(String path){
		this.settingUrl=path;
	}
	 
	 
	 
	 public Boolean verifyModule(int roleid,int moduleid,int privilegeid){
		
		 //Check First Main Layer 
		 Boolean status=false;
		 Map det=new HashMap();
		 det.put("roleid",roleid);
		 det.put("moduleid",moduleid);
		 det.put("privilegeid",privilegeid);
		 
		 Map dtr=this.fetchRolePrivilege(det);
		
		 
		 if(dtr.get("privilege_status").equals("disabled")){
			 status=false;
		 }else
		 {
			 if(privilegeid==0){
				 //General Settings 	
				 status=true;
			 }else{ 
				 
				 if(this.adjustChar((String)dtr.get("privilege_mode"),privilegeid)==1){
					 status=true;
				 }else{
					 status=false;
				 }
			 }
			 
		 }
		 
		 return status;
	 }	 
	 
	 
	 public Map fetchRolePrivilege(Map det){
			
			//privileges
			Map siDetails=new HashMap();
			String query="";
			 try{
					 
				 ResultSet rs=null;
				 this.fetchProperties();
				 
			
				 Connection connection=null;
				 Class.forName("com.mysql.jdbc.Driver");
				 connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
				 
				  Statement st = connection.createStatement();
				  
				  
				  query="Select * from role_privileges where " +
				  		"roleid='"+det.get("roleid")+"'" +
				  				"and moduleid='"+det.get("moduleid")+"'";
				 rs = st.executeQuery(query);
				  
				 
				  if (rs.next()) {  
					 do{
						 siDetails.put("roleid",rs.getString("roleid"));
						 siDetails.put("moduleid",rs.getString("moduleid"));
						 siDetails.put("privilege_mode",rs.getString("privilege_mode"));
						 siDetails.put("privilege_status",rs.getString("privilege_status"));
							
					 }while(rs.next());
					  
				  }else{
					  //Disabled
					  siDetails.put("privilege_status","disabled");
				  }
				
				  rs.close();
				  st.close();
				  connection.close();
				  
			 } catch (SQLException e) {
				  System.err.println("SQLException: "
			    	        +e.getMessage());
			      System.err.println("SQL Query ERROR: "+query);
			 
			 } catch (Exception e){
			 			System.out.println("Error in fetching"+e);
			 }	  
			
			return siDetails;
	}
	 
	 
	private int adjustChar(String privilege_mode, int privilegeid){
		int setPrivilegeid=privilegeid-1;
		int iVAl=Integer.parseInt ("" +privilege_mode.charAt(setPrivilegeid));
			
		return iVAl;
		//return privilege_mode.charAt(setPrivilegeid);
	} 
	 

	 
}
