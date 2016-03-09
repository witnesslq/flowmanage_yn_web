package com.ultrapower.flowmanage.modules.portVideoBiz.service;

import java.util.List;
import java.util.Map;

public interface PortVideoBizService {
	/**
	 * 根据视图类型、时间粒度、时间查询业务、网络质量视图
	 * @author Yuanxihua
	 * @param viewType
	 * @param timeType
	 * @param getTime
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> findBizNetQualityViewByTime(String viewType, String timeType, String getTime) throws Exception;
	
	/**
	 * 根据视图类型、时间粒度、时间查询业务、网络质量地市比对视图
	 * @author Yuanxihua
	 * @param viewType
	 * @param timeType
	 * @param getTime
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> findBizNetQualityCityContrastViewByTime(String viewType, String timeType, String getTime) throws Exception;
	
	/**
	 * 根据视图类型、时间粒度、时间、品质度查询业务、网络质量视频网站TOP3排名
	 * @author Yuanxihua
	 * @param viewType
	 * @param timeType
	 * @param getTime
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> findBizNetQualityVideoWebTop3ByTime(String viewType, String timeType, String getTime, String sort) throws Exception;
	
	/**
	 * 根据视图类型、时间粒度、地市ID、时间查询手机用户业务、网络质量微观呈现视图
	 * @author Yuanxihua
	 * @param viewType
	 * @param timeType
	 * @param cityId
	 * @param getTime
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> findBizNetQCMicroViewByTime(String viewType, String timeType, int cityId, String getTime) throws Exception;
	
	/**
	 * 根据时间粒度、地市ID、运营商、网站ID、时间查询当前(所有)视频网站各类型问题产生次数
	 * @author Yuanxihua
	 * @param timeType
	 * @param cityId
	 * @param operator
	 * @param getTime
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> findVideoWebProblemNumberByTime(String timeType, String cityId, String operator, String webId, String getTime) throws Exception;
	
	/**
	 * 根据时间粒度、时间查询质差网站列表及问题溯源分析
	 * @author Yuanxihua
	 * @param timeType
	 * @param getTime
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> findNetPoorQualityByTime(String timeType, String getTime) throws Exception;
	
	/**
	 * 根据时间粒度、时间查询业务质量图形、表格分析
	 * @author Yuanxihua
	 * @param timeType
	 * @param getTime
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> findBizQualityTableByTime(String timeType, String cityId, String operator, String getTime) throws Exception;
	
	/**
	 * 根据时间粒度、时间查询网络质量问题统计
	 * @author Yuanxihua
	 * @param timeType
	 * @param getTime
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> findNetQualityProblemCountByTime(String timeType, String getTime) throws Exception;
	
	/**
	 * 根据时间粒度、城市ID、网站ID、时间查询网络质量微观呈现
	 * @author Yuanxihua
	 * @param timeType
	 * @param cityId
	 * @param webId
	 * @param getTime
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> findNetQualityMicroByTime(String timeType, String cityId, String operator, String webId, String score, String getTime) throws Exception;
	
	/**
	 * 根据视图类型、时间粒度、城市ID、运营商、时间查询网站质量差视频网站(网络侧问题分析Label)
	 * @author Yuanxihua
	 * @param viewType
	 * @param timeType
	 * @param cityId
	 * @param operator
	 * @param getTime
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> findNetQualityPoorVideoByTime(String viewType, String timeType, String cityId, String operator, String getTime) throws Exception;
	
	/**
	 * 根据时间粒度、城市ID、网站ID、运营商、时间查询局向质量分析(拓扑)
	 * @author Yuanxihua
	 * @param timeType
	 * @param cityId
	 * @param webId
	 * @param operator
	 * @param getTime
	 * @return
	 * @throws Exception
	 */
	String findPartDirectionQualityTopo(String timeType, String cityId, String webId, String operator, String getTime, String layout) throws Exception;
}
