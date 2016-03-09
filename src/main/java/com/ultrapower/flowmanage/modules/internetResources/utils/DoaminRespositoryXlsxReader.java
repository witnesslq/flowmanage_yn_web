/**
 * 
 */
package com.ultrapower.flowmanage.modules.internetResources.utils;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionFactoryBean;

import com.ultrapower.flowmanage.common.utils.XxlsAbstract;
import com.ultrapower.flowmanage.modules.internetResources.dao.mapper.InternetResourcesMapper;
import com.ultrapower.flowmanage.modules.internetResources.vo.FmDomainRepository;
import com.ultrapower.flowmanage.modules.internetResources.vo.FmIpRange;
import com.ultrapower.flowmanage.modules.internetResources.vo.NetResDns;

/**
 * @author acer
 *
 */
public class DoaminRespositoryXlsxReader extends XxlsAbstract {
	private SqlSession session;
	private static final String REGX_IP = "((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)";
	private final static int DOMAIN_RESP_COL_NUM = 4;
	private int linenum = 0;
	private List<FmIpRange> ipRangeList;

	public DoaminRespositoryXlsxReader(SqlSession session, List<FmIpRange> ipRangeList) {
		this.session = session;
		this.linenum = 0;
		this.ipRangeList = ipRangeList;
	}

	/* (non-Javadoc)
	 * @see com.ultrapower.flowmanage.common.utils.XxlsAbstract#optRows(int, int, java.util.List)
	 */
	@Override
	public void optRows(int sheetIndex, int curRow, List<String> rowlist)
			throws Exception {

		Calendar cal = Calendar.getInstance();
		String parseDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
		
		// 一般行
		int colCount = rowlist.size();
//		System.out.println("colCount : " + colCount);
		System.out.println("linenum : " + linenum);
//		System.out.println("ipRangeList size:" + ipRangeList.size());
		
		//检查第一行的列名及列数是否正确
		if(linenum ==0 && colCount < DOMAIN_RESP_COL_NUM && !checkColume(rowlist))
		{
			throw new Exception("文件格式错误!");
		}else if(linenum > 0)
		{
			String websiteName = rowlist.get(0).toString();
			String domainName = rowlist.get(1).toString();
			String ip = rowlist.get(2).toString();
			String attribution = "";
			if(colCount == 4)
			{
				attribution = rowlist.get(3).toString();
			}
			System.out.println("websiteName : " + websiteName);
			System.out.println("domainName : " + domainName);
			System.out.println("ip : " + ip);
			System.out.println("attribution : " + attribution);
			
			String[] collectresult = ip.split(",");
			int ipLength = collectresult.length;
			
			for(String ip1 : collectresult)
			{
				String domain_operator = "";
				String domain_area = "";
				
				NetResDns netResDns = new NetResDns();
				netResDns.setDomainName(domainName);
				netResDns.setWebsiteName(websiteName);
				netResDns.setIp(ip1);
				netResDns.setBelongAddress(attribution);
				netResDns.setBak(new BigDecimal(1).divide(new BigDecimal(ipLength), 4, BigDecimal.ROUND_FLOOR).toString());
				
				for(FmIpRange iprange : ipRangeList)
				{
//					System.out.println("iprange : " + iprange.toString());
//					break;
					String start_ip = iprange.getStartIp();
					String end_ip = iprange.getEndIp();
					String operator = iprange.getOperator();
					String area = iprange.getArea();
					
					if(isInIpRange(ip1,start_ip,end_ip))
					{
						domain_operator = operator;
						domain_area = area;
						break;
					}
				}
				
				netResDns.setOperator(domain_operator);
				netResDns.setDistrict(domain_area);
				session.getMapper(InternetResourcesMapper.class).insertNetResDns(netResDns);
			}

			FmDomainRepository obj = new FmDomainRepository();
			obj.setDomainName(domainName);
			obj.setWebsiteName(websiteName);
			obj.setIp(ip);
			obj.setAttribution(attribution);
			obj.setImpDate(parseDate);
			
			session.getMapper(InternetResourcesMapper.class).insertDomainRespository(obj);
			if(linenum % 3000 == 0) {
				//手动每5000个一提交，提交后无法回滚
	    		session.commit();
	    		//清理缓存，防止溢出
	    		session.clearCache();
			}
		}

		session.commit();
		session.clearCache();
		
		linenum++;
	}
	
	/*
    * @description  判断IP是否在IP段内
    * @param 
    * ip: 需判断的IP
    * sIp： IP段起始IP
    * eIp： IP段终止IP
    * @return boolean
    */
	private boolean isInIpRange(String ip, String sIp, String eIp)
	{
		if (ip == null)
		{
			return false;
		}
		if (sIp == null)
		{
			return false;
		}
		if (eIp == null)
		{
			return false;
		}
		ip = ip.trim();
		sIp = sIp.trim();
		eIp = eIp.trim();
		
		// 验证IP网段和IP格式是否合法 
		if (!sIp.matches(REGX_IP) || !eIp.matches(REGX_IP) || !ip.matches(REGX_IP))
		{
			return false;
		}
		String[] sips = ip.split("\\.");
		String[] ssIps = sIp.split("\\.");
		String[] seIps = eIp.split("\\.");
		
		long ips = 0L, ipe = 0L, ipt = 0L;
		for(int i = 0;i < 4;i++)
		{
			ips = ips << 8 | Integer.parseInt(ssIps[i]);
			ipe = ipe << 8 | Integer.parseInt(seIps[i]);
			ipt = ipt << 8 | Integer.parseInt(sips[i]);
		}
		
		if (ips > ipe)
		{
			long t = ips;
			ips = ipe;
			ipe = t;
		}
		return ips <= ipt && ipt <= ipe;
	}

    private boolean checkColume(List<String> rowlist)
    {
    	boolean flag = false;
    	
    	String websiteName = rowlist.get(0).toString();
		String domainName = rowlist.get(1).toString();
		String ip = rowlist.get(2).toString();
		String attribution = rowlist.get(3).toString();
		
		if("网站".equals(websiteName) && "域名".equals(domainName) && "IP".equals(ip) && "归属地".equals(attribution))
		{
			flag = true;
		}
    	
    	return flag;
    }
}
