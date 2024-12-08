package jdbc.cursoe_test.controller;

import jdbc.cursoe_test.entity.CourseContent;
import jdbc.cursoe_test.service.CourseContentService;
import jdbc.cursoe_test.dto.ContentOrderUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courses/{courseId}/contents")
public class CourseContentController {

    @Autowired
    private CourseContentService contentService;

    @PostMapping
    public ResponseEntity<?> createContent(@PathVariable Long courseId, @RequestBody CourseContent content) {
        content.setCourseId(courseId);
        return ResponseEntity.ok(contentService.createContent(content));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateContent(@PathVariable Long courseId, @PathVariable Long id, 
                                         @RequestBody CourseContent content) {
        content.setId(id);
        content.setCourseId(courseId);
        return ResponseEntity.ok(contentService.updateContent(content));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContent(@PathVariable Long id) {
        contentService.deleteContent(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "内容已删除");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getContent(@PathVariable Long id) {
        return ResponseEntity.ok(contentService.getContentById(id));
    }

    @GetMapping
    public ResponseEntity<?> getCourseContents(@PathVariable Long courseId) {
        return ResponseEntity.ok(contentService.getCourseContents(courseId));
    }

    @PutMapping("/{id}/order")
    public ResponseEntity<?> updateContentOrder(@PathVariable Long id, @RequestParam Integer order) {
        contentService.updateContentOrder(id, order);
        Map<String, String> response = new HashMap<>();
        response.put("message", "排序已更新");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/reorder")
    public ResponseEntity<?> reorderContents(@RequestBody ReorderRequest request) {
        try {
            contentService.reorderContents(request.getUpdates());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Data
    public static class ReorderRequest {
        private List<ContentOrderUpdateDTO> updates;
    }
} 