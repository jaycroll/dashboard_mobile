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

import org.json.simple.JSONValue;

/**
 * Servlet implementation class AUser
 */
@WebServlet("/ASales")
public class ASales extends HttpServlet {
	private static final long serialVersionUID = 1L;
	   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ASales() {
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
		CustomHelper ch=new CustomHelper();
		ChannelModel channel=new ChannelModel();
		channel.projectFile=getServletContext().getRealPath("");
		
		SalesModel sales=new SalesModel();
		sales.projectFile=getServletContext().getRealPath("");
		
		
		HttpSession sess=request.getSession();
		
		if(action.equals("initAddSales")){
			
			Map det=new HashMap();
			ResultSet listChannel=channel.loadChannel(det);
			request.setAttribute("listChannel",listChannel);
			
			
			view = request.getRequestDispatcher("sales/initAddSales.jsp");
		 	response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			view.forward(request, response);
			
		
		}else if(action.equals("loadSales")){
			
			Map det=new HashMap();
			String channel_id	= request.getParameter("channel_id");
			String revenue_date	= request.getParameter("revenue_date");
			String amount	= request.getParameter("amount");
			String revenue_type	= request.getParameter("revenue_type");
			
			
			det.put("channel_id",channel_id);
			
			if(revenue_date != null && revenue_date != ""){
				det.put("revenue_date",ch.transposeDate(revenue_date));
			}
			
			det.put("amount",amount);
			det.put("revenue_type",revenue_type);
			
			 ResultSet listSales=sales.loadSales(det);
			 request.setAttribute("listSales",listSales);
			
			
		 	view = request.getRequestDispatcher("sales/result_SalesList.jsp");
		 	
		 	response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			view.forward(request, response);
			
		
		}else if(action.equals("processAddSales")){
			
			String channel_id	= request.getParameter("channel_id");
			String revenue_date	= request.getParameter("revenue_date");
			String amount	= request.getParameter("amount");
			String revenue_type	= request.getParameter("revenue_type");
		

			int errorCounter=0;
			
			if(ch.checkMemberSession(sess)){
				iVariable.put("logged",true);	
				
				
				if(revenue_date==null || revenue_date==""){
					iVariable.put("status","Please fill up revenue date");
					errorCounter=+1;
				}
				
				if(amount==null || amount==""){
					iVariable.put("status","Please fill up amount");
					errorCounter=+1;
				}
				
				
				if(errorCounter==0){
					iVariable.put("process",true);
					
					//iVariable.put("process",false);
					Map det=new HashMap();
					det.put("channel_id",channel_id);
					
					if(revenue_date != null && revenue_date != ""){
						det.put("revenue_date",ch.transposeDate(revenue_date));
					}
					
					det.put("revenue_type",revenue_type);
					
				///////////////////////////////////////////////////////////	
				//Check First
				ResultSet detSales=sales.loadSales(det);
					try {
						if (detSales.next()) {
							iVariable.put("process",false);
							iVariable.put("status","Existing entry having the same channel, revenue date and revenue type");
						}else{
						
						//Process Insert
						det.put("amount",amount);
						if(sales.InsertSales(det)){
							
							//Also Add the default Privilege
							////////////////////////////////////////
							iVariable.put("process",true);
							iVariable.put("status","New sales has been added");
							
						}else{
							iVariable.put("process",false);
							iVariable.put("status","Error on adding sales");
						}
						
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						
					}//end of checking existence
				///////////////////////////////////////////////////////////	
					
					
					
						
				}else{
					iVariable.put("process",false);
				}
				
			}else{
				iVariable.put("logged",false);	
			}
			
			//application/json
			response.setContentType("text/html");
			PrintWriter pWrite = response.getWriter();
			pWrite.println(JSONValue.toJSONString(iVariable));
			
					
		}else if(action.equals("initEditSales")){ 
			
			
			/*
			String channel_id	= request.getParameter("channel_id");
			String revenue_date	= request.getParameter("revenue_date");
			String amount	= request.getParameter("amount");
			String revenue_type	= request.getParameter("revenue_type");
			*/
			
			String salesinfo	= request.getParameter("salesinfo");
		    String[] dateRaw = salesinfo.split(":"); 
		    String channel_id=dateRaw[0];
		    String revenue_date=dateRaw[1];
		    String revenue_type=dateRaw[2];
			
		
			
			Map det=new HashMap();
			det.put("channel_id",channel_id);
			det.put("revenue_date",ch.transposeDate(revenue_date));
			det.put("revenue_type",revenue_type);
			ResultSet detSales=sales.loadSales(det);
			request.setAttribute("detSales",detSales);
			
			view = request.getRequestDispatcher("sales/initEditSales.jsp");
	 		response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			view.forward(request, response);
			
       }else if(action.equals("processEditSales")){
			
			
    	   String channel_id	= request.getParameter("channel_id");
			String revenue_date	= request.getParameter("revenue_date");
			String amount	= request.getParameter("amount");
			String revenue_type	= request.getParameter("revenue_type");
			
			
			int errorCounter=0;
			
			if(ch.checkMemberSession(sess)){
				iVariable.put("logged",true);	
				
				
				if(revenue_date==null || revenue_date==""){
					iVariable.put("status","Please fill up revenue date");
					errorCounter=+1;
				}
				
				if(amount==null || amount==""){
					iVariable.put("status","Please fill up amount");
					errorCounter=+1;
				}
				
				
				
				if(errorCounter==0){
					iVariable.put("process",true);
					
					//iVariable.put("process",false);
						
					Map det=new HashMap();
					det.put("channel_id",channel_id);
					
					if(revenue_date != null && revenue_date != ""){
						det.put("revenue_date",ch.transposeDate(revenue_date));
					}
					
					det.put("revenue_type",revenue_type);
					det.put("amount",amount);
					
					//Process Insert
					
					if(sales.UpdateSales(det)){
						
						//Also Add the default Privilege
						////////////////////////////////////////
						iVariable.put("process",true);
						iVariable.put("status","Revenue has been updated");
						
					}else{
						iVariable.put("process",false);
						iVariable.put("status","Error on updating revenue");
					}
						
				}else{
					iVariable.put("process",false);
				}
				
			}else{
				iVariable.put("logged",false);	
			}
			
			
			//application/json
			response.setContentType("text/html");
			PrintWriter pWrite = response.getWriter();
			pWrite.println(JSONValue.toJSONString(iVariable));
			
		}else if(action.equals("initDeleteSales")){
			
			String salesinfo	= request.getParameter("salesinfo");
		    String[] dateRaw = salesinfo.split(":"); 
		    String channel_id=dateRaw[0];
		    String revenue_date=dateRaw[1];
		    String revenue_type=dateRaw[2];
			
		    
			Map det=new HashMap();
			det.put("channel_id",channel_id);
			det.put("revenue_date",ch.transposeDate(revenue_date));
			det.put("revenue_type",revenue_type);
			ResultSet detSales=sales.loadSales(det);
			request.setAttribute("detSales",detSales);
			
			request.setAttribute("salesinfo",salesinfo);
		 	
			view = request.getRequestDispatcher("sales/initDeleteSales.jsp");
		 	
		 	response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			view.forward(request, response);
			
	   }else if(action.equals("processDeleteSales")){
			
		   
		    String salesinfo	= request.getParameter("salesinfo");
		    String[] dateRaw = salesinfo.split(":"); 
		    String channel_id=dateRaw[0];
		    String revenue_date=dateRaw[1];
		    String revenue_type=dateRaw[2];
			
		    
			Map det=new HashMap();
			det.put("channel_id",channel_id);
			det.put("revenue_date",ch.transposeDate(revenue_date));
			det.put("revenue_type",revenue_type);
		   
		   
			if(sales.DeleteSales(det)){
				iVariable.put("process",true);
				iVariable.put("status","Revenue has been deleted");
			}else{
				iVariable.put("process",false);
				iVariable.put("status","Revenue has not been deleted");
			}
			
			
			//application/json
			response.setContentType("text/html");
			PrintWriter pWrite = response.getWriter();
			pWrite.println(JSONValue.toJSONString(iVariable));	
	  }
		
		
		
		/*
		else if(action.equals("initDeleteChannel")){
			
			String channel_id = request.getParameter("channel_id");
			
			Map det=new HashMap();
			det.put("channel_id",channel_id);
			ResultSet detChannel=channel.loadChannel(det);
			request.setAttribute("detChannel",detChannel);
		 	
			view = request.getRequestDispatcher("channel/initDeleteChannel.jsp");
		 	
		 	response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			view.forward(request, response);
			
	   }else if(action.equals("processDeleteChannel")){
			
			String channel_id	= request.getParameter("channel_id");
			if(channel.DeleteChannel(channel_id)){
				iVariable.put("process",true);
				iVariable.put("status","Channel has been deleted");
			}else{
				iVariable.put("process",false);
				iVariable.put("status","Channel has not been deleted");
			}
			
			//application/json
			response.setContentType("text/html");
			PrintWriter pWrite = response.getWriter();
			pWrite.println(JSONValue.toJSONString(iVariable));	
	  }
		*/
		 
	}//end of function
	
	
}//end of class
