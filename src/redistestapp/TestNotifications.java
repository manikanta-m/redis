/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redistestapp;

import java.util.Date;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 *
 * @author manikanta.m
 */
public class TestNotifications {

    public static void main(String[] args) {
        try {
            JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
            Jedis jedis = pool.getResource();
            jedis.set("RTnotify", "umq");
            jedis.set("notify", "umq");
            jedis.set("notify1", "umq");
            jedis.set("RTnotify1", "umq");
            System.out.println("message inserted time : "+new Date().toString());
            jedis.setex("shadow:RTnotify", 10, "");

        } catch (Exception e) {

        }
    }
}
