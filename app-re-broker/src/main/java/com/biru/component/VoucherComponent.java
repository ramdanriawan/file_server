package com.biru.component;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.biru.entity.VoucherCounterEntity;
import com.biru.repository.VoucherCounterRepo;

@Component
public class VoucherComponent {
	
	@Autowired
	private VoucherCounterRepo voucherCounterRepo;
	
	private String formatVoucherCounter(String counter) {
		int length = 5;
		String result = counter;
		if(counter.length() < length) {
			int minusChar = length - counter.length();
			for (int i = 0; i < minusChar; i++) {
				result = "0".concat(result);
			}
		}
		return result;
	}
	
	@Deprecated
	public String getVoucherId(String date) {
		String voucherSufix = formatVoucherCounter(String.valueOf(getCounter(date)));
		String voucherPreffix = date.replace("-", "");
		return voucherPreffix.concat(voucherSufix);
	}
	
	public String getVoucherId(String date, Integer counter) {
		String voucherSufix = formatVoucherCounter(String.valueOf(counter));
		String voucherPreffix = date.replace("-", "");
		return voucherPreffix.concat(voucherSufix);
	}
	
	private Integer getCounter(String date) {
		VoucherCounterEntity voucherCounterEntity = voucherCounterRepo.findByDate(date);
		int counter = 1;
		if(voucherCounterEntity != null) {
			counter = voucherCounterEntity.getCounter()+1;
		}
		return counter;
	}
	
	@Transactional
	public String saveVoucherCounter(String date) {
		synchronized (date) {
			VoucherCounterEntity voucherCounterEntity = voucherCounterRepo.findByDate(date);
			if(voucherCounterEntity == null) {
				voucherCounterEntity = new VoucherCounterEntity();
				voucherCounterEntity.setId(0);
				voucherCounterEntity.setDate(date);
				voucherCounterEntity.setCounter(getCounter(date));
				voucherCounterRepo.save(voucherCounterEntity);
			}else {
				voucherCounterEntity.setCounter(voucherCounterEntity.getCounter()+1);
				voucherCounterRepo.save(voucherCounterEntity);
			}
			
			return getVoucherId(date, voucherCounterEntity.getCounter());
		}
	}
}
