package com.ultrapower.flowmanage.modules.hotspotResource.dao.impl;


import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ultrapower.flowmanage.modules.flow.vo.FsChartVo;
import com.ultrapower.flowmanage.modules.hotspotResource.dao.HotspotResourceDao;
import com.ultrapower.flowmanage.modules.hotspotResource.dao.mapper.HotspotResourceMapper;
@Service("hotspotResourceDao")
public class HotspotResourceDaoImpl implements HotspotResourceDao {
	private static Logger logger = Logger.getLogger(HotspotResourceDaoImpl.class);
	@Resource(name="hotspotResourceMapper")
	private HotspotResourceMapper hotspotResourceMapper;
	
	public List<FsChartVo> findExitNetVideoFlowRank(String videoType,String exitType, String flowType, Date getMonthFirstDay,Date nextMonthFirstDay) throws Exception {
		return hotspotResourceMapper.findExitNetVideoFlowRank(videoType, exitType, flowType, getMonthFirstDay, nextMonthFirstDay);
	}
	
	public List<Map<String, Object>> findHotspotVideoExitByVideoExitType(String videoType, String exitType, Date getMonthFirstDay,Date nextMonthFirstDay) throws Exception {
		return hotspotResourceMapper.findHotspotVideoExitByVideoExitType(videoType, exitType, getMonthFirstDay, nextMonthFirstDay);
	}

	public int findIDCResRowCount(String resType, Date getMonthFirstDay,
			Date nextMonthFirstDay) throws Exception {
		return hotspotResourceMapper.findIDCResRowCount(resType, getMonthFirstDay, nextMonthFirstDay);
	}

	public List<Map<String, Object>> findIDCResTable(String resType,
			Date getMonthFirstDay, Date nextMonthFirstDay, int pageNo,
			int pageSize) throws Exception {
		return hotspotResourceMapper.findIDCResTable(resType, getMonthFirstDay, nextMonthFirstDay, pageNo, pageSize);
	}

	public List<Map<String, Object>> findHotspotWebTable(Date getMonthFirstDay,
			Date nextMonthFirstDay) throws Exception {
		return hotspotResourceMapper.findHotspotWebTable(getMonthFirstDay, nextMonthFirstDay);
	}

	public List<Map<String, Object>> findHotspotP2PTable(Date getMonthFirstDay,
			Date nextMonthFirstDay) throws Exception {
		return hotspotResourceMapper.findHotspotP2PTable(getMonthFirstDay, nextMonthFirstDay);
	}

	public int findHotspotFlow50RowCount(Date getMonthFirstDay,
			Date nextMonthFirstDay) throws Exception {
		return hotspotResourceMapper.findHotspotFlow50RowCount(getMonthFirstDay, nextMonthFirstDay);
	}
	
	

	public List<Map<String, Object>> findHotspotFlow50Table(
			Date getMonthFirstDay, Date nextMonthFirstDay, int pageNo,
			int pageSize) throws Exception {
		return hotspotResourceMapper.findHotspotFlow50Table(getMonthFirstDay, nextMonthFirstDay, pageNo, pageSize);
	}
	
	

	public int findHotspotAcc50RowCount(Date getMonthFirstDay,
			Date nextMonthFirstDay) throws Exception {
		return hotspotResourceMapper.findHotspotAcc50RowCount(getMonthFirstDay, nextMonthFirstDay);
	}

	public List<Map<String, Object>> findHotspotAcc50Table(
			Date getMonthFirstDay, Date nextMonthFirstDay, int pageNo,
			int pageSize) throws Exception {
		return hotspotResourceMapper.findHotspotAcc50Table(getMonthFirstDay, nextMonthFirstDay, pageNo, pageSize);
	}

	public int findHotspotPer70RowCount(Date getMonthFirstDay,
			Date nextMonthFirstDay) throws Exception {
		return hotspotResourceMapper.findHotspotPer70RowCount(getMonthFirstDay, nextMonthFirstDay);
	}

	public List<Map<String, Object>> findHotspotPer70Table(
			Date getMonthFirstDay, Date nextMonthFirstDay, int pageNo,
			int pageSize) throws Exception {
		return hotspotResourceMapper.findHotspotPer70Table(getMonthFirstDay, nextMonthFirstDay, pageNo, pageSize);
	}

	public int findHotspotDNS1000RowCount(Date getMonthFirstDay,
			Date nextMonthFirstDay) throws Exception {
		return hotspotResourceMapper.findHotspotDNS1000RowCount(getMonthFirstDay, nextMonthFirstDay);
	}

	public List<Map<String, Object>> findHotspotDNS1000Table(
			Date getMonthFirstDay, Date nextMonthFirstDay, int pageNo,
			int pageSize) throws Exception {
		return hotspotResourceMapper.findHotspotDNS1000Table(getMonthFirstDay, nextMonthFirstDay, pageNo, pageSize);
	}

	public int findHotspotDNSNOCMRowCount(Date getMonthFirstDay,
			Date nextMonthFirstDay) throws Exception {
		return hotspotResourceMapper.findHotspotDNSNOCMRowCount(getMonthFirstDay, nextMonthFirstDay);
	}

	public List<Map<String, Object>> findHotspotDNSNOCMTable(
			Date getMonthFirstDay, Date nextMonthFirstDay, int pageNo,
			int pageSize) throws Exception {
		return hotspotResourceMapper.findHotspotDNSNOCMTable(getMonthFirstDay, nextMonthFirstDay, pageNo, pageSize);
	}

	public int findHotspotResRowCount(Date getMonthFirstDay,
			Date nextMonthFirstDay) throws Exception {
		return hotspotResourceMapper.findHotspotResRowCount(getMonthFirstDay, nextMonthFirstDay);
	}

	public List<Map<String, Object>> findHotspotResTable(Date getMonthFirstDay,
			Date nextMonthFirstDay, int pageNo, int pageSize) throws Exception {
		return hotspotResourceMapper.findHotspotResTable(getMonthFirstDay, nextMonthFirstDay, pageNo, pageSize);
	}

	public int findUserClickhotspotDomainsRowCount(Date getMonthFirstDay,
			Date nextMonthFirstDay) throws Exception {
		return hotspotResourceMapper.findUserClickhotspotDomainsRowCount(getMonthFirstDay, nextMonthFirstDay);
	}

	public List<Map<String, Object>> findUserClickhotspotDomainsTable(
			Date getMonthFirstDay, Date nextMonthFirstDay, int pageNo,
			int pageSize) throws Exception {
		return hotspotResourceMapper.findUserClickhotspotDomainsTable(getMonthFirstDay, nextMonthFirstDay, pageNo, pageSize);
	}

	
	
	
	//新增天粒度
	
	
	public List<FsChartVo> findExitNetVideoFlowRankOfDay(String videoType,String exitType, String flowType, Date getMonthFirstDay,Date nextMonthFirstDay) throws Exception {
		return hotspotResourceMapper.findExitNetVideoFlowRankOfDay(videoType, exitType, flowType, getMonthFirstDay, nextMonthFirstDay);
	}
	
	public List<Map<String, Object>> findHotspotVideoExitByVideoExitTypeOfDay(String videoType, String exitType, Date getMonthFirstDay,Date nextMonthFirstDay) throws Exception {
		return hotspotResourceMapper.findHotspotVideoExitByVideoExitTypeOfDay(videoType, exitType, getMonthFirstDay, nextMonthFirstDay);
	}

	public int findIDCResRowCountOfDay(String resType, Date getMonthFirstDay,
			Date nextMonthFirstDay) throws Exception {
		return hotspotResourceMapper.findIDCResRowCountOfDay(resType, getMonthFirstDay, nextMonthFirstDay);
	}

	public List<Map<String, Object>> findIDCResTableOfDay(String resType,
			Date getMonthFirstDay, Date nextMonthFirstDay, int pageNo,
			int pageSize) throws Exception {
		return hotspotResourceMapper.findIDCResTableOfDay(resType, getMonthFirstDay, nextMonthFirstDay, pageNo, pageSize);
	}

	public List<Map<String, Object>> findHotspotWebTableOfDay(Date getMonthFirstDay,
			Date nextMonthFirstDay) throws Exception {
		return hotspotResourceMapper.findHotspotWebTableOfDay(getMonthFirstDay, nextMonthFirstDay);
	}

	public List<Map<String, Object>> findHotspotP2PTableOfDay(Date getMonthFirstDay,
			Date nextMonthFirstDay) throws Exception {
		return hotspotResourceMapper.findHotspotP2PTableOfDay(getMonthFirstDay, nextMonthFirstDay);
	}

	public int findHotspotFlow50RowCountOfDay(Date getMonthFirstDay,
			Date nextMonthFirstDay) throws Exception {
		return hotspotResourceMapper.findHotspotFlow50RowCountOfDay(getMonthFirstDay, nextMonthFirstDay);
	}
	
	

	public List<Map<String, Object>> findHotspotFlow50TableOfDay(
			Date getMonthFirstDay, Date nextMonthFirstDay, int pageNo,
			int pageSize) throws Exception {
		return hotspotResourceMapper.findHotspotFlow50TableOfDay(getMonthFirstDay, nextMonthFirstDay, pageNo, pageSize);
	}
	
	

	public int findHotspotAcc50RowCountOfDay(Date getMonthFirstDay,
			Date nextMonthFirstDay) throws Exception {
		return hotspotResourceMapper.findHotspotAcc50RowCountOfDay(getMonthFirstDay, nextMonthFirstDay);
	}

	public List<Map<String, Object>> findHotspotAcc50TableOfDay(
			Date getMonthFirstDay, Date nextMonthFirstDay, int pageNo,
			int pageSize) throws Exception {
		return hotspotResourceMapper.findHotspotAcc50TableOfDay(getMonthFirstDay, nextMonthFirstDay, pageNo, pageSize);
	}

	public int findHotspotPer70RowCountOfDay(Date getMonthFirstDay,
			Date nextMonthFirstDay) throws Exception {
		return hotspotResourceMapper.findHotspotPer70RowCountOfDay(getMonthFirstDay, nextMonthFirstDay);
	}

	public List<Map<String, Object>> findHotspotPer70TableOfDay(
			Date getMonthFirstDay, Date nextMonthFirstDay, int pageNo,
			int pageSize) throws Exception {
		return hotspotResourceMapper.findHotspotPer70TableOfDay(getMonthFirstDay, nextMonthFirstDay, pageNo, pageSize);
	}

	public int findHotspotDNS1000RowCountOfDay(Date getMonthFirstDay,
			Date nextMonthFirstDay) throws Exception {
		return hotspotResourceMapper.findHotspotDNS1000RowCountOfDay(getMonthFirstDay, nextMonthFirstDay);
	}

	public List<Map<String, Object>> findHotspotDNS1000TableOfDay(
			Date getMonthFirstDay, Date nextMonthFirstDay, int pageNo,
			int pageSize) throws Exception {
		return hotspotResourceMapper.findHotspotDNS1000TableOfDay(getMonthFirstDay, nextMonthFirstDay, pageNo, pageSize);
	}

	public int findHotspotDNSNOCMRowCountOfDay(Date getMonthFirstDay,
			Date nextMonthFirstDay) throws Exception {
		return hotspotResourceMapper.findHotspotDNSNOCMRowCountOfDay(getMonthFirstDay, nextMonthFirstDay);
	}

	public List<Map<String, Object>> findHotspotDNSNOCMTableOfDay(
			Date getMonthFirstDay, Date nextMonthFirstDay, int pageNo,
			int pageSize) throws Exception {
		return hotspotResourceMapper.findHotspotDNSNOCMTableOfDay(getMonthFirstDay, nextMonthFirstDay, pageNo, pageSize);
	}

	public int findHotspotResRowCountOfDay(Date getMonthFirstDay,
			Date nextMonthFirstDay) throws Exception {
		return hotspotResourceMapper.findHotspotResRowCountOfDay(getMonthFirstDay, nextMonthFirstDay);
	}

	public List<Map<String, Object>> findHotspotResTableOfDay(Date getMonthFirstDay,
			Date nextMonthFirstDay, int pageNo, int pageSize) throws Exception {
		return hotspotResourceMapper.findHotspotResTableOfDay(getMonthFirstDay, nextMonthFirstDay, pageNo, pageSize);
	}

	public int findUserClickhotspotDomainsRowCountOfDay(Date getMonthFirstDay,
			Date nextMonthFirstDay) throws Exception {
		return hotspotResourceMapper.findUserClickhotspotDomainsRowCountOfDay(getMonthFirstDay, nextMonthFirstDay);
	}

	public List<Map<String, Object>> findUserClickhotspotDomainsTableOfDay(
			Date getMonthFirstDay, Date nextMonthFirstDay, int pageNo,
			int pageSize) throws Exception {
		return hotspotResourceMapper.findUserClickhotspotDomainsTableOfDay(getMonthFirstDay, nextMonthFirstDay, pageNo, pageSize);
	}
	
	

}
