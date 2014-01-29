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

@WebServlet(description = "Area servlet", urlPatterns = { "/Area" })

public class Area extends HttpServlet { 
	private static final long serialVersionUID = 1L;
	private String strName="Testing";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Area() {
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
			
			AreaModel area = new AreaModel();
			area.projectFile = getServletContext().getRealPath("");
			RequestDispatcher view=null;
			Boolean useDispatcher=false;
			
			if(action==null){
				
				
				
				Map det=new HashMap();
				det.put("territoryid",6);
				Map territoryid = new HashMap();
				Map areaid = new HashMap();
				ResultSet agentlist=usr.loadSalesUser(det);
				ResultSet getTerritory = area.loadTerritory();
				ResultSet getAreas = proj.loadAreas(det);
				
				//String[] areas = new String[100];//<--sucks cause i made this one static :/ i'll call you pro if you it dynamic :D
				try{
					getTerritory.last();
					int territoryCount = getTerritory.getRow();
					String[][] territories = new String[territoryCount][2];
					int a = 0;
					getTerritory.beforeFirst();
					if(getTerritory.next()){						
						do{
							territories[a][0] = getTerritory.getString("territoryid");
							territories[a][1] = getTerritory.getString("territoryname");
							a++;
						}while(getTerritory.next());
					}
					request.setAttribute("territories",territories);
				}catch(SQLException e){
					e.printStackTrace();
				}

				try {
					getAreas.last();
					int totalRowCount = getAreas.getRow();
					String[][] areas = new String[totalRowCount][3];
					String sAreas = "";
					System.out.println(totalRowCount);
					getAreas.beforeFirst();
					//System.out.println(totalRowCount);
					int i = 0;
					if(getAreas.next()){
						do{
						areas[i][0] = getAreas.getString("areaid");
						areas[i][1] = getAreas.getString("areaname");
						areas[i][2] = getAreas.getString("territoryid");
						i++;	
						}while(getAreas.next());
					}
					//System.out.println(sAreas);
					request.setAttribute("areas",areas);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//request.setAttribute("loadTerritory", getTerritory);
				
				
				request.setAttribute("graphContent",true);
				useDispatcher=true;
				view = request.getRequestDispatcher("area/main.jsp");
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
