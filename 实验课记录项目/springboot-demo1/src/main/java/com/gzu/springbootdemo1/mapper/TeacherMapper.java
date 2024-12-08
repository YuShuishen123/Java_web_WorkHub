package com.gzu.springbootdemo1.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gzu.springbootdemo1.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;

// 定义了一个MyBatis Plus的Mapper接口 TeacherMapper，用于操作 Teacher 实体类

@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {
}

