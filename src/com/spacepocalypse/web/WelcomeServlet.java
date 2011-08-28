package com.spacepocalypse.web;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class WelcomeServlet extends HttpServlet {
	private static final long serialVersionUID = -4439557059333677712L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		resp.setContentType("text/html");  // Can also use "text/plain" or others.
		PrintWriter out = resp.getWriter();

		// Create output (the response):
		out.println("<html><head><title>BeerMap</title></head>");
		out.println("<body>" );
		out.println("<h2>Welcome to <b>BeerMap</b></h2><br />");
		out.println("<form name=\"beer_search_form\" action=\"beersearch\" method=\"get\">");
		out.println("<input type=\"submit\" value=\"BeerSearch\" />");
		out.println("</form>");
		out.println("<form name=\"beer_barcode_form\" action=\"beerbarcode\" method=\"get\">");
		out.println("<input type=\"submit\" value=\"BeerBarcode\" />");
		out.println("</form>");
		out.println("<form name=\"beer_insert_form\" action=\"beerinsert\" method=\"get\">");
		out.println("<input type=\"submit\" value=\"BeerInsert\" />");
		out.println("</form>");
		out.println("</body></html>");
		out.close();
	}

}
