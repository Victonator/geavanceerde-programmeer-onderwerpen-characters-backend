package com.example.character;

import com.example.character.model.AnimeCharacter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AnimeCharacterModelTests {


    /**
     * Test of constructor en getters
     */
    @Test
    void testConstructorEnGetters() {
        AnimeCharacter animeCharacter = new AnimeCharacter(1,"Test",1,"12/08");
        assertEquals(1, animeCharacter.getAnimeId());
        assertEquals("Test", animeCharacter.getName());
        assertEquals(1, animeCharacter.getGender());
        assertEquals("12/08", animeCharacter.getBirthday());
    }


    /**
     * Test of setters.
     */
    @Test
    public void testSetters() {
        AnimeCharacter animeCharacter = new AnimeCharacter();

        animeCharacter.setName("Test");
        animeCharacter.setGender(1);
        animeCharacter.setName("12/08");

        assertEquals("Test", animeCharacter.getName());
        assertEquals(1, animeCharacter.getGender());
        assertEquals("12/08", animeCharacter.getBirthday());
    }




}
