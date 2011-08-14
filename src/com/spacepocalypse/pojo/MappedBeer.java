package com.spacepocalypse.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MappedBeer implements Serializable {
	private static final long serialVersionUID = 2563267495110624938L;

	private String name;
	private float abv;
	private String descript;
	private List<String> upcList;
	
	public MappedBeer() {
		setName("");
		setAbv(-1);
		setDescript("");
		setUpcList(new ArrayList<String>());
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
	public void setUpcList(List<String> upcList) {
		this.upcList = upcList;
	}
	public List<String> getUpcList() {
		return upcList;
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
		sb.append("] upcList=[");
		sb.append(getUpcList().toString());
		sb.append("]");
		return sb.toString();
		
	}
	
	
}
