package com.dianawu.blogApp.service.Impl;

import com.dianawu.blogApp.dto.RegistrationDto;
import com.dianawu.blogApp.entity.Role;
import com.dianawu.blogApp.entity.User;
import com.dianawu.blogApp.repository.RoleRepository;
import com.dianawu.blogApp.repository.UserRepository;
import com.dianawu.blogApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
    @Override
    public void saveUser(RegistrationDto registrationDto) {
        // save user to database
        User user = new User();
        user.setName(registrationDto.getFirstName()+" "+registrationDto.getLastName());
        user.setEmail(registrationDto.getEmail());

        // todo use spring security to encrypt password

        user.setPassword(registrationDto.getPassword());
        Role role= roleRepository.findByName("ROLE_GUEST");
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);

    }

    @Override
    public User findByEmail(String email) {

        return userRepository.findByEmail(email);
    }
}
