package com.softvision.consumer.controller;

import com.softvision.consumer.domain.Character;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {
    public List<Character> getCharters() {
        return List.of(
//                new Character("Test1", "Light"),
                new Character("Test2", "Dark"),
                new Character("Test3", "Light")
        );
    }
}
