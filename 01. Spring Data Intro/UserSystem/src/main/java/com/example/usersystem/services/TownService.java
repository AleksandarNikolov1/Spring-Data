package com.example.usersystem.services;


import com.example.usersystem.models.Town;

import java.util.List;

public interface TownService {
    void seedTowns();
    List<Town> generateTowns();
}
