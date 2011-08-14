package com.spacepocalypse.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeerDbAccess extends DbExecutor {
	private static final String SELECT_BY_NAME1 = "select name, abv, descript from beers where lower(name) like ?;";

	public List<Map<String, String>> findAllBeersByName(String name) {
		PreparedStatement ps = null;

		try {
			ps = getDbConnection().prepareStatement(SELECT_BY_NAME1);
			ps.setString(1, "%" + name + "%");
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<Map<String, String>>();
		}

		try {
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<Map<String, String>>();
		}

		List<Map<String, String>> results = new ArrayList<Map<String,String>>();
		Map<String, String> resultBldr;
		ResultSet rs;
		try {
			rs = ps.getResultSet();
			while (rs.next()) {
				resultBldr = new HashMap<String, String>();
				resultBldr.put("name", rs.getString(1));
				resultBldr.put("abv", rs.getString(2));
				resultBldr.put("descript", rs.getString(3));
				
				results.add(resultBldr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}


		return results;
	}

	public static void main(String[] args) {
		BeerDbAccess beerDb = new BeerDbAccess();

		List<Map<String, String>> beers = beerDb.findAllBeersByName("penguin");
		for (Map<String, String> ea : beers) {
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			System.out.println(ea.get("name"));
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		}

		try {
			beerDb.getDbConnection().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
