package com.irlix.task.service;

import com.irlix.task.entity.UserEntity;
import javassist.NotFoundException;
import java.util.List;

public interface UserService {

    void save(UserEntity user);

    List<UserEntity> getAll();

    UserEntity findByUsername(String username) throws NotFoundException;

    UserEntity findById(Long id) throws NotFoundException;

    void delete(Long id);

    UserEntity update(UserEntity user);
}
