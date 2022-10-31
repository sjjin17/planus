package com.planus.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planus.bucket.dto.BucketResDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class RedisUtil {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ModelMapper modelMapper;

    public List getListData(String key) {

        //return (String) redisTemplate.opsForHash().get(key);
        ListOperations<String, ?> listOperations = redisTemplate.opsForList();

        long size = listOperations.size(key) == null ? 0 : listOperations.size(key);
        return listOperations.range(key, 0, size);
        //return (String) redisTemplate.opsForValue().get(key);
    }

    public void setDto(String key, BucketResDTO bucketResDTO) {

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


    public <T> boolean saveData(String key, T data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String value = mapper.writeValueAsString(data);
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch(Exception e){
            return false;
        }
    }

    public <T> T get(String key, Class<T> clazz){
        Object o = redisTemplate.opsForValue().get(key);
        if(o != null) {
            if(o instanceof LinkedHashMap){
                return modelMapper.map(o, clazz);
            }else{
                return clazz.cast(o);
            }
        }
        return null;
    }

    public Map getHashData(String key) {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();

        Map<Object, Object> map = redisTemplate.opsForHash().entries(key);

//        hashMap.put("tripDate", (String) redisTemplate.opsForHash().get(key, "trip_date"));
//        hashMap.put("startTime", (String) redisTemplate.opsForHash().get(key, "start_time"));
//        hashMap.put("timetables", (String) redisTemplate.opsForHash().get(key, "timetables"));

        return map;
    }

    public boolean setHashData(String key, Map<String, Object> map) {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();

        try {
            // map으로 만든 파라미터를 넣기
            hashOperations.putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
