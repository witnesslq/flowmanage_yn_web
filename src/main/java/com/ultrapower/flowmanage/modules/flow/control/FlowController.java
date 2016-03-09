package com.ultrapower.flowmanage.modules.flow.control;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Controller;

import com.ultrapower.flowmanage.common.vo.FlowExitTree;
import com.ultrapower.flowmanage.common.vo.Page;
import com.ultrapower.flowmanage.modules.flow.service.FlowService;
import com.ultrapower.flowmanage.modules.flow.vo.FsChartVo;

/**
 * 湖北移动流量管理系统-互联网流量分析
 * @author Yuanxihua
 *
 */
@Controller(value="flowController")
@RemotingDestination(channels={"my-amf"})
public class FlowController {
	private static Logger logger = Logger.getLogger(FlowController.class);
	
	@Autowired
	private FlowService flowservice;
	
	/**
	 * 各地市访问电信及联通流量分析
	 * @author Huangsisi
	 * @param operName
	 * @param getTime
	 * @param timeType
	 * @return
	 */
	public List<FsChartVo> findFlowOper(String operName, String getTime, String timeType)
	{
		
		List<FsChartVo> list = new ArrayList<FsChartVo>();
		try {
			list = flowservice.findFlowOper(operName,getTime, timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "各地市访问电信及联通流量分析：" + list);
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 流量出口分析 全国对比
	 * @author Huangsisi
	 * @param param
	 * @param extiname
	 * @param timeStr
	 * @return
	 * @throws Exception
	 */
	public List<FsChartVo> findFlowExitCountry(String param, String extiname, String timeStr)
			throws Exception {
		List<FsChartVo> list = new ArrayList<FsChartVo>();
		try{
			list=flowservice.findFlowExitCountry(param, extiname, timeStr);
		}catch (Exception e) {
			logger.error(e.getMessage() + "流量出口分析 全国对比：" + list);
			e.printStackTrace();
		}
		
		return list;
	}
		
	/**
	 * 查询业务访问关键指标详情-流量流向分析表格数据
	 * @author Yuanxihua
	 * @return
	 */
	public List<Map<String, String>> findBusinessVisitKeyKpiDetails(String getTime, String timeType){
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		try {
			list = flowservice.findBusinessVisitKeyKpiDetails(getTime, timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "查询业务访问关键指标详情-流量流向分析表格数据：" + list);
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 业务类型名称查询
	 * @author Yuanxihua
	 * @param type
	 * @return
	 */
	public List<Map<String, String>> findBusinessTypeName(String type) {
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		try {
			list = flowservice.findBusinessTypeName(type);
			return list;
		} catch (Exception e) {
			logger.error(e.getMessage()+"业务类型名称查询: "+list);
			e.printStackTrace();
			return new ArrayList<Map<String,String>>();
		}
	}	


	/**
	 * 查询资源提供关键指标详情-流量流向分析表格数据 
	 * @author Yuanxihua
	 * @return
	 */
	public List<Map<String, String>> findResourceProvideKeyKpiDetails(String getTime, String timeType){
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		try {
			list = flowservice.findResourceProvideKeyKpiDetails(getTime, timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "查询资源提供关键指标详情-流量流向分析表格数据 ：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 查询业务访问网内及网外资源流量流向详表-流量流向分析表格数据
	 * @author Yuanxihua
	 * @return
	 */
	public List<Map<String, String>> findNetInOutResourceFlowDir(String getTime){
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		try {
			list = flowservice.findNetInOutResourceFlowDir(getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "查询业务访问网内及网外资源流量流向详表-流量流向分析表格数据 ：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 业务访问网内他省IDC流量流向详表-流量流向分析表格数据
	 * @author Yuanxihua
	 * @return
	 */
	public List<Map<String, String>> findNetInOtherProvinceIDCFlowDir(String getTime){
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		try {
			list = flowservice.findNetInOtherProvinceIDCFlowDir(getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "业务访问网内他省IDC流量流向详表-流量流向分析表格数据 ：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 本省资源节点提供流量流向详表-流量流向分析表格数据
	 * @author Yuanxihua
	 * @return
	 */
	public List<Map<String, String>> findResourceNodeFlowDir(String getTime){
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		try {
			list = flowservice.findResourceNodeFlowDir(getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "本省资源节点提供流量流向详表-流量流向分析表格数据 ：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 本省IDC资源节点提供给网内他省IDC流量详表-流量流向分析表格数据
	 * @author Yuanxihua
	 * @return
	 */
	public List<Map<String, String>> findOtherProvinceIDCFlow(String getTime){
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		try {
			list = flowservice.findOtherProvinceIDCFlow(getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "本省IDC资源节点提供给网内他省IDC流量详表-流量流向分析表格数据：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 各地市访问电信及联通流量分析-流量流向分析表格数据
	 * @author Yuanxihua
	 * @return
	 */
	public List<Map<String, String>> findCityVisitCTCCAndCUCCFlow(String getTime, String timeType){
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		try {
			list = flowservice.findCityVisitCTCCAndCUCCFlow(getTime, timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "各地市访问电信及联通流量分析-流量流向分析表格数据：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 业务访问及资源提供流量成分构成-流量成分分析表格数据
	 * @author Yuanxihua
	 * @return
	 */
	public List<Map<String, String>> findBusiVisitResFlowIngredientForm(String getTime, String timeType){
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		try {
			list = flowservice.findBusiVisitResFlowIngredientForm(getTime, timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "业务访问及资源提供流量成分构成-流量成分分析表格数据：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 业务访问成分构成详表(省网出口)-流量成分分析表格数据
	 * @author Yuanxihua
	 * @return
	 */
	public List<Map<String, String>> findBusiVisitIngredientFormInfo(String getTime, String timeType){
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		try {
			list = flowservice.findBusiVisitIngredientFormInfo(getTime, timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "业务访问成分构成详表(省网出口)-流量成分分析表格数据：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 资源节点内容提供成分详表（省网出口）-流量成分分析表格数据
	 * @author Yuanxihua
	 * @return
	 */
	public List<Map<String, String>> findResourceNodeIngredientInfo(String getTime, String timeType){
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		try {
			list = flowservice.findResourceNodeIngredientInfo(getTime, timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "资源节点内容提供成分详表（省网出口）-流量成分分析表格数据：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 流量性能分析-流量性能分析表格数据
	 * @author Yuanxihua
	 * @param exitid
	 * @param getTime
	 * @return
	 */
	public Page findFlowPropertyAnalyze(String exitid, String getTime){
		Page page = new Page();
		try {
			page = flowservice.findFlowPropertyAnalyze(exitid, getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "流量性能分析-流量性能分析表格数据：" + page);
			e.printStackTrace();
		}
		return page;
	}
	/**
	 * 流量性能分析-分页数据查询
	 * @author Yuanxihua
	 * @param pageNo
	 * @param pageSize
	 * @param exitid
	 * @param getTime
	 * @return
	 */
	public Page getFlowPropertyAnalyzeByPagination(Page page, String exitid, String getTime) {
		Page retPage = new Page();
		try {
			retPage = flowservice.findFlowPropertyAnalyzeByPagination(page, exitid, getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "流量性能分析-流量性能分析：" + retPage);
			e.printStackTrace();
		}
		return retPage;
	}
	
	/**
	 * 地市对比-流量出口分析
	 * @author Yuanxihua
	 * @param exitid
	 * @param getTime
	 * @return
	 */
	public Map<String, Object> findFlowExitCityContrast(String exitid, String getTime){
		Map<String, Object> list = new HashMap<String,Object>();
		try {
			list = flowservice.findFlowExitCityContrast(exitid, getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "地市对比-流量出口分析：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 地市对比.流量出口名称-流量出口分析
	 * @author Yuanxihua
	 * @return
	 */
	public List<Map<String, String>> findFlowExitName(){
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		try {
			list = flowservice.findFlowExitName();
		} catch (Exception e) {
			logger.error(e.getMessage() + "地市对比.流量出口名称-流量出口分析：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/** 创建拓补    create by zhengWei */
	public String createTopo(String flowtype, String brtype, String labeltype, String layout, String getTime, String timeType) {
		String xml = "";
		try {
			xml = flowservice.createTopo(flowtype, brtype, labeltype, layout, getTime, timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "拓补：" + xml);
			e.printStackTrace();
		}
		return xml;
	}
	
	/** 日均流量分析汇总   create by zhengWei*/
	public Page getTotalTreeTable(String objectType,String busiStr){
		Page page = flowservice.getTotalTreeTable(objectType, busiStr);
		return page;
	}
	/**
	 * @author Zhengwei
	 * @return
	 */
	public List<FlowExitTree> getTreeTable(Page page, String objectType,String busiStr) {
		List<FlowExitTree> list = new ArrayList<FlowExitTree>();
		list = flowservice.getTreeTable(page, objectType, busiStr);
		return list;
		
	}
	
	
//	public List<Map<String, String>> getFlowExitPage(Page page, String objectType) {
//		
//		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//		list = flowservice.getFlowExitPage(page, objectType);
//		return list;
//		
//	}

	/**
	 * 业务访问流量成分构成
	 * @author Huangsisi
	 * @param type1
	 * @param type2
	 * @param getTime
	 * @param timeType
	 * @return
	 */
	public List<FsChartVo> findFlowIngredient(String type1,String type2,String getTime, String timeType){
		List<FsChartVo> list = new ArrayList<FsChartVo>();
		try{
			list=flowservice.findFlowIngredient(type1, type2, getTime, timeType);
		}catch (Exception e) {
			logger.error(e.getMessage() + "业务访问流量成分构成：" + list);
			e.printStackTrace();
		}
		
		return list;
	}
	
	public List<Map<String, String>> getFlowIngerdientByDirName(String type1,String type2,String getTime, String timeType){
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		try{
			list=flowservice.getFlowIngerdientByDirName(type1, type2, getTime, timeType);
		}catch (Exception e) {
			logger.error(e.getMessage() + "业务访问流量成分构成网外、网内他省对比：" + list);
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 不同访问类型的流量成分构成(饼图) 
	 * @author Yuanxihua
	 * @param ingid
	 * @param getTime
	 * @param timeType
	 * @return
	 */
	public List<FsChartVo> getFlowIngerdientOhtChart(String ingid, String getTime,String timeType){

		List<FsChartVo> list = new ArrayList<FsChartVo>();
		try {

			list=flowservice.getFlowIngerdientOhtChart(ingid,getTime,timeType);

		} catch (Exception e) {
			logger.error(e.getMessage() + "不同访问类型的流量成分构成(饼图) ：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 不同访问类型的流量成分构成(表格)-流量成分分析
	 * @author Yuanxihua
	 * @param getTime
	 * @return
	 */
	public List<Map<String, String>> findFlowIngerdientTable(String getTime, String timeType){
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		try {
			list=flowservice.findFlowIngerdientTable(getTime, timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "不同访问类型的流量成分构成(表格)-流量成分分析：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 业务访问关键指标分析
	 * @author Yuanxihua
	 * @param getTime
	 * @param timeType
	 * @return
	 */
	public List<Map<String, String>> findBusiKpiChart(String getTime, String timeType){
			List<Map<String, String>> list = new ArrayList<Map<String,String>>();
			try {
				list=flowservice.findBusiKpiChart(getTime, timeType);
			} catch (Exception e) {
				logger.error(e.getMessage() + "业务访问关键指标分析 ：" + list);
				e.printStackTrace();
			}
			return list;
	}

	/**
	 * 资源提供关键指标分析
	 * @author Yuanxihua
	 * @param getTime
	 * @param timeType
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> findResKPI(String getTime, String timeType) throws Exception{
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		try {
			list=flowservice.findResKPI(getTime, timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "资源提供关键指标分析 ：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 出口链路流量概览-流量出口分析
	 * @author Yuanxihua
	 * @param getTime
	 * @return
	 */
	public List<Map<String, Object>> findFlowExitLinkOverview(String getTime, String timeType, String busType){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list=flowservice.findFlowExitLinkOverview(getTime, timeType , busType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "出口链路流量概览-流量出口分析 ：" + list);
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 性能指标概览
	 * @author Yuanxihua
	 * @param exitid
	 * @param getTime
	 * @return
	 */
	public Map<String, String> findPerformanceView(String exitid, String getTime) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			map=flowservice.findPerformanceView(exitid,getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + " 性能指标概览 ：" + map);
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 不同访问类型的流量成分构成.流量详细分类表格-流量成分分析
	 * @author Yuanxihua
	 * @param getTime
	 * @return
	 */
	public List<Map<String, Object>> findFlowDetailClass(String getTime, String timeType){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list=flowservice.findFlowDetailClass(getTime, timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "不同访问类型的流量成分构成.流量详细分类表格-流量成分分析：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据出口ID和指标ID查询当天全省各小时流量趋势变化情况
	 * @author Yuanxihua
	 * @param exitId
	 * @param kpiId
	 * @return
	 */
	public List<Map<String, Object>> findFlowTendencyAnalyze(String exitId,String kpiId){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list=flowservice.findFlowTendencyAnalyze(exitId, kpiId);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据出口ID和指标ID查询当天全省各小时流量趋势变化情况：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 互联网流量分析-流量流向分析表格数据Excel导出
	 * @author Yuanxihua
	 * @param getTime
	 * @return
	 */
	public byte[] flowDirectionExcelTable(String getTime, String timeType){
		byte[] bt = null;
		try {
			bt = flowservice.flowDirectionExcelTable(getTime, timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "互联网流量分析-流量流向分析表格数据Excel导出: " + bt);
			e.printStackTrace();
		}
		return bt;
	}
	
	/**
	 * 互联网流量分析-流量成分分析表格数据Excel导出
	 * @author Yuanxihua
	 * @param getTime
	 * @return
	 */
	public byte[] flowIngredientExcelTable(String getTime, String timeType){
		byte[] bt = null;
		try {
			bt = flowservice.flowIngredientExcelTable(getTime, timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "互联网流量分析-流量成分分析表格数据Excel导出: " + bt);
			e.printStackTrace();
		}
		return bt;
	}
	
	
	/**
	 * 互联网流量分析-流量出口分析表格数据Excel导出
	 * @author Yuanxihua
	 * @param getTime
	 * @return
	 */
	public byte[] flowExitExcelTable(String getTime){
		byte[] bt = null;
		try {
			bt = flowservice.flowExitExcelTable(getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "互联网流量分析-流量出口分析表格数据Excel导出: " + bt);
			e.printStackTrace();
		}
		return bt;
	}
	
	/**
	 * 互联网流量分析-流量性能分析表格数据Excel导出
	 * @author Yuanxihua
	 * @param getTime
	 * @return
	 */
	public byte[] flowPerformanceExcelTable(String getTime){
		byte[] bt = null;
		try {
			bt = flowservice.flowPerformanceExcelTable(getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "互联网流量分析-流量性能分析表格数据Excel导出: " + bt);
			e.printStackTrace();
		}
		return bt;
	}
	
	
	/**
	 * 资源提供关键指标分析(柱状图)
	 * @author Yuanxihua
	 * @param kpiId
	 * @param getTime
	 * @return
	 */
	public List<Map<String, String>> findResKpiHistogram(String kpiId, String getTime, String timeType){
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			list=flowservice.findResKpiHistogram(kpiId, getTime, timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + " 资源提供关键指标分析(柱状图) ：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	
	/**
	 * 查询全国IDC流量分布图
	 * @author Yuanxihua
	 * @param id
	 * @param type
	 * @param getTime
	 * @param timeType
	 * @return
	 */
	public List<Map<String, String>> findProvinceIDCFlowMap(String id, String type, String getTime, String timeType){
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			list=flowservice.findProvinceIDCFlowMap(id, type, getTime, timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + " 查询全国IDC流量分布图：" + list);
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * ING_IDX __ 全部 视频（含P2P） P2P下载 HTTP及其他
	 * FLOW_PM_EXIT—— 省网出口 三方出口 IDC 区域
	 * CITY 
	 * @return key-OBJECTID,OBJECTNAME
	 */
	public List<Map<String, Object>> findDIC(String type) {
		try {
			return flowservice.findDIC(type);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查字典表出错！", e);
			return null;
		}
		
	}
	/**
	 * 通过 parentid查 t_dic 子类指标
	 * @param type parenntid
	 * @return key-OBJECTID,OBJECTNAME
	 */
	public List<Map<String, Object>> findDICChild(String parentid) {
		try {
			return flowservice.findDICChild(parentid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("查字典表子类指标出错！", e);
			return null;
		}
	}
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
	public Page findCustomBusiFlow(String timeType, String type, String dirId,
			String starttime, String endtime, int pageSize, int pageNo) {
		try {
			return flowservice.findCustomBusiFlow(timeType, type, dirId, starttime, endtime, pageSize, pageNo);
		} catch (Exception e) {
			logger.error("业务访问流向日均流量及占比分析 自定义出错！", e);
			return null;
		}
	}
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
	public Page findCustomFlowComponent(String timeType, String dirname,
			String kpiid, String starttimeStr, String endtimeStr, int pageSize, int pageNo) {
		try {
			return flowservice.findCustomFlowComponent(timeType, dirname, kpiid, starttimeStr, endtimeStr, pageSize, pageNo);
		} catch (Exception e) {
			logger.error(" 自定义 业务访问及资源提供流量成分构成出错！", e);
			return null;
		}
	}
	/**
	 * 自定义分页  业务访问成分构成详表(省网出口)
	 * @param timeType  时间类型 day month
	 * @param dirid 网内他省或网外的id
	 * @param kpiid  视频（含P2P）P2P下载HTTP及其他 对应Id
	 * @param starttime
	 * @param endtime
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	public Page findCustomComponentDetails(String timeType, String dirid,
			String kpiid, String starttimeStr, String endtimeStr, int pageSize,
			int pageNo) {
		try {
			return flowservice.findCustomComponentDetails(timeType, dirid, kpiid, starttimeStr, endtimeStr, pageSize, pageNo);
		} catch (Exception e) {
			logger.error(" 自定义分页  业务访问成分构成详表(省网出口)出错！", e);
			return null;
		}
	}
	/**
	 * 自定义 资源节点内容提供成分详表（省网出口）
	 * @param timeType  时间类型 day month
	 * @param dirid 网内他省或网外的id
	 * @param kpiid  视频（含P2P）P2P下载HTTP及其他 对应Id
	 * @param starttime
	 * @param endtime
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	public Page findCustomResComponentDetails(String timeType, String dirid,
			String kpiid, String starttimeStr, String endtimeStr, int pageSize,
			int pageNo){
		try {
			return flowservice.findCustomResComponentDetails(timeType, dirid, kpiid, starttimeStr, endtimeStr, pageSize, pageNo);
		} catch (Exception e) {
			logger.error("自定义 资源节点内容提供成分详表（省网出口）出错！", e);
			return null;
		}
	}
	/**
	 * 自定义 资源提供流向日均流量及占比分析 分页 
	 * @param timeType 时间类型 day month
	 * @param dirid 流向id，也可能是流向id的集合
	 * @param starttime
	 * @param endtime
	 * @param pageSize
	 * @param pageNo
	 * @return DIRID,
		       DIRNAME,
		       ROUND(VAL/1024/1024/1024/1024,2) VAL,
		       PERC,
		       TO_CHAR(COLLECTTIME, 'yyyy-mm-dd') COLLECTTIME
	 * 
	 */
	public Page findCustomResDirection(String timeType, String dirid,
			String starttimeStr, String endtimeStr, int pageSize, int pageNo){
		try {
			return flowservice.findCustomResDirection(timeType, dirid, starttimeStr, endtimeStr, pageSize, pageNo);
		} catch (Exception e) {
			logger.error("自定义 资源提供流向日均流量及占比分析 分页 出错！", e);
			return null;
		}
	}
	/**
	 * 自定义 各地市访问电信及联通流量分析 分页 
	 * @param timeType 时间类型 day month
	 * @param opername 出口方向名称
	 * @param cityid 地市id
	 * @param starttime
	 * @param endtime
	 * @param pageSize
	 * @param pageNo
	 * @return OPERNAME,
		       CITY,
		       TO_CHAR(COLLECTTIME, 'yyyy-mm-dd') COLLECTTIME,
		       ROUND(VAL / 1024 / 1024 / 1024 / 1024, 2) VAL,
		       PERC
	 */
	public Page findCustomCityTelecomUnicomFlow(String timeType,
			String opername, String cityid, String starttimeStr,
			String endtimeStr, int pageSize, int pageNo){
		try {
			return flowservice.findCustomCityTelecomUnicomFlow(timeType, opername, cityid, starttimeStr, endtimeStr, pageSize, pageNo);
		} catch (Exception e) {
			logger.error("自定义 各地市访问电信及联通流量分析 分页 出错！", e);
			return null;
		}
	}
	/**
	 * 自定义 业务访问关键指标详情  分页
	 * @param timeType 时间类型 day month
	 * @param type2 所属业务名称
	 * @param kpiid 指标id
	 * @param starttime
	 * @param endtime
	 * @param pageSize
	 * @param pageNo
	 * @return TO_CHAR(COLLECTTIME, 'yyyy-mm-dd') COLLECTTIME,
		       KPINAME,
		       KPIID,
		       TYPE2,
		       ROUND(VAL / 1024 / 1024 / 1024 / 1024, 2) VAL,
		       PERC
	 */
	public Page findCustomBusKeyIndex(String timeType, String type2,
			String kpiid, String starttimeStr, String endtimeStr, int pageSize,
			int pageNo) {
		try {
			return flowservice.findCustomBusKeyIndex(timeType, type2, kpiid, starttimeStr, endtimeStr, pageSize, pageNo);
		} catch (Exception e) {
			logger.error("自定义 业务访问关键指标详情 IDC资源提供关键指标详情 分页出错！", e);
			return null;
		}
	}
	/**
	 * 自定义IDC资源提供关键指标详情 分页
	 * @param timeType 时间类型 day month
	 * @param kpiid 指标id
	 * @param starttime
	 * @param endtime
	 * @param pageSize
	 * @param pageNo
	 * @return TO_CHAR(COLLECTTIME, 'yyyy-mm-dd') COLLECTTIME,
		       KPINAME,
		       KPIID,
		       TYPE2,
		       ROUND(VAL / 1024 / 1024 / 1024 / 1024, 2) VAL,
		       PERC
	 */
	public Page findCustomResKeyIndex(String timeType, String kpiid,
			String starttimeStr, String endtimeStr, int pageSize, int pageNo) {
		try {
			return flowservice.findCustomResKeyIndex(timeType, kpiid, starttimeStr, endtimeStr, pageSize, pageNo);
		} catch (Exception e) {
			logger.error("自定义 业务访问关键指标详情 IDC资源提供关键指标详情 分页出错！", e);
			return null;
		}
	}
}
