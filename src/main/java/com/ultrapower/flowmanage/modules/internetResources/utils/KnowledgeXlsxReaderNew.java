/**
 * 
 */
package com.ultrapower.flowmanage.modules.internetResources.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.ultrapower.flowmanage.common.utils.excelRead.IRowReader;
import com.ultrapower.flowmanage.modules.internetResources.dao.mapper.InternetResourcesMapper;
import com.ultrapower.flowmanage.modules.internetResources.vo.FmIpRange;
import com.ultrapower.flowmanage.modules.internetResources.vo.FmKnowledge;

/**
 * @author acer
 *
 */
public class KnowledgeXlsxReaderNew implements IRowReader {
	private static Logger logger = Logger.getLogger(KnowledgeXlsxReaderNew.class);
	
	private SqlSession session;
	private static final String REGX_IP = "((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)";
	private final static int KNOWLEDGE_COL_NUM_FIRST = 13;
	private final static int KNOWLEDGE_COL_NUM_OTHER = 2;
	private int linenum = 0;
	private List<FmIpRange> ipRangeList;
	private FmKnowledge fmKnowledge;
	private int wrongSheetIdx = -1;

	public KnowledgeXlsxReaderNew(SqlSession session, List<FmIpRange> ipRangeList) {
		this.session = session;
		this.linenum = 0;
		this.ipRangeList = ipRangeList;
	}
	
	/* (non-Javadoc)
	 * @see com.ultrapower.flowmanage.flowManageJUnit.excelRead.IRowReader#getRows(int, int, java.util.List)
	 */
	@Override
	public void getRows(int sheetIndex, int curRow, List<String> rowlist) {

		try
		{
			Calendar cal = Calendar.getInstance();
			String parseDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
			
			// 一般行
			int colCount = rowlist.size();
			System.out.println("sheetIndex : " + sheetIndex);
			System.out.println("curRow : " + curRow);
//			System.out.println("linenum : " + linenum);
			System.out.println("colCount : " + colCount);
			//检查第一行的列名及列数是否正确
			if(curRow == 0 
					&& !checkColume(rowlist))
			{
				wrongSheetIdx = sheetIndex;
//				throw new Exception("文件sheet格式错误!");
			}else if(sheetIndex != wrongSheetIdx 
					&& curRow > 0 
					&& colCount == KNOWLEDGE_COL_NUM_FIRST)
			{
				int kid = session.getMapper(InternetResourcesMapper.class).getKnowledgeKid();
				
				String kname = rowlist.get(1) == null ? "" : rowlist.get(1).toString();
				String ktype = rowlist.get(2) == null ? "" : rowlist.get(2).toString();
				String versions = rowlist.get(3) == null ? "" : rowlist.get(3).toString();;
				String platforms = rowlist.get(4) == null ? "" : rowlist.get(4).toString();
				String corporation = rowlist.get(5) == null ? "" : rowlist.get(5).toString();
				String function = rowlist.get(6) == null ? "" : rowlist.get(6).toString();
				String feature = rowlist.get(7) == null ? "" : rowlist.get(7).toString();
				String usergroup = rowlist.get(8) == null ? "" : rowlist.get(8).toString();
				String protocols = rowlist.get(9) == null ? "" : rowlist.get(9).toString();
				String ports = rowlist.get(10) == null ? "" : rowlist.get(10).toString();
				String host = rowlist.get(11) == null ? "" : rowlist.get(11).toString();
				String ip = rowlist.get(12) == null ? "" : rowlist.get(12).toString();
				
				FmKnowledge vo = new FmKnowledge();
				vo.setKid(kid);
				vo.setKname(kname);
				vo.setKtype(ktype);
				vo.setVersions(versions);
				vo.setPlatforms(platforms);
				vo.setCorporation(corporation);
				vo.setFunction(function);
				vo.setFeature(feature);
				vo.setUsergroup(usergroup);
				vo.setProtocols(protocols);
				vo.setPorts(ports);
				vo.setHost(host);
				vo.setIp(ip);
				vo.setImpDate(parseDate);
				
				fmKnowledge = vo;
				
				session.getMapper(InternetResourcesMapper.class).insertKnowledge(vo);
				session.getMapper(InternetResourcesMapper.class).insertNetResKnowledge(vo);
				
				insertKnowledgeDetail(ip);

			}else if(sheetIndex != wrongSheetIdx 
					&& curRow > 0 
					&& colCount > 2
					)
			{
				FmKnowledge vo = new FmKnowledge();
				
//				int currentColNum = rowlist.size();

				String host = rowlist.get(colCount - 2).toString();
				String ip = rowlist.get(colCount - 1).toString();
				
				copyFmKnowledge(vo);
				vo.setHost(host);
				vo.setIp(ip);
				
//				System.out.println("vo :" + vo);
				
				session.getMapper(InternetResourcesMapper.class).insertKnowledge(vo);

				insertKnowledgeDetail(ip);
			}
//			else
//			{
//				int line = 0;
//				for(String s:rowlist)
//				{
//					System.out.println("行" + line + ":" + s);
//					line ++;
//				}
//			}
			
			if(linenum % 3000 == 0) {
				//手动每5000个一提交，提交后无法回滚
	    		session.commit();
	    		//清理缓存，防止溢出
	    		session.clearCache();
			}
			
			linenum++;
		} catch(Exception e)
		{
			logger.error("导入出错：" + e.getCause());
			e.printStackTrace();
		}
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

	//检查字段正确性
    private boolean checkColume(List<String> rowlist)
    {
    	boolean flag = false;
    	
    	if(rowlist.size() < KNOWLEDGE_COL_NUM_FIRST)
    	{
    		return false;
    	}

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
//				&& "端口细分".equals(port)
				&& "HOST".equals(host)
//				&& "域名作用".equals(effect)
				&& "IP".equals(ip))
		{
			flag = true;
		}
    	
    	return flag;
    }
    
    //复制von内容
    private void copyFmKnowledge(FmKnowledge vo)
    {
    	vo.setKid(fmKnowledge.getKid());
		vo.setKname(fmKnowledge.getKname());
		vo.setKtype(fmKnowledge.getKtype());
		vo.setVersions(fmKnowledge.getVersions());
		vo.setPlatforms(fmKnowledge.getPlatforms());
		vo.setCorporation(fmKnowledge.getCorporation());
		vo.setFunction(fmKnowledge.getFunction());
		vo.setFeature(fmKnowledge.getFeature());
		vo.setUsergroup(fmKnowledge.getUsergroup());
		vo.setProtocols(fmKnowledge.getProtocols());
		vo.setPorts(fmKnowledge.getPorts());
//		vo.setPort(fmKnowledge.getPort());
		vo.setHost(fmKnowledge.getHost());
//		vo.setEffect(fmKnowledge.getEffect());
		vo.setIp(fmKnowledge.getIp());
		vo.setImpDate(fmKnowledge.getImpDate());
    }
    
    //插入知识库详细的数据
    private void insertKnowledgeDetail(String ip)
    {
		try {
			String[] collectresult = ip.split("\n");
			
			for(String ip1 : collectresult)
			{
				if(!"".equals(ip1))
				{
					String[] collectresult2 = ip1.split(" ");
					for(String ip2 : collectresult2)
					{
				    	FmKnowledge vo = new FmKnowledge();
						copyFmKnowledge(vo);
						
						String domain_operator = "";
						String domain_area = "";
						
						for(FmIpRange iprange : ipRangeList)
						{
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
						vo.setIp(ip2);
						vo.setBelong(domain_area + domain_operator);
						
						session.getMapper(InternetResourcesMapper.class).insertNetResKnowledgeDetail(vo);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
