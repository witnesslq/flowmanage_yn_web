package com.ultrapower.flowmanage.modules.health.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.flowmanage.common.utils.DateUtil;
import com.ultrapower.flowmanage.common.vo.Tree;
import com.ultrapower.flowmanage.modules.flow.vo.FsChartVo;
import com.ultrapower.flowmanage.modules.health.vo.AssDimRelevance;
import com.ultrapower.flowmanage.modules.health.vo.CityContrastVo;
import com.ultrapower.flowmanage.modules.health.vo.CountryContrastVo;
import com.ultrapower.flowmanage.modules.health.vo.Emp;
import com.ultrapower.flowmanage.modules.health.vo.ThreeNetHealth;

public interface HealthDao {
	List<Emp> findEmp() throws Exception;
	List<Emp> findEmpByEmpno(String empno) throws Exception;
	List<ThreeNetHealth> findThreeNetHealth(String healthtype, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	List<ThreeNetHealth> findThreeNetHealthOfDay(String healthtype, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;

	List<CityContrastVo> findCityHealthScoreById(String healthId, String healthType, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	List<CityContrastVo> findCityHealthScoreByIdOfDay(String healthId, String healthType, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	List<CityContrastVo> findHealthNameAndScoreById(String healthId, String healthtype, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	List<CityContrastVo> findHealthNameAndScoreByIdOfDay(String healthId, String healthtype, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;

	List<CityContrastVo> findHealthBizScope() throws Exception;
	List<CityContrastVo> findCityInfo() throws Exception;
	List<CityContrastVo> findHealthNameByType(String healthType) throws Exception;
	List<CityContrastVo> findHealthNameScoreByHealthId(String healthId, String healthType, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	List<CityContrastVo> findHealthNameScoreByHealthIdOfDay(String healthId, String healthType, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	
	List<Tree> getMenuTable(String type,String lev) throws Exception;
	
	List<Tree> getHealthTable(String type, String area, String city, Date starttime, Date endtime ) throws Exception;
	List<Tree> getHealthTableOfDay(String type, String area, String city, Date starttime, Date endtime ) throws Exception;
//	List<Map<String, String>> getHealthTableOfDay(String type, String area, String city, Date starttime, Date endtime ) throws Exception;
	
	//获取全国对比的数据
	List<CountryContrastVo> getCountryConstrastList(String healthtype, Date beforeMonthFirstDay, Date getMonthFirstDay)throws Exception;
	//获取全国对比的数据
	List<CountryContrastVo> getCountryConstrastListOfDay(String healthtype, Date beforeMonthFirstDay, Date getMonthFirstDay)throws Exception;

	//评估点地市对比
	List<AssDimRelevance> getCityContrast(String healthtype)throws Exception;
	//评估点地市对比子节点
	List<AssDimRelevance> getCityContrastChild(String fatherId)throws Exception;
	//全省纵览
	List<Map<String, String>> getprovinceView(String healthType, Date getMonthFirstDay, Date nextMonthFirstDay)throws Exception;
	//全省纵览
	List<Map<String, String>> getprovinceViewOfDay(String healthType, Date getMonthFirstDay, Date nextMonthFirstDay)throws Exception;

	//竟争对手对比
	List<Map<String,String>>getOperView(Date beforeMonthFirstDay, Date getMonthFirstDay)throws Exception;
	//竟争对手对比
	List<Map<String,String>>getOperViewOfDay(Date beforeMonthFirstDay, Date getMonthFirstDay)throws Exception;
	//评估点地市对比charts
	List<Map<String,String>>getCityConstrastCharts(String healthname,String healthtype, Date beforeMonthFirstDay, Date getMonthFirstDay)throws Exception;
	//评估点地市对比charts
	List<Map<String,String>>getCityConstrastChartsOfDay(String healthname,String healthtype, Date beforeMonthFirstDay, Date getMonthFirstDay)throws Exception;

	//执行导出WORD的数据汇聚存储
	void proWordExportDay(Map para)throws Exception;
	//查找WORD数据
	List<Map<String, BigDecimal>> getWordDataForDate(String date)throws Exception;
	//根据时间粒度、健康度指标ID、健康度类型查询各地市健康度指标名称和分数
	List<CityContrastVo> findHealthNameScoreByTime(String timeType, String healthType, String healthId, Date startTime, Date endTime)throws Exception;

	//根据时间粒度、健康度指标ID、健康度类型查询健康度分数和指标值
	List<Map<String, String>> findHealthScoreKpiByType(String healthType, String healthId, String startTime, String endTime, String timeType, String num) throws Exception;
	//根据SQL字符串查询字段表信息
	List<Map<String, String>> findDICInfo(String sql) throws Exception;
	
	//指标详情 TOP7 charts 月粒度
	List<Map<String,String>>getKPIDetailTop7(String kpiId, Date startTime, Date endTime, String startTimeStr, String endTimeStr)throws Exception;
	//指标详情 TOP7 charts 天粒度
	List<Map<String,String>>getKPIDetailTop7OfDay(String kpiId, Date startTime, Date endTime, String startTimeStr, String endTimeStr)throws Exception;
	//查询自定义表格
	List<Map<String,String>> getUserDefinedInfo(String healthType, String kpiId, String timeType, Date starttime, Date endtime, int pageSize, int pageNo) ;
	//网间出口健康度明细
	List<Map<String,String>> getExitHealthDetail(String healthType, String kpiId, String timeType, Date starttime, Date endtime, int pageSize, int pageNo) ;

	int getUserDefinedInfoCount(String healthType, String kpiId, String timeType, Date starttime, Date endtime) ;
}
