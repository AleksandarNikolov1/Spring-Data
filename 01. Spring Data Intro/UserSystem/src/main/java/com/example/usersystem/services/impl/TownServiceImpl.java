package com.example.usersystem.services.impl;

import com.example.usersystem.models.Town;
import com.example.usersystem.repositories.TownRepository;
import com.example.usersystem.services.TownService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TownServiceImpl implements TownService {

    private final TownRepository townRepository;

    public TownServiceImpl(TownRepository townRepository) {
        this.townRepository = townRepository;
    }

    @Override
    public void seedTowns() {
        if (townRepository.count() == 0) {
            townRepository.saveAll(generateTowns());
        }
    }

    public List<Town> generateTowns(){
        List<Town> towns = new ArrayList<>();

        towns.add(new Town("Sofia", "Bulgaria"));
        towns.add(new Town("Plovdiv", "Bulgaria"));
        towns.add(new Town("Varna", "Bulgaria"));
        towns.add(new Town("Burgas", "Bulgaria"));
        towns.add(new Town("Ruse", "Bulgaria"));
        towns.add(new Town("Stara Zagora", "Bulgaria"));
        towns.add(new Town("Pleven", "Bulgaria"));
        towns.add(new Town("Veliko Tarnovo", "Bulgaria"));
        towns.add(new Town("Blagoevgrad", "Bulgaria"));
        towns.add(new Town("Vidin", "Bulgaria"));

        return towns;
    }
}
