package com.springboot.util;

import java.sql.Timestamp;

@SuppressWarnings("deprecation")
public class TimestampParser {
	
	public static String TimestampStart(String date) {
		if(!date.equals("")) {
			String[] dateString = date.split("/");
			int month = Integer.parseInt(dateString[0]);
			int day = Integer.parseInt(dateString[1]);
			int year = Integer.parseInt(dateString[2]);
			
			return new Timestamp((year - 1900), (month - 1), day, 0, 0, 0, 0).toString();
		} else {
			return "";
		}
	}
	
	public static String TimestampEnd(String date) {
		if(!date.equals("")) {
			String[] dateString = date.split("/");
			int month = Integer.parseInt(dateString[0]);
			int day = Integer.parseInt(dateString[1]);
			int year = Integer.parseInt(dateString[2]);
			
			return new Timestamp((year - 1900), (month - 1), day, 23, 59, 59, 999999999).toString();
		} else {
			return "";
		}
	}
}
