package jdbc.cursoe_test.service.impl;

import jdbc.cursoe_test.entity.Course;
import jdbc.cursoe_test.mapper.CourseMapper;
import jdbc.cursoe_test.mapper.CourseSelectionMapper;
import jdbc.cursoe_test.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseSelectionMapper courseSelectionMapper;

    @Override
    @Transactional
    public Course createCourse(Course course) {
        courseMapper.insert(course);
        return course;
    }

    @Override
    @Transactional
    public Course updateCourse(Course course) {
        courseMapper.update(course);
        return course;
    }

    @Override
    public Course getCourseById(Long id) {
        return courseMapper.findById(id);
    }

    @Override
    public List<Course> getCoursesByTeacher(Long teacherId) {
        return courseMapper.findByTeacherId(teacherId);
    }

    @Override
    public List<Course> getAllCourses(String keyword, int offset, int limit) {
        return courseMapper.findAll(keyword, offset, limit);
    }

    @Override
    public boolean isStudentEnrolled(Long courseId, Long studentId) {
        return courseSelectionMapper.exists(courseId, studentId);
    }

    @Override
    @Transactional
    public void selectCourse(Long courseId, Long studentId) {
        Course course = courseMapper.findById(courseId);
        if (course == null) {
            throw new RuntimeException("课程不存在");
        }
        
        // 确保 currentStudents 不为 null
        int currentStudents = course.getCurrentStudents() != null ? course.getCurrentStudents() : 0;
        
        if (currentStudents >= course.getMaxStudents()) {
            throw new RuntimeException("课程已满");
        }
        if (isStudentEnrolled(courseId, studentId)) {
            throw new RuntimeException("已经选择过该课程");
        }
        courseSelectionMapper.insert(courseId, studentId);
        courseMapper.updateCurrentStudents(courseId, 1);
    }

    @Override
    @Transactional
    public void unselectCourse(Long courseId, Long studentId) {
        if (!isStudentEnrolled(courseId, studentId)) {
            throw new RuntimeException("未选择该课程");
        }
        courseSelectionMapper.delete(courseId, studentId);
        courseMapper.updateCurrentStudents(courseId, -1);
    }

    @Override
    public List<Course> getStudentCourses(Long studentId) {
        return courseMapper.findByStudentId(studentId);
    }
} 