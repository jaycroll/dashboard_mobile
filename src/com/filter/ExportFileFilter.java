package com.filter;
import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.misc.lib.CustomHelper;
import com.misc.lib.RolePermission;

public class ExportFileFilter implements Filter {
	  @Override
	public void doFilter(ServletRequest req, ServletResponse res,
	            FilterChain chain) throws IOException, ServletException {
	 
	        HttpServletRequest request = (HttpServletRequest) req;
	         
	    	HttpSession sess=request.getSession();
			CustomHelper ch=new CustomHelper();
			
			
			
			
			if(ch.checkMemberSession(sess)){
				System.out.println(sess.getAttribute("roleid").toString());
				System.out.println(sess.getAttribute("username").toString());
				RolePermission rp=new RolePermission();
				System.out.println(this.realpath);
				rp.settingUrl=this.realpath;
				
				  if(rp.verifyModule(Integer.parseInt(sess.getAttribute("roleid").toString()),7,6)){
					  System.out.println("fetching export sales : " +request.getRequestURI());
					  chain.doFilter(req, res);  
				  }
				  else
				  {
					  System.out.println("ExportFileFilter: invalid permission");
					  throw new IOException("invalid permission");
				  }
			}
			else
			{
				  System.out.println("ExportFileFilter: not logged in");
				throw new ServletException("not logged in");
			}
	    }
	  public String realpath = "/";
	@Override
	public void init(FilterConfig config) throws ServletException {
		this.realpath = config.getServletContext().getRealPath("/");
	}
	  @Override
	public void destroy() {
	        //add code to release any resource
	    }

}
