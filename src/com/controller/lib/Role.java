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
 * Servlet implementation class ARole
 */
@WebServlet(description = "Role servlet", urlPatterns = { "/Roles" })
public class Role extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Role() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action	= request.getParameter("action");
		HttpSession sess=request.getSession();
		CustomHelper ch=new CustomHelper();
		Map iVariable=new HashMap();
		UserModel usr=new UserModel();
		usr.projectFile=getServletContext().getRealPath("");

		RoleModel role=new RoleModel();
	
		role.projectFile=getServletContext().getRealPath("");
		RequestDispatcher view=null;
		Boolean useDispatcher=false;
		 
		if(ch.checkMemberSession(sess)){
			
			if(action==null){
				
				 Map det=new HashMap();
				 ResultSet roleList=role.loadRole();
					request.setAttribute("roleList",roleList);
					view = request.getRequestDispatcher("role/main.jsp");
			 	
					response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
					response.setHeader("Pragma", "no-cache");
					view.forward(request, response);
					useDispatcher=true;
			}

		}
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
		RoleModel role=new RoleModel();
		CustomHelper ch=new CustomHelper();
		role.projectFile=getServletContext().getRealPath("");
		
		
		HttpSession sess=request.getSession();
		
		
		if(action.equals("initAdd")){
			
			
			ResultSet moduleList=role.loadModule();
			ResultSet privilegeList=role.loadPrivilege();
			request.setAttribute("moduleList",moduleList);
			try {
				if(moduleList.next()){
					do{
						System.out.println(moduleList.getString("modulename"));
					}while(moduleList.next());
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("privilegeList",privilegeList);
			
			view = request.getRequestDispatcher("role/initAddRole.jsp"); 
		 	
		 	response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			view.forward(request, response);
			
		}else if(action.equals("processAdd")){
			
			String rolename	= request.getParameter("rolename");
			int errorCounter=0;
			
			if(ch.checkMemberSession(sess)){
				iVariable.put("logged",true);	
				
				if(rolename==null || rolename==""){
					iVariable.put("status","Please fill up Role Name");
					errorCounter=+1;
				}
				
				
				if(errorCounter==0){
					iVariable.put("process",true);
					
					//iVariable.put("process",false);
					Map det=new HashMap();
					det.put("rolename",rolename);
					
					//Process Insert
					
					if(role.InsertRole(det,sess)){
						
						
						//Also Add the default Priviledge
						////////////////////////////////////////
						//role.roleid
	
						ResultSet lModule=role.loadModule();
						ResultSet lPrivilege=role.loadPrivilege();
						String privilege_mode="";
						
						try {
							
							String moduleStatus="disabled";
							Map detVariable=new HashMap();
							
							while(lModule.next()){
								
									moduleStatus	= request.getParameter("module_status"+lModule.getString("moduleid"));
								
								while(lPrivilege.next()){
									privilege_mode+=request.getParameter("privilege_status_"+lModule.getString("moduleid")+"_"+lPrivilege.getString("privilegeid"));
										
								}
								
								lPrivilege.beforeFirst();
								
								detVariable=new HashMap();
								detVariable.put("roleid",role.roleid);
								detVariable.put("moduleid",lModule.getString("moduleid"));
								detVariable.put("privilege_mode",privilege_mode);
								detVariable.put("privilege_status",moduleStatus);
								role.InsertRolePrivilege(detVariable);
								
								detVariable.clear();
								privilege_mode="";
								
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
							
						iVariable.put("process",true);
						iVariable.put("status","New role has been added");
						
						
					}else{
						iVariable.put("process",false);
						iVariable.put("status","Error on adding role");
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
			
					
		}else if(action.equals("subLoadRole")){
			
			Map det=new HashMap();
		
			ResultSet roleList=role.loadRole();
			request.setAttribute("roleList",roleList);
			view = request.getRequestDispatcher("role/result_RoleList.jsp");
	 	
			response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			view.forward(request, response);
				
			
		}else if(action.equals("initDelete")){
			
			
			String roleid = request.getParameter("roleid");
			
			Map thisRole=role.fetchRole(Integer.parseInt(roleid));
			request.setAttribute("rolename",thisRole.get("rolename"));
			request.setAttribute("roleid",thisRole.get("roleid"));
		 	view = request.getRequestDispatcher("role/initDelete.jsp");
		 	
		 	response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			view.forward(request, response);
			
	   }else if(action.equals("processDelete")){
			
			
			String roleid	= request.getParameter("roleid");
			
			if(role.DeleteRole(roleid)){
				role.DeleteRolePrivilege(roleid);
				iVariable.put("process",true);
				iVariable.put("status","Role has been deleted");
			}else{
				iVariable.put("process",false);
				iVariable.put("status","Error:Role has not been deleted");
			}
			
			//application/json
			response.setContentType("text/html");
			PrintWriter pWrite = response.getWriter();
			pWrite.println(JSONValue.toJSONString(iVariable));	
			
	   }else if(action.equals("initEdit")){
			
		   
		   	String roleid	= request.getParameter("roleid");
		   	Map roleDet=role.fetchRole(Integer.parseInt(roleid));
			
			ResultSet moduleList=role.loadModule();
			ResultSet privilegeList=role.loadPrivilege();
			
			ResultSet moduleListSettings=role.loadModule();
			ResultSet privilegeListSettings=role.loadPrivilege();
			
			
			int iCtr=0;
			try {
				while(moduleListSettings.next()){
					
					int iroleid= Integer.parseInt(roleid);
					int imoduleid= Integer.parseInt(moduleListSettings.getString("moduleid"));
					
					Map detP=role.fetchRolePrivilege(iroleid, imoduleid);
					
					
					String pMode="";
					if(!detP.isEmpty()){
						pMode=(String)detP.get("privilege_mode");
					}
					
					
					while(privilegeListSettings.next()){
					
						if(!detP.isEmpty()){
							//Extract+
							
																					//
							if(iCtr>=pMode.length()){
								request.setAttribute("modulePrivilegeStatus_"+moduleListSettings.getString("moduleid")+"_"+privilegeListSettings.getString("privilegeid"),"0");
							}else{
								request.setAttribute("modulePrivilegeStatus_"+moduleListSettings.getString("moduleid")+"_"+privilegeListSettings.getString("privilegeid"),""+pMode.charAt(iCtr)+"");
							}
							
							
						}else{
							request.setAttribute("modulePrivilegeStatus_"+moduleListSettings.getString("moduleid")+"_"+privilegeListSettings.getString("privilegeid"),"1");
							
						}
						iCtr++;
					}
					
						iCtr=0;
					
						privilegeListSettings.beforeFirst();
						
					if(detP.isEmpty()){
						request.setAttribute("moduleStatus"+moduleListSettings.getString("moduleid"),"disabled");
					}else{	
						request.setAttribute("moduleStatus"+moduleListSettings.getString("moduleid"),detP.get("privilege_status"));
					}	
					
					
					}//end of while
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			request.setAttribute("roleid",roleDet.get("roleid"));
			request.setAttribute("rolename",roleDet.get("rolename"));
			request.setAttribute("moduleList",moduleList);
			request.setAttribute("privilegeList",privilegeList);
			
			view = request.getRequestDispatcher("role/initEditRole.jsp");
		 	
		 	response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			view.forward(request, response);
			
		
	   }else if(action.equals("processEdit")){
			
			String roleid	= request.getParameter("roleid");
			String rolename	= request.getParameter("rolename");
			int errorCounter=0;
			
			if(ch.checkMemberSession(sess)){
				iVariable.put("logged",true);	
				
				if(rolename==null || rolename==""){
					iVariable.put("status","Please fill up Role Name");
					errorCounter=+1;
				}
				
				
				if(errorCounter==0){
					iVariable.put("process",true);
					
					//iVariable.put("process",false);
					Map det=new HashMap();
					det.put("roleid",roleid);
					det.put("rolename",rolename);
					
					//Process Insert
					
					if(role.UpdateRole(det)){
						
						
						//Also Add the default Priviledge
						////////////////////////////////////////
						//role.roleid
	
						ResultSet lModule=role.loadModule();
						ResultSet lPrivilege=role.loadPrivilege();
						String privilege_mode="";
						
						Map detP=new HashMap();
						
						try {
							
							String moduleStatus="disabled";
							Map detVariable=new HashMap();
							
							while(lModule.next()){
								
									moduleStatus	= request.getParameter("module_status"+lModule.getString("moduleid"));
								
								while(lPrivilege.next()){
									privilege_mode+=request.getParameter("privilege_status_"+lModule.getString("moduleid")+"_"+lPrivilege.getString("privilegeid"));
										
								}
								
								lPrivilege.beforeFirst();
								
								
								detP=role.fetchRolePrivilege(Integer.parseInt(roleid),Integer.parseInt(lModule.getString("moduleid")));
								
								
								detVariable=new HashMap();
								detVariable.put("roleid",roleid);
								detVariable.put("moduleid",lModule.getString("moduleid"));
								detVariable.put("privilege_mode",privilege_mode);
								detVariable.put("privilege_status",moduleStatus);
								
								if(detP.isEmpty()){
										role.InsertRolePrivilege(detVariable);
								}else{
										role.UpdateRolePrivilege(detVariable);
								}
								
								detVariable.clear();
								privilege_mode="";
								detP.clear();
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
							
						iVariable.put("process",true);
						iVariable.put("status","Role has been updated");
						
						
					}else{
						iVariable.put("process",false);
						iVariable.put("status","Error on adding role");
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
			
					
		}
		
		
	}//end of function
	
	
	
	

}//end of class
