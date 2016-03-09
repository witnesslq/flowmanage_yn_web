package com.ultrapower.flowmanage.modules.xdrAnalyse.service;

import java.util.List;
import java.util.Map;

import com.ultrapower.flowmanage.common.vo.Page;
	
public interface XdrAnalyseService {
	//根据类型、上级ID查询ID和名称
	List<Map<String, String>> findDICInfo(String objectType, String parentId) throws Exception;
	//根据区县名称查询网格编号
	List<Map<String, String>> findWGNumberBydistrict(String district) throws Exception;
	//根据时间、用户群ID、业务类型ID分页查询用户点击量信息列表
	Page findUserClickRateInfoTable(String userGroupId, String busiTypeId, String time, int pageNo, int pageSize) throws Exception;
	//根据时间段、地市ID、城市ID、网格编号、用户群ID、业务类型ID查询点击率最高TOP15网站
	List<Map<String, String>> findUserClickRateWebTOP15(String cityId, String countyId, String gridNumber, String userGroupId, String busiTypeId, String time) throws Exception;
	//根据时间、用户群ID、业务类型ID查询访问地市质量分析
	List<Map<String, String>> findVisitQualityByCity(String userGroupId, String busiTypeId, String time) throws Exception; 
	//根据时间、用户群ID、业务类型ID查询访问区县质量分析
	List<Map<String, String>> findVisitQualityByCounty(String userGroupId, String busiTypeId, String cityId, String time) throws Exception;
	
	//根据时间、用户群ID、业务类型ID分页查询网站质量分析列表
	Page findSitesQualityTable(String userGroupId, String busiTypeId, String time, int pageNo, int pageSize) throws Exception;
	//根据时间、用户群ID、业务类型ID分页查询网站质量分析列表
	Page findSitesQualityWarningTable(String userGroupId, String busiTypeId, String time, int pageNo, int pageSize) throws Exception;
				
}
