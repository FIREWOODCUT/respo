package com.springboot.mapper;

import com.springboot.domain.Employee;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

public interface EmployMapper {
    @Select("select * from employee where id=#{id}")
    public Employee getInfoById(Integer id);

    @Update("update employee set lastname=#{lastname} where id=#{id}")
    public Employee updateInfoById(String lastname, Integer id);

    @Delete("delete from employee where id={#id}")
    public int deleteInfoById(Integer id);
}
