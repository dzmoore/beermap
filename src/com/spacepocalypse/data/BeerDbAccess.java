package com.spacepocalypse.data;

import java.security.InvalidParameterException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.spacepocalypse.engine.BeerSearchEngine;
import com.spacepocalypse.pojo.MappedBeer;

public class BeerDbAccess extends DbExecutor {
	private static final String SELECT_
		= "select name, abv, descript, upc.upca ";

	private static final String SELECT_BY_NAME = 
		SELECT_ +
		"from beers left outer join upc on (beers.id = upc.beer_fk) " +
		"where lower(name) like ?";
	
	private static final String SELECT_BY_UPC =
		SELECT_ +
		"from beers join upc on (beers.id = upc.beer_fk) " +
		"where upc.upca = ?";

	private static final String SELECT_ALL_WHERE = 
		SELECT_ +
		"from beers left outer join upc on (beers.id = upc.beer_fk) " +
		"where ";
//	lower(name) like ? " +
//		"and upc.upca like ? " +
//		"and abv like ? ";
	
	public List<MappedBeer> findAllBeers(Map<String, String[]> parameters) throws SQLException, InvalidParameterException {
		PreparedStatement ps = null;
		Map<String, Integer> queryKeyOrder = new HashMap<String, Integer>();
		int keyOrderIndex = 1;
		StringBuilder sb = new StringBuilder(SELECT_ALL_WHERE);
		
		if (parameters.containsKey(BeerSearchEngine.QUERY_KEY_NAME)) {	
			sb.append("lower(name) like ? ");
			queryKeyOrder.put(BeerSearchEngine.QUERY_KEY_NAME, keyOrderIndex++);
		} 
		
		if (parameters.containsKey(BeerSearchEngine.QUERY_KEY_ABV)) {
			if (keyOrderIndex > 1) {
				sb.append("and ");
			}
			sb.append("abv like ? ");
			queryKeyOrder.put(BeerSearchEngine.QUERY_KEY_ABV, keyOrderIndex++);
		} 
		
		if (parameters.containsKey(BeerSearchEngine.QUERY_KEY_UPC)) {
			if (keyOrderIndex > 1) {
				sb.append("and ");
			}
			sb.append("upc.upca like ? ");
			queryKeyOrder.put(BeerSearchEngine.QUERY_KEY_UPC, keyOrderIndex++);
		}
		
		if (keyOrderIndex <= 1) {
			throw new InvalidParameterException("No valid search keys specified"); 
		}

		ps = getDbConnection().prepareStatement(sb.toString());
		for (String key : queryKeyOrder.keySet()) {
			int index = queryKeyOrder.get(key);
			ps.setString(index, "%" + parameters.get(key)[0] + "%");
		}
		
		ps.execute();
		
		List<MappedBeer> results = new ArrayList<MappedBeer>();
		ResultSet rs = ps.getResultSet();
		while (rs.next()) {
			results.add(createMappedBeer(rs));
		}
		
		return results;
	}
	
	
	public List<MappedBeer> findAllBeersByName(String name) {
		PreparedStatement ps = null;

		try {
			ps = getDbConnection().prepareStatement(SELECT_BY_NAME);
			ps.setString(1, "%" + name + "%");
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<MappedBeer>();
		}

		try {
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<MappedBeer>();
		}

		List<MappedBeer> results = new ArrayList<MappedBeer>();
		ResultSet rs = null;
		try {
			rs = ps.getResultSet();
			while (rs.next()) {
				results.add(createMappedBeer(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}


		return results;
	}
	
	public List<MappedBeer> findAllBeersByUpc(String upc) {
		PreparedStatement ps = null;

		try {
			ps = getDbConnection().prepareStatement(SELECT_BY_UPC);
			ps.setString(1, upc);
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<MappedBeer>();
		}

		try {
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<MappedBeer>();
		}

		List<MappedBeer> results = new ArrayList<MappedBeer>();
		ResultSet rs = null;
		try {
			rs = ps.getResultSet();
			while (rs.next()) {
				results.add(createMappedBeer(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}


		return results;
	}

	private static MappedBeer createMappedBeer(ResultSet rs) throws SQLException {
		MappedBeer beer = new MappedBeer();
		beer.setName(rs.getString(1));
		beer.setAbv(rs.getFloat(2));
		beer.setDescript(rs.getString(3));
		beer.setUpc(rs.getString(4));
		return beer;
	}
	
	public static void main(String[] args) {
		BeerDbAccess beerDb = new BeerDbAccess();

		Map<String,String[]> params = new HashMap<String, String[]>();
//		params.put(BeerSearchEngine.QUERY_KEY_UPC, "614141999996"); // test beer
		params.put(BeerSearchEngine.QUERY_KEY_NAME, new String[]{"coors"});
		params.put(BeerSearchEngine.QUERY_KEY_ABV, new String[]{"5"});
		List<MappedBeer> beers = null;
		try {
			beers = beerDb.findAllBeers(params);
		} catch (InvalidParameterException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		JSONArray arr = new JSONArray();
		for (MappedBeer ea : beers) {
//			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
//			System.out.println(ea.toString());
//			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			JSONObject obj = new JSONObject(ea);
			arr.put(obj);
		}
		
		String jsonArrayString = arr.toString();
		System.out.println(jsonArrayString);
		System.out.println("\n\n\n");
		
		try {
			JSONArray toJsonArray = new JSONArray(jsonArrayString);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		
		try {
			beerDb.getDbConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
