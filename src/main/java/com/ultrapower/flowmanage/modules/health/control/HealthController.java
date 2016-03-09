package com.ultrapower.flowmanage.modules.health.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Controller;

import com.ultrapower.flowmanage.common.utils.FlowTree;
import com.ultrapower.flowmanage.common.vo.Page;
import com.ultrapower.flowmanage.common.vo.Tree;
import com.ultrapower.flowmanage.modules.flow.vo.FsChartVo;
import com.ultrapower.flowmanage.modules.health.service.HealthService;
import com.ultrapower.flowmanage.modules.health.vo.AssDimRelevance;
import com.ultrapower.flowmanage.modules.health.vo.CityContrastVo;
import com.ultrapower.flowmanage.modules.health.vo.CountryContrastVo;

/**
 * 湖北移动流量管理系统-健康度分析
 * @author Yuanxihua
 *
 */
@Controller(value="healthController")
@RemotingDestination(channels={"my-amf"})
public class HealthController {
	private static Logger logger = Logger.getLogger(HealthController.class);
	
	@Autowired
	private HealthService healthservice;
	
	/**
	 * 根据地市名称和健康度类型查询健康度指标详情
	 * @author Yuanxihua
	 * @param cityname 地市名称
	 * @param healthtype 健康度类型
	 * @return
	 */
	public List<Map<String, String>> findThreeNetHealth(String healthtype, String getTime, String timeType) {
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		try {
			list = healthservice.findThreeNetHealth(healthtype, getTime, timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据地市名称和健康度类型查询健康度指标详情：" + list);
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 根据健康度指标ID查询各地市健康度分数排名
	 * @author Yuanxihua
	 * @param healthId 健康度ID
	 * @return
	 */
	public List<CityContrastVo> findCityHealthScoreById(String healthId, String healthType, String getTime, String timeType) {
		List<CityContrastVo> list = new ArrayList<CityContrastVo>();
		try {
			list = healthservice.findCityHealthScoreById(healthId, healthType, getTime, timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据健康度指标ID查询各地市健康度分数排名：" + list);
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 根据健康度指标ID和健康度类型查询各地市健康度指标名称和分数  健康度三级指标分数
	 * @author Yuanxihua
	 * @param healthId 健康度ID
	 * @param healthtype 健康度类型
	 * @return
	 */
	public List<Map<String, Object>> findHealthNameAndScoreById(String healthId, String healthtype, String getTime, String timeType) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = healthservice.findHealthNameAndScoreById(healthId, healthtype, getTime, timeType); 
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据健康度指标ID和健康度类型查询各地市健康度指标名称和分数  健康度三级指标分数：" + list);
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 查询地市信息
	 * @author Yuanxihua
	 * @return
	 */
	public List<CityContrastVo> findCityInfo() {
		List<CityContrastVo> list = new ArrayList<CityContrastVo>();
		try {
			list = healthservice.findCityInfo();
		} catch (Exception e) {
			logger.error(e.getMessage() + "查询地市信息：" + list);
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 根据健康度类型查询健康度名称
	 * @author Yuanxihua
	 * @param healthType 健康度类型
	 * @return
	 */
	public List<CityContrastVo> findHealthNameByType(String healthType) {
		List<CityContrastVo> list = new ArrayList<CityContrastVo>();
		try {
			list = healthservice.findHealthNameByType(healthType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据健康度类型查询健康度名称：" + list);
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 根据健康度指标ID和健康度类型查询各地市健康度指标名称和分数  健康度二级和三级指标分数
	 * @author Yuanxihua
	 * @param healthId 健康度ID
	 * @param healthType 健康度类型
	 * @return
	 */
	public List<Map<String, Object>> findHealthNameScoreByHealthId(String healthId, String healthType, String getTime, String timeType) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try {
			list = healthservice.findHealthNameScoreByHealthId(healthId, healthType, getTime, timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据健康度指标ID和健康度类型查询各地市健康度指标名称和分数  健康度二级和三级指标分数：" + list);
			e.printStackTrace();
		}
		return list;
	}
	

	/** 创建拓补  create by zhengWei */
	public String createTopo(String type, String area, String city, String getTime, String timeType) {
		String xml = "";
		try {
			xml = healthservice.createTopo(type, area, city, getTime, timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "拓补：" + xml);
			e.printStackTrace();
		}
		return xml;
	}
	
	/** 创建健康树  create by zhengWei */
	public Tree buildHealthTree(String type, String lev){
		//获取封装好的整棵树
		Tree rootTree = new Tree();
		try {
			rootTree = healthservice.buildMenuTree(type, lev);
		} catch (Exception e) {
			logger.error(e.getMessage() + "创建健康树：" + rootTree);
			e.printStackTrace();
		}
		return rootTree;
	}
	
	/**
	 * 全国对比
	 * @author Huangsisi
	 * @param healthtype
	 * @param getTime
	 * @param timeType
	 * @return
	 */
	public List<CountryContrastVo> getCountryConstrastList(String healthtype,String getTime, String timeType) {
		List<CountryContrastVo> list = new ArrayList<CountryContrastVo>();
		try {
			list = healthservice.getCountryConstrastList(healthtype,getTime,timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "全国对比：" + list);
			e.printStackTrace();
		}
		return list;
		
	}

	/**
	 * 评估点地市对比
	 * @author Huangsisi
	 * @param healthtype
	 * @return
	 */
	public List<AssDimRelevance> getCityContrast(String healthtype) {
		List<AssDimRelevance> list=new ArrayList<AssDimRelevance>();
		try {
			list = healthservice.getCityContrast(healthtype);
		} catch (Exception e) {
			logger.error(e.getMessage() + "评估点地市对比：" + list);
			e.printStackTrace();
		}
		return list;
		
	}

	/**
	 * 评估点地市对比子节点
	 * @author Huangsisi 
	 * @param fatherId
	 * @return
	 */
	public	List<AssDimRelevance> getCityContrastChild(String fatherId){
		List<AssDimRelevance> list=new ArrayList<AssDimRelevance>();
		try {
			list = healthservice.getCityContrastChild(fatherId);
		} catch (Exception e) {
			logger.error(e.getMessage() + "评估点地市对比子节点：" + list);
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 全省纵览
	 * @author Huangsisi
	 * @param healthType
	 * @param getTime
	 * @param timeType
	 * @return
	 */
	public 	List<Map<String, String>> getprovinceView(String healthType, String getTime, String timeType) {
		List<Map<String, String>> list=new ArrayList<Map<String, String>>();
		try {
			list = healthservice.getprovinceView(healthType, getTime, timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "全省纵览：" + list);
			e.printStackTrace();
		}
		return list;
		
	}
	/**
	 * 竟争对手对比
	 * @author Huangsisi
	 * @param getTime
	 * @param timeType
	 * @return
	 */
	public 	List<Map<String,String>>getOperView(String getTime, String timeType){
		List<Map<String, String>> list=new ArrayList<Map<String, String>>();
		try {
			list = healthservice.getOperView(getTime, timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "竟争对手对比：" + list);
			e.printStackTrace();
		}
		return list;
		
	}

	/**
	 * 评估点地市对比charts
	 * @author Yuanxihua
	 * @param healthname
	 * @param healthtype
	 * @param getTime
	 * @param timeType
	 * @return
	 */
	public List<Map<String,String>>getCityConstrastCharts(String healthname,String healthtype,String getTime, String timeType){
		List<Map<String, String>> list=new ArrayList<Map<String, String>>();
		try {
			list = healthservice.getCityConstrastCharts(healthname,healthtype,getTime, timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "评估点地市对比：" + list);
			e.printStackTrace();
		}
		return list;
	}

    /**
     * 健康度表格数据Excel导出
     * @author Yuanxihua
     * @param healthType
     * @param getTime
     * @return
     */
    public byte[] healthExcelTable(String healthType, String getTime, String timeType){
    	byte[] bt = null;
    	try {
			bt = healthservice.healthExcelTable(healthType, getTime, timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "健康度表格数据Excel导出：" + bt);
			e.printStackTrace();
		}
    	return bt;
    }
    
    /**
     * 全省网络运行情况简报日报word导出
     * @author Zouhaibo
     * @param healthType
     * @param getTime
     * @return
     */
    public byte[] healthWordTable(String getTime){
    	logger.debug("开始生成WORD文档");
    	System.out.println("开始生成WORD文档");
    	byte[] bt = null;
    	try {
			bt = healthservice.healthWordTable(getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "健康度word报表导出：" + bt);
			e.printStackTrace();
		}
    	return bt;
    }
	
  	/**
  	 * 根据时间粒度、健康度指标ID、健康度类型查询各地市健康度指标名称和分数
  	 * @author Yuanxihua
  	 * @param timeType
  	 * @param healthType
  	 * @param healthId
  	 * @param getTime
  	 * @return
  	 */
  	public List<Map<String, Object>> findHealthNameScoreByTime(String timeType, String healthType, String healthId, int lev, String getTime){
  		List<Map<String, Object>> list = null;
  		try {
			list = healthservice.findHealthNameScoreByTime(timeType, healthType, healthId, lev, getTime);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据时间粒度、健康度指标ID、健康度类型查询各地市健康度指标名称和分数：" + list);
			e.printStackTrace();
		}
  		return list;
  	}
  	
	/**
	 * 根据健康度类型、健康度指标ID、时间粒度查询健康度分数和指标值
	 * @param healthType
	 * @param healthId
	 * @param getTime
	 * @param timeType
	 * @param num
	 * @return
	 */
	public List<Map<String, Object>> findHealthScoreKpiByType(String healthType, String healthId, String getTime, String timeType, String num){
		List<Map<String, Object>> list = null;
		try {
			list = healthservice.findHealthScoreKpiByType(healthType, healthId, getTime, timeType, num);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据时间粒度、健康度指标ID、健康度类型查询健康度分数和指标值: " + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据健康度指标ID、时间、时间粒度 查询指标详情 TOP7 charts
	 * @param kpiId
	 * @param getTime
	 * @param timeType
	 * @return
	 */
	public List<Map<String,String>>getKPIDetailTop7(String kpiId,String getTime, String timeType){
		List<Map<String, String>> list=new ArrayList<Map<String, String>>();
		try {
			list = healthservice.getKPIDetailTop7(kpiId,getTime, timeType);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据健康度指标ID、时间、时间粒度 查询指标详情 TOP7 charts：" + list);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据健康度类型、健康度指标ID、时间粒度、开始时间、结束时间分页查询自定义表格
	 * @param healthType
	 * @param kpiId
	 * @param timeType
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	public Page getUserDefinedInfo(String healthType, String kpiId,String timeType, String starttime,
			String endtime,int pageSize, int pageNo) {
		try {
			return healthservice.getUserDefinedInfo(healthType,kpiId,timeType, starttime, endtime, pageSize, pageNo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查找自定义出错！");
			return null;
		}
	}
	/**
	 * 根据健康度类型、健康度指标ID、时间粒度、开始时间、结束时间分页查询网间出口健康度明细
	 * @param healthType
	 * @param kpiId
	 * @param timeType
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	public Page getExitHealthDetail(String healthType, String kpiId,String timeType, String starttime,
			String endtime,int pageSize, int pageNo) {
		Page page = null;
		try {
			page = healthservice.getExitHealthDetail(healthType,kpiId,timeType, starttime, endtime, pageSize, pageNo);
		} catch (Exception e) {
			logger.error(e.getMessage() + "根据健康度类型、健康度指标ID、时间粒度、开始时间、结束时间分页查询网间出口健康度明细：" + page);
			e.printStackTrace();
		}
		return page;
	}
}
