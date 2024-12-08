package jdbc.cursoe_test.service;

import jdbc.cursoe_test.entity.User;

public interface UserService {
    void register(User user);
    User findByUsername(String username);
    User findByEmail(String email);
    void updateProfile(User user);
    void updatePassword(Long userId, String oldPassword, String newPassword);
} 