package com.model.lib;

import com.misc.lib.CustomHelper;

import java.util.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

public class ChannelModel{
	
	//For Database Connection
	 private String connectionURL = "";
	 private String dbUser="";
	 private String dbPassword="";
	 public String projectFile="";
	 public int  channel_id=0;
	 
		
	 public void fetchProperties() throws IOException{
		 
		 String relativeWebPath = "/WEB-INF/app.properties";
		 Properties p = new Properties();
		 p.load(new FileInputStream(projectFile+relativeWebPath));
		 
		 this.connectionURL=p.getProperty("connectionURL");
		 this.dbUser=p.getProperty("dbUser");
		 this.dbPassword=p.getProperty("dbPassword");
	 }
	 
	 
	 public Boolean InsertChannel(Map det){
			
			String query="";
			Boolean process=false;
		try{
			 this.fetchProperties();
			 Connection connection=null;
			 Class.forName("com.mysql.jdbc.Driver");
			 connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
			 CustomHelper ch=new CustomHelper();
			 
			  Statement st = connection.createStatement();
			   query="INSERT INTO channel("+
							"channel_id,"+
							"channel_name," +
							"channel_group_id," +
							"channel_automated," +
							"app_id" +
							")" +
							"VALUES(" +
							"'"+det.get("channel_id")+"',"+
							"'"+det.get("channel_name")+"'," +
							"'"+det.get("channel_group_id")+"'," +
							"'"+det.get("channel_automated")+"'," +
							"'"+det.get("app_id")+"'" +
							")";
				
			  st.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
			  int ichannel_id=0;
			  ResultSet rs = st.getGeneratedKeys();
		        if (rs.next()){
		        	ichannel_id=rs.getInt(1);
		        }
		        this.channel_id=ichannel_id;
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
	 
	 public ResultSet loadChannelGroup(){
			
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
						" channel_group";
					
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
	 
	 
	 
	 public ResultSet loadApp(){
			
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
						" app";
					
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
	 
	  
	  
	  public ResultSet loadChannel(Map det){
			
			String query="";
			ResultSet rs=null;
			
			try{
				 this.fetchProperties();
				 Connection connection=null;
				 Class.forName("com.mysql.jdbc.Driver");
				 connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
				 
				  Statement st = connection.createStatement();
				  	query+=" SELECT channel_group.channel_group_name, "
							+ "       channel.channel_id, "
							+ "       channel.channel_name, "
							+ "       channel.channel_group_id, "
							+ "       channel.channel_automated, "
							+ "       channel.app_id, "
							+ "       ifnull(app.app_name,'') as app_name "
							+ "FROM   channel "
							+ "       INNER JOIN channel_group "
							+ "               ON channel.channel_group_id = channel_group.channel_group_id "
							+ "       LEFT JOIN app "
							+ "               ON channel.app_id = app.app_id "
						    + " where 1=1 ";
				  
				  
				  if(det.get("channel_id") != null && det.get("channel_id") != ""){
					  query+=" and channel.channel_id='"+det.get("channel_id")+"'";
				  }
				  
				  if(det.get("channel_name") != null && det.get("channel_name") != ""){
					  query+=" and channel.channel_name LIKE '%"+det.get("channel_name")+"%' ";
				  }
				  
				  
				  if(det.get("channel_group_id") != null && det.get("channel_group_id") != ""){
					  query+=" and channel.channel_group_id='"+det.get("channel_group_id")+"'";
				  }
				  
				  
				  if(det.get("channel_automated") != null && det.get("channel_automated") != ""){
					  query+=" and channel.channel_automated='"+det.get("channel_automated")+"'";
				  }
				  
				  if(det.get("app_id") != null && det.get("app_id") != ""){
					  query+=" and channel.app_id='"+det.get("app_id")+"'";
				  }
				  

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
	  public ResultSet loadChannelByLocation(Map det){
			
			String query="";
			ResultSet rs=null;
			
			try{
				 this.fetchProperties();
				 Connection connection=null;
				 Class.forName("com.mysql.jdbc.Driver");
				 connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
				 
				  Statement st = connection.createStatement();
				  	query+=" SELECT channel_group.channel_group_name, "
							+ "       channel.channel_id, "
							+ "       channel.channel_name, "
							+ "       channel.channel_group_id, "
							+ "       channel.channel_automated, "
							+ "       channel.app_id, "
							+ "       ifnull(app.app_name,'') as app_name "
							+ "FROM   channel "
							+ "       INNER JOIN channel_group "
							+ "               ON channel.channel_group_id = channel_group.channel_group_id "
							+ "       LEFT JOIN app "
							+ "               ON channel.app_id = app.app_id "
						    + " where 1=1 "
						    + "AND channel_group.category='"+det.get("location")+"'";
				  
				  
				  if(det.get("channel_id") != null && det.get("channel_id") != ""){
					  query+=" and channel.channel_id='"+det.get("channel_id")+"'";
				  }
				  
				  if(det.get("channel_name") != null && det.get("channel_name") != ""){
					  query+=" and channel.channel_name LIKE '%"+det.get("channel_name")+"%' ";
				  }
				  
				  
				  if(det.get("channel_group_id") != null && det.get("channel_group_id") != ""){
					  query+=" and channel.channel_group_id='"+det.get("channel_group_id")+"'";
				  }
				  
				  
				  if(det.get("channel_automated") != null && det.get("channel_automated") != ""){
					  query+=" and channel.channel_automated='"+det.get("channel_automated")+"'";
				  }
				  
				  if(det.get("app_id") != null && det.get("app_id") != ""){
					  query+=" and channel.app_id='"+det.get("app_id")+"'";
				  }
				  

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
	  
	  
	  

		
		public Boolean UpdateChannel(Map det){
				
				
				String query="";
				Boolean process=false;
			try{
				 this.fetchProperties();
				 Connection connection=null;
				 Class.forName("com.mysql.jdbc.Driver");
				 connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
				 CustomHelper ch=new CustomHelper();
				 
				  Statement st = connection.createStatement();
				  
				   query="UPDATE channel set " +
					   	 " channel_name='" +det.get("channel_name")+"',"+
					   	 " channel_group_id='" +det.get("channel_group_id")+"',"+
					   	 " channel_automated='" +det.get("channel_automated")+"',"+
					   	 " app_id='" +det.get("app_id")+"'"+
					   	 " where channel_id='" +det.get("channel_id")+"'";
					   
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


		
		public Boolean DeleteChannel(String channel_id){
			
			String query="";
			
			
			Boolean process=false;
		try{
			 this.fetchProperties();
			 Connection connection=null;
			 Class.forName("com.mysql.jdbc.Driver");
			 connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
			 CustomHelper ch=new CustomHelper();
			 
			  Statement st = connection.createStatement();
			  query="DELETE FROM channel WHERE channel_id='"+channel_id+"'";
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
