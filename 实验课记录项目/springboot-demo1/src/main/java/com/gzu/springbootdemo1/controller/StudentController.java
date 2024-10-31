package com.gzu.springbootdemo1.controller;

import com.gzu.springbootdemo1.entity.Student;
import com.gzu.springbootdemo1.service.StudentService;
import com.gzu.springbootdemo1.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired   
    private StudentService studentService;  // 

    @GetMapping("/students")
    public Result findAll() {
        Result result = new Result();
        try{
            result.setCode(200);
            result.setMsg("添加成功");
            result.setData(studentService.findAll());
            return result;
        }catch (Exception e){
            result.setCode(500);
            result.setMsg("添加失败");
            // 控制台输出错误信息
            e.printStackTrace();
            return result;
        }
    }

    @PostMapping("/students")
    public Result add(@RequestBody Student student) {
        Result result = new Result();
        try{
            studentService.add(student);
            result.setCode(201);
            result.setMsg("添加成功");
            return result;
        }catch (Exception e){
            result.setCode(500);
            result.setMsg("添加失败");
            return result;
        }
    }

    @PutMapping("/students")
    public void update(@RequestBody Student student) {
        studentService.update(student);
    }

    @GetMapping("/students/{id}")
    public Student findById(@PathVariable int id) {
        return studentService.findById(id);
    }

    @DeleteMapping("/students/{id}")
    public void delete(@PathVariable int id) {
        studentService.delete(id);
    }

}
