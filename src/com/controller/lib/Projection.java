package com.controller.lib;


import com.misc.lib.CustomHelper;

import java.io.IOException;
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

import com.model.lib.*;

/**
 * Servlet implementation class Index
 */

@WebServlet(description = "Projectioon servlet", urlPatterns = { "/Projection" })

public class Projection extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String strName="Testing";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Projection() {
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
				
				request.setAttribute("graphContent",true);
				useDispatcher=true;
				view = request.getRequestDispatcher("projection/main.jsp");
				////////////////////////////////////////////////////////
				
			}else if(action.equals("Monthly")){
				
				
				////////////////////////////////////////////////////////
				//////////////////////For Monthly///////////////////////
				 Map det=new HashMap();
				 useDispatcher=true;
				 det.put("revenue_type", "Sales");
				 det.put("year","2012");
				 ResultSet detSales=proj.loadProjectionMonthly(det);
				 
				 float[] iActualSales=new float[13];
				 
				 	float[] iSales=new float[13];
				 	try {
						if (detSales.next()) {  
							
							iSales[0]=(float) 00.0;
						do{
							
							for(int i=1;i<13;i++){
								if(detSales.getString("monthNum").equals(String.format("%02d", i))){
									iSales[i]=Float.parseFloat(detSales.getString("subTotal"));
								}
								
							}//end of loop
						
						} while (detSales.next());
						
						}
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				 	
				 	
				 	 det.put("revenue_type", "Refund");
					 det.put("year","2012");
					 ResultSet detRefund=proj.loadProjectionMonthly(det);
					 
					 float[] iRefund=new float[13];
					 	try {
							if (detRefund.next()) {  
						
								iRefund[0]=(float) 00.0;
							do{
								
								for(int i=1;i<13;i++){
									if(detRefund.getString("monthNum").equals(String.format("%02d", i))){
										iRefund[i]=Float.parseFloat(detRefund.getString("subTotal"));
									}
									
								}//end of loop
							
							} while (detRefund.next());
							
							}
							
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					 
					
					iActualSales[0]=(float) 00.0;
					
					for(int b=1;b<13;b++){
						iActualSales[b]=iSales[b]-iRefund[b];
					}//end of float
					
				request.setAttribute("iActualSales",iActualSales);
				view = request.getRequestDispatcher("target/main.jsp");	
				
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
