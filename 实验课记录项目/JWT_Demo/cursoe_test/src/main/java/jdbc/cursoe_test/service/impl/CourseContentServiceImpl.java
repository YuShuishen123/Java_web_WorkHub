package jdbc.cursoe_test.service.impl;

import jdbc.cursoe_test.entity.Course;
import jdbc.cursoe_test.entity.CourseContent;
import jdbc.cursoe_test.mapper.CourseContentMapper;
import jdbc.cursoe_test.mapper.CourseMapper;
import jdbc.cursoe_test.service.CourseContentService;
import jdbc.cursoe_test.dto.ContentOrderUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseContentServiceImpl implements CourseContentService {

    @Autowired
    private CourseContentMapper contentMapper;
    
    @Autowired
    private CourseMapper courseMapper;

    @Override
    @Transactional
    public CourseContent createContent(CourseContent content) {
        Course course = courseMapper.findById(content.getCourseId());
        if (course == null) {
            throw new RuntimeException("课程不存在");
        }
        contentMapper.insert(content);
        return content;
    }

    @Override
    @Transactional
    public CourseContent updateContent(CourseContent content) {
        CourseContent existingContent = contentMapper.findById(content.getId());
        if (existingContent == null) {
            throw new RuntimeException("课程内容不存在");
        }
        contentMapper.update(content);
        return content;
    }

    @Override
    @Transactional
    public void deleteContent(Long id) {
        CourseContent content = contentMapper.findById(id);
        if (content == null) {
            throw new RuntimeException("课程内容不存在");
        }
        contentMapper.delete(id);
    }

    @Override
    public CourseContent getContentById(Long id) {
        return contentMapper.findById(id);
    }

    @Override
    public List<CourseContent> getCourseContents(Long courseId) {
        return contentMapper.findByCourseId(courseId);
    }

    @Override
    @Transactional
    public void updateContentOrder(Long id, Integer newOrder) {
        CourseContent content = contentMapper.findById(id);
        if (content == null) {
            throw new RuntimeException("课程内容不存在");
        }
        contentMapper.updateSortOrder(id, newOrder);
    }

    @Override
    @Transactional
    public void reorderContents(List<ContentOrderUpdateDTO> updates) {
        for (ContentOrderUpdateDTO update : updates) {
            CourseContent content = contentMapper.findById(update.getId());
            if (content == null) {
                throw new RuntimeException("内容不存在");
            }
            contentMapper.updateSortOrder(update.getId(), update.getSortOrder());
        }
    }
} 