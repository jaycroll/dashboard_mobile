package com.controller.lib;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model.lib.UserModel;
import com.misc.lib.CustomHelper;


/**
 * Servlet implementation class User
 */
@WebServlet("/User")
public class User extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public User() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action	= request.getParameter("action");
		HttpSession sess=request.getSession();
		CustomHelper ch=new CustomHelper();
		
		UserModel usr=new UserModel();
		usr.projectFile=getServletContext().getRealPath("");
		
		RequestDispatcher view=null;
		Boolean useDispatcher=false;
		//System.out.println(action);
		if(ch.checkMemberSession(sess)){
			
			if(action==null){
				
				 Map det=new HashMap();
				 ResultSet usrListSpec=usr.loadUser(det);
				 ResultSet usrList=usr.loadUser(det);
				 ResultSet urlRole=usr.loadRole();
				 
				 request.setAttribute("showMenu",true);
				 request.setAttribute("usrListSpec",usrListSpec);
				 request.setAttribute("urlRole",urlRole);
				 request.setAttribute("usrList",usrList);
				 
				 request.setAttribute("banner","dashboard");
				 
				 view = request.getRequestDispatcher("user/main.jsp");
				 useDispatcher=true;
			
			}else if(action.equals("Main")){
				
				 Map det=new HashMap();
				 ResultSet usrList=usr.loadUser(det);
				 request.setAttribute("showMenu",true);
				 request.setAttribute("usrList",usrList);
				 view = request.getRequestDispatcher("user/main.jsp");
				 useDispatcher=true;
				 
				 
			}else if(action.equals("logout")){
				
				 useDispatcher=false;
				 //Destroy Session
				 sess.setAttribute("userid",null);
				 sess.setAttribute("loggedhash",null);
				 sess.setAttribute("username",null);
				 sess.setAttribute("logged",null);
				 sess.setAttribute("logout","logout");
				 response.setContentType("text/html");
				 PrintWriter pWrite = response.getWriter();
			
			}else if(action.equals("AddAccess")){
				
				 request.setAttribute("showMenu",true);
				 view = request.getRequestDispatcher("user/addAccess.jsp");
				 useDispatcher=true;
				 
				 String listMonth[]=ch.loadMonths();
				 request.setAttribute("listMonth",listMonth);
				 
				 //Load Role
				 ResultSet usrRole=usr.loadRole(); 
				 request.setAttribute("usrRole",usrRole);
				 
			}else if(action.equals("EditAccess")){
				
				 String userid	= request.getParameter("param");
				 request.setAttribute("showMenu",true);
				 
				 Map usrDet=new HashMap();
				 usrDet=usr.fetchUser(userid);
				 
				 
				 String listMonth[]=ch.loadMonths();
				 request.setAttribute("listMonth",listMonth);
				 
				 
				 view = request.getRequestDispatcher("user/editAccess.jsp");
				 useDispatcher=true;
				
				 //Load Role
				 ResultSet usrRole=usr.loadRole(); 
				 request.setAttribute("usrRole",usrRole);
				 
				 //Load User Details
				 request.setAttribute("usrDet",usrDet);
				 
			}else if(action.equals("PopulateAgent")){
				
				ResultSet listAgent=usr.loadBridgeUserAgent();
				Map det=new HashMap();
				PrintWriter out = response.getWriter();  
				out.println("------Fetch User-----");
				try {
					while(listAgent.next()){
						
						out.println("Dashboard ID:"+listAgent.getString("userid"));
						out.println("Agent ID:"+listAgent.getString("agentid"));
						out.println("Sales ID:"+listAgent.getString("salesuserid"));
				
						det.put("userid",listAgent.getString("userid"));
						det.put("agentid",listAgent.getString("agentid"));
						det.put("salesuserid",listAgent.getString("salesuserid"));
						
						usr.InsertBridgeAgent(det);
					}//end of while
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}	
			
			
		
			if(useDispatcher){	
				response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
				response.setHeader("Pragma", "no-cache");
				view.forward(request, response); 
			}
		}else{
			response.sendRedirect("/Dashboard_mobile");
		}
		 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action	= request.getParameter("action");
		HttpSession sess=request.getSession();
		CustomHelper ch=new CustomHelper();
		
		UserModel usr=new UserModel();
		usr.projectFile=getServletContext().getRealPath("");
		
		RequestDispatcher view=null;
		Boolean useDispatcher=false;
		//System.out.println(action);
		if(ch.checkMemberSession(sess)){
			
			if(action.equals("logout")){
				
				 useDispatcher=false;
				 //Destroy Session
				 sess.setAttribute("userid",null);
				 sess.setAttribute("loggedhash",null);
				 sess.setAttribute("username",null);
				 sess.setAttribute("logged",null);
				 sess.invalidate();
				 System.out.println("itgetshere");
				 
				 response.setContentType("text/html");
				 PrintWriter pWrite = response.getWriter();
			
			}
			
		
			if(useDispatcher){	
				response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
				response.setHeader("Pragma", "no-cache");
				view.forward(request, response); 
			}
		}else{
			response.sendRedirect("/Dashboard_mobile");
		}
		 
	}
}


