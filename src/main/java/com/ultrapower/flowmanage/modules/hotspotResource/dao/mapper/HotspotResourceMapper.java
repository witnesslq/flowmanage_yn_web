package com.ultrapower.flowmanage.modules.hotspotResource.dao.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ultrapower.flowmanage.modules.flow.vo.FsChartVo;

public interface HotspotResourceMapper {
	//根据视频类型、出口类型、流量类型查询出网视频流量排名(柱状图)
	List<FsChartVo> findExitNetVideoFlowRank(String videoType, String exitType, String flowType, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//根据视频类型和出口类型查询出网视频流量排名
	List<Map<String, Object>> findHotspotVideoExitByVideoExitType(String videoType, String exitType, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//根据资源类型查询省内IDC对外提供资源列表记录数
	int findIDCResRowCount(String resType, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//根据资源类型、当前页、每页的记录数分页查询省内IDC对外提供资源列表
	List<Map<String, Object>> findIDCResTable(String resType, Date getMonthFirstDay, Date nextMonthFirstDay, int pageNo, int pageSize) throws Exception;
	//查询省内热点网站情况
	List<Map<String, Object>> findHotspotWebTable(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//查询省内热点P2P下载/P2P视频情况 
	List<Map<String, Object>> findHotspotP2PTable(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//查询流量TOP50网站列表记录数
	int findHotspotFlow50RowCount(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//分页查询流量TOP50网站列表
	List<Map<String, Object>> findHotspotFlow50Table(Date getMonthFirstDay, Date nextMonthFirstDay, int pageNo, int pageSize) throws Exception;
	//查询访问TOP50网站列表记录数
	int findHotspotAcc50RowCount(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//分页查询访问TOP50网站列表
	List<Map<String, Object>> findHotspotAcc50Table(Date getMonthFirstDay, Date nextMonthFirstDay, int pageNo, int pageSize) throws Exception;
	//查询网内流量占比小于70%网站列表记录数
	int findHotspotPer70RowCount(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//分页查询网内流量占比小于70%网站列表
	List<Map<String, Object>> findHotspotPer70Table(Date getMonthFirstDay, Date nextMonthFirstDay, int pageNo, int pageSize) throws Exception;
	//查询TOP1000域名列表记录数
	int findHotspotDNS1000RowCount(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//分页查询TOP1000域名列表
	List<Map<String, Object>> findHotspotDNS1000Table(Date getMonthFirstDay, Date nextMonthFirstDay, int pageNo, int pageSize) throws Exception;
	//查询非移动引入域名列表记录数
	int findHotspotDNSNOCMRowCount(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//分页查询非移动引入域名列表
	List<Map<String, Object>> findHotspotDNSNOCMTable(Date getMonthFirstDay, Date nextMonthFirstDay, int pageNo, int pageSize) throws Exception;
	//查询热点资源引入分析列表记录数
	int findHotspotResRowCount(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//分页查询热点资源引入分析列表
	List<Map<String, Object>> findHotspotResTable(Date getMonthFirstDay, Date nextMonthFirstDay, int pageNo, int pageSize) throws Exception;
	
	//查询无线用户点击热点域名列表记录数
	int findUserClickhotspotDomainsRowCount(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//分页查询无线用户点击热点域名列表
	List<Map<String, Object>> findUserClickhotspotDomainsTable(Date getMonthFirstDay, Date nextMonthFirstDay, int pageNo, int pageSize) throws Exception;
	
	
	//后面新增--------------------suqiuliang
	
	//根据视频类型、出口类型、流量类型查询出网视频流量排名OfDay(柱状图)
		List<FsChartVo> findExitNetVideoFlowRankOfDay(String videoType, String exitType, String flowType, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
		//根据视频类型和出口类型查询出网视频流量排名
		List<Map<String, Object>> findHotspotVideoExitByVideoExitTypeOfDay(String videoType, String exitType, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
		//根据资源类型查询省内IDC对外提供资源列表记录数
		int findIDCResRowCountOfDay(String resType, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
		//根据资源类型、当前页、每页的记录数分页查询省内IDC对外提供资源列表
		List<Map<String, Object>> findIDCResTableOfDay(String resType, Date getMonthFirstDay, Date nextMonthFirstDay, int pageNo, int pageSize) throws Exception;
		//查询省内热点网站情况
		List<Map<String, Object>> findHotspotWebTableOfDay(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
		//查询省内热点P2P下载/P2P视频情况 
		List<Map<String, Object>> findHotspotP2PTableOfDay(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
		//查询流量TOP50网站列表记录数
		int findHotspotFlow50RowCountOfDay(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
		//分页查询流量TOP50网站列表
		List<Map<String, Object>> findHotspotFlow50TableOfDay(Date getMonthFirstDay, Date nextMonthFirstDay, int pageNo, int pageSize) throws Exception;
		//查询访问TOP50网站列表记录数
		int findHotspotAcc50RowCountOfDay(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
		//分页查询访问TOP50网站列表
		List<Map<String, Object>> findHotspotAcc50TableOfDay(Date getMonthFirstDay, Date nextMonthFirstDay, int pageNo, int pageSize) throws Exception;
		//查询网内流量占比小于70%网站列表记录数
		int findHotspotPer70RowCountOfDay(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
		//分页查询网内流量占比小于70%网站列表
		List<Map<String, Object>> findHotspotPer70TableOfDay(Date getMonthFirstDay, Date nextMonthFirstDay, int pageNo, int pageSize) throws Exception;
		//查询TOP1000域名列表记录数
		int findHotspotDNS1000RowCountOfDay(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
		//分页查询TOP1000域名列表
		List<Map<String, Object>> findHotspotDNS1000TableOfDay(Date getMonthFirstDay, Date nextMonthFirstDay, int pageNo, int pageSize) throws Exception;
		//查询非移动引入域名列表记录数
		int findHotspotDNSNOCMRowCountOfDay(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
		//分页查询非移动引入域名列表
		List<Map<String, Object>> findHotspotDNSNOCMTableOfDay(Date getMonthFirstDay, Date nextMonthFirstDay, int pageNo, int pageSize) throws Exception;
		//查询热点资源引入分析列表记录数
		int findHotspotResRowCountOfDay(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
		//分页查询热点资源引入分析列表
		List<Map<String, Object>> findHotspotResTableOfDay(Date getMonthFirstDay, Date nextMonthFirstDay, int pageNo, int pageSize) throws Exception;
		
		//查询无线用户点击热点域名列表记录数
		int findUserClickhotspotDomainsRowCountOfDay(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
		//分页查询无线用户点击热点域名列表
		List<Map<String, Object>> findUserClickhotspotDomainsTableOfDay(Date getMonthFirstDay, Date nextMonthFirstDay, int pageNo, int pageSize) throws Exception;
}
