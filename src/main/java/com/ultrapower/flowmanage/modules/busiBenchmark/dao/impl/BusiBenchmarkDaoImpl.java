package com.ultrapower.flowmanage.modules.busiBenchmark.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ultrapower.flowmanage.modules.busiBenchmark.dao.BusiBenchmarkDao;
import com.ultrapower.flowmanage.modules.busiBenchmark.dao.mapper.BusiBenchmarkMapper;

@Service("busiBenchmarkDao")
public class BusiBenchmarkDaoImpl implements BusiBenchmarkDao {
	private static Logger logger = Logger.getLogger(BusiBenchmarkDaoImpl.class);
	@Resource(name="busiBenchmarkMapper")
	private BusiBenchmarkMapper busiBenchmarkMapper;
	
	/**
	 * 根据业务类型查询字典表信息
	 * @param busiType
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> findBusinessInfoByType(String busiType, String busiId) throws Exception{
		return busiBenchmarkMapper.findBusinessInfoByType(busiType, busiId);
	}
	
	
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
	public List<Map<String, String>> findEmphasisNeedConcernWebInfo(String busiId, Date getDay, Date nextDay, String timeType, int overLineWebType) throws Exception {
		return busiBenchmarkMapper.findEmphasisNeedConcernWebInfo(busiId, getDay, nextDay, timeType, overLineWebType);
	}


	/**
	 * 查询业务基准线阀值
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> findBusiBenchmarkThreshold(String busiId) throws Exception{
		return busiBenchmarkMapper.findBusiBenchmarkThreshold(busiId);
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
	public List<Map<String, String>> findWebKpifloatDay30(@Param("busiId")String busiId, @Param("webName")String webName, @Param("getDay")Date getDay, @Param("beforeDay")Date beforeDay) throws Exception{
		return busiBenchmarkMapper.findWebKpifloatDay30(busiId, webName, getDay, beforeDay);
	}

	/**
	 * 根据业务ID、指标名称、时间、浮动时间粒度(单日、7天、30天浮动)查询需关注网站各项指标的正向、逆向越线TOP3网站
	 * @param busiId
	 * @param getDay
	 * @param nextDay
	 * @param timeType
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> findNeedfocusWebKpiPasslineTOP3(String busiId, Date getDay, Date nextDay, String timeType) throws Exception{
		return busiBenchmarkMapper.findNeedfocusWebKpiPasslineTOP3(busiId, getDay, nextDay, timeType);
	}

}
