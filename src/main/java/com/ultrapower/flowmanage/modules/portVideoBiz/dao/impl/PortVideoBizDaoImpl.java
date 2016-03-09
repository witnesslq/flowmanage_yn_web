package com.ultrapower.flowmanage.modules.portVideoBiz.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ultrapower.flowmanage.modules.portVideoBiz.dao.PortVideoBizDao;
import com.ultrapower.flowmanage.modules.portVideoBiz.dao.mapper.PortVideoBizMapper;
@Service("portVideoBizDao")
public class PortVideoBizDaoImpl implements PortVideoBizDao {
	private static Logger logger = Logger.getLogger(PortVideoBizDaoImpl.class);
	@Resource(name="portVideoBizMapper")
	private PortVideoBizMapper portVideoBizMapper;
	
	public List<Map<String, String>> findBizNetQualityViewByTime(String viewType, String timeType, Date startTime, Date endTime) throws Exception {
		return portVideoBizMapper.findBizNetQualityViewByTime(viewType, timeType, startTime, endTime);
	}
	
	public List<Map<String, String>> findBizNetQualityCityContrastViewByTime(String viewType, String timeType, Date startTime, Date endTime) throws Exception {
		return portVideoBizMapper.findBizNetQualityCityContrastViewByTime(viewType, timeType, startTime, endTime);
	}
	
	public List<Map<String, String>> findBizNetQualityVideoWebTop3ByTime(String viewType, String timeType, Date startTime, Date endTime, String sort) throws Exception {
		return portVideoBizMapper.findBizNetQualityVideoWebTop3ByTime(viewType, timeType, startTime, endTime, sort);
	}

	public List<Map<String, String>> findBizNetQCMicroViewByTime(String viewType, String timeType, int cityId, Date startTime, Date endTime) throws Exception {
		return portVideoBizMapper.findBizNetQCMicroViewByTime(viewType, cityId, timeType, startTime, endTime);
	}

	public List<Map<String, String>> findVideoWebProblemNumberByTime(String timeType, String cityId, String operator, String webId,Date startTime, Date endTime) throws Exception {
		return portVideoBizMapper.findVideoWebProblemNumberByTime(timeType, cityId, operator, webId, startTime, endTime);
	}

	public List<Map<String, String>> findNetPoorQualityByTime(String timeType,Date startTime, Date endTime) throws Exception {
		return portVideoBizMapper.findNetPoorQualityByTime(timeType, startTime, endTime);
	}

	public List<Map<String, String>> findBizQualityPaphByTime(String timeType, String cityId, String operator, Date startTime, Date endTime) throws Exception {
		return portVideoBizMapper.findBizQualityPaphByTime(timeType, cityId, operator, startTime, endTime);
	}

	public List<Map<String, String>> findNetQualityProblemCountByTime(String timeType, Date startTime, Date endTime) throws Exception {
		return portVideoBizMapper.findNetQualityProblemCountByTime(timeType, startTime, endTime);
	}

	public List<Map<String, String>> findNetQualityMicroByTime(String timeType,String cityId, String operator, String webId, Date startTime, Date endTime) throws Exception {
		return portVideoBizMapper.findNetQualityMicroByTime(timeType, cityId, operator, webId, startTime, endTime);
	}

	public List<Map<String, String>> findNetQualityThreshold() throws Exception {
		return portVideoBizMapper.findNetQualityThreshold();
	}

	public List<Map<String, String>> findNetQualityPoorVideoByTime(String viewType, String timeType, String cityId, String operator, Date startTime, Date endTime) throws Exception {
		return portVideoBizMapper.findNetQualityPoorVideoByTime(viewType, timeType, cityId, operator, startTime, endTime);
	}

	public List<Map<String, String>> findPartDirectionQualityByTime(String timeType, String cityId, String webId, String operator, Date startTime, Date endTime) throws Exception {
		return portVideoBizMapper.findPartDirectionQualityByTime(timeType, cityId, webId, operator, startTime, endTime);
	}

}
