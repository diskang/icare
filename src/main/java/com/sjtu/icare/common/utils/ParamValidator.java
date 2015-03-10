/**
 * @Package com.sjtu.icare.common.utils
 * @Description a class to validate the parameter
 * @date Mar 8, 2015 5:56:27 PM
 * @author Wang Qi
 * @version TODO
 */
package com.sjtu.icare.common.utils;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParamValidator {

	public static boolean isTemperature(String temperature) {
		if (temperature == null)
			return false;
		
		double tempTemperature = Double.parseDouble(temperature);
		if (tempTemperature > 0 && tempTemperature < 100)
			return true;
		else
			return false;
	}
	
	public static boolean isDate(String date) {
		if (date == null)
			return false;
		
		SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			pattern.parse(date);
			return true;
		} catch (Exception e){
			// pass
		}
		
		pattern = new SimpleDateFormat("yyyy-MM-dd");
		try {
			pattern.parse(date);
			return true;
		} catch (Exception e){
			// pass
		}
		
		return false;
	}

	public static boolean isBloodPressure(String systolicPressureParam) {
		if (systolicPressureParam == null)
			return false;
		
		double systolicPressure = Double.parseDouble(systolicPressureParam);
		if (systolicPressure > 0 && systolicPressure < 1000)
			return true;
		else
			return false;
	}

	public static boolean isHeartRate(String heartRateParam) {
		if (heartRateParam == null)
			return false;
		
		double hearRate = Double.parseDouble(heartRateParam);
		if (hearRate > 0 && hearRate < 1000)
			return true;
		else
			return false;
	}
	
	public static boolean isInteger(String param) {
		Pattern pattern = Pattern.compile("[0-9]+");
		Matcher matcher = pattern.matcher(param);
		if (matcher.matches())
			return true;
		else
			return false;
	}

	/**
	 * @Title isDecimal
	 * @Description TODO
	 * @param @param diastolicPressure
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean isDecimal(String param) {
		Pattern pattern = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");
		Matcher matcher = pattern.matcher(param);
		if (matcher.matches())
			return true;
		else
			return false;
	}
	
	
}
