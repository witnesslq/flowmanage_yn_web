package com.ultrapower.flowmanage.modules.userPermissions.dao;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.ultrapower.flowmanage.modules.userPermissions.service.UserPermissionsService;

/**
 * 用户权限DAO层
 * @author yuanxihua
 *
 */
@Service("userPermissionsDAO")
public class UserPermissionsDAO {
	private static Logger logger = Logger.getLogger(UserPermissionsDAO.class);
	
	//@Autowired
	private RedisTemplate<String, Object> jedisTemplate;
	
	@Resource(name="userPermissionsService")
	private UserPermissionsService userPermissionsService;
	
	/**
	 * 初始化用户的数据权限
	 * created by yuanxihua
	 */
	public void initUserPermissions(String userID, Map<String, Set<String>> operMap) throws Exception {
		logger.info("***Dao层初始化数据pasm用户登陆名***" + userID);
		jedisTemplate.delete("userInfo");
		BoundHashOperations<String, String, Object> bhash = jedisTemplate.boundHashOps("userInfo");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("busiarea", new HashSet<String>());
		map.put("region",  new HashSet<String>());
		if (operMap.containsKey("busiarea")) {
		    Set<String> busiareaOperSet = operMap.get("busiarea");
		    logger.info("***Dao层初始化pasm用户省级跨省权限***" + busiareaOperSet);
		    
		    int busiareaOperSize = busiareaOperSet.size();
		    
		    //初始化省级和跨省权限
		    if (busiareaOperSize > 0 ) {
		    	map.put("busiarea", busiareaOperSet);
		    }
		}
		
		logger.info("***Dao层初始化pasm用户市级权限***");
		
		if (operMap.containsKey("region")) {
		    Set<String> regionOperSet = operMap.get("region");
		    logger.info("***Dao层初始化pasm用户市级权限***" + regionOperSet);
		    
			int regionOperSize = regionOperSet.size();
			
			//初始化市级权限
			if (regionOperSize > 0) {
				map.put("region", regionOperSet);
			}
		}
		bhash.putAll(map);
		//getUserPermissions("yuanxihua");
	}
	
	public Map<String, Object> getUserPermissions(String userID)  throws Exception {
		logger.info("***Dao层获取权限pasm用户登陆名***" + userID);
		BoundHashOperations<String, String, Object> bhash = jedisTemplate.boundHashOps("userInfo");
		Map<String, Object> operMap = new HashMap<String, Object>();
		operMap.put("isAdmin", false);//设置管理员标识，false=普通用户，true=超级管理员
		if(userID == null){//用户是否失效
			bhash.delete("userInfo");//删除缓存中的Key
			return null;
		}
		//是否为超级管理员
		if(userPermissionsService.isAdmin()){
			operMap.put("isAdmin", true);
			logger.info("****当前登录用户权限角色为超级管理员****");
		}else{
			logger.info("****当前登录用户权限角色为普通用户****");
			for(Entry<String, Object> entry: bhash.entries().entrySet()){
				operMap.put(entry.getKey(), entry.getValue());
			}
			
			Set<String> busiarea = (Set<String>)operMap.get("busiarea");
			Set<String> region = (Set<String>)operMap.get("region");
			for(String str: busiarea){
				System.out.print(str+"  ");
			}
			for(String str: region){
				System.out.print(str+"  ");
			}
			
		}
		
		return operMap;
	}

}
