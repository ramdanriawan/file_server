package com.biru.service;

public interface LoginService {
	public Object loginPageItem();
	public void countLoginError();
	public Object isUserLocked(String username);
	public void addFailedLogin(String username);
	public void resetFailedLogin(String username);
	public void resetPassword(String username, String password) throws Exception;
	public Object inquiryUser();
}
