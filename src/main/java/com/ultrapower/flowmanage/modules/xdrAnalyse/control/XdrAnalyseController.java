package com.ultrapower.flowmanage.modules.xdrAnalyse.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Controller;

import com.ultrapower.flowmanage.common.vo.Page;
import com.ultrapower.flowmanage.modules.xdrAnalyse.service.XdrAnalyseService;

/**
 * 湖北移动流量管理系统-XDR分析视图
 * @author Yuanxihua
 *
 */
@Controller(value="xdrAnalyseController")
@RemotingDestination(channels={"my-amf"})
public class XdrAnalyseController {
	private static Logger logger = Logger.getLogger(XdrAnalyseController.class);
	
	@Autowired
	private XdrAnalyseService xdrAnalyseService;
	
	/**
	 * 根据类型、上级ID查询ID和名称
	 * @author Yuanxihua
	 * @param objectType
	 * @param parentId
	 * @return
	 */
	public List<Map<String, String>> findDICInfo(String objectType, String parentId){
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			list = xdrAnalyseService.findDICInfo(objectType, parentId);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据类型、上级ID查询ID和名称：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据区县名称查询网格编号
	 * @author Yuanxihua
	 * @param district
	 * @return
	 */
	public List<Map<String, String>> findWGNumberBydistrict(String district){
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			list = xdrAnalyseService.findWGNumberBydistrict(district);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据区县名称查询网格编号：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据时间、用户群ID、业务类型分页ID查询用户点击量信息列表
	 * @author Yuanxihua
	 * @param userGroupId
	 * @param busiTypeId
	 * @param time
	 * @return
	 */
	public Page findUserClickRateInfoTable(String userGroupId, String busiTypeId, String time, int pageNo, int pageSize){
		Page page = null;
		try {
			page = xdrAnalyseService.findUserClickRateInfoTable(userGroupId, busiTypeId, time, pageNo, pageSize);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据时间、用户群ID、业务类型ID分页查询用户点击量信息列表：" + page);
			e.printStackTrace();
		}
		return page;
	}
	
	/**
	 * 根据时间段、地市ID、城市ID、网格编号、用户群ID、业务类型ID查询点击率最高TOP15网站
	 * @author Yuanxihua
	 * @param cityId
	 * @param countyId
	 * @param gridNumber
	 * @param userGroupId
	 * @param busiTypeId
	 * @param time
	 * @return
	 */
	public List<Map<String, String>> findUserClickRateWebTOP15(String cityId, String countyId, String gridNumber, String userGroupId, String busiTypeId, String time){
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			list = xdrAnalyseService.findUserClickRateWebTOP15(cityId, countyId, gridNumber, userGroupId, busiTypeId, time);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据时间段、地市ID、城市ID、网格编号、用户群ID、业务类型ID查询点击率最高TOP15网站：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据时间、用户群ID、业务类型ID查询访问地市质量分析
	 * @author Yuanxihua
	 * @param userGroupId
	 * @param busiTypeId
	 * @param time
	 * @return
	 */
	public List<Map<String, String>> findVisitQualityByCity(String userGroupId, String busiTypeId, String time){
		List<Map<String, String>> list = null;
		try {
			list = xdrAnalyseService.findVisitQualityByCity(userGroupId, busiTypeId, time);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据时间、用户群ID、业务类型ID查询访问地市质量分析：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据时间、用户群ID、业务类型ID查询访问区县质量分析
	 * @author Yuanxihua
	 * @param userGroupId
	 * @param busiTypeId
	 * @param cityId
	 * @param time
	 * @return
	 */
	public List<Map<String, String>> findVisitQualityByCounty(String userGroupId, String busiTypeId, String cityId, String time){
		List<Map<String, String>> list = null;
		try {
			list = xdrAnalyseService.findVisitQualityByCounty(userGroupId, busiTypeId, cityId, time);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据时间、用户群ID、业务类型ID查询访问区县质量分析：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据时间、用户群ID、业务类型ID分页查询网站质量分析列表
	 * @author Yuanxihua
	 * @param userGroupId
	 * @param busiTypeId
	 * @param time
	 * @return
	 */
	public Page findSitesQualityTable(String userGroupId, String busiTypeId, String time, int pageNo, int pageSize){
		Page page = null;
		try {
			page = xdrAnalyseService.findSitesQualityTable(userGroupId, busiTypeId, time, pageNo, pageSize);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据时间、用户群ID、业务类型ID分页查询网站质量分析列表：" + page);
			e.printStackTrace();
		}
		return page;
	}
	
	/**
	 * 根据时间、用户群ID、业务类型ID分页查询网站质量分析列表
	 * @author Yuanxihua
	 * @param userGroupId
	 * @param busiTypeId
	 * @param time
	 * @return
	 */
	public Page findSitesQualityWarningTable(String userGroupId, String busiTypeId, String time, int pageNo, int pageSize){
		Page page = null;
		try {
			page = xdrAnalyseService.findSitesQualityWarningTable(userGroupId, busiTypeId, time, pageNo, pageSize);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据时间、用户群ID、业务类型ID分页查询网站质量分析列表：" + page);
			e.printStackTrace();
		}
		return page;
	}
		
}
