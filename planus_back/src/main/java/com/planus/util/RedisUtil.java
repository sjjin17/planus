package com.planus.util;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger2.mappers.ModelMapper;

@Component
@RequiredArgsConstructor
public class RedisUtil {
    private final RedisTemplate<String, Object> redisTemplate;

    public String getData(String key) {
        return (String) redisTemplate.opsForValue().get(key);
        //return (String) redisTemplate.opsForHash().get(key);
    }

    public void setData(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void deleteData(String key) {
        redisTemplate.delete(key);
    }


}
