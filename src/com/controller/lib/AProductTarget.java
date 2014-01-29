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
@WebServlet("/AProductTarget")
public class AProductTarget extends HttpServlet {
	private static final long serialVersionUID = 1L;
	   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AProductTarget() {
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
		TargetModel tar=new TargetModel();
		tar.projectFile=getServletContext().getRealPath("");
		
		HttpSession sess=request.getSession();
		
		if(action.equals("initAddTarget")){
		
			ResultSet listDepartment=tar.loadDepartment();
			request.setAttribute("listDepartment",listDepartment);
			
			ResultSet listTargetGroup=tar.loadTargetGroup();
			request.setAttribute("listTargetGroup",listTargetGroup);
			
			ResultSet listProducts = tar.loadProducts(iVariable);
			request.setAttribute("listProducts",listProducts);
			
			view = request.getRequestDispatcher("producttarget/initAddTarget.jsp");
		 	response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			view.forward(request, response);
		
		}else if(action.equals("processAddTarget")){
			
			String target_date	= request.getParameter("target_date");
			String target_amount	= request.getParameter("target_amount");
			String target_group_id	= request.getParameter("target_group_id");
			String channel_id	= request.getParameter("channel_id");
	
			int errorCounter=0;
			
			if(ch.checkMemberSession(sess)){
				iVariable.put("logged",true);	
				
				
				if(target_date==null || target_date==""){
					iVariable.put("status","Please fill up target date");
					errorCounter=+1;
				}
				
				if(target_amount==null || target_amount==""){
					iVariable.put("status","Please fill up target amount");
					errorCounter=+1;
				}
				
				
				if(errorCounter==0){
					iVariable.put("process",true);
					
					//iVariable.put("process",false);
					Map det=new HashMap();
						
					if(target_date != null && target_date != ""){
						det.put("target_date",ch.transposeDate(target_date));
					}
					
					det.put("target_amount",target_amount);
					det.put("target_group_id",target_group_id);
					det.put("channel_id",channel_id);
					

					//Process Insert
					
					if(tar.InsertProductTarget(det)){
						
						//Also Add the default Privilege
						////////////////////////////////////////
						iVariable.put("process",true);
						iVariable.put("status","New target has been added");
						
					}else{
						iVariable.put("process",false);
						iVariable.put("status","Error on adding target");
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
			
					
		}if(action.equals("loadDeparmentUser")){
			
			String department_id	= request.getParameter("department_id");
			String html="";
			Map det=new HashMap();
			det.put("department_id",department_id);
			ResultSet rs=tar.loadDepartmentUser(det);
			
			
			 html+="<option value='0'>All</option>";
			
			 try {
				if (rs.next()) {  
			
					do{
						  html+="<option value='"+rs.getString("userid")+"'>"+rs.getString("userfirstname")+" "+rs.getString("userlastname")+"</option>";
					} while (rs.next());
					
					}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			 	
			iVariable.put("html",html);
			//application/json
			response.setContentType("text/html");
			PrintWriter pWrite = response.getWriter();
			pWrite.println(JSONValue.toJSONString(iVariable));
			
		}else if(action.equals("loadTarget")){
			
			Map det=new HashMap();
			String target_date	= request.getParameter("target_date");
			String target_amount	= request.getParameter("target_amount");
			String target_group_id	= request.getParameter("target_group_id");
			String category = request.getParameter("category");
			String channel_id = request.getParameter("channel_id");
			String user	= request.getParameter("user");
			
			
			if(target_date != null && target_date != ""){
				det.put("target_date",ch.transposeDate(target_date));
			}
			
			det.put("target_amount",target_amount);
			det.put("target_group_id",target_group_id);
			det.put("category",category);
			det.put("channel_id",channel_id);
			det.put("user",user);
			
			
			ResultSet listTarget=tar.loadProductTarget(det);
			request.setAttribute("listTarget",listTarget);
			
		 	view = request.getRequestDispatcher("producttarget/result_TargetList.jsp");
		 	
		 	response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			view.forward(request, response);
		
		}else if(action.equals("initEditTarget")){ 
			
			String target_id	= request.getParameter("target_id");
	 		
		
			
			ResultSet listTargetGroup=tar.loadTargetGroup();
			request.setAttribute("listTargetGroup",listTargetGroup);
			
			Map det=new HashMap();
			det.put("target_id",target_id);
			ResultSet detTarget=tar.loadProductTarget(det);
			request.setAttribute("detTarget",detTarget);
			
			ResultSet fetDynamicDepartment=tar.loadProductTarget(det);
			
			String html="";
			String strSelected="";
			html+="<option value='0'>All</option>";
//			
			 request.setAttribute("optionHtml",html);
			
			view = request.getRequestDispatcher("producttarget/initEditTarget.jsp");
	 		response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			view.forward(request, response);
			
       }else if(action.equals("processEditTarget")){
			
			
    	    String target_date	= request.getParameter("target_date");
			String target_amount	= request.getParameter("target_amount");
			String target_group_id	= request.getParameter("target_group_id");
			String channel_id	= request.getParameter("channel_id");
			String target_id	= request.getParameter("target_id");
	
			int errorCounter=0;
			
			if(ch.checkMemberSession(sess)){
				iVariable.put("logged",true);	
				
				
				if(target_date==null || target_date==""){
					iVariable.put("status","Please fill up target date");
					errorCounter=+1;
				}
				
				if(target_amount==null || target_amount==""){
					iVariable.put("status","Please fill up target amount");
					errorCounter=+1;
				}
				
				
				if(errorCounter==0){
					iVariable.put("process",true);
					
					//iVariable.put("process",false);
					Map det=new HashMap();
						
					if(target_date != null && target_date != ""){
						det.put("target_date",ch.transposeDate(target_date));
					}
					
					det.put("target_amount",target_amount);
					det.put("target_group_id",target_group_id);
					det.put("channel_id",channel_id);
					det.put("target_id",target_id);

					//Process Insert
					
					if(tar.UpdateProductTarget(det)){
						
						//Also Add the default Privilege
						////////////////////////////////////////
						iVariable.put("process",true);
						iVariable.put("status","Target has been updated");
						
					}else{
						iVariable.put("process",false);
						iVariable.put("status","Error on updating target");
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
			
		}else if(action.equals("initDeleteTarget")){
			
			String target_id = request.getParameter("target_id");
			
			Map det=new HashMap();
			det.put("target_id",target_id);
			System.out.println(target_id);
			ResultSet detTarget=tar.loadProductTarget(det);
			request.setAttribute("detProductTarget",detTarget);

			view = request.getRequestDispatcher("producttarget/initDeleteTarget.jsp");

		 	response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			try{
				view.forward(request, response);
			}
			catch(NullPointerException e){
				System.out.println("ERROR:");
				e.printStackTrace();
			}
			
	   }else if(action.equals("processDeleteTarget")){
			
			String target_id	= request.getParameter("target_id");
			if(tar.DeleteProductTarget(target_id)){
				iVariable.put("process",true);
				iVariable.put("status","Target has been deleted");
			}else{
				iVariable.put("process",false);
				iVariable.put("status","Target has not been deleted");
			}
			
			//application/json
			response.setContentType("text/html");
			PrintWriter pWrite = response.getWriter();
			pWrite.println(JSONValue.toJSONString(iVariable));	
	  }
		
		 
	}//end of function
	
	
}//end of class
