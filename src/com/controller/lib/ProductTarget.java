package com.controller.lib;


import com.misc.lib.CustomHelper;

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

import com.model.lib.*;

/**
 * Servlet implementation class Index
 */

@WebServlet(description = "Target servlet", urlPatterns = { "/ProductTarget" })

public class ProductTarget extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String strName="Testing";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductTarget() {
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
		
		 TargetModel tar=new TargetModel();
		 tar.projectFile=getServletContext().getRealPath("");
		 ChannelModel channel = new ChannelModel();
		 channel.projectFile = getServletContext().getRealPath("");
		 
		if(ch.checkMemberSession(sess)){
	
			ProjectionModel proj=new ProjectionModel();
			proj.projectFile=getServletContext().getRealPath("");
			
			RequestDispatcher view=null;
			Boolean useDispatcher=false;
			
			if(action==null){
				
				 Map det=new HashMap();
				 request.setAttribute("banner","dashboard");
				 useDispatcher=true;
				 //For some weird reason it wont accept the request coming from the ChannelModel and wont display the response
				 //Ill try to look that up later
				 
				 ResultSet listChannel = tar.loadChannel(det);
				 request.setAttribute("listChannel",listChannel);
			
				 
				 ResultSet listTarget=tar.loadProductTarget(det);
				 request.setAttribute("listTarget",listTarget);
				 
				 ResultSet listTargetGroup=tar.loadTargetGroup();
				 request.setAttribute("listTargetGroup",listTargetGroup);
				 
				 view = request.getRequestDispatcher("producttarget/main.jsp");
				
				////////////////////////////////////////////////////////
				
			}
			
			if(useDispatcher){	
				response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
				response.setHeader("Pragma", "no-cache");
				view.forward(request, response); 
			}
		}else{
			response.sendRedirect("/Dashboard");
		}
		 
			
		
		 
	}

	private int count(float[] iSales) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
