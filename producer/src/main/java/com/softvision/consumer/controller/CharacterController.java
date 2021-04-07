package com.softvision.consumer.controller;

import com.softvision.consumer.domain.Character;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping(path = "/characters")
public class CharacterController {

    private final CharacterService characterService;

    @Autowired
    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping(path = "/{id}")
    public Character getCharter(@PathVariable("id") String id) {
        return new Character("Test", "Dark");
    }

//    @GetMapping
//    public List<Character> getCharters() {
//        return characterService.getCharters();
//    }

    @PostMapping(path = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Integer> createCharacter(@RequestBody Character character) {
        return Map.of("id", new Random().nextInt(10));
    }

}
