package com.xyhmo.commom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.*;

@Service
public class RedisService {

	@Autowired
	private ShardedJedisPool pool;

	/**
	 * @Description 向缓存中写入一对象，无关对象类型。
	 * 		例：save(String key, List<UserPO>);
	 * @param key String
	 * @param entity 例：Object; List<UserPO>; Map<String, UserPO>;
	 * @return void 返回类型
	 * @throws
	 */
	public <E> void set(String key, E entity) {
		byte[] k = SerializationUtils.serialize(key);
		byte[] v = SerializationUtils.serialize(entity);
		ShardedJedis sjedis = pool.getResource();
		sjedis.set(k, v);
		pool.returnResource(sjedis);
	}

	/**
	 * @Description 向缓存中写入一对象，无关对象类型。
	 * 		例：save(String key, List<UserPO>);
	 * @param key String
	 * @param expiredSeconds int 过期秒数
	 * @param entity 例：Object; List<UserPO>; Map<String, UserPO>;
	 * @return void 返回类型
	 * @throws
	 */
	public <E> void set(String key, E entity, int expiredSeconds) {
		byte[] k = SerializationUtils.serialize(key);
		byte[] v = SerializationUtils.serialize(entity);
		ShardedJedis sjedis = pool.getResource();
		sjedis.set(k, v);
		sjedis.expire(k, expiredSeconds);
		pool.returnResource(sjedis);
	}
	/**
	 * @Description 从缓存中获取键值key对应的对象，无关对象类型。
	 * 		例：List<UserPO> get(String key);
	 * @param key
	 * @return E 返回类型,可以是简单对象类型，也可以是复杂对象类型;
	 * 		例：Object; List<UserPO>; Map<String, UserPO>;
	 */
	@SuppressWarnings("unchecked")
	public <E> E get(String key) {
		byte[] k = SerializationUtils.serialize(key);
		ShardedJedis sjedis = pool.getResource();
		byte[] v = sjedis.get(k);
//		sjedis.exists(k);
//		sjedis.ttl(key);
		pool.returnResource(sjedis);
		return ((E) SerializationUtils.deserialize(v));
	}

	/**
	 * @Description 以设置过期时间的方法删除
	 * @param key
	 * @return void 影响的行数
	 */
	public long remove(String key){
		return expired(key, 0);
	}

	/**
	 * @Description 设置key的过期时间段(秒)
	 * @param key
	 * @param seconds
	 * @return Integer reply, specifically: 1: the timeout was set. 0: the
     *         timeout was not set since the key already has an associated
     *         timeout (this may happen only in Redis versions < 2.1.3, Redis >=
     *         2.1.3 will happily update the timeout), or the key does not
     *         exist.
	 */
	public long expired(String key, int seconds) {
		byte[] k = SerializationUtils.serialize(key);
		ShardedJedis sjedis = pool.getResource();
		long count = sjedis.expire(k, seconds);
		pool.returnResource(sjedis);
		return count;
	}

	/**
	 * @Description 设置key的过期时间点(毫秒值)
	 * @param key
	 * @param timestamp
	 * @return long 影响的行数
	 */
	public long expiredAt(String key, int timestamp) {
		byte[] k = SerializationUtils.serialize(key);
		ShardedJedis sjedis = pool.getResource();
		long count = sjedis.expireAt(k, timestamp);
		pool.returnResource(sjedis);
		return count;
	}

	/**
	 * 判断key是否存在
	 * @param // key
	 * @return boolean
	 * */
	public boolean exists(String key){
		byte[] k = SerializationUtils.serialize(key);
		ShardedJedis sjedis = pool.getResource();
		boolean result = sjedis.exists(k);;
		pool.returnResource(sjedis);
		return result;
	}

	/**
	 * 返回指定key存储的类型
	 * @param // key
	 * @return String  string|list|set|zset|hash
	 * **/
	public String type(String key) {
		byte[] k = SerializationUtils.serialize(key);
		ShardedJedis sjedis = pool.getResource();
		String type=sjedis.type(k);
		pool.returnResource(sjedis);
		return type;
	}

	/**
	 * @Description 向缓存中写入Map对象。
	 * 		例：setMap(String mapKey, Map<String, E> map);
	 * @param mapKey String
	 * @param map 例：Map<String, UserPO>;
	 * @return void 返回类型
	 */
	public <K, V> void setMap(String mapKey, Map<K, V> map) {
		byte[] mk = SerializationUtils.serialize(mapKey);
		ShardedJedis sjedis = pool.getResource();
		for(K key : map.keySet()) {
			byte[] k = SerializationUtils.serialize(key);
			byte[] v = SerializationUtils.serialize(map.get(key));
			sjedis.hset(mk, k, v);
		}
		pool.returnResource(sjedis);
	}
	/**
	 * @Description 向缓存中写入Map对象。
	 * 		例：setMap(String mapKey, Map<String, E> map);
	 * @param mapKey String
	 * @param // 例：Map<String, UserPO>;
	 * @return void 返回类型
	 */
	public <K, V> void setMap(String mapKey, K key, V entity) {
		byte[] mk = SerializationUtils.serialize(mapKey);
		byte[] k = SerializationUtils.serialize(key);
		byte[] v = SerializationUtils.serialize(entity);
		ShardedJedis sjedis = pool.getResource();
		sjedis.hset(mk, k, v);
		pool.returnResource(sjedis);
	}
	/**
	 * @Description 从缓存中取得Map对象。
	 * 		例：setMap(String mapKey, Map<String, E> map);
	 * @param mapKey String
	 * @return Map<String, E> 返回类型
	 */
	@SuppressWarnings("unchecked")
	public <K, V> Map<K, V> getMap(String mapKey) {
		byte[] mk = SerializationUtils.serialize(mapKey);
		ShardedJedis sjedis = pool.getResource();
		Map<byte[], byte[]> m = sjedis.hgetAll(mk);
		Map<K, V> map = new HashMap<K, V>();
		for(byte[] k : m.keySet()) {
			K key = (K) SerializationUtils.deserialize(k);
			V entity = (V) SerializationUtils.deserialize(m.get(k));
			map.put(key, entity);
		}
		pool.returnResource(sjedis);
		return map;
	}
	/**
	 * @Description 从缓存中取得Map对象中的entity子对象。
	 * 		例：setMap(String mapKey, String key);
	 * @param mapKey String
	 * @param key String
	 * @return E 返回类型
	 */
	@SuppressWarnings("unchecked")
	public <K, E> E getMap(String mapKey, K key) {
		byte[] mk = SerializationUtils.serialize(mapKey);
		byte[] k = SerializationUtils.serialize(key);
		ShardedJedis sjedis = pool.getResource();
		byte[] v = sjedis.hget(mk, k);
		E entity = (E) SerializationUtils.deserialize(v);
		pool.returnResource(sjedis);
		return entity;
	}
	/**
	 * 判断Map中是否存在键为key的子对象
	 * @param // mapKey, String key
	 * @return boolean
	 * */
	public <E> boolean existsMap(String mapKey, E key){
		byte[] mk = SerializationUtils.serialize(mapKey);
		byte[] k = SerializationUtils.serialize(key);
		ShardedJedis sjedis = pool.getResource();
		boolean result = sjedis.hexists(mk, k);
		pool.returnResource(sjedis);
		return result;
	}
	/**
	 * 判断Map中是否存在键为key的子对象
	 * @param // mapKey, String key
	 * @return boolean
	 * */
	public long lenMap(String mapKey){
		byte[] mk = SerializationUtils.serialize(mapKey);
		ShardedJedis sjedis = pool.getResource();
		long length = sjedis.hlen(mk);
		pool.returnResource(sjedis);
		return length;
	}

	public void flushAll(){
		ShardedJedis sjedis = pool.getResource();
		Collection<Jedis> coll = sjedis.getAllShards();
		byte[] pattern = "*".getBytes();
		for(Jedis jedis : coll){
			Set<byte[]> set = jedis.keys(pattern);
			for(byte[] key : set){
				jedis.del(key);
			}
		}
		pool.returnResource(sjedis);
	}

	public String getInfo(){
		StringBuilder shardInfo=new StringBuilder();
		ShardedJedis sjedis = pool.getResource();
		Collection<Jedis> coll = sjedis.getAllShards();
		for(Jedis jedis : coll){
			shardInfo.append(jedis.info());
			shardInfo.append("\n");
		}
		pool.returnResource(sjedis);
		return shardInfo.toString();
	}

	@SuppressWarnings("unchecked")
	public <E> List<E> pop(String listKey){
		byte[] k = SerializationUtils.serialize(listKey);
		ShardedJedis sjedis = pool.getResource();
		List<E> ret = new ArrayList<E>();
		//提取list中的所有数据
		int len = sjedis.llen(k).intValue();
		List<byte[]> v = sjedis.lrange(k, 0, len-1);
		for(byte[] b : v){
			ret.add((E)SerializationUtils.deserialize(b));
		}
		//对列表进行修剪,让列表只保留指定区间内的元素,即删除提取过的内容
		sjedis.ltrim(k, len, -1);
		pool.returnResource(sjedis);
		return ret;
	}

	public <E> void push(String key, E entity) {
		byte[] k = SerializationUtils.serialize(key);
		byte[] v = SerializationUtils.serialize(entity);
		ShardedJedis sjedis = pool.getResource();
		sjedis.rpush(k, v);
		pool.returnResource(sjedis);
	}

	public void publish(String string, String string2) {
		Jedis[] jedisArray = new Jedis[]{};
		jedisArray =pool.getResource().getAllShards().toArray(jedisArray);
		Jedis jedis = jedisArray[0];
        jedis.publish(string, string2);

	}

	public void subscribe(JedisPubSub jedisPubSub, String string) {
		Jedis[] jedisArray = new Jedis[]{};
		jedisArray =pool.getResource().getAllShards().toArray(jedisArray);
		Jedis jedis = jedisArray[0];
        jedis.subscribe(jedisPubSub, string);
	}
}