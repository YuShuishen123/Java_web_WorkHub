package jdbc.cursoe_test.controller;

import jdbc.cursoe_test.entity.Course;
import jdbc.cursoe_test.entity.User;
import jdbc.cursoe_test.service.CourseService;
import jdbc.cursoe_test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createCourse(@RequestBody Course course) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TEACHER"))) {
                return ResponseEntity.badRequest().body("只有教师可以创建课程");
            }

            // 获取当前登录的教师信息
            User teacher = userService.findByUsername(auth.getName());
            System.out.println("Teacher info: " + teacher); // 添加日志

            course.setTeacherId(teacher.getId());
            course.setTeacherName(teacher.getUsername());
            
            // 设置课程的初始状态
            course.setStatus(0); // 0-未发布
            course.setCurrentStudents(0); // 初始学生数为0
            course.setMaxStudents(course.getMaxStudents() != null ? course.getMaxStudents() : 100);
            
            // 设置时间，使用当前时区的时间
            LocalDateTime now = LocalDateTime.now();
            if (course.getStartTime() == null) {
                course.setStartTime(now);
            }
            if (course.getEndTime() == null) {
                course.setEndTime(now.plusMonths(3));
            }

            System.out.println("Creating course with data: " + course); // 添加日志
            Course createdCourse = courseService.createCourse(course);
            System.out.println("Created course: " + createdCourse); // 添加日志
            
            return ResponseEntity.ok(createdCourse);
        } catch (Exception e) {
            e.printStackTrace(); // 添加异常堆栈跟踪
            return ResponseEntity.badRequest().body("创建课程失败：" + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        course.setId(id);
        return ResponseEntity.ok(courseService.updateCourse(course));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourse(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @GetMapping
    public ResponseEntity<?> getCourses(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        int offset = (page - 1) * size;
        List<Course> courses = courseService.getAllCourses(keyword, offset, size);
        System.out.println("Found " + courses.size() + " courses");
        return ResponseEntity.ok(courses);
    }

    @PostMapping("/{id}/select")
    public ResponseEntity<?> selectCourse(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STUDENT"))) {
            return ResponseEntity.badRequest().body("只有学生可以选课");
        }
        
        // 获取当前登录学生的信息
        User student = userService.findByUsername(auth.getName());
        courseService.selectCourse(id, student.getId());
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "选课成功");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}/select")
    public ResponseEntity<?> unselectCourse(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // TODO: 获取当前登录学生的ID
        Long studentId = 1L; // 临时写死，实际需要从用户服务获取
        courseService.unselectCourse(id, studentId);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "退课成功");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyCourses(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        System.out.println("Current user: " + user.getUsername() + ", role: " + user.getRole());
        
        List<Course> courses;
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TEACHER"))) {
            courses = courseService.getCoursesByTeacher(user.getId());
        } else {
            courses = courseService.getStudentCourses(user.getId());
        }
        System.out.println("Found " + courses.size() + " courses for user");
        return ResponseEntity.ok(courses);
    }
} 