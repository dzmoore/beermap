package com.spacepocalypse.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.spacepocalypse.engine.BeerSearchEngine;
import com.spacepocalypse.engine.LogonEngine;
import com.spacepocalypse.pojo.MappedBeer;

public class AndroidBeerQueryServlet extends HttpServlet {
	private static final long serialVersionUID = -6893360066632654398L;
	private static final String QUERY_TYPE_KEY = "q";
	private static final String QUERY_TYPE_SEARCH = "_s";
	private static final String QUERY_TYPE_LOGON = "_l";
	private static final String USERNAME_PARAM_KEY = "_un";
	private static final String PASSWORD_PARAM_KEY = "_pw";
	
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
		Map<String, String[]> parameterMap = req.getParameterMap();
		logParameterMap(parameterMap);
		
		String[] queryTypeArr = parameterMap.get(QUERY_TYPE_KEY);
		
		if (queryTypeArr == null || queryTypeArr.length <= 0) {
			log4jLogger.error("Query type not specified!");
			return;
		}
		
		String queryType = queryTypeArr[0];
		
		if (queryType.equalsIgnoreCase(QUERY_TYPE_SEARCH)) {
			List<MappedBeer> beers = BeerSearchEngine.getInstance().doSearch(parameterMap);
			JSONArray jsonArr = new JSONArray();
			for (MappedBeer ea : beers) {
				JSONObject obj = new JSONObject(ea);
				jsonArr.put(obj);
			}
			
			resp.setContentType("text/plain");
			resp.getWriter().println(jsonArr.toString());
		} else if (queryType.equalsIgnoreCase(QUERY_TYPE_LOGON)) {
			String username = getFirstOrNullTrying(parameterMap, USERNAME_PARAM_KEY);
			String hashPass = getFirstOrNullTrying(parameterMap, PASSWORD_PARAM_KEY);
			boolean valid = LogonEngine.getInstance().authUser(username, hashPass);
			
			log4jLogger.info("authUser check return: " + valid);
			
			resp.setContentType("text/plain");
			JSONObject obj = new JSONObject();
			try {
				obj.put("valid", valid);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			log4jLogger.info("Writing: [" + obj.toString() + "]");
			resp.getWriter().println(obj.toString());
		}
	}
	
	private String getFirstOrNullTrying(Map<String, String[]> map, String key) {
		if (map.containsKey(key)) {
			String[] val = map.get(key);
			if (val != null && val.length > 0) {
				return val[0];
			}
		}
		return null;
	}

	private void logParameterMap(Map<String, String[]> parameterMap) {
		StringBuilder sb = new StringBuilder();
		boolean notFirst = false;
		for (String key : parameterMap.keySet()) {
			if (notFirst) {
				sb.append(", ");
			}
			sb.append(key);
			sb.append("=[");
			boolean innerNotFirst = false;
			for (String value : parameterMap.get(key)) {
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
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		resp.getWriter().println("beermap4android");
	}
}
