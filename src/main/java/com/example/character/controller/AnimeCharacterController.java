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

@RestController
public class AnimeCharacterController {

    @Autowired
    private AnimeCharacterRepository animeCharacterRepository;

    @PostConstruct
    public void fillDB() {
        if (animeCharacterRepository.count() == 0) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM");
            LocalDate date = LocalDate.now();
            animeCharacterRepository.save(new AnimeCharacter(1,"Mikasa Ackerman", 0, dtf.format(date)));
            animeCharacterRepository.save(new AnimeCharacter(2,"Naruto Uzumaki", 1, dtf.format(date)));
            animeCharacterRepository.save(new AnimeCharacter(3,"Izuku Midoriya", 1, dtf.format(date)));
            animeCharacterRepository.save(new AnimeCharacter(2,"Sakura Haruno", 0, dtf.format(date)));
            animeCharacterRepository.save(new AnimeCharacter(1,"Eren Yeager", 1, dtf.format(date)));

        }
    }


    @GetMapping("/characters/{name}")
    public AnimeCharacter getCharacterByName(@PathVariable String name) {
        return animeCharacterRepository.findAnimeCharacterByNameEquals(name);
    }
    @GetMapping("/characters/anime/{animeId}")
    public List<AnimeCharacter> getCharacterByAnime(@PathVariable int animeId) {
        return animeCharacterRepository.findCharactersByAnimeId(animeId);
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
    public AnimeCharacter updateCharacter(@RequestBody AnimeCharacter updatedCharacter) {
        AnimeCharacter retrievedCharacter = animeCharacterRepository.findCharacterById(updatedCharacter.getId());

        retrievedCharacter.setName(updatedCharacter.getName());
        retrievedCharacter.setGender(updatedCharacter.getGender());
        retrievedCharacter.setBirthday(updatedCharacter.getBirthday());

        animeCharacterRepository.save(retrievedCharacter);

        return retrievedCharacter;
    }

    @DeleteMapping("/characters/{id}")
    public ResponseEntity deleteCharacter(@PathVariable String id) {
        AnimeCharacter character = animeCharacterRepository.findCharacterById(id);
        if (character != null) {
            animeCharacterRepository.delete(character);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
