package com.gkbvision.TelegramBot;


public class LocalSqlServer {
	private String jdbcUrl;
	private String user;
	private String password;
	
	LocalSqlServer(String jdbc_CS,String user_name,String pass_word)
	{
		jdbcUrl=jdbc_CS;
		user=user_name;
		password=pass_word;
	}
	
	public String getJdbcUrl() {
		return jdbcUrl;
	}
	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

}