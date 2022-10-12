package com.biru.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EncryptionComponent {
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public String encrypt(String str) {
		return bCryptPasswordEncoder.encode(str);
	}
	
	
}
