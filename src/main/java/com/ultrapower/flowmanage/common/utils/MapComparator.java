package com.ultrapower.flowmanage.common.utils;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class MapComparator implements Comparator<Object>{
	public int compare(Object o1, Object o2) {
		Map<String, Object> m1 = (Map<String, Object>)o1; 
		Map<String, Object> m2 = (Map<String, Object>)o2; 
		BigDecimal bg1 = new BigDecimal(0);
		BigDecimal bg2 = new BigDecimal(0);
		Iterator iterator1 = m1.entrySet().iterator();
		while(iterator1.hasNext()){
			Map.Entry<String, Object> entry = (Entry<String, Object>)iterator1.next();
			if(entry.getKey().indexOf("flow") != -1){
				bg1 = new BigDecimal(String.valueOf(entry.getValue()));
			}else if(entry.getKey().indexOf("sumFlowVal") != -1){
				bg1 = new BigDecimal(String.valueOf(entry.getValue()));
			}else if(entry.getKey().indexOf("score") != -1){
				bg1 = new BigDecimal(String.valueOf(entry.getValue()));
			}
		}
		Iterator iterator2 = m2.entrySet().iterator();
		while(iterator2.hasNext()){
			Map.Entry<String, Object> entry = (Entry<String, Object>)iterator2.next();
			if(entry.getKey().indexOf("flow") != -1){
				bg2 = new BigDecimal(String.valueOf(entry.getValue()));
			}else if(entry.getKey().indexOf("sumFlowVal") != -1){
				bg2 = new BigDecimal(String.valueOf(entry.getValue()));
			}else if(entry.getKey().indexOf("score") != -1){
				bg2 = new BigDecimal(String.valueOf(entry.getValue()));
			}
		}
		//比较大小
		return bg2.compareTo(bg1);
	}

}
