package com.spacepocalypse.pojo;

public class MappedUser {
	private int id;
	private String username;
	private boolean active;
	
	public MappedUser() {
		setUsername("");
		setActive(false);
		setId(-1);
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
