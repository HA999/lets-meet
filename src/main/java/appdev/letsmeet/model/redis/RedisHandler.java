/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdev.letsmeet.model.redis;

import appdev.letsmeet.control.utils.jsonBeans.SubCategoryBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

/**
 *
 * @author HANAN&OLYA
 */
public class RedisHandler {
    
    private static RedisHandler redisHandlerInstance;
    private static final JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
    private final String loggedInUsers = "loggedInUsers";
    private final String activities = "activities";
    private final long numActivities = 2;
    
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

    public void addLoggedInUser(String userID) {
        Jedis j = pool.getResource();
        j.sadd(loggedInUsers, userID);
    }
    
    public Boolean isLoggedInUser(String userID) {
        Jedis j = pool.getResource();
        return j.sismember(loggedInUsers, userID);
    }
    
    public void removeLoggedInUser(String userID) {
        Jedis j = pool.getResource();
        j.srem(loggedInUsers, userID);
    }

    public void addActivity(SubCategoryBean subCategory, String city, String actId) {
        Pipeline p = pool.getResource().pipelined();
        String subCatFullName = subCategory.catName + "-" + subCategory.subCatname; 
        
        p.sadd(city, actId);
        p.sadd(subCatFullName, actId);
        p.lpush(activities, actId);
        p.ltrim(activities, 0, numActivities);
        p.sync();
    }
    
    public List<String> getTopActivities(){
        Jedis j = pool.getResource();
        return j.lrange(activities, 0, numActivities);
    }
    
    public void deleteActivity(SubCategoryBean subCategory, String city, String actId){
        Pipeline p = pool.getResource().pipelined();
        String subCatFullName = subCategory.catName + "-" + subCategory.subCatname;
        
        p.srem(city, actId);
        p.srem(subCatFullName, actId);
        p.lrem(activities, 0, actId);
        p.sync();
    }
    
    public List<String> searchActivities(String category, String subCategory, String city){
        Jedis j = pool.getResource();
        String subCatFullName = category + "-" + subCategory;
        Set<String> resSet = j.sinter(city, subCatFullName);
        if(resSet.isEmpty()){
            return null;
        }
        else{
            return new ArrayList<>(resSet);
        }
    }

    public void updateActivity(String city, String oldCity, String actId) {
        Jedis j = pool.getResource();
        if(!city.equals(oldCity)){
            j.srem(oldCity, actId);
            j.sadd(city, actId);
        }
    }

    public void deleteAllData() {
        Jedis j = pool.getResource();
        j.flushAll();
    }

}
