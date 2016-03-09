package com.ultrapower.flowmanage.modules.keyIndexSurvey.service;

import java.util.List;
import java.util.Map;

import com.ultrapower.flowmanage.common.vo.Page;

public interface KeyIndexSurveyService {
	//根据时间查询手机点击本网率
	List<Map<String, Object>> findFlowNetRate(String getTime) throws Exception;
	//根据时间查询手机点击本网率明细列表
	Page findFlowNetRateTable(int pageNo, int pageSize, String getTime) throws Exception;
	//根据时间查询手机点击本网率
	List<Map<String, Object>> findPhoneHiteRate(String getTime) throws Exception;
	//根据时间查询手机点击本网率明细列表
	Page findPhoneHiteRateTable(int pageNo, int pageSize, String getTime) throws Exception;
	//根据时间查询本省内容满足率
	List<Map<String, Object>> findContentmeetRate(String getTime) throws Exception;
	//根据时间查询本省内容满足率明细列表
	Page findContentmeetRateTable(int pageNo, int pageSize, String getTime) throws Exception;
	
	//互联网关键指标监测表格数据Excel导出
	byte[] keyIndexSurveyExcelTable(int pageNo, int pageSize, String getTime) throws Exception;
}
