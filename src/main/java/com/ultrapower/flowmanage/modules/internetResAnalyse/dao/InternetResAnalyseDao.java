package com.ultrapower.flowmanage.modules.internetResAnalyse.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface InternetResAnalyseDao {
	//根据网站类型查询资源数量及大小分析TOP10网站名称
	List<Map<String, Object>> findTop10WebName(String webType, String sourceType, String getTime) throws Exception;
	//根据网站类型、网站名称和运营商查询资源数量及大小分析(饼图)
	List<Map<String, Object>> findSourceAnalyse(String webType, String webName, String sourceType, String time) throws Exception;
	//根据网站类型和网站名称查询各归属地移动资源大小及数量排名
	List<Map<String, String>> findMobileSourceRank(String webType, String webName, String sourceType, String time) throws Exception;
	//根据网站类型、网站名称分页查询资源明细列表记录数
	int findResDetailTableCount(String webType, String webName, Date today, Date nextDay) throws Exception;
	//根据网站类型、网站名称分页查询资源明细列表
	List<Map<String, String>> findResDetailTable(String webType, String webName, Date today, Date nextDay, int pageNo, int pageSize) throws Exception;
	//根据网站类型、网站名称分页查询资源明细列表
	List<Map<String, String>> findResDetailTableAll(String webType, String webName, Date today, Date nextDay) throws Exception;	
}
