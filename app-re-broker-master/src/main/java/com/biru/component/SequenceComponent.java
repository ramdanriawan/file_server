package com.biru.component;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.biru.entity.SequenceEntity;
import com.biru.repository.SequenceRepo;

@Component
public class SequenceComponent {
	
	@Autowired
	private SequenceRepo sequenceRepo;
	
	@Transactional
	public synchronized Integer getSequence(String sequenceName) {
		SequenceEntity seq = sequenceRepo.findBySequenceName(sequenceName);
		seq.setCounter(seq.getCounter()+1);
		
		sequenceRepo.save(seq);
		return seq.getCounter();
	}
}
