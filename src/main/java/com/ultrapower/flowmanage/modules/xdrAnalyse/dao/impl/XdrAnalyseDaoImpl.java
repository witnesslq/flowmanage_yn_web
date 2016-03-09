package com.ultrapower.flowmanage.modules.xdrAnalyse.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ultrapower.flowmanage.modules.xdrAnalyse.dao.XdrAnalyseDao;
import com.ultrapower.flowmanage.modules.xdrAnalyse.dao.mapper.XdrAnalyseMapper;

@Service("xdrAnalyseDao")
public class XdrAnalyseDaoImpl implements XdrAnalyseDao {
	private static Logger logger = Logger.getLogger(XdrAnalyseDaoImpl.class);
	@Resource(name="xdrAnalyseMapper")
	private XdrAnalyseMapper xdrAnalyseMapper;

	//根据类型、上级ID查询ID和名称
	public List<Map<String, String>> findDICInfo(String objectType, String parentId) throws Exception {
		return xdrAnalyseMapper.findDICInfo(objectType, parentId);
	}

	//根据区县名称查询网格编号
	public List<Map<String, String>> findWGNumberBydistrict(String district) throws Exception {
		return xdrAnalyseMapper.findWGNumberBydistrict(district);
	}

	//根据时间、用户群ID、业务类型ID查询用户点击量信息记录数
	public int findUserClickRateInfoRowCount(String userGroupId, String busiTypeId, String time) throws Exception{
		return xdrAnalyseMapper.findUserClickRateInfoRowCount(userGroupId, busiTypeId, time);
	}
	
	//根据时间、用户群ID、业务类型ID分页查询用户点击量信息列表
	public List<Map<String, String>> findUserClickRateInfoTable(String userGroupId, String busiTypeId, String time, int pageNo, int pageSize) throws Exception {
		return xdrAnalyseMapper.findUserClickRateInfoTable(userGroupId, busiTypeId, time, pageNo, pageSize);
	}

	//根据时间段、地市ID、城市ID、网格编号、用户群ID、业务类型ID查询点击率最高TOP15网站
	public List<Map<String, String>> findUserClickRateWebTOP15(String cityId,
			String countyId, String gridNumber, String userGroupId,
			String busiTypeId, String time) throws Exception {
		return xdrAnalyseMapper.findUserClickRateWebTOP15(cityId, countyId, gridNumber, userGroupId, busiTypeId, time);
	}

	//根据时间、用户群ID、业务类型ID查询访问地市质量分析
	public List<Map<String, String>> findVisitQualityByCity(String userGroupId, String busiTypeId, String time) throws Exception{
		return xdrAnalyseMapper.findVisitQualityByCity(userGroupId, busiTypeId, time);
	}
	
	//根据时间、用户群ID、业务类型ID查询访问区县质量分析
	public List<Map<String, String>> findVisitQualityByCounty(String userGroupId, String busiTypeId, String cityId, String time) throws Exception{
		return xdrAnalyseMapper.findVisitQualityByCounty(userGroupId, busiTypeId, cityId, time);
	}
	
	//根据时间、用户群ID、业务类型ID查询网站质量分析记录数
	public int findSitesQualityRowCount(String userGroupId, String busiTypeId, String time) throws Exception{
		return xdrAnalyseMapper.findSitesQualityRowCount(userGroupId, busiTypeId, time);
	}
	
	//根据时间、用户群ID、业务类型ID分页查询网站质量分析列表
	public List<Map<String, String>> findSitesQualityTable(String userGroupId, String busiTypeId, String time, int pageNo, int pageSize) throws Exception{
		return xdrAnalyseMapper.findSitesQualityTable(userGroupId, busiTypeId, time, pageNo, pageSize);
	}
	
	//根据时间、用户群ID、业务类型ID查询网站质量分析记录数
	public int findSitesQualityWarningRowCount(String userGroupId, String busiTypeId, String time, int pageNo, int pageSize) throws Exception{
		return xdrAnalyseMapper.findSitesQualityWarningRowCount(userGroupId, busiTypeId, time);
	}
	
	//根据时间、用户群ID、业务类型ID分页查询网站质量分析列表
	public List<Map<String, String>> findSitesQualityWarningTable(String userGroupId, String busiTypeId, String time, int pageNo, int pageSize) throws Exception{
		return xdrAnalyseMapper.findSitesQualityWarningTable(userGroupId, busiTypeId, time, pageNo, pageSize);
	}
		
}
