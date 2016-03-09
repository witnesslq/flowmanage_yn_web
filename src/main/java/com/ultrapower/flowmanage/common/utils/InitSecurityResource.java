package com.ultrapower.flowmanage.common.utils;

import java.io.File;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ultrapower.accredit.api.SecurityService;
import com.ultrapower.accredit.rmiclient.RmiClientApplication;


/**
 * 
 * @author jf_Wen 初始化pasm系统，包括初始化resources和accessType文件以及资源信息
 * @since 2011-6-29
 */
public class InitSecurityResource extends HttpServlet{

	private static final Logger log = LoggerFactory.getLogger(InitSecurityResource.class);
	//SecurityService securityService = RmiClientApplication.getInstance().getSecurityService();

	public InitSecurityResource(){
		init();
	}
	public void initRes() {/*
		if (SysProperties.IS_SECURITY) {
			// 判断是否要权限并导入资源到pasm
			if (SysProperties.IS_SENDRESTOPASM) {
				log.info("<<<<<<<<<<<<初始化权限系统--开始");
				File file = new File(Path.CONF_PATH + "security");
				log.info("<<<<<<<<<<<<读取并导入ULTRAESS_pp.xml文件--开始");
				PasmResourceParser.initResourceByAppClient(file,
						SysProperties.APP_NAME);
				log.info("<<<<<<<<<<<<读取并导入ULTRAESS_pp.xml文件--结束");
				// 开始加载动态资源，主要是区域信息
				List<BaseResource> baseResourceList = (ArrayList<BaseResource>) (CommonConstants.COMMON_CONSTANTS
						.get("REGION"));
				BaseResource region = null;
				if (baseResourceList != null && baseResourceList.size() > 0) {
					region = baseResourceList.get(0);
				}
				if (region != null) {
					// UlTRAESS0201对应的是ULTRAESS_pp.xml文件中要挂区域的目录id
					List<BaseResource> regions = new ArrayList<BaseResource>();
					regions.add(region);
					importRegionToPasm("UlTRAESS0201", regions);
				} else {
					log.warn("没有查到区域信息，不导入到权限系统中。");
				}

				// 导入通讯车资源信息到权限库
				List<BaseResource> vehicleBelongResources = (ArrayList<BaseResource>) (CommonConstants.COMMON_CONSTANTS
						.get("VEHICLE_BELONG"));

				if (vehicleBelongResources != null
						&& vehicleBelongResources.size() > 0) {
					importRegionToPasm("UlTRAESS0202", vehicleBelongResources);

				} else {
					log.warn("没有查到通讯车归属信息，不导入到权限系统中。");
				}

				// 导入油机资源信息到权限库
				List<BaseResource> oilBelongResources = (ArrayList<BaseResource>) (CommonConstants.COMMON_CONSTANTS
						.get("OIL_BELONG"));

				if (oilBelongResources != null && oilBelongResources.size() > 0) {
					importRegionToPasm("UlTRAESS0203", oilBelongResources);

				} else {
					log.warn("没有查到油机归属信息，不导入到权限系统中。");
				}

				log.info("<<<<<<<<<<<<初始化权限系统--结束");
			} else {
				log.info("系统配置不导入资源到权限系统中。");
			}
		} else {
			log.info("系统配置不使用权限系统。");
		}

	*/}

	/**
	 * 递归将区域信息导入到pasm系统中
	 * 
	 * @param parentId
	 */
	/*private void importRegionToPasm(String parentId, List<BaseResource> regions) {
		log.debug("parentId=" + parentId);
		if (regions != null && regions.size() > 0) {
			for (BaseResource region : regions) {
				Resource res = new Resource();
				res.setResourceId(String.valueOf(region.getItemId()));
				res.setName(region.getName());
				res.setSuperId(parentId);
				res.setAccessType("isedit");
				res.setLoadType(0);
				res.setPpType(0);
				res.setAppName(SysProperties.SYS_APP_NAME);
				// 导入到权限系统中
				RmiClientApplication.getInstance().addResource(res);
				// 开始处理其子节点
				importRegionToPasm(String.valueOf(region.getItemId()), region
						.getChildren());
			}
		}

	}*/

	public void init() {
//		if (SysProperties.IS_SECURITY) {
//			// 判断是否要权限并导入资源到pasm
//			if (SysProperties.IS_SENDRESTOPASM) {
//				log.info(String.valueOf(SysProperties.IS_SENDRESTOPASM));
		log.info("<<<<<<<<<<<<准备初始化权限系统");
		log.info("chushihuaquanxian");
		 if(Common.IS_SENDRESTOPASM){
			 log.info("<<<<<<<<<<<<初始化权限系统--开始" + Common.IS_SENDRESTOPASM);
			 log.info("chushihuaquanxianStart" + Common.IS_SENDRESTOPASM);
				String path = "/"+Thread.currentThread().getContextClassLoader().getResource("/").toString();
				path = path.replace("file:/","");
				System.out.println("**********************path:"+path);
				File file = new File(path+"FLOWMANAGE_dimension_hubei.xml");
//				File file = new File("E:\\ultrapower\\湖南移动\\综合监控\\域权限\\demension\\ TOMAS_dimension.xml");
				log.info("<<<<<<<<<<<<开始-初始化FLOWMANAGE_dimension_hubei.xml文件");
				try {
					boolean b = RmiClientApplication.getInstance().initDimensionByApp("FLOWMANAGE_HUBEI", file);
//				int b = RmiClientApplication.getInstance().initResourceByApp("FLOWMANAGE");
//				int b = RmiClientApplication.getInstance().initResourceByApp("NMS");
					if(b){
						log.info("<<<<<<<<<<<<成功-初始化FLOWMANAGE_dimension_hubei.xml文件");
						log.info("chushihuaquanxianSuccess");
					}else{
						log.error("失败-未能初始化权限配置文件FLOWMANAGE_dimension_hubei.xml");
						log.info("chushihuaquanxianFailure");
						return;
					}
				} catch (Exception e) {
					log.error("失败-未能初始化权限配置文件FLOWMANAGE_dimension_hubei.xml");
					log.info("chushihuaquanxianFailureFailureFailure");
					e.printStackTrace();
					return;
				}
		 }
				
				
				/*Map<String, List<BaseResource>> map = (Map<String, List<BaseResource>>) CommonConstants.COMMON_CONSTANTS;
				List<BaseResource> regions = map.get(SysProperties.REGION);
				
				if (regions != null && regions.size() > 0) {
					String code = SysProperties.CODE_PRE + SysProperties.REGION;
					String scopeName = "区域";
					DimensionScope scopeBool = setDeminsionScope(regions, code,
							scopeName);
					if (scopeBool == null) {
						log.warn("无法增加域值范围");
						return;
					}
					List<Operation> operations = getOperations();
					ObjectClass objClass = setObjClass(SysProperties.OBJID_REGION, "网元业务对象", "网元",
							operations);
					if (objClass != null) {
						setDimension(code, scopeBool.getId(),"网元域", objClass.getId());
					}

				} else {
					log.warn("没有查到区域信息，不导入到权限系统中。");
				}

				List<BaseResource> oilBelongs = map.get(SysProperties.OIL_BELONG);
				
				if (oilBelongs != null && oilBelongs.size() > 0) {
					String code = SysProperties.CODE_PRE + SysProperties.OIL_BELONG;
					String scopeName = "油机归属关系";
					DimensionScope scopeBool = setDeminsionScope(oilBelongs, code,
							scopeName);
					if (scopeBool==null) {
						log.warn("无法增加域值范围");
						return;
					}
					List<Operation> operations = getOperations();
					ObjectClass objClass = setObjClass(SysProperties.OBJID_OIL, "油机业务对象", "油机管理", operations);
					if (objClass != null) {
						setDimension(code, scopeBool.getId(),"油机归属域", objClass.getId());
					}
				} else {
					log.warn("没有查到油机归属信息，不导入到权限系统中。");
				}

				List<BaseResource> vehicleBelongs = map
						.get(SysProperties.VEHICLE_BELONG);
				if (vehicleBelongs != null && vehicleBelongs.size() > 0) {
					String codeVehicle = SysProperties.CODE_PRE + SysProperties.VEHICLE_BELONG;
					String scopeName = "通信车和人员归属关系";
					DimensionScope scopeBool = setDeminsionScope(vehicleBelongs, codeVehicle,
							scopeName);
					if (scopeBool == null) {
						log.warn("无法增加域值范围");
						return;
					}
					List<Operation> vehicleOP = getOperations();
					ObjectClass vehicleObj = setObjClass(SysProperties.OBJID_VEHICLE, "通信车业务对象", "通信车管理", vehicleOP);
					if (vehicleObj != null) {
						setDimension(codeVehicle, scopeBool.getId(),"通信车归属域", vehicleObj.getId());
					}
					
					String codePerson = SysProperties.CODE_PRE + "PERSON";
					List<Operation> personOP = getOperations();
					ObjectClass personObj = setObjClass(SysProperties.OBJID_PERSON, "人员管理业务对象", "人员管理", personOP);
					if (personObj != null) {
						setDimension(codePerson, scopeBool.getId(),"人员归属域", personObj.getId());
					}
					
				} else {
					log.warn("没有查到通讯车归属信息，不导入到权限系统中。");
				}

				log.info("<<<<<<<<<<<<初始化权限系统--结束");
			} else {
				log.info("系统配置不导入资源到权限系统中。");
			}*/
		/*} else {
			log.info("系统配置不使用权限系统。");
		}*/
	}

/*	private void setDimension(String code, String scopeId,String name, String objId) {
		Dimension dimension = new Dimension();
		dimension.setCode(code);
		dimension.setId(UUIDGenerater.generate());
		dimension.setDscopeId(scopeId);
		dimension.setName(name);
		dimension.setObjectClassId(objId);
		try {
			securityService.addDimension(dimension);
		} catch (Exception e) {
			log.error(SysProperties.SYS_APP_NAME + "应用中，添加域失败!域名称为" + name
					+ ",code为" + code);
			e.printStackTrace();
		}
	}
*/
	/*private ObjectClass setObjClass(String id, String desc, String name,
			List<Operation> operations) {
		ObjectClass objClass = new ObjectClass();
		objClass.setAppName(SysProperties.SYS_APP_NAME);
		objClass.setId(id);
		objClass.setDesc(desc);
		objClass.setName(name);
		objClass.setOperations(operations);
		try {
			boolean b = securityService.addObjectClass(objClass);
			if (b)
				return objClass;
		} catch (Exception e) {
			log.error(SysProperties.SYS_APP_NAME + "应用中，添加对象类失败!对象类名称为" + name
					+ ",id为" + id);
			e.printStackTrace();
		}
		return null;
	}*/

/*	private DimensionScope setDeminsionScope(List<BaseResource> resources, String scopeId,
			String scopeName) {
		DimensionScope dimensionScope = new DimensionScope();
		dimensionScope.setAppname(SysProperties.SYS_APP_NAME);
		dimensionScope.setCode(scopeId);
		dimensionScope.setId(UUIDGenerater.generate());
		dimensionScope.setName(scopeName);
		try {
			boolean b = securityService.addDimensionScope(dimensionScope);
			if (b) {
				log.info("域值范围插入成功。"+dimensionScope.getAppname()+","+dimensionScope.getCode()+
						","+dimensionScope.getName()+","+dimensionScope.getId()+","+dimensionScope.getType());
				setDeminsionValue(resources, dimensionScope.getId());
				return dimensionScope;
			} else {
				log.error(SysProperties.SYS_APP_NAME + "应用中，添加域值范围失败!域值范围名称为"
						+ scopeName + ",code为" + scopeId);
			}
		} catch (Exception e) {
			log.error(SysProperties.SYS_APP_NAME + "应用中，添加域值范围失败!域值范围名称为"
					+ scopeName + ",code为" + scopeId);
			e.printStackTrace();
		}
		return null;
	}*/

	/*private List<Operation> getOperations() {
		List<Operation> ops = new ArrayList<Operation>();
		Operation viewOP = new Operation();
		viewOP.setCode("view");
		viewOP.setName("查看");
		viewOP.setType("select");
		viewOP.setId(UUIDGenerater.generate());

		Operation operation = new Operation();
		operation.setCode("operation");
		operation.setName("操作");
		operation.setType("operation");
		operation.setId(UUIDGenerater.generate());
//		operation.setDependId(viewOP.getCode());
		ops.add(viewOP);
		ops.add(operation);
		return ops;
	}*/

	/*private void setDeminsionValue(List<BaseResource> resources, String scopeId) {
		if(resources != null && resources.size()>0){
			for(BaseResource resource : resources){			
				if (resource != null) {
					DimensionValue dimensionValue = new DimensionValue();
					dimensionValue.setCode(resource.getItemIdStr());
					dimensionValue.setDscopeId(scopeId);
					dimensionValue.setId(resource.getItemIdStr());
					dimensionValue.setName(resource.getName());
					String parentId = resource.getParentIdStr();
					if (parentId != null && (!parentId.isEmpty())) {
						dimensionValue.setParentId(parentId);
					}
//					log.info("code:" + resource.getItemIdStr() + ",dscopeid:" + scopeId
//							+ ",parentId:" + parentId);
					try {
						securityService.addDimensionValue(dimensionValue);
					} catch (Exception e) {
						log.error(SysProperties.SYS_APP_NAME + "应用中，添加域值失败!域值名称为"
								+ dimensionValue.getName() + ",code为"
								+ dimensionValue.getCode());
						e.printStackTrace();
					}

					setDeminsionValue( resource.getChildren(),scopeId);

				}
			}// end of for loop			
		}		
	}*/
	
	public static void main(String[] args) {
		InitSecurityResource i = new InitSecurityResource();
		i.init();
	}
		
}
