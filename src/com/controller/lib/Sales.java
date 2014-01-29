package com.controller.lib;


import com.misc.lib.CustomHelper;

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

import com.model.lib.*;

/**
 * Servlet implementation class Index
 */

@WebServlet(description = "Sales servlet", urlPatterns = { "/Sales" })

public class Sales extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String strName="Testing";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sales() {
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
		
		 ChannelModel channel=new ChannelModel();
		 channel.projectFile=getServletContext().getRealPath("");
		 
		 SalesModel sales=new SalesModel();
		 sales.projectFile=getServletContext().getRealPath("");
		
		if(ch.checkMemberSession(sess)){
			
			RequestDispatcher view=null;
			Boolean useDispatcher=false;
			
			if(action==null){
				
				 Map det=new HashMap();
				 request.setAttribute("banner","dashboard");
				 useDispatcher=true;
				 
				 ResultSet listChannel=channel.loadChannel(det);
				 request.setAttribute("listChannel",listChannel);
				 
				 ResultSet listSales=sales.loadSales(det);
				 request.setAttribute("listSales",listSales);
				 
				 view = request.getRequestDispatcher("sales/main.jsp");
				
				////////////////////////////////////////////////////////
				
			}else if(action.equals("PropagateFromSales")){
				
				 
				//Load All Sales Order That Are Complete
				ResultSet salesList=sales.loadFromSalesTeam();
				
				    Map det=new HashMap();
					PrintWriter out = response.getWriter();  
					out.println("-------SalesRevenue Propagation-----------");
				try {
					while(salesList.next()){
						
						
						out.println("Order ID:"+salesList.getString("orderid"));
						out.println("Agent ID:"+salesList.getString("agentid"));
						out.println("Area ID:"+salesList.getString("areaid"));
						out.println("Territory ID:"+salesList.getString("territoryid"));
						out.println("Amount:"+salesList.getString("netamount"));
						out.println("Completed Date:"+salesList.getString("salesinvoicelastactiondate"));
						out.println("Shorten Date:"+ch.trimDate(salesList.getString("salesinvoicelastactiondate").toString()));
						
						det.put("orderid",salesList.getString("orderid"));
						det.put("agentid",salesList.getString("agentid"));
						det.put("areaid",salesList.getString("areaid"));
						det.put("territoryid",salesList.getString("territoryid"));
						det.put("amount",salesList.getString("netamount"));
						det.put("completeddate",salesList.getString("salesinvoicelastactiondate"));
						sales.InsertSalesRevenue(det);
						
						//////////////////////////////////////////////////
						///Check On Channel Revenue
						Map variable=new HashMap();
						variable.put("channel_id","99");
						variable.put("revenue_date",ch.trimDate(salesList.getString("salesinvoicelastactiondate").toString()));
						variable.put("amount",salesList.getString("netamount"));
						variable.put("revenue_type","Sales");
						variable.put("reference",salesList.getString("orderid"));
						
						
						if(sales.checkSalesRevenueExistence(variable)){
							//Nothing new
							out.println("Existing Record");
						}else{
							//Insert
							out.println("Inserting Record");
							sales.InsertSales(variable);
						}
						out.println("------------------------------------<br>");
			
				}//end of while
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				useDispatcher=false;
				
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
