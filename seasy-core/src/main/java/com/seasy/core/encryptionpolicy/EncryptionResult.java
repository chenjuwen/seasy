package com.seasy.core.encryptionpolicy;

public class EncryptionResult {
	private String salt;
	private String password;
	
	public EncryptionResult(){
		
	}
	
	public EncryptionResult(String salt, String password){
		this.salt = salt;
		this.password = password;
	}
	
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
