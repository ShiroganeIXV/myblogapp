package com.dianawu.blogApp.security;

import com.dianawu.blogApp.entity.User;
import com.dianawu.blogApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
public class CustomUserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user != null){
            org.springframework.security.core.userdetails.User authenticatedUser = new org.springframework.security.core.userdetails
                    .User(user.getEmail()
                    , user.getPassword()
                    , user.getRoles()
                        .stream()
                        .map((role)-> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList()));
            return authenticatedUser;
        }else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }
}

