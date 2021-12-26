package com.example.character.controller;


import com.example.character.model.AnimeCharacter;
import com.example.character.repository.AnimeCharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
public class AnimeCharacterController {

    @Autowired
    private AnimeCharacterRepository animeCharacterRepository;

    @PostConstruct
    public void fillDB() {
        if (animeCharacterRepository.count() == 0) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM");
            LocalDate date = LocalDate.now();
            animeCharacterRepository.save(new AnimeCharacter("Mikasa Ackerman", 0, dtf.format(date)));
            animeCharacterRepository.save(new AnimeCharacter("Naruto Uzumaki", 1, dtf.format(date)));
            animeCharacterRepository.save(new AnimeCharacter("Izuku Midoriya", 1, dtf.format(date)));
            animeCharacterRepository.save(new AnimeCharacter("Sakura Haruno", 0, dtf.format(date)));
            animeCharacterRepository.save(new AnimeCharacter("Eren Yeager", 1, dtf.format(date)));

        }

        System.out.println(animeCharacterRepository);
    }

    @GetMapping("/characters")
    public List<AnimeCharacter> getCharacters() {
        return animeCharacterRepository.findAll();
    }

    @GetMapping("/characters/{name}")
    public AnimeCharacter getCharacterByName(@PathVariable String name) {
        return animeCharacterRepository.findCharacterByNameContaining(name);
    }

    @GetMapping("/characters/gender/{gender}")
    public List<AnimeCharacter> getCharactersByGender(@PathVariable int gender) {
        return animeCharacterRepository.findCharactersByGender(gender);
    }

    @PostMapping("/characters")
    public AnimeCharacter addCharacter(@RequestBody AnimeCharacter character) {

        animeCharacterRepository.save(character);

        return character;
    }

    @PutMapping("/characters")
    public AnimeCharacter updatecharacter(@RequestBody AnimeCharacter updatedCharacter) {
        AnimeCharacter retrievedCharacter = animeCharacterRepository.findCharacterById(updatedCharacter.getId());

        retrievedCharacter.setName(updatedCharacter.getName());
        retrievedCharacter.setGender(updatedCharacter.getGender());
        retrievedCharacter.setBirthday(updatedCharacter.getBirthday());

        animeCharacterRepository.save(retrievedCharacter);

        return retrievedCharacter;
    }

    @DeleteMapping("/characters/{Id}")
    public ResponseEntity deletecharacter(@PathVariable String Id) {
        AnimeCharacter character = animeCharacterRepository.findCharacterById(Id);
        if (character != null) {
            animeCharacterRepository.delete(character);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
