package com.springboot.util;

public class Common {
	public static String orderIdParser(int length) {
		int len = String.valueOf(length).length();
		String temp = String.valueOf(length+1);
		if(len < 4) {
			for(; len < 4; len++) {
				temp = "0" + temp;
			}
		}
		
		return temp;
	}
}
