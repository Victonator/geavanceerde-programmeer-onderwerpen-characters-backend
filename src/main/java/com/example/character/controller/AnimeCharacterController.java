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
            animeCharacterRepository.save(new AnimeCharacter("Your name","Taki Tachibana", 1, dtf.format(date)));
            animeCharacterRepository.save(new AnimeCharacter("Jujutsu Kaisen","Yuji Itadori", 1, dtf.format(date)));
            animeCharacterRepository.save(new AnimeCharacter("Your name","Mitsuha Miyamizu", 0, dtf.format(date)));
            animeCharacterRepository.save(new AnimeCharacter("Jujutsu Kaisen","Megumi Fushiguro", 1, dtf.format(date)));
            animeCharacterRepository.save(new AnimeCharacter("Your name","Miki Okudera", 0, dtf.format(date)));
            animeCharacterRepository.save(new AnimeCharacter("Jujutsu Kaisen","Nobara Kugisaki", 0, dtf.format(date)));

        }
    }


    @GetMapping("/characters/{name}")
    public AnimeCharacter getCharacterByName(@PathVariable String name) {
        return animeCharacterRepository.findAnimeCharacterByNameEquals(name);
    }
    @GetMapping("/characters/anime/{animeName}")
    public List<AnimeCharacter> getCharacterByAnime(@PathVariable String animeName) {
        return animeCharacterRepository.findCharactersByAnimeName(animeName);
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

        retrievedCharacter.setanimeName(updatedCharacter.getanimeName());
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
