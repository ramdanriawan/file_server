package com.biru.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConcurrentDateFormatAccess {
	
	public ConcurrentDateFormatAccess(String pattern) {
		this.pattern = pattern;
	}
	
	private String pattern;
	private Logger logger = LoggerFactory.getLogger(ConcurrentDateFormatAccess.class);
	private ThreadLocal<DateFormat> dateFormat = new ThreadLocal<DateFormat>() {

		@Override
		public DateFormat get() {
			return super.get();
		}
	
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat(pattern);
		}
	
		@Override
		public void remove() {
			super.remove();
		}
	
		@Override
		public void set(DateFormat value) {
			super.set(value);
		}

	};

	public Date parse(String dateStr) throws ParseException {
		return dateFormat.get().parse(dateStr);
	}
	
	public String format(Date date) {
		int countTry = 0;
		int maxTry = Integer.valueOf(System.getProperty("rebroker.dateformat.max.try","20"));
		
		String result = null;
		while(true) {
			result = dateFormat.get().format(date);
			
			if(result!=null) {
				break;
			}else {
				countTry++;
				if(countTry>=maxTry) {
					logger.error("Failed format date : {}.", date);
					break;
				}
			}
		}
		
		return result;
	}
	
}
