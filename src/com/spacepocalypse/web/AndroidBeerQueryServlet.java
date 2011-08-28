package com.spacepocalypse.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.spacepocalypse.engine.BeerSearchEngine;
import com.spacepocalypse.pojo.MappedBeer;

public class AndroidBeerQueryServlet extends HttpServlet {
	private static final long serialVersionUID = -6893360066632654398L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
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
