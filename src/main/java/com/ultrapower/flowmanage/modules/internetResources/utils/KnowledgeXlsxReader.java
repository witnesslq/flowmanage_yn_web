/**
 * 
 */
package com.ultrapower.flowmanage.modules.internetResources.utils;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.ultrapower.flowmanage.common.utils.XxlsAbstract;
import com.ultrapower.flowmanage.modules.internetResources.vo.FmIpRange;

/**
 * @author acer
 *
 */
public class KnowledgeXlsxReader extends XxlsAbstract {private SqlSession session;
	private static final String REGX_IP = "((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)";
	private final static int KNOWLEDGE_COL_NUM = 17;
	private int linenum = 0;
	private List<FmIpRange> ipRangeList;

	public KnowledgeXlsxReader(SqlSession session, List<FmIpRange> ipRangeList) {
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
		String kname = rowlist.get(1).toString();
		String ktype = rowlist.get(2).toString();
		String versions = rowlist.get(3).toString();
		String platforms = rowlist.get(4).toString();
		String corporation = rowlist.get(5).toString();
		String function = rowlist.get(6).toString();
		String feature = rowlist.get(7).toString();
		String usergroup = rowlist.get(8).toString();
		String protocols = rowlist.get(9).toString();
		String ports = rowlist.get(10).toString();
//		String port = rowlist.get(11).toString();
		String host = rowlist.get(11).toString();
//		String effect = rowlist.get(13).toString();
		String ip = rowlist.get(12).toString();

		System.out.println("linenum :" + linenum);
		System.out.println("kname :" + kname);
		System.out.println("ktype :" + ktype);
		System.out.println("versions :" + versions);
		System.out.println("platforms :" + platforms);
		System.out.println("corporation :" + corporation);
		System.out.println("function :" + function);
		System.out.println("feature :" + feature);
		System.out.println("usergroup :" + usergroup);
		System.out.println("protocols :" + protocols);
		System.out.println("ports :" + ports);
//		System.out.println("port :" + port);
		System.out.println("host :" + host);
//		System.out.println("effect :" + effect);
		System.out.println("ip :" + ip);

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
    	
		String kname = rowlist.get(1).toString();
		String ktype = rowlist.get(2).toString();
		String versions = rowlist.get(3).toString();
		String platforms = rowlist.get(4).toString();
		String corporation = rowlist.get(5).toString();
		String function = rowlist.get(6).toString();
		String feature = rowlist.get(7).toString();
		String usergroup = rowlist.get(8).toString();
		String protocols = rowlist.get(9).toString();
		String ports = rowlist.get(10).toString();
		String port = rowlist.get(11).toString();
		String host = rowlist.get(12).toString();
		String effect = rowlist.get(13).toString();
		String ip = rowlist.get(14).toString();

		
		if("业务名称".equals(kname)
				&& "业务类别".equals(ktype)
				&& "软件版本".equals(versions)
				&& "适用平台".equals(platforms)
				&& "开 发 商".equals(corporation)
				&& "业务功能".equals(function)
				&& "业务特点".equals(feature)
				&& "用户群".equals(usergroup)
				&& "协议".equals(protocols)
				&& "端口".equals(ports)
				&& "端口细分".equals(port)
				&& "HOST".equals(host)
				&& "域名作用".equals(effect)
				&& "IP".equals(ip))
		{
			flag = true;
		}
    	
    	return flag;
    }

}
