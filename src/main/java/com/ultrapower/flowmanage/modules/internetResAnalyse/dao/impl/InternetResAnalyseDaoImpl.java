package com.ultrapower.flowmanage.modules.internetResAnalyse.dao.impl;


import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ultrapower.flowmanage.modules.internetResAnalyse.dao.InternetResAnalyseDao;
import com.ultrapower.flowmanage.modules.internetResAnalyse.dao.mapper.InternetResAnalyseMapper;
@Service("internetResAnalyseDao")
public class InternetResAnalyseDaoImpl implements InternetResAnalyseDao {
	private static Logger logger = Logger.getLogger(InternetResAnalyseDaoImpl.class);
	@Resource(name="internetResAnalyseMapper")
	private InternetResAnalyseMapper internetResAnalyseMapper;
	
	public List<Map<String, Object>> findTop10WebName(String webType,
			String sourceType, String getTime) throws Exception {
		return internetResAnalyseMapper.findTop10WebName(webType, sourceType, getTime);
	}
	
	public List<Map<String, Object>> findSourceAnalyse(String webType,
			String webName, String sourceType, String getTime) throws Exception {
		return internetResAnalyseMapper.findSourceAnalyse(webType, webName, sourceType, getTime);
	}
	
	public List<Map<String, String>> findMobileSourceRank(String webType,
			String webName, String sourceType, String getTime) throws Exception  {
		return internetResAnalyseMapper.findMobileSourceRank(webType, webName, sourceType, getTime);
	}
	
	public List<Map<String, String>> findResDetailTable(String webType, String webName,
			Date today, Date nextDay, int pageNo, int pageSize) throws Exception {
		return internetResAnalyseMapper.findResDetailTable(webType, webName, today, nextDay, pageNo, pageSize);
	}

	public int findResDetailTableCount(String webType, String webName, Date today, Date nextDay)
			throws Exception {
		return internetResAnalyseMapper.findResDetailTableCount(webType, webName, today, nextDay);
	}

	public List<Map<String, String>> findResDetailTableAll(String webType, String webName,
			Date today, Date nextDay) throws Exception {
		return internetResAnalyseMapper.findResDetailTableAll(webType, webName, today, nextDay);
	}
	
	

}
