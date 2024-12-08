package jdbc.cursoe_test.service;

import jdbc.cursoe_test.entity.CourseContent;
import jdbc.cursoe_test.dto.ContentOrderUpdateDTO;
import java.util.List;

public interface CourseContentService {
    CourseContent createContent(CourseContent content);
    CourseContent updateContent(CourseContent content);
    void deleteContent(Long id);
    CourseContent getContentById(Long id);
    List<CourseContent> getCourseContents(Long courseId);
    void updateContentOrder(Long id, Integer newOrder);
    void reorderContents(List<ContentOrderUpdateDTO> updates);
} 