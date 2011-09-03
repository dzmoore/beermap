package com.spacepocalypse.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.spacepocalypse.engine.BeerSearchEngine;
import com.spacepocalypse.pojo.MappedBeer;

public class AndroidBeerQueryServlet extends HttpServlet {
	private static final long serialVersionUID = -6893360066632654398L;
	private Logger log4jLogger;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		log4jLogger = Logger.getLogger(AndroidBeerQueryServlet.class);
		log4jLogger.info("Initializing.");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		StringBuilder sb = new StringBuilder();
		boolean notFirst = false;
		for (String key : req.getParameterMap().keySet()) {
			if (notFirst) {
				sb.append(", ");
			}
			sb.append(key);
			sb.append("=[");
			boolean innerNotFirst = false;
			for (String value : req.getParameterMap().get(key)) {
				if (innerNotFirst) {
					sb.append(", ");
				}
				sb.append(value);
				innerNotFirst = true;
			}
			sb.append("]");
			notFirst = true;
		}
		log4jLogger.info("Request's parameter map=[" + sb.toString() + "]");
		List<MappedBeer> beers = BeerSearchEngine.getInstance().doSearch(req.getParameterMap());
		JSONArray jsonArr = new JSONArray();
		for (MappedBeer ea : beers) {
			JSONObject obj = new JSONObject(ea);
			jsonArr.put(obj);
		}
		
		resp.setContentType("text/plain");
		resp.getWriter().println(jsonArr.toString());
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		resp.getWriter().println("beermap4android");
	}
}
