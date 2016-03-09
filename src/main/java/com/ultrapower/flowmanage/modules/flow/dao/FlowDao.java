package com.ultrapower.flowmanage.modules.flow.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ultrapower.flowmanage.common.vo.FlowExitTree;
import com.ultrapower.flowmanage.common.vo.Page;
import com.ultrapower.flowmanage.common.vo.Tree;
import com.ultrapower.flowmanage.modules.flow.vo.CustomFlowDirection;
import com.ultrapower.flowmanage.modules.flow.vo.FlowExitLinkVo;
import com.ultrapower.flowmanage.modules.flow.vo.FsChartVo;

public interface FlowDao {

	//各地市访问电信及联通流量分析
	List<FsChartVo> findFlowOper(String operName,Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	
	//各地市访问电信及联通流量分析(天粒度)
	List<FsChartVo> findFlowOperOfDay(String operName,Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;

	//流量出口分析 全国对比
	List<FsChartVo>findFlowExitCountry(String param, String extiname, Date startDate, Date endDate)throws Exception;

	List<Map<String, String>> findBusinessVisitKeyKpiDetails(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	List<Map<String, String>> findBusinessVisitKeyKpiDetailsOfDay(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;

	List<Map<String, String>> findResourceProvideKeyKpiDetails(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	List<Map<String, String>> findResourceProvideKeyKpiDetailsOfDay(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	List<Map<String, String>> findNetInOutResourceFlowDir(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	List<Map<String, String>> findNetInOtherProvinceIDCFlowDir(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	List<Map<String, String>> findResourceNodeFlowDir(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	List<Map<String, String>> findOtherProvinceIDCFlow(Date getMonthFirstDay, Date nextMonthFirstDay/*,String timeType*/) throws Exception;
	List<Map<String, String>> findCityVisitCTCCAndCUCCFlow(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	List<Map<String, String>> findCityVisitCTCCAndCUCCFlowOfDay(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	List<Map<String, String>> findBusiVisitResFlowIngredientForm(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	List<Map<String, String>> findBusiVisitResFlowIngredientFormOfDay(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	List<Map<String, String>> findBusiVisitIngredientFormInfo(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	List<Map<String, String>> findBusiVisitIngredientFormInfoOfDay(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	List<Map<String, String>> findResourceNodeIngredientInfo(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	List<Map<String, String>> findResourceNodeIngredientInfoOfDay(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	List<Map<String, String>> findFlowPropertyAnalyze(String exitid, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//流量性能分析-流量性能分析表格数据--分页查询数据
	List<Map<String, String>> findFlowPropertyAnalyzeByPagination(String id, int pageNo, int pageSize, 
				Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;

	//业务类型名称查询
	List<Map<String,String>> findBusinessTypeName(String type) throws Exception;
	
	FlowExitTree getExitRootNode();
	List<FlowExitTree> getFlowExit(List<String> collecttimeList, String busiStr);
	
	List<Map<String, String>> getFlowExitPage(Page page, Date startTime, Date endTime);
	
	//业务访问流向以及资源访问流向日均流量及占比分析
	List<Tree> getFlowTable(String flowtype, String brtype, String labeltype, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//业务访问流向以及资源访问流向日均流量及占比分析(天粒度)
	List<Tree> getFlowTableOfDay(String flowtype, String brtype, String labeltype, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//业务访问流量成分构成 
	List<FsChartVo> findFlowIngredient(String type1,String type2,Date getMonthFirstDay, Date nextMonthFirstDay)throws Exception;
	//业务访问流量成分构成网内他省和网外对比
	List<Map<String, String>> getFlowIngerdientByDirName(String type1,String type2,Date getMonthFirstDay, Date nextMonthFirstDay)throws Exception;
	//业务访问流量成分构成网内他省和网外对比(天粒度)
	List<Map<String, String>> getFlowIngerdientByDirNameOfDay(String type1,String type2,Date getMonthFirstDay, Date nextMonthFirstDay)throws Exception;
	//业务访问流量成分构成(天粒度)
	List<FsChartVo> findFlowIngredientOfDay(String type1,String type2,Date getMonthFirstDay, Date nextMonthFirstDay)throws Exception;
	//不同访问类型的流量成分构成(饼图) 
	List<FsChartVo>getFlowIngerdientOhtChart(String ingid, Date getMonthFirstDay, Date nextMonthFirstDay)throws Exception;
	//不同访问类型的流量成分构成(饼图)天粒度
	List<FsChartVo>getFlowIngerdientOhtChartOfDay(String ingid, Date getMonthFirstDay, Date nextMonthFirstDay)throws Exception;
	List<Map<String, String>> findFlowIngerdientTable(Date getMonthFirstDay, Date nextMonthFirstDay)throws Exception;
	List<Map<String, String>> findFlowIngerdientTableOfDay(Date getMonthFirstDay, Date nextMonthFirstDay)throws Exception;

	List<Map<String, String>> findFlowExitCityContrast(String exitid, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	List<Map<String, String>> findFlowExitName() throws Exception;
	//资源提供关键指标分析
	List<Map<String, String>> findResKPI(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//资源提供关键指标分析(天粒度)
	List<Map<String, String>> findResKPIOfDay(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	List<FlowExitLinkVo> findFlowExitLinkOverview(Date getMonthFirstDay, Date nextMonthFirstDay, String busType) throws Exception;
	List<FlowExitLinkVo> findFlowExitLinkOverviewOfDay(Date getMonthFirstDay, Date nextMonthFirstDay, String busType ) throws Exception;
	// 性能指标概览
	List<Map<String, String>> findPerformanceView(String exitid,Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//不同访问类型的流量成分构成.流量详细分类表格-流量成分分析
	List<Map<String, Object>> findFlowDetailClass(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//不同访问类型的流量成分构成.流量详细分类表格-流量成分分析(天粒度)
	List<Map<String, Object>> findFlowDetailClassOfDay(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//根据出口ID和指标ID查询当天全省各小时流量趋势变化情况
	List<Map<String, Object>> findFlowTendencyAnalyze(String exitId, String kpiId) throws Exception;
	//资源提供关键指标分析(柱状图)
	List<Map<String, String>> findResKpiHistogram(String kpiId, Date beforeMonthFirstDay, Date getMonthFirstDay) throws Exception;
	//资源提供关键指标分析(柱状图-天粒度)
	List<Map<String, String>> findResKpiHistogramOfDay(String kpiId, Date beforeMonthFirstDay, Date getMonthFirstDay) throws Exception;
	
	//查询全国IDC流量分布图(月)
	List<Map<String, String>> findProvinceIDCFlowMap(String id, String type, Date beforeMonthFirstDay, Date getMonthFirstDay) throws Exception;
	//查询全国IDC流量分布图(天)
	List<Map<String, String>> findProvinceIDCFlowMapOfDay(String id, String type, Date startTimeOfDay, Date endimeOfDay) throws Exception;
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
	 * @param timeType 时间类型
	 * @param type BUSI_TYPE
	 * @param dirId 流向id
	 * @param starttime
	 * @param endtime
	 * @param pageSize
	 * @param pageNo
	 * @return CustomFlowDirection
	 */
	List<CustomFlowDirection> findCustomBusiFlow(String timeType,String type,String dirId,Date starttime,Date endtime, int pageSize, int pageNo);
	/**
	 * 业务访问流向日均流量及占比分析 自定义
	 * @param timeType 时间类型
	 * @param type BUSI_TYPE
	 * @param dirId 流向id
	 * @param starttime
	 * @param endtime
	 * @return 
	 */
	int findCustomBusiFlowTotal(String timeType,String type,String dirId,Date starttime,Date endtime);
	
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
	List<HashMap<String, Object>> findCustomFlowComponent(String timeType,String dirname,String kpiid,Date starttime,Date endtime, int pageSize, int pageNo);
	/**
	 * 自定义 业务访问及资源提供流量成分构成
	 * @param timeType 时间类型 day month
	 * @param dirname 网内他省 往外
	 * @param kpiid 视频（含P2P）P2P下载HTTP及其他 对应Id
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	int findCustomFlowComponentTotal(String timeType,String dirname,String kpiid,Date starttime,Date endtime);
	
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
	List<HashMap<String, Object>> findCustomComponentDetails(String timeType,String dirid,String kpiid,Date starttime,Date endtime, int pageSize,  int pageNo);
	/**
	 * 自定义总数  业务访问成分构成详表(省网出口)
	 * @param timeType
	 * @param dirid
	 * @param kpiid
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	int findCustomComponentDetailsTotal(String timeType,String dirid,String kpiid,Date starttime,Date endtime);
	
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
	List<HashMap<String, Object>> findCustomResComponentDetails(String timeType,String dirid,String kpiid,Date starttime,Date endtime, int pageSize,  int pageNo);
	/**
	 * 自定义 资源节点内容提供成分详表（省网出口） 总数
	 * @param timeType
	 * @param dirid
	 * @param kpiid
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	int findCustomResComponentDetailsTotal(String timeType,String dirid,String kpiid,Date starttime,Date endtime);
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
	List<HashMap<String, Object>> findCustomResDirection(String timeType,@Param("dirid")String dirid,Date starttime,Date endtime, int pageSize, int pageNo);
	/**
	 * 自定义  资源提供流向日均流量及占比分析  总数
	 * @param timeType 时间类型 day month
	 * @param dirid 流向id，也可能是流向id的集合
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	int findCustomResDirectionTotal(String timeType,@Param("dirid")String dirid,Date starttime,Date endtime);
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
	List<HashMap<String, Object>> findCustomCityTelecomUnicomFlow(String timeType,String opername,String cityid,Date starttime,Date endtime, int pageSize, int pageNo);
	/**
	 * 自定义  各地市访问电信及联通流量分析  总数
	 * @param timeType 时间类型 day month
	 * @param opername 出口方向名称
	 * @param cityid 地市id
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	int findCustomCityTelecomUnicomFlowTotal(String timeType,String opername,String cityid,Date starttime,Date endtime);
	/**
	 * 自定义 业务访问关键指标详情 IDC资源提供关键指标详情 分页
	 * @param timeType 时间类型 day month
	 * @param type 业务还是IDC res bus
	 * @param type2 所属业务名称
	 * @param kpiid 指标id
	 * @param starttime
	 * @param endtime
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	List<HashMap<String, Object>> findCustomKeyIndex(String timeType,String type,String type2,String kpiid,Date starttime,Date endtime, int pageSize, int pageNo);
	/**
	 * 自定义 业务访问关键指标详情 IDC资源提供关键指标详情  总数 
	 * @param timeType 时间类型 day month
	 * @param type 业务还是IDC res bus
	 * @param type2 所属业务名称
	 * @param kpiid 指标id
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	int findCustomKeyIndexTotal(String timeType,String type,String type2,String kpiid,Date starttime,Date endtime);

}
