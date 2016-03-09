package com.ultrapower.flowmanage.modules.testExcel;


/**
 * 
 * 
 * @Title: 测试SAX方式解析Excel 
 * @Description: 实现TODO
 * @Copyright:Copyright (c) 2011
 * @Company:易程科技股份有限公司
 * @Date:2012-6-17
 * @author  longgangbai
 * @version 1.0
 */
public class SAXEventUserModel {
	public static void main(String[] args) throws Exception {
		System.out.println("开始采用SAX解析Excel ！");
		SAXHandlerEventUserModel howto = new SAXHandlerEventUserModel();
		//howto.processOneSheet("C:\\station2stationone.xlsx");
		//System.out.println("单独一个sheet解析完毕！");
		howto.processAllSheets("G:\\testexcel\\station2stationmany.xlsx");
		//System.out.println("多个sheet解析完毕！");
		System.out.println("采用SAX解析Excel 完毕！");
	}

}
