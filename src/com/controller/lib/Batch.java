package com.controller.lib;

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

import com.misc.lib.CustomHelper;
import com.model.lib.ChannelModel;
import com.model.lib.SalesModel;
import com.model.lib.UserModel;

/**
 * Servlet implementation class Misc
 */
@WebServlet("/Batch")
public class Batch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Batch() {
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
		 
		 RequestDispatcher view=null;
		 Boolean useDispatcher=false;
		 
		UserModel usr=new UserModel();
		usr.projectFile=getServletContext().getRealPath("");
	
		 if(action.equals("PropagateFromSales")){
				
			 
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
				
			}else if(action.equals("PopulateAgent")){
				
				ResultSet listAgent=usr.loadBridgeUserAgent();
				Map det=new HashMap();
				PrintWriter out = response.getWriter();  
				out.println("------Fetch User-----");
				try {
					while(listAgent.next()){
						
						out.println("Dashboard ID:"+listAgent.getString("userid"));
						out.println("Agent ID:"+listAgent.getString("agentid"));
						out.println("Sales ID:"+listAgent.getString("salesuserid"));
				
						det.put("userid",listAgent.getString("userid"));
						det.put("agentid",listAgent.getString("agentid"));
						det.put("salesuserid",listAgent.getString("salesuserid"));
						
						usr.InsertBridgeAgent(det);
					}//end of while
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}	
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
