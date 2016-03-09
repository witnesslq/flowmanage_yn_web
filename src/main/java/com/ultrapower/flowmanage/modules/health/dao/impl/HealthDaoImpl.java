package com.ultrapower.flowmanage.modules.health.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ultrapower.flowmanage.common.vo.Tree;
import com.ultrapower.flowmanage.modules.flow.vo.FsChartVo;
import com.ultrapower.flowmanage.modules.health.dao.HealthDao;
import com.ultrapower.flowmanage.modules.health.dao.mapper.HealthMapper;
import com.ultrapower.flowmanage.modules.health.vo.AssDimRelevance;
import com.ultrapower.flowmanage.modules.health.vo.CityContrastVo;
import com.ultrapower.flowmanage.modules.health.vo.CountryContrastVo;
import com.ultrapower.flowmanage.modules.health.vo.Emp;
import com.ultrapower.flowmanage.modules.health.vo.ThreeNetHealth;

@Service("healthdao")
public class HealthDaoImpl implements HealthDao{
	private static Logger logger = Logger.getLogger(HealthDaoImpl.class);
	
	@Resource(name="healthmapper")
	private HealthMapper healthMapper;
	
	public List<Emp> findEmp() throws Exception{
		return healthMapper.findEmp();
	}

	public List<Emp> findEmpByEmpno(String empno) throws Exception{
		return healthMapper.findEmpByEmpno(empno);
	}

	public List<ThreeNetHealth> findThreeNetHealth(String healthtype, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception{
		return healthMapper.findThreeNetHealth(healthtype, getMonthFirstDay, nextMonthFirstDay);
	}
	public List<ThreeNetHealth> findThreeNetHealthOfDay(String healthtype, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception{
		return healthMapper.findThreeNetHealthOfDay(healthtype, getMonthFirstDay, nextMonthFirstDay);
	}
	public List<CityContrastVo> findCityHealthScoreById(String healthId, String healthType, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception{
		return healthMapper.findCityHealthScoreById(healthId, healthType, getMonthFirstDay, nextMonthFirstDay);
	}
	public List<CityContrastVo> findCityHealthScoreByIdOfDay(String healthId, String healthType, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception{
		return healthMapper.findCityHealthScoreByIdOfDay(healthId, healthType, getMonthFirstDay, nextMonthFirstDay);
	}
	public List<CityContrastVo> findHealthNameAndScoreById(String healthId, String healthtype, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception{
		return healthMapper.findHealthNameAndScoreById(healthId,healthtype,getMonthFirstDay,nextMonthFirstDay);
	}
	public List<CityContrastVo> findHealthNameAndScoreByIdOfDay(String healthId, String healthtype, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception{
		return healthMapper.findHealthNameAndScoreByIdOfDay(healthId,healthtype,getMonthFirstDay,nextMonthFirstDay);
	}
	public List<CityContrastVo> findHealthBizScope() throws Exception{
		return healthMapper.findHealthBizScope();
	}

	public List<CityContrastVo> findCityInfo() throws Exception{
		return healthMapper.findCityInfo();
	}

	public List<CityContrastVo> findHealthNameByType(String healthType) throws Exception{
		return healthMapper.findHealthNameByType(healthType);
	}

	public List<CityContrastVo> findHealthNameScoreByHealthId(String healthId, String healthType, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception{
		return healthMapper.findHealthNameScoreByHealthId(healthId, healthType, getMonthFirstDay, nextMonthFirstDay);
	}
	public List<CityContrastVo> findHealthNameScoreByHealthIdOfDay(String healthId, String healthType, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception{
		return healthMapper.findHealthNameScoreByHealthIdOfDay(healthId, healthType, getMonthFirstDay, nextMonthFirstDay);
	}
	public List<Tree> getHealthTable(String type, String area, String city, Date starttime, Date endtime) throws Exception {
		return healthMapper.getHealthTable(type, area, city, starttime, endtime);
	}
	public List<Tree> getHealthTableOfDay(String type, String area, String city, Date starttime, Date endtime) throws Exception {
		return healthMapper.getHealthTableOfDay(type, area, city, starttime, endtime);
	}
	
	
	public List<Tree> getMenuTable(String type,String lev) throws Exception {
		return healthMapper.getMenuTable(type,lev);
	}

	/**
	 * 全国对比排名
	 * @return
	 */
	public List<CountryContrastVo> getCountryConstrastList(String healthtype, Date beforeMonthFirstDay, Date getMonthFirstDay)throws Exception  {
		
		return healthMapper.getCountryConstrastList(healthtype,beforeMonthFirstDay,getMonthFirstDay);
	}
	/**
	 * 全国对比排名
	 * @return
	 */
	public List<CountryContrastVo> getCountryConstrastListOfDay(String healthtype, Date beforeMonthFirstDay, Date getMonthFirstDay)throws Exception  {
		
		return healthMapper.getCountryConstrastListOfDay(healthtype,beforeMonthFirstDay,getMonthFirstDay);
	}

	/**
	 * 获取评估点地市对比
	 */
	public List<AssDimRelevance> getCityContrast(String healthtype)throws Exception  {
		
		return healthMapper.getCityContrast(healthtype);
	}
	//评估点地市对比子节点
	public List<AssDimRelevance> getCityContrastChild(String fatherId)throws Exception {
		return healthMapper.getCityContrastChild(fatherId);
	}
	//全省纵览
	public List<Map<String, String>> getprovinceView(String healthType, Date getMonthFirstDay, Date nextMonthFirstDay)throws Exception {
		return healthMapper.getprovinceView(healthType, getMonthFirstDay, nextMonthFirstDay);
	}
	//全省纵览
	public List<Map<String, String>> getprovinceViewOfDay(String healthType, Date getMonthFirstDay, Date nextMonthFirstDay)throws Exception {
		return healthMapper.getprovinceViewOfDay(healthType, getMonthFirstDay, nextMonthFirstDay);
	}
		//竟争对手对比
	public List<Map<String,String>>getOperView(Date beforeMonthFirstDay, Date getMonthFirstDay)throws Exception {
		return healthMapper.getOperView(beforeMonthFirstDay,getMonthFirstDay);
	}
	//竟争对手对比
	public List<Map<String,String>>getOperViewOfDay(Date beforeMonthFirstDay, Date getMonthFirstDay)throws Exception {
		return healthMapper.getOperViewOfDay(beforeMonthFirstDay,getMonthFirstDay);
	}
	//评估点地市对比charts
	public List<Map<String,String>>getCityConstrastCharts(String healthname,String healthtype, Date beforeMonthFirstDay, Date getMonthFirstDay)throws Exception {
		return healthMapper.getCityConstrastCharts(healthname, healthtype,beforeMonthFirstDay,getMonthFirstDay);
	}
	//评估点地市对比charts
	public List<Map<String,String>>getCityConstrastChartsOfDay(String healthname,String healthtype, Date beforeMonthFirstDay, Date getMonthFirstDay)throws Exception {
		return healthMapper.getCityConstrastChartsOfDay(healthname, healthtype,beforeMonthFirstDay,getMonthFirstDay);
	}

	//执行导出WORD的数据汇聚存储
	public void proWordExportDay(Map para)throws Exception
	{
		healthMapper.proWordExportDay(para);
	}
	//查找WORD数据
	public List<Map<String, BigDecimal>> getWordDataForDate(String date)throws Exception
	{
		return healthMapper.getWordDataForDate(date);
	}

	//根据时间粒度、健康度指标ID、健康度类型查询各地市健康度指标名称和分数
	public List<CityContrastVo> findHealthNameScoreByTime(String timeType, String healthType, String healthId, Date startTime, Date endTime)throws Exception {
		return healthMapper.findHealthNameScoreByTime(timeType, healthType, healthId, startTime, endTime);
	}
	
	//根据时间粒度、健康度指标ID、健康度类型查询健康度分数和指标值
	public List<Map<String, String>> findHealthScoreKpiByType(String healthType, String healthId, String startTime, String endTime, String timeType, String num) throws Exception {
		return healthMapper.findHealthScoreKpiByType(healthType, healthId, startTime, endTime, timeType, num);
	}

	public List<Map<String, String>> findDICInfo(String sql) throws Exception {
		return healthMapper.findDICInfo(sql);
	}
	
	//指标详情 TOP7 charts 月粒度
	public List<Map<String,String>>getKPIDetailTop7(String kpiId, Date startTime, Date endTime, String startTimeStr, String endTimeStr)throws Exception {
		return healthMapper.getKPIDetailTop7(kpiId,startTime,endTime,startTimeStr,endTimeStr);
	}
	//指标详情 TOP7 charts 天粒度
	public List<Map<String,String>>getKPIDetailTop7OfDay(String kpiId, Date startTime, Date endTime, String startTimeStr, String endTimeStr)throws Exception {
		return healthMapper.getKPIDetailTop7OfDay(kpiId,startTime,endTime,startTimeStr,endTimeStr);
	}
	
	@Override
	public List<Map<String,String>> getUserDefinedInfo(String healthType, String kpiId, String timeType, Date starttime, Date endtime,int pageSize, int pageNo) {
		return healthMapper.getUserDefinedInfo(healthType, kpiId, timeType, starttime, endtime, pageSize,pageNo);
	}
	@Override
	public List<Map<String,String>> getExitHealthDetail(String healthType, String kpiId, String timeType, Date starttime, Date endtime,int pageSize, int pageNo) {
		return healthMapper.getExitHealthDetail(healthType, kpiId, timeType, starttime, endtime, pageSize,pageNo);
	}
	@Override
	public int getUserDefinedInfoCount(String healthType, String kpiId, String timeType, Date starttime, Date endtime) {
		return healthMapper.getUserDefinedInfoCount(healthType, kpiId, timeType, starttime, endtime);
	}

}
