package com.ultrapower.flowmanage.modules.portVideoBiz.control;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Controller;

import com.ultrapower.flowmanage.modules.portVideoBiz.service.PortVideoBizService;

/**
 * 湖北移动流量管理系统-端到端视频业务诊断视图
 * @author Yuanxihua
 *
 */
@Controller(value="portVideoBizController")
@RemotingDestination(channels={"my-amf"})
public class PortVideoBizController {
	private static Logger logger = Logger.getLogger(PortVideoBizController.class);
	
	@Autowired
	private PortVideoBizService portVideoBizService;
	
	/**
	 * 根据视图类型、时间粒度、时间查询业务、网络质量视图
	 * @author Yuanxihua
	 * @param viewType
	 * @param timeType
	 * @param getTime
	 * @return
	 */
	public List<Map<String, String>> findBizNetQualityViewByTime(String viewType, String timeType, String getTime){
		List<Map<String, String>> list = null;
		try {
			list = portVideoBizService.findBizNetQualityViewByTime(viewType, timeType, getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据视图类型、时间粒度、时间查询业务、网络质量视图：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据视图类型、时间粒度、时间查询业务、网络质量地市比对视图 
	 * @author Yuanxihua
	 * @param viewType
	 * @param timeType
	 * @param getTime
	 * @return
	 */
	public List<Map<String, String>> findBizNetQualityCityContrastViewByTime(String viewType, String timeType, String getTime){
		List<Map<String, String>> list = null;
		try {
			list = portVideoBizService.findBizNetQualityCityContrastViewByTime(viewType, timeType, getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据视图类型、时间粒度、时间查询业务、网络质量地市比对视图 ：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据视图类型、时间粒度、时间、品质度查询业务、网络质量视频网站TOP3排名
	 * @author Yuanxihua
	 * @param viewType
	 * @param timeType
	 * @param getTime
	 * @param sort
	 * @return
	 */
	public List<Map<String, String>> findBizNetQualityVideoWebTop3ByTime(String viewType, String timeType, String getTime, String sort){
		List<Map<String, String>> list = null;
		try {
			list = portVideoBizService.findBizNetQualityVideoWebTop3ByTime(viewType, timeType, getTime, sort);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据视图类型、时间粒度、时间、品质度查询业务、网络质量视频网站TOP3排名 ：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据视图类型、时间粒度、地市ID、时间查询手机用户业务、网络质量微观呈现视图
	 * @author Yuanxihua
	 * @param viewType
	 * @param timeType
	 * @param getTime
	 * @param sort
	 * @return
	 */
	public List<Map<String, String>> findBizNetQCMicroViewByTime(String viewType, String timeType, int cityId, String getTime){
		List<Map<String, String>> list = null;
		try {
			list = portVideoBizService.findBizNetQCMicroViewByTime(viewType, timeType, cityId, getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据视图类型、时间粒度、地市ID、时间查询手机用户业务、网络质量微观呈现视图 ：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据时间粒度、地市ID、运营商、网站ID、时间查询当前(所有)视频网站各类型问题产生次数
	 * @author Yuanxihua
	 * @param timeType
	 * @param cityId
	 * @param operator
	 * @param webId
	 * @param getTime
	 * @return
	 */
	public List<Map<String, String>> findVideoWebProblemNumberByTime(String timeType, String cityId, String operator, String webId, String getTime){
		List<Map<String, String>> list = null;
		try {
			list = portVideoBizService.findVideoWebProblemNumberByTime(timeType, cityId, operator, webId, getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据时间粒度、地市ID、运营商、网站ID、时间查询当前(所有)视频网站各类型问题产生次数：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据时间粒度、时间查询质差网站列表及问题溯源分析
	 * @author Yuanxihua
	 * @param timeType
	 * @param getTime
	 * @return
	 */
	public List<Map<String, String>> findNetPoorQualityByTime(String timeType, String getTime){
		List<Map<String, String>> list = null;
		try {
			list = portVideoBizService.findNetPoorQualityByTime(timeType, getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据时间粒度、时间查询质差网站列表及问题溯源分析：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据时间粒度、时间查询业务质量图形、表格分析
	 * @author Yuanxihua
	 * @param timeType
	 * @param getTime
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> findBizQualityTableByTime(String timeType, String cityId, String operator, String getTime){
		Map<String, Object> map = null;
		try {
			map = portVideoBizService.findBizQualityTableByTime(timeType, cityId, operator, getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据时间粒度、时间查询业务质量图形、表格分析：" + map);
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 根据时间粒度、时间查询网络质量问题统计
	 * @author Yuanxihua
	 * @param timeType
	 * @param getTime
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> findNetQualityProblemCountByTime(String timeType, String getTime){
		Map<String, Object> map = null;
		try {
			map = portVideoBizService.findNetQualityProblemCountByTime(timeType, getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据时间粒度、时间查询网络质量问题统计：" + map);
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 根据时间粒度、城市ID、网站ID、时间查询网络质量微观呈现
	 * @author Yuanxihua
	 * @param timeType
	 * @param cityId
	 * @param webId
	 * @param getTime
	 * @return
	 */
	public List<Map<String, String>> findNetQualityMicroByTime(String timeType, String cityId, String operator, String webId, String score, String getTime){
		List<Map<String, String>> list = null;
		try {
			list = portVideoBizService.findNetQualityMicroByTime(timeType, cityId, operator, webId, score, getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据时间粒度、城市ID、网站ID、时间查询网络质量微观呈现：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据视图类型、时间粒度、城市ID、运营商、时间查询网站质量差视频网站(网络侧问题分析Label)
	 * @author Yuanxihua
	 * @param viewType
	 * @param timeType
	 * @param cityId
	 * @param operator
	 * @param getTime
	 * @return
	 */
	public List<Map<String, String>> findNetQualityPoorVideoByTime(String viewType, String timeType, String cityId, String operator, String getTime){
		List<Map<String, String>> list = null;
		try {
			list = portVideoBizService.findNetQualityPoorVideoByTime(viewType, timeType, cityId, operator, getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据时间粒度、城市ID、网站ID、时间查询网络质量微观呈现：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据时间粒度、城市ID、网站ID、运营商、时间查询局向质量分析(拓扑)
	 * @author Yuanxihua
	 * @param timeType
	 * @param cityId
	 * @param webId
	 * @param operator
	 * @param getTime
	 * @return
	 */
	public String findPartDirectionQualityTopo(String timeType, String cityId, String webId, String operator, String getTime, String layout){
		String xml = null;
		try {
			xml = portVideoBizService.findPartDirectionQualityTopo(timeType, cityId, webId, operator, getTime,layout);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据时间粒度、城市ID、网站ID、运营商、时间查询局向质量分析(拓扑)：" + xml);
			e.printStackTrace();
		}
		return xml;
	}
}
