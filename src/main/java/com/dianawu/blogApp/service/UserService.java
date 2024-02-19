package com.dianawu.blogApp.service;

import com.dianawu.blogApp.dto.RegistrationDto;
import com.dianawu.blogApp.entity.User;
import org.springframework.stereotype.Service;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);

    User findByEmail(String email);
}
