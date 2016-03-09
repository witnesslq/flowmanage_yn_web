package com.ultrapower.flowmanage.modules.redis;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

@SuppressWarnings("unchecked")
public class MyTestRedis1 {
	/**
	 * 从redis缓存中读取数据
	 * author zhengWei
	 */
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:config/junit_applicationContext.xml");
		RedisTemplate<String, Object> redisTmp = (RedisTemplate<String, Object>)context.getBean("jedisTemplate");
		RedisTemplate<String, User> redisTemplate = (RedisTemplate<String, User>)context.getBean("jedisTemplate");    
		System.out.println("读取redis中的缓存数据 start ..........");
        BoundZSetOperations<String, User> zset1 =  redisTemplate.boundZSetOps("zsetkey1");
        /** 如果已经缓存了该键值的数据, 立刻从缓存读取 */
        if (zset1.size() > 0) {
            Set<TypedTuple<User>> zrangeSet = zset1.rangeWithScores(0, -1);
            for (TypedTuple<User> ttUser: zrangeSet) {
            	User user = ttUser.getValue();
            	System.out.println(user.getName() + "|" + ttUser.getScore());
            }
        }
        
        
        //*********boundHash
        BoundHashOperations<String, String, User> bhash1 = redisTemplate.boundHashOps("bhash1");
        Map<String, User> map1 = new HashMap<String, User>();
        User u1 = new User("xxxxx", 30);
        User u2 = new User("yyyy", 10);
        map1.put("user1", u1);
        map1.put("user2", u2);
        bhash1.putAll(map1);
        u1 = new User("Sarlly1", 100);
        u2 = new User("Sarlly2", 200);
        map1.put("user1", u1);
        map1.put("user2", u2);
        for (Entry<String, User> entry: bhash1.entries().entrySet()) {
        	String str = entry.getKey();
        	User user = entry.getValue();
        	System.out.println(""+str + user.getName() + user.getAge());
        }
        
        redisTemplate.delete("aa");
        
        //********boundHash
        BoundValueOperations<String, Object> value = redisTmp.boundValueOps("name");
        System.out.println("name值是否存在："+redisTmp.hasKey("bhash1")+"  hashCode: "+redisTemplate.hashCode());
        
        System.out.println("读取redis中的缓存数据 end ..........");
        
        BoundHashOperations<String, String, Object> users = redisTmp.boundHashOps("users");
        Map<String, Object> map = new HashMap<String, Object>();
        Set<String> set1 = new HashSet<String>();
        Set<String> set2 = new HashSet<String>();
        set1.add("111");
        set2.add("222");
        map.put("aa", set1);
        map.put("bb", set1);
        users.putAll(map);
        
        BoundValueOperations<String, Object> bvalue = redisTmp.boundValueOps("value");
        bvalue.set(13);
        System.out.println("###################");
	}

}
