package com.controller.lib;

import java.io.IOException;
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

import com.misc.lib.CustomHelper;
import com.model.lib.RoleModel;

@WebServlet("/AProfile")
public class AProfile extends HttpServlet{

	public AProfile(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		RequestDispatcher view=null;
		Boolean useDispatcher=false;
		
		Map iVariable=new HashMap();
		
		String action	= request.getParameter("action");
		RoleModel role=new RoleModel();
		CustomHelper ch=new CustomHelper();
		role.projectFile=getServletContext().getRealPath("");
		
		
		HttpSession sess=request.getSession();
		
		
		if(action.equals("initView")){
			
			
			ResultSet moduleList=role.loadModule();
			ResultSet privilegeList=role.loadPrivilege();
			request.setAttribute("moduleList",moduleList);
			request.setAttribute("privilegeList",privilegeList);
			view = request.getRequestDispatcher("role/loadProfile.jsp");
		 	
		 	response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			view.forward(request, response);
			
		}
	}
}
