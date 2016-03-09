package com.ultrapower.flowmanage.modules.busiBenchmark.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Controller;

import com.ultrapower.flowmanage.modules.busiBenchmark.service.BusiBenchmarkService;

/**
 * 湖北移动流量管理系统-业务基准线分析
 * @author Yuanxihua
 *
 */
@Controller(value="busiBenchmarkController")
@RemotingDestination(channels={"my-amf"})
public class BusiBenchmarkController {
	private static Logger logger = Logger.getLogger(BusiBenchmarkController.class);
	
	@Autowired
	private BusiBenchmarkService busiBenchmarkService;
	
	/**
	 * 根据业务类型查询字典表信息
	 * @param busiType
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> findBusinessInfoByType(String busiType, String busiId){
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		try {
			list = busiBenchmarkService.findBusinessInfoByType(busiType, busiId);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据业务类型查询字典表信息：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据业务ID和时间查询关注网站
	 * 重点关注网站:两个及两个以上指标越线的网站
	 * 需关注网站:一个指标越线的网站 
	 * @param busiId
	 * @param getTime
	 * @param timeType
	 * @param overLineWebType
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> findEmphasisNeedConcernWebInfo(String busiId, String getTime, String timeType, int overLineWebType) {
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		try {
			list = busiBenchmarkService.findEmphasisNeedConcernWebInfo(busiId, getTime, timeType, overLineWebType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据业务ID和时间查询关注网站：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据业务ID、网站名称、时间查询30天网站各指标波动情况
	 * @param busiId
	 * @param webName
	 * @param getDay
	 * @param beforeDay
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findWebKpifloatDay30(String busiId, String webName, String getTime){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = busiBenchmarkService.findWebKpifloatDay30(busiId, webName, getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据业务ID、网站名称、时间查询30天网站各指标波动情况：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据业务ID、时间、浮动时间粒度(单日、7天、30天浮动)查询需关注网站各项指标的正向、逆向越线TOP3网站
	 * @param busiId
	 * @param getDay
	 * @param nextDay
	 * @param timeType
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findNeedfocusWebKpiPasslineTOP3(String busiId, String getTime, String timeType){
		List<Map<String, Object>> list = null;
		try {
			list = busiBenchmarkService.findNeedfocusWebKpiPasslineTOP3(busiId, getTime, timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据业务ID、时间、浮动时间粒度(单日、7天、30天浮动)查询需关注网站各项指标的正向、逆向越线TOP3网站：" + list);
			e.printStackTrace();
		}
		return list;
	}
}
