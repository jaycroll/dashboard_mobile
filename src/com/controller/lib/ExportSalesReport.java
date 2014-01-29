package com.controller.lib;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.misc.lib.CustomHelper;
import com.model.lib.ProjectionModel;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@WebServlet(description = "Projectioon servlet", urlPatterns = { "/ExportSales" })
public class ExportSalesReport extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private String strName="Testing";
	
	 public ExportSalesReport() {
	        super();
	        // TODO Auto-generated constructor stub
	        
	    }

	 
	 	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	 	{
	 		
			String action	= request.getParameter("action");
			HttpSession sess=request.getSession();
			CustomHelper ch=new CustomHelper();
			
			
			
			
			if(ch.checkMemberSession(sess)){
		
				ProjectionModel proj=new ProjectionModel();
				proj.projectFile=getServletContext().getRealPath("");
				
				RequestDispatcher view=null;
				Boolean useDispatcher=false;
				
				if(action==null){
					
					////////////////////////////////////////////////////////
					//For Year
					/*
					 Map det=new HashMap();
					 request.setAttribute("banner","dashboard");
					
					
					//Monthly
					det.put("fromMonth",ch.formatDate("yyyy-MM"));
					det.put("toMonth",ch.formatDate("yyyy-MM-dd"));
					
					//Yearly
					det.put("fromYear",ch.formatDate("yyyy"));
					det.put("toYear",ch.formatDate("yyyy-MM-dd"));
					
					ResultSet rDetails=proj.loadProjectionA(det);
					request.setAttribute("rDetails",rDetails);
					*/
				
					////////////////////////////////////////////////////////
					
				}else if(action.equals("export")){
					
					String month	= request.getParameter("month");
					Date d = new Date();
					Calendar cal = Calendar.getInstance(); 
					cal.setTime(d);  
					cal.add(Calendar.DATE, -1); // add 10 days 
					  
					Date nextday = cal.getTime();
					if (month.isEmpty())
					{
						DateFormat dateFormat = new SimpleDateFormat("MM");
						month = dateFormat.format(nextday);
					}
					String yr =  request.getParameter("year");
					if (yr.isEmpty())
					{
						DateFormat dateFormat = new SimpleDateFormat("yyyy");
						yr = dateFormat.format(nextday);
					}
					////////////////////////////////////////////////////////
					//////////////////////For Monthly///////////////////////
					 String mimetype = "multipart/related";

					String viewfile = getServletContext().getRealPath("") + File.separator + "export/salesreport-" + month + yr + ".mht";
						String viewfilev =  "export/salesreport-" + month + yr + ".mht";
					

					useDispatcher=true;


					try {
						
						File file = new File(viewfile);
						int BUFSIZE= 4096;
						response.setContentType(mimetype);
						 response.setContentLength(-1);
						 response.setBufferSize((int)file.length());
						  String fileName = file.getName();
//							 response.setHeader("Content-Disposition", "infile; filename=\"" + fileName+ "\"");
//						   view = request.getRequestDispatcher(viewfilev);
//							view.forward(request, response);
						  int length   = 0;
				        byte[] byteBuffer = new byte[BUFSIZE];
				        DataInputStream in = new DataInputStream(new FileInputStream(file));
				        BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());
				       System.out.println("viewfile=" + viewfile);
				       System.out.println("readfile size=" + file.length());
//				        // reads the file's bytes and writes them to the response stream
				        int totalsize = 0;
				        while ((in != null) && ((length = in.read(byteBuffer)) != -1))
				        {
				            outStream.write(byteBuffer,0,length);
				            outStream.flush();
				            totalsize += length;
				            System.out.println("read : " + totalsize + " of " + file.length());
				        }
				         System.out.println("done sending:" + viewfile);
				        in.close();
				        outStream.close();
					}catch (FileNotFoundException e) {
			            e.printStackTrace();
			            response.sendError(404);
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
					
				        
//				        byte buf[] = new byte[1024 * 4];
//				        try {
//				        	File file = new File(viewfile);
//							response.setContentType(mimetype);
//					        response.setContentLength((int)file.length());
//					        String fileName = file.getName();
//				            ServletOutputStream out = response.getOutputStream();
//				            IOUtils.copy(new FileInputStream(file), out);
//				          //  response.setHeader("Content-Disposition", "infile; filename=\"" +
//				          //   fileName+ "\"");
//				        //    response.setContentLength(file.length());
//				            out.flush();
//				        } catch (FileNotFoundException e) {
//				            e.printStackTrace();
//				            response.sendError(404);
//				        } catch (Exception e) {
//				            e.printStackTrace();
//				        }
//				        
					
					
					////////////////////////////////////////////////////////
				}
				
				
				if (!useDispatcher)
				{
					String viewfile = "salesreport/noreport.html";
					view = request.getRequestDispatcher(viewfile);
					view.forward(request, response);
				}
				
				
			}else{
				response.sendRedirect("/Dashboard");
			}
			 
	 	}
		/**
		 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
		 */
		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// TODO Auto-generated method stub
			doProcess(request,response);
				
			
			 
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
			doProcess(request,response);
		}


}
