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
@WebServlet("/ATeam")
public class ATeam extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ATeam() {
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
		proj.projectFile=getServletContext().getRealPath("");
		
		SalesModel sales=new SalesModel();
		sales.projectFile=getServletContext().getRealPath("");
		
		UserModel usr=new UserModel();
		usr.projectFile=getServletContext().getRealPath("");
		
		
		
		
		HttpSession sess=request.getSession();
		
		
		if(action.equals("loadDefault")){
				
			//Load Default For Overall
		
	   }if(action.equals("loadMonth")){
		   
		     String user_id	= request.getParameter("user_id");
		   
		   	 Map det=new HashMap();
			 useDispatcher=true;
			 det.put("revenue_type", "Sales");
			 det.put("year",ch.formatDate("yyyy"));
			 det.put("user_id",user_id);
			 det.put("userid",user_id);
			 
			 ResultSet detSales=sales.loadProjectionMonthly(det);
			 
			 
			 float[] iActualSales=new float[13];
			 float[] iActualTarget=new float[13];
			 
		
			
			
			try {
					//det.put("month",ch.formatDate("yyyy-")+String.format("%02d", i));
					ResultSet rsMonthRevenue=sales.loadAgentMonthlySales(det,true);
					
					if (rsMonthRevenue.next()) {  
						  do{
							  for(int i=1;i<13;i++){	
								  if(i == Integer.parseInt(rsMonthRevenue.getString("month"))){
									  iActualSales[i]=Float.parseFloat(rsMonthRevenue.getString("totalpayment"));
									  //System.out.println(rsMonthRevenue.getString("month"));
									  //request.setAttribute("rsMonthRevenue",rsMonthRevenue.getString("actual_revenue"));
								  }
							  }
						  } while (rsMonthRevenue.next());
						  rsMonthRevenue.close();
						  }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			 	
			
		
			try {
				//det.put("month",ch.formatDate("yyyy-")+String.format("%02d", i));
				ResultSet rsMonthTarget=proj.loadAgentMonthlyTarget(det,true);
				
				if (rsMonthTarget.next()) {  
					  do{
						  for(int i=1;i<13;i++){
							  if(i == Integer.parseInt(rsMonthTarget.getString("month"))){
							    iActualTarget[i]=Float.parseFloat(rsMonthTarget.getString("target_amount"));
						  		//request.setAttribute("rsMonthTarget",rsMonthTarget.getString("target_amount"));
							  }
						  }
				      } while (rsMonthTarget.next());
					  rsMonthTarget.close();
				  }
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  
			
			
			 	
		//////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////
			//Load User Details
			Map usrDet=new HashMap();
			usrDet=usr.fetchUser(user_id);
			request.setAttribute("usrDet",usrDet);
			request.setAttribute("user_id",user_id);
			
			Map det2=new HashMap();
			det2.put("user_id",user_id);
			
			
			try {
			ResultSet salesArea=sales.loadAgentArea(det2);
			if (salesArea.next()) {  
				request.setAttribute("agent_area",salesArea.getString("territoryname")+" "+salesArea.getString("areaname"));
			 }else{
				 request.setAttribute("agent_area","None");
			 }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			request.setAttribute("iActualSales",iActualSales);
			request.setAttribute("iActualTarget",iActualTarget);
			view = request.getRequestDispatcher("profile/loadMonth.jsp");
			
			response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			view.forward(request, response);
		   
	   }
		
		 
	}//end of function
	
	
	
	

}//end of class
