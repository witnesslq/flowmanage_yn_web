package com.ultrapower.flowmanage.modules.hotspotResource.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Controller;

import com.ultrapower.flowmanage.common.vo.Page;
import com.ultrapower.flowmanage.modules.flow.vo.FsChartVo;
import com.ultrapower.flowmanage.modules.hotspotResource.service.HotspotResourceService;


/**
 * 湖北移动流量管理系统-热点资源分析
 * @author Yuanxihua
 *
 */
@Controller(value="hotspotResourceController")
@RemotingDestination(channels={"my-amf"})
public class HotspotResourceController {
	private static Logger logger = Logger.getLogger(HotspotResourceController.class);
	@Autowired
	private HotspotResourceService hotspotResourceService;
	
	/**
	 * 根据视频类型、出口类型、流量类型查询出网视频流量排名(柱状图)
	 * @author Yuanxihua
	 * @param videoType
	 * @param exitType
	 * @param flowType
	 * @param getTime
	 * @return
	 */
	public List<FsChartVo> findExitNetVideoFlowRank(String videoType,String exitType, String flowType, String getTime,String timeType){
		List<FsChartVo> list = new ArrayList<FsChartVo>();
		try {
			list = hotspotResourceService.findExitNetVideoFlowRank(videoType, exitType, flowType, getTime,timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据视频类型、出口类型、流量类型查询出网视频流量排名(柱状图): " + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据视频类型和出口类型查询出网视频流量排名
	 * @author Yuanxihua
	 * @param videoType
	 * @param exitType
	 * @param getTime
	 * @return
	 */
	public List<Map<String, Object>> findHotspotVideoExitByVideoExitType(String videoType, String exitType, String getTime,String timeType){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = hotspotResourceService.findHotspotVideoExitByVideoExitType(videoType, exitType, getTime,timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据视频类型和出口类型查询出网视频流量排名: " + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据资源类型、当前页、每页的记录数分页查询省内IDC对外提供资源列表
	 * @author Yuanxihua
	 * @param pageNo
	 * @param pageSize
	 * @param resType
	 * @param getTime
	 * @return
	 */
	public Page findIDCResTable(int pageNo, int pageSize, String resType,String getTime,String timeType){
		Page page = new Page();
		try {
			page = hotspotResourceService.findIDCResTable(pageNo, pageSize, resType, getTime,timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据资源类型、当前页、每页的记录数查询省内IDC对外提供资源列表: " + page);
			e.printStackTrace();
		}
		return page;
	}
	
	/**
	 * 查询省内热点网站情况
	 * @author Yuanxihua
	 * @param getTime
	 * @return
	 */
	public List<Map<String, Object>> findHotspotWebTable(String getTime,String timeType){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = hotspotResourceService.findHotspotWebTable(getTime,timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "查询省内热点网站情况: " + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 查询省内热点P2P下载/P2P视频情况 
	 * @author Yuanxihua
	 * @param getTime
	 * @return
	 */
	public List<Map<String, Object>> findHotspotP2PTable(String getTime,String timeType){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = hotspotResourceService.findHotspotP2PTable(getTime,timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "查询省内热点P2P下载/P2P视频情况 : " + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 分页查询流量TOP50网站列表
	 * @author Yuanxihua
	 * @param pageNo
	 * @param pageSize
	 * @param getTime
	 * @return
	 */
	public Page findHotspotFlow50Table(int pageNo, int pageSize, String getTime,String timeType){
		Page page = new Page();
		try {
			page = hotspotResourceService.findHotspotFlow50Table(pageNo, pageSize, getTime,timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "分页查询流量TOP10000网站列表: " + page);
			e.printStackTrace();
		}
		return page;
	}
	
	/**
	 * 分页查询访问TOP50网站列表
	 * @author Yuanxihua
	 * @param pageNo
	 * @param pageSize
	 * @param getTime
	 * @return
	 */
	public Page findHotspotAcc50Table(int pageNo, int pageSize, String getTime,String timeType){
		Page page = new Page();
		try {
			page = hotspotResourceService.findHotspotAcc50Table(pageNo, pageSize, getTime, timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "分页查询访问TOP10000网站列表: " + page);
			e.printStackTrace();
		}
		return page;
	}
	
	/**
	 * 分页查询网内流量占比小于70%网站列表
	 * @author Yuanxihua
	 * @param pageNo
	 * @param pageSize
	 * @param getTime
	 * @return
	 */
	public Page findHotspotPer70Table(int pageNo, int pageSize, String getTime,String timeType){
		Page page = new Page();
		try {
			page = hotspotResourceService.findHotspotPer70Table(pageNo, pageSize, getTime,timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "分页查询网内流量占比小于70%网站列表: " + page);
			e.printStackTrace();
		}
		return page;
	}
	
	/**
	 * 分页查询TOP1000域名列表
	 * @author Yuanxihua
	 * @param pageNo
	 * @param pageSize
	 * @param getTime
	 * @return
	 */
	public Page findHotspotDNS1000Table(int pageNo, int pageSize, String getTime,String timeType){
		Page page = new Page();
		try {
			page = hotspotResourceService.findHotspotDNS1000Table(pageNo, pageSize, getTime,timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "分页查询TOP1000域名列表: " + page);
			e.printStackTrace();
		}
		return page;
	}
	
	/**
	 * 分页查询非移动引入域名列表
	 * @author Yuanxihua
	 * @param pageNo
	 * @param pageSize
	 * @param getTime
	 * @return
	 */
	public Page findHotspotDNSNOCMTable(int pageNo, int pageSize, String getTime,String timeType){
		Page page = new Page();
		try {
			page = hotspotResourceService.findHotspotDNSNOCMTable(pageNo, pageSize, getTime,timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "分页查询非移动引入域名列表: " + page);
			e.printStackTrace();
		}
		return page;
	}
	
	/**
	 * 分页查询热点资源引入分析列表
	 * @author Yuanxihua
	 * @param pageNo
	 * @param pageSize
	 * @param getTime
	 * @return
	 */
	public Page findHotspotResTable(int pageNo, int pageSize, String getTime,String timeType){
		Page page = new Page();
		try {
			page = hotspotResourceService.findHotspotResTable(pageNo, pageSize, getTime,timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "分页查询热点资源引入分析列表: " + page);
			e.printStackTrace();
		}
		return page;
	}
	
	/**
	 * 热点资源分析表格数据Excel导出
	 * @author Yuanxihua
	 * @param getTime
	 * @return
	 */
	public byte[] hotspotResourceExcelTable(String getTime,String timeType){
		byte[] bt = null;
		try {
			bt = hotspotResourceService.hotspotResourceExcelTable(getTime,timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "热点资源分析表格数据Excel导出: " + bt);
			e.printStackTrace();
		}
		return bt;
	}
	
	/**
	 * 分页查询无线用户点击热点域名列表
	 * @author Yuanxihua
	 * @param pageNo
	 * @param pageSize
	 * @param getTime
	 * @return
	 */
	public Page findUserClickhotspotDomainsTable(int pageNo, int pageSize, String getTime,String timeType){
		Page page = new Page();
		try {
			page = hotspotResourceService.findUserClickhotspotDomainsTable(pageNo, pageSize, getTime,timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "分页查询无线用户点击热点域名列表: " + page);
			e.printStackTrace();
		}
		return page;
	}
}
