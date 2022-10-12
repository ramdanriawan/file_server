package com.biru.common.param;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.biru.common.util.ConcurrentDateFormatAccess;

public class Param {
	
	public static final String SORT 		= "sort";
	public static final String ORDER 		= "order";
	public static final String OFFSET		= "offset";
	public static final String LIMIT 		= "limit";
	public static final String TENANT_ID	= "tenantId";
	public static final String USER			= "user";
	public static final String FILTER_KEY	= "filterKey";
	public static final String FILTER_VALUE	= "filterValue";
	public static final String CREATE_BY	= "createBy";
	public static final String CREATE_ON	= "createOn";
	public static final String MODIFY_BY	= "modifyBy";
	public static final String MODIFY_ON	= "modifyOn";
	public static final String DATE			= "date";
	public static final String CURR			= "curr";

	public static String getStr(Map<String, Object> p_Map, String p_Key) {
		Object value = p_Map.get(p_Key);
		
		if(value==null)
			return null;
		
		return value.toString();
	}

	public static List<String> getListFromString(Map<String, Object> p_Map, String p_Key) {
		Object value = p_Map.get(p_Key);
		
		if(value==null)
			return null;
		String str[] = value.toString().split(",");
		List<String> listStr = new ArrayList<String>();
		listStr = Arrays.asList(str);

		return listStr;
	}
	
	public static String getStrWithDef(Map<String, Object> p_Map, String p_Key) {
		Object value = p_Map.get(p_Key);
		
		if(value==null)
			return "";
		
		return value.toString();
	}
	
	public static Integer getInt(Map<String, Object> p_Map, String p_Key) {
		Object value = p_Map.get(p_Key);
		
		if(value==null)
			return null;
		if(StringUtils.isBlank(value.toString()))
			return null;
		
		Integer plus = 1;
		String val = value.toString().replace(",", "");
		if(val.startsWith("(") 
				&& val.endsWith(")")) {
			plus = -1;
			val = val.replace("(", "").replace(")", "");
		}			
		
		return Integer.valueOf(val) * plus;
	}
	
	public static Integer getIntWithDef(Map<String, Object> p_Map, String p_Key) {
		Object value = p_Map.get(p_Key);
		
		if(value==null)
			return 0;
		if(StringUtils.isBlank(value.toString()))
			return 0;
		
		Integer plus = 1;
		String val = value.toString().replace(",", "");
		if(val.startsWith("(") 
				&& val.endsWith(")")) {
			plus = -1;
			val = val.replace("(", "").replace(")", "");
		}
		
		return Integer.valueOf(val) * plus;
	}
	
	public static Long getLong(Map<String, Object> p_Map, String p_Key) {
		Object value = p_Map.get(p_Key);
		
		if(value==null)
			return null;
		if(StringUtils.isBlank(value.toString()))
			return null;	
		
		Long plus = 1l;
		String val = value.toString().replace(",", "");
		if(val.startsWith("(") 
				&& val.endsWith(")")) {
			plus = -1l;
			val = val.replace("(", "").replace(")", "");
		}
		
		return Long.valueOf(val) * plus;
	}
	
	public static Long getLongWithDef(Map<String, Object> p_Map, String p_Key) {
		Object value = p_Map.get(p_Key);
		
		if(value==null)
			return 0l;
		if(StringUtils.isBlank(value.toString()))
			return 0l;		
		
		Long plus = 1l;
		String val = value.toString().replace(",", "");
		if(val.startsWith("(") 
				&& val.endsWith(")")) {
			plus = -1l;
			val = val.replace("(", "").replace(")", "");
		}
		
		return Long.valueOf(val) * plus;
	}
	
	public static BigDecimal getBdWithDef(Map<String, Object> p_Map, String p_Key) {
		Object value = p_Map.get(p_Key);
		
		if(value==null)
			return BigDecimal.ZERO;
		if(StringUtils.isBlank(value.toString()))
			return BigDecimal.ZERO;
		
		BigDecimal plus = BigDecimal.ONE;
		String val = value.toString().replace(",", "");
		if(val.startsWith("(") 
				&& val.endsWith(")")) {
			plus = plus.negate();
			val = val.replace("(", "").replace(")", "");
		}
		
		return new BigDecimal(val).multiply(plus);
	}
	
	public static BigDecimal getBd(Map<String, Object> p_Map, String p_Key) {
		Object value = p_Map.get(p_Key);
		
		if(value==null)
			return null;
		if(StringUtils.isBlank(value.toString()))
			return null;
		
		BigDecimal plus = BigDecimal.ONE;
		String val = value.toString().replace(",", "");
		if(val.startsWith("(") 
				&& val.endsWith(")")) {
			plus = plus.negate();
			val = val.replace("(", "").replace(")", "");
		}
		
		return new BigDecimal(val).multiply(plus);
	}
	
	public static Boolean getBoolean(Map<String, Object> p_Map, String p_Key) {
		Object value = p_Map.get(p_Key);
		
		if(value==null)
			return null;
		
		return Boolean.valueOf(value.toString());
	}
	
	public static Date getDate(Map<String, Object> p_Map, String p_Key) {
		Object value = p_Map.get(p_Key);
		
		if(value==null)
			return null;
		if(StringUtils.isBlank(value.toString()))
			return null;
		
		String date [] = value.toString().split("/");
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date[0]));
		calendar.set(Calendar.MONTH, Integer.parseInt((date[1]))-1);
		calendar.set(Calendar.YEAR, Integer.parseInt(date[2]));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		return calendar.getTime();
	}
	
	public static Date getDate(Map<String, Object> p_Map, String p_Key, SimpleDateFormat sdf) 
			throws ParseException {
		Object value = p_Map.get(p_Key);
		
		if(value==null)
			return null;
		if(StringUtils.isBlank(value.toString()))
			return null;
		
		Date result = sdf.parse(value.toString());
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(result);
		
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		return calendar.getTime();
	}
	
	public static Date getDate(Map<String, Object> p_Map, String p_Key, ConcurrentDateFormatAccess sdf) 
			throws ParseException {
		Object value = p_Map.get(p_Key);
		
		if(value==null)
			return null;
		if(StringUtils.isBlank(value.toString()))
			return null;
		
		Date result = sdf.parse(value.toString());
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(result);
		
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		return calendar.getTime();
	}
	
}
