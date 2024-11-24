package com.example.demo.service.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("HashMapCache")
public class HashMapCache implements CacheService {

  private Map<String, Object> cache = new ConcurrentHashMap<>();

  @Override
  public void add(String key, Object value) {
    cache.put(key, value);
  }

  @Override
  public Object get(String key) {
    return cache.get(key);
  }

  @Override
  public void remove(String key) {
    cache.remove(key);
  }

  @Override
  public void clear() {
    cache.clear();
  }

}
