package com.ultrapower.flowmanage.modules.keyIndexSurvey.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ultrapower.flowmanage.common.vo.Page;

public interface KeyIndexSurveyDao {
		//根据时间查询手机点击本网率
		List<Map<String, Object>> findFlowNetRate(Date startTime, Date endTime) throws Exception;
		//根据时间查询手机点击本网率明细列表
		List<Map<String, String>> findFlowNetRateTable(Date startTime, Date endTime, int pageNo, int pageSize) throws Exception;
		//根据时间查询手机点击本网率明细列表记录数
		int findFlowNetRateTableCount(Date startTime, Date endTime) throws Exception;
		
		//根据时间查询手机点击本网率
		List<Map<String, Object>> findPhoneHiteRate(Date startTime, Date endTime) throws Exception;
		//根据时间查询手机点击本网率明细列表
		List<Map<String, String>> findPhoneHiteRateTable(Date startTime, Date endTime, int pageNo, int pageSize) throws Exception;
		//根据时间查询手机点击本网率明细列表记录数
		int findPhoneHiteRateTableCount(Date startTime, Date endTime) throws Exception;
		
		//根据时间查询本省内容满足率
		List<Map<String, Object>> findContentmeetRate(Date startTime, Date endTime) throws Exception;
		//根据时间查询本省内容满足率明细列表
		List<Map<String, String>> findContentmeetRateTable(Date startTime, Date endTime, int pageNo, int pageSize) throws Exception;
		//根据时间查询手机点击本网率明细列表记录数
		int findContentmeetRateTableCount(Date startTime, Date endTime) throws Exception;
	
		
}
