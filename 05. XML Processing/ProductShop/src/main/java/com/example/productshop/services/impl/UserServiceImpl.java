package com.example.productshop.services.impl;

import com.example.productshop.models.dtos.*;
import com.example.productshop.models.entities.User;
import com.example.productshop.repositories.UserRepository;
import com.example.productshop.services.UserService;
import com.example.productshop.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public long getEntitiesCount() {
        return userRepository.count();
    }

    @Override
    public void seedUsers(List<UserSeedDto> users) {
        users
                .stream()
                .filter(validationUtil::isValid)
                .map(userSeedDto -> modelMapper.map(userSeedDto, User.class))
                .forEach(userRepository::save);
    }

    @Override
    public User getRandomUser() {
        long randomId = ThreadLocalRandom.current().nextLong(1, userRepository.count() + 1);

        return userRepository.findById(randomId).orElse(null);
    }

    @Override
    public UserViewRootDto findUsersWithMoreThanOneSoldProduct() {
        UserViewRootDto userViewRootDto = new UserViewRootDto();

        userViewRootDto
                .setProducts(userRepository
                        .findAllWithMoreThanOneSoldProduct()
                        .stream()
                        .map(user -> modelMapper.map(user, UserWithProductsDto.class))
                        .collect(Collectors.toList()));

        return userViewRootDto;
    }

    @Override
    public UserProductsViewRootDto getUsersAndSoldProducts() {
        UserProductsViewRootDto userProductsViewRootDto = new UserProductsViewRootDto();

        List<SellerDto> sellerDtos = userRepository.findAllUsersWithMoreThanOneSoldProductOrderProductsCount()
                .stream()
                .map(user -> {
                    SellerDto seller = modelMapper.map(user, SellerDto.class);

                    SoldProductsDto soldProductsDto = new SoldProductsDto();

                    soldProductsDto.setProductsCount(user.getSoldProducts().size());
                    soldProductsDto.setProducts(
                            user.getSoldProducts()
                                    .stream()
                                    .map(product -> modelMapper.map(product, ProductDto.class))
                                    .collect(Collectors.toList())
                    );

                    seller.setSoldProductsDto(soldProductsDto);

                    return seller;
                })
                .collect(Collectors.toList());


        userProductsViewRootDto.setUsersCount(sellerDtos.size());
        userProductsViewRootDto.setSellers(sellerDtos);

        return userProductsViewRootDto;

    }
}
