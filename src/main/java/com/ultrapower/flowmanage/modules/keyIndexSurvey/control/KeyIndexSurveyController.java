package com.ultrapower.flowmanage.modules.keyIndexSurvey.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Controller;

import com.ultrapower.flowmanage.common.vo.Page;
import com.ultrapower.flowmanage.modules.keyIndexSurvey.service.KeyIndexSurveyService;


/**
 * 陕西移动-互联网关键指标监测
 * @author shangdechou
 *
 */
@Controller(value="keyIndexSurveyController")
@RemotingDestination(channels={"my-amf"})
public class KeyIndexSurveyController {
	private static Logger logger = Logger.getLogger(KeyIndexSurveyController.class);
	@Autowired
	private KeyIndexSurveyService keyIndexSurveyService;
	
	/**
	 * 根据时间查询流量本网率线图
	 * @author shangdechou
	 * @param getTime
	 * @return
	 */
	public List<Map<String, Object>> findFlowNetRate(String getTime){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = keyIndexSurveyService.findFlowNetRate(getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据时间查询流量本网率线图: " + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据时间查询流量本网率明细列表
	 * @author shangdechou
	 * @param pageNo
	 * @param pageSize
	 * @param getTime
	 * @return
	 */
	public Page findFlowNetRateTable(int pageNo, int pageSize, String getTime){
		Page page = new Page();
		try {
			page = keyIndexSurveyService.findFlowNetRateTable(pageNo, pageSize, getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据时间查询流量本网率明细列表: " + page);
			e.printStackTrace(); 
		}
		return page;
	}
	
	/**
	 * 根据时间查询手机点击本网率
	 * @author shangdechou
	 * @param getTime
	 * @return
	 */
	public List<Map<String, Object>> findPhoneHiteRate(String getTime){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = keyIndexSurveyService.findPhoneHiteRate(getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据时间查询手机点击本网率: " + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据时间查询手机点击本网率明细列表
	 * @author shangdechou
	 * @param pageNo
	 * @param pageSize
	 * @param getTime
	 * @return
	 */
	public Page findPhoneHiteRateTable(int pageNo, int pageSize, String getTime){
		Page page = new Page();
		try {
			page = keyIndexSurveyService.findPhoneHiteRateTable(pageNo, pageSize, getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据时间查询手机点击本网率明细列表: " + page);
			e.printStackTrace();
		}
		return page;
	}
	
	/**
	 * 根据时间查询本省内容满足率
	 * @author shangdechou
	 * @param getTime
	 * @return
	 */
	public List<Map<String, Object>> findContentmeetRate(String getTime){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = keyIndexSurveyService.findContentmeetRate(getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据时间查询本省内容满足率: " + list);
			e.printStackTrace();
		}
		return list;
	}
	
	
	/**
	 * 根据时间查询本省内容满足率明细列表
	 * @author shangdechou
	 * @param pageNo
	 * @param pageSize
	 * @param getTime
	 * @return
	 */
	public Page findContentmeetRateTable(int pageNo, int pageSize, String getTime){
		Page page = new Page();
		try {
			page = keyIndexSurveyService.findContentmeetRateTable(pageNo, pageSize, getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据时间查询本省内容满足率明细列表: " + page);
			e.printStackTrace();
		}
		return page;
	}
	
	
	/**
	 * 互联网关键指标监测表格数据Excel导出
	 * @author Yuanxihua
	 * @param getTime
	 * @return
	 */
	public byte[] keyIndexSurveyExcelTable(int pageNo, int pageSize, String getTime){
		byte[] bt = null;
		try {
			bt = keyIndexSurveyService.keyIndexSurveyExcelTable(pageNo, pageSize,getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "互联网质量分析表格数据Excel导出: " + bt);
			e.printStackTrace();
		}
		return bt;
	}
	
}