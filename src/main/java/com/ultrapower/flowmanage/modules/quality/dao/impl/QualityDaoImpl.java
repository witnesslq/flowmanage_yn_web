package com.ultrapower.flowmanage.modules.quality.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ultrapower.flowmanage.modules.flow.vo.FsChartVo;
import com.ultrapower.flowmanage.common.vo.Tree;
import com.ultrapower.flowmanage.modules.quality.dao.QualityDao;
import com.ultrapower.flowmanage.modules.quality.dao.mapper.QualityMapper;
import com.ultrapower.flowmanage.modules.quality.vo.Test;
import com.ultrapower.flowmanage.modules.quality.vo.ThresholdConfigVo;
@Service("qualityDao")
public class QualityDaoImpl implements QualityDao {
	private static Logger logger = Logger.getLogger(QualityDaoImpl.class);
	@Resource(name="qualityMapper")
	private QualityMapper qualityMapper;

	public List<Map<String, Object>> findCityTop200WebsiteTimedelay(
			Date startTime, Date endTime,String timeType,String companyId) throws Exception {
		return qualityMapper.findCityTop200WebsiteTimedelay(startTime,endTime,timeType,companyId);
	}

	

	//接入KQI地市对比
	public	List<Map<String,String>> findKQIMenu() throws Exception{
		return qualityMapper.findKQIMenu();
	}
	//接入KQI出口对比
	public List<FsChartVo>findKQIExit(String exitid,Date getMonthFirstDay, Date nextMonthFirstDay)throws Exception{
		return qualityMapper.findKQIExit(exitid, getMonthFirstDay, nextMonthFirstDay);
	}
	//接入KQI地市对比
	public 	List<FsChartVo> findKQICityComponent(String kqiType,String kpiId,Date startTime, Date endTime,String timeType,String companyId,String cityId) throws Exception{
		return qualityMapper.findKQICityComponent(kqiType, kpiId, startTime, endTime,timeType,companyId,cityId);
	}
		
	//业务KQI地市对比 -导航详情
	@Override
	public List<Tree> getBusiKqiNavTable(String type) throws Exception {
		List<Tree> list = qualityMapper.getBusiKqiNavTable(type);
		return list;
	}
	//业务KQI地市对比菜单
	public List<Map<String,String>> findBUIKQIMenu() throws Exception{
		return qualityMapper.findBUIKQIMenu();
	}

	//根据业务类型查询互联网投诉及用户满意度地市对比-互联网质量评估
	public List<FsChartVo> findNetComplaintCSATCityContrast(String kpiId,Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception {
		return qualityMapper.findNetComplaintCSATCityContrast(kpiId, getMonthFirstDay, nextMonthFirstDay);
	}

	//根据字典表类型查询业务名称
	public List<Map<String, Object>> findBusinessName(String bizType) throws Exception {
		return qualityMapper.findBusinessName(bizType);
	}
	
	//根据地市ID和业务类型查询省内热点网站情况
	public List<Map<String, Object>> findProvincialHotspotNetTable(String bizId, String cityId, Date startTime, Date endTime,String timeType,String companyId) throws Exception {
		return qualityMapper.findProvincialHotspotNetTable(bizId, cityId, startTime, endTime,timeType,companyId);
	}

	//根据接入或业务类型和KPIID查询KPI地市对比 
	public List<Map<String, String>> findKPICityComponent(String kqiType, String kpiId,Date startTime, Date endTime,String timeType,String companyId,String cityId) throws Exception {
		return qualityMapper.findKPICityComponent(kqiType, kpiId, startTime, endTime,timeType,companyId,cityId);
	}

	//根据接入或业务类型查询KQI地市对比(菜单)
	public List<Map<String, Object>> findKQICityMenu(String objectType)throws Exception {
		return qualityMapper.findKQICityMenu(objectType);
	}

	//根据业务类型和业务级别查询接入KQI出口指标名称
	public List<Map<String, String>> findAccKQIExitName(String objectType, String lev) throws Exception {
		return qualityMapper.findAccKQIExitName(objectType, lev);
	}

	//根据KPIID查询接入KQI出口对比
	public List<FsChartVo> findAccKQIExitContrast(String kpiId, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception {
		return qualityMapper.findAccKQIExitContrast(kpiId, getMonthFirstDay, nextMonthFirstDay);
	}

	//根据KPI的父级ID查询接入KPI出口对比
	public List<Map<String, String>> findAccKPIExitContrast(String kpiId, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception {
		return qualityMapper.findAccKPIExitContrast(kpiId, getMonthFirstDay, nextMonthFirstDay);
	}

	@Override
	public void insertTest(Test test) throws Exception {
		qualityMapper.insertTest(test);
		
	}

	public List<HashMap<String, String>> findCompany() throws Exception {
		// TODO Auto-generated method stub
		return qualityMapper.findCompany();
	}



	@Override
	public List<Map<String, Object>> findGroupHotspotTop10Net(Date startTime,
			Date endTime, String timeType, String companyId) throws Exception {
		// TODO Auto-generated method stub
		return qualityMapper.findGroupHotspotTop10Net(startTime, endTime, timeType, companyId);
	}



	@Override
	public List<HashMap<String, Object>> getZidingyi1(String timeType,String city,
			String company, Date starttime, Date endtime,int pageSize, int pageNo) {
		// TODO Auto-generated method stub
		return qualityMapper.getZidingyi1(timeType,city, company, starttime, endtime,
				pageSize,pageNo);
	}


	@Override
	public int getZidingyiCount1(String timeType,String city, String company, Date starttime,
			Date endtime) {
		// TODO Auto-generated method stub
		return qualityMapper.getZidingyiCount1(timeType,city, company, starttime, endtime);
	}

	@Override
	public int getZidingyiCount2(String timeType,String city, String company, Date starttime,
			Date endtime) {
		// TODO Auto-generated method stub
		return qualityMapper.getZidingyiCount2(timeType,city, company, starttime, endtime);
	}
	@Override
	public List<HashMap<String, Object>> getZidingyi2(String timeType,String city,
			String company, Date starttime, Date endtime,int pageSize, int pageNo) {
		// TODO Auto-generated method stub
		return qualityMapper.getZidingyi2(timeType,city, company, starttime, endtime,
				pageSize,pageNo);
	}



	@Override
	public int getZidingyiCount3(String timeType,String city, String company, Date starttime,
			Date endtime) {
		// TODO Auto-generated method stub
		return qualityMapper.getZidingyiCount3(timeType,city, company, starttime, endtime);
	}
	@Override
	public List<HashMap<String, Object>> getZidingyi3(String timeType,String city,
			String company, Date starttime, Date endtime,int pageSize, int pageNo) {
		// TODO Auto-generated method stub
		return qualityMapper.getZidingyi3(timeType,city, company, starttime, endtime,
				pageSize,pageNo);
	}



	@Override
	public int getZidingyiCount4(String timeType,String city, String company, Date starttime,
			Date endtime) {
		// TODO Auto-generated method stub
		return qualityMapper.getZidingyiCount4(timeType,city, company, starttime, endtime);
	}
	@Override
	public List<HashMap<String, Object>> getZidingyi4(String timeType,String city,
			String company, Date starttime, Date endtime,int pageSize, int pageNo) {
		// TODO Auto-generated method stub
		return qualityMapper.getZidingyi4(timeType,city, company, starttime, endtime,
				pageSize,pageNo);
	}



	@Override
	public List<HashMap<String,Object>> getXiangxi4(String webname,
			List<String> city, String company, Date starttime, Date endtime,
			int pageSize, int pageNo) {
		// TODO Auto-generated method stub
		return qualityMapper.getXiangxi4(webname, city, company, starttime, endtime, pageSize, pageNo);
	}



	@Override
	public int getXiangxiCount4(String webname, List<String> city, String company,
			Date starttime, Date endtime) {
		// TODO Auto-generated method stub
		return qualityMapper.getXiangxiCount4(webname, city, company, starttime, endtime);
	}



	@Override
	public List<HashMap<String,Object>> getXiangxi3(List<String> city,
			String company, Date starttime, Date endtime, int pageSize,
			int pageNo) {
		// TODO Auto-generated method stub
		return qualityMapper.getXiangxi3( city, company, starttime, endtime, pageSize, pageNo);
	}



	@Override
	public int getXiangxiCount3(List<String> city, String company, Date starttime,
			Date endtime) {
		// TODO Auto-generated method stub
		return qualityMapper.getXiangxiCount3( city, company, starttime, endtime);
	}



	@Override
	public List<HashMap<String,Object>> getXiangxi1(String yewu,List<String> city,
			String company, Date starttime, Date endtime, int pageSize,
			int pageNo) {
		// TODO Auto-generated method stub
		return qualityMapper.getXiangxi1(yewu, city, company, starttime, endtime, pageSize, pageNo);
	}



	@Override
	public int getXiangxiCount1(String yewu,List<String> city, String company, Date starttime,
			Date endtime) {
		// TODO Auto-generated method stub
		return qualityMapper.getXiangxiCount1(yewu, city, company, starttime, endtime);
	}



	@Override
	public List<HashMap<String,Object>> getXiangxi2(String yewu,List<String> city,
			String company, Date starttime, Date endtime, int pageSize,
			int pageNo) {
		// TODO Auto-generated method stub
		return qualityMapper.getXiangxi2(yewu, city, company, starttime, endtime, pageSize, pageNo);
	}



	@Override
	public int getXiangxiCount2(String yewu,List<String> city, String company, Date starttime,
			Date endtime) {
		// TODO Auto-generated method stub
		return qualityMapper.getXiangxiCount2(yewu, city, company, starttime, endtime);
	}


	@Override
	public List<Map<String, String>> findQualityKPIInfo(String timeType, Date startTime, Date endTime) throws Exception{
		return qualityMapper.findQualityKPIInfo(timeType, startTime, endTime);
	}



	@Override
	public List<ThresholdConfigVo> findThresholdConfig(String moduleName,
			String bizType) throws Exception {
		return qualityMapper.findThresholdConfig(moduleName, bizType);
	}
}
