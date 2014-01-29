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

@WebServlet(description="Product Sales Projection", urlPatterns={"/AInternationalSales"})

public class AInternationalSales extends HttpServlet{
		private static final long serialVersionID = 1L;
		
		public AInternationalSales(){
			super();
		}
		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			
		
		}
		@Override
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			
			String action = request.getParameter("action");
			HttpSession session = request.getSession();
			CustomHelper ch = new CustomHelper();
			Map thisVar = new HashMap();
			ProjectionModel proj = new ProjectionModel();
			proj.projectFile = getServletContext().getRealPath("");
			ChannelModel chModel = new ChannelModel();
			chModel.projectFile = getServletContext().getRealPath("");
			SalesModel salesModel = new SalesModel();
			salesModel.projectFile = getServletContext().getRealPath("");
			RequestDispatcher view = null;
			Boolean useDispatcher = false;
			//System.out.println("it gets here");
			if(ch.checkMemberSession(session)){
				
				if(action.equals("loadMonth")){
					Map ProjectionMap = new HashMap();
					Map requestMap = new HashMap();

					useDispatcher = true;
					view = request.getRequestDispatcher("productsales/loadMonth.jsp");	
					//request.setAttribute("asd","asd");
					
					ProjectionMap.put("location","International");
					String[][][] productArray = new String[100][100][100];
					
					ResultSet channels = chModel.loadChannelByLocation(ProjectionMap);
					int[]  i = new int[1];
					
					try{
						if(channels.next()){
							do{
								System.out.println(channels.getString("channel_id")+": ");
								ProjectionMap.put("channelid",channels.getString("channel_id"));
								productArray[i[0]][0][0] = channels.getString("channel_id");
								//System.out.println(productArray[i[0]][0][0]);
								//ResultSet yearSales = salesModel.ldYearRevenue2(ProjectionMap);
								ResultSet monthSales = salesModel.ldProductMonthlyRevenue(ProjectionMap,false);
										try{
											if(monthSales.next()){
												do{
													productArray[i[0]][1][Integer.parseInt(monthSales.getString("month"))] = monthSales.getString("actual_revenue");
													System.out.println(Integer.parseInt(monthSales.getString("month"))+"-"+productArray[i[0]][1][Integer.parseInt(monthSales.getString("month"))]);
												}while(monthSales.next());
												
											}
										}catch(SQLException e){
											
										}
										productArray[i[0]][3][0] = channels.getString("channel_name");
										i[0]++;
								
							}while(channels.next());
							
						}
					}catch(SQLException e){
						e.printStackTrace();
					}
					
					request.setAttribute("productArray2",productArray);
					request.setAttribute("channels", i);
					//System.out.println(i[0]);
					
				}
				if(useDispatcher=true){
					response.setHeader("Cache-control","private, no-store, no-cahce,must-revalidate");
					response.setHeader("Pragma","no-cache");
					view.forward(request, response);
				}
			}
		}
		/**
		 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
		 */
	
	}
	


