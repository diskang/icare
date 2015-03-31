package com.sjtu.icare.common.utils;

import java.util.Comparator;

public class MapKeyComparator implements Comparator<String>{  
    public int compare(String str1, String str2) {  
        return str1.compareTo(str2);  
    }  
}
