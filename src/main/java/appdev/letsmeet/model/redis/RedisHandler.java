/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.model.redis;

import appdev.letsmeet.control.utils.jsonBeans.LoginUserBean;
import java.util.List;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.SortingParams;

/**
 *
 * @author leppa
 */
public class RedisHandler {
    
    private static RedisHandler redisHandlerInstance;
    private static final JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
    private final String loggedInUsers = "loggedInUsers";
    
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

    public void deleteKey(String key) {
        pool.getResource().del(key);
    }
    
    private void sortList(String list, SortingParams sortParams) {
        pool.getResource().sort(list, sortParams);
    }
    
    public List<String> getCategoryList() {
        return pool.getResource().lrange(RedisProperties.categoryList, 0, -1);
    }

    public void createCategoryList(List<String> cat) {
        deleteCategoryList();
        listLPush(RedisProperties.categoryList, cat);
        sortList(RedisProperties.categoryList, new SortingParams().alpha());
    }
    
    public void deleteCategoryList() {
        deleteKey(RedisProperties.categoryList);
    }

    public void addLoggedInUser(int userID) {
        Jedis j = pool.getResource();
        j.sadd(loggedInUsers, Integer.toString(userID));
    }
    
    public Boolean isLoggedInUser(int userID) {
        Jedis j = pool.getResource();
        return j.sismember(loggedInUsers, Integer.toString(userID));
    }
    
    public void removeLoggedInUser(int userID) {
        Jedis j = pool.getResource();
        j.srem(loggedInUsers, Integer.toString(userID));
    }

}
