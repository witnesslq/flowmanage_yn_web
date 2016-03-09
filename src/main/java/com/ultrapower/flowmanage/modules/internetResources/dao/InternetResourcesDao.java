package com.ultrapower.flowmanage.modules.internetResources.dao;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.ibatis.annotations.Param;

import com.ultrapower.flowmanage.modules.internetResources.vo.FmDomainRepository;
import com.ultrapower.flowmanage.modules.internetResources.vo.FmIpRange;
import com.ultrapower.flowmanage.modules.internetResources.vo.FmKnowledge;
import com.ultrapower.flowmanage.modules.internetResources.vo.NetResDns;

public interface InternetResourcesDao {
	//查询TOP10网站域名总数统计
	List<Map<String, String>> findTop10WebDomainCount() throws Exception;
	//查询其他网站域名总数统计
	List<Map<String, String>> findOtherWebDomainCount() throws Exception;
	//根据网站名称查询运营商分布统计情况
	List<Map<String, String>> findOperatorDistributionCountBywebName(String webName) throws Exception;
	//根据网站名称和运营商查询区域分布统计情况
	List<Map<String, String>> findDistrictDistributionBywebNameOperator(String webName, String operator) throws Exception;
	//根据网站名称和运营商查询运营商维度明细统计列表记录数
	int findOperatorDetailBywebNameOperatorRowCount(String webName, String operator) throws Exception;
	//根据网站名称和运营商查询运营商维度明细统计列表
	List<Map<String, String>> findOperatorDetailBywebNameOperatorTable(String webName, String operator, int pageNo, int pageSize) throws Exception;
	//根据网站名称和运营商、区域查询区域维度明细统计列表记录数
	int findDistrictwebNameOperatorAreaRowCount(String webName, String operator, String area) throws Exception;
	//根据网站名称和运营商、区域查询区域维度明细统计列表
	List<Map<String, String>> findDistrictwebNameOperatorAreaTable(String webName, String operator, String area, int pageNo, int pageSize) throws Exception;
	//根据网站名称、域名、IP地址、归属区域查询域名库列表记录数
	int findDomainLibraryRowCount(String webName, String domainName, String ip, String district) throws Exception;
	//根据网站名称、域名、IP地址、归属区域查询域名库列表
	List<Map<String, String>> findDomainLibraryTable(String webName, String domainName, String ip, String district, int pageNo, int pageSize) throws Exception;
	//根据网站名称、域名、IP地址、归属区域查询域名库列表Excel
	Vector findDomainLibraryTableExcel(String webName, String domainName, String ip, String district) throws Exception;

	//插入域名库数据
	void insertDomainRespository(FmDomainRepository fmDomainRepository) throws Exception;
	//删除域名库数据
	void deleteDomainRespository() throws Exception;
	//删除域名库前台数据
	void deleteNetResDns() throws Exception;
	//重置sequence为1
	void resetSeq(Map para) throws Exception;
	//查找IP范围规则
	List<FmIpRange> getIpRange() throws Exception;
	//插入域名库前台数据
	void insertNetResDns(NetResDns netResDns) throws Exception;
	//返回知识百科当前序列号
	int getKnowledgeKid() throws Exception;
	//删除知识百科数据
	void deleteKnowledge() throws Exception;
	//删除知识百科前台数据
	void deleteNetResKnowledge() throws Exception;
	//删除知识百科详细前台数据
	void deleteNetResKnowledgeDetail() throws Exception;
	//插入知识百科数据
	void insertKnowledge(FmKnowledge fmKnowledge) throws Exception;
	//插入知识百科前台数据
	void insertNetResKnowledge(FmKnowledge fmKnowledge) throws Exception;
	//插入知识百科详细前台数据
	void insertNetResKnowledgeDetail(FmKnowledge fmKnowledge) throws Exception;


	//查询业务总体统计
	List<Map<String, String>> findBusinessTotalCount() throws Exception;
	//根据业务大类名称查询业务明细统计
	List<Map<String, String>> findBusinessDetailsCountByBizName(String bizName) throws Exception;
	
	//根据业务ID查询业务基础信息
	Map<String, Object> findBusinessTotalInfoByKid(String kid) throws Exception;
	//根据业务ID查询业务详细信息
	List<Map<String, String>> findBusinessDetailsInfoByKid(String kid) throws Exception;
}
