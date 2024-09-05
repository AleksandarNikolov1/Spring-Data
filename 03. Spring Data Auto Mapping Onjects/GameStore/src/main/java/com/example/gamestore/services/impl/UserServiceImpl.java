package com.example.gamestore.services.impl;

import com.example.gamestore.models.dtos.UserLoginDto;
import com.example.gamestore.models.dtos.UserRegisterDto;
import com.example.gamestore.models.entities.Game;
import com.example.gamestore.models.entities.User;
import com.example.gamestore.repositories.GameRepository;
import com.example.gamestore.repositories.UserRepository;
import com.example.gamestore.services.UserService;
import com.example.gamestore.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final GameRepository gameRepository;
    private User loggedInUser;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gameRepository = gameRepository;
    }

    @Override
    public void registerUser(UserRegisterDto userRegisterDto) {
        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())){
            System.out.println("Wrong confirm password");
            return;
        }

        Set<ConstraintViolation<UserRegisterDto>> violations =
                validationUtil.violations(userRegisterDto);

        if (!violations.isEmpty()){
            violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);

            return;
        }

        User user = modelMapper.map(userRegisterDto, User.class);
        userRepository.save(user);
    }

    @Override
    public void loginUser(UserLoginDto userLoginDto) {
        Set<ConstraintViolation<UserLoginDto>> violations =
                validationUtil.violations(userLoginDto);

        if (!violations.isEmpty()){
            violations.stream().map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);

            return;
        }

        User user = userRepository.findByEmailAndPassword(
                userLoginDto.getEmail(), userLoginDto.getPassword()
        ).orElse(null);

        if (user == null){
            System.out.println("Incorrect username / password");
            return;
        }

        loggedInUser = user;
    }

    @Override
    public void logout() {
        if (loggedInUser == null){
            System.out.println("Cannot logout. No user was logged in");
        }

        else
            loggedInUser = null;
    }

    @Override
    public User getLoggedUser() {
        return loggedInUser;
    }
}
