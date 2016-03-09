package com.ultrapower.flowmanage.modules.quality.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.flowmanage.common.vo.Page;
import com.ultrapower.flowmanage.common.vo.Tree;
import com.ultrapower.flowmanage.modules.flow.vo.FsChartVo;


public interface QualityService {
	
	/**
	 * //查询地市TOP200网站时延-互联网质量评估表格数据
	 * @param startTime
	 * @param endTime
	 * @param timeType
	 * @param companyId
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> findCityTop200WebsiteTimedelay(String startTime,
			String endTime, String timeType, String companyId) throws Exception;
	/**
	 * 查询集团热点TOP10网站信息表格-互联网质量评估 
	 * @param startTime
	 * @param endTime
	 * @param timeType 粒度类型
	 * @param companyId 厂家id
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> findGroupHotspotTop10Net(String startTime, String endTime,String timeType,String companyId) throws Exception;

	//接入KQI地市对比
	List<Map<String,String>> findKQIMenu() throws Exception;
	//接入KQI出口对比
	List<FsChartVo>findKQIExit(String exitid,String getTime)throws Exception;
	//接入KQI地市对比
//	Map<String, Object> findKQICityComponent(String kqiType,String kpiId,String startTime,
//			String endTime, String timeType, String companyId,String cityId) throws Exception;
	List<Map<String, Object>> findKQICityComponent(String kqiType,String kpiId,String startTime,
			String endTime, String timeType, String companyId,String cityId)throws Exception;
		
	//业务KQI地市对比 -导航详情
	List<Tree> getBusiKqiNavTable(String type) throws Exception;
	//创建业务KQI地市对比 导航树 
	Tree buildBusiKqiNavTree(String type) throws Exception;
	//业务KQI地市对比菜单
	List<Map<String,String>> findBUIKQIMenu() throws Exception;
	//根据业务类型查询互联网投诉及用户满意度地市对比-互联网质量评估
	Map<String, Object> findNetComplaintCSATCityContrast(String kpiId, String getTime) throws Exception;
	//根据字典表类型查询业务名称
	List<Map<String, Object>> findBusinessName(String bizType) throws Exception;
	//根据地市ID和业务类型查询省内热点网站情况
	/**
	 * 
	 * @param bizId
	 * @param cityId
	 * @param startTime
	 * @param endTime
	 * @param timeType
	 * @param companyId
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> findProvincialHotspotNetTable(String bizId, String cityId, String startTime,
			String endTime, String timeType, String companyId) throws Exception;
	
	//根据接入或业务类型查询KQI地市对比(菜单)
	List<Map<String, Object>> findKQICityMenu(String objectType) throws Exception;
	//根据业务类型和业务级别查询接入KQI出口指标名称
	List<Map<String, String>> findAccKQIExitName(String objectType, String lev) throws Exception;
	//根据KPIID查询接入KQI出口对比
	List<FsChartVo> findAccKQIExitContrast(String kpiId, String getTime) throws Exception;
	/**
	 * 互联网质量分析表格数据Excel导出
	 * @param startTime
	 * @param endTime
	 * @param timeType
	 * @return
	 * @throws Exception
	 */
	byte[] qualityExcelTable(String startTime,String endTime, String timeType) throws Exception;
	void insertTest()  throws Exception;
	/**
	 * 查找厂家
	 * @return
	 * @throws Exception
	 */
	List<HashMap<String, String>> findCompany() throws Exception;
	/**
	 * 查询自定义
	 * @param yewuType 业务类型 1，-业务KQI地市对比 2省内热点网站情况 3地市TOP200网站时延  4集团TOP10网站信息
	 * @param timeType 时间类型，天周月，day week month
	 * @param city 地市id
	 * @param company 厂商id
	 * @param starttime 查询的开始时间
	 * @param endtime 查询的结束时间
	 * @param pageSize 每页多少条
	 * @param pageNo 第多少页
	 * @return
	 * @throws ParseException
	 */
	Page getZidingyi(int yewuType,String timeType,String city,String company,String starttime,String endtime,int pageSize, int pageNo) throws ParseException;
	
	/**
	 * 分页查询详细页  1业务KQI地市对比 2省内热点网站情况
	 * @param yewuType  1，-业务KQI地市对比 2省内热点网站情况
	 * @param yewu 业务中文名称
	 * @param city 地市名称
	 * @param company 厂商名称
	 * @param starttime 查询的开始时间
	 * @param endtime 查询的结束时间
	 * @param pageSize 每页多少条
	 * @param pageNo 第多少页
	 * @return
	 * @throws ParseException
	 */
	Page getXiangxi12(int yewuType,String yewu,List<String> city,String company,String starttime,String endtime,int pageSize, int pageNo) throws ParseException;

	/**
	 * 分页查询详细页  地市TOP200网站时延
	 * @param city 地市名称
	 * @param company 厂商名称
	 * @param starttime 查询的开始时间
	 * @param endtime 查询的结束时间
	 * @param pageSize 每页多少条
	 * @param pageNo 第多少页
	 * @return
	 * @throws ParseException
	 */
	Page getXiangxi3(List<String> city,String company,String starttime,String endtime,int pageSize, int pageNo) throws ParseException;
	/**
	 * 分页查询详细页  地市TOP200网站时延
	 * @param webname 网站名称
	 * @param city 地市名称
	 * @param company 厂商名称
	 * @param starttime 查询的开始时间
	 * @param endtime 查询的结束时间
	 * @param pageSize 每页多少条
	 * @param pageNo 第多少页
	 * @return
	 * @throws ParseException
	 */
	Page getXiangxi4(String webname,List<String> city,String company,String starttime,String endtime,int pageSize, int pageNo) throws ParseException;
	
	
	/**
	 * 自定义查询导出 
	 * @param yewuType 1，-业务KQI地市对比 2省内热点网站情况 3地市TOP200网站时延  4集团TOP10网站信息
	 * @param timeType 时间类型，天周月，day week month
	 * @param city 地市id
	 * @param company 厂商id
	 * @param starttime 查询的开始时间
	 * @param endtime 查询的结束时间
	 * @param pageSize 如果为0，导出所有记录 ，否则导出分页记录
	 * @param pageNo 如果为0，导出所有记录 ，否则导出分页记录
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	byte[] daochuZidingyi(int yewuType,String timeType,String city, String company, String starttimeStr,
			String endtimeStr,int pageSize, int pageNo) throws ParseException, IOException;
	/**
	 * 在天周月粒度页面点击导出 调用此方法
	 * @param yewuType 1，-业务KQI地市对比 2省内热点网站情况 3地市TOP200网站时延  4集团TOP10网站信息
	 * @param timeType 时间类型，天周月，day week month
	 * @param city 地市id
	 * @param company 厂商id
	 * @param starttime 查询的开始时间
	 * @param endtime 查询的结束时间
	 * @param pageSize 如果为0，导出所有记录 ，否则导出分页记录
	 * @param pageNo 如果为0，导出所有记录 ，否则导出分页记录
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	byte[] daochuTianzhouyue(int yewuType,String timeType,String city, String company, String starttimeStr,
			String endtimeStr,int pageSize, int pageNo) throws ParseException, IOException;
	
	/**
	 * 导出详细页-省内热点网站情况-业务KQI地市对比
	 * @param yewuType 1为业务KQI地市对比，2为省内热点网站情况
	 * @param yewu 业务中文名称
	 * @param city 地市名称
	 * @param company 厂商名称
	 * @param starttime 查询的开始时间
	 * @param endtime 查询的结束时间
	 * @param pageSize 如果为0，导出所有记录 ，否则导出分页记录
	 * @param pageNo 如果为0，导出所有记录 ，否则导出分页记录
	 * @return
	 * @throws Exception
	 */
	byte[] daochuXiangxi12(int yewuType,String yewu,List<String> city,String company,String starttime,String endtime,int pageSize, int pageNo) throws Exception;

	/**
	 * 
	 * 导出详细页--地市TOP200网站时延 分页 
	 * @param city 地市名称
	 * @param company 厂商名称
	 * @param starttime 查询的开始时间
	 * @param endtime 查询的结束时间
	 * @param pageSize 如果为0，导出所有记录 ，否则导出分页记录
	 * @param pageNo 如果为0，导出所有记录 ，否则导出分页记录
	 * @return
	 * @throws Exception
	 */
	byte[] daochuXiangxi3(List<String> city,String company,String starttime,String endtime,int pageSize, int pageNo) throws Exception;
	/**
	 * 导出详细页-集团TOP10网站信息
	 * @param webname 网站名称
	 * @param city 地市名称
	 * @param company 厂商名称
	 * @param starttime 查询的开始时间
	 * @param endtime 查询的结束时间
	 * @param pageSize 如果为0，导出所有记录 ，否则导出分页记录
	 * @param pageNo 如果为0，导出所有记录 ，否则导出分页记录
	 * @return
	 * @throws Exception
	 */
	byte[] daochuXiangxi4(String webname,List<String> city,String company,String starttime,String endtime,int pageSize, int pageNo) throws Exception;

	/**
	 * 根据时间和时间类型查询重点指标概述
	 * @param timeType 时间类型
	 * @param getTime 时间
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> findQualityKPIInfo(String timeType, String getTime) throws Exception;
}
