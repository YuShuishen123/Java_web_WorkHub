package jdbc.cursoe_test.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CourseSelectionMapper {
    void insert(@Param("courseId") Long courseId, @Param("studentId") Long studentId);
    void delete(@Param("courseId") Long courseId, @Param("studentId") Long studentId);
    boolean exists(@Param("courseId") Long courseId, @Param("studentId") Long studentId);
} 