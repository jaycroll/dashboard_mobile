package com.model.lib;

import java.util.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

public class AreaModel{
        
        //For Database Connection
         private String connectionURL = "";
         private String dbUser="";
         private String dbPassword="";
         public String projectFile="";
         public String salesDB="";
         public String dashboardDB="";
         public Connection connection=null;
                
         public void fetchProperties() throws IOException, SQLException, ClassNotFoundException{
                 
                 String relativeWebPath = "/WEB-INF/app.properties";
                 Properties p = new Properties();
                 p.load(new FileInputStream(projectFile+relativeWebPath));
                 
                 this.connectionURL=p.getProperty("connectionURL");
                 this.dbUser=p.getProperty("dbUser");
                 this.dbPassword=p.getProperty("dbPassword");
                 this.salesDB=p.getProperty("salesDB");
                 this.dashboardDB=p.getProperty("dashboardDB");
                 Class.forName("com.mysql.jdbc.Driver");
                 this.connection = DriverManager.getConnection(this.connectionURL,this.dbUser,this.dbPassword);
         }
         
         public ResultSet loadTerritory(){
             
             String query="";
             ResultSet rs=null;
             
             try{
                      this.fetchProperties();
                       Statement st = this.connection.createStatement();
                       query= "SELECT "+this.salesDB+".territories.territoryname, "+this.salesDB+".territories.territoryid "
                    		 +"FROM "+this.salesDB+".territories "
                    		 +"WHERE "+this.salesDB+".territories.territoryid !='' ";
                     // System.out.println(query);
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
