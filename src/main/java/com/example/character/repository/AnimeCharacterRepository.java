package com.example.character.repository;

import com.example.character.model.AnimeCharacter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimeCharacterRepository extends MongoRepository<AnimeCharacter, String> {

    AnimeCharacter findAnimeCharacterByNameEquals(String name);
    AnimeCharacter findCharacterById(String Id);
    List<AnimeCharacter> findCharactersByGender(int gender);
    List<AnimeCharacter> findCharactersByAnimeName(String animeName);
}
