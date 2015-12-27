/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.model.redis;

import java.util.List;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

/**
 *
 * @author leppa
 */
public class RedisHandler {
    
    private static RedisHandler redisHandlerInstance;
    private static final JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
    
    private RedisHandler() {
        System.out.println("RedisHandler instance created.");
    };
    
    public static RedisHandler getInstance() {
        
        if (redisHandlerInstance == null) {
            synchronized(RedisHandler.class) {
                if (redisHandlerInstance == null) {
                    redisHandlerInstance = new RedisHandler();
                }
            }
        }
        return redisHandlerInstance;
    }
    
    public Boolean addSet(String key, String value) {
        Jedis j = pool.getResource();
        return (j.sadd(key, value) > 0) ;
    }
    
    public void listLPush(String key, List<String> list) {
        Pipeline p = pool.getResource().pipelined();
        list.forEach(l -> p.lpush(key, l));
        p.sync();
    }
    
}
