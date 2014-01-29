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

@WebServlet(description = "Team servlet", urlPatterns = { "/Team" })

public class Team extends HttpServlet { 
	private static final long serialVersionUID = 1L;
	private String strName="Testing";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Team() {
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
			
			UserModel usr=new UserModel();
			usr.projectFile=getServletContext().getRealPath("");
			
			SalesModel sales=new SalesModel();
			sales.projectFile=getServletContext().getRealPath("");
			
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
				
				Map det=new HashMap();
				//det.put();
				ResultSet agentlist=usr.loadSalesTeam(det);
				
				Map detMonthTarget=new HashMap();
				Map detYearTarget=new HashMap();
				Map detYearTotalTarget=new HashMap();
				
				
				Map detMonthRevenue=new HashMap();
				Map detYearRevenue=new HashMap();
				Map detYearTotalRevenue=new HashMap();
				
				
				String strMonthTarget="";
				String strYearTarget="";
				String strYearTotalTarget="";
				
				String strMonthRevenue="";
				String strYearRevenue="";
				String strYearTotalRevenue="";
				
				try {
					int iCtr=0;
				while(agentlist.next()){
					
					//Agent Name
					request.setAttribute("fullname_"+iCtr,agentlist.getString("userfirstname")+" "+agentlist.getString("userlastname"));
					request.setAttribute("userid_"+iCtr,agentlist.getString("userid"));
					///////////////////////////////////////////////////////////////////////////
					///////////////////////////////////////////////////////////////////////////
					//Get Per Month Settings
					//Monthly Target 
					try {
						detMonthTarget.put("month",ch.formatDate("yyyy-MM"));
						detMonthTarget.put("user_id",agentlist.getString("userid"));
						detMonthTarget.put("year",ch.formatDate("yyyy"));
						ResultSet rsMonthTarget=sales.ldMonthTarget2(detMonthTarget,false);
						
						if (rsMonthTarget.next()) {  
							  do{
								 // System.out.println("Target Month Amount"+rsMonthTarget.getString("target_amount"));
								 request.setAttribute("monthly_"+iCtr,rsMonthTarget.getString("target_amount"));
								 strMonthTarget=rsMonthTarget.getString("target_amount");
							  } while (rsMonthTarget.next());
						 }else{
							 
								detMonthTarget.put("user_id",null);
								ResultSet rsMonthTarget2=sales.ldMonthTarget(detMonthTarget,false);
								
								if (rsMonthTarget2.next()) {  
									  do{
										 request.setAttribute("monthly_"+iCtr,rsMonthTarget2.getString("target_amount"));
										 strMonthTarget=rsMonthTarget2.getString("target_amount");
										 
									  } while (rsMonthTarget2.next());
								 }else{
									 request.setAttribute("monthly_"+iCtr,"00.00");
									 strMonthTarget="00.00";
									
								 }
						 }
						 
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					///////////////////////////////////////////////////////////////////////////
					///////////////////////////////////////////////////////////////////////////
					
					
					
					///////////////////////////////////////////////////////////////////////////
					///////////////////////////////////////////////////////////////////////////
					//Get Per Year Settings
					//Yearly Target 
					try {
					
					detYearTarget.put("year",ch.formatDate("yyyy"));
					detYearTarget.put("user_id",agentlist.getString("userid"));
					ResultSet rsYearTarget=sales.ldYearTarget(detYearTarget);
					
					if (rsYearTarget.next()) {  
					do{
						request.setAttribute("yearly_"+iCtr,rsYearTarget.getString("target_amount"));
						strYearTarget=rsYearTarget.getString("target_amount");
					} while (rsYearTarget.next());
					}else{
					
					detYearTarget.put("user_id",null);
					ResultSet rsYearTarget2=sales.ldYearTarget(detYearTarget);
					
					if (rsYearTarget2.next()) {  
					do{
						request.setAttribute("yearly_"+iCtr,rsYearTarget2.getString("target_amount"));
						strYearTarget=rsYearTarget2.getString("target_amount");
					} while (rsYearTarget2.next());
					}else{
						request.setAttribute("yearly_"+iCtr,"00.00");
						strYearTarget="00.00";
					}
					}
					
					} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
					///////////////////////////////////////////////////////////////////////////
					///////////////////////////////////////////////////////////////////////////
					
										
					///////////////////////////////////////////////////////////////////////////
					///////////////////////////////////////////////////////////////////////////
					//Get Per Year Total Settings
					//Yearly  Total Target 
					try {
					
						detYearTotalTarget.put("year",ch.formatDate("yyyy"));
						detYearTotalTarget.put("user_id",agentlist.getString("userid"));
					ResultSet rsYearTotalTarget=sales.ldYearTarget(detYearTotalTarget);
					
					if (rsYearTotalTarget.next()) {  
					do{
						request.setAttribute("yearlytotal_"+iCtr,rsYearTotalTarget.getString("target_amount"));
						strYearTotalTarget=rsYearTotalTarget.getString("target_amount");
						
					} while (rsYearTotalTarget.next());
					}else{
					
						detYearTotalTarget.put("user_id",null);
					ResultSet rsYearTotalTarget2=sales.ldYearTarget(detYearTotalTarget);
					
					if (rsYearTotalTarget2.next()) {  
					do{
						request.setAttribute("yearlytotal_"+iCtr,rsYearTotalTarget2.getString("target_amount"));
						strYearTotalTarget=rsYearTotalTarget2.getString("target_amount");
					} while (rsYearTotalTarget2.next());
					}else{
						request.setAttribute("yearlytotal_"+iCtr,"00.00");
						strYearTotalTarget="00.00";
					}
					}
					
					} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
					///////////////////////////////////////////////////////////////////////////
					///////////////////////////////////////////////////////////////////////////
					
					
					
			
					///////////////////////////////////////////////////////////////////////////
					///////////////////////////////////////////////////////////////////////////
					/////////Monthly Revenue
					try {
						
						detMonthRevenue.put("month",ch.formatDate("yyyy-MM"));
						detMonthRevenue.put("year",ch.formatDate("yyyy"));
						detMonthRevenue.put("userid",agentlist.getString("userid"));
						
						ResultSet rsMonthRevenue=sales.loadAgentMonthlySales(detMonthRevenue,false);
					
						if (rsMonthRevenue.next()) {  
						do{
							request.setAttribute("monthlyrevenue_"+iCtr,rsMonthRevenue.getString("totalpayment"));
							if(!rsMonthRevenue.getString("totalpayment").equals(null)){
								
								strMonthRevenue=rsMonthRevenue.getString("totalpayment");
								
							}

						} while (rsMonthRevenue.next());
						}
						else{
							request.setAttribute("monthlyrevenue_"+iCtr,"0.000");
							strMonthRevenue = "0.000";
						}
					} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
				
						request.setAttribute("monthlypercentage_"+iCtr,ch.computePercentage(strMonthTarget,strMonthRevenue));
					
						//System.out.println(ch.computePercentage(strMonthTarget,strMonthRevenue));
					//request.setAttribute("monthlypercentage_"+iCtr,ch.computePercentage("3600000","4332000"));	
					///////////////////////////////////////////////////////////////////////////
					///////////////////////////////////////////////////////////////////////////
					
					
					
					
					///////////////////////////////////////////////////////////////////////////
					///////////////////////////////////////////////////////////////////////////
					/////////Yearly Revenue
					try {
					
					detYearRevenue.put("year",ch.formatDate("yyyy"));
					detYearRevenue.put("user_id",agentlist.getString("userid"));
					detYearRevenue.put("userid",agentlist.getString("userid"));
					ResultSet rsYearRevenue=sales.loadAgentYearlySales(detYearRevenue,true);
					
					if (rsYearRevenue.next()) {  
					do{
					request.setAttribute("yearlyrevenue_"+iCtr,rsYearRevenue.getString("totalpayment"));
						strYearRevenue=rsYearRevenue.getString("totalpayment");
					} while (rsYearRevenue.next());
					}
					else{
						request.setAttribute("yearlyrevenue_"+iCtr,"0.000");
						strYearRevenue = "0.000";
					}
					} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}				
					
					//Compute Monthly Percentage
					request.setAttribute("yearlypercentage_"+iCtr,ch.computePercentage(strYearTarget,strYearRevenue));
					
					request.setAttribute("yearlytotalpercentage_"+iCtr,ch.computePercentage(strYearTotalTarget,strYearRevenue));
					//request.setAttribute("monthlypercentage_"+iCtr,ch.computePercentage("3600000","4332000"));	
					///////////////////////////////////////////////////////////////////////////
					///////////////////////////////////////////////////////////////////////////
												
					request.setAttribute("iCtr",iCtr);
					iCtr++;
					
				}//end of while
				
					
				
				
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//request.setAttribute("graphContent",true);
				useDispatcher=true;
				view = request.getRequestDispatcher("team/main.jsp");
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
								else{
									iSales[i]=(float) 00.0;
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
