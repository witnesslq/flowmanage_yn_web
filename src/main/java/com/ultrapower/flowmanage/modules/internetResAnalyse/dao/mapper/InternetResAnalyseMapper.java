package com.ultrapower.flowmanage.modules.internetResAnalyse.dao.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ultrapower.flowmanage.modules.flow.vo.FsChartVo;

public interface InternetResAnalyseMapper {
	//根据网站类型查询资源数量及大小分析TOP10网站名称
	List<Map<String, Object>> findTop10WebName(@Param("webType")String webType, @Param("sourceType")String sourceType, @Param("getTime")String getTime) throws Exception;
		
	//根据网站类型、网站名称查询各运营商资源数量及大小(饼图)
	List<Map<String, Object>> findSourceAnalyse(@Param("webType")String webType, @Param("webName")String webName, @Param("sourceType")String sourceType, @Param("getTime")String getTime) throws Exception;
	//根据网站类型和网站名称查询各归属地移动资源大小及数量排名
	List<Map<String, String>> findMobileSourceRank(@Param("webType")String webType, @Param("webName")String webName, @Param("sourceType")String sourceType, @Param("getTime")String getTime) throws Exception;
	//根据网站类型、网站名称分页查询资源明细列表记录数
	int findResDetailTableCount(@Param("webType")String webType, @Param("webName")String webName, @Param("today")Date today, @Param("nextDay")Date nextDay) throws Exception;
	//根据网站类型、网站名称分页查询资源明细列表
	List<Map<String, String>> findResDetailTable(@Param("webType")String webType, @Param("webName")String webName, @Param("today")Date today, @Param("nextDay")Date nextDay, @Param("pageNo")int pageNo, @Param("pageSize")int pageSize) throws Exception;
	//根据网站类型、网站名称分页查询资源明细列表
	List<Map<String, String>> findResDetailTableAll(@Param("webType")String webType, @Param("webName")String webName, @Param("today")Date today, @Param("nextDay")Date nextDay) throws Exception;
}
