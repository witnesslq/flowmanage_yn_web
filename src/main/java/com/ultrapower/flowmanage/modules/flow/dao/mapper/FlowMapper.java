package com.ultrapower.flowmanage.modules.flow.dao.mapper;
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

public interface FlowMapper {
	//各地市访问电信及联通流量分析
	List<FsChartVo> findFlowOper(String operName,Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//各地市访问电信及联通流量分析(天粒度)
	List<FsChartVo> findFlowOperOfDay(String operName,Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;

	//业务类型名称查询
	List<Map<String,String>> findBusinessTypeName(@Param("type")String type) throws Exception;

	//查询业务访问关键指标详情-流量流向分析表格数据
	List<Map<String, String>> findBusinessVisitKeyKpiDetails(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//查询业务访问关键指标详情-流量流向分析表格数据(天粒度)
	List<Map<String, String>> findBusinessVisitKeyKpiDetailsOfDay(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;

	List<FsChartVo> findFlowExitCountry(@Param("param")String param, @Param("extiname")String extiname, @Param("startDate")Date startDate, @Param("endDate")Date endDate )throws Exception;

	//查询资源提供关键指标详情-流量流向分析表格数据 
	List<Map<String, String>> findResourceProvideKeyKpiDetails(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//查询资源提供关键指标详情-流量流向分析表格数据(天粒度)
	List<Map<String, String>> findResourceProvideKeyKpiDetailsOfDay(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//查询业务访问网内及网外资源流量流向详表-流量流向分析表格数据
	List<Map<String, String>> findNetInOutResourceFlowDir(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//业务访问网内他省IDC流量流向详表-流量流向分析表格数据
	List<Map<String, String>> findNetInOtherProvinceIDCFlowDir(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//本省资源节点提供流量流向详表-流量流向分析表格数据
	List<Map<String, String>> findResourceNodeFlowDir(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//本省IDC资源节点提供给网内他省IDC流量详表-流量流向分析表格数据
	List<Map<String, String>> findOtherProvinceIDCFlow(Date getMonthFirstDay, Date nextMonthFirstDay/*,String timeType*/) throws Exception;
	//各地市访问电信及联通流量分析-流量流向分析表格数据
	List<Map<String, String>> findCityVisitCTCCAndCUCCFlow(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//各地市访问电信及联通流量分析-流量流向分析表格数据(天粒度)
	List<Map<String, String>> findCityVisitCTCCAndCUCCFlowOfDay(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//业务访问及资源提供流量成分构成-流量成分分析表格数据
	List<Map<String, String>> findBusiVisitResFlowIngredientForm(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//业务访问及资源提供流量成分构成-流量成分分析表格数据(天粒度)
	List<Map<String, String>> findBusiVisitResFlowIngredientFormOfDay(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//业务访问成分构成详表(省网出口)-流量成分分析表格数据
	List<Map<String, String>> findBusiVisitIngredientFormInfo(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//业务访问成分构成详表(省网出口)-流量成分分析表格数据(天粒度)
	List<Map<String, String>> findBusiVisitIngredientFormInfoOfDay(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//资源节点内容提供成分详表（省网出口）-流量成分分析表格数据
	List<Map<String, String>> findResourceNodeIngredientInfo(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//资源节点内容提供成分详表（省网出口）-流量成分分析表格数据(天粒度)
	List<Map<String, String>> findResourceNodeIngredientInfoOfDay(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//流量性能分析-流量性能分析表格数据
	List<Map<String, String>> findFlowPropertyAnalyze(String exitid, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//流量性能分析-流量性能分析表格数据--分页查询数据
	List<Map<String, String>> findFlowPropertyAnalyzeByPagination(@Param("id")String id, @Param("pageNo")int pageNo, @Param("pageSize")int pageSize, 
			@Param("nowmonfirstday")Date getMonthFirstDay, @Param("nextmonfirstday")Date nextMonthFirstDay) throws Exception;
	//业务访问流向以及资源访问流向日均流量及占比分析
	List<Tree> getFlowTable(@Param("flowtype")String flowtype, @Param("brtype")String brtype, @Param("labeltype")String labeltype, 
			@Param("getmonthfirstday")Date getMonthFirstDay, @Param("nextmonthfirstday")Date nextMonthFirstDay) throws Exception;
	//业务访问流向以及资源访问流向日均流量及占比分析(天粒度)
	List<Tree> getFlowTableOfDay(@Param("flowtype")String flowtype, @Param("brtype")String brtype, @Param("labeltype")String labeltype, 
			@Param("getmonthfirstday")Date getMonthFirstDay, @Param("nextmonthfirstday")Date nextMonthFirstDay) throws Exception;

	FlowExitTree getExitRootNode();
	List<FlowExitTree> getFlowExit(@Param("collecttimeList")List<String> collecttimeList, @Param("busiStr")String busiStr);
	
	List<Map<String, String>> getFlowExitPage(@Param("page") Page page,@Param("startTime")Date startTime, @Param("endTime")Date endTime);
	
	//业务访问流量成分构成
	List<FsChartVo> findFlowIngredient(String type1,String type2,Date getMonthFirstDay, Date nextMonthFirstDay)throws Exception;
	//业务访问流量成分构成(天粒度)
	List<FsChartVo> findFlowIngredientOfDay(String type1,String type2,Date getMonthFirstDay, Date nextMonthFirstDay)throws Exception;
	//业务访问流量成分构成网内他省和网外对比
	List<Map<String, String>> getFlowIngerdientByDirName(@Param("type1") String type1,@Param("type2") String type2,@Param("getMonthFirstDay") Date getMonthFirstDay,@Param("nextMonthFirstDay") Date nextMonthFirstDay)throws Exception;
	//业务访问流量成分构成网内他省和网外对比(天粒度)
	List<Map<String, String>> getFlowIngerdientByDirNameOfDay(@Param("type1") String type1,@Param("type2") String type2,@Param("getMonthFirstDay") Date getMonthFirstDay,@Param("nextMonthFirstDay") Date nextMonthFirstDay)throws Exception;

	//不同访问类型的流量成分构成(饼图) 
	List<FsChartVo>getFlowIngerdientOhtChart(String ingid, Date getMonthFirstDay, Date nextMonthFirstDay)throws Exception;
	//不同访问类型的流量成分构成(饼图)天粒度
	List<FsChartVo>getFlowIngerdientOhtChartOfDay(String ingid, Date getMonthFirstDay, Date nextMonthFirstDay)throws Exception;
	//不同访问类型的流量成分构成(表格)-流量成分分析
	List<Map<String, String>> findFlowIngerdientTable(Date getMonthFirstDay, Date nextMonthFirstDay)throws Exception;
	//不同访问类型的流量成分构成(表格)-流量成分分析(天粒度)
	List<Map<String, String>> findFlowIngerdientTableOfDay(Date getMonthFirstDay, Date nextMonthFirstDay)throws Exception;
	//地市对比-流量出口分析
	List<Map<String, String>> findFlowExitCityContrast(String exitid, Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//地市对比.流量出口名称-流量出口分析
	List<Map<String, String>> findFlowExitName() throws Exception;
	//资源提供关键指标分析
	List<Map<String, String>> findResKPI(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//资源提供关键指标分析(天粒度)
	List<Map<String, String>> findResKPIOfDay(Date getMonthFirstDay, Date nextMonthFirstDay) throws Exception;
	//出口链路流量概览-流量出口分析
	List<FlowExitLinkVo> findFlowExitLinkOverview(Date getMonthFirstDay, Date nextMonthFirstDay, String busType) throws Exception;
	//出口链路流量概览-流量出口分析(天粒度)
	List<FlowExitLinkVo> findFlowExitLinkOverviewOfDay(Date getMonthFirstDay, Date nextMonthFirstDay, String busType) throws Exception;
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
	List<Map<String,Object>> findDIC(@Param("type")String type);
	
	/**
	 * 通过 parentid查 t_dic 子类指标
	 * @param type parenntid
	 * @return
	 */
	List<Map<String,Object>> findDICChild(@Param("parentid")String parentid);
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
	List<CustomFlowDirection> findCustomBusiFlow(@Param("timeType")String timeType,@Param("type")String type,@Param("dirId")String dirId,@Param("starttime")Date starttime,@Param("endtime")Date endtime,@Param("pageSize") int pageSize, @Param("pageNo") int pageNo);
	/**
	 * 业务访问流向日均流量及占比分析 自定义
	 * @param timeType 时间类型
	 * @param type BUSI_TYPE
	 * @param dirId 流向id
	 * @param starttime
	 * @param endtime
	 * @return 
	 */
	int findCustomBusiFlowTotal(@Param("timeType")String timeType,@Param("type")String type,@Param("dirId")String dirId,@Param("starttime")Date starttime,@Param("endtime")Date endtime);

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
	List<HashMap<String, Object>> findCustomFlowComponent(@Param("timeType")String timeType,@Param("dirname")String dirname,@Param("kpiid")String kpiid,@Param("starttime")Date starttime,@Param("endtime")Date endtime,@Param("pageSize") int pageSize, @Param("pageNo") int pageNo);
	/**
	 * 自定义 业务访问及资源提供流量成分构成
	 * @param timeType 时间类型 day month
	 * @param dirname 网内他省 往外
	 * @param kpiid 视频（含P2P）P2P下载HTTP及其他 对应Id
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	int findCustomFlowComponentTotal(@Param("timeType")String timeType,@Param("dirname")String dirname,@Param("kpiid")String kpiid,@Param("starttime")Date starttime,@Param("endtime")Date endtime);
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
	List<HashMap<String, Object>> findCustomComponentDetails(@Param("timeType")String timeType,@Param("dirid")String dirid,@Param("kpiid")String kpiid,@Param("starttime")Date starttime,@Param("endtime")Date endtime,@Param("pageSize") int pageSize, @Param("pageNo") int pageNo);
	/**
	 * 自定义总数  业务访问成分构成详表(省网出口)
	 * @param timeType
	 * @param dirid
	 * @param kpiid
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	int findCustomComponentDetailsTotal(@Param("timeType")String timeType,@Param("dirid")String dirid,@Param("kpiid")String kpiid,@Param("starttime")Date starttime,@Param("endtime")Date endtime);
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
	List<HashMap<String, Object>> findCustomResComponentDetails(@Param("timeType")String timeType,@Param("dirid")String dirid,@Param("kpiid")String kpiid,@Param("starttime")Date starttime,@Param("endtime")Date endtime,@Param("pageSize") int pageSize, @Param("pageNo") int pageNo);
	/**
	 * 自定义 资源节点内容提供成分详表（省网出口） 总数
	 * @param timeType
	 * @param dirid
	 * @param kpiid
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	int findCustomResComponentDetailsTotal(@Param("timeType")String timeType,@Param("dirid")String dirid,@Param("kpiid")String kpiid,@Param("starttime")Date starttime,@Param("endtime")Date endtime);
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
	List<HashMap<String, Object>> findCustomResDirection(@Param("timeType")String timeType,@Param("dirid")String dirid,@Param("starttime")Date starttime,@Param("endtime")Date endtime,@Param("pageSize") int pageSize, @Param("pageNo") int pageNo);
	/**
	 * 自定义  资源提供流向日均流量及占比分析  总数
	 * @param timeType 时间类型 day month
	 * @param dirid 流向id，也可能是流向id的集合
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	int findCustomResDirectionTotal(@Param("timeType")String timeType,@Param("dirid")String dirid,@Param("starttime")Date starttime,@Param("endtime")Date endtime);
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
	List<HashMap<String, Object>> findCustomCityTelecomUnicomFlow(@Param("timeType")String timeType,@Param("opername")String opername,@Param("cityid")String cityid,@Param("starttime")Date starttime,@Param("endtime")Date endtime,@Param("pageSize") int pageSize, @Param("pageNo") int pageNo);
	/**
	 * 自定义  各地市访问电信及联通流量分析  总数
	 * @param timeType 时间类型 day month
	 * @param opername 出口方向名称
	 * @param cityid 地市id
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	int findCustomCityTelecomUnicomFlowTotal(@Param("timeType")String timeType,@Param("opername")String opername,@Param("cityid")String cityid,@Param("starttime")Date starttime,@Param("endtime")Date endtime);
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
	List<HashMap<String, Object>> findCustomKeyIndex(@Param("timeType")String timeType,@Param("type")String type,@Param("type2")String type2,@Param("kpiid")String kpiid,@Param("starttime")Date starttime,@Param("endtime")Date endtime,@Param("pageSize") int pageSize, @Param("pageNo") int pageNo);
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
	int findCustomKeyIndexTotal(@Param("timeType")String timeType,@Param("type")String type,@Param("type2")String type2,@Param("kpiid")String kpiid,@Param("starttime")Date starttime,@Param("endtime")Date endtime);


}


