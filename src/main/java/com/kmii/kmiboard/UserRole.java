package com.kmii.kmiboard;

import lombok.Getter;

@Getter
public enum UserRole {  //열거형
	
	ADMIN("ROLE_ADMIN"),
	USER("ROLE_USER");
	
	private UserRole(String value) {
		this.value = value;
	}
	
	private String value;
	

}
