package com.controller.lib;

import com.misc.lib.CustomHelper;
import com.misc.lib.RolePermission;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONValue;

/**
 * Servlet implementation class AUser
 */
@WebServlet("/APermission")
public class APermission extends HttpServlet {
	private static final long serialVersionUID = 1L;
	   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public APermission() {
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
		
		String action	= request.getParameter("action");
		CustomHelper ch=new CustomHelper();
		RolePermission rp=new RolePermission();
		rp.settingUrl=getServletContext().getRealPath("/");
		
		HttpSession sess=request.getSession();
		
		if(action.equals("checkPermission")){
			
			
			String html="";
			
			if(ch.checkMemberSession(sess)){
				iVariable.put("logged",true);	
				
				int moduleid 	= Integer.parseInt(request.getParameter("moduleid"));
				int privilegeid 	= Integer.parseInt(request.getParameter("privilegeid"));
				
				  if(rp.verifyModule(Integer.parseInt(sess.getAttribute("roleid").toString()),moduleid,privilegeid)){ 
					  
					  iVariable.put("granted",true);
				  }else{
					  iVariable.put("granted",false);
					 
					  html="<div class='divCenter txtCenter'>"+
							   "<div class='access_denied'>Access Denied</div>"+
							   "<div class='clr'>&nbsp;</div>"+
							   "<div class='clr'><a  style='color:#3E7836;' href='"+getServletContext().getContextPath()+"/"+"'>Back to Main</a></div>"+
	                           "<div class='clr'>&nbsp;</div>"+
	                           "</div>";
					 	
				  }	  
				
				  
				  iVariable.put("html",html);	
				  
				  
				
			}else{
				iVariable.put("logged",false);	
			}
			
			
			//application/json
			response.setContentType("text/html");
			PrintWriter pWrite = response.getWriter();
			pWrite.println(JSONValue.toJSONString(iVariable));
			
		}
		
		 
	}//end of function
	
	
}//end of class
