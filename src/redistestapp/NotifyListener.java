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
import redis.clients.jedis.JedisPubSub;

/**
 *
 * @author manikanta.m
 */
public class NotifyListener {

    public static void main(String[] args) {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
            Jedis jedis1 = pool.getResource();
        /*    jedis1.psubscribe(new NotificationListener(), "__key*__:RT*");  */
        Jedis jedis = new Jedis("localhost");
        jedis.psubscribe(new JedisPubSub() {
            @Override
            public void onPSubscribe(String pattern, int subscribedChannels) {
                System.out.println("onPSubscribe " + pattern + " " + subscribedChannels);
            }

            @Override
            public void onPMessage(String pattern, String channel, String message) {
                System.out.print("[Pattern:" + pattern + "]");
                System.out.print("[Channel: " + channel + "]");
                System.out.println("[Message: " + message + "]");
                
                if(message.equalsIgnoreCase("expired")){
                    String ikey = channel.split(":")[2];
                    System.out.print("Message expired time : "+new Date() +" key :"+ikey); 
                    String ivalue = jedis1.get(ikey);
                    System.out.println("value :"+ivalue);
                    jedis1.del(ikey);
                }
            }
        }, "__key*__:shadow*");

    }
}
