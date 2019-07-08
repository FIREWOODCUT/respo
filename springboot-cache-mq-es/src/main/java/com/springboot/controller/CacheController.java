package com.springboot.controller;

import com.springboot.domain.Employee;
import com.springboot.mapper.EmployMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class CacheController {
    @Autowired
    EmployMapper employMapper; //mybatis

    @Autowired
    StringRedisTemplate stringRedisTemplate; //for string

    @Autowired
    RedisTemplate redisTemplate; //for Object

    @Autowired
    RedisCacheManager redisCacheManager; // redis缓存管理器

    /**
     * caching用于复杂操作
     * key:前缀+方法名
     *
     * @param id
     * @return
     */
    @GetMapping("/employ/{id}")
//    @Cacheable(value = "emp", key = "#root.methodName", condition = "#id > 0", unless = "#result == null")
    @Caching(cacheable = {
            @Cacheable(value = "emp", key = "#root.methodName", condition = "#id > 0", unless = "#result == null")
    }, put = {
            @CachePut(value = "emp", key = "#result.lastName")
    })
    public Employee getInfoById(@PathVariable("id") Integer id) {
        System.out.println("开始查询数据库...");
        Employee employee = employMapper.getInfoById(id);
        return employee;
    }

    /**
     * 更新
     *
     * @param e
     * @return
     */
    @PostMapping("/employ/update")
    @CachePut(value = "emp", key = "#e.id"/*"#result.id"*/)
    public Employee updateInfoById(Employee e) {
        Employee employee = employMapper.updateInfoById(e.getLastName(), e.getdId());
        return employee;
    }

    /**
     * 清空
     *
     * @param id
     */
    @RequestMapping("/delete/{id}")
    @CacheEvict(value = "emp", key = "#id"/*,beforeInvocation = true,allEntries = true*/)
    public void delteInfoById(@PathVariable("id") Integer id) {
        employMapper.deleteInfoById(id);
    }
}
