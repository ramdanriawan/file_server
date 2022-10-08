package com.biru.component;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateComponent {
	public String getMonthName (int month) {
		if(month == 0) {
			return "Januari";
		}else if(month == 1) {
			return "Februari";
		}else if(month == 2) {
			return "Maret";
		}else if(month == 3) {
			return "April";
		}else if(month == 4) {
			return "Mei";
		}else if(month == 5) {
			return "Juni";
		}else if(month == 6) {
			return "Juli";
		}else if(month == 7) {
			return "Agustus";
		}else if(month == 8) {
			return "September";
		}else if(month == 9) {
			return "Oktober";
		}else if(month == 10) {
			return "November";
		}else if(month == 11) {
			return "Desember";
		}else {
			return "";
		}
	}
	
	public Date toEod(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		return c.getTime();
	}
	
	public Date toSod(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 1);
		return c.getTime();
	}
}
