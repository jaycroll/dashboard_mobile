package com.model.lib;

import java.util.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

public class ProjectionModel{
	
	//For Database Connection
	 private String connectionURL = "";
	 private String dbUser="";
	 private String dbPassword="";
	 public String projectFile="";
	 public Connection connection=null;
	private String salesDB = "";
		
	 public void fetchProperties() throws IOException, SQLException, ClassNotFoundException{
		 
		 String relativeWebPath = "/WEB-INF/app.properties";
		 Properties p = new Properties();
		 p.load(new FileInputStream(projectFile+relativeWebPath));
         //System.out.println(projectFile);
         this.connectionURL=p.getProperty("connectionURL");
         this.dbUser=p.getProperty("dbUser");	
         this.dbPassword=p.getProperty("dbPassword");
         this.salesDB=p.getProperty("salesDB");
         Class.forName("com.mysql.jdbc.Driver");
		 this.connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
		
	
	 }
	 
	 
	 public ResultSet loadProjectionA(Map det){
			
		 	/////Notes here
		 
			String query="";
			ResultSet rs=null;
			
			try{
				 this.fetchProperties();
				 Statement st = this.connection.createStatement();
				  
				  query = ""
						  + "SELECT ( (SELECT Ifnull(Sum(revenue_report.amount), 0) AS thisyear "
						  + "          FROM   revenue_report "
						  + "          WHERE  Date_format(revenue_report.revenue_date, '%Y-%m') = '"+det.get("fromMonth")+"' "
						  + "                 AND Date_format(revenue_report.revenue_date, '%Y-%m-%d') < "
						  + "                     '"+det.get("toMonth")+"' "
						  + "                 AND revenue_type = 'Sales') - (SELECT "
						  + "                Ifnull( "
						  + "                                               Sum(revenue_report.amount), 0) AS "
						  + "                thisyear "
						  + "                                                FROM   revenue_report "
						  + "                                                WHERE "
						  + "                Date_format( "
						  + "                revenue_report.revenue_date, '%Y-%m') = '"+det.get("fromMonth")+"' "
						  + "                AND Date_format( "
						  + "                    revenue_report.revenue_date, '%Y-%m-%d') < "
						  + "                    '"+det.get("toMonth")+"' "
						  + "                AND revenue_type = 'Refund') ) AS ActualMonthSales, "
						  + "       ( (SELECT Ifnull(Sum(revenue_report.amount), 0) AS thisyear "
						  + "          FROM   revenue_report "
						  + "          WHERE  Date_format(revenue_report.revenue_date, '%Y') = '"+det.get("fromYear")+"' "
						  + "                 AND Date_format(revenue_report.revenue_date, '%Y-%m-%d') < "
						  + "                     '"+det.get("toYear")+"' "
						  + "                 AND revenue_type = 'Sales') - (SELECT "
						  + "         Ifnull( "
						  + "                                               Sum(revenue_report.amount), 0) AS "
						  + "         thisyear "
						  + "                                                FROM   revenue_report "
						  + "                                                WHERE "
						  + "         Date_format( "
						  + "         revenue_report.revenue_date, '%Y') = '"+det.get("fromYear")+"' "
						  + "         AND Date_format( "
						  + "             revenue_report.revenue_date, '%Y-%m-%d') < "
						  + "             '"+det.get("toYear")+"' "
						  + "         AND revenue_type = 'Refund') )        AS ActualYearSales "
						  + "FROM   revenue_report "
						  + "LIMIT  1 ";

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
				  /* query= ""
						  + "SELECT Sum(revenue_report.amount)                     AS subTotal, "
						  + "       Date_format(revenue_report.revenue_date, '%m') AS monthNum "
						  + "FROM   revenue_report "
						  + "WHERE  revenue_type = '"+det.get("revenue_type")+"' "
						  + "       AND Date_format(revenue_report.revenue_date, '%Y') = '"+det.get("year")+"' "
						  + "GROUP  BY Date_format(revenue_report.revenue_date, '%m-%y') ";
						 */
				  
		
			 query="SELECT Ifnull(Sum(CASE "
				  + "                    WHEN revenue_type = 'Sales' THEN amount "
				  + "                    ELSE 0 "
				  + "                  end) - Sum(CASE "
				  + "                               WHEN revenue_type = 'Refund' THEN amount "
				  + "                               ELSE 0 "
				  + "                             end), 0)                 AS subTotal, "
				  + "       Date_format(revenue_report.revenue_date, '%m') AS monthNum "
				  + "FROM   revenue_report "
				  + "WHERE  revenue_type = 'Sales' "
				  + "       AND Date_format(revenue_report.revenue_date, '%Y') = '"+det.get("year")+"' "
				  + "GROUP  BY Date_format(revenue_report.revenue_date, '%m-%y') ";
				   
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
	 public ResultSet loadProductProjectionMonthly(Map det){
			
		 	/////Notes here
		 
			String query="";
			ResultSet rs=null;
			
			try{
				 this.fetchProperties();
				 Statement st = this.connection.createStatement();

		
			 query="SELECT Ifnull(Sum(CASE "
				  + "                    WHEN revenue_type = 'Sales' THEN amount "
				  + "                    ELSE 0 "
				  + "                  end) - Sum(CASE "
				  + "                               WHEN revenue_type = 'Refund' THEN amount "
				  + "                               ELSE 0 "
				  + "                             end), 0)                 AS subTotal, "
				  + "       Date_format(revenue_report.revenue_date, '%m') AS monthNum "
				  + "FROM   revenue_report "
				  + "WHERE  revenue_type = 'Sales' "
				  + "       AND Date_format(revenue_report.revenue_date, '%Y') = '"+det.get("year")+"' "
				  + "GROUP  BY Date_format(revenue_report.revenue_date, '%m-%y') ";
				   
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
								  + "                    WHEN revenue_type = 'Sales' THEN amount "
								  + "                    ELSE 0 "
								  + "                  end) - Sum(CASE "
								  + "                               WHEN revenue_type = 'Refund' THEN amount ELSE 0 "
								  + "                             end), 0) AS actual_revenue "
								  + "FROM   revenue_report "
								  + "WHERE  Date_format(revenue_date, '%Y-%m') = ";
				   			
				   				if(specDate){
				   					query += "       '"+det.get("month")+"'";
				   				}else{
				   					query += "       Date_format(NOW() - INTERVAL 1 day, '%Y-%m' ";
				   					query += "       ) ";
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
	 
	 
	 public ResultSet ldMonthTarget(Map det,Boolean specDate) throws SQLException{
			
		 	/////Notes here
		 
			String query="";
			ResultSet rs=null;
			
			try{
				 this.fetchProperties();
				 Statement st = this.connection.createStatement();
				  
				   			query = ""
		   							+ "SELECT Ifnull(Sum(target_amount),0) as target_amount "
		   							+ "FROM   targets "
		   							+ "WHERE  target_group_id = 2 and department_id='1'"
		   							+ "       AND Date_format(target_date, '%Y-%m') = "; 
		   							
									if(specDate){
											query += "      '"+det.get("month")+"' ";
									}else{
											query += "       Date_format(NOW() - INTERVAL 1 day, '%Y-%m' ";
											query += "       ); ";	
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
	 public void killProcess() throws SQLException{
		
	 }
	 public ResultSet loadAgentMonthlyTarget(Map det,Boolean byYear){
			
		 	/////Notes here
		 
			String query="";
			ResultSet rs=null;
			
			try{
				 this.fetchProperties();
				 Statement st = this.connection.createStatement();
				  
				   			query = "  SELECT target_amount, YEAR(target_date) as year, DATE_FORMAT(target_date,'%m') as month "
				   					+" FROM targets "
				   					+" WHERE user_id='"+det.get("userid")+"' "
				   					+" AND target_group_id=2 "
				   					+" AND department_id=2 "
				   				    +" AND YEAR(target_date)='"+det.get("year")+"' "
				   					+" GROUP BY YEAR(target_date), MONTH(target_date)";
				
				 
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
	 
	 public ResultSet loadAgentsMonthlyTarget(Map det,Boolean byYear){
			
		 	/////Notes here
		 
			String query="";
			ResultSet rs=null;
			
			try{
				 this.fetchProperties();
				 Statement st = this.connection.createStatement();
				  
				   			query = "  SELECT Ifnull(SUM(target_amount),0.00) as target_amount, YEAR(target_date) as year, DATE_FORMAT(target_date,'%m') as month "
				   					+" FROM targets,"+this.salesDB+".agents,"+this.salesDB+".agent_areas,user"
				   					+" WHERE "+this.salesDB+".agent_areas.areaid= "+det.get("areaid")+ ""
				   					+" AND "+this.salesDB+".agents.agentid= "+this.salesDB+".agent_areas.agentid"
				   					+" AND user.userid = "+this.salesDB+".agents.agentuserid "
				   					+" AND targets.user_id = user.userid"
				   					+" AND target_group_id=2 "
				   					+" AND department_id=2 "
				   				    +" AND YEAR(target_date)='"+det.get("year")+"' "
				   					+" GROUP BY YEAR(target_date), MONTH(target_date)";
				
				 
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
		   							+ "                    WHEN revenue_type = 'Sales' THEN amount "
		   							+ "                    ELSE 0 "
		   							+ "                  end),0) -  Ifnull(Sum(CASE "
		   							+ "                               WHEN revenue_type = 'Refund' THEN amount - 0 "
		   							+ "                             end), 0) AS actual_revenue "
		   							+ "FROM   revenue_report "
		   							+ "WHERE  Date_format(revenue_date, '%Y') = Date_format(NOW() - INTERVAL 1 day, "
		   							+ "                                         '%Y') ";
				   
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
	 
	 public ResultSet ldYearTarget(Map det){
			
		 	/////Notes here
		 
			String query="";
			ResultSet rs=null;
			
			try{
				 this.fetchProperties();
				 Statement st = this.connection.createStatement();
				  
				  query = ""
						  + "SELECT Ifnull(Sum(target_amount),0) as target_amount "
						  + "FROM   targets "
						  + "WHERE  target_group_id = 2  and department_id='1'"
						  + "       AND Date_format(target_date, '%Y-%m') <= Date_format(NOW() - "
						  + "                                                INTERVAL 1 day, '%Y-%m' "
						  + "                                                ) ";		   
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
	 
	 
	 public ResultSet ldYearTotalTarget(Map det){
			
		 	/////Notes here
		 
			String query="";
			ResultSet rs=null;
			
			try{
				 this.fetchProperties();
				 Statement st = this.connection.createStatement();
				  
				  query =    "SELECT  Ifnull(Sum(target_amount),0) as target_amount "
							 + "FROM   targets "
							 + "WHERE  target_group_id = 2 and department_id='1'"
							 + "       AND Date_format(target_date, '%Y') = Date_format(Now() - INTERVAL 1 day, "
							 + "                                            '%Y') ";	   
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
	 
	  
	 
	 
	 /*
	 
	 public ResultSet loadTerritory(){
			
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
						" territories order by territoryid asc";
					
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
	 
	 
	 
	 public Map fetchArea(int iAreaId){
			
			//privileges
			Map territoryDetails=new HashMap();
			String query="";
			 try{
					 
				 ResultSet rs=null;
				 this.fetchProperties();
				 Connection connection=null;
				 Class.forName("com.mysql.jdbc.Driver");
				 connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
				 
				  Statement st = connection.createStatement();
				  query="Select * from areas where areaid='"+iAreaId+"'";
				
				  rs = st.executeQuery(query);
				  
				  if (rs.next()) {  
					 do{
						 territoryDetails.put("areaid",rs.getString("areaid"));
						 territoryDetails.put("areaname",rs.getString("areaname"));
						 territoryDetails.put("territoryid",rs.getString("territoryid"));
						 territoryDetails.put("status",rs.getString("status"));
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
			
			return territoryDetails;
	} 
	
			*/
	 public ResultSet loadAreasFromTerritory(Map det){
         
         String query="";
         ResultSet rs=null;
         
         try{
                  this.fetchProperties();
                  Statement st = this.connection.createStatement();
                   query= "SELECT "+this.salesDB +".areas.areaname, "+this.salesDB+".areas.areaid "
                		 +"FROM "+this.salesDB+".areas, "+this.salesDB+".territories "
                		 +"WHERE "+this.salesDB+".territories.territoryid ="+det.get("territoryid")+" "
                		 +"AND "+this.salesDB+".areas.territoryid = "+this.salesDB+".territories.territoryid ";
                  
                   rs = st.executeQuery(query);
                  //st.close();
          } catch (SQLException e) {
                   System.err.println("SQLException: "
                             +e.getMessage());
               System.err.println("SQL Query: "+query);
          } catch (Exception e){
                                  System.out.println("Error in fetching"+e);
          }        
        
         return rs;
         
     }
public ResultSet loadAreas(Map det){
         
         String query="";
         ResultSet rs=null;
         
         try{
                  this.fetchProperties();
                  Statement st = this.connection.createStatement();
                   query= "SELECT "+this.salesDB +".areas.areaname, "+this.salesDB+".areas.areaid,"+this.salesDB+".territories.territoryid "
                		 +"FROM "+this.salesDB+".areas, "+this.salesDB+".territories "
                		 +"WHERE "+this.salesDB+".areas.territoryid = "+this.salesDB+".territories.territoryid ";
                  
                   rs = st.executeQuery(query);
                  //st.close();
          } catch (SQLException e) {
                   System.err.println("SQLException: "
                             +e.getMessage());
               System.err.println("SQL Query: "+query);
          } catch (Exception e){
                                  System.out.println("Error in fetching"+e);
          }        
        
         return rs;
         
     }
     public ResultSet loadAgentsFromAreas(Map det){
         
         String query="";
         ResultSet rs=null;
         
         try{
                  this.fetchProperties();
                  Statement st = this.connection.createStatement();
                   query= "SELECT "+this.salesDB+".agents.agentid, user.userid, "+this.salesDB+".areas.areaid,user.userfirstname,user.userlastname "
                		 +"FROM "+this.salesDB+".agents, "+this.salesDB+".agent_areas, "+this.salesDB+".areas, user "
                		 +"WHERE "+this.salesDB+".areas.areaid ="+det.get("areaid")+" "
                		 +"AND "+this.salesDB+".agent_areas.areaid = "+this.salesDB+".areas.areaid "
                		 +"AND "+this.salesDB+".agents.agentid = "+this.salesDB+".agent_areas.agentid "
                		 +"AND user.userid = "+this.salesDB+".agents.agentuserid "
                		 +"AND user.roleid =5";
                  
                   rs = st.executeQuery(query);
                  //st.close();
          } catch (SQLException e) {
                   System.err.println("SQLException: "
                             +e.getMessage());
               System.err.println("SQL Query: "+query);
          } catch (Exception e){
                                  System.out.println("Error in fetching"+e);
          }        
        
         return rs;
         
     }
     public ResultSet loadTeamLeaderFromAreas(Map det){
         
         String query="";
         ResultSet rs=null;
         
         try{
                  this.fetchProperties();
                  Statement st = this.connection.createStatement();
                   query= "SELECT "+this.salesDB+".agents.agentid, user.userid, "+this.salesDB+".areas.areaid,user.userfirstname,user.userlastname "
                		 +"FROM "+this.salesDB+".agents, "+this.salesDB+".agent_areas, "+this.salesDB+".areas, user "
                		 +"WHERE "+this.salesDB+".areas.areaid ="+det.get("areaid")+" "
                		 +"AND "+this.salesDB+".agent_areas.areaid = "+this.salesDB+".areas.areaid "
                		 +"AND "+this.salesDB+".agents.agentid = "+this.salesDB+".agent_areas.agentid "
                		 +"AND user.userid = "+this.salesDB+".agents.agentuserid "
                		 +"AND user.roleid =4";
                  
                   rs = st.executeQuery(query);
                  //st.close();
          } catch (SQLException e) {
                   System.err.println("SQLException: "
                             +e.getMessage());
               System.err.println("SQL Query: "+query);
          } catch (Exception e){
                                  System.out.println("Error in fetching"+e);
          }        
        
         return rs;
         
     }
     public ResultSet loadFLAreaId(Map det){
         
         String query="";
         ResultSet rs=null;
         
         try{
                  this.fetchProperties();
                  Statement st = this.connection.createStatement();
                   query= "SELECT (SELECT "+this.salesDB+".areas.areaid "
                		 +"FROM "+this.salesDB+".areas, "+this.salesDB+".territories "
                		 +"WHERE "+this.salesDB+".territories.territoryid ="+det.get("territoryid")+" "
                		 +"AND "+this.salesDB+".areas.territoryid = "+this.salesDB+".territories.territoryid "
                		 +"ORDER BY areaid ASC LIMIT 1) as first,"
                		 +"(SELECT "+this.salesDB+".areas.areaid "
                         +"FROM "+this.salesDB+".areas, "+this.salesDB+".territories "
                         +"WHERE "+this.salesDB+".territories.territoryid ="+det.get("territoryid")+" "
                         +"AND "+this.salesDB+".areas.territoryid = "+this.salesDB+".territories.territoryid "
                         +"ORDER BY areaid DESC LIMIT 1) as last";
                  
                   rs = st.executeQuery(query);
                  //st.close();
          } catch (SQLException e) {
                   System.err.println("SQLException: "
                             +e.getMessage());
               System.err.println("SQL Query: "+query);
          } catch (Exception e){
                                  System.out.println("Error in fetching"+e);
          }        
        
         return rs;
         
     }
     public ResultSet loadAreaSalesMTD(Map det){
         
         String query="";
         ResultSet rs=null;
         
         try{
                  this.fetchProperties();
                  Statement st = this.connection.createStatement();
                   query= "SELECT Ifnull(SUM("+this.salesDB+".sales_order.totalamountpaid),0) as totalsales "
                		  +" FROM "+this.salesDB+".sales_order, "+this.salesDB+".agents, "+this.salesDB+".agent_areas, "+this.salesDB+".areas "
                		  +" WHERE "+this.salesDB+".areas.areaid = '"+det.get("areaid")+"' "
                		  +" AND "+this.salesDB+".agent_areas.areaid = "+this.salesDB+".areas.areaid "
                		  +" AND "+this.salesDB+".agents.agentid = "+this.salesDB+".agent_areas.agentid "
                		  +" AND "+this.salesDB+".sales_order.agentid = "+this.salesDB+".agents.agentid "
                		  +" AND YEAR("+this.salesDB+".sales_order.salesinvoicecreateddate) = YEAR(NOW()) "
                		  +" AND MONTH("+this.salesDB+".sales_order.salesinvoicecreateddate) = MONTH(NOW())"
                		  +" AND DAY("+this.salesDB+".sales_order.salesinvoicecreateddate) < DAY(NOW())";
                  
                   rs = st.executeQuery(query);
                  //st.close();
          } catch (SQLException e) {
                   System.err.println("SQLException: "
                             +e.getMessage());
               System.err.println("SQL Query: "+query);
          } catch (Exception e){
                                  System.out.println("Error in fetching"+e);
          }        
        
         return rs;
         
     }
     public ResultSet loadAreaSalesYTD(Map det){
         
         String query="";
         ResultSet rs=null;
         
         try{
                  this.fetchProperties();
                  Statement st = this.connection.createStatement();
                   query= "SELECT Ifnull(SUM("+this.salesDB+".sales_order.totalamountpaid),0) as totalsales "
                		  +" FROM "+this.salesDB+".sales_order,"+this.salesDB+".agents, "+this.salesDB+".agent_areas, "+this.salesDB+".areas "
                		  +" WHERE "+this.salesDB+".areas.areaid= '"+det.get("areaid")+"' "
                		  +" AND "+this.salesDB+".agent_areas.areaid = "+this.salesDB+".areas.areaid "
                		  +" AND "+this.salesDB+".agents.agentid = "+this.salesDB+".agent_areas.agentid "
                		  +" AND "+this.salesDB+".sales_order.agentid = "+this.salesDB+".agents.agentid "
                		  +" AND YEAR("+this.salesDB+".sales_order.salesinvoicecreateddate) = YEAR(NOW()) "
                		  +" AND DATE_FORMAT("+this.salesDB+".sales_order.salesinvoicecreateddate,'%m-%d') < DATE_FORMAT(NOW(),'%m-%d')";                     
                   rs = st.executeQuery(query);
                  //st.close();
          } catch (SQLException e) {
                   System.err.println("SQLException: "
                             +e.getMessage());
               System.err.println("SQL Query: "+query);
          } catch (Exception e){
                                  System.out.println("Error in fetching"+e);
          }        
        
         return rs;
         
     }
     public ResultSet loadAreaTargetMTD(Map det){
         
         String query="";
         ResultSet rs=null;
         
         try{
                  this.fetchProperties();
                  Statement st = this.connection.createStatement();
                   query= "SELECT Ifnull(SUM(target_amount),0) as target_amount, MONTH(target_date) as month "
                		  +"FROM targets, user, "+this.salesDB+".areas, "+this.salesDB+".agent_areas, "+this.salesDB+".agents "
                		  +"WHERE "+this.salesDB+".areas.areaid ='"+det.get("areaid")+"' "
                		  +"AND "+this.salesDB+".agent_areas.areaid = "+this.salesDB+".areas.areaid "
                		  +"AND "+this.salesDB+".agents.agentid = "+this.salesDB+".agent_areas.agentid "
                		  +"AND user.userid = "+this.salesDB+".agents.agentuserid "
                		  +"AND targets.user_id = user.userid "
                		  +"AND targets.department_id = 2 "
                		  +"AND YEAR(target_date) = YEAR(NOW()) "
                		  +"AND MONTH(target_date) = MONTH(NOW()) " ;
                  
                   rs = st.executeQuery(query);
                  //st.close();
          } catch (SQLException e) {
                   System.err.println("SQLException: "
                             +e.getMessage());
               System.err.println("SQL Query: "+query);
          } catch (Exception e){
                                  System.out.println("Error in fetching"+e);
          }        
        
         return rs;
         
     }
     public ResultSet loadAreaTargetYTD(Map det){
         
         String query="";
         ResultSet rs=null;
         
         try{
                  this.fetchProperties();
                  Statement st = this.connection.createStatement();
                   query= "SELECT Ifnull(SUM(target_amount),0) as target_amount, MONTH(target_date) as month "
                		  +"FROM targets, user, "+this.salesDB+".areas, "+this.salesDB+".agent_areas, "+this.salesDB+".agents "
                		  +"WHERE "+this.salesDB+".areas.areaid ='"+det.get("areaid")+"' "
                		  +"AND "+this.salesDB+".agent_areas.areaid = "+this.salesDB+".areas.areaid "
                		  +"AND "+this.salesDB+".agents.agentid = "+this.salesDB+".agent_areas.agentid "
                		  +"AND user.userid = "+this.salesDB+".agents.agentuserid "
                		  +"AND targets.user_id = user.userid "
                		  +"AND targets.department_id = 2 "
                		  +"AND YEAR(target_date) = YEAR(NOW())";
                  
                   rs = st.executeQuery(query);
                  //st.close();
          } catch (SQLException e) {
                   System.err.println("SQLException: "
                             +e.getMessage());
               System.err.println("SQL Query: "+query);
          } catch (Exception e){
                                  System.out.println("Error in fetching"+e);
          }        
        
         return rs;
         
     }
     public ResultSet loadAreaMonthlySales(Map det,Boolean byYear){
			
			String query="";
			ResultSet rs=null;
			
			try{
				 this.fetchProperties();
				 Statement st = this.connection.createStatement();
				 query =  " SELECT Ifnull(SUM("+this.salesDB+".sales_order.totalamountpaid),0) as totalpayment, "
						  +" YEAR("+this.salesDB+".sales_order.salesinvoicecreateddate) as year, MONTH("+this.salesDB+".sales_order.salesinvoicecreateddate) as month  "
						  +" FROM "+this.salesDB+".sales_order,"+this.salesDB+".agents ,"+this.salesDB+".agent_areas "
						  +" WHERE "+this.salesDB+".agent_areas.areaid='"+det.get("areaid")+"'"
						  +" AND "+this.salesDB+".agents.agentid = "+this.salesDB+".agent_areas.agentid"
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

	 
}
