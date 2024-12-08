package jdbc.cursoe_test.mapper;

import jdbc.cursoe_test.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseMapper {
    void insert(Course course);
    void update(Course course);
    Course findById(Long id);
    List<Course> findByTeacherId(Long teacherId);
    List<Course> findAll(@Param("keyword") String keyword, @Param("offset") int offset, @Param("limit") int limit);
    void updateCurrentStudents(@Param("courseId") Long courseId, @Param("increment") int increment);
    List<Course> findByStudentId(Long studentId);
} 