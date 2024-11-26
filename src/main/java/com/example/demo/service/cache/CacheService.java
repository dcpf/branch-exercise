package com.example.demo.service.cache;

public interface CacheService {
	void add(String key, Object value);

	Object get(String key);

	void remove(String key);

	void clear();

}
