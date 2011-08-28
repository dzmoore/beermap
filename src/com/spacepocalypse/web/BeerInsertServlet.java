package com.spacepocalypse.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spacepocalypse.engine.MarkupEngine;

public class BeerInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 8207539888102342349L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String name = req.getParameter("name");
		if (name != null && !name.trim().isEmpty()) {
			
		}
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");  // Can also use "text/plain" or others.
		PrintWriter out = resp.getWriter();

		out.println(MarkupEngine.getInstance().getHeader("Create New Beer"));
		
		out.println(MarkupEngine.getInstance().getInsertComponents());
		
		out.println(MarkupEngine.getInstance().getNavFooter());
	}
	
	private boolean validateAtLeastOneParamNotEmpty(HttpServletRequest req, String[] paramNames) {
		for (String ea : paramNames) {
			String val = req.getParameter(ea);
			if (val != null && !val.trim().isEmpty()) {
				return true;
			}
		}
		return false;
	}
}
