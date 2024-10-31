package com.gzu.springbootdemo1.service.impl;

import com.gzu.springbootdemo1.entity.Student;
import com.gzu.springbootdemo1.mapper.StudentMapper;
import com.gzu.springbootdemo1.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired  // 自动装配
    // 注入
    private StudentMapper studentMapper;

    @Override
    public List<Student> findAll() {
        return studentMapper.selectList(null);
    }

    @Override
    public Student findById(int id) {
        return studentMapper.selectById(id);
    }

    @Override
    public void add(Student student) {
        studentMapper.insert(student);
    }

    @Override
    public void update(Student student) {
        studentMapper.updateById(student);
    }

    @Override
    public void delete(int id) {
        studentMapper.deleteById(id);
    }
}
