package com.ultrapower.flowmanage.modules.health.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ultrapower.flowmanage.common.vo.Page;
import com.ultrapower.flowmanage.common.vo.Tree;
import com.ultrapower.flowmanage.modules.flow.vo.FsChartVo;
import com.ultrapower.flowmanage.modules.health.vo.AssDimRelevance;
import com.ultrapower.flowmanage.modules.health.vo.CityContrastVo;
import com.ultrapower.flowmanage.modules.health.vo.CountryContrastVo;
import com.ultrapower.flowmanage.modules.health.vo.Emp;

public interface HealthService {
	List<Emp> findEmp() throws Exception;
	List<Emp> findEmpByEmpno(String empno) throws Exception;
	List<Map<String, String>> findThreeNetHealth(String healthtype, String getTime, String timeType) throws Exception;
	List<CityContrastVo> findCityHealthScoreById(String healthId, String healthType, String getTime, String timeType) throws Exception;
	List<Map<String, Object>> findHealthNameAndScoreById(String healthId, String healthtype, String getTime, String timeType) throws Exception;
	List<CityContrastVo> findCityInfo() throws Exception;
	List<CityContrastVo> findHealthNameByType(String healthType) throws Exception;
	List<Map<String, Object>> findHealthNameScoreByHealthId(String healthId, String healthType, String getTime, String timeType) throws Exception;
	
	/** 创建健康树 created by zhengWei */
	public Tree buildMenuTree(String type,String lev) throws Exception;
	
	/** 获取健康度列表基本表信息  created by zhengWei */
	List<Tree> getHealthTable(String type, String area, String city, Date starttime, Date endtime, String timeType) throws Exception;
	
	/** 创建健康树  created by zhengWei */
	Tree buildHealthTree(String type, String area, String city, String getTime, String timeType) throws Exception;
	
	/** 根据健康树创建拓补xml created by zhengWei */
	String createTopo(String type, String area, String city,  String getTime, String timeType) throws Exception;
	//全国对比
		List<CountryContrastVo> getCountryConstrastList(String healthtype,String getTime, String timeType)throws Exception;
		//评估点地市对比
		List<AssDimRelevance> getCityContrast(String healthtype)throws Exception;
		//评估点地市对比子节点
		List<AssDimRelevance> getCityContrastChild(String fatherId)throws Exception;
		//全省纵览
		List<Map<String, String>> getprovinceView(String healthType, String getTime, String timeType)throws Exception;
		//竟争对手对比
		List<Map<String,String>> getOperView(String getTime, String timeType)throws Exception;
		//评估点地市对比charts
		 List<Map<String,String>> getCityConstrastCharts(String healthname,String healthtype, String getTime, String timeType)throws Exception;
    //健康度表格数据Excel导出
	byte[] healthExcelTable(String healthType, String getTime, String timeType) throws Exception;
	
	
	//健康度word报表导出，zouhaibo
	byte[] healthWordTable(String getTime) throws Exception;
	
	//根据时间粒度、健康度指标ID、健康度类型查询各地市健康度指标名称和分数
	List<Map<String, Object>> findHealthNameScoreByTime(String timeType, String healthType, String healthId, int lev, String getTime) throws Exception; 

	//根据时间粒度、健康度指标ID、健康度类型查询健康度分数和指标值
	List<Map<String, Object>> findHealthScoreKpiByType(String healthType, String healthId, String getTime, String timeType, String num) throws Exception;

	//指标详情 TOP7 charts
	List<Map<String,String>> getKPIDetailTop7(String kpiId, String getTime, String timeType)throws Exception;
	//根据健康度类型、健康度指标ID、时间粒度、开始时间、结束时间分页查询自定义表格
	Page getUserDefinedInfo(String healthType, String kpiId, String timeType, String starttime, String endtime, int pageSize, int pageNo) throws ParseException;
	//根据健康度类型、健康度指标ID、时间粒度、开始时间、结束时间分页查询网间出口健康度明细
	Page getExitHealthDetail(String healthType, String kpiId, String timeType, String starttime, String endtime, int pageSize, int pageNo) throws ParseException;

}
