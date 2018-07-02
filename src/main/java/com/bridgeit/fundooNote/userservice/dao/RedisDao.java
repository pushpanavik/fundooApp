package com.bridgeit.fundooNote.userservice.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisDao {

	@Autowired
	private StringRedisTemplate redistemplate;
	
	
	
	public void addToken(String key,String token) {
		redistemplate.opsForValue().set(key, token);
	}
	public void deleteToken(String key) {
		redistemplate.delete(key);
	}
	public String  getToken(String id) {
		return redistemplate.opsForValue().get(id);
	}
}
