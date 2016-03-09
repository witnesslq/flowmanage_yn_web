package com.ultrapower.flowmanage.modules.portVideoBiz.dao.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface PortVideoBizMapper {
	List<Map<String, String>> findBizNetQualityViewByTime(@Param("viewType")String viewType, @Param("timeType")String timeType, @Param("startTime")Date startTime, @Param("endTime")Date endTime) throws Exception;
	List<Map<String, String>> findBizNetQualityCityContrastViewByTime(@Param("viewType")String viewType, @Param("timeType")String timeType, @Param("startTime")Date startTime, @Param("endTime")Date endTime) throws Exception;
	List<Map<String, String>> findBizNetQualityVideoWebTop3ByTime(@Param("viewType")String viewType, @Param("timeType")String timeType, @Param("startTime")Date startTime, @Param("endTime")Date endTime, @Param("sort")String sort) throws Exception;
	List<Map<String, String>> findBizNetQCMicroViewByTime(@Param("viewType")String viewType, @Param("cityId")int cityId, @Param("timeType")String timeType, @Param("startTime")Date startTime, @Param("endTime")Date endTime) throws Exception;
	List<Map<String, String>> findVideoWebProblemNumberByTime(@Param("timeType")String timeType, @Param("cityId")String cityId, @Param("operator")String operator, @Param("webId")String webId, @Param("startTime")Date startTime, @Param("endTime")Date endTime) throws Exception;
	List<Map<String, String>> findNetPoorQualityByTime(@Param("timeType")String timeType, @Param("startTime")Date startTime, @Param("endTime")Date endTime) throws Exception;
	List<Map<String, String>> findBizQualityPaphByTime(@Param("timeType")String timeType, @Param("cityId")String cityId, @Param("operator")String operator, @Param("startTime")Date startTime, @Param("endTime")Date endTime) throws Exception;
	List<Map<String, String>> findNetQualityProblemCountByTime(@Param("timeType")String timeType, @Param("startTime")Date startTime, @Param("endTime")Date endTime) throws Exception;
	List<Map<String, String>> findNetQualityMicroByTime(@Param("timeType")String timeType, @Param("cityId")String cityId, @Param("operator")String operator, @Param("webId")String webId, @Param("startTime")Date startTime, @Param("endTime")Date endTime) throws Exception;
	List<Map<String, String>> findNetQualityThreshold() throws Exception;
	List<Map<String, String>> findNetQualityPoorVideoByTime(@Param("viewType")String viewType, @Param("timeType")String timeType, @Param("cityId")String cityId, @Param("operator")String operator,  @Param("startTime")Date startTime, @Param("endTime")Date endTime) throws Exception;
	List<Map<String, String>> findPartDirectionQualityByTime(@Param("timeType")String timeType, @Param("cityId")String cityId, @Param("webId")String webId, @Param("operator")String operator,  @Param("startTime")Date startTime, @Param("endTime")Date endTime) throws Exception;
}
