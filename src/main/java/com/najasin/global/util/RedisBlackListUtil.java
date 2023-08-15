package com.najasin.global.util;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisBlackListUtil {
	private final RedisTemplate<String, Object> redisBlackListTemplate;

	public void setBlackList(String key, Object o, int day) {
		redisBlackListTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(o.getClass()));
		redisBlackListTemplate.opsForValue().set(key, o, day, TimeUnit.DAYS);
	}

	public Object getBlackList(String key) {
		return redisBlackListTemplate.opsForValue().get(key);
	}

	public boolean deleteBlackList(String key) {
		return Boolean.TRUE.equals(redisBlackListTemplate.delete(key));
	}

	public boolean hasKeyBlackList(String key) {
		return Boolean.TRUE.equals(redisBlackListTemplate.hasKey(key));
	}
}