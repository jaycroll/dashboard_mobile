package com.controller.lib;


import com.misc.lib.CustomHelper;

import java.io.IOException;
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

@WebServlet(description = "SalesReports servlet", urlPatterns = { "/SalesReports" })

public class SalesReports extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String strName="Testing";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SalesReports() {
        super();
        // TODO Auto-generated constructor stub
        
    }
protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	// TODO Auto-generated method stub
	
	String action	= request.getParameter("action");
	HttpSession sess=request.getSession();
	CustomHelper ch=new CustomHelper();
	
	
	
	
	if(ch.checkMemberSession(sess)){

		ProjectionModel proj=new ProjectionModel();
		proj.projectFile=getServletContext().getRealPath("");
		
		RequestDispatcher view=null;
		Boolean useDispatcher=false;
		
		if(action==null){
			
			////////////////////////////////////////////////////////
			//For Year
			/*
			 Map det=new HashMap();
			 request.setAttribute("banner","dashboard");
			
			
			//Monthly
			det.put("fromMonth",ch.formatDate("yyyy-MM"));
			det.put("toMonth",ch.formatDate("yyyy-MM-dd"));
			
			//Yearly
			det.put("fromYear",ch.formatDate("yyyy"));
			det.put("toYear",ch.formatDate("yyyy-MM-dd"));
			
			ResultSet rDetails=proj.loadProjectionA(det);
			request.setAttribute("rDetails",rDetails);
			*/
			

			useDispatcher=true;
			view = request.getRequestDispatcher("salesreports/main.jsp");
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
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		 doProcess(request,response);
			
		
		 
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
		doProcess(request,response);
	}

}
