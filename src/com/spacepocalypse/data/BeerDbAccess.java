package com.spacepocalypse.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.spacepocalypse.pojo.MappedBeer;

public class BeerDbAccess extends DbExecutor {
	private static final String SELECT_BY_NAME = 
		"select name, abv, descript " +
		"from beers " +
		"where lower(name) like ?;";
	
	private static final String SELECT_BY_UPC =
		"select name, abv, descript " +
		"from beers join upc on (beers.id = upc.beer_fk)" +
		"where upc.upca = ?";

		
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
		return beer;
	}
	
	public static void main(String[] args) {
		BeerDbAccess beerDb = new BeerDbAccess();

		List<MappedBeer> beers = beerDb.findAllBeersByUpc("614141999996");
		for (MappedBeer ea : beers) {
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			System.out.println(ea.toString());
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		}

		try {
			beerDb.getDbConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
