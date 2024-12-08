package jdbc.cursoe_test.service;

import jdbc.cursoe_test.entity.Course;
import java.util.List;

public interface CourseService {
    Course createCourse(Course course);
    Course updateCourse(Course course);
    Course getCourseById(Long id);
    List<Course> getCoursesByTeacher(Long teacherId);
    List<Course> getAllCourses(String keyword, int offset, int limit);
    void selectCourse(Long courseId, Long studentId);
    void unselectCourse(Long courseId, Long studentId);
    List<Course> getStudentCourses(Long studentId);
    boolean isStudentEnrolled(Long courseId, Long studentId);
} 