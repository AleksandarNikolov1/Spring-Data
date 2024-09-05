package com.example.gamestore.services;

import com.example.gamestore.models.entities.Game;
import com.example.gamestore.models.entities.User;

import java.util.Set;

public interface OrderService {

   void removeItem(String gameTitle);
   void addItem(String gameTitle);
   void buyItem();

}
