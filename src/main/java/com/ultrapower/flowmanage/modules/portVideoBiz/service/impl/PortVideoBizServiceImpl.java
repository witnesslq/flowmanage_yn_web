package com.ultrapower.flowmanage.modules.portVideoBiz.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ultrapower.flowmanage.common.utils.Common;
import com.ultrapower.flowmanage.common.utils.DateUtil;
import com.ultrapower.flowmanage.common.utils.FlowTree;
import com.ultrapower.flowmanage.common.utils.Utils;
import com.ultrapower.flowmanage.common.vo.Tree;
import com.ultrapower.flowmanage.modules.portVideoBiz.dao.PortVideoBizDao;
import com.ultrapower.flowmanage.modules.portVideoBiz.service.PortVideoBizService;
import com.ultrapower.flowmanage.modules.xdrAnalyse.dao.XdrAnalyseDao;

@Service("portVideoBizService")
public class PortVideoBizServiceImpl implements PortVideoBizService {
	private static Logger logger = Logger.getLogger(PortVideoBizServiceImpl.class);
	@Resource(name="portVideoBizDao")
	private PortVideoBizDao portVideoBizDao;
	@Resource(name="xdrAnalyseDao")
	private XdrAnalyseDao xdrAnalyseDao;
	
	public List<Map<String, String>> findBizNetQualityViewByTime(String viewType, String timeType, String getTime) throws Exception {
		Map<String, Date> dateMap = DateUtil.getDateByType(timeType, getTime);
		List<Map<String, String>> list = portVideoBizDao.findBizNetQualityViewByTime(viewType, timeType, dateMap.get("startTime"), dateMap.get("endTime"));
		return list;
	}

	public List<Map<String, String>> findBizNetQualityCityContrastViewByTime(String viewType, String timeType, String getTime) throws Exception {
		Map<String, Date> dateMap = DateUtil.getDateByType(timeType, getTime);
		List<Map<String, String>> list = portVideoBizDao.findBizNetQualityCityContrastViewByTime(viewType, timeType, dateMap.get("startTime"), dateMap.get("endTime"));
		for (Map<String, String> map : list) {
			if("0".equals(String.valueOf(map.get("score")))){
				map.put("score", "—");
			}
		}
		return list;
	}

	public List<Map<String, String>> findBizNetQualityVideoWebTop3ByTime(String viewType, String timeType, String getTime, String sort) throws Exception {
		Map<String, Date> dateMap = DateUtil.getDateByType(timeType, getTime);
		List<Map<String, String>> list = portVideoBizDao.findBizNetQualityVideoWebTop3ByTime(viewType, timeType, dateMap.get("startTime"), dateMap.get("endTime"), sort);
		return list;
	}

	public List<Map<String, String>> findBizNetQCMicroViewByTime(String viewType, String timeType, int cityId, String getTime) throws Exception {
		List<Map<String, String>> cityList = xdrAnalyseDao.findDICInfo("VIDEOBIZ_VIDEONET", null);
		Map<String, Date> dateMap = DateUtil.getDateByType(timeType, getTime);
		List<Map<String, String>> list = portVideoBizDao.findBizNetQCMicroViewByTime(viewType, timeType, cityId, dateMap.get("startTime"), dateMap.get("endTime"));
		for (Map<String, String> cityMap : cityList) {
			int i = 0;
			for (Map<String, String> map : list) {
				if(cityMap.get("name").equals(map.get("webName"))){
					cityMap.put(Common.operatorMap.get(map.get("operator"))+"Value", String.valueOf(map.get("score")));
					i++;
				}
				if(i==3) 
					break;
			}
		}
		return cityList;
	}

	
	public List<Map<String, String>> findVideoWebProblemNumberByTime(String timeType, String cityId, String operator, String webId, String getTime) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("标清", "bq");
		map.put("高清", "gq");
		List<Map<String, String>> kpiList = xdrAnalyseDao.findDICInfo("VIDEOBIZ_BIZPROBLEM_KPI", null);
		Map<String, Date> dateMap = DateUtil.getDateByType(timeType, getTime);
		List<Map<String, String>> list = portVideoBizDao.findVideoWebProblemNumberByTime(timeType, cityId, operator, webId, dateMap.get("startTime"), dateMap.get("endTime"));
		for (Map<String, String> kpiMap : kpiList) {
			int i = 0;
			for (Map<String, String> mp : list) {
				if(kpiMap.get("name").equals(mp.get("kpiName"))){
					kpiMap.put(map.get(mp.get("definition"))+"Value", String.valueOf(mp.get("problemnumber")));
					i++;
				}
				if(i==2) 
					break;
			}
		}
		return kpiList;
	}

	public List<Map<String, String>> findNetPoorQualityByTime(String timeType,String getTime) throws Exception {
		Map<String, Date> dateMap = DateUtil.getDateByType(timeType, getTime);
		List<Map<String, String>> list = portVideoBizDao.findNetPoorQualityByTime(timeType, dateMap.get("startTime"), dateMap.get("endTime"));
		return list;
	}

	public Map<String, Object> findBizQualityTableByTime(String timeType, String cityId, String operator, String getTime) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> biList = new ArrayList<Map<String,String>>();
		List<Map<String, String>> partList = new ArrayList<Map<String,String>>();
		List<Map<String, String>> clearList = new ArrayList<Map<String,String>>();
		Map<String, String> biMap = null, partMap = null, clearMap = null;
		Map<String, Date> dateMap = DateUtil.getDateByType(timeType, getTime);
		List<Map<String, String>> list = portVideoBizDao.findBizQualityPaphByTime(timeType, cityId, operator, dateMap.get("startTime"), dateMap.get("endTime"));
		if(list.size() > 0){
			for (Map<String, String> mp : list) {
				Iterator<Entry<String, String>> iterator = mp.entrySet().iterator();
				biMap = new HashMap<String, String>();
				partMap = new HashMap<String, String>();
				clearMap = new HashMap<String, String>();
				while(iterator.hasNext()){
					Map.Entry<String, String> entry = (Entry<String, String>)iterator.next();
					if(entry.getKey().equals("webName") || entry.getKey().equals("belongAddr")){
						biMap.put(entry.getKey(), entry.getValue());
						partMap.put(entry.getKey(), entry.getValue());
						clearMap.put(entry.getKey(), entry.getValue());
					}
					if(entry.getKey().indexOf("bi") > -1){
						biMap.put(entry.getKey(), String.valueOf(entry.getValue()));
					}
					if(entry.getKey().indexOf("part") > -1){
						partMap.put(entry.getKey(), String.valueOf(entry.getValue()));
					}
					if(entry.getKey().indexOf("clear") > -1){
						clearMap.put(entry.getKey(), String.valueOf(entry.getValue()));
					}
				}
				biMap.put("label", "("+biMap.get("webName")+")"+biMap.get("belongAddr"));
				partMap.put("label", "("+partMap.get("webName")+")"+partMap.get("belongAddr"));
				clearMap.put("label", "("+clearMap.get("webName")+")"+clearMap.get("belongAddr"));
				biList.add(biMap);
				partList.add(partMap);
				clearList.add(clearMap);
			}
		}
		map.put("bi", biList);//闲忙时质量
		map.put("part", partList);//局向质量
		map.put("clear", clearList);//清晰度质量
		return map;
	}

	public Map<String, Object> findNetQualityProblemCountByTime(String timeType, String getTime) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> tbList = new ArrayList<Map<String,String>>();
		List<Map<String, String>> chartList = new ArrayList<Map<String,String>>();
		Map<String, String> tbMap = null, chartMap = null;
		Map<String, Date> dateMap = DateUtil.getDateByType(timeType, getTime);
		List<Map<String, String>> list = portVideoBizDao.findNetQualityProblemCountByTime(timeType, dateMap.get("startTime"), dateMap.get("endTime"));
		for (String netqc : Common.netQualityName) {
			if(list == null) break;
			int i = 0, total = 0;
			tbMap = new HashMap<String, String>();
			tbMap.put("kpiName", netqc);
			for (Map<String, String> mp : list) {
				if(netqc.equals(mp.get("kpiName"))){
					tbMap.put(Common.operatorMap.get(mp.get("operator"))+"Value", String.valueOf(mp.get("problemNumber")));
					tbMap.put(Common.operatorMap.get(mp.get("operator"))+"Chain", String.valueOf(mp.get("chain")));
					total += Integer.valueOf(String.valueOf(mp.get("problemNumber")));
					i++;
				}
				if(i==3){
					tbMap.put("total", String.valueOf(total));
					break;
				}
			}
			tbList.add(tbMap);
		}
		if(tbList.size() > 0){
			for (Map<String, String> mp : tbList) {
				Iterator<Entry<String, String>> iterator = mp.entrySet().iterator();
				chartMap = new HashMap<String, String>();
				chartMap.put("title", mp.get("kpiName"));
				while(iterator.hasNext()){
					Map.Entry<String, String> entry = (Entry<String, String>)iterator.next();
					if(entry.getKey().indexOf("Value") > -1){
						int perc = (int)(Utils.div(Double.valueOf(entry.getValue()), Double.valueOf(mp.get("total")), 2) * 100);
						chartMap.put(entry.getKey(), String.valueOf(perc));
					}
				}
				chartList.add(chartMap);
			}
		}
		map.put("table", tbList);//表格
		map.put("chart", chartList);//图形
		return map;
	}

	public List<Map<String, String>> findNetQualityMicroByTime(String timeType, String cityId, String operator, String webId, String score, String getTime) throws Exception {
		List<Map<String, String>> lists = new ArrayList<Map<String,String>>();
		Map<String, String> mp = null;
		Map<String, Date> dateMap = DateUtil.getDateByType(timeType, getTime);
		List<Map<String, String>> kpiList = portVideoBizDao.findNetQualityThreshold();
		List<Map<String, String>> list = portVideoBizDao.findNetQualityMicroByTime(timeType, cityId, operator, webId, dateMap.get("startTime"), dateMap.get("endTime"));
		for (Map<String, String> map : list) {
			if(score != null)
			map.put("webName", map.get("webName")+"("+score+")");
			mp = new HashMap<String, String>();
			mp.putAll(map);
			Iterator<Entry<String, String>> kpiIterator = this.kpiMap.entrySet().iterator();
			while(kpiIterator.hasNext()){
				Map.Entry<String, String> kpiEntry = (Map.Entry<String, String>)kpiIterator.next();
				map.put(kpiEntry.getKey(), kpiEntry.getValue());
				for (Map<String, String> kpiMap : kpiList) {
					if(map.get("goalAddr").equals(kpiMap.get("busiName"))
					   && map.get(kpiEntry.getKey()).equals(kpiMap.get("kpiName"))){
					   map.put(kpiEntry.getKey(), kpiEntry.getValue()+""+kpiMap.get("value"));
					   break;
					}
				}
			}
			lists.add(map);
			lists.add(mp);
		}
		return lists;
	}

	@SuppressWarnings("serial")
	public  Map<String, String> kpiMap = new HashMap<String, String>() {
		{
			put("globalScore","总体得分");
			put("netdelay","网络时延");
			put("netdelayScore","网络时延得分");
			put("netshake","网络抖动");
			put("netshakeScore","网络抖动得分");
			put("netpkloss","网络丢包");
			put("netpklossScore","网络丢包得分");
		}
	};

	public List<Map<String, String>> findNetQualityPoorVideoByTime(String viewType, String timeType, String cityId, String operator, String getTime) throws Exception {
		Map<String, Date> dateMap = DateUtil.getDateByType(timeType, getTime);
		List<Map<String, String>> list = portVideoBizDao.findNetQualityPoorVideoByTime(viewType, timeType, cityId, operator, dateMap.get("startTime"), dateMap.get("endTime"));
		return list;
	}

	public String findPartDirectionQualityTopo(String timeType, String cityId, String webId, String operator, String getTime,String layout) throws Exception {
		String xml = "", maxLevel = "0";
		Map<String, Date> dateMap = DateUtil.getDateByType(timeType, getTime);
		List<Map<String, String>> lists = portVideoBizDao.findPartDirectionQualityByTime(timeType, cityId, webId, operator, dateMap.get("startTime"), dateMap.get("endTime"));
		if(lists != null){
			List<Tree> list = this.getTree(lists);
			//临时map做树的列表存储, key是树的唯一id,value是树
			Map<String, Tree> treeMap = new HashMap<String, Tree>(list.size());
			for (Tree tempTree : list) {
				treeMap.put(tempTree.getId(), tempTree);
			}
			//做整棵树的封装
			Tree rootTree = FlowTree.buildTree(treeMap);
			
			maxLevel = FlowTree.getMaxlevel(list);
//			xml = FlowTree.createXml(rootTree, null, maxLevel);
			xml = FlowTree.createXmlWithLayout(rootTree, null, layout);
			/*
			for (Tree t : list) {
				System.out.println("id："+t.getId()+"  name："+t.getName()+"  parentid："+t.getParentid()+"  leve："+t.getLev()+"  score："+t.getScore()+"  value："+t.getVal()+"  unit："+t.getUnit());
			}
			*/
		}
		return xml;
	}

	public List<Tree> getTree(List<Map<String, String>> list){
		Map<String, String> kpiMap = new HashMap<String, String>();
		kpiMap.put("partquFlowWeight", "流量权重");
		kpiMap.put("partquScore", "得分");
		List<Tree> lists = new ArrayList<Tree>();
		Tree tree = null;
		int kpiId = 0;
		for (int i = 0; i < list.size(); i++) {
			Map<String, String> map = list.get(i);
			if(i==0){//一级菜单
				tree = new Tree();
				tree.setId(String.valueOf(map.get("webId")));
				tree.setParentid("-1");
				tree.setName(map.get("webName"));
				tree.setLev("1");
				tree.setScore(String.valueOf(map.get("partquGlobalScore")));
				lists.add(tree);
				kpiId = Integer.valueOf(String.valueOf(map.get("webId")));
			}
			//二级菜单
			tree = new Tree();
			tree.setId(String.valueOf(map.get("id")));
			tree.setParentid(String.valueOf(map.get("webId")));
			tree.setName(map.get("belongAddr"));
			tree.setLev("2");
			lists.add(tree);
			
			//三级菜单(流量权重)
			kpiId += 1;
			tree = new Tree();
			tree.setId(String.valueOf(kpiId));
			tree.setParentid(String.valueOf(map.get("id")));
			tree.setName(kpiMap.get("partquFlowWeight"));
			tree.setVal(String.valueOf(map.get("partquFlowWeight")));
			tree.setLev("3");
			tree.setUnit("%");
			lists.add(tree);
			
			//三级菜单(得分)
			tree = new Tree();
			kpiId += 1;
			tree.setId(String.valueOf(kpiId));
			tree.setParentid(String.valueOf(map.get("id")));
			tree.setName(kpiMap.get("partquScore"));
			tree.setScore(String.valueOf(map.get("partquScore")));
			tree.setLev("3");
			lists.add(tree);
		}
		return lists;
	}
}
