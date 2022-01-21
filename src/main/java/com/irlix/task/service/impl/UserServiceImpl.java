package com.irlix.task.service.impl;

import com.irlix.task.entity.UserEntity;
import com.irlix.task.enumeration.Active;
import com.irlix.task.enumeration.Roles;
import com.irlix.task.repository.UserRepository;
import com.irlix.task.service.UserService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void save(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRoles(Roles.ROLE_CUSTOMER);

        user.setActive(Active.ACTIVE);

        user.setBalance(0L);

        userRepository.save(user);

        logger.info(String.format("User with username: %s, has been registered successfully!", user.getUsername()));
    }

    @Override
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }


    @Override
    public UserEntity findByUsername(String username) throws NotFoundException {

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(String.format("No such user with username: %s", username)));
    }

    @Override
    public UserEntity findById(Long id) throws NotFoundException {
        return userRepository.findById(id)
                .orElseThrow(()->new NotFoundException(String.format("No such user with id: %d", id)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
    userRepository.deleteById(id);
    }

    @Override
    public UserEntity update(UserEntity user) {
        return userRepository.save(user);
    }
}
