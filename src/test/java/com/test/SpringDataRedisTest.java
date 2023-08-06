package com.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringDataRedisTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testString(){
        redisTemplate.opsForValue().set("city","beijing");
    }
    @Test
    public void testList(){
        ListOperations listOperations = redisTemplate.opsForList();
        listOperations.leftPush("mylist","a");
        listOperations.leftPushAll("mylist","b","c");
        List<String> mylist = listOperations.range("mylist", 0, -1);
        for (String o : mylist) {
            System.out.println(o);
        }

        Long size = listOperations.size("mylist");
        int Lsize = size.intValue();
        listOperations.rightPop("mylist");
        for (int i = 0; i < Lsize; i++) {
            String element = (String) listOperations.rightPop("mylist");
            System.out.println(element);
        }
    }
    @Test
    public void testSet(){
        SetOperations setOperations = redisTemplate.opsForSet();
        setOperations.add("myset","a","b","c","d");
        Set<String> myset = setOperations.members("myset");
        for (String o : myset) {
            System.out.println(o);
        }
        setOperations.remove("myset","a","b");

    }
    @Test
    public void testZset(){
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add("myZset","a",10.0);
        zSetOperations.add("myZset","b",9.0);
        zSetOperations.add("myZset","c",90.0);
        zSetOperations.add("myZset","a",20.0);
        Set<String> myZset = zSetOperations.range("myZset", 0, -1);
        for (String s : myZset) {
            System.out.println(s);
        }
        zSetOperations.incrementScore("myZset","b",20.0);
        zSetOperations.remove("myZset","a","b");

    }
    @Test
    public void testCommon(){
        Set<String> keys = redisTemplate.keys("*");
        for(String s:keys){
            System.out.println(s);
        }
        Boolean itcast = redisTemplate.hasKey("itcast");
        System.out.println(itcast);
        redisTemplate.delete("myZset");
        DataType myset = redisTemplate.type("myset");
        System.out.println(myset.name());
    }
}

