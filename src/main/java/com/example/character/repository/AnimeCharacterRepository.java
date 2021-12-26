package com.example.character.repository;

import com.example.character.model.AnimeCharacter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimeCharacterRepository extends MongoRepository<AnimeCharacter, String> {

    AnimeCharacter findCharacterByNameContaining(String name);
    List<AnimeCharacter> findCharactersByGenderContaining(int Gender);
}
