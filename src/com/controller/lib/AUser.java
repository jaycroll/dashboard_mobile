package com.controller.lib;

import com.misc.lib.CustomHelper;
import com.misc.lib.RolePermission;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;



import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model.lib.*;

import org.json.simple.JSONValue;

/**
 * Servlet implementation class AUser
 */
@WebServlet("/AUser")
public class AUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		RequestDispatcher view=null;
		Boolean useDispatcher=false;
		
		Map iVariable=new HashMap();
		
		String action	="";
		if(!request.getParameter("action").isEmpty()){
			action=request.getParameter("action");
		}
		
		UserModel usr=new UserModel();
		CustomHelper ch=new CustomHelper();
		usr.projectFile=getServletContext().getRealPath("/");
		
		RolePermission rp=new RolePermission();
		rp.settingUrl=getServletContext().getRealPath("/");
		
		//System.out.println(action);
		
		HttpSession sess=request.getSession();
		        
		if(action.equals("login")){
				String username	= request.getParameter("username");
				String password	= request.getParameter("password");
				Map loginState=new HashMap();
				loginState=usr.processLogin(username,password);
				
				//Load Session
				if((Boolean) loginState.get("logged")!=null && (Boolean) loginState.get("logged")){
					 sess.setAttribute("userid",loginState.get("userid"));
					 sess.setAttribute("loggedhash",loginState.get("loggedhash"));
					 sess.setAttribute("username",loginState.get("username"));
					 sess.setAttribute("rolename",loginState.get("rolename"));
					 sess.setAttribute("roleid",loginState.get("roleid"));
					 sess.setAttribute("logged",true);
				}
				//application/json
				response.setContentType("text/html");
				PrintWriter pWrite = response.getWriter();
				pWrite.println(JSONValue.toJSONString(loginState));
			 	
				
				
		}else if(action.equals("chkLogged")){
			
			Map loggedState=new HashMap();
			if(ch.checkMemberSession(sess)){
				loggedState.put("logged",true);
			}else{
				loggedState.put("logged",false);	
			}
	    	
			//application/json
			response.setContentType("text/html");
			PrintWriter pWrite = response.getWriter();
			pWrite.println(JSONValue.toJSONString(loggedState));
		
		}else if(action.equals("loadUser")){
			
				Map det=new HashMap();
				
				
				if(rp.verifyModule(Integer.parseInt(sess.getAttribute("roleid").toString()),1,6)){	
					ResultSet usrList=usr.loadUser(det);
					request.setAttribute("usrList",usrList);
				}
				
				
				
				ResultSet usrListSpec=usr.loadUser(det);
				
				ResultSet urlRole=usr.loadRole();
			
			 	request.setAttribute("urlRole",urlRole);
			 	request.setAttribute("usrListSpec",usrListSpec);
			 	
			 	view = request.getRequestDispatcher("user/sub_UserList.jsp");
			 	
			 	response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
				response.setHeader("Pragma", "no-cache");
				view.forward(request, response);
				
		
		}else if(action.equals("loadRole")){
			
			Map det=new HashMap();
			ResultSet usrList=usr.loadUser(det);
			ResultSet usrListSpec=usr.loadUser(det);
			
			if(rp.verifyModule(Integer.parseInt(sess.getAttribute("roleid").toString()),2,6)){	
			ResultSet urlRole=usr.loadRole();
		 	request.setAttribute("roleList",urlRole);
			}
		 	
		 	view = request.getRequestDispatcher("role/sub_RoleList.jsp");

		 	response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			view.forward(request, response);
			
	
		}else if(action.equals("loadPrivilege")){
		
			Map det=new HashMap();
			
			PrivilegeModel prv=new PrivilegeModel();
			prv.projectFile=getServletContext().getRealPath("");
			
			ResultSet prvList=prv.loadPrivilege();
			
			
			request.setAttribute("prvList",prvList);
		 	view = request.getRequestDispatcher("privilege/sub_PrivilegeList.jsp");
	
		 	response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			view.forward(request, response);
		

		}else if(action.equals("subLoadUser")){
		
			String userid	= request.getParameter("userid");
			String roleid	= request.getParameter("roleid");
			Map det=new HashMap();
		
			det.put("userid",userid);
			det.put("roleid",roleid);
		
			
			ResultSet usrList=null;	
		if(rp.verifyModule(Integer.parseInt(sess.getAttribute("roleid").toString()),1,6)){	
			usrList=usr.loadUser(det);
		}else{
			
			usrList=null;
		}
	 	request.setAttribute("usrList",usrList);
	 	view = request.getRequestDispatcher("user/result_UserList.jsp");
	 	
	 	response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		view.forward(request, response);
		
		
		}else if(action.equals("processAddAccess")){
			
			int errorCounter=0;
			
			
			if(ch.checkMemberSession(sess)){
				iVariable.put("logged",true);	
				
				String userfirstname	= request.getParameter("userfirstname");
				String userlastname	= request.getParameter("userlastname");
				String username	= request.getParameter("username");
				String password	= request.getParameter("password");
				String password2	= request.getParameter("password2");
				String status	= request.getParameter("status");
				String email_address	= request.getParameter("email_address");
				String mobile	= request.getParameter("mobile");
				String roleid	= request.getParameter("roleid");
				
				
				String bMonth	= request.getParameter("bMonth");
				String bDate	= request.getParameter("bDate");
				String bYear	= request.getParameter("bYear");
				String birthdate= bYear+"-"+bMonth+"-"+bDate;
				
				String hMonth	= request.getParameter("hMonth");
				String hDate	= request.getParameter("hDate");
				String hYear	= request.getParameter("hYear");
				String hireddate= hYear+"-"+hMonth+"-"+hDate;
				
				
				if(userfirstname==null || userfirstname==""){
					iVariable.put("iError_userfirstname","required");
					errorCounter=+1;
				}else{
					iVariable.put("iError_userfirstname","");
				}
				
				
				if(userlastname==null || userlastname==""){
					iVariable.put("iError_userlastname","required");
					errorCounter=+1;
				}else{
					iVariable.put("iError_userlastname","");
				}
				
				
				if(username==null || username==""){
					iVariable.put("iError_username","required");
					errorCounter=+1;
				}else{
					iVariable.put("iError_username","");
				}
				
				
				if(password==null || password==""){
					iVariable.put("iError_password","required");
					errorCounter=+1;
					
				}else{
					
					if(password.equals(password2)){
						iVariable.put("iError_password","");
					}else{
						iVariable.put("iError_password","password does not match");
						errorCounter=+1;	
					}
				}
				
				
				if(password2==null || password2==""){
					iVariable.put("iError_password2","required");
					errorCounter=+1;
				}else{
					iVariable.put("iError_password2","");
				}
				
				if(email_address==null || email_address==""){
					iVariable.put("iError_email_address","required");
					errorCounter=+1;
				}else{
					iVariable.put("iError_email_address","");
				}
				
				if(mobile==null || mobile==""){
					iVariable.put("iError_mobile","required");
					errorCounter=+1;
				}else{
					iVariable.put("iError_mobile","");
				}
				
				
				
				if(errorCounter==0){
					iVariable.put("process",true);
					
					//iVariable.put("process",false);
					Map det=new HashMap();
					det.put("userfirstname",userfirstname);
					det.put("userlastname",userlastname);
					det.put("username",username);
					det.put("password",password);
					det.put("status",status);
					det.put("email_address",email_address);
					det.put("mobile",mobile);
					det.put("roleid",roleid);
					det.put("birthdate",birthdate);
					det.put("hireddate",hireddate);
					
					//Process Insert
					if(usr.InsertUser(det,sess)){
						iVariable.put("process",true);
					}else{
						iVariable.put("process",false);
					}
			
				}else{
					iVariable.put("process",false);
				}
				
			}else{
				iVariable.put("logged",false);	
			}
			
			//application/json
			response.setContentType("text/html");
			PrintWriter pWrite = response.getWriter();
			pWrite.println(JSONValue.toJSONString(iVariable));
			
		}else if(action.equals("processEditAccess")){
		
		
		int errorCounter=0;
		
		
		if(ch.checkMemberSession(sess)){
			iVariable.put("logged",true);	
			
			String userfirstname	= request.getParameter("userfirstname");
			//System.oou
			String userlastname	= request.getParameter("userlastname");
			String username	= request.getParameter("username");
			String password	= request.getParameter("password");
			String password2	= request.getParameter("password2");
			String status	= request.getParameter("status");
			String email_address	= request.getParameter("email_address");
			String mobile	= request.getParameter("mobile");
			String roleid	= request.getParameter("roleid");
			String userid	= request.getParameter("userid");
			
			String bMonth	= request.getParameter("bMonth");
			String bDate	= request.getParameter("bDate");
			String bYear	= request.getParameter("bYear");
			String birthdate= bYear+"-"+bMonth+"-"+bDate;
			
			
			String hMonth	= request.getParameter("hMonth");
			String hDate	= request.getParameter("hDate");
			String hYear	= request.getParameter("hYear");
			String hireddate= hYear+"-"+hMonth+"-"+hDate;
			
			
			if(userfirstname==null || userfirstname==""){
				iVariable.put("iError_userfirstname","required");
				errorCounter=+1;
			}else{
				iVariable.put("iError_userfirstname","");
			}
			
			
			if(userlastname==null || userlastname==""){
				iVariable.put("iError_userlastname","required");
				errorCounter=+1;
			}else{
				iVariable.put("iError_userlastname","");
			}
			
			
			if(username==null || username==""){
				iVariable.put("iError_username","required");
				errorCounter=+1;
			}else{
				iVariable.put("iError_username","");
			}
			
			
			if(password==null || password==""){
				//iVariable.put("iError_password","required");
				//errorCounter=+1;
				
			}else{
				
				if(password.equals(password2)){
					iVariable.put("iError_password","");
				}else{
					iVariable.put("iError_password","password does not match");
					errorCounter=+1;	
				}
			}
			
			
			if(password2==null || password2==""){
				//iVariable.put("iError_password2","required");
				//errorCounter=+1;
			}else{
				iVariable.put("iError_password2","");
			}
			
			if(email_address==null || email_address==""){
				iVariable.put("iError_email_address","required");
				errorCounter=+1;
			}else{
				iVariable.put("iError_email_address","");
			}
			
			if(mobile==null || mobile==""){
				iVariable.put("iError_mobile","required");
				errorCounter=+1;
			}else{
				iVariable.put("iError_mobile","");
			}
			
			
			
			if(errorCounter==0){
				iVariable.put("process",true);
				
				//iVariable.put("process",false);
				Map det=new HashMap();
				det.put("userfirstname",userfirstname);
				det.put("userlastname",userlastname);
				det.put("username",username);
				det.put("password",password);
				det.put("status",status);
				det.put("email_address",email_address);
				det.put("mobile",mobile);
				det.put("roleid",roleid);
				det.put("userid",userid);
				det.put("birthdate",birthdate);
				det.put("hireddate",hireddate);
				
				//Process Insert
				if(usr.UpdateUser(det,sess)){
					iVariable.put("process",true);
				}else{
					iVariable.put("process",false);
				}
		
			}else{
				iVariable.put("process",false);
			}
			
		}else{
			iVariable.put("logged",false);	
		}
		
		//application/json
		response.setContentType("text/html");
		PrintWriter pWrite = response.getWriter();
		pWrite.println(JSONValue.toJSONString(iVariable));
		
	}else if(action.equals("initDelete")){
			
				String userid	= request.getParameter("userid");
				Map thisUser=usr.fetchUser(userid);
				
				int roleid=(Integer) thisUser.get("roleid");
				Map thisRole=usr.fetchRole(roleid);
				
				request.setAttribute("userid",thisUser.get("userid"));
				request.setAttribute("fullname",thisUser.get("userfirstname")+" "+thisUser.get("userlastname"));
				request.setAttribute("rolename",thisRole.get("rolename"));
			 	view = request.getRequestDispatcher("user/initDelete.jsp");
			 	
			 	response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
				response.setHeader("Pragma", "no-cache");
				view.forward(request, response);
		}else if(action.equals("processDelete")){
			
			
			String userid	= request.getParameter("userid");
			
			if(usr.DeletetUser(userid)){
				iVariable.put("process",true);
				iVariable.put("status","User has been deleted");
			}else{
				iVariable.put("process",false);
				iVariable.put("status","Error:User has not been deleted");
			}
			
			//application/json
			response.setContentType("text/html");
			PrintWriter pWrite = response.getWriter();
			pWrite.println(JSONValue.toJSONString(iVariable));	
			
		}
		
	}//end of function
	
	
	
	

}//end of class
