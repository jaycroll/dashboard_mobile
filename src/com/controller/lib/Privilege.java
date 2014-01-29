package com.controller.lib;

import com.misc.lib.CustomHelper;

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
 * Servlet implementation class ARole
 */
@WebServlet(description = "Privilege servlet", urlPatterns = { "/Privilege" })
public class Privilege extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Privilege() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action	= request.getParameter("action");
		HttpSession sess=request.getSession();
		CustomHelper ch=new CustomHelper();
		
		UserModel usr=new UserModel();
		usr.projectFile=getServletContext().getRealPath("");
		
		RequestDispatcher view=null;
		Boolean useDispatcher=false;
		 
		if(ch.checkMemberSession(sess)){
			
			if(action==null){
				
				Map det=new HashMap();
				
				PrivilegeModel prv=new PrivilegeModel();
				prv.projectFile=getServletContext().getRealPath("");			
				ResultSet prvList=prv.loadPrivilege();						
				request.setAttribute("prvList",prvList);
			 	response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
				response.setHeader("Pragma", "no-cache");				 
				 request.setAttribute("banner","dashboard");			 
				 view = request.getRequestDispatcher("privilege/result_PrivilegeList.jsp");
				 useDispatcher=true;
				 view.forward(request, response);
			}
			else if(action.equals("logout")){
				
				 useDispatcher=false;
				 //Destroy Session
				 sess.setAttribute("userid",null);
				 sess.setAttribute("loggedhash",null);
				 sess.setAttribute("username",null);
				 sess.setAttribute("logged",null);
				 response.sendRedirect("/Dashboard");
			
			}
			
			
		
			if(useDispatcher){	
				response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
				response.setHeader("Pragma", "no-cache");
				view.forward(request, response); 
			}
		}
		else{
			response.sendRedirect("/Dashboard");
		}
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
		
		String action	= request.getParameter("action");
		PrivilegeModel prv=new PrivilegeModel();
		CustomHelper ch=new CustomHelper();
		prv.projectFile=getServletContext().getRealPath("");
		
		
		HttpSession sess=request.getSession();
		
		
	if(action.equals("initAdd")){
			
			
			view = request.getRequestDispatcher("privilege/initAddPrivilege.jsp");
		 	response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			view.forward(request, response);
	
	}else if(action.equals("processAdd")){
		
		String privilege	= request.getParameter("privilege");
		String privilegeid	= request.getParameter("privilegeid");
		int errorCounter=0;
		
		if(ch.checkMemberSession(sess)){
			iVariable.put("logged",true);	
			
			
			if(privilegeid==null || privilegeid==""){
				iVariable.put("status","Please fill up Privilege ID");
				errorCounter=+1;
			}
			
			
			if(privilege==null || privilege==""){
				iVariable.put("status","Please fill up Privilege");
				errorCounter=+1;
			}
			
			
			if(errorCounter==0){
				iVariable.put("process",true);
				
				//iVariable.put("process",false);
				Map det=new HashMap();
				det.put("privilegeid",privilegeid);
				det.put("privilege",privilege);
				
				//Process Insert
				
				if(prv.InsertPrivilege(det)){
					
					//Also Add the default Privilege
					////////////////////////////////////////
						
					iVariable.put("process",true);
					iVariable.put("status","New privilege has been added");
					
				}else{
					iVariable.put("process",false);
					iVariable.put("status","Error on adding privilege");
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
		
				
	}else if(action.equals("subLoadPrivilege")){
		
		ResultSet prvList=prv.loadPrivilege();
		request.setAttribute("prvList",prvList);
		view = request.getRequestDispatcher("privilege/main.jsp");
 	
		response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		view.forward(request, response);
		
	}else if(action.equals("initEdit")){ 
		
		   
	   	String privilegeid	= request.getParameter("privilegeid");
	 	
			Map prvDet=new HashMap();
			prvDet=prv.fetchPrivilege(Integer.parseInt(privilegeid));
			
			request.setAttribute("privilegeid",prvDet.get("privilegeid"));
			request.setAttribute("privilege",prvDet.get("privilege"));
			
			view = request.getRequestDispatcher("privilege/initEditPrivilege.jsp");
	 	
	 	response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		view.forward(request, response);
	
   }else if(action.equals("processEdit")){
		
		String privilegeid	= request.getParameter("privilegeid");
		String privilege	= request.getParameter("privilege");
		int errorCounter=0;
		
		if(ch.checkMemberSession(sess)){
			iVariable.put("logged",true);	
			
			if(privilege==null || privilege==""){
				iVariable.put("status","Please fill up Privilege Name");
				errorCounter=+1;
			}
			
			
			if(errorCounter==0){
				iVariable.put("process",true);
				
				//iVariable.put("process",false);
				Map det=new HashMap();
				det.put("privilegeid",privilegeid);
				det.put("privilege",privilege);
				
				//Process Insert
				
				if(prv.UpdatePrivilege(det)){
					
					iVariable.put("process",true);
					iVariable.put("status","Privilege has been updated");
					
				}else{
					iVariable.put("process",false);
					iVariable.put("status","Error on adding role");
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
		
		String privilegeid = request.getParameter("privilegeid");
		
		Map thisPrivilege=prv.fetchPrivilege(Integer.parseInt(privilegeid));
		request.setAttribute("privilege",thisPrivilege.get("privilege"));
		request.setAttribute("privilegeid",thisPrivilege.get("privilegeid"));
	 	view = request.getRequestDispatcher("privilege/initDelete.jsp");
	 	
	 	response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		view.forward(request, response);
		
   }else if(action.equals("processDelete")){
		
		
		String privilegeid	= request.getParameter("privilegeid");
		
		if(prv.DeletePrivilege(privilegeid)){
			iVariable.put("process",true);
			iVariable.put("status","Privilege has been deleted");
		}else{
			iVariable.put("process",false);
			iVariable.put("status","Error:Privilege has not been deleted");
		}
		
		//application/json
		response.setContentType("text/html");
		PrintWriter pWrite = response.getWriter();
		pWrite.println(JSONValue.toJSONString(iVariable));	
  }
		
		
		
		
		
	}//end of function
	
	
	
	

}//end of class
