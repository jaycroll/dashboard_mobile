package com.model.lib;

import com.misc.lib.CustomHelper;

import java.util.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

import javax.activation.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class TargetModel{
	
	//For Database Connection
	 private String connectionURL = "";
	 private String dbUser="";
	 private String dbPassword="";
	 public String projectFile="";
	 public int  targetid=0;
	 
		
	 public void fetchProperties() throws IOException{
		 
		 String relativeWebPath = "/WEB-INF/app.properties";
		 Properties p = new Properties();
		 p.load(new FileInputStream(projectFile+relativeWebPath));
		 
		 this.connectionURL=p.getProperty("connectionURL");
		 this.dbUser=p.getProperty("dbUser");
		 this.dbPassword=p.getProperty("dbPassword");
		 
	 }
	 
	 
	 public Boolean InsertTarget(Map det){
			
			String query="";
			Boolean process=false;
		try{
			 this.fetchProperties();
			 Connection connection=null;
			 Class.forName("com.mysql.jdbc.Driver");
			 connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
			 CustomHelper ch=new CustomHelper();
			 
			  Statement st = connection.createStatement();
			   query="INSERT INTO targets("+
							"target_date,"+
							"target_amount," +
							"target_group_id," +
							"department_id," +
							"user_id," +
							"target_datecreated"+
							")" +
							"VALUES(" +
							"'"+det.get("target_date")+"',"+
							"'"+det.get("target_amount")+"'," +
							"'"+det.get("target_group_id")+"'," +
							"'"+det.get("department_id")+"'," +
							"'"+det.get("user_id")+"'," +
							"'"+ch.loadDateNow()+"'" +
							")";
				
			  st.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
			  int iTargetid=0;
			  ResultSet rs = st.getGeneratedKeys();
		        if (rs.next()){
		        	iTargetid=rs.getInt(1);
		        }
		        this.targetid=iTargetid;
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
	 
	 
	 public ResultSet loadDepartment(){
			
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
						" department order by department_id asc";
					
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
	 
	 
	  public ResultSet loadTargetGroup(){
			
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
						" target_group";
					
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
		  
	  public ResultSet loadDepartmentUser(Map det){
			
			String query="";
			ResultSet rs=null;
			
			try{
				 this.fetchProperties();
				 Connection connection=null;
				 Class.forName("com.mysql.jdbc.Driver");
				 connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
				 
				  Statement st = connection.createStatement();
				  		  query="SELECT `user`.userid, "
							  + "       `user`.userfirstname, "
							  + "       `user`.userlastname, "
							  + "       roles.rolename, "
							  + "       department.department_name, "
							  + "       roles.department_id "
							  + "FROM   department "
							  + "       INNER JOIN roles "
							  + "               ON roles.department_id = department.department_id "
							  + "       INNER JOIN `user` "
							  + "               ON `user`.roleid = roles.roleid "
							  + " WHERE 1=1 ";
							
						  if(det.get("department_id") != null && det.get("deparment_id") != ""){
							  query+=" and roles.department_id = '"+det.get("department_id")+"' ";;
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
	 
	  
	  
	  public ResultSet loadTarget(Map det){
			
			String query="";
			ResultSet rs=null;
			
			try{
				 this.fetchProperties();
				 Connection connection=null;
				 Class.forName("com.mysql.jdbc.Driver");
				 connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
				 
				  Statement st = connection.createStatement();
				  query= "SELECT targets.target_id, "
						  + "       DATE_FORMAT(targets.target_date, '%m/%d/%Y') as target_date, "
						  + "       targets.target_amount, "
						  + "       targets.target_group_id, "
						  + "       targets.user_id, "
						  + "       targets.department_id, "
						  + "       targets.target_datecreated, "
						  + "       target_group.target_group_name, "
						  + "       `user`.userid, "
						  + "       `user`.userfirstname, "
						  + "       `user`.userlastname, "
						  + "       department.department_name "
						  + "FROM   targets "
						  + "       LEFT JOIN `user` "
						  + "              ON targets.user_id = `user`.userid "
						  + "       INNER JOIN department "
						  + "               ON targets.department_id = department.department_id "
						  + "       INNER JOIN target_group "
						  + "               ON targets.target_group_id = target_group.target_group_id "
						  + " where 1=1 ";
				  
				  
				  if(det.get("target_id") != null && det.get("target_id,") != ""){
					  query+=" and targets.target_id='"+det.get("target_id")+"'";
				  }
				  
				  if(det.get("target_date") != null && det.get("target_date,") != ""){
					  query+=" and targets.target_date='"+det.get("target_date")+"'";
				  }
				  

				  if(det.get("target_amount") != null && det.get("target_amount") != ""){
					  query+=" and targets.target_amount LIKE '%"+det.get("target_amount")+"%'";
				  }
				  
				  if(det.get("department_id") != null && det.get("department_id") != ""){
					  query+=" and targets.department_id='"+det.get("department_id")+"'";
				  }
				  
				  if(det.get("target_group_id") != null && det.get("target_group_id") != ""){
					  query+=" and targets.target_group_id='"+det.get("target_group_id")+"'";
				  }
				  
				  if(det.get("user") != null && det.get("user") != ""){
					  query+=" and (user.userfirstname LIKE '%"+det.get("user")+"%' || user.userlastname LIKE '%"+det.get("user")+"%')";
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
	  public ResultSet loadProductTarget(Map det){
			
			String query="";
			ResultSet rs=null;
			
			try{
				 this.fetchProperties();
				 Connection connection=null;
				 Class.forName("com.mysql.jdbc.Driver");
				 connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
				 
				  Statement st = connection.createStatement();
				  query= "SELECT product_targets.target_id, "
						  + "       DATE_FORMAT(product_targets.target_date, '%m/%d/%Y') as target_date, "
						  + "       product_targets.target_amount, "
						  + "       product_targets.target_group_id, "
						  + "       product_targets.target_datecreated, "
						  + "       product_targets.channel_id, "	
						  + "       product_targets.target_group_id, "
						  + "       target_group.target_group_name, "
						  + "		channel.channel_name, "
						  + "		channel_group.category  "	
						  + "FROM   product_targets "	
						  + "       INNER JOIN target_group "
						  + "               ON product_targets.target_group_id = target_group.target_group_id "
						  + "       INNER JOIN channel "
						  + "               ON product_targets.channel_id = channel.channel_id "
						  + "       INNER JOIN channel_group "
						  + "               ON channel.channel_group_id = channel_group.channel_group_id "
						  + " where 1=1 ";
				  
				  
				  if(det.get("target_id") != null && det.get("target_id,") != ""){
					  query+=" and product_targets.target_id='"+det.get("target_id")+"'";
				  }
				  
				  if(det.get("target_date") != null && det.get("target_date,") != ""){
					  query+=" and product_targets.target_date='"+det.get("target_date")+"'";
				  }
				  

				  if(det.get("target_amount") != null && det.get("target_amount") != ""){
					  query+=" and product_targets.target_amount LIKE '%"+det.get("target_amount")+"%'";
				  }
				  
				
				  if(det.get("target_group_id") != null && det.get("target_group_id") != ""){
					  query+=" and product_targets.target_group_id='"+det.get("target_group_id")+"'";
				  }
				  if(det.get("channel_id") != null && det.get("channel_id") != ""){
					  query+=" and product_targets.channel_id='"+det.get("channel_id")+"'";
				  }
				  if(det.get("category") != null && det.get("category") != ""){
					  query+=" and channel_group.category='"+det.get("category")+"'";
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
	  
	  public ResultSet loadProducts(Map det){
		  String query="SELECT channel.channel_id,"
				 + " channel.channel_name,"
				 + " channel.channel_group_id,"
				 + " channel_automated,"
				 + " app_id"
				 + " FROM channel"
				 + " WHERE 1=1";
		  ResultSet rs =null;
		  
		  try{
			  this.fetchProperties();
			  Connection connection=null;
			  Class.forName("com.mysql.jdbc.Driver");
			  connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
			  
			  Statement st = connection.createStatement();
			  if(det.get("target_id")!=null && det.get("target_id")!=""){
				  query += " AND target_id='"+det.get("target_id")+"'";
			  }
			  if(det.get("target_date")!=null && det.get("target_date")!=""){
				  query += " AND target_date='"+det.get("target_date")+"'";
			  }
			  if(det.get("target_amount")!=null && det.get("target_amount")!=""){
				  query += " AND target_date='"+det.get("target_amount")+"'";
			  }
			  if(det.get("target_group_id")!=null && det.get("target_group_id")!=""){
				  query += " AND target_group_id='"+det.get("target_group_id")+"'";
			  }
			  if(det.get("channel_id")!=null && det.get("channel_id")!=""){
				  query += " AND channel_id='"+det.get("channel_id")+"'";
			  }
			  if(det.get("target_datecreated")!=null && det.get("target_datecreated")!=""){
				  query += " AND target_datecreated='"+det.get("target_datecreated")+"'";
			  }
			  rs = st.executeQuery(query);
		  }	
		  catch (SQLException e) {
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

		
		public Boolean UpdateTarget(Map det){
				
				
				String query="";
				Boolean process=false;
			try{
				 this.fetchProperties();
				 Connection connection=null;
				 Class.forName("com.mysql.jdbc.Driver");
				 connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
				 CustomHelper ch=new CustomHelper();
				 
				  Statement st = connection.createStatement();
				  
				   query="UPDATE targets set " +
					   	 " target_date='" +det.get("target_date")+"',"+
					   	 " target_amount='" +det.get("target_amount")+"',"+
					   	 " target_group_id='" +det.get("target_group_id")+"',"+
					   	 " department_id='" +det.get("department_id")+"',"+
					   	 " user_id='" +det.get("user_id")+"'"+
					   	 " where target_id='" +det.get("target_id")+"'";
					   
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


		
		public Boolean DeleteTarget(String target_id){
			
			String query="";
			Boolean process=false;
		try{
			 this.fetchProperties();
			 Connection connection=null;
			 Class.forName("com.mysql.jdbc.Driver");
			 connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
			 CustomHelper ch=new CustomHelper();
			 
			  Statement st = connection.createStatement();
			  query="DELETE FROM targets WHERE target_id='"+target_id+"'";
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


		public boolean InsertProductTarget(Map det) {
			
			String query="";
			Boolean process=false;
		try{
			 this.fetchProperties();
			 Connection connection=null;
			 Class.forName("com.mysql.jdbc.Driver");
			 connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
			 CustomHelper ch=new CustomHelper();
			 
			  Statement st = connection.createStatement();
			   query="INSERT INTO product_targets("+
							"target_date,"+
							"target_amount," +
							"target_group_id," +
							"channel_id," +
							"target_datecreated"+
							")" +
							"VALUES(" +
							"'"+det.get("target_date")+"',"+
							"'"+det.get("target_amount")+"'," +
							"'"+det.get("target_group_id")+"'," +
							"'"+det.get("channel_id")+"'," +
							"'"+ch.loadDateNow()+"'" +
							")";
				
			  st.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
			  int iTargetid=0;
			  ResultSet rs = st.getGeneratedKeys();
		        if (rs.next()){
		        	iTargetid=rs.getInt(1);
		        }
		        this.targetid=iTargetid;
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


		public boolean DeleteProductTarget(String target_id) {
			String query="";
			Boolean process=false;
		try{
			 this.fetchProperties();
			 Connection connection=null;
			 Class.forName("com.mysql.jdbc.Driver");
			 connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
			 CustomHelper ch=new CustomHelper();
			 
			  Statement st = connection.createStatement();
			  query="DELETE FROM product_targets WHERE target_id='"+target_id+"'";
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


		public boolean UpdateProductTarget(Map det) {
			String query="";
			Boolean process=false;
		try{
			 this.fetchProperties();
			 Connection connection=null;
			 Class.forName("com.mysql.jdbc.Driver");
			 connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
			 CustomHelper ch=new CustomHelper();
			 
			  Statement st = connection.createStatement();
			  
			   query="UPDATE product_targets set " +
				   	 " target_date='" +det.get("target_date")+"',"+
				   	 " target_amount='" +det.get("target_amount")+"',"+
				   	 " target_group_id='" +det.get("target_group_id")+"',"+
				   	 " channel_id='" +det.get("channel_id")+"'"+
				   	 " where target_id='" +det.get("target_id")+"'";
				   
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
