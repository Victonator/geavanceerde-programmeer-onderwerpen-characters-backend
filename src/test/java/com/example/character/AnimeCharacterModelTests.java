package com.example.character;

import com.example.character.model.AnimeCharacter;
import com.example.character.repository.AnimeCharacterRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class AnimeCharacterModelTests {

    @MockBean
    private AnimeCharacterRepository animeCharacterRepository;


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
        animeCharacter.setBirthday("12/08");

        assertEquals("Test", animeCharacter.getName());
        assertEquals(1, animeCharacter.getGender());
        assertEquals("12/08", animeCharacter.getBirthday());
    }




}
