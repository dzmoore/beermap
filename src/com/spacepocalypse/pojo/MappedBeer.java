package com.spacepocalypse.pojo;

import java.io.Serializable;

public class MappedBeer implements Serializable {
	private static final long serialVersionUID = 2563267495110624938L;

	private String name;
	private float abv;
	private String descript;
	private String upc;
	
	public MappedBeer() {
		setName("");
		setAbv(-1);
		setDescript("");
		setUpc("");
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setAbv(float abv) {
		this.abv = abv;
	}
	public float getAbv() {
		return abv;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	public String getDescript() {
		return descript;
	}
	
	public void setUpc(String upc) {
		this.upc = upc;
	}

	public String getUpc() {
		return upc;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("name=[");
		sb.append(getName());
		sb.append("] abv=[");
		sb.append(getAbv());
		sb.append("] descript=[");
		sb.append(getDescript());
		sb.append("] upc=[");
		sb.append(getUpc());
		sb.append("]");
		return sb.toString();
		
	}
	
	public static MappedBeer createMappedBeer(String json) {
		
		return null;
	}
	
	
}
