package com.gzu.springbootdemo1.controller;

import com.gzu.springbootdemo1.entity.Teacher;
import com.gzu.springbootdemo1.service.TeacherService;
import com.gzu.springbootdemo1.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")  // 忽略SpringJavaAutowiredFieldsWarningInspection警告
@RestController  // 声明该类为控制器
@RequestMapping("/api") // 路径前缀
public class TeacherController {
    // 创建一个持久化层对象
    @Autowired
    private TeacherService teacherService;

    // 初始化 Logger 实例，用于异常日志输出
    private static final Logger logger = LoggerFactory.getLogger(TeacherController.class);

    // 查询全部教师
    @GetMapping("/teachers")
    public Result queryAllTeachers(){
        Result result = new Result();
        try{
            result.setData(teacherService.findAll());
            result.setCode(200);
            result.setMsg("查询成功");
            return result;
        }catch(Exception e){
            result.setCode(500);
            result.setMsg("查询失败");
            // 控制台输出错误信息
            logger.error("查询全部学生时发生异常", e);
            return result;
        }
    }

    // 根据id查询
    @GetMapping("/teachers/{id}")
    public Result findById(@PathVariable int id){
        Result result = new Result();
        try{
            if(teacherService.findById(id)==null){
                result.setCode(300);
                result.setMsg("记录不存在");
            }else{
                result.setData(teacherService.findById(id));
                result.setCode(200);
                result.setMsg("查询成功");
            }
            return result;
        }catch(Exception e){
            result.setCode(500);
            result.setMsg("查询失败");
            // 控制台输出错误信息
            logger.error("根据id查询时发生异常", e);
            System.out.println("根据id查询时发生异常-----");
            return result;
        }
    }

    // 根据iD删除
    @PutMapping("/teachers/delete/{id}")
    public Result deleteById(@PathVariable int id){
        Result result = new Result();
        try{
            if(teacherService.findById(id)==null) {
                result.setCode(300);
                result.setMsg("记录不存在");
            }else{
                teacherService.delete(id);
                result.setCode(200);
                result.setMsg("删除成功");
            }
            return result;
        }catch(Exception e){
            result.setCode(500);
            result.setMsg("删除失败");
            // 控制台输出错误信息
            logger.error("根据id删除时发生异常", e);
            return result;
        }
    }

    // 插入教师
    @PostMapping("/teachers/insert")
    public Result add(@RequestBody Teacher teacher){
        Integer id = teacher.getId();
        Result result = new Result();
        try {
            if(id == null){ // 首先判断id是否为空，为空则直接插入，否则判断是否已经存在该记录
                result.setCode(400);
                result.setMsg("添加成功");
                result.setData(teacher);
                teacherService.add(teacher);
            }else{
                // 判断该id是否已经被占用
                if(teacherService.findById(id)!=null){  //查询结果非空，说明已经存在该记录
                    result.setCode(300);
                    result.setMsg("记录已存在，添加失败");
                }else{
                    result.setCode(400);  // 查询结果为空， 说明该id 没有被占用，可以插入
                    result.setMsg("添加成功");
                    result.setData(teacher);
                    teacherService.add(teacher);
                }
            }
            return result;
        }catch (Exception e){
            result.setCode(500);
            result.setMsg("添加失败");
            // 控制台输出错误信息
            logger.error("插入教师信息时发生异常", e);
            return result;
        }
    }

    // 更新教师信息
    @PutMapping("/teachers/update")
    public Result update(@RequestBody Teacher teacher){
        Integer generatedId = teacher.getId();
        Result result = new Result();
        try{
            if(teacherService.findById(generatedId)==null){
                result.setCode(300);
                result.setMsg("记录不存在，更新失败");
            }else{
                teacherService.update(teacher);
                result.setCode(200);
                result.setMsg("更新成功");
                result.setData(teacherService.findById(generatedId));
            }
            return result;
        }catch (Exception e){
            result.setCode(500);
            result.setMsg("更新失败");
            // 控制台输出错误信息
            logger.error("更新教师信息时发生异常", e);
            return result;
        }
    }
}
