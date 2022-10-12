package com.biru.service;

import java.util.Map;

public interface ParticipantService {
	
	public Object inquiry(); 
	
	public Object getParticipant(Map<String, Object> param); 
	
	public Object save(Map<String, Object> param); 
	
}
