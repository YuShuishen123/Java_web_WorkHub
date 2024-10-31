/**
 * 学生信息映射器接口
 * 用于定义对学生实体类对应数据库表的操作
 * 继承自BaseMapper，自动获得MyBatis-Plus提供的基础CRUD功能
 */
package com.gzu.springbootdemo1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gzu.springbootdemo1.entity.Student;
import org.apache.ibatis.annotations.Mapper;

/**
 * StudentMapper继承BaseMapper，泛型Student指定实体类
 * 通过继承BaseMapper，无需编写SQL语句即可实现基本的数据库操作
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {  // 说明： BaseMapper<T> T为实体类 对应的表
}
