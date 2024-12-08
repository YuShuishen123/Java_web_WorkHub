package com.gzu.springbootdemo1.service.impl;


import com.gzu.springbootdemo1.entity.Teacher;
import com.gzu.springbootdemo1.mapper.TeacherMapper;
import com.gzu.springbootdemo1.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


// 该类负责实现TeacherService接口中声明的服务
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
public class TeacherServiceImpl implements TeacherService {
    // 业务层调用持久层
    @Autowired
    private TeacherMapper teacherMapper;  // 注入持久层
    @Override
    public void add(Teacher teacher)   // 重写实现添加功能
    {
        teacherMapper.insert(teacher);  // 调用持久层添加方法
    }
    @Override
    public void delete(int id)   // 重写实现删除功能
    {
        teacherMapper.deleteById(id);
    }

    @Override
    public List<Teacher> findAll()  // 重写实现查询所有功能
    {
        return teacherMapper.selectList(null);
    }

    @Override
    public Teacher findById(int id)   // 重写实现根据id查询功能
    {
        return teacherMapper.selectById(id);
    }

    @Override
    public void update(Teacher teacher)
    {
        teacherMapper.updateById(teacher);  // 调用持久层修改方法
    }

}
