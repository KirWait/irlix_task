package com.irlix.task.controller;

import com.irlix.task.dto.UserRequestDto;
import com.irlix.task.dto.UserResponseDto;
import com.irlix.task.entity.UserEntity;
import com.irlix.task.mapper.UserMapper;
import com.irlix.task.security.jwt.JwtTokenProvider;
import com.irlix.task.service.UserService;
import javassist.NotFoundException;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
                                    UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserRequestDto requestDto) throws NotFoundException {


            String username = requestDto.getUsername();

            UserEntity user = userService.findByUsername(username);

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));

            logger.info(String.format(("Successfully authenticated with %s username"), username));

            String token = jwtTokenProvider.createToken(username, user.getRoles());

            logger.info(String.format(("Successfully created JWT token: %s"), token));


            return new ResponseEntity<>(String.format(
                    "Hi! You have successfully logged in with %s! Here is your token %s",username, token),  HttpStatus.OK);

    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRequestDto requestDto) {

        UserEntity user = mapper.userRequestDTOToUserEntity(requestDto);
        userService.save(user);
        UserResponseDto responseDto = mapper.userEntityToUserResponseDTO(user);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

}

