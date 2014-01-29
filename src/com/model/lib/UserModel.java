package com.model.lib;

import com.misc.lib.CustomHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

import javax.servlet.http.HttpSession;

public class UserModel{
        
        //For Database Connection
         private String connectionURL = "";
         private String dbUser="";
         private String dbPassword="";
         public String projectFile="";
         public String salesDB="";
         public String dashboardDB="";
         public Connection connection=null;
                
         public void fetchProperties() throws IOException,SQLException, ClassNotFoundException{
                 
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
          
         
        public Map fetchUser(String iUserId){
                
                Map usrDetails=new HashMap();
                String query="";
                 try{
                                
                         ResultSet rs=null;
                         this.fetchProperties();
                         Statement st = this.connection.createStatement();
                          query="Select * from user where userid='"+iUserId+"'";
                          
                          rs = st.executeQuery(query);
                          
                          if (rs.next()) {  
                                 do{
                                         
                                         usrDetails.put("userid",rs.getInt("userid"));
                                         usrDetails.put("roleid",rs.getInt("roleid"));
                                         usrDetails.put("userfirstname",rs.getString("userfirstname"));
                                         usrDetails.put("userlastname",rs.getString("userlastname"));
                                         usrDetails.put("username",rs.getString("username"));
                                         usrDetails.put("status",rs.getString("status"));
                                         usrDetails.put("email_address",rs.getString("email_address"));
                                         usrDetails.put("mobile",rs.getString("mobile"));
                                         usrDetails.put("hireddate",rs.getString("hireddate"));
                                         usrDetails.put("birthdate",rs.getString("birthdate"));
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
        
        public Map fetchRole(int iRoleId){
                
                Map usrDetails=new HashMap();
                String query="";
                 try{
                                
                         ResultSet rs=null;
                         this.fetchProperties();
                         Statement st = this.connection.createStatement();
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


        public Map processLogin(String username,String password) {
                        
                 String query="";
                 int rowSize = 0;
                 Map usrDetails=new HashMap();
                 CustomHelper ch=new CustomHelper(); 

                 try{
                        
                         ResultSet rs=null;
                         this.fetchProperties();
                         Statement st = this.connection.createStatement();
                          //query="Select * from user where username='"+username+"' and password='"+ch.generateHash(password)+"'";
                          query="SELECT"+
                                        " roles.rolename,"+
                                        " `user`.userid,"+
                                        " `user`.userfirstname,"+
                                        " `user`.userlastname,"+
                                        " `user`.username,"+
                                        " `user`.`password`,"+
                                        " `user`.`status`,"+
                                        " `user`.email_address,"+
                                        " `user`.mobile,"+
                                        " `user`.roleid,"+
                                        " `user`.createdby,"+
                                        " `user`.createddate"+
                                        " FROM "+
                                        " `user` "+
                                        " Inner Join roles ON `user`.roleid = roles.roleid "+
                                        " where username='"+username+"' and password='"+ch.generateHash(password)+"'";
                          rs = st.executeQuery(query);
                          
                        
                          
                          if (rs.next()) {  
                                 do{
                                         DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                         usrDetails.put("count",rs.getFetchSize());
                                         usrDetails.put("status","");
                                         usrDetails.put("userid",rs.getString("userid"));
                                         usrDetails.put("username",rs.getString("username"));
                                         usrDetails.put("roleid",rs.getString("roleid"));
                                         usrDetails.put("rolename",rs.getString("rolename"));
                                         usrDetails.put("logged",true);
                                         String userHash=rs.getString("userid")+rs.getString("username")+dateFormat;
                                         usrDetails.put("loggedhash",ch.generateHash(userHash));
                                         
                                         
                                 }while(rs.next());
                                  
                          }else{
                                          usrDetails.put("count",rs.getFetchSize());
                                          usrDetails.put("status","Check Username and Password");
                                          usrDetails.put("logged",false);
                          }
                        
                          rs.close();
                          st.close();
                      
                 } catch (SQLException e) {
                          System.err.println("SQLException: "
                                    +e.getMessage());
                      System.err.println("SQL Query: "+query);
                 
                 } catch (Exception e){
                                         System.out.println("Error in fetching"+e);
                 }
                         
                 return usrDetails;
                
                }//end of function

                public ResultSet loadUser(Map det){
                        
                        String query="";
                        ResultSet rs=null;
                        
                        try{
                                 this.fetchProperties();
                                 Statement st = this.connection.createStatement();
                                  query="SELECT"+
                                                " `user`.userid,"+
                                                " `user`.userfirstname,"+
                                                " `user`.userlastname,"+
                                                " roles.rolename"+
                                                " FROM"+
                                                " `user`"+
                                                " Inner Join roles ON `user`.roleid = roles.roleid" +
                                                " where 1=1 ";
                                                  
                                  if(det.get("userid") != null && det.get("userid") != ""){
                                          query+=" and `user`.userid='"+det.get("userid")+"'";
                                  }
                                  
                                  if(det.get("roleid") != null && det.get("roleid") != ""){
                                          query+="and roles.roleid='"+det.get("roleid")+"'";
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
                
                
                public ResultSet loadRole(){
                        
                        String query="";
                        ResultSet rs=null;
                        
                        try{
                                 this.fetchProperties();
                                 Statement st = this.connection.createStatement();
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
                
                public Boolean InsertUser(Map det, HttpSession sess){
                                        
                        
                                String query="";
                                Boolean process=false;
                        try{
                                 this.fetchProperties();
                                 CustomHelper ch=new CustomHelper();
                                 Statement st = this.connection.createStatement();
                                   query="INSERT INTO user("+
                                                                "userfirstname,"+
                                                                "userlastname,"+
                                                                "username,"+
                                                                "password,"+
                                                                "status,"+
                                                                "email_address,"+
                                                                "mobile,"+
                                                                "roleid,"+
                                                                "createdby,"+
                                                                "createddate," +
                                                                "hireddate," +
                                                                "birthdate)" +
                                                                "VALUES(" +
                                                                "'"+det.get("userfirstname")+"'," +
                                                                "'"+det.get("userlastname")+"'," +
                                                                "'"+det.get("username")+"'," +
                                                                "'"+ch.generateHash((String)det.get("password"))+"'," +
                                                                "'"+det.get("status")+"'," +
                                                                "'"+det.get("email_address")+"'," +
                                                                "'"+det.get("mobile")+"'," +
                                                                "'"+det.get("roleid")+"'," +
                                                                "'"+sess.getAttribute("userid")+"'," +
                                                                "'"+ch.loadDateNow()+"'," +
                                                                "'"+det.get("hireddate")+"'," +
                                                                "'"+det.get("birthdate")+"')";
                                        
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
                
                public Boolean UpdateUser(Map det, HttpSession sess){
                        
                        
                        String query="";
                        Boolean process=false;
                try{
                         this.fetchProperties();
                         CustomHelper ch=new CustomHelper();
                         Statement st = this.connection.createStatement();
                           
                           query="UPDATE user set " +
                                           "userfirstname='" +det.get("userfirstname")+"',"+
                                           "userlastname='" +det.get("userlastname")+"',"+
                                           "username='" +det.get("username")+"',"+
                                           "status='" +det.get("status")+"',"+
                                           "email_address='" +det.get("email_address")+"',"+
                                           "mobile='" +det.get("mobile")+"',"+
                                           "roleid='" +det.get("roleid")+"',"+
                                           "hireddate='" +det.get("hireddate")+"',"+
                                           "birthdate='" +det.get("birthdate")+"'";
                           
                                           if(det.get("password")!=null && det.get("password")!=""){
                                                   query+=",password='"+ch.generateHash((String)det.get("password"))+"'";
                                           }
                                           
                                           query+=" where userid='"+det.get("userid")+"'";        
                                           
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
                
                
public Boolean DeletetUser(String userid){
                                        
                                String query="";
                                Boolean process=false;
                        try{
                                 this.fetchProperties();
                                 CustomHelper ch=new CustomHelper();
                                 Statement st = this.connection.createStatement();
                                  query="DELETE FROM user WHERE userid='"+userid+"'";
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




public ResultSet loadBridgeUserAgent(){
        
        String query="";
        ResultSet rs=null;
        
        try{
                 this.fetchProperties();
                 Statement st = this.connection.createStatement();
                  
                  query="SELECT "+this.salesDB+".`user`.userid AS salesuserid, "
                                  + "   "+this.dashboardDB+".`user`.userid, "
                                  + "       "+this.salesDB+".agents.agentid, "
                                  + "       "+this.dashboardDB+".`user`.userfirstname, "
                                  + "       "+this.dashboardDB+".`user`.email_address "
                                  + "FROM   "+this.dashboardDB+".`user` "
                                  + "       INNER JOIN "+this.salesDB+".`user` "
                                  + "               ON "+this.dashboardDB+".`user`.email_address = "
                                  + "                  "+this.salesDB+".`user`.email_address "
                                  + "       INNER JOIN "+this.salesDB+".agents "
                                  + "               ON "+this.salesDB+".`user`.userid = "+this.salesDB+".agents.agentuserid ";
                                  
                   
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



public Boolean InsertBridgeAgent(Map det){
        
        String query="";
        Boolean process=false;
try{
         this.fetchProperties();
         CustomHelper ch=new CustomHelper();
         Statement st = this.connection.createStatement();
          query="INSERT IGNORE INTO salesuser_bridge (userid,agentid,salesuserid)" +
                          "VALUES('"+det.get("userid")+"','"+det.get("agentid")+"','"+det.get("salesuserid")+"')";
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

        public ResultSet loadSalesUser(Map det){
                
                String query="";
                ResultSet rs=null;
                
                try{
                         this.fetchProperties();
                         Statement st = this.connection.createStatement();
                         query= "SELECT " +
                                          " `user`.userid, " +
                                          " `user`.userfirstname, " +
                                          " `user`.`status`, " +
                                          " `user`.email_address, " +
                                          " `user`.mobile, " +
                                          " `user`.roleid, " +
                                          " `user`.createdby, " +
                                          " `user`.createddate, " +
                                          " `user`.userlastname, " +
                                          " `user`.birthdate, " +
                                          " `user`.hireddate, " +
                                          " `roles`.rolename, " +
                                          " roles.rolename, " +
                                          " salesuser_bridge.userid, " +
                                          " salesuser_bridge.agentid " +
                                          " FROM " +
                                          " salesuser_bridge " +
                                          " Right Join `user` ON `user`.userid = salesuser_bridge.userid " +
                                          " Inner Join roles ON `user`.roleid = roles.roleid "+ 
                                          " where 1=1 ";
                          
                          if(det.get("roleid") != null && det.get("roleid") != ""){
                                  query+=" and `user`.roleid='"+det.get("roleid")+"'";
                                
                          }else{
                                  query+="and  (`user`.roleid='2' or  `user`.roleid='3' or `user`.roleid='4' or `user`.roleid='5' )";
                          }
                          
                          if(det.get("userid") != null && det.get("userid") != ""){
                                  query+=" and `user`.userid = '"+det.get("userid")+"'";
                          }
                         
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
        public ResultSet loadSalesTeam(Map det){
            
            String query="";
            ResultSet rs=null;
            
            try{
                     this.fetchProperties();
                     Statement st = this.connection.createStatement();
                     query= "SELECT " +
                                      " `user`.userid, " +
                                      " `user`.userfirstname, " +
                                      " `user`.`status`, " +
                                      " `user`.email_address, " +
                                      " `user`.mobile, " +
                                      " `user`.roleid, " +
                                      " `user`.createdby, " +
                                      " `user`.createddate, " +
                                      " `user`.userlastname, " +
                                      " `user`.birthdate, " +
                                      " `user`.hireddate, " +
                                      " `roles`.rolename, " +
                                      " roles.rolename, " +
                                      " salesuser_bridge.userid, " +
                                      " salesuser_bridge.agentid " +
                                      " FROM " +
                                      " salesuser_bridge " +
                                      " Right Join `user` ON `user`.userid = salesuser_bridge.userid " +
                                      " Inner Join roles ON `user`.roleid = roles.roleid "+ 
                                      " where 1=1 ";
                      
                      if(det.get("roleid") != null && det.get("roleid") != ""){
                              query+=" and `user`.roleid='"+det.get("roleid")+"'";
                            
                      }else{
                              query+="and  ( `user`.roleid='4' or `user`.roleid='5' )";
                      }
                      
                      if(det.get("userid") != null && det.get("userid") != ""){
                              query+=" and `user`.userid = '"+det.get("userid")+"'";
                      }
                     
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
        public ResultSet loadSalesTeamFromArea(Map det){
            
            String query="";
            ResultSet rs=null;
            
            try{
                     this.fetchProperties();
                     Statement st = this.connection.createStatement();
                     query= "SELECT " +
                                      " `user`.userid, " +
                                      " `user`.userfirstname, " +
                                      " `user`.`status`, " +
                                      " `user`.email_address, " +
                                      " `user`.mobile, " +
                                      " `user`.roleid, " +
                                      " `user`.createdby, " +
                                      " `user`.createddate, " +
                                      " `user`.userlastname, " +
                                      " `user`.birthdate, " +
                                      " `user`.hireddate, " +
                                      " `roles`.rolename, " +
                                      " roles.rolename, " +
                                      " salesuser_bridge.userid, " +
                                      " salesuser_bridge.agentid " +
                                      " FROM " +
                                      " salesuser_bridge " +
                                      " Right Join `user` ON `user`.userid = salesuser_bridge.userid " +
                                      " Inner Join roles ON `user`.roleid = roles.roleid "+ 
                                      " where 1=1 ";
                      
                      if(det.get("roleid") != null && det.get("roleid") != ""){
                              query+=" and `user`.roleid='"+det.get("roleid")+"'";
                            
                      }else{
                              query+="and  ( `user`.roleid='4' or `user`.roleid='5' )";
                      }
                      
                      if(det.get("userid") != null && det.get("userid") != ""){
                              query+=" and `user`.userid = '"+det.get("userid")+"'";
                      }
                     
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

        
}
