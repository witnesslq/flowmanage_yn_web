package com.ultrapower.flowmanage.modules.flow.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ultrapower.flowmanage.common.vo.FlowExitTree;
import com.ultrapower.flowmanage.common.vo.Page;
import com.ultrapower.flowmanage.common.vo.Tree;
import com.ultrapower.flowmanage.modules.flow.dao.FlowDao;
import com.ultrapower.flowmanage.modules.flow.dao.mapper.FlowMapper;
import com.ultrapower.flowmanage.modules.flow.vo.CustomFlowDirection;
import com.ultrapower.flowmanage.modules.flow.vo.FlowExitLinkVo;
import com.ultrapower.flowmanage.modules.flow.vo.FsChartVo;
@Service("flowdao")
public class FlowDaoImpl implements FlowDao {
	private static Logger logger = Logger.getLogger(FlowDaoImpl.class);
	@Resource(name="flowMapper")
	private FlowMapper flowMapper;
	
	//各地市访问电信及联通流量分析
	public List<FsChartVo> findFlowOper( String operName,Date getMonthFirstDay, Date nextMonthFirstDay)
			throws Exception {
		
		return flowMapper.findFlowOper(operName,getMonthFirstDay,nextMonthFirstDay);
	}
	
	//各地市访问电信及联通流量分析(天粒度)
	public List<FsChartVo> findFlowOperOfDay( String operName,Date getMonthFirstDay, Date nextMonthFirstDay)
			throws Exception {
		
		return flowMapper.findFlowOperOfDay(operName,getMonthFirstDay,nextMonthFirstDay);
	}


	//业务类型名称查询
	@Override
	public List<Map<String, String>> findBusinessTypeName(String type)
			throws Exception {
		return flowMapper.findBusinessTypeName(type);
	}



	//流量出口分析 全国对比
	public List<FsChartVo> findFlowExitCountry(String param, String extiname , Date startDate, Date endDate)
			throws Exception {
		return flowMapper.findFlowExitCountry(param, extiname, startDate, endDate);
	}


	public List<Map<String, String>> findBusinessVisitKeyKpiDetails(Date getMonthFirstDay, Date nextMonthFirstDay)
			throws Exception {
		return flowMapper.findBusinessVisitKeyKpiDetails(getMonthFirstDay,nextMonthFirstDay);
	}
	
	public List<Map<String, String>> findBusinessVisitKeyKpiDetailsOfDay(Date getMonthFirstDay, Date nextMonthFirstDay)
			throws Exception {
		return flowMapper.findBusinessVisitKeyKpiDetailsOfDay(getMonthFirstDay,nextMonthFirstDay);
	}

	public List<Map<String, String>> findResourceProvideKeyKpiDetails(Date getMonthFirstDay, Date nextMonthFirstDay)
			throws Exception {
		return flowMapper.findResourceProvideKeyKpiDetails(getMonthFirstDay,nextMonthFirstDay);
	}
	
	public List<Map<String, String>> findResourceProvideKeyKpiDetailsOfDay(Date getMonthFirstDay, Date nextMonthFirstDay)
			throws Exception {
		return flowMapper.findResourceProvideKeyKpiDetailsOfDay(getMonthFirstDay,nextMonthFirstDay);
	}

	public List<Map<String, String>> findNetInOutResourceFlowDir(Date getMonthFirstDay, Date nextMonthFirstDay)
			throws Exception {
		return flowMapper.findNetInOutResourceFlowDir(getMonthFirstDay,nextMonthFirstDay);
	}

	public List<Map<String, String>> findNetInOtherProvinceIDCFlowDir(Date getMonthFirstDay, Date nextMonthFirstDay)
			throws Exception {
		return flowMapper.findNetInOtherProvinceIDCFlowDir(getMonthFirstDay,nextMonthFirstDay);
	}

	public List<Map<String, String>> findResourceNodeFlowDir(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception {
		return flowMapper.findResourceNodeFlowDir(getMonthFirstDay,nextMonthFirstDay);
	}

	public List<Map<String, String>> findOtherProvinceIDCFlow(Date getMonthFirstDay, Date nextMonthFirstDay/*,String timeType*/)
			throws Exception {
		return flowMapper.findOtherProvinceIDCFlow(getMonthFirstDay,nextMonthFirstDay/*,timeType*/);
	}

	public List<Map<String, String>> findCityVisitCTCCAndCUCCFlow(Date getMonthFirstDay, Date nextMonthFirstDay)
			throws Exception {
		return flowMapper.findCityVisitCTCCAndCUCCFlow(getMonthFirstDay,nextMonthFirstDay);
	}
	
	public List<Map<String, String>> findCityVisitCTCCAndCUCCFlowOfDay(Date getMonthFirstDay, Date nextMonthFirstDay)
			throws Exception {
		return flowMapper.findCityVisitCTCCAndCUCCFlowOfDay(getMonthFirstDay,nextMonthFirstDay);
	}

	public FlowExitTree getExitRootNode() {
		return flowMapper.getExitRootNode();
	}
	
	public List<FlowExitTree> getFlowExit(List<String> collecttimeList,String busiStr) {
		List<FlowExitTree> list = new ArrayList<FlowExitTree>();
		list = flowMapper.getFlowExit(collecttimeList,busiStr);
		return list;
		
	}

	//测试分页
	public List<Map<String, String>> getFlowExitPage(Page page, Date startTime, Date endTime) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		list = flowMapper.getFlowExitPage(page, startTime, endTime);
		return list;
		
	}
	
	
	public List<Map<String, String>> findBusiVisitResFlowIngredientForm(Date getMonthFirstDay, Date nextMonthFirstDay)
			throws Exception {
		return flowMapper.findBusiVisitResFlowIngredientForm(getMonthFirstDay,nextMonthFirstDay);
	}
	
	public List<Map<String, String>> findBusiVisitResFlowIngredientFormOfDay(Date getMonthFirstDay, Date nextMonthFirstDay)
			throws Exception {
		return flowMapper.findBusiVisitResFlowIngredientFormOfDay(getMonthFirstDay,nextMonthFirstDay);
	}
	/**
	 * 业务访问流向以及资源访问流向日均流量及占比分析
	 */
	public List<Tree> getFlowTable(String flowtype, String brtype,
			String labeltype, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception {
		return flowMapper.getFlowTable(flowtype, brtype, labeltype, getMonthFirstDay, nextMonthFirstDay);
	}
	
	/**
	 * 业务访问流向以及资源访问流向日均流量及占比分析(天粒度)
	 */
	public List<Tree> getFlowTableOfDay(String flowtype, String brtype,
			String labeltype, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception {
		return flowMapper.getFlowTableOfDay(flowtype, brtype, labeltype, getMonthFirstDay, nextMonthFirstDay);
	}

	public List<Map<String, String>> findBusiVisitIngredientFormInfo(Date getMonthFirstDay, Date nextMonthFirstDay)
			throws Exception {
		return flowMapper.findBusiVisitIngredientFormInfo(getMonthFirstDay,nextMonthFirstDay);
	}
	
	public List<Map<String, String>> findBusiVisitIngredientFormInfoOfDay(Date getMonthFirstDay, Date nextMonthFirstDay)
			throws Exception {
		return flowMapper.findBusiVisitIngredientFormInfoOfDay(getMonthFirstDay,nextMonthFirstDay);
	}

	public List<Map<String, String>> findResourceNodeIngredientInfo(Date getMonthFirstDay, Date nextMonthFirstDay)
			throws Exception {
		return flowMapper.findResourceNodeIngredientInfo(getMonthFirstDay,nextMonthFirstDay);
	}
	
	public List<Map<String, String>> findResourceNodeIngredientInfoOfDay(Date getMonthFirstDay, Date nextMonthFirstDay)
			throws Exception {
		return flowMapper.findResourceNodeIngredientInfoOfDay(getMonthFirstDay,nextMonthFirstDay);
	}
	
	//业务访问流量成分构成
	public 	List<FsChartVo> findFlowIngredient(String type1,String type2,Date getMonthFirstDay, Date nextMonthFirstDay)throws Exception{
		return flowMapper.findFlowIngredient(type1, type2, getMonthFirstDay, nextMonthFirstDay);
	}
	
	//业务访问流量成分构成(天粒度)
	public 	List<FsChartVo> findFlowIngredientOfDay(String type1,String type2,Date getMonthFirstDay, Date nextMonthFirstDay)throws Exception{
		return flowMapper.findFlowIngredientOfDay(type1, type2, getMonthFirstDay, nextMonthFirstDay);
	}
	
	//业务访问流量成分构成网内他省和网外对比
	public 	List<Map<String, String>> getFlowIngerdientByDirName(String type1,String type2,Date getMonthFirstDay, Date nextMonthFirstDay)throws Exception{
		return flowMapper.getFlowIngerdientByDirName(type1, type2, getMonthFirstDay, nextMonthFirstDay);
	}
	
	//业务访问流量成分构成网内他省和网外对比(天粒度)
	public 	List<Map<String, String>> getFlowIngerdientByDirNameOfDay(String type1,String type2,Date getMonthFirstDay, Date nextMonthFirstDay)throws Exception{
		return flowMapper.getFlowIngerdientByDirNameOfDay(type1, type2, getMonthFirstDay, nextMonthFirstDay);
	}

	public List<Map<String, String>> findFlowPropertyAnalyze(String exitid,
			Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception {
		return flowMapper.findFlowPropertyAnalyze(exitid, getMonthFirstDay, nextMonthFirstDay);
	}
	//不同访问类型的流量成分构成(饼图) 

	public List<FsChartVo> getFlowIngerdientOhtChart(String ingid, Date getMonthFirstDay, Date nextMonthFirstDay)throws Exception {
		return flowMapper.getFlowIngerdientOhtChart(ingid, getMonthFirstDay, nextMonthFirstDay);	

	}
	
	//不同访问类型的流量成分构成(饼图)天粒度
	public List<FsChartVo> getFlowIngerdientOhtChartOfDay(String ingid, Date getMonthFirstDay, Date nextMonthFirstDay)throws Exception {
		return flowMapper.getFlowIngerdientOhtChartOfDay(ingid, getMonthFirstDay, nextMonthFirstDay);	
	}


	public List<Map<String, String>> findFlowExitCityContrast(String exitid, Date getMonthFirstDay, Date nextMonthFirstDay)
			throws Exception {
		return flowMapper.findFlowExitCityContrast(exitid, getMonthFirstDay, nextMonthFirstDay);
	}

	public List<Map<String, String>> findFlowExitName() throws Exception {
		return flowMapper.findFlowExitName();
	}
	//资源提供关键指标分析
	public List<Map<String, String>> findResKPI(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception{
		return flowMapper.findResKPI(getMonthFirstDay, nextMonthFirstDay);
	}

	//资源提供关键指标分析(天粒度)
	public List<Map<String, String>> findResKPIOfDay(Date getMonthFirstDay,Date nextMonthFirstDay) throws Exception {
		return flowMapper.findResKPIOfDay(getMonthFirstDay, nextMonthFirstDay);
	}
	
	//流量性能分析-流量性能分析表格数据--分页查询数据
	@Override
	public List<Map<String, String>> findFlowPropertyAnalyzeByPagination(
			String id, int pageNo, int pageSize, Date getMonthFirstDay,
			Date nextMonthFirstDay) throws Exception {
		return flowMapper.findFlowPropertyAnalyzeByPagination(id, pageNo, pageSize, getMonthFirstDay, nextMonthFirstDay);
	}
	public List<Map<String, String>> findFlowIngerdientTable(
			Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception {
		return flowMapper.findFlowIngerdientTable(getMonthFirstDay, nextMonthFirstDay);
	}
	public List<Map<String, String>> findFlowIngerdientTableOfDay(
			Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception {
		return flowMapper.findFlowIngerdientTableOfDay(getMonthFirstDay, nextMonthFirstDay);
	}

	public List<FlowExitLinkVo> findFlowExitLinkOverview(Date getMonthFirstDay,
			Date nextMonthFirstDay, String busType) throws Exception {
		return flowMapper.findFlowExitLinkOverview(getMonthFirstDay, nextMonthFirstDay , busType);
	}
	public List<FlowExitLinkVo> findFlowExitLinkOverviewOfDay(Date getMonthFirstDay,
			Date nextMonthFirstDay, String busType) throws Exception {
		return flowMapper.findFlowExitLinkOverviewOfDay(getMonthFirstDay, nextMonthFirstDay,busType);
	}
	// 性能指标概览
	public List<Map<String, String>> findPerformanceView(String exitid,Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception{
		return flowMapper.findPerformanceView(exitid, getMonthFirstDay, nextMonthFirstDay);
	}

	public List<Map<String, Object>> findFlowDetailClass(Date getMonthFirstDay,
			Date nextMonthFirstDay) throws Exception {
		return flowMapper.findFlowDetailClass(getMonthFirstDay, nextMonthFirstDay);
	}
	
	public List<Map<String, Object>> findFlowDetailClassOfDay(Date getMonthFirstDay,
			Date nextMonthFirstDay) throws Exception {
		return flowMapper.findFlowDetailClassOfDay(getMonthFirstDay, nextMonthFirstDay);
	}

	public List<Map<String, Object>> findFlowTendencyAnalyze(String exitId,String kpiId) throws Exception {
		return flowMapper.findFlowTendencyAnalyze(exitId, kpiId);
	}


	//资源提供关键指标分析(柱状图)
	public List<Map<String, String>> findResKpiHistogram(String kpiId, Date beforeMonthFirstDay, Date getMonthFirstDay) throws Exception {
		return flowMapper.findResKpiHistogram(kpiId, beforeMonthFirstDay, getMonthFirstDay);
	}
	
	//资源提供关键指标分析(柱状图-天粒度)
	public List<Map<String, String>> findResKpiHistogramOfDay(String kpiId, Date beforeMonthFirstDay, Date getMonthFirstDay) throws Exception {
		return flowMapper.findResKpiHistogramOfDay(kpiId, beforeMonthFirstDay, getMonthFirstDay);
	}

	//查询全国IDC流量分布图(月)
	public List<Map<String, String>> findProvinceIDCFlowMap(String id,
			String type, Date beforeMonthFirstDay, Date getMonthFirstDay)
			throws Exception {
		return flowMapper.findProvinceIDCFlowMap(id, type, beforeMonthFirstDay, getMonthFirstDay);
	}

	//查询全国IDC流量分布图(天)
	public List<Map<String, String>> findProvinceIDCFlowMapOfDay(String id,
			String type, Date startTimeOfDay, Date endimeOfDay)
			throws Exception {
		return flowMapper.findProvinceIDCFlowMapOfDay(id, type, startTimeOfDay, endimeOfDay);
	}

	@Override
	public List<Map<String, Object>> findDIC(String type) {
		
		return flowMapper.findDIC(type);
	}

	@Override
	public List<Map<String, Object>> findDICChild(String parentid) {
		
		return flowMapper.findDICChild(parentid);
	}

	@Override
	public List<CustomFlowDirection> findCustomBusiFlow(String timeType,
			String type, String dirId, Date starttime, Date endtime,
			int pageSize, int pageNo) {
		
		return flowMapper.findCustomBusiFlow(timeType, type, dirId, starttime, endtime, pageSize, pageNo);
	}

	@Override
	public int findCustomBusiFlowTotal(String timeType, String type,
			String dirId, Date starttime, Date endtime) {
		
		return flowMapper.findCustomBusiFlowTotal(timeType, type, dirId, starttime, endtime);
	}

	@Override
	public List<HashMap<String, Object>> findCustomFlowComponent(
			String timeType, String dirname, String kpiid, Date starttime,
			Date endtime, int pageSize, int pageNo) {
		
		return flowMapper.findCustomFlowComponent(timeType, dirname, kpiid, starttime, endtime, pageSize, pageNo);
	}

	@Override
	public int findCustomFlowComponentTotal(String timeType, String dirname,
			String kpiid, Date starttime, Date endtime) {
		
		return flowMapper.findCustomFlowComponentTotal(timeType, dirname, kpiid, starttime, endtime);
	}

	@Override
	public List<HashMap<String, Object>> findCustomComponentDetails(
			String timeType, String dirid, String kpiid, Date starttime,
			Date endtime, int pageSize, int pageNo) {
		
		return flowMapper.findCustomComponentDetails(timeType, dirid, kpiid, starttime, endtime, pageSize, pageNo);
	}

	@Override
	public int findCustomComponentDetailsTotal(String timeType, String dirid,
			String kpiid, Date starttime, Date endtime) {
		
		return flowMapper.findCustomComponentDetailsTotal(timeType, dirid, kpiid, starttime, endtime);
	}

	@Override
	public List<HashMap<String, Object>> findCustomResComponentDetails(
			String timeType, String dirid, String kpiid, Date starttime,
			Date endtime, int pageSize, int pageNo) {
		
		return flowMapper.findCustomResComponentDetails(timeType, dirid, kpiid, starttime, endtime, pageSize, pageNo);
	}

	@Override
	public int findCustomResComponentDetailsTotal(String timeType,
			String dirid, String kpiid, Date starttime, Date endtime) {
		
		return flowMapper.findCustomResComponentDetailsTotal(timeType, dirid, kpiid, starttime, endtime);
	}

	@Override
	public List<HashMap<String, Object>> findCustomResDirection(
			String timeType, String dirid, Date starttime, Date endtime,
			int pageSize, int pageNo) {
		
		return flowMapper.findCustomResDirection(timeType, dirid, starttime, endtime, pageSize, pageNo);
	}

	@Override
	public int findCustomResDirectionTotal(String timeType, String dirid,
			Date starttime, Date endtime) {
		
		return flowMapper.findCustomResDirectionTotal(timeType, dirid, starttime, endtime);
	}

	@Override
	public List<HashMap<String, Object>> findCustomCityTelecomUnicomFlow(
			String timeType, String opername, String cityid, Date starttime,
			Date endtime, int pageSize, int pageNo) {
		
		return flowMapper.findCustomCityTelecomUnicomFlow(timeType, opername, cityid, starttime, endtime, pageSize, pageNo);
	}

	@Override
	public int findCustomCityTelecomUnicomFlowTotal(String timeType,
			String opername, String cityid, Date starttime, Date endtime) {
		
		return flowMapper.findCustomCityTelecomUnicomFlowTotal(timeType, opername, cityid, starttime, endtime);
	}

	@Override
	public List<HashMap<String, Object>> findCustomKeyIndex(String timeType,
			String type, String type2, String kpiid, Date starttime,
			Date endtime, int pageSize, int pageNo) {
		
		return flowMapper.findCustomKeyIndex(timeType, type, type2, kpiid, starttime, endtime, pageSize, pageNo);
	}

	@Override
	public int findCustomKeyIndexTotal(String timeType, String type,
			String type2, String kpiid, Date starttime, Date endtime) {
		
		return flowMapper.findCustomKeyIndexTotal(timeType, type, type2, kpiid, starttime, endtime);
	}

}
