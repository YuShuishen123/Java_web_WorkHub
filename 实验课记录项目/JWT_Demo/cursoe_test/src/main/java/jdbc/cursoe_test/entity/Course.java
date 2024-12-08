package jdbc.cursoe_test.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Course {
    private Long id;
    private String title;
    private String description;
    private Long teacherId;
    private String teacherName;
    private Integer status = 0; // 默认未发布
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer maxStudents = 100; // 默认最大学生数
    private Integer currentStudents = 0; // 默认当前学生数
} 