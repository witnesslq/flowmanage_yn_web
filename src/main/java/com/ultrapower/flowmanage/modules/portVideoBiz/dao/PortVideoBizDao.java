package com.ultrapower.flowmanage.modules.portVideoBiz.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface PortVideoBizDao {
	List<Map<String, String>> findBizNetQualityViewByTime(String viewType, String timeType, Date startTime, Date endTime) throws Exception;
	List<Map<String, String>> findBizNetQualityCityContrastViewByTime(String viewType, String timeType, Date startTime, Date endTime) throws Exception;
	List<Map<String, String>> findBizNetQualityVideoWebTop3ByTime(String viewType, String timeType, Date startTime, Date endTime, String sort) throws Exception;
	List<Map<String, String>> findBizNetQCMicroViewByTime(String viewType, String timeType, int cityId, Date startTime, Date endTime) throws Exception;
	List<Map<String, String>> findVideoWebProblemNumberByTime(String timeType, String cityId, String operator, String webId, Date startTime, Date endTime) throws Exception;
	List<Map<String, String>> findNetPoorQualityByTime(String timeType, Date startTime, Date endTime) throws Exception;
	List<Map<String, String>> findBizQualityPaphByTime(String timeType, String cityId, String operator, Date startTime, Date endTime) throws Exception;
	List<Map<String, String>> findNetQualityProblemCountByTime(String timeType, Date startTime, Date endTime) throws Exception;
	List<Map<String, String>> findNetQualityMicroByTime(String timeType, String cityId, String operator, String webId, Date startTime, Date endTime) throws Exception;
	List<Map<String, String>> findNetQualityThreshold() throws Exception;
	List<Map<String, String>> findNetQualityPoorVideoByTime(String viewType, String timeType, String cityId, String operator, Date startTime, Date endTime) throws Exception;
	List<Map<String, String>> findPartDirectionQualityByTime(String timeType, String cityId, String webId, String operator, Date startTime, Date endTime) throws Exception;
}
