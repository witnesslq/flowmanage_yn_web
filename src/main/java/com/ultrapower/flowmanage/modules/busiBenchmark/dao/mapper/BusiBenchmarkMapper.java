package com.ultrapower.flowmanage.modules.busiBenchmark.dao.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface BusiBenchmarkMapper {
	
	/**
	 * 根据业务类型查询字典表信息
	 * @param busiType
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> findBusinessInfoByType(String busiType, String busiId) throws Exception;
	
	
	/**
	 * 根据业务ID和时间查询关注网站
	 * 重点关注网站:两个及两个以上指标越线的网站
	 * 需关注网站:一个指标越线的网站
	 * @param busiId
	 * @param getDay
	 * @param nextDay
	 * @param timeType
	 * @param overLineWebType
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String >> findEmphasisNeedConcernWebInfo(@Param("busiId")String busiId, @Param("getDay")Date getDay, @Param("nextDay")Date nextDay, @Param("timeType")String timeType, @Param("overLineWebType")int overLineWebType) throws Exception;

	/**
	 * 查询业务基准线阀值
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> findBusiBenchmarkThreshold(String busiId) throws Exception;
	
	/**
	 * 根据业务ID、网站名称、时间查询30天网站各指标波动情况
	 * @param busiId
	 * @param webName
	 * @param getDay
	 * @param beforeDay
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> findWebKpifloatDay30(@Param("busiId")String busiId, @Param("webName")String webName, @Param("getDay")Date getDay, @Param("beforeDay")Date beforeDay) throws Exception;

	/**
	 * 根据业务ID、指标名称、时间、浮动时间粒度(单日、7天、30天浮动)查询需关注网站各项指标的正向、逆向越线TOP3网站
	 * @param busiId
	 * @param getDay
	 * @param nextDay
	 * @param timeType
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> findNeedfocusWebKpiPasslineTOP3(@Param("busiId")String busiId, @Param("getDay")Date getDay, @Param("nextDay")Date nextDay, @Param("timeType")String timeType) throws Exception;

}
