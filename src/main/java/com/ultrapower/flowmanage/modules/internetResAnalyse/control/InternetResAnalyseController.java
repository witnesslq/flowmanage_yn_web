package com.ultrapower.flowmanage.modules.internetResAnalyse.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Controller;

import com.ultrapower.flowmanage.common.vo.Page;
import com.ultrapower.flowmanage.modules.internetResAnalyse.service.InternetResAnalyseService;


/**
 * 陕西移动-互联网资源分析
 * @author shangdechou
 *
 */
@Controller(value="internetResAnalyseController")
@RemotingDestination(channels={"my-amf"})
public class InternetResAnalyseController {
	private static Logger logger = Logger.getLogger(InternetResAnalyseController.class);
	@Autowired
	private InternetResAnalyseService internetResAnalyseService;
	
	/**
	 * 根据网站类型查询资源数量及大小分析Top10网站名称
	 * @author shangdechou
	 * @param webType
	 * @param webName
	 * @param operator
	 * @param getTime
	 * @return
	 */
	public List<Map<String, Object>> findTop10WebName(String webType, String sourceType, String getTime){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = internetResAnalyseService.findTop10WebName(webType, sourceType, getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据网站类型查询资源数量及大小分析Top10网站名称: " + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据网站类型、网站名称查询各运营商资源数量及大小(饼图)
	 * @author shangdechou
	 * @param webType
	 * @param webName
	 * @param operator
	 * @param getTime
	 * @return
	 */
	public List<Map<String, Object>> findSourceAnalyse(String webType, String webName, String sourceType, String getTime){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = internetResAnalyseService.findSourceAnalyse(webType, webName, sourceType, getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据网站类型、网站名称查询各运营商资源数量及大小(饼图): " + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据网站类型和网站名称查询各归属地移动资源大小及数量排名
	 * @author shangdechou
	 * @param webType
	 * @param webName
	 * @param getTime
	 * @return
	 */
	public List<Map<String, String>> findMobileSourceRank(String webType, String webName, String sourceType, String getTime){
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		try {
			list = internetResAnalyseService.findMobileSourceRank(webType, webName, sourceType, getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据网站类型和网站名称查询各归属地移动资源大小及数量排名: " + list);
			e.printStackTrace();
		}
		return list;
	}
	
	
	/**
	 * 根据网站类型、网站名称分页查询资源明细列表
	 * @author shangdechou
	 * @param pageNo
	 * @param pageSize
	 * @param webType
	 * @param getTime
	 * @return
	 */
	public Page findResDetailTable(int pageNo, int pageSize, String webType, String webName, String getTime){
		Page page = new Page();
		try {
			page = internetResAnalyseService.findResDetailTable(pageNo, pageSize, webType, webName, getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据网站类型、网站名称分页查询资源明细列表: " + page);
			e.printStackTrace();
		}
		return page;
	}
	
	/**
	 * 互联网资源分析表格数据Excel导出
	 * @author shangdechou
	 * @param getTime
	 * @return
	 */
	public byte[] qualityExcelTable(String webType,String getTime){
		byte[] bt = null;
		try {
			bt = internetResAnalyseService.qualityExcelTable(webType,getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "互联网质量分析表格数据Excel导出: " + bt);
			e.printStackTrace();
		}
		return bt;
	}
	
}