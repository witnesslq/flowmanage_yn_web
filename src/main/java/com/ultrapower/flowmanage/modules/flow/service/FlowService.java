package com.ultrapower.flowmanage.modules.flow.service;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ultrapower.flowmanage.common.vo.FlowExitTree;
import com.ultrapower.flowmanage.common.vo.Page;
import com.ultrapower.flowmanage.common.vo.Tree;
import com.ultrapower.flowmanage.modules.flow.vo.FsChartVo;


public interface FlowService {

	//各地市访问电信及联通流量分析
	List<FsChartVo> findFlowOper(String operName, String getTime, String timeType) throws Exception;


	//业务类型名称查询
	List<Map<String,String>> findBusinessTypeName(String type) throws Exception;



	//流量出口分析 全国对比
	List<FsChartVo>findFlowExitCountry(String param, String extiname, String timeStr)throws Exception;	

	List<Map<String, String>> findBusinessVisitKeyKpiDetails(String getTime, String timeType) throws Exception;

	List<Map<String, String>> findResourceProvideKeyKpiDetails(String getTime, String timeType) throws Exception;
	List<Map<String, String>> findNetInOutResourceFlowDir(String getTime) throws Exception;
	List<Map<String, String>> findNetInOtherProvinceIDCFlowDir(String getTime) throws Exception;
	List<Map<String, String>> findResourceNodeFlowDir(String getTime) throws Exception;
	List<Map<String, String>> findOtherProvinceIDCFlow(String getTime) throws Exception;
	List<Map<String, String>> findCityVisitCTCCAndCUCCFlow(String getTime, String timeType) throws Exception;

	/** 获取流量出口根节点  created by zhengWei */
	FlowExitTree getExitRootNode();

	/** 日均流量分析汇总 */
	public Page getTotalTreeTable(String objectType, String busiStr);
	/** 流量出口表格树形结构数据封装  created by zhengWei */
	List<FlowExitTree> getTreeTable(Page page, String objectType, String busiStr);
	
	/** 测试分页 */
//	List<Map<String, String>> getFlowExitPage(Page page, String objectType);
	
	List<Map<String, String>> findBusiVisitResFlowIngredientForm(String getTime, String timeType) throws Exception;
	List<Map<String, String>> findBusiVisitIngredientFormInfo(String getTime, String timeType) throws Exception;
	List<Map<String, String>> findResourceNodeIngredientInfo(String getTime, String timeType) throws Exception;
	Page findFlowPropertyAnalyze(String exitid, String getTime) throws Exception;
	//流量性能分析-流量性能分析表格数据--分页查询数据
	Page findFlowPropertyAnalyzeByPagination(Page page, String exitid, String getTime) throws Exception;

	//业务访问流向以及资源访问流向日均流量及占比分析
	List<Tree> getFlowTable(String flowtype, String brtype, String labeltype, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//业务访问流向以及资源访问流向日均流量及占比分析(天粒度)
	List<Tree> getFlowTableOfDay(String flowtype, String brtype, String labeltype, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	/** 创建流量树   */
	Tree buildFlowTree(String flowtype, String brtype, String labeltype, String getTime, String timeType) throws Exception;
	
	/** 根据流量树创建拓补 */
	String createTopo(String flowtype, String brtype, String labeltype, String layout, String getTime, String timeType) throws Exception;
	//业务访问流量成分构成
	List<FsChartVo> findFlowIngredient(String type1,String type2,String getTime, String timeType)throws Exception;
	//业务访问流量成分构成网内他省和网外对比
	List<Map<String, String>> getFlowIngerdientByDirName(String type1,String type2,String getTime, String timeType)throws Exception;
	//不同访问类型的流量成分构成(饼图) 
	List<FsChartVo> getFlowIngerdientOhtChart(String ingid, String getTime,String timeType)throws Exception ;
	List<Map<String, String>> findFlowIngerdientTable(String getTime, String timeType)throws Exception;
	//业务访问关键指标分析
	List<Map<String, String>> findBusiKpiChart(String getTime, String timeType)throws Exception ;
	Map<String, Object> findFlowExitCityContrast(String exitid, String getTime) throws Exception;
	List<Map<String, String>> findFlowExitName() throws Exception;
	//资源提供关键指标分析
	List<Map<String, String>> findResKPI(String getTime, String timeType) throws Exception;
	List<Map<String, Object>> findFlowExitLinkOverview(String getTime, String timeType, String busType) throws Exception;
	//按时间查询流量性能指标值
	public Map<String, String> findPerformanceKpiValue(String exitid, String getTime) throws Exception;
	// 性能指标概览
	Map<String, String> findPerformanceView(String exitid, String getTime) throws Exception;
	List<Map<String, Object>> findFlowDetailClass(String getTime, String timeType) throws Exception;
	//根据出口ID和指标ID查询当天全省各小时流量趋势变化情况
	List<Map<String, Object>> findFlowTendencyAnalyze(String exitId, String kpiId) throws Exception;
	
	
	//互联网流量分析-流量流向分析表格数据Excel导出
	byte[] flowDirectionExcelTable(String getTime, String timeType) throws Exception;
	//互联网流量分析-流量成分分析表格数据Excel导出
	byte[] flowIngredientExcelTable(String getTime, String timeType) throws Exception;
	//互联网流量分析-流量出口分析表格数据Excel导出
	byte[] flowExitExcelTable(String getTime) throws Exception;
	//互联网流量分析-流量性能分析表格数据Excel导出
	byte[] flowPerformanceExcelTable(String getTime) throws Exception;
	
	//资源提供关键指标分析(柱状图)
	public List<Map<String, String>> findResKpiHistogram(String kpiId, String getTime, String timeType) throws Exception;
	//查询全国IDC流量分布图
	public List<Map<String, String>> findProvinceIDCFlowMap(String id, String type, String getTime, String timeType) throws Exception;
	
	
	/**
	 * 通过 OBJECTTYPE查 t_dic 
	 * @param type OBJECTTYPE
	 * @return
	 */
	List<Map<String,Object>> findDIC(String type);
	
	/**
	 * 通过 parentid查 t_dic 子类指标
	 * @param type parenntid
	 * @return
	 */
	List<Map<String,Object>> findDICChild(String parentid);
	/**
	 * 业务访问流向日均流量及占比分析 自定义
	 * @param timeType 时间类型 day month
	 * @param type BUSI_TYPE
	 * @param dirId 流向id
	 * @param starttime
	 * @param endtime
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	Page findCustomBusiFlow(String timeType,String type,String dirId,String starttime,String endtime, int pageSize, int pageNo)throws ParseException;
	/**
	 * 自定义 业务访问及资源提供流量成分构成
	 * @param timeType 时间类型 day month
	 * @param dirname 网内他省 往外
	 * @param kpiid 视频（含P2P）P2P下载HTTP及其他 对应Id
	 * @param starttime
	 * @param endtime
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	Page findCustomFlowComponent(String timeType,String dirname,String kpiid,String starttime,String endtime, int pageSize, int pageNo) throws ParseException;
	/**
	 * 自定义分页  业务访问成分构成详表(省网出口)
	 * @param timeType
	 * @param dirid
	 * @param kpiid
	 * @param starttime
	 * @param endtime
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	Page findCustomComponentDetails(String timeType,String dirid,String kpiid,String starttimeStr,String endtimeStr, int pageSize,  int pageNo)throws ParseException;
	/**
	 * 自定义 资源节点内容提供成分详表（省网出口）分页
	 * @param timeType
	 * @param dirid
	 * @param kpiid
	 * @param starttime
	 * @param endtime
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	Page findCustomResComponentDetails(String timeType,String dirid,String kpiid,String starttimeStr,String endtimeStr, int pageSize,  int pageNo) throws ParseException ;

	/**
	 * 自定义 资源提供流向日均流量及占比分析 分页 
	 * @param timeType 时间类型 day month
	 * @param dirid 流向id，也可能是流向id的集合
	 * @param starttime
	 * @param endtime
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	Page findCustomResDirection(String timeType,String dirid,String starttimeStr,String endtimeStr, int pageSize, int pageNo) throws ParseException;
	/**
	 * 自定义 各地市访问电信及联通流量分析 分页 
	 * @param timeType 时间类型 day month
	 * @param opername 出口方向名称
	 * @param cityid 地市id
	 * @param starttime
	 * @param endtime
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	Page findCustomCityTelecomUnicomFlow(String timeType,String opername,String cityid,String starttimeStr,String endtimeStr, int pageSize, int pageNo) throws ParseException ;
	/**
	 * 自定义 业务访问关键指标详情  分页
	 * @param timeType 时间类型 day month
	 * @param type2 所属业务名称
	 * @param kpiid 指标id
	 * @param starttime
	 * @param endtime
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	Page findCustomBusKeyIndex(String timeType,String type2,String kpiid,String starttimeStr,String endtimeStr, int pageSize, int pageNo)  throws ParseException ;
	/**
	 * 自定义  IDC资源提供关键指标详情 分页
	 * @param timeType 时间类型 day month
	 * @param kpiid 指标id
	 * @param starttime
	 * @param endtime
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	Page findCustomResKeyIndex(String timeType,String kpiid,String starttimeStr,String endtimeStr, int pageSize, int pageNo) throws ParseException ;

	
	
}


