package com.spacepocalypse.engine;

import com.spacepocalypse.data.BeerDbAccess;

public class LogonEngine {
	private static LogonEngine instance;
	
	private LogonEngine() {}
	
	public static LogonEngine getInstance() {
		if (instance == null) {
			instance = new LogonEngine();
		}
		return instance;
	}
	
	public boolean authUser(String username, String hashPass) {
		return getDbAccess().userAndPasswordMatch(username, hashPass);
	}
	
	public BeerDbAccess getDbAccess() {
		return BeerDbAccess.getAccess();
	}
}
