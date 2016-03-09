package com.ultrapower.flowmanage.modules.xdrAnalyse.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface XdrAnalyseMapper {
	//根据类型、上级ID查询ID和名称
	List<Map<String, String>> findDICInfo(@Param("objectType")String objectType, @Param("parentId")String parentId) throws Exception;
	//根据区县名称查询网格编号
	List<Map<String, String>> findWGNumberBydistrict(String district) throws Exception;
	//根据时间、用户群ID、业务类型ID查询用户点击量信息记录数
	int findUserClickRateInfoRowCount(String userGroupId, String busiTypeId, String time) throws Exception;
	//根据时间、用户群ID、业务类型ID分页查询用户点击量信息列表
	List<Map<String, String>> findUserClickRateInfoTable(String userGroupId, String busiTypeId, String time, int pageNo, int pageSize) throws Exception;
	//根据时间段、地市ID、城市ID、网格编号、用户群ID、业务类型ID查询点击率最高TOP15网站
	List<Map<String, String>> findUserClickRateWebTOP15(@Param("cityId")String cityId, @Param("countyId")String countyId, @Param("gridNumber")String gridNumber, @Param("userGroupId")String userGroupId, @Param("busiTypeId")String busiTypeId, @Param("time")String time) throws Exception;
	 
	//根据时间、用户群ID、业务类型ID查询访问地市质量分析
	List<Map<String, String>> findVisitQualityByCity(String userGroupId, String busiTypeId, String time) throws Exception; 
	//根据时间、用户群ID、业务类型ID查询访问区县质量分析
	List<Map<String, String>> findVisitQualityByCounty(String userGroupId, String busiTypeId, String cityId, String time) throws Exception;
	//根据时间、用户群ID、业务类型ID查询网站质量分析记录数
	int findSitesQualityRowCount(@Param("userGroupId")String userGroupId, @Param("busiTypeId")String busiTypeId, @Param("time")String time) throws Exception;
	//根据时间、用户群ID、业务类型ID分页查询网站质量分析列表
	List<Map<String, String>> findSitesQualityTable(@Param("userGroupId")String userGroupId, @Param("busiTypeId")String busiTypeId, @Param("time")String time, @Param("pageNo")int pageNo, @Param("pageSize")int pageSize) throws Exception;
	//根据时间、用户群ID、业务类型ID查询网站质量分析记录数
	int findSitesQualityWarningRowCount(@Param("userGroupId")String userGroupId, @Param("busiTypeId")String busiTypeId, @Param("time")String time) throws Exception;
	//根据时间、用户群ID、业务类型ID分页查询网站质量分析列表
	List<Map<String, String>> findSitesQualityWarningTable(@Param("userGroupId")String userGroupId, @Param("busiTypeId")String busiTypeId, @Param("time")String time, @Param("pageNo")int pageNo, @Param("pageSize")int pageSize) throws Exception;
		
}
