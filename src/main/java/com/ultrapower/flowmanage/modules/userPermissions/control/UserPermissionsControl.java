package com.ultrapower.flowmanage.modules.userPermissions.control;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;

import com.ultrapower.accredit.api.SecurityService;
import com.ultrapower.accredit.common.value.dimension.Menu;
import com.ultrapower.accredit.rmiclient.RmiClientApplication;
import com.ultrapower.accredit.util.GetUserUtil;
import com.ultrapower.flowmanage.modules.userPermissions.service.UserPermissionsService;

import flex.messaging.FlexContext;

/**
 * 用户权限控制器层
 * @author yuanxihua
 *
 */
@Service("userPermissionsControl")
@RemotingDestination(channels = {"my-amf"})
public class UserPermissionsControl {
	private static Logger logger = Logger.getLogger(UserPermissionsControl.class);
	
	@Resource(name="userPermissionsService")
	private UserPermissionsService userPermissionsService;
	
	/**
	 * 初始化PASM的登陆用户的权限菜单树
	 * @author Yuanxihua
	 * @return
	 */
	public boolean initUserPermissions() {
		try {
			logger.info("***control层pasm开始调用初始化权限方法***");
			userPermissionsService.initUserPermissions();
			logger.info("***control层pasm调用初始化权限方法完毕***");
		} catch (Exception e) {
			logger.error("权限初始化失败!");
			return false;
			
		}
		return true;
	}

	
	/**
	 * 根据访问页面URL验证用户权限
	 * @author Yuanxihua
	 * @param pageUrl 访问页面URL
	 * @return
	 */
	public Object validateUserPermissions(String pageUrl){
		Object object = null;
		try {
			object = userPermissionsService.validateUserPermissions(pageUrl);
		} catch (Exception e) {
			System.out.println("根据访问页面URL验证用户权限失败"+e.getMessage());
			e.printStackTrace();
		}
		return object;
	}

}
