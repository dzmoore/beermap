package com.spacepocalypse.engine;

import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.spacepocalypse.data.BeerDbAccess;
import com.spacepocalypse.pojo.MappedBeer;

public class BeerSearchEngine {
	private static BeerSearchEngine instance;
	
	public static final String QUERY_KEY_NAME = "_name";
	public static final String QUERY_KEY_ABV = "_abv";
	public static final String QUERY_KEY_UPC = "_upc";
	private static final String[] VALID_QUERY_KEYS = {
		QUERY_KEY_NAME,
		QUERY_KEY_ABV,
		QUERY_KEY_UPC
	};
	
	private BeerDbAccess beerDbAccess;

	public static BeerSearchEngine getInstance() {
		if (instance == null) {
			instance = new BeerSearchEngine();
		}
		return instance;
	}
	
	public void doSearchByBeerName(PrintWriter out, String beerName) {
		List<MappedBeer> results = getBeerDbAccess().findAllBeersByName(beerName);
		printResults(out, beerName, results);
	}
	
	public List<MappedBeer> doSearch(Map<String, String[]> parameters) {
		for (String key : VALID_QUERY_KEYS) {
			if (parameters.containsKey(key)) {
				try {
					return getBeerDbAccess().findAllBeers(parameters);
				} catch (InvalidParameterException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return new ArrayList<MappedBeer>();
	}

	private void printResults(PrintWriter out, String beerName,
			List<MappedBeer> results) {
		out.println("<b>Results for \"" + beerName + "\":</b><br /> <br />");
		out.println("<hr />");

		for (MappedBeer beer : results) {
			out.print("<h3><b>"+beer.getName() + "</b></h3>");
			
			out.print("<b>abv</b>: "+beer.getAbv());
			out.print("<br />");
			
			out.print("<b>UPC</b>: " + beer.getUpc());
			out.print("<br />");
			
			out.println("<b>descript</b>: "+beer.getDescript());
			out.println("<hr />");
		}
	}
	
	public void doSearchByUpc(PrintWriter out, String upcData) {
		List<MappedBeer> results = getBeerDbAccess().findAllBeersByUpc(upcData);
		printResults(out, upcData, results);
	}
	
	public List<MappedBeer> doSearchByUpc(String upcData) {
		return getBeerDbAccess().findAllBeersByUpc(upcData);
	}
	
	public BeerDbAccess getBeerDbAccess() {
		if (beerDbAccess  == null) {
			beerDbAccess = new BeerDbAccess();
		}
		return beerDbAccess;
	}
	
	public void destroy() {
		if (instance != null) {
			try {
				getBeerDbAccess().getDbConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			instance = null;
		}
	}

}
