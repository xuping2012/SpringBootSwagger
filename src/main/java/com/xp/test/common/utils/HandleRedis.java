package com.xp.test.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import redis.clients.jedis.Jedis;

public class HandleRedis {

	private Jedis redis;

	/**
	 * 定义一个获取redis-key的值的方法
	 * 
	 * @param serverip
	 * @param port
	 * @param passwd
	 * @param dbnum
	 * @param key
	 * @return
	 */
	public String getValueOfKey(String serverip, int port, String passwd,
			int dbnum, String key) {
		// 创建redis连接
		redis = new Jedis(serverip, port);
		// 密码认证
		redis.auth(passwd);
		// 选择db
		redis.select(dbnum);
		// getkey的值
		String v = redis.get(key);
		// 关闭连接
		if (v == null) {
			redis.close();
			return "redis查询为空";
		} else {
			redis.close();
		}
		// 并返回值
		return v;
	}

	public static byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			// 序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {

		}
		return null;
	}

	public static Object unserialize(byte[] bytes) {
		ByteArrayInputStream bais = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {

		}
		return null;
	}
}