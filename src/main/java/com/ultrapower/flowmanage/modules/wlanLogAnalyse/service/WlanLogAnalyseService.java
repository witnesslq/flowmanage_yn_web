package com.ultrapower.flowmanage.modules.wlanLogAnalyse.service;

import com.ultrapower.flowmanage.common.vo.Page;
import com.ultrapower.flowmanage.modules.wlanLogAnalyse.vo.WlanLogParameter;

public interface WlanLogAnalyseService {
	
	//根据时间、用户名称、上线认证、下线认证、认证成功、认证失败分页查询portal日志列表
	Page findPortalLogTable(WlanLogParameter parameter) throws Exception;
	
	//根据时间、用户名称、执行成功、执行失败分页查询MML日志列表
	Page findMmlLogTable(WlanLogParameter parameter) throws Exception;
	
	//根据时间、用户名称、上线认证、下线认证、认证成功、认证失败查询portal日志导出Excel表格
	byte[] exportPortalLogExcelTable(WlanLogParameter parameter) throws Exception;
	
	//根据时间、用户名称、执行成功、执行失败查询MML日志导出Excel表格
	byte[] exportMmlLogExcelTable(WlanLogParameter parameter) throws Exception;
	
}
