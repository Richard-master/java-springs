package com.yxy.springsecurity.utils;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Log4j2
@Component
@ConditionalOnClass(RedisTemplate.class)
public class RedisHelper {

	private RedisTemplate redisTemplate;
	private Environment environment;

	public RedisHelper(Environment environment) {
		this.environment = environment;
	}

	@Resource
	public void setRedisTemplate(RedisTemplate redisTemplate) {
		redisTemplate.setKeySerializer(getKeySerializer());
		redisTemplate.setValueSerializer(getValueSerializer());
		this.redisTemplate = redisTemplate;
	}

	public static RedisSerializer getKeySerializer() {
		return new StringRedisSerializer();
	}

	public static RedisSerializer getValueSerializer() {
		return new GenericFastJsonRedisSerializer();
	}

	/**
	 * 写入缓存
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, Object value) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean increment(final String key, Long value) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.increment(key, value);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 写入缓存设置时效时间
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, Object value, Long expireTime) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value, expireTime, TimeUnit.SECONDS);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 批量删除对应的value
	 *
	 * @param keys
	 */
	public void remove(final String... keys) {
		for (String key : keys) {
			remove(key);
		}
	}

	/**
	 * 删除对应的value
	 *
	 * @param key
	 */
	public boolean remove(final String key) {
		return redisTemplate.delete(key);
	}

	/**
	 * 判断缓存中是否有对应的value
	 *
	 * @param key
	 * @return
	 */
	public boolean exists(final String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 设置缓存失效时间（单位：秒）
	 *
	 * @param expiredSeconds
	 * @return
	 */
	public boolean expire(String key, final Long expiredSeconds) {
		return redisTemplate.expire(key, expiredSeconds, TimeUnit.SECONDS);
	}

	/**
	 * 读取缓存
	 *
	 * @param key
	 * @return Object
	 */
	public Object get(final String key) {
		Object result = null;
		ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
		result = operations.get(key);
		return result;
	}

	/**
	 * 读取缓存
	 *
	 * @param key
	 * @return String
	 */
	public String getString(final String key) {
		String result = "";
		ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
		Object object = operations.get(key);
		if (object != null) {
			result = String.valueOf(object);
		}

		return result;
	}

	/**
	 * 哈希添加
	 *
	 * @param key
	 * @param hashKey
	 * @param value
	 */
	public void hmSet(String key, Object hashKey, Object value) {
		HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
		hash.put(key, hashKey, value);
	}

	/**
	 * 哈希添加多个值
	 *
	 * @param key
	 * @param m
	 */
	public void hmSetAll(String key, Map<Object, Object> m) {
		HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
		hash.putAll(key, m);
	}

	/**
	 * 哈希删除
	 *
	 * @param key
	 * @param hashKey
	 */
	public Long hmDel(String key, Object... hashKey) {
		HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
		return hash.delete(key, hashKey);
	}

	/**
	 * 哈希获取数据
	 *
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public Object hmGet(String key, Object hashKey) {
		HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
		return hash.get(key, hashKey);
	}

	/**
	 * 哈希长度
	 *
	 * @param key
	 * @return
	 */
	public Long hmLen(String key) {
		HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
		return hash.size(key);
	}

//	/**
//	 * 哈希批量获取数据
//	 *
//	 * @param key
//	 * @param options
//	 * @return
//	 */
//	public Cursor<Map.Entry<Object, Object>> hmScan(String key, ScanOptions options) {
//		HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
//		return hash.scan(key, options);
//	}

	/**
	 * 列表添加
	 *
	 * @param k
	 * @param v
	 */
	public void lPush(String k, Object v) {
		ListOperations<String, Object> list = redisTemplate.opsForList();
		list.leftPush(k, v);
	}

	/**
	 * 列表添加
	 *
	 * @param k
	 * @param v
	 */
	public void rPush(String k, Object v) {
		ListOperations<String, Object> list = redisTemplate.opsForList();
		list.rightPush(k, v);
	}

	/**
	 * 移出并获取列表的第一个元素
	 *
	 * @param key
	 */
	public Object lPop(String key) {
		ListOperations<String, Object> list = redisTemplate.opsForList();
		Object result = list.leftPop(key);
		return result;
	}

	/**
	 * 列表长度
	 *
	 * @param k
	 */
	public Long lLen(String k) {
		ListOperations<String, Object> list = redisTemplate.opsForList();
		return list.size(k);
	}

	/**
	 * 列表批量添加
	 *
	 * @param k
	 * @param v
	 */
	public void lPushAll(String k, Object... v) {
		ListOperations<String, Object> list = redisTemplate.opsForList();
		list.leftPushAll(k, v);
	}

	/**
	 * 列表批量添加
	 *
	 * @param k
	 * @param v
	 */
	public void rPushAll(String k, Object... v) {
		ListOperations<String, Object> list = redisTemplate.opsForList();
		list.rightPushAll(k, v);
	}

	/**
	 * 列表获取
	 *
	 * @param k
	 * @param l
	 * @param l1
	 * @return
	 */
	public List<Object> lRange(String k, long l, long l1) {
		ListOperations<String, Object> list = redisTemplate.opsForList();
		return list.range(k, l, l1);
	}

	/**
	 * 集合添加
	 *
	 * @param key
	 * @param value
	 */
	public void add(String key, Object value) {
		SetOperations<String, Object> set = redisTemplate.opsForSet();
		set.add(key, value);
	}

	/**
	 * 集合获取
	 *
	 * @param key
	 * @return
	 */
	public Set<Object> setMembers(String key) {
		SetOperations<String, Object> set = redisTemplate.opsForSet();
		return set.members(key);
	}

	/**
	 * 判断集合中是否存在成员
	 *
	 * @param key
	 * @param member
	 * @return
	 */
	public Boolean existsMember(String key, Object member) {
		SetOperations<String, Object> set = redisTemplate.opsForSet();
		return set.isMember(key, member);
	}

	/**
	 * 有序集合添加
	 *
	 * @param key
	 * @param value
	 * @param scoure
	 */
	public void zAdd(String key, Object value, double scoure) {
		ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
		zset.add(key, value, scoure);
	}

	/**
	 * 有序集合获取
	 *
	 * @param key
	 * @param scoure
	 * @param scoure1
	 * @return
	 */
	public Set<Object> rangeByScore(String key, double scoure, double scoure1) {
		ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
		return zset.rangeByScore(key, scoure, scoure1);
	}

	/**
	 * 分布式锁 一次性设置（单位：秒）
	 *
	 * @param key
	 * @param value
	 * @param expirationTime
	 * @return
	 */
	public Boolean setNX(String key, String value, long expirationTime) {

		RedisCallback<Boolean> callback = (connection) -> {
			return connection.set(redisTemplate.getKeySerializer().serialize(key),
					redisTemplate.getValueSerializer().serialize(value),
					Expiration.from(expirationTime, TimeUnit.SECONDS), RedisStringCommands.SetOption.SET_IF_ABSENT);
		};
		Boolean result = (Boolean) redisTemplate.execute(callback);

		return result;
	}

	/**
	 * 每隔指定时间尝试获取分布式锁 获取成功前一直阻塞
	 *
	 * @param key
	 * @param value
	 * @param expirationTime 锁过期时间
	 * @return
	 */
	public Boolean getLock(String key, String value, long expirationTime, long sleepSecond) {
		while (true) {
			if (setNX(key, value, expirationTime)) {
				return true;
			}
			try {
				Thread.sleep(sleepSecond * 1000);
			} catch (InterruptedException e) {
				log.error("获取redis锁时当前线程被中断：", e);
			}
		}
	}

	/**
	 * 分布式锁 一次性清除
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public Boolean releaseLock(String key, String value) {
		// 序列化需要保持一致 key是主键 arg 是值
		String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end ";

		DefaultRedisScript<Long> redisScript = new DefaultRedisScript<Long>();
		redisScript.setScriptText(script);
		redisScript.setResultType(Long.class);

		Long result = (Long) redisTemplate.execute(redisScript, Collections.singletonList(key), value);
		return result != null && result.equals(1L);
	}

}
