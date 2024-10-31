package com.gzu.springbootdemo1.controller;

import com.gzu.springbootdemo1.entity.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, Spring Boot!";
    }

    @GetMapping("/hello/students")
    public Student helloStudents() {
        Student student = new Student();
        student.setId(1);
        student.setName("Alice");
        student.setAge(20);
        student.setGender("女");
        return student;
    }

}
