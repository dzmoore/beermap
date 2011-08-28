package com.spacepocalypse.engine;

public class MarkupEngine {

	private static final String NAV_FOOTER = "<form name=\"nav_footer_form\" action=\"home\" method=\"get\">" +
		"<input type=\"submit\" value=\"Main\" />" +
		"</form></body></html>";
	
	private static final String INSERT_COMPONENTS = 
		"<form name=\"insert_form\" action=\"beerinsert\" method=\"post\">" +
		"<table border=\"0\">" +
		"<tr>" + 
		"<td>Name:</td><td><input type=\"text\" name=\"name\"/></td>" + 
		"</tr>" + 
		"<tr>" + 
		"<td>Abv:</td><td><input type=\"text\" name=\"abv\"/></td>" + 
		"</tr>" + 
		"<tr>" + 
		"<td>Description:</td><td><textarea name=\"descript\" rows=\"10\" cols=\"30\"></textarea></td>" + 
		"</tr>" + 
		"</table>" +
		"<input type=\"submit\" value=\"Submit\"/>" +
		"</form>";
	
	private static final String INSERT_COMPONENTS1 = 
		"<form name=\"insert_form\" action=\"beerinsert\" method=\"post\">" +
		"<table border=\"0\">" +
		"<tr>" + 
		"<td>Name:</td><td><input type=\"text\" name=\"name\"/></td>" + 
		"</tr>" + 
		"<tr "; 
	
	private static final String INSERT_COMPONENTS2 = 
		"><td>Abv:</td><td><input name=\"abv\"  type=\"input\"/></td>" + 
		"</tr>" + 
		"<tr ";
	
	private static final String INSERT_COMPONENTS3 =
		"><td>Description:</td><td><textarea name=\"descript\" rows=\"10\" cols=\"30\"></textarea></td>" + 
		"</tr>" + 
		"</table>" +
		"<input type=\"submit\" value=\"Submit\"/>" +
		"</form>";
	
	private static final String HEADER1 = "<html><head><title>";
	private static final String HEADER2 = "</title></head><body><h1>";
	private static final String HEADER3 = "</h1>";
	
	private static final String INSERT_ERROR = 
		HEADER1 + "Error Inserting Beer" + HEADER2 + "Error" + HEADER3
		+ "At least one field must be not blank for a valid submission.<br/>";
	
	private static final String STYLE_HIDDEN = "style=\"display:none\"";
	
	private static MarkupEngine instance;

	public static MarkupEngine getInstance() {
		if (instance == null) {
			instance = new MarkupEngine();
		}
		return instance;
	}

	public String getNavFooter() {
		return NAV_FOOTER;
	}
	
	public String getInsertComponents() {
		return INSERT_COMPONENTS;
	}
	
	public String getHeader(String title) {
		StringBuilder sb = new StringBuilder(HEADER1);
		sb.append(title);
		sb.append(HEADER2);
		sb.append(title);
		sb.append(HEADER3);
		return sb.toString();
	}
	
	public String getInsertError() {
		return INSERT_ERROR;
	}
	
	public String getInsertComponents(boolean hideAbv, boolean hideDescript) {
		StringBuilder sb = new StringBuilder(INSERT_COMPONENTS1);
		
		if (hideAbv) {
			sb.append(STYLE_HIDDEN);
		}
		
		sb.append(INSERT_COMPONENTS2);
		
		if (hideDescript) {
			sb.append(STYLE_HIDDEN);
		}
		
		sb.append(INSERT_COMPONENTS3);
		
		return sb.toString();
	}
}
