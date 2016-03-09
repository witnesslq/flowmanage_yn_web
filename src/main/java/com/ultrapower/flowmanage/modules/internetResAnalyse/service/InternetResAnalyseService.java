package com.ultrapower.flowmanage.modules.internetResAnalyse.service;

import java.util.List;
import java.util.Map;

import com.ultrapower.flowmanage.common.vo.Page;

public interface InternetResAnalyseService {
	//根据网站类型查询资源数量及大小分析TOP10网站名称
	List<Map<String, Object>> findTop10WebName(String webType, String sourceType, String getTime) throws Exception;
		
	//根据网站类型、网站名称查询各运营商资源数量及大小(饼图)
	List<Map<String, Object>> findSourceAnalyse(String webType, String webName, String sourceType, String getTime) throws Exception;
	//根据网站类型和网站名称查询各归属地移动资源大小及数量排名
	List<Map<String, String>> findMobileSourceRank(String webType, String webName, String sourceType, String getTime) throws Exception;
	//根据网站类型、网站名称分页查询资源明细列表
	Page findResDetailTable(int pageNo, int pageSize, String webType, String webName, String getTime) throws Exception;
	//互联网资源分析表格数据Excel导出
	byte[] qualityExcelTable(String webType,String getTime) throws Exception;	

}
