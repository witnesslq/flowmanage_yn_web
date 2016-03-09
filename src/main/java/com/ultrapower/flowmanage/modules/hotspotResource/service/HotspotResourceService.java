package com.ultrapower.flowmanage.modules.hotspotResource.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ultrapower.flowmanage.common.vo.Page;
import com.ultrapower.flowmanage.modules.flow.vo.FsChartVo;

public interface HotspotResourceService {
	//根据视频类型、出口类型、流量类型查询出网视频流量排名(柱状图)
	List<FsChartVo> findExitNetVideoFlowRank(String videoType, String exitType, String flowType, String getTime,String timeType) throws Exception;
	//根据视频类型和出口类型查询出网视频流量排名
	List<Map<String, Object>> findHotspotVideoExitByVideoExitType(String videoType, String exitType, String getTime,String timeType) throws Exception;
	//根据资源类型、当前页、每页的记录数分页查询省内IDC对外提供资源列表
	Page findIDCResTable(int pageNo, int pageSize, String resType, String getTime,String timeType) throws Exception;
	//查询省内热点网站情况
	List<Map<String, Object>> findHotspotWebTable(String getTime,String timeType) throws Exception;
	//查询省内热点P2P下载/P2P视频情况 
	List<Map<String, Object>> findHotspotP2PTable(String getTime,String timeType) throws Exception;
	//分页查询流量TOP50网站列表
	Page findHotspotFlow50Table(int pageNo, int pageSize, String getTime,String timeType) throws Exception;
	//分页查询访问TOP50网站列表
	Page findHotspotAcc50Table(int pageNo, int pageSize, String getTime,String timeType) throws Exception;
	//分页查询网内流量占比小于70%网站列表
	Page findHotspotPer70Table(int pageNo, int pageSize, String getTime,String timeType) throws Exception;
	//分页查询TOP1000域名列表
	Page findHotspotDNS1000Table(int pageNo, int pageSize, String getTime,String timeType) throws Exception;
	//分页查询非移动引入域名列表
	Page findHotspotDNSNOCMTable(int pageNo, int pageSize, String getTime,String timeType) throws Exception;
	//分页查询热点资源引入分析列表
	Page findHotspotResTable(int pageNo, int pageSize, String getTime,String timeType) throws Exception;
	//热点资源分析表格数据Excel导出
	byte[] hotspotResourceExcelTable(String getTime,String timeType) throws Exception;
	

	//分页查询无线用户点击热点域名列表
	Page findUserClickhotspotDomainsTable(int pageNo, int pageSize, String getTime,String timeType) throws Exception;
	
}
