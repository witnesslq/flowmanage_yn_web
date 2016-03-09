package com.ultrapower.flowmanage.modules.internetResources.service.impl;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ultrapower.flowmanage.common.utils.ExcelReader;
import com.ultrapower.flowmanage.common.utils.ThreadPoolTask;
import com.ultrapower.flowmanage.common.vo.Page;
import com.ultrapower.flowmanage.common.utils.excelRead.ExcelReaderUtil;
import com.ultrapower.flowmanage.common.utils.excelRead.IRowReader;
import com.ultrapower.flowmanage.common.utils.excelRead.RowReader;
import com.ultrapower.flowmanage.modules.internetResources.dao.InternetResourcesDao;
import com.ultrapower.flowmanage.modules.internetResources.service.InternetResourcesService;
import com.ultrapower.flowmanage.modules.internetResources.utils.DoaminRespositoryXlsxReader;
import com.ultrapower.flowmanage.modules.internetResources.utils.DoaminRespositoryXlsxReaderNew;
import com.ultrapower.flowmanage.modules.internetResources.utils.KnowledgeXlsxReaderNew;
import com.ultrapower.flowmanage.modules.internetResources.vo.FmIpRange;

@Service("internetResourcesService")
public class InternetResourcesServiceImpl implements InternetResourcesService {
	private static Logger logger = Logger.getLogger(InternetResourcesServiceImpl.class);
	@Resource(name="internetResourcesDao")
    private InternetResourcesDao internetResourcesDao;
	@Autowired
	private SqlSessionFactoryBean sessionFactory;
	
	private final static int DOMAIN_RESP_COL_NUM = 4;
	private static final String REGX_IP = "((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)";
	
	//查询TOP10网站域名总数统计 
	public Map<String, Object> findTop10WebDomainCount() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> list = internetResourcesDao.findTop10WebDomainCount();
		if(list != null){
			for (Map<String, String> mp : list) {
				if("1".equals(mp.get("number"))){
					map.put("webName", mp.get("label"));
				}
				mp.put("link", "javascript:findOperatorDistributionCountBywebName('"+mp.get("label")+"')");
			}
			map.put("list", list);
		}
		return map;
	}

	//查询其他网站域名总数统计
	public List<Map<String, String>> findOtherWebDomainCount() throws Exception {
		List<Map<String, String>> list = internetResourcesDao.findOtherWebDomainCount();
		if(list != null){
			for (Map<String, String> mp : list) {
				mp.put("link", "");
			}
		}
		return list;
	}

	//根据网站名称查询运营商分布统计情况
	public Map<String, Object> findOperatorDistributionCountBywebName(String webName) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> list = internetResourcesDao.findOperatorDistributionCountBywebName(webName);
		if(list != null){
			for (Map<String, String> mp : list) {
				if("1".equals(mp.get("number"))){
					map.put("operator", mp.get("label"));
				}
				mp.put("link", "javascript:findDistrictDistributionBywebNameOperator('"+webName+"','"+mp.get("label")+"')");
			}
			map.put("list", list);
		}
		return map;
	}

	//根据网站名称和运营商查询区域分布统计情况
	public Map<String, Object> findDistrictDistributionBywebNameOperator(String webName, String operator) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> list = internetResourcesDao.findDistrictDistributionBywebNameOperator(webName, operator);
		if(list != null){
			for (Map<String, String> mp : list) {
				if("1".equals(mp.get("number"))){
					map.put("area", mp.get("label"));
				}
				mp.put("link", "javascript:findDistrictwebNameOperatorAreaTable('"+webName+"','"+operator+"','"+mp.get("label")+"', 1, 20)");
			}
			map.put("list", list);
		}
		return map;
	}

	//根据网站名称和运营商查询运营商维度明细统计列表
	public Page findOperatorDetailBywebNameOperatorTable(String webName, String operator, int pageNo, int pageSize) throws Exception {
		Page page = new Page();
		int rowCount = internetResourcesDao.findOperatorDetailBywebNameOperatorRowCount(webName, operator);
		List<Map<String, String>> list = internetResourcesDao.findOperatorDetailBywebNameOperatorTable(webName, operator, pageNo, pageSize);
		page.setPageNo(pageNo);//当前页
		page.setPageSize(pageSize);//每页的记录数
		page.setRowCount(rowCount);//总行数
		page.setResultList(list);//每页的记录
		return page;
	}

	//根据网站名称和运营商、区域查询区域维度明细统计列表
	public Page findDistrictwebNameOperatorAreaTable(String webName, String operator, String area, int pageNo, int pageSize) throws Exception {
		Page page = new Page();
		int rowCount = internetResourcesDao.findDistrictwebNameOperatorAreaRowCount(webName, operator, area);
		List<Map<String, String>> list = internetResourcesDao.findDistrictwebNameOperatorAreaTable(webName, operator, area, pageNo, pageSize);
		page.setPageNo(pageNo);//当前页
		page.setPageSize(pageSize);//每页的记录数
		page.setRowCount(rowCount);//总行数
		page.setResultList(list);//每页的记录
		return page;
	}

	//根据网站名称、域名、IP地址、归属区域查询域名库列表
	public Page findDomainLibraryTable(String webName, String domainName, String ip, String district, int pageNo, int pageSize) throws Exception{
		Page page = new Page();
		int rowCount = internetResourcesDao.findDomainLibraryRowCount(webName, domainName, ip, district);
		List<Map<String, String>> list = internetResourcesDao.findDomainLibraryTable(webName, domainName, ip, district, pageNo, pageSize);
		page.setPageNo(pageNo);//当前页
		page.setPageSize(pageSize);//每页的记录数
		page.setRowCount(rowCount);//总行数
		page.setResultList(list);//每页的记录
		return page;
	}
	/*
	//根据网站名称、域名、IP地址、归属区域导出Excel表格
	public byte[] exportDomainLibraryExcelTable(String webName, String domainName, String ip, String district) throws Exception{
		//域名库信息列表总行数
		int rowCount = internetResourcesDao.findDomainLibraryRowCount(webName, domainName, ip, district);
		List<Map<String, String>> list = internetResourcesDao.findDomainLibraryTable(webName, domainName, ip, district, 1, rowCount);
		*//************************** Excel导出  *******************************//*
		*//*** 热点资源引入分析_流量TOP50网站列表***//*
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFCell cell = null;
		XSSFRow row = null;
		String tit = null;
		XSSFSheet sheet =  wb.createSheet("互联网资源库_自由查询_域名库列表(1)");
		String[] str = new String[]{"网站","域名","IP地址","归属区域"};//表头名称
		String[] mapKeyName = new String[]{"webName","domainName","ip","belongAddress"};//Map的Key值
		sheet.setDefaultRowHeightInPoints(10);    
		sheet.setDefaultColumnWidth(30);
		//标题样式
		XSSFCellStyle cellStyleTit = wb.createCellStyle();
		cellStyleTit.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		cellStyleTit.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
		//			cellStyle.setWrapText(true);// 指定单元格自动换行
		XSSFFont fontTit = wb.createFont();
		fontTit.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		fontTit.setFontHeightInPoints((short) 12);
		fontTit.setFontName("微软雅黑");
		fontTit.setFontHeight((short) 220);
		cellStyleTit.setFont(fontTit);
		
		//内容单元格样式
		XSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT); // 指定单元格向左对齐
		cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
		//			cellStyle.setWrapText(true);// 指定单元格自动换行
		XSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setFontName("宋体");
		font.setFontHeight((short) 200);
		cellStyle.setFont(font);
		
		sheet.setColumnWidth(1, 50*256);//设置第二列的宽度
		sheet.setColumnWidth(2, 50*256);//设置第三列的宽度
		XSSFRow rowTit = sheet.createRow(0);
		for (int i = 0; i < str.length; ++i) {
			tit = (String)str[i];
			cell = rowTit.createCell(i);
			cell.setCellStyle(cellStyleTit);
			cell.setCellValue(new XSSFRichTextString(tit));
		}
		//构造一个线程池
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(10, 20, 20,
				TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(20),
				new ThreadPoolExecutor.DiscardOldestPolicy());
		FileOutputStream writeFile = new FileOutputStream("D:/桌面/互联网资源库_资源库列表.xlsx");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("sheet", sheet);
		map.put("row", row);
		map.put("cell", cell);
		map.put("mapKeyName", mapKeyName);
		map.put("cellStyle", cellStyle);
		map.put("wb", wb);
		map.put("writeFile", writeFile);
		this.getLinkedBlockingQueue().put(map);
		System.out.println("主线程执行任务的近似线程数:"+threadPool.getActiveCount());
		if(list.size() > 0){
			int taskSize = list.size() % 5000 != 0 ? list.size() / 5000 + 1 : list.size() / 5000;
			for(int i = 0; i < taskSize ;i ++){
				ThreadPoolTask th = new ThreadPoolTask(i);
				threadPool.execute(th);
				Thread.sleep(1000 * 10);
			}
		}
		
		//内容
		int k=0, n=1;
		if(rowCount > 0){
			for (int i = 0; i < list.size(); ++i) {
				Map<String, String> map = (Map<String, String>)list.get(i);
				if(50000 * n == i){
					k = 0;
					n++;
					sheet =  wb.createSheet("互联网资源库_自由查询_域名库列表("+n+")");
					sheet.setColumnWidth(1, 50*256);//设置第二列的宽度
					sheet.setColumnWidth(2, 50*256);//设置第三列的宽度
					rowTit = sheet.createRow(0);
					for (int t = 0; t < str.length; ++t) {
						tit = (String)str[t];
						cell = rowTit.createCell(t);
						cell.setCellStyle(cellStyleTit);
						cell.setCellValue(new XSSFRichTextString(tit));
					}
				}
				
				row = sheet.createRow(k + 1);
				for (int j = 0; j < mapKeyName.length; j++) {
					cell = row.createCell(j);
					cell.setCellStyle(cellStyle);
					String value = map.get(mapKeyName[j]) == null ? "" : String.valueOf(map.get(mapKeyName[j]));
					cell.setCellValue(new XSSFRichTextString(value));
				}
				k++;
			}
		}
		try {
			ByteArrayOutputStream fos=new ByteArrayOutputStream();
			
			wb.write(fos);
			
			
			FileOutputStream writeFile = new FileOutputStream("D:/桌面/互联网资源库_资源库列表.xlsx");
			wb.write(writeFile);
			writeFile.close();
			
			byte[] content =fos.toByteArray();
			return content;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	*/
	private static LinkedBlockingQueue linkedBlockingQueue = null;
	public static LinkedBlockingQueue getLinkedBlockingQueue() {
		if (linkedBlockingQueue == null) {
			linkedBlockingQueue = new LinkedBlockingQueue<Map<String, Object>>();
		}
		//System.out.println("linkedBlockingQueue::::::::::::"+linkedBlockingQueue.size());
		return linkedBlockingQueue;
	}
	
	//根据网站名称、域名、IP地址、归属区域导出Excel表格
	public byte[] exportDomainLibraryExcelTable(String webName, String domainName, String ip, String district) throws Exception{
		ByteArrayOutputStream fos=new ByteArrayOutputStream();
		//FileOutputStream writeFile = new FileOutputStream("D:/桌面/互联网资源库_资源库列表.xls");
		WritableWorkbook book = Workbook.createWorkbook(fos);
		WritableSheet sheet = book.createSheet("互联网资源库_自由查询_域名库列表(1)", 0);//创建工作薄
		String[] titles = new String[]{"网站","域名","IP地址","归属区域"};//表头名称
		WritableFont titleFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false,UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.DARK_BLUE);
		WritableCellFormat titleCellFormat = new WritableCellFormat(titleFont);
		titleCellFormat.setAlignment(Alignment.CENTRE);
		for (int i = 0; i < titles.length; i++) {
		 sheet.setColumnView(i, 30);
	     Label label = new Label(i, 0, titles[i], titleCellFormat);
	     sheet.addCell(label);
	    }
		 //动态填充数据
	     Vector vector = internetResourcesDao.findDomainLibraryTableExcel(webName, domainName, ip, district);
	     if(vector.size() > 0){
	    	 int k=0, n=1;
	    	 for (int i = 0; i < vector.size(); i++) { 
	    		 String[] sdata = (String[]) vector.elementAt(i); 
	    		 if(50000 * n == i){
	    			 k = 0;
	    			 n++;
	    			 sheet = book.createSheet("互联网资源库_自由查询_域名库列表("+n+")", n);
	    			 for (int t = 0; t < titles.length; t++) {
	    				 sheet.setColumnView(t, 30);
	    				 Label label = new Label(t, 0, titles[t], titleCellFormat);
	    				 sheet.addCell(label);
	    			 }
	    		 }
	    		 for (int j = 0; j < sdata.length; j++) {
	    			 Label wlabel1 = new Label(j, k+1, sdata[j]); 
	    			 sheet.addCell(wlabel1);
	    		 }
	    		 k++;
	    	 }
	     }
	    
	    try {
	    	book.write();
	    	book.close();
			byte[] content =fos.toByteArray();
			return content;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	//上传资源库文件
	public boolean upload(byte[] data, String type, String filesuffix) throws Exception{
		boolean flag = false;
		
		System.out.println("type: " + type);
		System.out.println("data: " + data);
		//域名库导入
		if("1".equals(type) || "2".equals(type))
		{
			//普通模式导入
//			flag = insertDoaminRespository(data, type, filesuffix);
			
			//eventmodel模式导入
			flag = insertDoaminRespositoryNew(data, type, filesuffix);
		} 
		//知识百科导入
		else if ("3".equals(type) || "4".equals(type))
		{
//			flag = insertKnowledge(data, type, filesuffix);
			
			flag = insertKnowledgeNew(data, type, filesuffix);
		}
		
		return flag;
	}
	
	private void resetSeq(String seqName) throws Exception
	{
		Map para = new HashMap();
		para.put("seqname", seqName);
		internetResourcesDao.resetSeq(para);
	}
	
	private void deleteDomainRespository() throws Exception
	{
		internetResourcesDao.deleteDomainRespository();
	}
	
	private void deleteNetResDns() throws Exception
	{
		internetResourcesDao.deleteNetResDns();
	}

	//删除知识百科数据
	private void deleteKnowledge() throws Exception
	{
		internetResourcesDao.deleteKnowledge();
	}
	
	//删除知识百科前台数据
	private void deleteNetResKnowledge() throws Exception
	{
		internetResourcesDao.deleteNetResKnowledge();
	}
	
	//删除知识百科详细前台数据
	private void deleteNetResKnowledgeDetail() throws Exception
	{
		internetResourcesDao.deleteNetResKnowledgeDetail();
	}
	
	private List<FmIpRange> getIpRange() throws Exception{
		return internetResourcesDao.getIpRange();
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
	
	private boolean insertDoaminRespositoryNew(byte[] data, String type, String filesuffix) throws Exception 
	{
		boolean flag = false;
		SqlSession session = sessionFactory.getObject().openSession(ExecutorType.BATCH, true);
//		ByteArrayInputStream is = null;
		try {
			Calendar cal = Calendar.getInstance();
			String parseDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
			List<FmIpRange> ipRangeList = getIpRange();
			
			logger.debug("开始读取xls文件");
			System.out.println("开始读取xls文件");
//			is = new ByteArrayInputStream(data);
			
			//全量导入先删除全表，然后重置sequcence
			if("1".equals(type))
			{
				deleteDomainRespository();
				deleteNetResDns();
				resetSeq("SEQ_DOMAIN_RESP");
			}
			
//			DoaminRespositoryXlsxReader reader = new DoaminRespositoryXlsxReader(session, ipRangeList);
//			reader.processOneSheet(is, 1, 0);
			IRowReader reader = new DoaminRespositoryXlsxReaderNew(session, ipRangeList);
			ExcelReaderUtil.readExcel(reader, data, filesuffix);

			session.commit();
			session.clearCache();
    		flag = true;
		} catch (Exception e) {
			//没有提交的数据可以回滚
			flag = false;
            session.rollback();
			e.printStackTrace();
			throw new Exception("文件导入错误!");
		} finally {
	    	session.close();
//	    	is.close();
	    }
		return flag;
	}
	

	private boolean insertKnowledgeNew(byte[] data, String type, String filesuffix) throws Exception 
	{
		boolean flag = false;
		SqlSession session = sessionFactory.getObject().openSession(ExecutorType.BATCH, true);
//		ByteArrayInputStream is = null;
		try {
			Calendar cal = Calendar.getInstance();
			String parseDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
			List<FmIpRange> ipRangeList = getIpRange();
			
			logger.debug("开始读取xls文件");
			System.out.println("开始读取xls文件");
//			is = new ByteArrayInputStream(data);
			
			//全量导入先删除全表，然后重置sequcence
			if("3".equals(type))
			{
				deleteKnowledge();
				deleteNetResKnowledge();
				deleteNetResKnowledgeDetail();
				resetSeq("SEQ_KNOWLEDGE");
			}
			
//			DoaminRespositoryXlsxReader reader = new DoaminRespositoryXlsxReader(session, ipRangeList);
//			reader.processOneSheet(is, 1, 0);
			IRowReader reader = new KnowledgeXlsxReaderNew(session, ipRangeList);
			ExcelReaderUtil.readExcel(reader, data, filesuffix);
			

			session.commit();
			session.clearCache();
			
    		flag = true;
		} catch (Exception e) {
			//没有提交的数据可以回滚
			flag = false;
            session.rollback();
			e.printStackTrace();
			throw new Exception("文件导入错误!");
		} finally {
	    	session.close();
//	    	is.close();
	    }
		return flag;
	}

	//查询业务总体统计
	public Map<String, Object> findBusinessTotalCount() throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> list = internetResourcesDao.findBusinessTotalCount();
		if(list != null){
			for (Map<String, String> mp : list) {
				if("1".equals(mp.get("number"))){
					map.put("bizName", mp.get("label"));
				}
				mp.put("link", "javascript:findBusinessDetailsCountByBizName('"+mp.get("label")+"')");
			}
			map.put("list", list);
		}
		return map;
	}
	
	//根据业务大类名称查询业务明细统计
	public Map<String, Object> findBusinessDetailsCountByBizName(String bizName) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, String>> list = internetResourcesDao.findBusinessDetailsCountByBizName(bizName);
		if(list != null){
			for (Map<String, String> mp : list) {
				if("1".equals(mp.get("number"))){
					map.put("kid", mp.get("kid"));
				}
				mp.put("link", "javascript:findBusinessDetailsInfoByKid('"+mp.get("kid")+"')");
			}
			map.put("list", list);
		}
		return map;
	}

	//根据业务ID查询业务详细信息
	public Map<String, Object> findBusinessDetailsInfoByKid(String kid)
			throws Exception {
		Map<String, Object> map = internetResourcesDao.findBusinessTotalInfoByKid(kid);
		List<Map<String, String>> list = internetResourcesDao.findBusinessDetailsInfoByKid(kid);
		if(list.size() > 0){
			map.put("list", list);
		}
		return map;
	}
}
