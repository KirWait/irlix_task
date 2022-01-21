package com.irlix.task.security.jwt;

import com.irlix.task.entity.UserEntity;
import com.irlix.task.enumeration.Active;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JwtFactory {

    public static JwtUser create(UserEntity userEntity){

        return new JwtUser(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getActive().equals(Active.ACTIVE),
                Stream.of(userEntity.getRoles().name())
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()));
    }
}
