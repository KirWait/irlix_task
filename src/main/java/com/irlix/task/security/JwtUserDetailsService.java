package com.irlix.task.security;

import com.irlix.task.security.jwt.JwtFactory;
import com.irlix.task.security.jwt.JwtUser;
import com.irlix.task.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;
    @Autowired
    public JwtUserDetailsService(UserService userService){
        this.userService = userService;
    }



    @Override
    public UserDetails loadUserByUsername(String userName){

        JwtUser jwtUser = null;
        try {
            jwtUser = JwtFactory.create(userService.findByUsername(userName));
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        return jwtUser;
    }
}
