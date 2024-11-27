package com.tranduy.blog.service;


import com.tranduy.blog.dto.RegistrationDto;
import com.tranduy.blog.entity.User;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);

    User findByEmail(String email);
}
