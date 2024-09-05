package com.example.shampoocompany.services.impl;

import com.example.shampoocompany.entities.Ingredient;
import com.example.shampoocompany.entities.Shampoo;
import com.example.shampoocompany.entities.Size;
import com.example.shampoocompany.repositories.ShampooRepository;
import com.example.shampoocompany.services.ShampooService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShampooServiceImpl implements ShampooService {
    private final ShampooRepository shampooRepository;

    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public List<String> findAllBySize(Size size) {
        return shampooRepository
                .findAllBySize(size)
                .stream()
                .map(s -> String.format("%s %s %.2flv.",
                        s.getBrand(), s.getSize().name(), s.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBySizeOrLabelIdOrderByPrice(Size size, Long id) {
        return shampooRepository
                .findAllBySizeOrLabelIdOrderByPrice(size, id)
                .stream()
                .map(s -> String.format("%s %s %.2flv.",
                        s.getBrand(), s.getSize().name(), s.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllByPriceGreaterThan(BigDecimal price) {
        return shampooRepository
                .findAllByPriceGreaterThanOrderByPriceDesc(price)
                .stream()
                .map(s -> String.format("%s %s %.2flv.",
                        s.getBrand(), s.getSize().name(), s.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public int countAllByPriceLessThan(BigDecimal price) {
        return shampooRepository.countAllByPriceLessThan(price);
    }

    @Override
    public Set<String> findAllByContainingIngredientsIn(Set<String> names) {
        return shampooRepository
                .findAllByContainingIngredientsIn(names)
                .stream()
                .map(Shampoo::getBrand)
                .collect(Collectors.toSet());
    }

    @Override
    public List<String> findAllByIngredientsCountLessThan(int count) {
        return shampooRepository
                .findAllByIngredientsCountLess(count)
                .stream()
                .map(Shampoo::getBrand)
                .collect(Collectors.toList());
    }
}
