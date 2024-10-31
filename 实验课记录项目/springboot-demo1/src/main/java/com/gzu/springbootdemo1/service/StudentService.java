package com.gzu.springbootdemo1.service;

import com.gzu.springbootdemo1.entity.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {

    // 查询所有学生
    public List<Student> findAll();

    // 根据id查询学生
    public Student findById(int id);

    // 添加学生
    public void add(Student student);

    // 修改学生
    public void update(Student student);

    // 删除学生
    public void delete(int id);

}
