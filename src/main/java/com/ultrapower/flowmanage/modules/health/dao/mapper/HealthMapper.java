package com.ultrapower.flowmanage.modules.health.dao.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.ultrapower.flowmanage.common.vo.Tree;
import com.ultrapower.flowmanage.modules.flow.vo.FsChartVo;
import com.ultrapower.flowmanage.modules.health.vo.AssDimRelevance;
import com.ultrapower.flowmanage.modules.health.vo.CityContrastVo;
import com.ultrapower.flowmanage.modules.health.vo.CountryContrastVo;
import com.ultrapower.flowmanage.modules.health.vo.Emp;
import com.ultrapower.flowmanage.modules.health.vo.ThreeNetHealth;

public interface HealthMapper {
	List<Emp> findEmp() throws Exception;
	List<Emp> findEmpByEmpno(String empno) throws Exception;
	List<ThreeNetHealth> findThreeNetHealth(@Param("healthtype")String healthtype, 
			@Param("getMonthFirstDay")Date getMonthFirstDay, @Param("nextMonthFirstDay")Date nextMonthFirstDay) throws Exception;
	List<ThreeNetHealth> findThreeNetHealthOfDay(@Param("healthtype")String healthtype, 
			@Param("getMonthFirstDay")Date getMonthFirstDay, @Param("nextMonthFirstDay")Date nextMonthFirstDay) throws Exception;
	
	List<CityContrastVo> findCityHealthScoreById(String healthId, String healthType, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	List<CityContrastVo> findCityHealthScoreByIdOfDay(String healthId, String healthType, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	
	List<CityContrastVo> findHealthNameAndScoreById(String healthId, String healthtype, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	List<CityContrastVo> findHealthNameAndScoreByIdOfDay(String healthId, String healthtype, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	
	List<CityContrastVo> findHealthBizScope() throws Exception;
	List<CityContrastVo> findCityInfo() throws Exception;
	List<CityContrastVo> findHealthNameByType(String healthType) throws Exception;
	
	List<CityContrastVo> findHealthNameScoreByHealthId(String healthId, String healthType, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	List<CityContrastVo> findHealthNameScoreByHealthIdOfDay(String healthId, String healthType, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	
	List<Tree> getMenuTable(@Param("type")String type,@Param("lev")String lev) throws Exception;
	
	List<Tree> getHealthTable(@Param("type")String type, @Param("area")String area, @Param("city")String city, @Param("starttime")Date starttime, @Param("endtime")Date endtime) throws Exception;
	List<Tree> getHealthTableOfDay(@Param("type")String type, @Param("area")String area, @Param("city")String city, @Param("starttime")Date starttime, @Param("endtime")Date endtime) throws Exception;
	//List<Map<String, String>> getHealthTableOfDay(@Param("type")String type, @Param("area1")String area, @Param("city")String city, @Param("starttime")Date starttime, @Param("endtime")Date endtime) throws Exception;
//	List<Map<String, String>> getHealthTableOfDay(String type, String area) throws Exception;

	/**
	 * 全国对比排名
	 * @return
	 */
	List<CountryContrastVo> getCountryConstrastList(String healthtype, Date beforeMonthFirstDay, Date getMonthFirstDay);
	/**
	 * 全国对比排名
	 * @return
	 */
	List<CountryContrastVo> getCountryConstrastListOfDay(String healthtype, Date beforeMonthFirstDay, Date getMonthFirstDay);

	//评估点地市对比
	List<AssDimRelevance> getCityContrast(String healthtype);
	//评估点地市对比子节点
	List<AssDimRelevance> getCityContrastChild(String fatherId);
	//全省纵览
	List<Map<String, String>> getprovinceView(@Param("healthType")String healthType, @Param("starttime")Date starttime, @Param("endtime")Date endtime);
	//全省纵览
	List<Map<String, String>> getprovinceViewOfDay(@Param("healthType")String healthType, @Param("starttime")Date starttime, @Param("endtime")Date endtime);

	//竟争对手对比
	List<Map<String,String>> getOperView(Date beforeMonthFirstDay, Date getMonthFirstDay);
	//竟争对手对比
	List<Map<String,String>> getOperViewOfDay(Date beforeMonthFirstDay, Date getMonthFirstDay);
	//评估点地市对比charts
	List<Map<String,String>> getCityConstrastCharts(String healthname,String healthtype, Date beforeMonthFirstDay, Date getMonthFirstDay);
	//评估点地市对比charts
	List<Map<String,String>> getCityConstrastChartsOfDay(String healthname,String healthtype, Date beforeMonthFirstDay, Date getMonthFirstDay);

	//执行导出WORD的数据汇聚存储
	void proWordExportDay(Map para);
	//查找WORD数据
	List<Map<String, BigDecimal>> getWordDataForDate(String date);
	//根据时间粒度、健康度指标ID、健康度类型查询各地市健康度指标名称和分数
	List<CityContrastVo> findHealthNameScoreByTime(@Param("timeType")String timeType, @Param("healthType")String healthType, @Param("healthId")String healthId, @Param("startTime")Date startTime, @Param("endTime")Date endTime) throws Exception;

	//根据时间粒度、健康度指标ID、健康度类型查询健康度分数和指标值
	List<Map<String, String>> findHealthScoreKpiByType(@Param("healthType")String healthType, @Param("healthId")String healthId, @Param("startTime")String startTime, @Param("endTime")String endTime,@Param("timeType")String timeType, @Param("num")String num) throws Exception;
	//根据SQL字符串查询字段表信息
	List<Map<String, String>> findDICInfo(@Param("sql")String sql) throws Exception;

	//指标详情 TOP7 charts 月粒度
	List<Map<String,String>> getKPIDetailTop7(@Param("kpiId")String kpiId, @Param("startTime")Date startTime, @Param("endTime")Date endTime, @Param("startTimeStr")String startTimeStr, @Param("endTimeStr")String endTimeStr);
	//指标详情 TOP7 charts 天粒度
	List<Map<String,String>> getKPIDetailTop7OfDay(@Param("kpiId")String kpiId, @Param("startTime")Date startTime, @Param("endTime")Date endTime, @Param("startTimeStr")String startTimeStr, @Param("endTimeStr")String endTimeStr);
	//查询自定义表格
	List<Map<String,String>> getUserDefinedInfo(@Param("healthType")String healthType, @Param("kpiId")String kpiId, @Param("timeType")String timeType, @Param("starttime")Date starttime, @Param("endtime")Date endtime, @Param("pageSize") int pageSize, @Param("pageNo") int pageNo) ;
	//网间出口健康度明细
	List<Map<String,String>> getExitHealthDetail(@Param("healthType")String healthType, @Param("kpiId")String kpiId, @Param("timeType")String timeType, @Param("starttime")Date starttime, @Param("endtime")Date endtime, @Param("pageSize") int pageSize, @Param("pageNo") int pageNo) ;

	int getUserDefinedInfoCount(@Param("healthType")String healthType, @Param("kpiId")String kpiId, @Param("timeType")String timeType, @Param("starttime")Date starttime, @Param("endtime")Date endtime);

}
