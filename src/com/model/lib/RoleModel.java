package com.model.lib;

import com.misc.lib.CustomHelper;

import java.util.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

import javax.servlet.http.HttpSession;

public class RoleModel{
	
	//For Database Connection
	 private String connectionURL = "";
	 private String dbUser="";
	 private String dbPassword="";
	 public String projectFile="";
	 public int  roleid=0;
	 
	 
		
	 public void fetchProperties() throws IOException{
		 
		 String relativeWebPath = "/WEB-INF/app.properties";
		 Properties p = new Properties();
		 p.load(new FileInputStream(projectFile+relativeWebPath));
		 
		 
		 this.connectionURL=p.getProperty("connectionURL");
		 this.dbUser=p.getProperty("dbUser");
		 this.dbPassword=p.getProperty("dbPassword");
		
	 }
	  
	
	public Map fetchRole(int iRoleId){
		
		Map usrDetails=new HashMap();
		String query="";
		 try{
				
			 ResultSet rs=null;
			 this.fetchProperties();
			 Connection connection=null;
			 Class.forName("com.mysql.jdbc.Driver");
			 connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
			 
			  Statement st = connection.createStatement();
			  query="Select * from roles where roleid='"+iRoleId+"'";
			
			  rs = st.executeQuery(query);
			  
			  if (rs.next()) {  
				 do{
					 usrDetails.put("roleid",rs.getString("roleid"));
					 usrDetails.put("rolename",rs.getString("rolename"));
				 }while(rs.next());
				  
			  }else{
				   //Details Here
			  }
			
			  
		 } catch (SQLException e) {
			  System.err.println("SQLException: "
		    	        +e.getMessage());
		      System.err.println("SQL Query: "+query);
		 
		 } catch (Exception e){
		 			System.out.println("Error in fetching"+e);
		 }	  
		
		return usrDetails;
	} 
	
	
	
	
public Map fetchRolePrivilege(int roleid,int moduleid){
		
		Map usrDetails=new HashMap();
		String query="";
		 try{
				
			 ResultSet rs=null;
			 this.fetchProperties();
			 Connection connection=null;
			 Class.forName("com.mysql.jdbc.Driver");
			 connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
			 
			  Statement st = connection.createStatement();
			  query="Select * from role_privileges where roleid='"+roleid+"'" +
			  		"and moduleid='"+moduleid+"'";
			
			  rs = st.executeQuery(query);
			  
			  if (rs.next()) {  
				 do{
					 usrDetails.put("roleid",rs.getString("roleid"));
					 usrDetails.put("moduleid",rs.getString("moduleid"));
					 usrDetails.put("privilege_mode",rs.getString("privilege_mode"));
					 usrDetails.put("privilege_status",rs.getString("privilege_status"));
					 
				 }while(rs.next());
				  
			  }else{
				   //Details Here
			  }
			  
			  
		 } catch (SQLException e) {
			  System.err.println("SQLException: "
		    	        +e.getMessage());
		      System.err.println("SQL Query: "+query);
		 
		 } catch (Exception e){
		 			System.out.println("Error in fetching"+e);
		 }	  
		
		return usrDetails;
	} 


		public ResultSet loadRole(){
			
			String query="";
			ResultSet rs=null;
			
			try{
				 this.fetchProperties();
				 Connection connection=null;
				 Class.forName("com.mysql.jdbc.Driver");
				 connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
				 
				  Statement st = connection.createStatement();
				  query="SELECT"+
						" *"+
						" FROM"+
						" roles";
					
				  rs = st.executeQuery(query);
				  
			 } catch (SQLException e) {
				  System.err.println("SQLException: "
			    	        +e.getMessage());
			      System.err.println("SQL Query: "+query);
			 } catch (Exception e){
			 			System.out.println("Error in fetching"+e);
			 }	  
			return rs;
		}
		
		
		public ResultSet loadModule(){
			
			String query="";
			ResultSet rs=null;
			
			try{
				 this.fetchProperties();
				 Connection connection=null;
				 Class.forName("com.mysql.jdbc.Driver");
				 connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
				 
				  Statement st = connection.createStatement();
				  query="SELECT"+
						" *"+
						" FROM"+
						" modules";
					
				  rs = st.executeQuery(query);
				  		
				  
			 } catch (SQLException e) {
				  System.err.println("SQLException: "
			    	        +e.getMessage());
			      System.err.println("SQL Query: "+query);
			 } catch (Exception e){
			 			System.out.println("Error in fetching"+e);
			 }	  
			return rs;
		}
		
		
public ResultSet loadPrivilege(){
			
			String query="";
			ResultSet rs=null;
			
			try{
				 this.fetchProperties();
				 Connection connection=null;
				 Class.forName("com.mysql.jdbc.Driver");
				 connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
				 
				  Statement st = connection.createStatement();
				  query="SELECT"+
						" *"+
						" FROM"+
						" privileges";
					
				  rs = st.executeQuery(query);
				  
			 } catch (SQLException e) {
				  System.err.println("SQLException: "
			    	        +e.getMessage());
			      System.err.println("SQL Query: "+query);
			 } catch (Exception e){
			 			System.out.println("Error in fetching"+e);
			 }	  
			return rs;
		}
		
		public Boolean InsertRole(Map det, HttpSession sess){
					
			
				String query="";
				Boolean process=false;
			try{
				 this.fetchProperties();
				 Connection connection=null;
				 Class.forName("com.mysql.jdbc.Driver");
				 connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
				 CustomHelper ch=new CustomHelper();
				 
				  Statement st = connection.createStatement();
				   query="INSERT INTO roles("+
								"rolename"+
								")" +
								"VALUES(" +
								"'"+det.get("rolename")+"'"+
								")";
				  System.out.println(query);
				  st.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
				  int iRoleID=0;
				  ResultSet rs = st.getGeneratedKeys();
			        if (rs.next()){
			        	iRoleID=rs.getInt(1);
			        }
			        this.roleid=iRoleID;
			        rs.close();
			        
					process=true;  
				  
			 } catch (SQLException e) {
				  System.err.println("SQLException: "
			    	        +e.getMessage());
			      System.err.println("SQL Query: "+query);
			 } catch (Exception e){
			 			System.out.println("Error in fetching"+e);
			 }	
			return process;
		}
		
		
		
		public Boolean UpdateRole(Map det){
			
			
			String query="";
			Boolean process=false;
		try{
			 this.fetchProperties();
			 Connection connection=null;
			 Class.forName("com.mysql.jdbc.Driver");
			 connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
			 CustomHelper ch=new CustomHelper();
			 
			  Statement st = connection.createStatement();
			  
			   query="UPDATE roles set " +
				   	 " rolename='" +det.get("rolename")+"'"+
				   	 " where roleid='" +det.get("roleid")+"'";
				   
			  st.executeUpdate(query);
			  
			  process=true;  
			  
		 } catch (SQLException e) {
			  System.err.println("SQLException: "
		    	        +e.getMessage());
		      System.err.println("SQL Query: "+query);
		 } catch (Exception e){
		 			System.out.println("Error in fetching"+e);
		 }	
		return process;
	}
		
		
	public Boolean InsertRolePrivilege(Map det){
			
			
			String query="";
			Boolean process=false;
		try{
			 this.fetchProperties();
			 Connection connection=null;
			 Class.forName("com.mysql.jdbc.Driver");
			 connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
			 CustomHelper ch=new CustomHelper();
			 
			  Statement st = connection.createStatement();
			   query="INSERT INTO role_privileges("+
							"roleid," +
							"moduleid," +
							"privilege_mode," +
							"privilege_status"+
							")" +
							"VALUES(" +
							"'"+det.get("roleid")+"',"+
							"'"+det.get("moduleid")+"',"+
							"'"+det.get("privilege_mode")+"',"+
							"'"+det.get("privilege_status")+"'"+
							")";
				
			      st.executeUpdate(query);
			      st.close();
				  process=true;
			  
			  
		 } catch (SQLException e) {
			  System.err.println("SQLException: "
		    	        +e.getMessage());
		      System.err.println("SQL Query: "+query);
		 } catch (Exception e){
		 			System.out.println("Error in fetching"+e);
		 }	
		return process;
	}
		
	
	
	public Boolean UpdateRolePrivilege(Map det){
		
		
		String query="";
		Boolean process=false;
	try{
		 this.fetchProperties();
		 Connection connection=null;
		 Class.forName("com.mysql.jdbc.Driver");
		 connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
		 CustomHelper ch=new CustomHelper();
		 
		  Statement st = connection.createStatement();
		  
		  query="UPDATE role_privileges set "+
				"privilege_mode='" +det.get("privilege_mode")+"'," + 
				"privilege_status='" +det.get("privilege_status")+"'" +
				" where " +
				" roleid='" +det.get("roleid")+"' and " +
				" moduleid='" +det.get("moduleid")+"'";  
		      st.executeUpdate(query);
		      st.close();
			  process=true;
		  
	 } catch (SQLException e) {
		  System.err.println("SQLException: "
	    	        +e.getMessage());
	      System.err.println("SQL Query: "+query);
	 } catch (Exception e){
	 			System.out.println("Error in fetching"+e);
	 }	
	return process;
}
		
		
public Boolean DeleteRole(String roleid){
					
				String query="";
				Boolean process=false;
			try{
				 this.fetchProperties();
				 Connection connection=null;
				 Class.forName("com.mysql.jdbc.Driver");
				 connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
				 CustomHelper ch=new CustomHelper();
				 
				  Statement st = connection.createStatement();
				  query="DELETE FROM roles WHERE roleid='"+roleid+"'";
				  st.executeUpdate(query);
				  process=true;
				  
				  
			 } catch (SQLException e) {
				  System.err.println("SQLException: "
			    	        +e.getMessage());
			      System.err.println("SQL Query: "+query);
			 } catch (Exception e){
			 			System.out.println("Error in fetching"+e);
			 }	
			return process;
		} 

public Boolean DeleteRolePrivilege(String roleid){
	
	String query="";
	Boolean process=false;
try{
	 this.fetchProperties();
	 Connection connection=null;
	 Class.forName("com.mysql.jdbc.Driver");
	 connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
	 CustomHelper ch=new CustomHelper();
	 
	  Statement st = connection.createStatement();
	  query="DELETE FROM role_privileges WHERE roleid='"+roleid+"'";
	  st.executeUpdate(query);
	  process=true;
	  
	  
 } catch (SQLException e) {
	  System.err.println("SQLException: "
    	        +e.getMessage());
      System.err.println("SQL Query: "+query);
 } catch (Exception e){
 			System.out.println("Error in fetching"+e);
 }	
return process;
} 


}
