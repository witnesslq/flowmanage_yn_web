package com.ultrapower.flowmanage.modules.internetResources.control;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Controller;

import com.ultrapower.flowmanage.common.vo.Page;
import com.ultrapower.flowmanage.modules.internetResources.service.InternetResourcesService;

/**
 * 湖北移动流量管理系统-互联网资源库
 * @author Yuanxihua
 *
 */
@Controller(value="internetResourcesController")
@RemotingDestination(channels={"my-amf"})
public class InternetResourcesController {
	private static Logger logger = Logger.getLogger(InternetResourcesController.class);
	
	@Autowired
	private InternetResourcesService internetResourcesService;
	
	
	/**
	 * 查询TOP10网站域名总数统计
	 * @author Yuanxihua 
	 * @return
	 */
	public Map<String, Object> findTop10WebDomainCount(){
		Map<String, Object> map = null;
		try {
			map = internetResourcesService.findTop10WebDomainCount();
		} catch (Exception e) {
			logger.error(e.getMessage() + "查询TOP10网站域名总数统计：  " + map);
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 查询其他网站域名总数统计
	 * @author Yuanxihua 
	 * @return
	 */
	public List<Map<String, String>> findOtherWebDomainCount(){
		List<Map<String, String>> list = null;
		try {
			list = internetResourcesService.findOtherWebDomainCount();
		} catch (Exception e) {
			logger.error(e.getMessage() + "查询其他网站域名总数统计：  " + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据网站名称查询运营商分布统计情况
	 * @author Yuanxihua
	 * @param webName
	 * @return
	 */
	public Map<String, Object> findOperatorDistributionCountBywebName(String webName){
		Map<String, Object> map = null;
		try {
			map = internetResourcesService.findOperatorDistributionCountBywebName(webName);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据网站名称查询运营商分布统计情况：  " + map);
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 根据网站名称和运营商查询区域分布统计情况
	 * @author Yuanxihua
	 * @param webName
	 * @param operator
	 * @return
	 */
	public Map<String, Object> findDistrictDistributionBywebNameOperator(String webName, String operator){
		Map<String, Object> map = null;
		try {
			map = internetResourcesService.findDistrictDistributionBywebNameOperator(webName, operator);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据网站名称查询区域分布统计情况：  " + map);
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 根据网站名称和运营商查询运营商维度明细统计列表
	 * @author Yuanxihua
	 * @param webName
	 * @param operator
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page findOperatorDetailBywebNameOperatorTable(String webName, String operator, int pageNo, int pageSize){
		Page page = null;
		try {
			page = internetResourcesService.findOperatorDetailBywebNameOperatorTable(webName, operator, pageNo, pageSize);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据网站名称和运营商查询运营商维度明细统计列表：  " + page);
			e.printStackTrace();
		}
		return page;
	}
	
	/**
	 * 根据网站名称和运营商、区域查询区域维度明细统计列表
	 * @author Yuanxihua
	 * @param webName
	 * @param operator
	 * @param area
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page findDistrictwebNameOperatorAreaTable(String webName, String operator, String area, int pageNo, int pageSize) {
		Page page = null;
		try {
			page = internetResourcesService.findDistrictwebNameOperatorAreaTable(webName, operator, area, pageNo, pageSize);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据网站名称和运营商、区域查询区域维度明细统计列表：  " + page);
			e.printStackTrace();
		}
		return page;
	}
	
	/**
	 * 根据网站名称、域名、IP地址、归属区域查询域名库列表
	 * @author Yuanxihua
	 * @param webName
	 * @param domainName
	 * @param ip
	 * @param district
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page findDomainLibraryTable(String webName, String domainName, String ip, String district, int pageNo, int pageSize) {
		Page page = null;
		try {
			page = internetResourcesService.findDomainLibraryTable(webName, domainName, ip, district, pageNo, pageSize);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据网站名称、域名、IP地址、归属区域查询域名库列表：  " + page);
			e.printStackTrace();
		}
		return page;
	}
	
	
	/**
	 * 根据网站名称、域名、IP地址、归属区域导出Excel表格
	 * @author Yuanxihua
	 * @param webName
	 * @param domainName
	 * @param ip
	 * @param district
	 * @return
	 */
	public byte[] exportDomainLibraryExcelTable(String webName, String domainName, String ip, String district){
		byte[] bt = null;
		try {
			bt = internetResourcesService.exportDomainLibraryExcelTable(webName, domainName, ip, district);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据网站名称、域名、IP地址、归属区域导出Excel表格：  " + bt);
			e.printStackTrace();
		}
		return bt;
	}
	

	/**
	 * 上传资源库文件
	 * @author Zouhaibo
	 * @param data  数据byte流
	 * @param type  业务类型。1：全量域名导入；2：增量域名导入；3：全量知识百科导入；4：增量知识百科导入
	 * @return
	 */
	public boolean upload(byte[] data, String type, String filesuffix){
		
		System.out.println("type: " + type);
		System.out.println("data: " + data);
		System.out.println("filesuffix: " + filesuffix);
		boolean flag = false;
		try {
			flag = internetResourcesService.upload(data, type, filesuffix);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("上传资源库文件错误：" + e.getMessage());
			e.printStackTrace();
		}
		
		return flag;
	}
	
	/**
	 * 查询业务总体统计
	 * @author Yuanxihua
	 * @return
	 */
	public Map<String, Object> findBusinessTotalCount(){
		Map<String, Object> map = null;
		try {
			map = internetResourcesService.findBusinessTotalCount();
		} catch (Exception e) {
			logger.error(e.getMessage() + "查询业务总体统计：  " + map);
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 根据业务大类名称查询业务明细统计
	 * @author Yuanxihua
	 * @param bizName
	 * @return
	 */
	public Map<String, Object> findBusinessDetailsCountByBizName(String bizName){
		Map<String, Object> map = null;
		try {
			map = internetResourcesService.findBusinessDetailsCountByBizName(bizName);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据业务大类名称查询业务明细统计：  " + map);
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 根据业务ID查询业务详细信息
	 * @author Yuanxihua
	 * @param kid
	 * @return
	 */
	public Map<String, Object> findBusinessDetailsInfoByKid(String kid){
		Map<String, Object> map = null;
		try {
			map = internetResourcesService.findBusinessDetailsInfoByKid(kid);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据业务ID查询业务详细信息：  " + map);
			e.printStackTrace();
		}
		return map;
	}
}
