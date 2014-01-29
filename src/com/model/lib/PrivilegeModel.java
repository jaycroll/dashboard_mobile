package com.model.lib;

import com.misc.lib.CustomHelper;

import java.util.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

public class PrivilegeModel{
	
	//For Database Connection
	 private String connectionURL = "";
	 private String dbUser="";
	 private String dbPassword="";
	 public String projectFile="";
	 public int  privilegeid=0;
	 
	 
		
	 public void fetchProperties() throws IOException{
		 
		 String relativeWebPath = "/WEB-INF/app.properties";
		 Properties p = new Properties();
		 p.load(new FileInputStream(projectFile+relativeWebPath));
		 
		 this.connectionURL=p.getProperty("connectionURL");
		 this.dbUser=p.getProperty("dbUser");
		 this.dbPassword=p.getProperty("dbPassword");
		
	 }
	  
	public Map fetchPrivilege(int iPrivilegeId){
		
		//privileges
		Map privilegeDetails=new HashMap();
		String query="";
		 try{
				 
			 ResultSet rs=null;
			 this.fetchProperties();
			 Connection connection=null;
			 Class.forName("com.mysql.jdbc.Driver");
			 connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
			 
			  Statement st = connection.createStatement();
			  query="Select * from privileges where privilegeid='"+iPrivilegeId+"'";
			
			  rs = st.executeQuery(query);
			  
			  if (rs.next()) {  
				 do{
					 privilegeDetails.put("privilegeid",rs.getString("privilegeid"));
					 privilegeDetails.put("privilege",rs.getString("privilege"));
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
		
		return privilegeDetails;
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
						" privileges order by privilegeid asc";
					
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


		public Boolean InsertPrivilege(Map det){
					
			
				String query="";
				Boolean process=false;
			try{
				 this.fetchProperties();
				 Connection connection=null;
				 Class.forName("com.mysql.jdbc.Driver");
				 connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
				 CustomHelper ch=new CustomHelper();
				 
				  Statement st = connection.createStatement();
				   query="INSERT INTO privileges("+
								"privilegeid,"+
								"privilege"+
								")" +
								"VALUES(" +
								"'"+det.get("privilegeid")+"',"+
								"'"+det.get("privilege")+"'"+
								")";
					
				  st.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
				  int iPrivilegeId=0;
				  ResultSet rs = st.getGeneratedKeys();
			        if (rs.next()){
			        	iPrivilegeId=rs.getInt(1);
			        }
			        this.privilegeid=iPrivilegeId;
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
		
		
		
		public Boolean UpdatePrivilege(Map det){
			
			
			String query="";
			Boolean process=false;
		try{
			 this.fetchProperties();
			 Connection connection=null;
			 Class.forName("com.mysql.jdbc.Driver");
			 connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
			 CustomHelper ch=new CustomHelper();
			 
			  Statement st = connection.createStatement();
			  
			   query="UPDATE privileges set " +
				   	 " privilege='" +det.get("privilege")+"'"+
				   	 " where privilegeid='" +det.get("privilegeid")+"'";
				   
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
		
		
public Boolean DeletePrivilege(String privilegeid){
					
				String query="";
				Boolean process=false;
			try{
				 this.fetchProperties();
				 Connection connection=null;
				 Class.forName("com.mysql.jdbc.Driver");
				 connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
				 CustomHelper ch=new CustomHelper();
				 
				  Statement st = connection.createStatement();
				  query="DELETE FROM privileges WHERE privilegeid='"+privilegeid+"'";
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
