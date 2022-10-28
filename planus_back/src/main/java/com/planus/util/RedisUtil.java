package com.planus.util;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RedisUtil {
    private final RedisTemplate<String, Object> redisTemplate;

    public List getListData(String key) {

        //return (String) redisTemplate.opsForHash().get(key);
        ListOperations<String, Object> listOperations = redisTemplate.opsForList();
        System.out.println(listOperations);
        System.out.println(listOperations.size(key));
        long size = listOperations.size(key) == null ? 0 : listOperations.size(key);
        System.out.println(listOperations.range(key, 0, size));
        return listOperations.range(key, 0, size);
        //return (String) redisTemplate.opsForValue().get(key);
    }

    public void setData(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }


    public List setListData(String key, Object value) {

        redisTemplate.opsForList().rightPush(key, value);
        return getListData(key);
    }
    public void deleteData(String key) {
        redisTemplate.delete(key);
    }


    public boolean isExists(String key){
        return redisTemplate.hasKey(key);
    }

}
