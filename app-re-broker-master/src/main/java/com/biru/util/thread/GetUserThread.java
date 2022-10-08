package com.biru.util.thread;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.biru.entity.US0001Entity;
import com.biru.repository.US0001Repo;
import com.biru.service.impl.LoginImpl;

public class GetUserThread implements Runnable{

	private int index;
	private int chunk;
	private US0001Repo uS0001Repo;



	public GetUserThread(int index, int chunk, US0001Repo uS0001Repo) {
		super();
		this.index = index;
		this.chunk = chunk;
		this.uS0001Repo = uS0001Repo;
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub
		Pageable pr = PageRequest.of(index, chunk);
		List<US0001Entity> listUser = uS0001Repo.findAllActive(pr).getContent();
		for (US0001Entity us0001Entity : listUser) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("username", us0001Entity.getUsCode());
			data.put("password", us0001Entity.getUsPass());
			data.put("role", us0001Entity.getGroupId().toUpperCase().replace(" ", ""));
			System.out.println("select User "+data);
			LoginImpl.listUserDetails.add(data);
		}
	}

}
