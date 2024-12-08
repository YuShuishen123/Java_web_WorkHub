package jdbc.cursoe_test.controller;

import jdbc.cursoe_test.entity.User;
import jdbc.cursoe_test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        return ResponseEntity.ok(user);
    }
    
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody User user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findByUsername(auth.getName());
        
        // 只允许更新某些字段
        currentUser.setEmail(user.getEmail());
        currentUser.setRealName(user.getRealName());
        
        userService.updateProfile(currentUser);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "个人资料更新成功");
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> passwords) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        
        userService.updatePassword(
            user.getId(),
            passwords.get("oldPassword"),
            passwords.get("newPassword")
        );
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "密码修改成功");
        return ResponseEntity.ok(response);
    }
} 