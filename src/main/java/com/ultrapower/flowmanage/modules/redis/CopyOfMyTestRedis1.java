package com.ultrapower.flowmanage.modules.redis;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

@SuppressWarnings("unchecked")
public class CopyOfMyTestRedis1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:config/junit_applicationContext.xml");
		RedisTemplate redisTemplate = (RedisTemplate)context.getBean("jedisTemplate");
		//其中key采取了StringRedisSerializer
		//其中value采取JdkSerializationRedisSerializer
		/*ValueOperations<String, User> valueOper = redisTemplate.opsForValue();
		User u1 = new User("zhengwei", 30);
		User u2 = new User("yuanxihua", 25);
		valueOper.set("user1", u1);
		valueOper.set("user2", u2);
		System.out.println(valueOper.get("user1").getName());
		System.out.println(valueOper.get("user2").getName());*/

		/*User u3 = new User("zhengwei", 30);
		User u4 = new User("yuanxihua", 25);
		ListOperations<String, User> listOper = redisTemplate.opsForList();
		listOper.leftPush("user3", u3);
		listOper.leftPush("user4", u4);
		System.out.println(listOper.leftPop("user4").getName());*/
		
		
		RedisTemplate<String, User> template3 = (RedisTemplate<String, User>)context.getBean("jedisTemplate");    
        BoundListOperations<String, User> listBounds = template3.boundListOps("eee"); 

        /*User u1 = new User("zhengwei", 30);
		User u2 = new User("yuanxihua", 25);
		
        listBounds.rightPush(u1);    
        listBounds.rightPush(u2);*/    

        BoundListOperations<String, User> listBounds2 = template3.boundListOps("eee");
        listBounds2.expire(120, TimeUnit.SECONDS);
        
        for (int i = 0; i < listBounds2.size(); i++) {
        	System.out.println(listBounds2.index(i).getName());	
        }
        //System.out.println(listBounds2.size());
        
/*        System.out.println(listBounds2.rightPop());
        System.out.println(listBounds2.rightPop());
        System.out.println(listBounds2.rightPop());
        System.out.println(listBounds2.rightPop());*/
        
        
        BoundSetOperations<String, User> set1 = template3.boundSetOps("setkey1");
        User u1 = new User("zhengwei", 30);
        /*set1.add(u1);
        set1.add(u1);
        set1.add(u1);*/
        System.out.println(set1.size());
        
        
        BoundZSetOperations<String, User> zset1 =  template3.boundZSetOps("zsetkey1");
        User u2 = new User("aaaaa", 10);
        User u3 = new User("bbbbb", 5);
        
        //zset1.add(u1, 6);
        //zset1.add(u2, 1);
       // zset1.add(u3, 3);
        
        Set<TypedTuple<User>> zrangeSet = zset1.rangeWithScores(0, -1);
        for (TypedTuple<User> ttUser: zrangeSet) {
        	User user = ttUser.getValue();
        	System.out.println(user.getName() + "|" + ttUser.getScore());
        	
        }
       
        


	}

}
