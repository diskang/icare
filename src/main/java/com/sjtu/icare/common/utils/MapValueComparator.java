package com.sjtu.icare.common.utils;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

public class MapValueComparator implements Comparator<Map.Entry<String, String>> {  
	public int compare(Entry<String, String> me1, Entry<String, String> me2) {  
		return me1.getValue().compareTo(me2.getValue());  
	}  
}  