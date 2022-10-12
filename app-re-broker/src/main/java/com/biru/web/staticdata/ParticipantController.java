package com.biru.web.staticdata;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.PARTICIPANT;
import com.biru.service.ParticipantService;

@RestController
@RequestMapping(REST.STATIC_DATA)
public class ParticipantController {
	
	@Autowired
	private ParticipantService participantService;
	
	@RequestMapping(value = PARTICIPANT.INQUIRY, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object inquiry() {
		return participantService.inquiry();
	}
	
	@RequestMapping(value = PARTICIPANT.GET_PARTICIPANT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getParticipant(@RequestBody Map<String, Object> param) {
		return participantService.getParticipant(param);
	}
	
	@RequestMapping(value = PARTICIPANT.SAVE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object save(@RequestBody Map<String, Object> param) {
		return participantService.save(param);
	}
}
