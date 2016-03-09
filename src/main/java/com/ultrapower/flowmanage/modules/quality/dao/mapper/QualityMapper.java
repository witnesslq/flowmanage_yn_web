package com.ultrapower.flowmanage.modules.quality.dao.mapper;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ultrapower.flowmanage.common.vo.Tree;
import com.ultrapower.flowmanage.modules.flow.vo.FsChartVo;
import com.ultrapower.flowmanage.modules.quality.vo.Test;
import com.ultrapower.flowmanage.modules.quality.vo.ThresholdConfigVo;

public interface QualityMapper {
	//查询地市TOP200网站时延-互联网质量评估表格数据
	/**
	 * 
	 * @param startTime
	 * @param endTime
	 * @param timeType
	 * @param companyId
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> findCityTop200WebsiteTimedelay(@Param("startTime")Date startTime, @Param("endTime")Date endTime,@Param("timeType")String timeType,@Param("companyId")String companyId) throws Exception;
	//接入KQI地市对比
	List<Map<String,String>> findKQIMenu() throws Exception;
	//接入KQI出口对比
	List<FsChartVo>findKQIExit(String exitid,Date getMonthFirstDay, Date nextMonthFirstDay)throws Exception;
	
	/**
	 * 查询集团热点TOP10网站信息表格-互联网质量评估 
	 * @param startTime
	 * @param endTime
	 * @param timeType 粒度类型
	 * @param companyId 厂家id
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> findGroupHotspotTop10Net(@Param("startTime")Date startTime, @Param("endTime")Date endTime,@Param("timeType")String timeType,@Param("companyId")String companyId) throws Exception;;
	//接入KQI地市对比
	List<FsChartVo> findKQICityComponent(String kqiType,String kpiId,@Param("startTime")Date startTime, @Param("endTime")Date endTime,@Param("timeType")String timeType,@Param("companyId")String companyId,@Param("cityId") String cityId) throws Exception;
	//根据接入或业务类型和KPIID查询KPI地市对比 
	List<Map<String, String>> findKPICityComponent(String kqiType,String kpiId,@Param("startTime")Date startTime, @Param("endTime")Date endTime,@Param("timeType")String timeType,@Param("companyId")String companyId,@Param("cityId") String cityId) throws Exception;
	
	//业务KQI地市对比 -导航详情
	List<Tree> getBusiKqiNavTable(@Param("type")String type);
	//业务KQI地市对比菜单
	List<Map<String,String>> findBUIKQIMenu() throws Exception;
	
	//根据业务类型查询互联网投诉及用户满意度地市对比-互联网质量评估
	List<FsChartVo> findNetComplaintCSATCityContrast(String kpiId,Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//根据字典表类型查询业务名称
	List<Map<String, Object>> findBusinessName(String bizType) throws Exception;
	//根据地市ID和业务类型查询省内热点网站情况
	List<Map<String, Object>> findProvincialHotspotNetTable(String bizId, String cityId, @Param("startTime")Date startTime, @Param("endTime")Date endTime,@Param("timeType")String timeType,@Param("companyId")String companyId) throws Exception;
	//根据接入或业务类型查询KQI地市对比(菜单)
	List<Map<String, Object>> findKQICityMenu(@Param("objectType")String objectType) throws Exception;
	//根据业务类型和业务级别查询接入KQI出口指标名称
	List<Map<String, String>> findAccKQIExitName(String objectType, String lev) throws Exception;
	//根据KPIID查询接入KQI出口对比
	List<FsChartVo> findAccKQIExitContrast(String kpiId, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//根据KPI的父级ID查询接入KPI出口对比
	List<Map<String, String>> findAccKPIExitContrast(String kpiId, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	
	void insertTest(Test test)  throws Exception;
	/**
	 * 查找厂家
	 * @return
	 * @throws Exception
	 */
	List<HashMap<String, String>> findCompany() throws Exception;
	
	/**
	 * 自定义查询-分页-业务KQI地市对比
	 * @param timeType 时间类型 day month
	 * @param city 地市id
	 * @param company 厂商id
	 * @param starttime 查询的开始时间
	 * @param endtime 查询的结束时间
	 * @param pageSize 每页多少条
	 * @param pageNo 第多少页
	 * @return
	 */
	List<HashMap<String,Object>> getZidingyi1(@Param("timeType")String timeType,@Param("city")String city,@Param("company")String company,@Param("starttime")Date starttime,@Param("endtime")Date endtime,@Param("pageSize") int pageSize, @Param("pageNo") int pageNo) ;
	/**
	 * 自定义查询-分页总记录数-业务KQI地市对比
	 * @param timeType 时间类型 day month
	 * @param city 地市id
	 * @param company 厂商id
	 * @param starttime 查询的开始时间
	 * @param endtime 查询的结束时间
	 * @return
	 */
	int getZidingyiCount1(@Param("timeType")String timeType,@Param("city")String city,@Param("company")String company,@Param("starttime")Date starttime,@Param("endtime")Date endtime);
	
	/**
	 *  自定义查询-分页总记录数-省内热点网站情况
	 * @param timeType 时间类型 day month
	 * @param city 地市id
	 * @param company 厂商id
	 * @param starttime 查询的开始时间
	 * @param endtime 查询的结束时间
	 * @param pageSize 每页多少条
	 * @param pageNo 第多少页
	 * @return
	 */
	List<HashMap<String,Object>> getZidingyi2(@Param("timeType")String timeType,@Param("city")String city,@Param("company")String company,@Param("starttime")Date starttime,@Param("endtime")Date endtime,@Param("pageSize") int pageSize, @Param("pageNo") int pageNo) ;
	
	/**
	 *  自定义查询-分页总记录数-省内热点网站情况
	 * @param timeType 时间类型 day month
	 * @param city 地市id
	 * @param company 厂商id
	 * @param starttime 查询的开始时间
	 * @param endtime 查询的结束时间
	 * @return
	 */
	int getZidingyiCount2(@Param("timeType")String timeType,@Param("city")String city,@Param("company")String company,@Param("starttime")Date starttime,@Param("endtime")Date endtime);

	/**
	 *  自定义查询-分页-地市TOP200网站时延
	 * @param timeType 时间类型 day month
	 * @param city 地市id
	 * @param company 厂商id
	 * @param starttime 查询的开始时间
	 * @param endtime 查询的结束时间
	 * @param pageSize 每页多少条
	 * @param pageNo 第多少页
	 * @return
	 */
	List<HashMap<String,Object>> getZidingyi3(@Param("timeType")String timeType,@Param("city")String city,@Param("company")String company,@Param("starttime")Date starttime,@Param("endtime")Date endtime,@Param("pageSize") int pageSize, @Param("pageNo") int pageNo) ;
	
	/**
	 *  自定义查询-分页总记录数-地市TOP200网站时延
	 * @param timeType 时间类型 day month
	 * @param city 地市id
	 * @param company 厂商id
	 * @param starttime 查询的开始时间
	 * @param endtime 查询的结束时间
	 * @return
	 */
	int getZidingyiCount3(@Param("timeType")String timeType,@Param("city")String city,@Param("company")String company,@Param("starttime")Date starttime,@Param("endtime")Date endtime);
	/**
	 * 
	 * 自定义查询-分页-集团TOP10网站信息
	 * @param timeType 时间类型 day month
	 * @param city 地市id
	 * @param company 厂商id
	 * @param starttime 查询的开始时间
	 * @param endtime 查询的结束时间
	 * @param pageSize 每页多少条
	 * @param pageNo 第多少页
	 * @return
	 */
	List<HashMap<String,Object>> getZidingyi4(@Param("timeType")String timeType,@Param("city")String city,@Param("company")String company,@Param("starttime")Date starttime,@Param("endtime")Date endtime,@Param("pageSize") int pageSize, @Param("pageNo") int pageNo) ;
	
	/**
	 * 自定义查询-分页总记录数-集团TOP10网站信息
	 * @param timeType 时间类型 day month
	 * @param city 地市id
	 * @param company 厂商id
	 * @param starttime 查询的开始时间
	 * @param endtime 查询的结束时间
	 * @return
	 */
	int getZidingyiCount4(@Param("timeType")String timeType,@Param("city")String city,@Param("company")String company,@Param("starttime")Date starttime,@Param("endtime")Date endtime);

	
	
	/**
	 * 查询详细页-业务KQI地市对比-分页
	 * @param yewu 业务中文名称
	 * @param city 地市名称
	 * @param company 厂商名称
	 * @param starttime 查询的开始时间
	 * @param endtime 查询的结束时间
	 * @param pageSize 每页多少条
	 * @param pageNo 第多少页
	 * @return
	 */
	List<HashMap<String,Object>> getXiangxi1(@Param("yewu")String yewu,@Param("city")List<String> city,@Param("company")String company,@Param("starttime")Date starttime,@Param("endtime")Date endtime,@Param("pageSize") int pageSize, @Param("pageNo") int pageNo) ;
	
	/**
	 * 查询详细页-业务KQI地市对比-分页总记录数
	 * @param yewu 业务中文名称
	 * @param city 地市名称
	 * @param company 厂商名称
	 * @param starttime 查询的开始时间
	 * @param endtime 查询的结束时间
	 * @return
	 */
	int getXiangxiCount1(@Param("yewu")String yewu,@Param("city")List<String> city,@Param("company")String company,@Param("starttime")Date starttime,@Param("endtime")Date endtime);
	/**
	 * 
	 * 查询详细页-省内热点网站情况 -分页
	 * @param yewu 业务中文名称
	 * @param city 地市名称
	 * @param company 厂商名称
	 * @param starttime 查询的开始时间
	 * @param endtime 查询的结束时间
	 * @param pageSize 每页多少条
	 * @param pageNo 第多少页
	 * @return
	 */
	List<HashMap<String,Object>> getXiangxi2(@Param("yewu")String yewu,@Param("city")List<String> city,@Param("company")String company,@Param("starttime")Date starttime,@Param("endtime")Date endtime,@Param("pageSize") int pageSize, @Param("pageNo") int pageNo) ;
	
	/**
	 * 查询详细页-省内热点网站情况 -分页总记录数
	 * @param yewu 业务中文名称
	 * @param city 地市名称
	 * @param company 厂商名称
	 * @param starttime 查询的开始时间
	 * @param endtime 查询的结束时间
	 * @return
	 */
	int getXiangxiCount2(@Param("yewu")String yewu,@Param("city")List<String> city,@Param("company")String company,@Param("starttime")Date starttime,@Param("endtime")Date endtime);

	/**
	 * 查询详细--地市TOP200网站时延 分页 
	 * @param webname 网站名称
	 * @param city 地市名称
	 * @param company 厂商名称
	 * @param starttime 查询的开始时间
	 * @param endtime 查询的结束时间
	 * @return
	 */
	List<HashMap<String,Object>> getXiangxi3(@Param("city")List<String> city,@Param("company")String company,@Param("starttime")Date starttime,@Param("endtime")Date endtime,@Param("pageSize") int pageSize, @Param("pageNo") int pageNo) ;
	
	/**
	 * 查询详细--地市TOP200网站时延 分页 总记录数
	 * @param city  地市名称
	 * @param company 厂商名称
	 * @param starttime 查询的开始时间
	 * @param endtime 查询的结束时间
	 * @return
	 */
	int getXiangxiCount3(@Param("city")List<String> city,@Param("company")String company,@Param("starttime")Date starttime,@Param("endtime")Date endtime);

	/**
	 * 查询详细--集团TOP10网站信息 分页 
	 * @param webname 网站名称
	 * @param city 地市名称
	 * @param company 厂商名称
	 * @param starttime 查询的开始时间
	 * @param endtime 查询的结束时间
	 * @param pageSize 每页多少条
	 * @param pageNo 第多少页
	 * @return
	 */
	List<HashMap<String,Object>> getXiangxi4(@Param("webname")String webname,@Param("city")List<String> city,@Param("company")String company,@Param("starttime")Date starttime,@Param("endtime")Date endtime,@Param("pageSize") int pageSize, @Param("pageNo") int pageNo) ;
	
	/**
	 * 查询详细--集团TOP10网站信息 分页 总记录数
	 * @param webname 网站名称
	 * @param city 地市名称
	 * @param company 厂商名称
	 * @param starttime 查询的开始时间
	 * @param endtime 查询的结束时间
	 * @return 
	 */
	int getXiangxiCount4(@Param("webname")String webname,@Param("city")List<String> city,@Param("company")String company,@Param("starttime")Date starttime,@Param("endtime")Date endtime);
	
	/**
	 * 根据时间和时间类型查询重点指标概述
	 * @param timeType 时间类型
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> findQualityKPIInfo(@Param("timeType")String timeType, @Param("startTime")Date startTime, @Param("endTime")Date endTime) throws Exception;
	
	/**
	 * 根据业务类型、模块名称查询指标阀值信息
	 * @param moduleName 模块名称
	 * @param bizType 业务类型 
	 * @return
	 * @throws Exception
	 */
	List<ThresholdConfigVo> findThresholdConfig(String moduleName, String bizType) throws Exception;
}
