package com.ultrapower.flowmanage.modules.internetResources.service;

import java.util.List;
import java.util.Map;

import com.ultrapower.flowmanage.common.vo.Page;


public interface InternetResourcesService {
	//查询TOP10网站域名总数统计
	Map<String, Object> findTop10WebDomainCount() throws Exception;
	//查询其他网站域名总数统计
	List<Map<String, String>> findOtherWebDomainCount() throws Exception;
	//根据网站名称查询运营商分布统计情况
	Map<String, Object> findOperatorDistributionCountBywebName(String webName) throws Exception;
	//根据网站名称和运营商查询区域分布统计情况
	Map<String, Object> findDistrictDistributionBywebNameOperator(String webName, String operator) throws Exception;
	//根据网站名称和运营商查询运营商维度明细统计列表
	Page findOperatorDetailBywebNameOperatorTable(String webName, String operator, int pageNo, int pageSize) throws Exception;
	//根据网站名称和运营商、区域查询区域维度明细统计列表
	Page findDistrictwebNameOperatorAreaTable(String webName, String operator, String area, int pageNo, int pageSize) throws Exception;
	//根据网站名称、域名、IP地址、归属区域查询域名库列表
	Page findDomainLibraryTable(String webName, String domainName, String ip, String district, int pageNo, int pageSize) throws Exception;
	//根据网站名称、域名、IP地址、归属区域导出Excel表格
	byte[] exportDomainLibraryExcelTable(String webName, String domainName, String ip, String district) throws Exception;
	
	//上传资源库文件
	boolean upload(byte[] data, String type, String filesuffix) throws Exception;
	

	//查询业务总体统计
	Map<String, Object> findBusinessTotalCount() throws Exception;
	//根据业务大类名称查询业务明细统计
	Map<String, Object> findBusinessDetailsCountByBizName(String bizName) throws Exception;
	//根据业务ID查询业务详细信息
	Map<String, Object> findBusinessDetailsInfoByKid(String kid) throws Exception;
}
