package com.ultrapower.flowmanage.modules.wlanLogAnalyse.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ultrapower.flowmanage.modules.wlanLogAnalyse.dao.WlanLogAnalyseDao;
import com.ultrapower.flowmanage.modules.wlanLogAnalyse.dao.mapper.WlanLogAnalyseMapper;
import com.ultrapower.flowmanage.modules.wlanLogAnalyse.vo.WlanLogParameter;
@Service("wlanLogAnalyseDao")
public class WlanLogAnalyseDaoImpl implements WlanLogAnalyseDao {
	private static Logger logger = Logger.getLogger(WlanLogAnalyseDaoImpl.class);
	@Resource(name="wlanLogAnalyseMapper")
	private WlanLogAnalyseMapper wlanLogAnalyseMapper;
	
	//根据时间、用户名称、上线认证、下线认证、认证成功、认证失败查询portal日志记录数
	public int findPortalLogRowCount(WlanLogParameter parameter) throws Exception{
		return wlanLogAnalyseMapper.findPortalLogRowCount(parameter);
	}
			
	//根据时间、用户名称、上线认证、下线认证、认证成功、认证失败分页查询portal日志列表
	public List<Map<String, String>> findPortalLogTable(WlanLogParameter parameter) throws Exception{
		return wlanLogAnalyseMapper.findPortalLogTable(parameter);
	}


	//根据时间、用户名称、执行成功、执行失败查询MML日志记录数
	public int findMmlLogRowCount(WlanLogParameter parameter) throws Exception{
		return wlanLogAnalyseMapper.findMmlLogRowCount(parameter);
	}

	//根据时间、用户名称、执行成功、执行失败分页查询MML日志列表
	public List<Map<String, String>> findMmlLogTable(WlanLogParameter parameter) throws Exception{
		return wlanLogAnalyseMapper.findMmlLogTable(parameter);
	}
	

}
