package com.controller.lib;

import com.misc.lib.CustomHelper;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
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
@WebServlet("/AChannel")
public class AChannel extends HttpServlet {
	private static final long serialVersionUID = 1L;
	   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AChannel() {
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
		
		
		HttpSession sess=request.getSession();
		
		if(action.equals("initAddChannel")){
			
			ResultSet listChannelGroup=channel.loadChannelGroup();
			request.setAttribute("listChannelGroup",listChannelGroup);
			
			ResultSet listApp=channel.loadApp();
			request.setAttribute("listApp",listApp);
		
			
			view = request.getRequestDispatcher("channel/initAddChannel.jsp");
		 	response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			view.forward(request, response);
			
		
		}else if(action.equals("processAddChannel")){
			
			String channel_id	= request.getParameter("channel_id");
			String channel_name	= request.getParameter("channel_name");
			String channel_group_id	= request.getParameter("channel_group_id");
			String channel_automated	= request.getParameter("channel_automated");
			String app_id	= request.getParameter("app_id");
	
			int errorCounter=0;
			
			if(ch.checkMemberSession(sess)){
				iVariable.put("logged",true);	
				
				
				if(channel_id==null || channel_id==""){
					iVariable.put("status","Please fill up channel id");
					errorCounter=+1;
				}
				
				if(channel_name==null || channel_name==""){
					iVariable.put("status","Please fill up channel name");
					errorCounter=+1;
				}
				
				
				if(errorCounter==0){
					iVariable.put("process",true);
					
					//iVariable.put("process",false);
					Map det=new HashMap();
					
					det.put("channel_id",channel_id);
					det.put("channel_name",channel_name);
					det.put("channel_group_id",channel_group_id);
					det.put("channel_automated",channel_automated);
					det.put("app_id",app_id);

					//Process Insert
					
					if(channel.InsertChannel(det)){
						
						//Also Add the default Privilege
						////////////////////////////////////////
						iVariable.put("process",true);
						iVariable.put("status","New channel has been added");
						
					}else{
						iVariable.put("process",false);
						iVariable.put("status","Error on adding channel");
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
			
					
		}else if(action.equals("loadChannel")){
			
			Map det=new HashMap();
			String channel_id	= request.getParameter("channel_id");
			String channel_name	= request.getParameter("channel_name");
			String channel_group_id	= request.getParameter("channel_group_id");
			String channel_automated	= request.getParameter("channel_automated");
			String app_id	= request.getParameter("app_id");
			
			det.put("channel_id",channel_id);
			det.put("channel_name",channel_name);
			det.put("channel_group_id",channel_group_id);
			det.put("channel_automated",channel_automated);
			det.put("app_id",app_id);
			
			ResultSet listChannel=channel.loadChannel(det);
			request.setAttribute("listChannel",listChannel);
			
		 	view = request.getRequestDispatcher("channel/result_ChannelList.jsp");
		 	
		 	response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			view.forward(request, response);
		
		}else if(action.equals("initEditChannel")){ 
			
			String channel_id	= request.getParameter("channel_id");
	 		
			ResultSet listChannelGroup=channel.loadChannelGroup();
			request.setAttribute("listChannelGroup",listChannelGroup);
			
			ResultSet listApp=channel.loadApp();
			request.setAttribute("listApp",listApp);
			
			
			Map det=new HashMap();
			det.put("channel_id",channel_id);
			ResultSet detChannel=channel.loadChannel(det);
			request.setAttribute("detChannel",detChannel);
		
			
			view = request.getRequestDispatcher("channel/initEditChannel.jsp");
	 		response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			view.forward(request, response);
			
       }else if(action.equals("processEditChannel")){
			
			
    	    String channel_id	= request.getParameter("channel_id");
			String channel_name	= request.getParameter("channel_name");
			String channel_group_id	= request.getParameter("channel_group_id");
			String channel_automated	= request.getParameter("channel_automated");
			String app_id	= request.getParameter("app_id");
			
			
			int errorCounter=0;
			
			if(ch.checkMemberSession(sess)){
				iVariable.put("logged",true);	
				
				
				if(channel_id==null || channel_id==""){
					iVariable.put("status","Please fill up channel id");
					errorCounter=+1;
				}
				
				if(channel_name==null || channel_name==""){
					iVariable.put("status","Please fill up channel name");
					errorCounter=+1;
				}
				
				
				
				if(errorCounter==0){
					iVariable.put("process",true);
					
					//iVariable.put("process",false);
					Map det=new HashMap();
					
					det.put("channel_id",channel_id);
					det.put("channel_name",channel_name);
					det.put("channel_group_id",channel_group_id);
					det.put("channel_automated",channel_automated);
					det.put("app_id",app_id);
					//Process Insert
					
					if(channel.UpdateChannel(det)){
						
						//Also Add the default Privilege
						////////////////////////////////////////
						iVariable.put("process",true);
						iVariable.put("status","Channel has been updated");
						
					}else{
						iVariable.put("process",false);
						iVariable.put("status","Error on updating channel");
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
			
		}else if(action.equals("initDeleteChannel")){
			
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
		 
	}//end of function
	
	
}//end of class
