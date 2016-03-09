package com.ultrapower.flowmanage.modules.wlanLogAnalyse.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ultrapower.flowmanage.modules.wlanLogAnalyse.vo.WlanLogParameter;

public interface WlanLogAnalyseDao {
	
	//根据时间、用户名称、上线认证、下线认证、认证成功、认证失败查询portal日志记录数
	int findPortalLogRowCount(WlanLogParameter parameter) throws Exception;

	//根据时间、用户名称、上线认证、下线认证、认证成功、认证失败分页查询portal日志列表
	List<Map<String, String>> findPortalLogTable(WlanLogParameter parameter) throws Exception;

	//根据时间、用户名称、执行成功、执行失败查询MML日志记录数
	int findMmlLogRowCount(WlanLogParameter parameter) throws Exception;

	//根据时间、用户名称、执行成功、执行失败分页查询MML日志列表
	List<Map<String, String>> findMmlLogTable(WlanLogParameter parameter) throws Exception;

}
