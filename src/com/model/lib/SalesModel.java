package com.model.lib;

import com.misc.lib.CustomHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.security.*;
import java.math.*;

import javax.servlet.http.HttpSession;

public class SalesModel{
	
	//For Database Connection
	 private String connectionURL = "";
	 private String dbUser="";
	 private String dbPassword="";
	 public String projectFile="";
	 public int  iSalesInfo=0;
	 public String salesDB="";
	 public String dashboardDB="";
	 public Connection connection=null;
	 
	 
		
	 public void fetchProperties() throws IOException, SQLException, ClassNotFoundException{
		 
		 String relativeWebPath = "/WEB-INF/app.properties";
		 Properties p = new Properties();
		 p.load(new FileInputStream(projectFile+relativeWebPath));
		 
		 connectionURL=p.getProperty("connectionURL");
		 dbUser=p.getProperty("dbUser");
		 dbPassword=p.getProperty("dbPassword");
		 salesDB=p.getProperty("salesDB");
		 Class.forName("com.mysql.jdbc.Driver");
		 this.connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
	 }
	 
	 
	 public ResultSet loadSales(Map det){
			
			String query="";
			ResultSet rs=null;
			
			try{
				 this.fetchProperties();
				 Statement st = this.connection.createStatement();
				  	query+="SELECT channel.channel_name, "
							+ "       revenue_report.channel_id, "
							+ "       DATE_FORMAT(revenue_report.revenue_date, '%m/%d/%Y') as revenue_date, "
							+ "       revenue_report.revenue_date as revDate, "
							+ "       revenue_report.amount, "
							+ "       revenue_report.revenue_type "
							+ "FROM   revenue_report "
							+ "       INNER JOIN channel "
							+ "               ON revenue_report.channel_id = channel.channel_id "
						    + " where 1=1 ";
				  
				  
				  if(det.get("channel_id") != null && det.get("channel_id") != ""){
					  query+=" and revenue_report.channel_id='"+det.get("channel_id")+"'";
				  }
				  
				  if(det.get("revenue_date") != null && det.get("revenue_date") != ""){
					  query+=" and revenue_report.revenue_date='"+det.get("revenue_date")+"'";
				  }
				  
				  if(det.get("amount") != null && det.get("amount") != ""){
					  query+=" and revenue_report.amount LIKE '%"+det.get("amount")+"%'";
				  }
				  
				  if(det.get("revenue_type") != null && det.get("revenue_type") != ""){
					  query+=" and  revenue_report.revenue_type='"+det.get("revenue_type")+"'";
				  }
				  
				  query+=" order by revDate desc";
				  
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
	  
	 
	 public Boolean InsertSales(Map det){
			
			String query="";
			Boolean process=false;
		try{
			 this.fetchProperties();
			 Statement st = this.connection.createStatement();
			  
			  String queryColumn="";
			  String queryValue="";
			  
			  if(det.get("reference") != null && det.get("reference") != ""){
				  queryColumn+=",reference";
				  queryValue=",'"+det.get("reference")+"'";
			  }
			  
			   query="INSERT INTO revenue_report("+
							"channel_id,"+
							"revenue_date," +
							"amount," +
							"revenue_type" +queryColumn+
							")" +
							"VALUES(" +
							"'"+det.get("channel_id")+"',"+
							"'"+det.get("revenue_date")+"'," +
							"'"+det.get("amount")+"'," +
							"'"+det.get("revenue_type")+"'" +queryValue+
							")";
				
			  st.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
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
	 
	 
		public Boolean UpdateSales(Map det){
			
			
			String query="";
			Boolean process=false;
		try{
			 this.fetchProperties();
			 CustomHelper ch=new CustomHelper();
			 Statement st = this.connection.createStatement();
			 query="UPDATE revenue_report set " +
				   	 " amount='" +det.get("amount")+"'"+
				   	 " where channel_id='" +det.get("channel_id")+"'" +
				   	 " and revenue_date='" +det.get("revenue_date")+"'" +
				   	 " and revenue_type='" +det.get("revenue_type")+"'";
				   
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

		
		
		
		public Boolean DeleteSales(Map det){
			
			String query="";
			Boolean process=false;
		try{
			 this.fetchProperties();
			 CustomHelper ch=new CustomHelper();
			 Statement st = this.connection.createStatement();
			 query="DELETE FROM revenue_report " +
			  		 " where channel_id='" +det.get("channel_id")+"'" +
				   	 " and revenue_date='" +det.get("revenue_date")+"'" +
				   	 " and revenue_type='" +det.get("revenue_type")+"'";
			  
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

		
	
		public ResultSet loadFromSalesTeam(){
			
			String query="";
			ResultSet rs=null;
			
			try{
				 this.fetchProperties();
				 Statement st = this.connection.createStatement();
				 query=    "SELECT "+this.salesDB+".sales_order.orderid, "
						  + "       "+this.salesDB+".sales_order.salesinvoice, "
						  + "       "+this.salesDB+".sales_order.accountid, "
						  + "       "+this.salesDB+".sales_order.purchase_date, "
						  + "       "+this.salesDB+".sales_order.requestor, "
						  + "       "+this.salesDB+".sales_order.totalamount, "
						  + "       "+this.salesDB+".sales_order.netamount, "
						  + "       "+this.salesDB+".sales_order.orderstatus, "
						  + "       "+this.salesDB+".sales_order.deliverystatus, "
						  + "       "+this.salesDB+".sales_order.paymentstatus, "
						  + "       "+this.salesDB+".sales_order.totalamountpaid, "
						  + "       "+this.salesDB+".sales_order.salesinvoicehash, "
						  + "       "+this.salesDB+".sales_order.salesinvoicecreateddate, "
						  + "       "+this.salesDB+".sales_order.salesinvoicestatus, "
						  + "       "+this.salesDB+".sales_order.salesinvoicelastactiondate, "
						  + "       "+this.salesDB+".sales_order.agentid, "
						  + "       "+this.salesDB+".agents.agentuserid, "
						  + "       "+this.salesDB+".territories.territoryid, "
						  + "       "+this.salesDB+".territories.territoryname, "
						  + "       "+this.salesDB+".areas.areaname, "
						  + "       "+this.salesDB+".agent_areas.areaid "
						  + "FROM   "+this.salesDB+".sales_order "
						  + "       INNER JOIN "+this.salesDB+".agents "
						  + "               ON "+this.salesDB+".sales_order.agentid = "+this.salesDB+".agents.agentid "
						  + "       INNER JOIN "+this.salesDB+".agent_areas "
						  + "               ON "+this.salesDB+".agents.agentid = "+this.salesDB+".agent_areas.agentid "
						  + "       INNER JOIN "+this.salesDB+".areas "
						  + "               ON "+this.salesDB+".agent_areas.areaid = "+this.salesDB+".areas.areaid "
						  + "       INNER JOIN "+this.salesDB+".territories "
						  + "               ON "+this.salesDB+".areas.territoryid = "
						  + "                  "+this.salesDB+".territories.territoryid "
						  + " where 1=1 "
				  	      +"  and  "+this.salesDB+".sales_order.orderstatus='completed'";
				  
				 // System.out.println(query);
				/*
				  if(det.get("userid") != null && det.get("userid") != ""){
					  query+=" and `user`.userid='"+det.get("userid")+"'";
				  }
				  */
		
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
		public ResultSet loadAgentMonthlySales(Map det,Boolean byYear){
			
			String query="";
			ResultSet rs=null;
			
			try{
				 this.fetchProperties();
				 Statement st = this.connection.createStatement();
				 query =  " SELECT Ifnull(SUM("+this.salesDB+".sales_order.totalamountpaid),0) as totalpayment, "
						  +" YEAR("+this.salesDB+".sales_order.salesinvoicecreateddate) as year, MONTH("+this.salesDB+".sales_order.salesinvoicecreateddate) as month  "
						  +" FROM "+this.salesDB+".sales_order,"+this.salesDB+".agents "
						  +" WHERE "+this.salesDB+".agents.agentuserid = '"+det.get("userid")+"' "
						  +" AND "+this.salesDB+".sales_order.agentid = "+this.salesDB+".agents.agentid "
						  +" AND "+this.salesDB+".sales_order.paymentstatus"
				  		  +" AND YEAR("+this.salesDB+".sales_order.salesinvoicecreateddate) =  '"+det.get("year")+"' "
				  		  +" GROUP BY YEAR("+this.salesDB+".sales_order.salesinvoicecreateddate), MONTH("+this.salesDB+".sales_order.salesinvoicecreateddate)";
				 // System.out.println(query);
				/*
				  if(det.get("userid") != null && det.get("userid") != ""){
					  query+=" and `user`.userid='"+det.get("userid")+"'";
				  }
				  */
		
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
		public ResultSet loadAgentYearlySales(Map det,Boolean byYear){
			
			String query="";
			ResultSet rs=null;
			
			try{
				 this.fetchProperties();
				 Connection connection=null;
				 Statement st = this.connection.createStatement();
				 query =  " SELECT Ifnull(SUM(salesteam.sales_order.totalamountpaid),0) as totalpayment, "
						  +" YEAR(salesteam.sales_order.salesinvoicecreateddate) as year, MONTH(salesteam.sales_order.salesinvoicecreateddate) as month  "
						  +" FROM salesteam.sales_order,salesteam.agents "
						  +" WHERE salesteam.agents.agentuserid = '"+det.get("userid")+"' "
						  +" AND salesteam.sales_order.agentid = salesteam.agents.agentid "
						  +" AND salesteam.sales_order.paymentstatus"
				  		  +" AND YEAR(salesteam.sales_order.salesinvoicecreateddate) =  '"+det.get("year")+"' "
				  		  +" GROUP BY YEAR(salesteam.sales_order.salesinvoicecreateddate)";
				 // System.out.println(query);
				/*
				  if(det.get("userid") != null && det.get("userid") != ""){
					  query+=" and `user`.userid='"+det.get("userid")+"'";
				  }
				  */
		
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
		
		
		public Boolean InsertSalesRevenue(Map det){
			
			String query="";
			Boolean process=false;
		try{
			 this.fetchProperties();
			 CustomHelper ch=new CustomHelper();
			 Statement st = this.connection.createStatement();
			 query="INSERT IGNORE INTO sales_revenue (orderid,agentid,areaid,territoryid,amount,completeddate)" +
			  		"VALUES('"+det.get("orderid")+"','"+det.get("agentid")+"','"+det.get("areaid")+"','"+det.get("territoryid")+"','"+det.get("amount")+"','"+det.get("completeddate")+"')";
			 
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

		
		

		public Boolean checkSalesRevenueExistence(Map det){
			
			String query="";
			ResultSet rs=null;
			Boolean status=false;
			
			try{
				 this.fetchProperties();
				 Statement st = this.connection.createStatement();
				 query=    "Select * from  revenue_report"
						  + " where 1=1 ";
				  
				  if(det.get("channel_id") != null && det.get("channel_id") != ""){
					  query+=" and channel_id='"+det.get("channel_id")+"'";
				  }
				  
				  if(det.get("revenue_type") != null && det.get("revenue_type") != ""){
					  query+=" and revenue_type='"+det.get("revenue_type")+"'";
				  }
				  
				  if(det.get("reference") != null && det.get("reference") != ""){
					  query+=" and reference='"+det.get("reference")+"'";
				  }
				
				  rs = st.executeQuery(query);
				
				  try {
					  if (rs.next()) {  
						  status=true;
						}else{
							status=false;
						}
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			 } catch (SQLException e) {
				  System.err.println("SQLException: "
			    	        +e.getMessage());
			      System.err.println("SQL Query: "+query);
			 } catch (Exception e){
			 			System.out.println("Error in fetching"+e);
			 }	  
			return status;
		}
		
		
		 public ResultSet ldMonthTarget(Map det,Boolean specDate){
				
			 	/////Notes here
			 
				String query="";
				ResultSet rs=null;
				
				try{
					 this.fetchProperties();
					 Statement st = this.connection.createStatement();
					 query = "";
					   			
					   			if(det.get("user_id") != null && det.get("user_id") != ""){
					   				query+="SELECT target_amount ";
								}else{
									query+="SELECT Ifnull(Sum(target_amount),0) as target_amount ";
								}		
					   					query+=""
			   							+ "FROM   targets "
			   							+ "WHERE  target_group_id = 2  and department_id='2'";
			   							
			   							
								   		 if(det.get("user_id") != null && det.get("user_id") != ""){
											  query+=" and user_id='"+det.get("user_id")+"'";
											
										}else{
											query+=" and user_id='0'";
											
										}
								   		 query+= "       AND Date_format(target_date, '%Y-%m') = "; 
					   					if(specDate){
												query += "      '"+det.get("month")+"' ";
										}else{
												query += "       Date_format(NOW() - INTERVAL 1 day, '%Y-%m' ";
												query += "       ) ";	
										}
					   					
					 rs = st.executeQuery(query);
					 //System.out.println(query);
				 } catch (SQLException e) {
					  System.err.println("SQLException: "
				    	        +e.getMessage());
				      System.err.println("SQL Query: "+query);
				 } catch (Exception e){
				 			System.out.println("Error in fetching"+e);
				 }	  
				return rs;
			}
		 public ResultSet ldMonthTarget2(Map det,Boolean specDate){
				
			 	/////Notes here
			 
				String query="";
				ResultSet rs=null;
				
				try{
					 this.fetchProperties();
					 Statement st = this.connection.createStatement();
					 query = "";
					   			
					   			if(det.get("user_id") != null && det.get("user_id") != ""){
					   				query+="SELECT target_amount ";
								}else{
									query+="SELECT Ifnull(Sum(target_amount),0) as target_amount ";
								}		
					   					query+=""
			   							+ "FROM   targets "
			   							+ "WHERE  target_group_id = 2  and department_id='2'";
			   							
			   							
								   		 if(det.get("user_id") != null && det.get("user_id") != ""){
											  query+=" and user_id='"+det.get("user_id")+"'";
											
										}else{
											query+=" and user_id='0'";
											
										}
								   		 query+= "       AND Date_format(target_date, '%Y-%m') = "; 
					   					if(specDate){
												query += "      '"+det.get("month")+"' ";
										}else{
												query += "       Date_format(NOW() - INTERVAL 1 day, '%Y-%m' ";
												query += "       ) ";	
										}
					   					
					 rs = st.executeQuery(query);
					 //System.out.println(query);
				 } catch (SQLException e) {
					  System.err.println("SQLException: "
				    	        +e.getMessage());
				      System.err.println("SQL Query: "+query);
				 } catch (Exception e){
				 			System.out.println("Error in fetching"+e);
				 }	  
				return rs;
			}
		 
		 
		 public ResultSet ldYearTarget(Map det){
				
			 	/////Notes here
			 
				String query="";
				ResultSet rs=null;
				
				try{
					 this.fetchProperties();
					 Statement st = this.connection.createStatement();
					 query = "";
							  
					  if(det.get("user_id") != null && det.get("user_id") != ""){
			   				query+="SELECT Ifnull(SUM(target_amount),0) as target_amount ";
						}else{
							query+="SELECT Ifnull(Sum(target_amount),0) as target_amount ";
						}	  
							  query+=" "
							  + "FROM   targets "
							  + "WHERE  target_group_id = '2'  and department_id='2'";
							  
								if(det.get("user_id") != null && det.get("user_id") != ""){
									  query+=" and user_id='"+det.get("user_id")+"'";
								}else{
									query+=" and user_id='0'";
								}	
							  
								query+="       AND Date_format(target_date, '%Y-%m') <= Date_format(NOW() - "
							  + "                                                INTERVAL 1 day, '%Y-%m' "
							  + "                                                ) ";		   
					  rs = st.executeQuery(query);
					  //System.out.println(query);
				 } catch (SQLException e) {
					  System.err.println("SQLException: "
				    	        +e.getMessage());
				      System.err.println("SQL Query: "+query);
				 } catch (Exception e){
				 			System.out.println("Error in fetching"+e);
				 }	  
				return rs;
			}
		 
		 
		 public ResultSet ldYearTotalTarget(Map det){
				
			 	/////Notes here
			 
				String query="";
				ResultSet rs=null;
				
				try{
					 this.fetchProperties();
					 Statement st = this.connection.createStatement();
					 if(det.get("user_id") != null && det.get("user_id") != ""){
			   				query+="SELECT target_amount ";
						}else{
							query+="SELECT Ifnull(Sum(target_amount),0) as target_amount ";
						}
					  
					  
					  		query+=" FROM   targets "
					  			 + "WHERE  target_group_id = '2'  and department_id='2'"
								 + "       AND Date_format(target_date, '%Y') = Date_format(Now() - INTERVAL 1 day, "
								 + "                                            '%Y') ";	
					  		
					  		if(det.get("user_id") != null && det.get("user_id") != ""){
								  query+=" and user_id='"+det.get("user_id")+"'";
							}else{
								query+=" and user_id='0'";
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
		 
		 
		 public ResultSet ldMonthRevenue(Map det,Boolean specDate){
				
			 	/////Notes here
			 
				String query="";
				ResultSet rs=null;
				
				try{
					 this.fetchProperties();
					 Statement st = this.connection.createStatement();
					 query = ""
									  + "SELECT Ifnull(Sum(CASE "
									  + "                    WHEN revenue_type = 'Sales' THEN revenue_report.amount "
									  + "                    ELSE 0 "
									  + "                  end) - Sum(CASE "
									  + "                               WHEN revenue_type = 'Refund' THEN revenue_report.amount ELSE 0 "
									  + "                             end), 0) AS actual_revenue "
									  + "FROM   revenue_report "
									  + " Inner Join sales_revenue ON revenue_report.reference = sales_revenue.orderid"
									  + " Inner Join salesuser_bridge ON sales_revenue.agentid = salesuser_bridge.agentid "
									  + "WHERE   channel_id='99'" 
									  +" and  Date_format(revenue_date, '%Y-%m') = ";
					   			
					   				if(specDate){
					   					query += "       '"+det.get("month")+"'";
					   				}else{
					   					query += "       Date_format(NOW() - INTERVAL 1 day, '%Y-%m' ";
					   					query += "       ) ";
					   				}  
					   		
					   				if(det.get("user_id") != null && det.get("user_id") != ""){
										  query+=" and salesuser_bridge.userid='"+det.get("user_id")+"'";
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
		 
		 public ResultSet ldMonthRevenue2(Map det,Boolean specDate){
				
			 	/////Notes here
			 
				String query="";
				ResultSet rs=null;
				
				try{
					 this.fetchProperties();
					 Statement st = this.connection.createStatement();
					 query = ""
									  + "SELECT Ifnull(Sum(CASE "
									  + "                    WHEN revenue_type = 'Sales' THEN revenue_report.amount "
									  + "                    ELSE 0 "
									  + "                  end) - Sum(CASE "
									  + "                               WHEN revenue_type = 'Refund' THEN revenue_report.amount ELSE 0 "
									  + "                             end), 0) AS actual_revenue "
									  + "FROM   revenue_report "
									  + "WHERE   channel_id='"+det.get("channelid")+"'" 
									  +" AND MONTH(revenue_date) = MONTH(NOW()) AND DAY(revenue_date) < DAY(NOW()) AND Date_format(revenue_date, '%Y-%m')";
					   			
					   		
					   				if(det.get("user_id") != null && det.get("user_id") != ""){
										  query+=" and salesuser_bridge.userid='"+det.get("user_id")+"'";
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
		 public ResultSet ldMonthRevenue3(Map det,Boolean specDate){
				
			 	/////Notes here
			 
				String query="";
				ResultSet rs=null;
				
				try{
					 this.fetchProperties();
					 Statement st = this.connection.createStatement();
					 query = ""
									  + "SELECT Ifnull(Sum(CASE "
									  + "                    WHEN revenue_type = 'Sales' THEN revenue_report.amount "
									  + "                    ELSE 0 "
									  + "                  end) - Sum(CASE "
									  + "                               WHEN revenue_type = 'Refund' THEN revenue_report.amount ELSE 0 "
									  + "                             end), 0) AS actual_revenue "
									  + "FROM   revenue_report "
									  + "WHERE   channel_id='"+det.get("channelid")+"'" 
									  +" AND MONTH(revenue_date) = MONTH(NOW()) AND DAY(revenue_date) < DAY(NOW()) AND Date_format(revenue_date, '%Y-%m')";
					   			
					   		
					   				if(det.get("user_id") != null && det.get("user_id") != ""){
										  query+=" and salesuser_bridge.userid='"+det.get("user_id")+"'";
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
		 
		 public ResultSet ldProductMonthlyRevenue(Map det,Boolean specDate){
				
			 	/////Notes here
			 
				String query="";
				ResultSet rs=null;
				
				try{
					 this.fetchProperties();
					 Statement st = this.connection.createStatement();
					 query = ""
									  + "SELECT Ifnull(Sum(CASE "
									  + "                    WHEN revenue_type = 'Sales' THEN revenue_report.amount "
									  + "                    ELSE 0 "
									  + "                  end) - Sum(CASE "
									  + "                               WHEN revenue_type = 'Refund' THEN revenue_report.amount ELSE 0 "
									  + "                             end), 0) AS actual_revenue, MONTH(revenue_date) as month "
									  + "FROM   revenue_report "
									  + "WHERE  channel_id="+det.get("channelid")+" " 
									  +" AND YEAR(revenue_date) = YEAR(NOW()) AND DAY(revenue_date) < DAY(NOW()) AND Date_format(revenue_date, '%Y-%m')"
									  + " GROUP BY MONTH(revenue_date), YEAR(revenue_date)";
					   			
					   		
					   				if(det.get("user_id") != null && det.get("user_id") != ""){
										  query+=" and salesuser_bridge.userid='"+det.get("user_id")+"'";
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
		 
		 public ResultSet ldYearRevenue(Map det){
				
			 	/////Notes here
			 
				String query="";
				ResultSet rs=null;
				
				try{
					 this.fetchProperties();
					 Statement st = this.connection.createStatement();
					 query = ""
			   							+ "SELECT Ifnull(Sum(CASE "
			   							+ "                    WHEN revenue_type = 'Sales' THEN revenue_report.amount "
			   							+ "                    ELSE 0 "
			   							+ "                  end),0) -  Ifnull(Sum(CASE "
			   							+ "                               WHEN revenue_type = 'Refund' THEN revenue_report.amount - 0 "
			   							+ "                             end), 0) AS actual_revenue "
			   							+ "FROM   revenue_report "
			   							+ " Inner Join sales_revenue ON revenue_report.reference = sales_revenue.orderid"
			   							+ " Inner Join salesuser_bridge ON sales_revenue.agentid = salesuser_bridge.agentid "
			   							+ "WHERE  Date_format(revenue_date, '%Y') = Date_format(NOW() - INTERVAL 1 day, "
			   							+ "                                         '%Y')   and channel_id='99'";
					   			
					   			
					   			if(det.get("user_id") != null && det.get("user_id") != ""){
									  query+=" and salesuser_bridge.userid='"+det.get("user_id")+"'";
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
		 public ResultSet ldYearRevenue2(Map det){
				
			 	/////Notes here
			 
				String query="";
				ResultSet rs=null;
				
				try{
					 this.fetchProperties();
					 Statement st = this.connection.createStatement();
					 query = ""
			   							+ "SELECT Ifnull(Sum(CASE "
			   							+ "                    WHEN revenue_type = 'Sales' THEN revenue_report.amount "
			   							+ "                    ELSE 0 "
			   							+ "                  end),0) -  Ifnull(Sum(CASE "
			   							+ "                               WHEN revenue_type = 'Refund' THEN revenue_report.amount - 0 "
			   							+ "                             end), 0) AS actual_revenue "
			   							+ "FROM   revenue_report "
			   							+ "WHERE  Date_format(revenue_date, '%Y') = Date_format(NOW() - INTERVAL 1 day, "
			   							+ "                                         '%Y')   and channel_id='"+det.get("channelid")+"'";
					   			 
					   			
					   			if(det.get("user_id") != null && det.get("user_id") != ""){
									  query+=" and salesuser_bridge.userid='"+det.get("user_id")+"'";
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
		 
		 
		 public ResultSet loadProjectionMonthly(Map det){
				
			 	/////Notes here
			 
				String query="";
				ResultSet rs=null;
				
				try{
					 this.fetchProperties();
					 Statement st = this.connection.createStatement();
					 query="SELECT Ifnull(Sum(CASE "
					  + "                    WHEN revenue_type = 'Sales' THEN revenue_report.amount "
					  + "                    ELSE 0 "
					  + "                  end) - Sum(CASE "
					  + "                               WHEN revenue_type = 'Refund' THEN revenue_report.amount "
					  + "                               ELSE 0 "
					  + "                             end), 0)                 AS subTotal, "
					  + "       Date_format(revenue_report.revenue_date, '%m') AS monthNum "
					  + "FROM   revenue_report "
					  + " Inner Join sales_revenue ON revenue_report.reference = sales_revenue.orderid"
					  + " Inner Join salesuser_bridge ON sales_revenue.agentid = salesuser_bridge.agentid "
					  + "WHERE  revenue_type = 'Sales' "
					  + "       AND Date_format(revenue_report.revenue_date, '%Y') = '"+det.get("year")+"'   and channel_id='99'";
				 
				 	
					if(det.get("user_id") != null && det.get("user_id") != ""){
						  query+=" and salesuser_bridge.userid='"+det.get("user_id")+"' ";
					}
					 query+= " GROUP  BY Date_format(revenue_report.revenue_date, '%m-%y') ";
					   
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
		 public ResultSet loadProjectionMonthly2(Map det){
				
			 	/////Notes here
			 
				String query="";
				ResultSet rs=null;
				
				try{
					 this.fetchProperties();
					 Statement st = this.connection.createStatement();
					 query="SELECT Ifnull(Sum(CASE "
					  + "                    WHEN revenue_type = 'Sales' THEN revenue_report.amount "
					  + "                    ELSE 0 "
					  + "                  end) - Sum(CASE "
					  + "                               WHEN revenue_type = 'Refund' THEN revenue_report.amount "
					  + "                               ELSE 0 "
					  + "                             end), 0)                 AS subTotal, "
					  + "       MONTH(revenue_report.revenue_date) AS monthNum "
					  + "FROM   revenue_report "
					  + " Inner Join sales_revenue ON revenue_report.reference = sales_revenue.orderid"
					  + " Inner Join salesuser_bridge ON sales_revenue.agentid = salesuser_bridge.agentid "
					  + "WHERE  revenue_type = 'Sales' "
					  + "       AND Date_format(revenue_report.revenue_date, '%Y') = '"+det.get("year")+"'   and channel_id='"+det.get("channelid")+"'";
				 
				 	
					if(det.get("user_id") != null && det.get("user_id") != ""){
						  query+=" and salesuser_bridge.userid='"+det.get("user_id")+"' ";
					}
					 query+= " GROUP  BY Date_format(revenue_report.revenue_date, '%m-%y') ";
					   
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
		 
		 
			public ResultSet loadAgentArea(Map det){
				
				String query="";
				ResultSet rs=null;
				
				try{
					 this.fetchProperties();
					 Statement st = this.connection.createStatement();
					 query = ""
							  + "SELECT "+this.salesDB+".territories.territoryname, "
							  + "       "+this.salesDB+".areas.areaname, "
							  + "       "+this.dashboardDB+".salesuser_bridge.agentid, "
							  + "       "+this.dashboardDB+".salesuser_bridge.userid "
							  + "FROM   "+this.dashboardDB+".salesuser_bridge "
							  + "       INNER JOIN "+this.salesDB+".agent_areas "
							  + "               ON "+this.dashboardDB+".salesuser_bridge.agentid = "
							  + "                  "+this.salesDB+".agent_areas.agentid "
							  + "       INNER JOIN "+this.salesDB+".areas "
							  + "               ON "+this.salesDB+".agent_areas.areaid ="+this.salesDB+".areas.areaid "
							  + "       INNER JOIN "+this.salesDB+".territories "
							  + "               ON "+this.salesDB+".areas.territoryid = "
							  + "                  "+this.salesDB+".territories.territoryid ";
					  
					  if(det.get("user_id") != null && det.get("user_id") != ""){
						  query+=" and salesuser_bridge.userid='"+det.get("user_id")+"'";
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
			public ResultSet loadSalesTeamArea(Map det){
				
				String query="";
				ResultSet rs=null;
				
				try{
					 this.fetchProperties();
					 Statement st = this.connection.createStatement();
					 query = ""
							  + "SELECT "+this.salesDB+".territories.territoryname, "
							  + "       "+this.salesDB+".areas.areaname, "
							  + "       "+this.dashboardDB+".salesuser_bridge.agentid, "
							  + "       "+this.dashboardDB+".salesuser_bridge.userid "
							  + "FROM   "+this.dashboardDB+".salesuser_bridge "
							  + "       INNER JOIN "+this.salesDB+".agent_areas "
							  + "               ON "+this.dashboardDB+".salesuser_bridge.agentid = "
							  + "                  "+this.salesDB+".agent_areas.agentid "
							  + "       INNER JOIN "+this.salesDB+".areas "
							  + "               ON "+this.salesDB+".agent_areas.areaid ="+this.salesDB+".areas.areaid "
							  + "       INNER JOIN "+this.salesDB+".territories "
							  + "               ON "+this.salesDB+".areas.territoryid = "
							  + "                  "+this.salesDB+".territories.territoryid ";
					  
					  if(det.get("user_id") != null && det.get("user_id") != ""){
						  query+=" and salesuser_bridge.userid='"+det.get("user_id")+"'";
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


			public ResultSet ldProductYearlyTarget(Map det, boolean specifiedDate) {
				String query = "";
				
				ResultSet rs = null;
				
				try{
					this.fetchProperties();
					Statement st = this.connection.createStatement();
					
					query = "SELECT Ifnull(SUM(target_amount),0) as actual_revenue,"
							+ " channel_id,"
							+ " target_group_id"
							+ " FROM product_targets"
							+ " WHERE YEAR(target_date) = YEAR(NOW())"
							+ " AND target_group_id = 4";
							
							
					if(det.get("channelid")!=null && det.get("channelid")!=""){
						query+=" AND channel_id='"+det.get("channelid")+"'";
					}
					
				
					
					rs = st.executeQuery(query);
				}catch (SQLException e) {
					  System.err.println("SQLException: "
				    	        +e.getMessage());
				      System.err.println("SQL Query: "+query);
				 } catch (Exception e){
				 			System.out.println("Error in fetching"+e);
				 }
				
				return rs;
			}


			public ResultSet ldProductMonthTarget(Map det, boolean specifiedDate) {
				String query = "";
				
				ResultSet rs = null;
				
				try{
					this.fetchProperties();
					Statement st = this.connection.createStatement();
					query = "SELECT Ifnull(SUM(target_amount),0) as actual_revenue,"
							+ " channel_id,"
							+ " target_group_id"
							+ " FROM product_targets"
							+ " WHERE MONTH(target_date) = MONTH(NOW())"
							+ " AND DAY(target_date) < DAY(NOW())"
							+ " AND target_group_id != 4";		
					
					if(det.get("channelid")!=null && det.get("channelid")!=""){
						query+=" AND channel_id='"+det.get("channelid")+"'";
					}
				
					
					rs = st.executeQuery(query);
				}catch (SQLException e) {
					  System.err.println("SQLException: "
				    	        +e.getMessage());
				      System.err.println("SQL Query: "+query);
				 } catch (Exception e){
				 			System.out.println("Error in fetching"+e);
				 }
				
				return rs;
			}
			public ResultSet ldProductMonthlyTarget(Map det, boolean specifiedDate) {
				String query = "";
				
				ResultSet rs = null;
				
				try{
					this.fetchProperties();
					Statement st = this.connection.createStatement();
					query = "SELECT Ifnull(SUM(target_amount),0) as actual_revenue,"
							+ " channel_id,"
							+ " target_group_id,"
							+ " MONTH(target_date) as month"
							+ " FROM product_targets"
							+ " where target_group_id!=4";		
					
					if(det.get("channelid")!=null && det.get("channelid")!=""){
						query+=" AND channel_id='"+det.get("channelid")+"'";
					}
					if(det.get("year")!=null && det.get("year")!=""){
						query+=" AND YEAR(target_date)='"+det.get("year")+"'";
					}
					else{
						query+=" AND YEAR(target_date)=YEAR(NOW())";
					}
					query+= " GROUP BY MONTH(target_date)";
					//System.out.println(query);
					rs = st.executeQuery(query);
				}catch (SQLException e) {
					  System.err.println("SQLException: "
				    	        +e.getMessage());
				      System.err.println("SQL Query: "+query);
				 } catch (Exception e){
				 			System.out.println("Error in fetching"+e);
				 }
				
				return rs;
			}

}
