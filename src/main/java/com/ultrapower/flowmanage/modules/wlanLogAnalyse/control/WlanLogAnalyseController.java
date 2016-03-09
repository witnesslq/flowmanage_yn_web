package com.ultrapower.flowmanage.modules.wlanLogAnalyse.control;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Controller;

import com.ultrapower.flowmanage.common.vo.Page;
import com.ultrapower.flowmanage.modules.wlanLogAnalyse.service.WlanLogAnalyseService;
import com.ultrapower.flowmanage.modules.wlanLogAnalyse.vo.WlanLogParameter;

/**
 * 湖北移动流量管理系统-WLAN日志分析
 * @author Yuanxihua
 *
 */
@Controller(value="wlanLogAnalyseController")
@RemotingDestination(channels={"my-amf"})
public class WlanLogAnalyseController {
	private static Logger logger = Logger.getLogger(WlanLogAnalyseController.class);
	
	@Autowired
	private WlanLogAnalyseService wlanLogAnalyseService;
	
	/**
	 * 根据时间、用户名称、上线认证、下线认证、认证成功、认证失败分页查询portal日志列表
	 * @param Yuanxihua
	 * @return
	 */
	public Page findPortalLogTable(WlanLogParameter parameter){
		Page page = null;
		try {
			page = wlanLogAnalyseService.findPortalLogTable(parameter);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据时间、用户名称、上线认证、下线认证、认证成功、认证失败分页查询portal日志列表：  " + page);
			e.printStackTrace();
		}
		return page;
	}
	
	/**
	 * 根据时间、用户名称、执行成功、执行失败分页查询MML日志列表
	 * @author Yuanxihua
	 * @return
	 */
	public Page findMmlLogTable(WlanLogParameter parameter){
		Page page = null;
		try {
			page = wlanLogAnalyseService.findMmlLogTable(parameter);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据时间、用户名称、执行成功、执行失败分页查询MML日志列表：  " + page);
			e.printStackTrace();
		}
		return page;
	}
	
	/**
	 * 根据时间、用户名称、上线认证、下线认证、认证成功、认证失败查询portal日志导出Excel表格
	 * @author Yuanxihua
	 * @param parameter
	 * @return
	 */
	public byte[] exportPortalLogExcelTable(WlanLogParameter parameter){
		byte[] bt = null;
		try {
			bt = wlanLogAnalyseService.exportPortalLogExcelTable(parameter);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据时间、用户名称、上线认证、下线认证、认证成功、认证失败查询portal日志导出Excel表格：  " + bt);
			e.printStackTrace();
		}
		return bt;
	}
	
	/**
	 * 根据时间、用户名称、执行成功、执行失败查询MML日志导出Excel表格
	 * @author Yuanxihua
	 * @param parameter
	 * @return
	 */
	public byte[] exportMmlLogExcelTable(WlanLogParameter parameter){
		byte[] bt = null;
		try {
			bt = wlanLogAnalyseService.exportMmlLogExcelTable(parameter);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据时间、用户名称、执行成功、执行失败查询MML日志导出Excel表格：  " + bt);
			e.printStackTrace();
		}
		return bt;
	}
	
}
