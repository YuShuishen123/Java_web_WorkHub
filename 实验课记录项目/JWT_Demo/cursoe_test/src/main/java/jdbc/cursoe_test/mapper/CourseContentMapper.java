package jdbc.cursoe_test.mapper;

import jdbc.cursoe_test.entity.CourseContent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseContentMapper {
    void insert(CourseContent content);
    void update(CourseContent content);
    void delete(Long id);
    CourseContent findById(Long id);
    List<CourseContent> findByCourseId(Long courseId);
    void updateSortOrder(@Param("id") Long id, @Param("sortOrder") Integer sortOrder);
} 