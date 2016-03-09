package com.ultrapower.flowmanage.modules.redis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

//@Resource
public class InitCache {
	/**
	 * redis初始化缓存
	 * author zhengWei
	 */
	@Autowired
	private RedisTemplate<String, User> jedisTemplate;

	//web服务器启动时, 生成类的实例, 并对资源数据进行缓存
	public void init(){
		System.out.println("缓存数据 start ..........");
		
        BoundZSetOperations<String, User> zset1 =  jedisTemplate.boundZSetOps("zsetkey1");
        User u1 = new User("zzzzz", 30);
        User u2 = new User("aaaaa", 10);
        User u3 = new User("bbbbb", 5);
        
        zset1.add(u1, 6);
        zset1.add(u2, 1);
        zset1.add(u3, 3);
        
        BoundHashOperations<String, String, User> bhash1 = jedisTemplate.boundHashOps("bhash1");
        Map<String, User> map1 = new HashMap<String, User>();
        map1.put("user1", u1);
        map1.put("user2", u2);
        map1.put("user3", u3);
        bhash1.putAll(map1);
        
        
        
        
        
        System.out.println("是否设值："+jedisTemplate.hasKey("zsetkey1"));
        //zset按成员分数值排序
        Set<TypedTuple<User>> zrangeSet = zset1.rangeWithScores(0, -1);
        for (TypedTuple<User> ttUser: zrangeSet) {
        	User user = ttUser.getValue();
        	//System.out.println(user.getName() + "|" + ttUser.getScore());	
        }
        
        System.out.println("缓存数据 end ..........");
	}
}
