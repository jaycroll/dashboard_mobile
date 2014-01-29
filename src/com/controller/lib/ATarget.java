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
@WebServlet("/ATarget")
public class ATarget extends HttpServlet {
	private static final long serialVersionUID = 1L;
	   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ATarget() {
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
			
			view = request.getRequestDispatcher("target/initAddTarget.jsp");
		 	response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			view.forward(request, response);
		
		}else if(action.equals("processAddTarget")){
			
			String target_date	= request.getParameter("target_date");
			String target_amount	= request.getParameter("target_amount");
			String target_group_id	= request.getParameter("target_group_id");
			String department_id	= request.getParameter("department_id");
			String user_id	= request.getParameter("user_id");
	
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
					det.put("department_id",department_id);
					det.put("user_id",user_id);

					//Process Insert
					
					if(tar.InsertTarget(det)){
						
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
			String user	= request.getParameter("user");
			String department_id	= request.getParameter("department_id");
			
			if(target_date != null && target_date != ""){
				det.put("target_date",ch.transposeDate(target_date));
			}
			
			det.put("target_amount",target_amount);
			det.put("target_group_id",target_group_id);
			det.put("department_id",department_id);
			det.put("user",user);
			
			
			ResultSet listTarget=tar.loadTarget(det);
			request.setAttribute("listTarget",listTarget);
			
		 	view = request.getRequestDispatcher("target/result_TargetList.jsp");
		 	
		 	response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			view.forward(request, response);
		
		}else if(action.equals("initEditTarget")){ 
			
			String target_id	= request.getParameter("target_id");
	 		
			ResultSet listDepartment=tar.loadDepartment();
			request.setAttribute("listDepartment",listDepartment);
			
			ResultSet listTargetGroup=tar.loadTargetGroup();
			request.setAttribute("listTargetGroup",listTargetGroup);
			
			Map det=new HashMap();
			det.put("target_id",target_id);
			ResultSet detTarget=tar.loadTarget(det);
			request.setAttribute("detTarget",detTarget);
			
			ResultSet fetDynamicDepartment=tar.loadTarget(det);
			
			String html="";
			String strSelected="";
			html+="<option value='0'>All</option>";
			 try {
					if (fetDynamicDepartment.next()) {  
				
						do{
								
							Map det2=new HashMap();
							det2.put("department_id",fetDynamicDepartment.getString("department_id"));
							ResultSet rs2=tar.loadDepartmentUser(det2);
							
							 try {
								if (rs2.next()) {  
							
									do{
										
										
										if(fetDynamicDepartment.getString("userid")==null){
											   strSelected="";
										}else{
											if(rs2.getString("userid").equals(fetDynamicDepartment.getString("userid").toString())){
												strSelected="selected";
											}else{
												strSelected="";
											}
										}	
										
										  html+="<option value='"+rs2.getString("userid")+"' "+strSelected+">"+rs2.getString("userfirstname")+" "+rs2.getString("userlastname")+"</option>";
									} while (rs2.next());
									
									}
								
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}	
							 	
							
							
							
						} while (fetDynamicDepartment.next());
						
						}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 request.setAttribute("optionHtml",html);
			
			view = request.getRequestDispatcher("target/initEditTarget.jsp");
	 		response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			view.forward(request, response);
			
       }else if(action.equals("processEditTarget")){
			
			
    	    String target_date	= request.getParameter("target_date");
			String target_amount	= request.getParameter("target_amount");
			String target_group_id	= request.getParameter("target_group_id");
			String department_id	= request.getParameter("department_id");
			String user_id	= request.getParameter("user_id");
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
					det.put("department_id",department_id);
					det.put("user_id",user_id);
					det.put("target_id",target_id);

					//Process Insert
					
					if(tar.UpdateTarget(det)){
						
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
			ResultSet detTarget=tar.loadTarget(det);
			request.setAttribute("detTarget",detTarget);
		 	
			view = request.getRequestDispatcher("target/initDeleteTarget.jsp");
		 	
		 	response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			view.forward(request, response);
			
	   }else if(action.equals("processDeleteTarget")){
			
			String target_id	= request.getParameter("target_id");
			if(tar.DeleteTarget(target_id)){
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
