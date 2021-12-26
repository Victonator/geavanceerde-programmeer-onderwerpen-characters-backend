package com.example.character.controller;



import com.example.character.model.AnimeCharacter;
import com.example.character.repository.AnimeCharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class AnimeCharacterController {

    @Autowired
    private AnimeCharacterRepository animeCharacterRepository;

    @PostConstruct
    public void fillDB(){
        if(animeCharacterRepository.count()==0){
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM");
            LocalDate date = LocalDate.now();
            animeCharacterRepository.save(new AnimeCharacter("Mikasa Ackerman",0, dtf.format(date)));
            animeCharacterRepository.save(new AnimeCharacter("Naruto Uzumaki",1,dtf.format(date)));
        }

        System.out.println(animeCharacterRepository);
    }

    @GetMapping("/characters}")
    public List<AnimeCharacter> getCharacters(@PathVariable String name){
        return animeCharacterRepository.findAll();
    }

    @GetMapping("/characters/{name}")
    public AnimeCharacter getCharacterByName(@PathVariable String name){
        return animeCharacterRepository.findCharacterByNameContaining(name);
    }

    @GetMapping("/characters/gender/{gender}")
    public List<AnimeCharacter> getReviewsByISBN(@PathVariable int gender){
        return animeCharacterRepository.findCharactersByGenderContaining(gender);
    }

}
