package com.example.character.repository;

import com.example.character.model.AnimeCharacter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimeCharacterRepository extends MongoRepository<AnimeCharacter, String> {

    AnimeCharacter findCharacterByNameContaining(String name);
    AnimeCharacter findCharacterById(String id);
    List<AnimeCharacter> findCharactersByGender(int Gender);
}
