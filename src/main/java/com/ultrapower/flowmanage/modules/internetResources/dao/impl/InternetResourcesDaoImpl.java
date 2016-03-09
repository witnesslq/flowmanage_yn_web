package com.ultrapower.flowmanage.modules.internetResources.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.annotation.Resource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ultrapower.flowmanage.modules.internetResources.dao.InternetResourcesDao;
import com.ultrapower.flowmanage.modules.internetResources.dao.mapper.InternetResourcesMapper;
import com.ultrapower.flowmanage.modules.internetResources.vo.FmDomainRepository;
import com.ultrapower.flowmanage.modules.internetResources.vo.FmIpRange;
import com.ultrapower.flowmanage.modules.internetResources.vo.FmKnowledge;
import com.ultrapower.flowmanage.modules.internetResources.vo.NetResDns;
@Service("internetResourcesDao")
public class InternetResourcesDaoImpl implements InternetResourcesDao {
	private static Logger logger = Logger.getLogger(InternetResourcesDaoImpl.class);
	@Resource(name="internetResourcesMapper")
	private InternetResourcesMapper internetResourcesMapper;

	@Autowired
	private SqlSessionFactoryBean sessionFactory;
	
	//查询TOP10网站域名总数统计 
	public List<Map<String, String>> findTop10WebDomainCount() throws Exception {
		return internetResourcesMapper.findTop10WebDomainCount();
	}

	//查询其他网站域名总数统计 
	public List<Map<String, String>> findOtherWebDomainCount() throws Exception {
		return internetResourcesMapper.findOtherWebDomainCount();
	}

	//根据网站名称查询运营商分布统计情况
	public List<Map<String, String>> findOperatorDistributionCountBywebName(String webName)
			throws Exception {
		return internetResourcesMapper.findOperatorDistributionCountBywebName(webName);
	}

	//根据网站名称和运营商查询区域分布统计情况
	public List<Map<String, String>> findDistrictDistributionBywebNameOperator(String webName, String operator)
			throws Exception {
		return internetResourcesMapper.findDistrictDistributionBywebNameOperator(webName, operator);
	}

	//根据网站名称和运营商查询运营商维度明细统计列表记录数
	public int findOperatorDetailBywebNameOperatorRowCount(String webName, String operator) throws Exception{
		return internetResourcesMapper.findOperatorDetailBywebNameOperatorRowCount(webName, operator);
	}

	//根据网站名称和运营商查询运营商维度明细统计列表
	public List<Map<String, String>> findOperatorDetailBywebNameOperatorTable(String webName, String operator, int pageNo, int pageSize) throws Exception{
		return internetResourcesMapper.findOperatorDetailBywebNameOperatorTable(webName, operator, pageNo, pageSize);
	}

	//根据网站名称和运营商、区域查询区域维度明细统计列表记录数
	public int findDistrictwebNameOperatorAreaRowCount(String webName, String operator, String area) throws Exception{
		return internetResourcesMapper.findDistrictwebNameOperatorAreaRowCount(webName, operator, area);
	}
	
	//根据网站名称和运营商、区域查询区域维度明细统计列表
	public List<Map<String, String>> findDistrictwebNameOperatorAreaTable(String webName, String operator, String area, int pageNo, int pageSize) throws Exception{
		return internetResourcesMapper.findDistrictwebNameOperatorAreaTable(webName, operator, area, pageNo, pageSize);
	}
	
	//根据网站名称、域名、IP地址、归属区域查询域名库列表记录数
	public int findDomainLibraryRowCount(String webName, String domainName, String ip, String district) throws Exception{
		return internetResourcesMapper.findDomainLibraryRowCount(webName, domainName, ip, district);
	}
		
	//根据网站名称、域名、IP地址、归属区域查询域名库列表
	public List<Map<String, String>> findDomainLibraryTable(String webName, String domainName, String ip, String district, int pageNo, int pageSize) throws Exception{
		return internetResourcesMapper.findDomainLibraryTable(webName, domainName, ip, district, pageNo, pageSize);
	}

	//根据网站名称、域名、IP地址、归属区域查询域名库列表Excel
	public Vector findDomainLibraryTableExcel(String webName,String domainName, String ip, String district) throws Exception {
		Vector vect = new Vector();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT WEBNAME,DOMAIN_NAME DOMAINNAME,IP,BELONG_ADDRESS BELONGADDRESS FROM T_NETRES_DNS40W"
				  +" WHERE 1 = 1 ");
		if(webName != null && !"".equals(webName)){
			sb.append(" AND WEBNAME LIKE '%"+webName+"%'");
		}
		if(domainName != null && !"".equals(domainName)){
			sb.append(" AND DOMAIN_NAME LIKE '%"+domainName+"%'");
		}
		if(ip != null && !"".equals(ip)){
			sb.append(" AND IP LIKE '%"+ip+"%'");
		}
		if(district != null && !"".equals(district)){
			sb.append(" AND BELONG_ADDRESS LIKE '%"+district+"%' ");
		}
		SqlSession session = sessionFactory.getObject().openSession(ExecutorType.BATCH, true);
		Connection cnn = session.getConnection();
		Statement st = cnn.createStatement();
		ResultSet rs = st.executeQuery( sb.toString());
	    int collength = 0;
	    while (rs.next()) {
	     collength = rs.getMetaData().getColumnCount();
	     String[] rowdata = new String[collength];
	     for (int k = 1; k <= collength; k++) {
	      rowdata[k - 1] = rs.getString(k);
	     }
	     vect.add(rowdata);
	    }
	    rs.close();
	    st.close();
	    cnn.close();

		return vect;
	}
	

	//插入域名库数据
	public void insertDomainRespository(FmDomainRepository fmDomainRepository) throws Exception {
		internetResourcesMapper.insertDomainRespository(fmDomainRepository);
	}
	//删除域名库数据
	public void deleteDomainRespository() throws Exception {
		internetResourcesMapper.deleteDomainRespository();
	}
	
	//删除域名库前台数据
	public void deleteNetResDns() throws Exception {
		internetResourcesMapper.deleteNetResDns();
	}
	//重置sequence为1
	public void resetSeq(Map para) throws Exception {
		internetResourcesMapper.resetSeq(para);
	}
	//查找IP范围规则
	public List<FmIpRange> getIpRange() throws Exception
	{
		return internetResourcesMapper.getIpRange();
	}
	
	//插入域名库前台数据
	public void insertNetResDns(NetResDns netResDns) throws Exception
	{
		internetResourcesMapper.insertNetResDns(netResDns);
	}
	//返回知识百科当前序列号
	public int getKnowledgeKid() throws Exception
	{
		return internetResourcesMapper.getKnowledgeKid();
	}

	//删除知识百科数据
	public void deleteKnowledge() throws Exception
	{
		internetResourcesMapper.deleteKnowledge();
	}
	
	//删除知识百科前台数据
	public void deleteNetResKnowledge() throws Exception
	{
		internetResourcesMapper.deleteNetResKnowledge();
	}
	
	//删除知识百科详细前台数据
	public void deleteNetResKnowledgeDetail() throws Exception
	{
		internetResourcesMapper.deleteNetResKnowledgeDetail();
	}
	
	//插入知识百科数据
	public void insertKnowledge(FmKnowledge fmKnowledge) throws Exception
	{
		internetResourcesMapper.insertKnowledge(fmKnowledge);
	}
	//插入知识百科前台数据
	public void insertNetResKnowledge(FmKnowledge fmKnowledge) throws Exception
	{
		internetResourcesMapper.insertNetResKnowledge(fmKnowledge);
	}
	//插入知识百科详细前台数据
	public void insertNetResKnowledgeDetail(FmKnowledge fmKnowledge) throws Exception
	{
		internetResourcesMapper.insertNetResKnowledgeDetail(fmKnowledge);
	}


	//查询业务总体统计
	public List<Map<String, String>> findBusinessTotalCount() throws Exception{
		return internetResourcesMapper.findBusinessTotalCount();
	}
	
	//根据业务大类名称查询业务明细统计
	public List<Map<String, String>> findBusinessDetailsCountByBizName(String bizName) throws Exception{
		return internetResourcesMapper.findBusinessDetailsCountByBizName(bizName);
	}
	
	//根据业务ID查询业务基础信息
	public Map<String, Object> findBusinessTotalInfoByKid(String kid) throws Exception{
		return internetResourcesMapper.findBusinessTotalInfoByKid(kid);
	}
	//根据业务ID查询业务详细信息
	public List<Map<String, String>> findBusinessDetailsInfoByKid(String kid) throws Exception{
		return internetResourcesMapper.findBusinessDetailsInfoByKid(kid);
	}
}
