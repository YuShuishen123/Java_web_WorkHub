package com.gzu.springbootdemo1.service;

import com.gzu.springbootdemo1.entity.Teacher;
import org.springframework.stereotype.Service;

import java.util.List;

// 该类用来声明接口，接口中声明方法，方法中声明参数，返回值
@Service
public interface TeacherService {  // 接口，用来声明方法
    // 新增
    void add(Teacher teacher);
    // 删除
    void delete(int id);
    // 查询所有教师记录
    List<Teacher> findAll();
    // 根据教师ID查询
    Teacher findById(int id);
    // 修改教师信息
    void update(Teacher teacher);
}
