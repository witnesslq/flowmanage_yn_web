package com.ultrapower.flowmanage.modules.userPermissions.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.accredit.api.SecurityService;
import com.ultrapower.accredit.common.value.dimension.DimensionCondition;
import com.ultrapower.accredit.common.value.dimension.DimensionPermission;
import com.ultrapower.accredit.common.value.dimension.DvalueCondition;
import com.ultrapower.accredit.common.value.dimension.Menu;
import com.ultrapower.accredit.common.value.dimension.Rule;
import com.ultrapower.accredit.rmiclient.RmiClientApplication;
import com.ultrapower.accredit.util.GetUserUtil;
import com.ultrapower.flowmanage.modules.userPermissions.dao.UserPermissionsDAO;
import com.ultrapower.flowmanage.modules.userPermissions.vo.UserInfo;

import flex.messaging.FlexContext;
import flex.messaging.MessageException;

/**
 * 用户权限业务层
 * @author yuanxihua
 *
 */
@Service("userPermissionsService")
public class UserPermissionsService {
private static Logger logger = Logger.getLogger(UserPermissionsService.class);
	@Resource(name="userPermissionsDAO") 
	private UserPermissionsDAO userPermissionsDAO;

	@Value(value="#{'${appName}'}")
	private String appName; 
	 
	@Value(value="#{'${pasm.UserPermission}'}")
	private String userPermissionFlag; 
	
	// 获取PASM用户名
	public String getUsername() throws Exception {
		logger.info("获取当前用户权限==========================");
		String userName = "-1";
		if(userPermissionFlag.toString().indexOf("true") > -1){
			userName = GetUserUtil.getUsername();
			if (userName == null) {
				throw new MessageException("session time out");
			}
		}
	    
		logger.info("当前权限=========================="+userName);
		return userName;
	}

	public DimensionPermission getDimenPerm() throws Exception {
		SecurityService securityService = RmiClientApplication.getInstance().
			getSecurityService();
		return securityService.getRulePermissions(getUsername(), appName);
	}
	
	public boolean isAdmin() throws Exception {
		return getDimenPerm().isAdmin();//正式部署应取消注释
	}
	/**
	 * 调用PASM系统提供的接口,获得用户权限，并初始化到内存数据库
	 * created by yuanxihua
	 */
	public void initUserPermissions() throws Exception {
		//if(userPermissionFlag.get("pasm.UserPermission").toString().indexOf("true")!= -1){
		//}
		try {
			logger.info("***service层pasm开始准备获得用户登陆名***");
			String userID = getUsername();
			logger.info("***service层pasm用户登陆名***" + userID);
			
			String temp = "";
			Set<String> busiOperSet = null;
			Set<String> regionOperSet = null;
			Map<String, Set<String>> operMap = new HashMap<String, Set<String>>();
			DimensionPermission dimensionPermission = getDimenPerm();
			
			logger.info("***service层pasm管理员前***");
			if (dimensionPermission.isAdmin()) return;
			logger.info("***service层pasm管理员后***");
			
			List<Rule> ruleList = dimensionPermission.getRuleList();
			for (Rule rule: ruleList) {
				List<DimensionCondition> dimensionConditionList = rule.getDimensionConditionList();
				if (dimensionConditionList.size() > 0) {
					for (DimensionCondition dimensionCondition: dimensionConditionList) {
						List<DvalueCondition> dvalueConditionList = dimensionCondition.getdValueConditionList();
						for (DvalueCondition dvalueCondition: dvalueConditionList) {
							temp = dvalueCondition.getdValueId();
							if ("quansheng".equals(temp)) temp = "全省";
							else if ("shengji".equals(temp)) temp = "省级";
							else if ("kuasheng".equals(temp)) temp = "跨省";
							else if ("shiji".equals(temp)) temp = "市级";
							if ("省级".equals(temp) || "跨省".equals(temp) || "全省".equals(temp)) {
								if (operMap.containsKey("busiarea")) {
									operMap.get("busiarea").add(temp);
								} else {
									busiOperSet = new HashSet<String>();
									busiOperSet.add(temp);
									operMap.put("busiarea", busiOperSet);
								}	
							} else if (!"市级".equals(temp)) {
								if (operMap.containsKey("region")) {
									operMap.get("region").add(temp);
								} else {
									regionOperSet = new HashSet<String>();
									regionOperSet.add(temp);
									operMap.put("region", regionOperSet);
								}
							}	
						}
						
					}
				}
				
			}
			
			logger.info("***service层初始化获取pasm用户接口权限准备写入内存数据库***" + operMap);
			
			initUserPermissions(userID, operMap);
		} catch (Exception e) {
			logger.error("权限初始化失败！"+e);
			throw e;
		}
	
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class, readOnly=false)
    public void initUserPermissions(String userID, Map<String, Set<String>> operMap) throws Exception {
    	userPermissionsDAO.initUserPermissions(userID, operMap);
    }
    	
	//获取用户权限
	public Map<String, Object> getUserPermissions() throws Exception {
		return userPermissionsDAO.getUserPermissions(getUsername());	
	}
	

	/**
	 * 根据访问页面URL验证用户权限
	 * @param pageUrl 访问页面URL
	 * @return
	 */
	public Object validateUserPermissions(String pageUrl) throws Exception{
		UserInfo user = new UserInfo();
		HttpServletRequest request = FlexContext.getHttpRequest();
		user.setUsername(this.getUsername());
		if(this.getUsername().equals("-1")){
			user.setUrlExitFlag(true);
			if(pageUrl.indexOf("swGis") != -1){
				user.setUrl(pageUrl);
			}else{
				user.setUrl(pageUrl.substring(5));
			}
			request.getSession().setAttribute("user", user);
			return user;
		}
		if(this.isAdmin()){
			System.out.println("当前登录用户：超级管理员 "+this.getUsername());
			user.setUrlExitFlag(true);
			if(pageUrl.indexOf("swGis") != -1){
				user.setUrl(pageUrl);
			}else{
				user.setUrl(pageUrl.substring(5));
			}
		}else{
			SecurityService securityService = RmiClientApplication.getInstance().getSecurityService();
			List<Menu> menu = securityService.getMenuPermissions(this.getUsername(), appName);//获取域权限菜单
			for (Menu mu : menu) {
				if(pageUrl.equals(mu.getUrl())){
					user.setUrlExitFlag(true);
					if(pageUrl.indexOf("swGis") != -1){
						user.setUrl(pageUrl);
					}else{
						user.setUrl(pageUrl.substring(5));
					}
					break;
				}
			}
			
			/*
			List<com.ultrapower.accredit.common.value.Resource>  res = securityService.getResourcesByLogin(this.getUsername());//获取资源菜单
			for (com.ultrapower.accredit.common.value.Resource resource : res) {
				if(pageUrl.equals(resource.getUrl())){
					user.setUrlExitFlag(true);
					if(pageUrl.indexOf("swGis") != -1){
						user.setUrl(pageUrl);
					}else{
						user.setUrl(pageUrl.substring(5));
					}
					break;
				}
			}
			*/
		}
		if(!user.isUrlExitFlag()){
			user.setErre("您当前登录的"+this.getUsername()+"没有访问当前页面的权限，请重新设置权限!");
		}
		request.getSession().setAttribute("user", user);
		return user;
	}
}
