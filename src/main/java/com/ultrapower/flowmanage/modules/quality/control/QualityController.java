package com.ultrapower.flowmanage.modules.quality.control;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Controller;

import com.ultrapower.flowmanage.common.vo.Page;
import com.ultrapower.flowmanage.common.vo.Tree;
import com.ultrapower.flowmanage.modules.flow.vo.FsChartVo;
import com.ultrapower.flowmanage.modules.quality.service.QualityService;

/**
 * 湖北移动流量管理系统-互联网质量分析
 * @author Yuanxihua
 *
 */
@Controller(value="qualityController")
@RemotingDestination(channels={"my-amf"})
public class QualityController {
	private static Logger logger = Logger.getLogger(QualityController.class);
	
	@Autowired
	private QualityService qualityService;
	
	
	/**
	 * 查询地市TOP200网站时延-互联网质量评估表格数据
	 * @param startTime
	 * @param endTime
	 * @param timeType
	 * @param companyId
	 * @return
	 */
	public List<Map<String, Object>> findCityTop200WebsiteTimedelay(String startTime,
			String endTime, String timeType, String companyId){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			System.out.println("findCityTop200WebsiteTimedelay---"+startTime+" "+endTime+" "+timeType+" "+companyId+" ");
			list=qualityService.findCityTop200WebsiteTimedelay(startTime,endTime,timeType,companyId);
		} catch (Exception e) {
			logger.error(e.getMessage() + " 查询地市TOP200网站时延-互联网质量评估表格数据 ：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 查询集团热点TOP10网站信息表格-互联网质量评估
	 * @param startTime
	 * @param endTime
	 * @param timeType
	 * @param companyId
	 * @return
	 */
	public List<Map<String, Object>> findGroupHotspotTop10Net(String startTime,
			String endTime, String timeType, String companyId){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			System.out.println("findGroupHotspotTop10Net---"+startTime+" "+endTime+" "+timeType+" "+companyId+" ");
			list=qualityService.findGroupHotspotTop10Net(startTime,endTime,timeType,companyId);
		} catch (Exception e) {
			logger.error(e.getMessage() + " 查询集团热点TOP10网站信息表格-互联网质量评估：" + list);
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 接入KQI地市对比
	 * @author Yuanxihua
	 * @return
	 */
	public	List<Map<String,String>> findKQIMenu(){
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			list=qualityService.findKQIMenu();
		} catch (Exception e) {
			logger.error(e.getMessage() + " 接入KQI地市对比：" + list);
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 接入KQI出口对比
	 * @author Yuanxihua
	 * @param exitid
	 * @param getTime
	 * @return
	 */
	public List<FsChartVo>findKQIExit(String exitid,String getTime){
		List<FsChartVo> list = new ArrayList<FsChartVo>();
		try {
			list=qualityService.findKQIExit(exitid, getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + " 接入KQI出口对比：" + list);
			e.printStackTrace();
		}
		return list;
	}

	
	/**
	 * 接入KQI地市对比
	 * @author Yuanxihua
	 * @param kqiType
	 * @param kpiId
	 * @param getTime
	 * @return
	 */
	public List<Map<String, Object>> findKQICityComponent(String kqiType,String kpiId,String startTime,
			String endTime, String timeType, String companyId,String cityId){
		try {
			return qualityService.findKQICityComponent(kqiType, kpiId, startTime, endTime, timeType, companyId, cityId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage() + " 接入KQI地市对比：" );
			return null;
		}
	}
			
//	public Map<String, Object> findKQICityComponent(String kqiType,String kpiId,String startTime,
//			String endTime, String timeType, String companyId,String cityId) {
//		 Map<String, Object> map = new HashMap<String, Object>();
//		try {
//			System.out.println("findKQICityComponent---"+kqiType+" "+kpiId+" "+startTime+" "+endTime+" "+timeType+" "+companyId+" "+cityId);
//			map=qualityService.findKQICityComponent(kqiType, kpiId, startTime,endTime,timeType,companyId,cityId);
//		} catch (Exception e) {
//			logger.error(e.getMessage() + " 接入KQI地市对比：" + map);
//			e.printStackTrace();
//		}
//		return map;
//	}
	

	/**
	 * 创建业务KQI地市对比 导航树 
	 * @author Yuanxihua
	 * @param type
	 * @return
	 */
	public Tree buildBusiKqiNavTree(String type) {
		Tree rootTree = new Tree();
		try {
			rootTree = qualityService.buildBusiKqiNavTree(type);
		} catch (Exception e) {
			logger.error(e.getMessage() + " 业务KQI地市对比导航：" + rootTree);
			e.printStackTrace();
		}
		return rootTree;
	}

	/**
	 * 业务KQI地市对比
	 * @author Yuanxihua
	 * @return
	 */
	public List<Map<String,String>> findBUIKQIMenu(){
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			list=qualityService.findBUIKQIMenu();
		} catch (Exception e) {
			logger.error(e.getMessage() + " 接入KQI地市对比：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据业务类型查询互联网投诉及用户满意度地市对比-互联网质量评估
	 * @author Yuanxihua
	 * @param kpiId
	 * @param getTime
	 * @return
	 */
	public Map<String, Object> findNetComplaintCSATCityContrast(String kpiId,String getTime){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = qualityService.findNetComplaintCSATCityContrast(kpiId, getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据业务类型查询互联网投诉及用户满意度地市对比-互联网质量评估：" + map);
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 根据字典表类型查询业务名称
	 * @author Yuanxihua
	 * @return
	 */
	public List<Map<String, Object>> findBusinessName(String bizType){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = qualityService.findBusinessName(bizType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据字典表类型查询业务名称：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	
	/**
	 * 根据地市ID和业务类型查询省内热点网站情况
	 * @author Yuanxihua
	 * @param bizId
	 * @param cityId
	 * @param getTime
	 * @return
	 */
	public List<Map<String, Object>> findProvincialHotspotNetTable(String bizId, String cityId, String startTime,
			String endTime, String timeType, String companyId){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			System.out.println("findProvincialHotspotNetTable---"+bizId+" "+cityId+" "+startTime+" "+endTime+" "+timeType+" "+companyId);
			list = qualityService.findProvincialHotspotNetTable(bizId, cityId, startTime,endTime,timeType,companyId);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据地市ID和业务类型查询省内热点网站情况：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据接入或业务类型查询KQI地市对比(菜单)
	 * @author Yuanxihua
	 * @param objectType
	 * @return
	 */
	public List<Map<String, Object>> findKQICityMenu(String objectType){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = qualityService.findKQICityMenu(objectType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据接入或业务类型查询KQI地市对比(菜单)：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	
	/**
	 * 根据业务类型和业务级别查询接入KQI出口指标名称
	 * @author Yuanxihua
	 * @param objectType
	 * @param lev
	 * @return
	 */
	public List<Map<String, String>> findAccKQIExitName(String objectType,String lev){
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		try {
			list = qualityService.findAccKQIExitName(objectType, lev);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据业务类型和业务级别查询接入KQI出口指标名称：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据KPIID查询接入KQI出口对比
	 * @author Yuanxihua
	 * @param kpiId
	 * @param getTime
	 * @return
	 */
	public List<FsChartVo> findAccKQIExitContrast(String kpiId, String getTime){
		List<FsChartVo> list = new ArrayList<FsChartVo>();
		try {
			list=qualityService.findAccKQIExitContrast(kpiId, getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据KPIID查询接入KQI出口对比：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 互联网质量分析表格数据Excel导出
	 * @param startTime
	 * @param endTime
	 * @param timeType
	 * @return
	 */
	public byte[] qualityExcelTable(String startTime,String endTime, String timeType){
		byte[] bt = null;
		try {
			bt = qualityService.qualityExcelTable(startTime,endTime,timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "互联网质量分析表格数据Excel导出: " + bt);
			e.printStackTrace();
		}
		return bt;
	}
	
	public void insertTest(){
		try {
			long startTime=System.currentTimeMillis();   //获取开始时间
			qualityService.insertTest();
			long endTime=System.currentTimeMillis(); //获取结束时间
			System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 查找厂家
	 * @return
	 */
	public List<HashMap<String, String>> findCompany()  {
		// TODO Auto-generated method stub
		try {
			return qualityService.findCompany();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("查找厂家出错！");
			return null;
		}
	}
	/**
	 * 查询自定义
	 * @param yewuType 业务类型 1，-业务KQI地市对比 2省内热点网站情况 3地市TOP200网站时延  4集团TOP10网站信息
	 * @param timeType 时间类型，天周月，day week month
	 * @param city 地市id
	 * @param company 厂商id
	 * @param starttime 查询的开始时间
	 * @param endtime 查询的结束时间
	 * @param pageSize 每页多少条
	 * @param pageNo 第多少页
	 * @return
	 */
	public Page getZidingyi(int yewuType,String timeType,String city, String company, String starttime,
			String endtime,int pageSize, int pageNo) {
		try {
			return qualityService.getZidingyi(yewuType,timeType,city, company, starttime, endtime, pageSize, pageNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("查找自定义出错！");
			return null;
		}
	}
	/**
	 * 分页查询详细页  地市TOP200网站时延
	 * @param webname 网站名称
	 * @param city 地市名称
	 * @param company 厂商名称
	 * @param starttime 查询的开始时间
	 * @param endtime 查询的结束时间
	 * @param pageSize 每页多少条
	 * @param pageNo 第多少页
	 * @return
	 */
	public Page getXiangxi4(String webname, List<String> city, String company,
			String starttime, String endtime, int pageSize, int pageNo){
		try {
			return qualityService.getXiangxi4(webname,city, company, starttime, endtime, pageSize, pageNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("查找详细出错！");
			return null;
		}
	}
	/**
	 * 分页查询详细页  地市TOP200网站时延
	 * @param city 地市名称
	 * @param company 厂商名称
	 * @param starttime 查询的开始时间
	 * @param endtime 查询的结束时间
	 * @param pageSize 每页多少条
	 * @param pageNo 第多少页
	 * @return
	 */
	public Page getXiangxi3(List<String> city, String company, String starttime,
			String endtime, int pageSize, int pageNo)  {
		try {
			return qualityService.getXiangxi3(city, company, starttime, endtime, pageSize, pageNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("查找详细出错！");
			return null;
		}
	}
	/**
	 * 分页查询详细页  1业务KQI地市对比 2省内热点网站情况
	 * @param yewuType  1，-业务KQI地市对比 2省内热点网站情况
	 * @param yewu 业务中文名称
	 * @param city 地市名称
	 * @param company 厂商名称
	 * @param starttime 查询的开始时间
	 * @param endtime 查询的结束时间
	 * @param pageSize 每页多少条
	 * @param pageNo 第多少页
	 * @return
	 */
	public Page getXiangxi12(int yewuType, String yewu,List<String> city,
			String company, String starttime, String endtime, int pageSize,
			int pageNo){
		try {
			return qualityService.getXiangxi12(yewuType,yewu,city, company, starttime, endtime, pageSize, pageNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("查找详细出错！");
			return null;
		}
	}
	/**
	 * 自定义查询导出 
	 * @param yewuType 1，-业务KQI地市对比 2省内热点网站情况 3地市TOP200网站时延  4集团TOP10网站信息
	 * @param timeType 时间类型，天周月，day week month
	 * @param city 地市id
	 * @param company 厂商id
	 * @param starttime 查询的开始时间
	 * @param endtime 查询的结束时间
	 * @param pageSize 如果为0，导出所有记录 ，否则导出分页记录
	 * @param pageNo 如果为0，导出所有记录 ，否则导出分页记录
	 * @return
	 */
	public byte[] daochuZidingyi(int yewuType,String timeType,String city, String company, String starttimeStr,
			String endtimeStr,int pageSize, int pageNo)  {
		try {
			return qualityService.daochuZidingyi(yewuType, timeType, city, company, starttimeStr, endtimeStr, pageSize, pageNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("导出自定义出错！");
			return null;
		}
		
	}
	/**
	 * 在天周月粒度页面点击导出 调用此方法
	 * @param yewuType 1，-业务KQI地市对比 2省内热点网站情况 3地市TOP200网站时延  4集团TOP10网站信息
	 * @param timeType 时间类型，天周月，day week month
	 * @param city 地市id
	 * @param company 厂商id
	 * @param starttime 查询的开始时间
	 * @param endtime 查询的结束时间
	 * @param pageSize 如果为0，导出所有记录 ，否则导出分页记录
	 * @param pageNo 如果为0，导出所有记录 ，否则导出分页记录
	 * @return
	 */
	public byte[] daochuTianzhouyue(int yewuType,String timeType,String city, String company, String starttimeStr,
			String endtimeStr,int pageSize, int pageNo) {
		try {
			return qualityService.daochuTianzhouyue(yewuType, timeType, city, company, starttimeStr, endtimeStr, pageSize, pageNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("导出天周月出错！");
			return null;
		}
	}
	
	/**
	 * 导出详细页-集团TOP10网站信息
	 * @param webname 网站名称
	 * @param city 地市名称
	 * @param company 厂商名称
	 * @param starttime 查询的开始时间
	 * @param endtime 查询的结束时间
	 * @param pageSize 如果为0，导出所有记录 ，否则导出分页记录
	 * @param pageNo 如果为0，导出所有记录 ，否则导出分页记录
	 * @return
	 */
	public byte[] daochuXiangxi4(String webname, List<String> city, String company,
			String starttime, String endtime, int pageSize, int pageNo){
		try {
			return qualityService.daochuXiangxi4(webname,city, company, starttime, endtime, pageSize, pageNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("查找详细出错！");
			return null;
		}
	}
	/**
	 * 
	 * 导出详细页--地市TOP200网站时延 分页 
	 * @param city 地市名称
	 * @param company 厂商名称
	 * @param starttime 查询的开始时间
	 * @param endtime 查询的结束时间
	 * @param pageSize 如果为0，导出所有记录 ，否则导出分页记录
	 * @param pageNo 如果为0，导出所有记录 ，否则导出分页记录
	 * @return
	 */
	public byte[] daochuXiangxi3(List<String> city, String company, String starttime,
			String endtime, int pageSize, int pageNo)  {
		try {
			return qualityService.daochuXiangxi3(city, company, starttime, endtime, pageSize, pageNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("查找详细出错！");
			return null;
		}
	}
	/**
	 * 导出详细页-省内热点网站情况-业务KQI地市对比
	 * @param yewuType 1为业务KQI地市对比，2为省内热点网站情况
	 * @param yewu 业务中文名称
	 * @param city 地市名称
	 * @param company 厂商名称
	 * @param starttime 查询的开始时间
	 * @param endtime 查询的结束时间
	 * @param pageSize 如果为0，导出所有记录 ，否则导出分页记录
	 * @param pageNo 如果为0，导出所有记录 ，否则导出分页记录
	 * @return
	 */
	public byte[] daochuXiangxi12(int yewuType, String yewu,List<String> city,
			String company, String starttime, String endtime, int pageSize,
			int pageNo){
		try {
			return qualityService.daochuXiangxi12(yewuType,yewu,city, company, starttime, endtime, pageSize, pageNo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("查找详细出错！");
			return null;
		}
	}
	
	/**
	 * 根据时间和时间类型查询重点指标概述
	 * @param timeType 时间类型
	 * @param getTime 时间
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> findQualityKPIInfo(String timeType, String getTime){
		List<Map<String, String>> list = null;
		try {
			list = qualityService.findQualityKPIInfo(timeType, getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据时间和时间类型查询重点指标概述：" + list);
			e.printStackTrace();
		}
		return list;
	}
}
