/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.model.redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

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
}
