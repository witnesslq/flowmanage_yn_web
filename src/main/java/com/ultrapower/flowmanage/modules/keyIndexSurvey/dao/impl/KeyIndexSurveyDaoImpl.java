package com.ultrapower.flowmanage.modules.keyIndexSurvey.dao.impl;


import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ultrapower.flowmanage.modules.keyIndexSurvey.dao.KeyIndexSurveyDao;
import com.ultrapower.flowmanage.modules.keyIndexSurvey.dao.mapper.KeyIndexSurveyMapper;
@Service("keyIndexSurveyDao")
public class KeyIndexSurveyDaoImpl implements KeyIndexSurveyDao {
	private static Logger logger = Logger.getLogger(KeyIndexSurveyDaoImpl.class);
	@Resource(name="keyIndexSurveyMapper")
	private KeyIndexSurveyMapper keyIndexSurveyMapper;
	
	public List<Map<String, Object>> findFlowNetRate(Date startTime, Date endTime)
			throws Exception {
		
		return keyIndexSurveyMapper.findFlowNetRate(startTime, endTime);
	}
	
	public List<Map<String, String>> findFlowNetRateTable(Date startTime,
			Date endTime, int pageNo, int pageSize) throws Exception {
		
		return keyIndexSurveyMapper.findFlowNetRateTable(startTime, endTime, pageNo, pageSize);
	}
	
	public int findFlowNetRateTableCount(Date startTime, Date endTime)
			throws Exception {
		
		return keyIndexSurveyMapper.findFlowNetRateTableCount(startTime, endTime);
	}
	
	public List<Map<String, Object>> findPhoneHiteRate(Date startTime, Date endTime)
			throws Exception {
		
		return keyIndexSurveyMapper.findPhoneHiteRate(startTime, endTime);
	}
	
	public List<Map<String, String>> findPhoneHiteRateTable(Date startTime,
			Date endTime, int pageNo, int pageSize) throws Exception {
		
		return keyIndexSurveyMapper.findPhoneHiteRateTable(startTime, endTime, pageNo, pageSize);
	}
	
	public int findPhoneHiteRateTableCount(Date startTime, Date endTime)
			throws Exception {
		
		return keyIndexSurveyMapper.findPhoneHiteRateTableCount(startTime, endTime);
	}
	
	public List<Map<String, Object>> findContentmeetRate(Date startTime, Date endTime)
			throws Exception {
		
		return keyIndexSurveyMapper.findContentmeetRate(startTime, endTime);
	}
	
	public List<Map<String, String>> findContentmeetRateTable(Date startTime,
			Date endTime, int pageNo, int pageSize) throws Exception {
		
		return keyIndexSurveyMapper.findContentmeetRateTable(startTime, endTime, pageNo, pageSize);
	}
	
	public int findContentmeetRateTableCount(Date startTime, Date endTime)
			throws Exception {
		
		return keyIndexSurveyMapper.findContentmeetRateTableCount(startTime, endTime);
	}
	
	
	

}
