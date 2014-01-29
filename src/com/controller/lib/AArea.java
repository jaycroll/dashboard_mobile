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
 * Servlet implementation class AUser
 */
@WebServlet("/AArea")
public class AArea extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AArea() {
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
		ProjectionModel proj=new ProjectionModel();
		CustomHelper ch=new CustomHelper();
		AreaModel area = new AreaModel();
		proj.projectFile=getServletContext().getRealPath("");
		HttpSession sess=request.getSession();
		String territoryid = request.getParameter("territoryid");
		String areaid = request.getParameter("areaid");
		
		//System.out.println(action);

		

		if(action.equals("loadProjection")){
	
		   	Map det=new HashMap();
			 useDispatcher=true;
			 det.put("revenue_type", "Sales");
			 det.put("year",ch.formatDate("yyyy"));
			 det.put("areaid", areaid);
			 //ResultSet detSales=proj.loadProjectionMonthly(det);
			 ResultSet teamLead = proj.loadTeamLeaderFromAreas(det);
			 ResultSet team = proj.loadAgentsFromAreas(det);
			 ResultSet targetYtd = proj.loadAreaTargetYTD(det);
			 ResultSet targetMtd = proj.loadAreaTargetMTD(det);
			 ResultSet actualYtd = proj.loadAreaSalesYTD(det);
			 ResultSet actualMtd = proj.loadAreaSalesMTD(det);
			 ResultSet actualMonthly = proj.loadAreaMonthlySales(det, false);
			 ResultSet targetMonthly = proj.loadAgentsMonthlyTarget(det, false);
			 //System.out.println(territoryid);
			 	try{
				 	String[] teamLeadinfo = new String[3];

					if(teamLead.next()){
						do{
							teamLeadinfo[0] = teamLead.getString("userid");
							teamLeadinfo[1] = teamLead.getString("userfirstname");
							teamLeadinfo[2] = teamLead.getString("userlastname");
							//System.out.println(teamLeadinfo[1]);
						}while(teamLead.next());
					}
				}catch(SQLException e){
					e.printStackTrace();
				}
			 	try{
			 		team.last();
			 		int rowCount = team.getRow();
			 		String[][] teamMemberinfo = new String[rowCount][3];
			 		team.beforeFirst();
			 		int iterator = 0;
			 		if(team.next()){
			 			do{
			 				teamMemberinfo[iterator][0] = team.getString("userid");
			 				teamMemberinfo[iterator][1] = team.getString("userfirstname");
			 				teamMemberinfo[iterator][2] = team.getString("userlastname");
			 				//System.out.println(teamMemberinfo[iterator][1]);
			 			}
			 			while(team.next());
			 		}
			 	}catch(SQLException e){
			 		e.printStackTrace();
			 	}
			 	try{
			 		targetYtd.beforeFirst();
			 		if(targetYtd.next()){
			 			String targetYtdContainer = targetYtd.getString("target_amount");
			 		}
			 	}
			 	catch(SQLException e){
			 		e.printStackTrace();
			 	}
			 	try{
			 		targetMtd.beforeFirst();
			 		if(targetMtd.next()){
			 			String targetMtdContainer = targetMtd.getString("target_amount");
			 		}
			 	}catch(SQLException e){
			 		e.printStackTrace();
			 	}
			 	try{
			 		actualYtd.beforeFirst();
			 		if(actualYtd.next()){
			 			String actualYtdContainer = actualYtd.getString("totalsales");
			 		}
			 	}catch(SQLException e){
			 		e.printStackTrace();
			 	}
			 	try{
			 		actualMtd.beforeFirst();
			 		if(actualMtd.next()){
			 			String actualMtdContainer = actualMtd.getString("totalsales");
			 		}
			 	}catch(SQLException e){
			 		
			 	}
			 	try{
			 		actualMonthly.last();
			 		int iterator_2 = actualMonthly.getRow();
			 		actualMonthly.beforeFirst();
			 		String[] monthlySales = new String[12];
			 		int i = 0;
			 		if(actualMonthly.next()){
			 			do{
			 				i = Integer.parseInt(actualMonthly.getString("month"));
			 				monthlySales[i] = actualMonthly.getString("month"); 
			 			}while(actualMonthly.next());
			 		}
			 	}catch(SQLException e){
			 		e.printStackTrace();
			 	}
			 	try{
			 		targetMonthly.last();
			 		targetMonthly.beforeFirst();
			 		String[] monthlyTarget = new String[13];
			 		int i = 1;
			 		if(targetMonthly.next()){
			 			do{
			 				
			 				i = Integer.parseInt(targetMonthly.getString("month"));
			 				//if(targetMonthly.getString("target_amount")!=null || targetMonthly.getString("target_amount")!=""){
			 					monthlyTarget[i] = targetMonthly.getString("target_amount");
			 			}while(targetMonthly.next());
			 		}
			 	}catch(SQLException e){
			 		e.printStackTrace();
			 	}
			 	
					
		
	
			
			view = request.getRequestDispatcher("area/loadMonth.jsp");
			response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			view.forward(request, response);

		   
	   }
		
		 
	}//end of function
	
	
	
	
	

}//end of class
