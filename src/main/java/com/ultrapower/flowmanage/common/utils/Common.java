package com.ultrapower.flowmanage.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Common {

	/**
	 * 判断是否需要初始化
	 */
	public static final boolean IS_SENDRESTOPASM = true;
	
	public static final String[] cityName = {"武汉","十堰","恩施","襄阳","宜昌","随州","荆门","孝感","天门",
											"潜江","荆州","新江汉","黄冈","鄂州","黄石","咸宁"};
	public static final String[] netQualityName = {"时延","抖动","丢包"};
	
	public  static final Map<String, String> cityMap = new HashMap<String, String>() {
		{
			put("十堰", "shiyanshi");
			//put("神龙架", "shenlongjia");
			put("襄阳", "xiangyangshi");
			put("宜昌", "yichangshi");
			put("恩施", "enshizizhizhou");
			put("随州", "suizhoushi");
			put("荆门", "jingmenshi");
			put("孝感", "xiaoganshi");
			put("天门", "tianmenshi");
			put("潜江", "qianjiangshi");
			put("荆州", "jingzhoushi");
			put("新江汉", "xinjianghan");
			put("武汉", "wuhanshi");
			put("黄冈", "huanggangshi");
			put("鄂州", "ezhoushi");
			put("黄石", "huangshishi");
			put("咸宁", "xianningshi");
			//put("江汉", "jianghanshi");
		}
	};
	
	
	public static final Map<String, String> healthThreeFactor = new HashMap<String, String>(){
		{
			put("GSM流量发展健康度","gsmchildnode");
			put("TD流量发展健康度","tdchildnode");
			put("WLAN流量发展健康度","wlanchildnode");
			put("分流健康度","threenetchildnode");
			
			put("分流维度","splitflowchildnode");
			put("质量维度","qualitychildnode");
			put("效益维度","benefitchildnode");
			put("覆盖维度","coverchildnode");
			put("感知维度","perceptionchildnode");
		}
	};
	
	public  static final Map<String, String> healthNameMap = new HashMap<String, String>() {
		{
			//三网流量发展健康度 指标
			put("GSM流量发展健康度","gsm");
			put("TD流量发展健康度","td");
			put("WLAN流量发展健康度","wlan");
			put("分流健康度","threenet");
			
			put("资源消耗","resource");
			put("承载效率","loadingEfficiency");
			put("网络负荷","networkLoad");
			put("网络质量","networkQuality");
			put("增长结构","growthStructure");
			put("网络效益","networkBenefit");
			put("整体分流效果","overallShunt");
			put("TD分流","tdShunt");
			put("WLAN分流","wlanShunt");
			
			//TD网络健康度 指标
			put("分流维度","splitflow");
			put("质量维度","quality");
			put("效益维度","benefit");
			put("覆盖维度","cover");
			put("感知维度","perception");
			
			put("TD话务占总话务比例", "tdTrafficRatio");
			put("TD流量占两网比例", "tdFlowRatio");
			put("TD客户渗透率", "tdCustomerPenetration");
			put("TD网络客户占TD客户比例", "tdNetCustomerRatio");
			
			put("高质差小区占比", "highBadVillageRatio");
			put("路测TD语音呼叫全程成功率", "tdVoiceCallSuccessRate");
			put("路测TD数据下载速率", "tdDataDownloadSuccessRate");
			put("基站退服率", "baseStationRefundRate");
			
			put("TD码资源利用率", "tdResourceUserRatio");
			put("超闲小区数比例", "busyVillageRatio");
			put("TD流量构成", "tdFlowForm");
			
			put("路测3G时长占比", "durationRatio");
			put("弱覆盖占总测试里程占比", "weakCoverRatio");
			put("深度覆盖不足占总测试里程占比", "depthCoverDeficiencyRatio");
			put("呼叫切换比", "calloutSwitchRatio");
			
			put("万用户投诉比", "userComplaintRatio");
			put("TD网络类投诉占比", "tdNetComplaintRatio");
			put("客户满意度", "customerSatisfaction");
		}
	};
	
	public  static final Map<String, String> flowNameMap = new HashMap<String, String>() {
		{
			//流量流向分析 指标
			put("铁通资源共享比重","tietongResourceRatio");
			put("网外资源总依赖度","netOutResourceRelyLimit");
			put("省级出口分流占比","promotionExitRatio");
			put("视频（含P2P）跨网流量占比","videoRatio");
			put("P2P下载跨网流量占比","p2pRatio");
			put("HTTP及其他跨网流量占比","httpRatio");
			
//			put("全部","busiVisit");
//			put("CMNET","cmnet");
//			put("CMWAP","cmwap");
//			put("WLAN","wlan");
//			put("家客","homeGeek");
//			put("集客","groupGeek");
			
			put("全部","busiVisit");
			put("GPRS/LTE","GPRSLTE");
			put("WLAN","wlan");
			put("小区宽带","xiaoqu");
			put("专线","zhuanxian");
			
			
			put("IDC","idc");
			put("WEBCACHE","webCaChe");
			put("P2PCACHE","p2pCaChe");
			
			put("电信","ctcc");
			put("联通","cucc");
			put("电信及联通","ctccAndcucc");
			
			put("网外资源利用率","netOutResourceUseRatio");
			put("IDC资源服务网内他省率","idc");
			put("IDC服务本网率","idc");
			put("WEBCACHE有效缓存比","caChe");
			put("WEBCACHE缓存增益比","caChe");
			put("P2PCACHE有效缓存比","caChe");
			put("P2PCACHE缓存增益比","caChe");
			
			//流量成分分析
			put("视频","video");
			put("下载","download");
			put("HTTP浏览","httpBrowse");
			put("邮件","email");
			put("即时通信","im");
			put("游戏","game");
			put("VOIP","voip");
			put("其他","other");

			put("视频PPTV(含P2P类)","pptvFlow");
			put("视频PPS(含P2P类)","ppsFlow");
			put("HTTP优酷","youkuFlow");
			put("HTTP土豆","tudouFlow");
			put("HTTP乐视","letvFlow");
			put("HTTP奇艺","iqiyiFlow");
			put("HTTP酷6","ku6Flow");
			put("HTTP搜狐","sohuFlow");
			
			//流量性能分析
			put("入流速均值","inFlowAvgValue");
			put("出流速均值","outFlowAvgValue");
			put("带宽峰值","bwPeakValue");
			put("带宽峰值利用率","bwPeakValueUseRatio");
		}
	};

	public  static final Map<String, String> operatorMap = new HashMap<String, String>() {
		{
			put("移动","cmcc");
			put("电信","ctcc");
			put("联通","cucc");
		}
	};
		
			
	/**
	 * List集合去重复
	 * @param list
	 */
	public static void removeDuplicateWithOrder(List list) {
		   Set set = new HashSet();
		   List newList = new ArrayList();
		   for (Iterator iter = list.iterator(); iter.hasNext();) {
		          Object element = iter.next();
		          if (set.add(element))
		             newList.add(element);
	       }
	       list.clear();
	       list.addAll(newList);
//		     System.out.println( " remove duplicate " + list);
	}
}
