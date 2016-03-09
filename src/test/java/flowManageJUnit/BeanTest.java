package flowManageJUnit;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.ultrapower.flowmanage.common.vo.Page;
import com.ultrapower.flowmanage.common.vo.Tree;
import com.ultrapower.flowmanage.modules.busiBenchmark.control.BusiBenchmarkController;
import com.ultrapower.flowmanage.modules.flow.control.FlowController;
import com.ultrapower.flowmanage.modules.flow.service.FlowService;
import com.ultrapower.flowmanage.modules.flow.vo.FlowExitLinkVo;
import com.ultrapower.flowmanage.modules.flow.vo.FsChartVo;
import com.ultrapower.flowmanage.modules.health.control.HealthController;
import com.ultrapower.flowmanage.modules.hotspotResource.control.HotspotResourceController;
import com.ultrapower.flowmanage.modules.internetResources.control.InternetResourcesController;
import com.ultrapower.flowmanage.modules.portVideoBiz.control.PortVideoBizController;
import com.ultrapower.flowmanage.modules.quality.control.QualityController;
import com.ultrapower.flowmanage.modules.wlanLogAnalyse.control.WlanLogAnalyseController;
import com.ultrapower.flowmanage.modules.wlanLogAnalyse.vo.WlanLogParameter;
import com.ultrapower.flowmanage.modules.xdrAnalyse.control.XdrAnalyseController;


public class BeanTest{
	Logger logger = Logger.getLogger(BeanTest.class);
	FlowService flowservice = null;
	
	FlowController flowController = null;
	HealthController healthController = null;
	QualityController qualityController = null;
	HotspotResourceController hotspotResourceController = null;
	InternetResourcesController internetResourcesController = null;
	BusiBenchmarkController busiBenchmarkController = null;
	WlanLogAnalyseController wlanLogAnalyseController = null;
	XdrAnalyseController xdrAnalyseController = null;
	PortVideoBizController portVideoBizController = null;
	
	@Before
	public void init() {
		String path = BeanTest.class.getClassLoader().getResource("") + "log4j.properties";
		System.out.println("log4j路径："+BeanTest.class.getClassLoader().getResource(""));
		PropertyConfigurator.configure(path.substring(6));  
		ApplicationContext ac = new FileSystemXmlApplicationContext("classpath:junit_applicationContext.xml");
		FlowService flowservice = (FlowService)ac.getBean("flowservice");
		
		FlowController flowController = (FlowController)ac.getBean("flowController");
		healthController = (HealthController)ac.getBean("healthController");
		qualityController = (QualityController)ac.getBean("qualityController");
		hotspotResourceController = (HotspotResourceController)ac.getBean("hotspotResourceController");
		internetResourcesController = (InternetResourcesController)ac.getBean("internetResourcesController");
		busiBenchmarkController = (BusiBenchmarkController)ac.getBean("busiBenchmarkController");
		wlanLogAnalyseController = (WlanLogAnalyseController)ac.getBean("wlanLogAnalyseController");
		xdrAnalyseController = (XdrAnalyseController)ac.getBean("xdrAnalyseController");
		portVideoBizController = (PortVideoBizController)ac.getBean("portVideoBizController");
		this.flowservice = flowservice;
		this.flowController = flowController;
		this.healthController = healthController;
		this.qualityController = qualityController;
		this.hotspotResourceController = hotspotResourceController;
		this.internetResourcesController = internetResourcesController;
		this.busiBenchmarkController = busiBenchmarkController;
		this.wlanLogAnalyseController = wlanLogAnalyseController;
		this.xdrAnalyseController = xdrAnalyseController;
		this.portVideoBizController = portVideoBizController;
	}
	
	@Test
	public void flowTest() {
//		Tree tree = healthController.buildHealthTree("HEALTH_NETWORK");
	   
//		List<Map<String, String>> lists = healthController.findThreeNetHealth("全省","HEALTH_3NET","");
//		List<CityContrastVo> lists = healthController.findCityHealthScoreById("56","HEALTH_3NET","");
//		List<Map<String, String>> lists = healthservice.findThreeNetHealth("全省","HEALTH_3NET");

//		List<CityContrastVo> lists = healthservice.findCityHealthScoreById("105");
//		List<Map<String, String>> lists = flowController.findFlowOper("电信及联通");
//		System.out.println(lists.size());
		
//		System.out.println(flowController.findBusinessTypeName("RES_TYPE"));

//		List<CityContrastVo> lists = healthController.findCityHealthScoreById("105");
//		List<Map<String, String>> lists = flowController.findFlowOper("电信及联通");
//		System.out.println(lists.size());

//		for (CityContrastVo city : lists) {
//			System.out.println(city.getId()+" "+city.getCityname()+" "+city.getScore()+" "+city.getScopevalue());
//		}
//		List<CityContrastVo> lists = healthservice.findCityInfo();
//		for (CityContrastVo city : lists) {
//			System.out.println(city.getId()+" "+city.getCityid()+" "+city.getCityname());
//		}
//		List<CityContrastVo> lists = healthservice.findHealthNameByType("HEALTH_3NET");
//		List<Map<String, Object>> lists = healthController.findHealthNameScoreByHealthId("100", "HEALTH_TD", "");
//		List<Map<String, Object>> lists = healthController.findHealthNameAndScoreById("57", "HEALTH_3NET");
//		
//		System.out.println(lists.size());
//		
//		
//		//测试拓补
//		String xml = healthController.createTopo("HEALTH_3NET", "全省", "");
//		System.out.println(xml);
		
//		List<Map<String, String>> list = flowController.findBusinessVisitKeyKpiDetails("");
//		List<Map<String, String>> list = flowController.findResourceProvideKeyKpiDetails("");
//		List<Map<String, String>> list = flowController.findNetInOutResourceFlowDir("");
//		List<Map<String, String>> list = flowController.findNetInOtherProvinceIDCFlowDir("");
//		List<Map<String, String>> list = flowController.findResourceNodeFlowDir("");
//		List<Map<String, String>> list = flowController.findOtherProvinceIDCFlow("");

		/** 树形表格分页测试 created by zhengWei */
//		Page page = new Page();
//		page.setPageNo(1);
//		page.setPageSize(2);
//		List<FlowExitTree> flowExitTreeList = flowController.getTreeTable(page, null);
//		System.out.println(flowExitTreeList);

//		List<Map<String, String>> list = flowController.findCityVisitCTCCAndCUCCFlow("");
//		List<Map<String, String>> list = flowController.findBusiVisitResFlowIngredientForm("");
//		List<Map<String, String>> list = flowController.findBusiVisitIngredientFormInfo("");
//		List<Map<String, String>> list = flowController.findResourceNodeIngredientInfo("");
//		Page page = flowController.findFlowPropertyAnalyze("250", "");
//		List<Map<String, String>> list = flowController.findCityVisitCTCCAndCUCCFlow("");
//		Map<String, Object> list = flowController.findFlowExitCityContrast("249", "");
//		List<Map<String, String>> list = flowController.findFlowIngerdientTable("");
//		List<FsChartVo> list = flowController.getFlowIngerdientOhtChart("234", "");
//		List<Map<String, String>> list = flowController.findPerformanceView("250", "");
		
//		List<Map<String, Object>> list = flowController.findFlowExitLinkOverview("");
		
//		List<Map<String, String>> list = flowController.findFlowExitName();
		
//		System.out.println(list);
		
//		String xml = flowController.createTopo("BUSI_DIR", "BUSI_TYPE", "全部", "down","");
//		System.out.println(xml);
		/*String xml = flowController.createTopo("BUSI_DIR", "BUSI_TYPE", "全部", "down","");
		System.out.println(xml);*/
	
//		List<Map<String, Object>> list = qualityController.findCityTop200WebsiteTimedelay("");
//		List<Map<String, Object>> list = qualityController.findGroupHotspotTop10Net("");
//		List<Map<String, Object>> list = flowController.findFlowDetailClass("");
		
//		Tree tree = qualityCont
//		System.out.println(tree.getId()+"   "+tree.getSubTree().get(0).getId());
		
//		List<Map<String, Object>> list = hotspotResourceController.findHotspotVideoExitByVideoExitType("P2P视频", "省网出口", "");
//		Page pageList = hotspotResourceController.findIDCResTable(2, 4, "网内他省", "");
//		List<Map<String, Object>> list = hotspotResourceController.findHotspotWebTable("");
//		List<Map<String, Object>> list = hotspotResourceController.findHotspotP2PTable("");
//		Page pageList = hotspotResourceController.findHotspotFlow50Table(2, 4, "");
//		Page pageList = hotspotResourceController.findHotspotAcc50Table(2, 4, "");
//		Page pageList = hotspotResourceController.findHotspotPer70Table(2, 4, "");
//		Page pageList = hotspotResourceController.findHotspotDNS1000Table(2, 4, "");
//		Page pageList = hotspotResourceController.findHotspotDNSNOCMTable(2, 4, "");
//		Page pageList = hotspotResourceController.findHotspotResTable(2, 4, "");
//		List<FsChartVo> list = hotspotResourceController.findExitNetVideoFlowRank("P2P视频", "第三方出口", "总流量", "");
//		Map<String, Object> map = qualityController.findNetComplaintCSATCityContrast("460", "");
//		List<Map<String, Object>> list = qualityController.findBusinessName('QU_COMPLAINT');
//		List<Map<String, Object>> list = qualityController.findProvincialHotspotNetTable("443", "36", "");
//		List<Map<String, Object>> list = flowController.findFlowDetailClass("");
//		
//		
//		Tree tree = qualityController.buildBusiKqiNavTree("QU_BUSI_KQI");
//		System.out.println(tree.getId()+"   "+tree.getSubTree().get(0).getId());
//		List<Map<String, String>> list = healthController.getCityConstrastCharts("96", "HEALTH_3NET", "");
//		List<Map<String, Object>> list = flowController.findFlowTendencyAnalyze("249", "258");
//		Map<String, Object> list = qualityController.findKQICityComponent("KQI_BUSI", "975", "");
//		List<Map<String, Object>> list = qualityController.findKQICityMenu("QU_ACC_KQI");
//		List<Map<String, String>> list = qualityController.findAccKQIExitName("QU_ACC_EXIT_KQI", "2");
//		List<FsChartVo> list = qualityController.findAccKQIExitContrast("603", "");
		/*
		Page page = new Page();
		page.setPageNo(1);
		page.setPageSize(50);
		List<FlowExitTree> list = flowController.getTreeTable(page, "");
		*/
//		byte[] bt = flowController.flowIngredientExcelTable("");
//		byte[] bt = flowController.flowExitExcelTable("");
		//List<Map<String, String>> list = flowController.findResKpiHistogram("150", "");
//		List<Map<String, String>> list = flowController.findProvinceIDCFlowMap("176", "全部", "2014-04-01", "month");
//		qualityController.insertTest();
//		Map<String, Object> map = internetResourcesController.findTop10WebDomainCount();
//		List<Map<String, String>> list = internetResourcesController.findOtherWebDomainCount();
//		Map<String, Object> map = internetResourcesController.findOperatorDistributionCountBywebName("搜狐");
//		Map<String, Object> map = internetResourcesController.findDistrictDistributionBywebNameOperator("搜狐", "电信");
//		Page page = internetResourcesController.findOperatorDetailBywebNameOperatorTable("搜狐", "电信", 1, 20);
//		Page page = internetResourcesController.findDistrictwebNameOperatorAreaTable("搜狐", "电信", "福建省福州市", 1, 20);
//		Page page = internetResourcesController.findDomainLibraryTable("搜狐", "17173", "17", "福建", 1, 10);
//		List<Map<String, String>> list = internetResourcesController.findDistrictwebNameOperatorArea("中国网络电视台", "联通", "北京市");
//		byte[] bt = internetResourcesController.exportDomainLibraryExcelTable(null, null, null, "湖南");
//		Map<String, Object> list = internetResourcesController.findBusinessTotalCount();
//		Map<String, Object> list1 = internetResourcesController.findBusinessDetailsCountByBizName("音乐");
//		Map<String, Object> list = internetResourcesController.findBusinessDetailsInfoByKid("1");
		
		
//		List<Map<String, String>> list = busiBenchmarkController.findBusinessInfoByType("BUSI_BENCHMARK_LINE_KPI");
//		List<Map<String, String>> list = busiBenchmarkController.findEmphasisNeedConcernWebInfo("1015", "2014-11-13", "day", 1);
//		List<Map<String, Object>> list = busiBenchmarkController.findWebKpifloatDay30("1015", "http://www.letv.com/ptv/vplay/1877519.html", "2014-11-13");
//		List<Map<String, Object>> list = busiBenchmarkController.findNeedfocusWebKpiPasslineTOP3("1015", "2014-11-13", "day");
		/*String[] action = new String[2];
		action[0] = "上线";
		action[1] = "下线";
		String[] result = new String[2];
		result[0] = "失败";
		result[1] = "成功";*/
//		WlanLogParameter parameter = new WlanLogParameter(null,action,result,"2014-12-17 15:00","2014-12-17 16:10",1,10);
		
//		Page page = wlanLogAnalyseController.findPortalLogTable(parameter);
//		Page page1 = wlanLogAnalyseController.findMmlLogTable(parameter);
//		Page pageMml = wlanLogAnalyseController.findMmlRowTable("15801145678", "失败", "", "2014-12-04", 1, 40);
//		List<Map<String, String>> list = xdrAnalyseController.findDICInfo("CITY", null);
//		List<Map<String, String>> list = xdrAnalyseController.findWGNumberBydistrict("天门");
//		Page page = xdrAnalyseController.findWebFlowInfoTable("WLAN", "浏览", "2015-07-09", 1, 10);
//		Page page = xdrAnalyseController.findClickTopWebTable("2015-07-09", 1, 10);
//		Page page = xdrAnalyseController.findFlowTopWebTable("2015-07-09", 1, 10);
		
//		List<Map<String, String>> list = xdrAnalyseController.findUserClickRateWebTOP15("武汉", "WLAN", "浏览", "2015-07-09", "07");
//		byte[] by = wlanLogAnalyseController.exportPortalLogExcelTable(parameter);
//		byte[] by = wlanLogAnalyseController.exportMmlLogExcelTable(parameter);
		
		//List<Map<String, String>> list = xdrAnalyseController.findVisitQualityByCity("WLAN", "浏览", "2015-07-09");
//		List<Map<String, String>> list1 = xdrAnalyseController.findVisitQualityByCounty("1023", "1032", "36", "2015-01-20");
		//Page page1 = xdrAnalyseController.findSitesQualityTable("WLAN", "浏览", "2015-07-09", 1, 10);
//		Page page2 = xdrAnalyseController.findSitesQualityWarningTable("WLAN", "浏览", "2015-07-09", 1, 10);
		
//		List<Map<String, String>> list = portVideoBizController.findBizNetQualityViewByTime("BIZQUALITY", "month", "2015-03-12");
//		List<Map<String, String>> list = portVideoBizController.findBizNetQualityCityContrastViewByTime("BIZQUALITY", "day", "2015-04-14");
//		List<Map<String, String>> list = portVideoBizController.findBizNetQualityVideoWebTop3ByTime("BIZQUALITY", "day", "2015-04-13","asc");
//		List<Map<String, String>> list = portVideoBizController.findBizNetQCMicroViewByTime("BIZQUALITY", "day", 21, "2015-04-14");
//		List<Map<String, String>> list = portVideoBizController.findVideoWebProblemNumberByTime("day", "21", "移动", null, "2015-04-14");
//		List<Map<String, String>> list = portVideoBizController.findNetPoorQualityByTime("week", "2015-04-12");
//		Map<String, Object> map = portVideoBizController.findBizQualityTableByTime("day", "21", "移动", "2015-04-21");
//		Map<String, Object> map = portVideoBizController.findNetQualityProblemCountByTime("day", "2015-04-19");
//		List<Map<String, String>> list = portVideoBizController.findNetQualityMicroByTime("day", "21", "移动", "1148", "56", "2015-04-21");
//		List<Map<String, String>> list = portVideoBizController.findNetQualityPoorVideoByTime("NETQUALITY", "day", "21", "移动", "2015-04-13");
//		String xml = portVideoBizController.findPartDirectionQualityTopo("day", "21", "1148", "移动", "2015-04-21", "top");
//		List<Map<String, Object>> list = healthController.findHealthNameScoreByTime("month", "HEALTH_4NET", "1205", 2, "2015-03-01");
		List<Map<String, String>> list = qualityController.findQualityKPIInfo("day", "2015-12-20");
		
	}
	
}
