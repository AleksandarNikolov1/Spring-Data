package com.example.productshop.services.impl;

import com.example.productshop.models.dtos.*;
import com.example.productshop.models.entities.User;
import com.example.productshop.repositories.UserRepository;
import com.example.productshop.services.UserService;
import com.example.productshop.utils.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final String FILE_PATH = "src/main/resources/json-files/users.json";
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper, ValidationUtil validationUtil, Gson gson) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public void seedUsers() throws IOException {
        if (userRepository.count() == 0) {

            Arrays.stream(gson.fromJson(
                            Files.readString(Path.of(FILE_PATH)), UserSeedDto[].class))
                    .filter(validationUtil::isValid)
                    .map(userSeedDto -> mapper.map(userSeedDto, User.class))
                    .forEach(userRepository::save);
        }
    }

    @Override
    public User findRandomUser() {
        long randomId = ThreadLocalRandom.current()
                .nextLong(1, userRepository.count() + 1);

        return userRepository.findById(randomId).orElse(null);
    }

    @Override
    public List<UserSoldDto> findAllUsersWithMoreThanOneSoldProductOrderByLastNameThenFirstName() {
        return userRepository
                .findAllUsersWithMoreThanOneSoldProductOrderByLastNameThenFirstName()
                .stream()
                .map(user -> mapper.map(user, UserSoldDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserSoldProductsDto getAllUsersBySoldProducts() {

        UserSoldProductsDto userSoldProductsDto = new UserSoldProductsDto();

        List<SellerDto> sellers = userRepository
                .findAllUsersWithMoreThanOneSoldProductOrderProductsCount()
                .stream()
                .map(user ->{
                    SellerDto sellerDto = mapper.map(user, SellerDto.class);

                    SoldProductDto soldProductDto = new SoldProductDto();
                    soldProductDto.setProductsCount(user.getSoldProducts().size());
                    soldProductDto.setProducts(
                           user.getSoldProducts()
                                   .stream()
                                   .map(product -> mapper.map(product, ProductDto.class))
                                   .collect(Collectors.toList()));


                    sellerDto.setSoldProducts(soldProductDto);


                    return sellerDto;
                } )
                .toList();


        userSoldProductsDto.setUsersCount(sellers.size());
        userSoldProductsDto.setUsers(sellers);

        return userSoldProductsDto;
    }
}
