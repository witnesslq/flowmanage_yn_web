package com.ultrapower.flowmanage.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public class Utils {
	public static String getMapString(Map<String, String> map, String key) {
		return map.get(key) == null ? "" : String.valueOf(map.get(key));
	}
	public static String valueOf(String value){
		DecimalFormat dft = new DecimalFormat("0.00");
		if(value == null || "".equals(value) || "0".equals(value)){
			return "0";
		}
		BigDecimal bg = new BigDecimal(value);
		return dft.format(bg);
	}
	
	public static String valueOf(double value){
		DecimalFormat dft = new DecimalFormat("0.00");
		BigDecimal bg = new BigDecimal(value);
		return dft.format(bg);
	}
	public static String valueOf(String value, String format){
		DecimalFormat dft = new DecimalFormat("0.00");
		if(value == null || "".equals(value) || "0".equals(value)){
			return "0";
		}
		BigDecimal bg = new BigDecimal(value);
		if(bg.compareTo(new BigDecimal(1)) == -1){
			return dft.format(bg);
		}
		if(format != null && !"".equals(format)){
			dft.applyPattern(format);
		}
		return dft.format(bg);
	}
	
	public static double getDoubleFromString(String value, String format) throws ParseException{
		if(value == null) return 0.00;
		DecimalFormat dft = new DecimalFormat("0.00");
		if(format != null && !"".equals(format)){
			dft.applyPattern(format);
		}
		return dft.parse(value).doubleValue();
	}
	
	public static double div(double d1,double d2,int len) {// 进行除法运算
		 if(d2 == 0){
			 return 0;
		 }
         BigDecimal b1 = new BigDecimal(d1);
         BigDecimal b2 = new BigDecimal(d2);
	     return b1.divide(b2,len,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	//求集合中数值的平均值
	public static String getAverage(List<String> list) {
      	BigDecimal bg = new BigDecimal(0);
        for(int i=0;i<list.size();i++){
        	bg = bg.add(new BigDecimal(list.get(i)));
        }
        if(list.size() > 0 ) {
        	bg = bg.divide(new BigDecimal(list.size()), 2, BigDecimal.ROUND_HALF_UP);
        }
        return bg.toString();
	 }
}
