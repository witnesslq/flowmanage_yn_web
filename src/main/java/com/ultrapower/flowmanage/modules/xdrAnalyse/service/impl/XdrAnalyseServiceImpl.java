package com.ultrapower.flowmanage.modules.xdrAnalyse.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ultrapower.flowmanage.common.utils.Utils;
import com.ultrapower.flowmanage.common.vo.Page;
import com.ultrapower.flowmanage.modules.xdrAnalyse.dao.XdrAnalyseDao;
import com.ultrapower.flowmanage.modules.xdrAnalyse.service.XdrAnalyseService;

@Service("xdrAnalyseService")
public class XdrAnalyseServiceImpl implements XdrAnalyseService {
	private static Logger logger = Logger.getLogger(XdrAnalyseServiceImpl.class);
	@Resource(name="xdrAnalyseDao")
	private XdrAnalyseDao xdrAnalyseDao;
	
	//根据类型、上级ID查询ID和名称
	public List<Map<String, String>> findDICInfo(String objectType, String parentId) throws Exception {
		return xdrAnalyseDao.findDICInfo(objectType, parentId);
	}
	
	//根据区县名称查询网格编号
	public List<Map<String, String>> findWGNumberBydistrict(String district) throws Exception {
		return xdrAnalyseDao.findWGNumberBydistrict(district);
	}
	
	//根据时间、用户群ID、业务类型ID分页查询用户点击量信息列表
	public Page findUserClickRateInfoTable(String userGroupId, String busiTypeId, String time, int pageNo, int pageSize) throws Exception {
		Page page = new Page();
		int rowCount = xdrAnalyseDao.findUserClickRateInfoRowCount(userGroupId, busiTypeId, time);
		List<Map<String, String>> list = xdrAnalyseDao.findUserClickRateInfoTable(userGroupId, busiTypeId, time, pageNo, pageSize);
		for (Map<String, String> map : list) {
			map.put("provinceResPerc", Utils.valueOf(String.valueOf(map.get("provinceResPerc")))+"%");
			map.put("netinResPerc", Utils.valueOf(String.valueOf(map.get("netinResPerc")))+"%");
			map.put("crccPerc", Utils.valueOf(String.valueOf(map.get("crccPerc")))+"%");
			map.put("ctccPerc", Utils.valueOf(String.valueOf(map.get("ctccPerc")))+"%");
			map.put("cuccPerc", Utils.valueOf(String.valueOf(map.get("cuccPerc")))+"%");
			map.put("other_perc", Utils.valueOf(String.valueOf(map.get("other_perc")))+"%");
		}
		page.setPageNo(pageNo);//当前页
		page.setPageSize(pageSize);//每页的记录数
		page.setRowCount(rowCount);//总行数
		page.setResultList(list);//每页的记录
		return page;
	}

	//根据时间段、地市ID、城市ID、网格编号、用户群ID、业务类型ID查询点击率最高TOP15网站
	public List<Map<String, String>> findUserClickRateWebTOP15(String cityId,
			String countyId, String gridNumber, String userGroupId,
			String busiTypeId, String time) throws Exception {
		List<Map<String, String>> list = xdrAnalyseDao.findUserClickRateWebTOP15(cityId, countyId, gridNumber, userGroupId, busiTypeId, time);
		for (Map<String, String> map : list) {
			map.put("hits", Utils.valueOf(String.valueOf(map.get("hits")),"#,###"));
		}
		return list;
	}

	//根据时间、用户群ID、业务类型ID查询访问地市质量分析
	public List<Map<String, String>> findVisitQualityByCity(String userGroupId, String busiTypeId, String time) throws Exception{
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		list = xdrAnalyseDao.findVisitQualityByCity(userGroupId, busiTypeId, time);
		return list;
	}
	
	//根据时间、用户群ID、业务类型ID查询访问区县质量分析
	public List<Map<String, String>> findVisitQualityByCounty(String userGroupId, String busiTypeId, String cityId, String time) throws Exception{
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		list = xdrAnalyseDao.findVisitQualityByCounty(userGroupId, busiTypeId, cityId, time);
		return list;
	}
	
	//根据时间、用户群ID、业务类型ID分页查询网站质量分析列表
	public Page findSitesQualityTable(String userGroupId, String busiTypeId, String time, int pageNo, int pageSize) throws Exception{
		Page page = new Page();
		int rowCount = xdrAnalyseDao.findSitesQualityRowCount(userGroupId, busiTypeId, time);
		List<Map<String, String>> list = xdrAnalyseDao.findSitesQualityTable(userGroupId, busiTypeId, time, pageNo, pageSize);
		page.setPageNo(pageNo);// 当前页
		page.setPageSize(pageSize);// 每页的记录数
		page.setRowCount(rowCount);// 总行数
		page.setResultList(list);// 每页的记录
		return page;
	}
	
	//根据时间、用户群ID、业务类型ID分页查询网站质量分析列表
	public Page findSitesQualityWarningTable(String userGroupId, String busiTypeId, String time, int pageNo, int pageSize) throws Exception{
		Page page = new Page();
		int rowCount = xdrAnalyseDao.findSitesQualityWarningRowCount(userGroupId, busiTypeId, time, pageNo, pageSize);
		List<Map<String, String>> list = xdrAnalyseDao.findSitesQualityWarningTable(userGroupId, busiTypeId, time, pageNo, pageSize);
		page.setPageNo(pageNo);// 当前页
		page.setPageSize(pageSize);// 每页的记录数
		page.setRowCount(rowCount);// 总行数
		page.setResultList(list);// 每页的记录
		return page;
	}

}
