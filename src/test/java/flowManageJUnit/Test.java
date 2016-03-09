package flowManageJUnit;

import java.io.IOException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Test {
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("aaa");
		list.add("bb");
		list.add("cc");
		list.add(0, "ccc");
		for (String string : list) {
			System.out.println(string);
		}
	}
	
	public static List<Map<String, String>> getResult(){
		List<Map<String, String>> lists = new ArrayList<Map<String,String>>();
		Map<String, String> mp = null;
		List<Map<String, String>> kpiList = Test.getKpiResult();
		List<Map<String, String>> list = Test.getNetQualityResult();
		
		for (Map<String, String> map : list) {
			mp = new HashMap<String, String>();
			mp.putAll(map);
			Iterator<Entry<String, String>> kpiIterator = Test.kpiMap.entrySet().iterator();
			while(kpiIterator.hasNext()){
				Map.Entry<String, String> kpiEntry = (Map.Entry<String, String>)kpiIterator.next();
				map.put(kpiEntry.getKey(), kpiEntry.getValue());
				for (Map<String, String> kpiMap : kpiList) {
					if(map.get("goaladdr").equals(kpiMap.get("businame"))
					   && map.get(kpiEntry.getKey()).equals(kpiMap.get("kpiname"))){
					   map.put(kpiEntry.getKey(), kpiEntry.getValue()+""+kpiMap.get("value"));
					   break;
					}
				}
			}
			lists.add(map);
			lists.add(mp);
		}
		
		return lists;
	}
	
	public  static final Map<String, String> kpiMap = new HashMap<String, String>() {
		{
			put("globalScore","总体得分");
			put("netdelay","网络时延");
			put("netdelayScore","网络时延得分");
			put("netshake","网络抖动");
			put("netshakeScore","网络抖动得分");
			put("netpkloss","网络丢包");
			put("netpklossScore","网络丢包得分");
		}
	};

	public static List<Map<String, String>> getNetQualityResult(){
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Map<String, String> m1 = new HashMap<String, String>();
		m1.put("webname","56视频");
		m1.put("belongaddr","湖北Cache移动");
		m1.put("goaladdr","IDC节点");
		m1.put("globalScore","40");
		m1.put("netdelay","50");
		m1.put("netdelayScore","60");
		m1.put("netshake","70");
		m1.put("netshakeScore","80");
		m1.put("netpkloss","90");
		m1.put("netpklossScore","99");
		Map<String, String> m2 = new HashMap<String, String>();
		m2.put("webname","56视频");
		m2.put("belongaddr","湖北Cache移动");
		m2.put("goaladdr","省网节点");
		m2.put("globalScore","50");
		m2.put("netdelay","60");
		m2.put("netdelayScore","60");
		m2.put("netshake","80");
		m2.put("netshakeScore","80");
		m2.put("netpkloss","90");
		m2.put("netpklossScore","99");
		Map<String, String> m3 = new HashMap<String, String>();
		m3.put("webname","56视频");
		m3.put("belongaddr","湖北Cache移动");
		m3.put("goaladdr","地市节点");
		m3.put("globalScore","50");
		m3.put("netdelay","60");
		m3.put("netdelayScore","60");
		m3.put("netshake","80");
		m3.put("netshakeScore","80");
		m3.put("netpkloss","90");
		m3.put("netpklossScore","99");
		Map<String, String> m4 = new HashMap<String, String>();
		m4.put("webname","56视频");
		m4.put("belongaddr","湖北Cache移动");
		m4.put("goaladdr","网关节点");
		m4.put("globalScore","50");
		m4.put("netdelay","60");
		m4.put("netdelayScore","60");
		m4.put("netshake","80");
		m4.put("netshakeScore","80");
		m4.put("netpkloss","90");
		m4.put("netpklossScore","99");
		Map<String, String> m5 = new HashMap<String, String>();
		m5.put("webname","56视频");
		m5.put("belongaddr","江西IDC移动");
		m5.put("goaladdr","IDC节点");
		m5.put("globalScore","50");
		m5.put("netdelay","60");
		m5.put("netdelayScore","60");
		m5.put("netshake","70");
		m5.put("netshakeScore","80");
		m5.put("netpkloss","90");
		m5.put("netpklossScore","99");
		list.add(m1);
		list.add(m2);
		list.add(m3);
		list.add(m4);
		list.add(m5);
		return list;
	}
	
	public static List<Map<String, String>> getKpiResult(){
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Map<String, String> m1 = new HashMap<String, String>();
		m1.put("businame","IDC节点");
		m1.put("kpiname","网络时延");
		m1.put("value","(-85,250)");
		Map<String, String> m2 = new HashMap<String, String>();
		m2.put("businame","IDC节点");
		m2.put("kpiname","网络抖动");
		m2.put("value","(35,60)");
		Map<String, String> m3 = new HashMap<String, String>();
		m3.put("businame","IDC节点");
		m3.put("kpiname","网络丢包");
		m3.put("value","(0,5)");
		
		Map<String, String> m4 = new HashMap<String, String>();
		m4.put("businame","省网节点");
		m4.put("kpiname","网络时延");
		m4.put("value","(-55,250)");
		Map<String, String> m5 = new HashMap<String, String>();
		m5.put("businame","省网节点");
		m5.put("kpiname","网络抖动");
		m5.put("value","(10,60)");
		Map<String, String> m6 = new HashMap<String, String>();
		m6.put("businame","省网节点");
		m6.put("kpiname","网络丢包");
		m6.put("value","(0,6)");
		
		Map<String, String> m7 = new HashMap<String, String>();
		m7.put("businame","地市节点");
		m7.put("kpiname","网络时延");
		m7.put("value","(-40,250)");
		Map<String, String> m8 = new HashMap<String, String>();
		m8.put("businame","地市节点");
		m8.put("kpiname","网络抖动");
		m8.put("value","(20,60)");
		Map<String, String> m9 = new HashMap<String, String>();
		m9.put("businame","地市节点");
		m9.put("kpiname","网络丢包");
		m9.put("value","(0,4)");

		Map<String, String> m10 = new HashMap<String, String>();
		m10.put("businame","网关节点");
		m10.put("kpiname","网络时延");
		m10.put("value","(-50,250)");
		Map<String, String> m11 = new HashMap<String, String>();
		m11.put("businame","网关节点");
		m11.put("kpiname","网络抖动");
		m11.put("value","(30,60)");
		Map<String, String> m12 = new HashMap<String, String>();
		m12.put("businame","网关节点");
		m12.put("kpiname","网络丢包");
		m12.put("value","(0,3)");
		
		list.add(m1);
		list.add(m2);
		list.add(m3);
		list.add(m4);
		list.add(m6);
		list.add(m7);
		list.add(m8);
		list.add(m9);
		list.add(m10);
		list.add(m12);
		
		return list;
		
	}
}
