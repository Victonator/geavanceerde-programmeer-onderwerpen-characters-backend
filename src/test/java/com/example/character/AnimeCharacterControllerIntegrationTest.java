package com.example.character;

import com.example.character.model.AnimeCharacter;
import com.example.character.repository.AnimeCharacterRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AnimeCharacterControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AnimeCharacterRepository animeCharacterRepository;

    /**
     * Create characters
     */
    AnimeCharacter animeCharacter1 = new AnimeCharacter(1,"Test1",0,"12/08");
    AnimeCharacter animeCharacter2 = new AnimeCharacter(2,"Test2",1,"12/08");
    AnimeCharacter animeCharacter3 = new AnimeCharacter(2,"Test3",2,"14/10");
    AnimeCharacter animeCharacter4 = new AnimeCharacter(1,"Test4",1,"14/10");


    /**
     * Fill Database
     */
    @BeforeEach
    public void beforeAllTests() {
        animeCharacterRepository.deleteAll();
        animeCharacterRepository.save(animeCharacter1);
        animeCharacterRepository.save(animeCharacter2);
        animeCharacterRepository.save(animeCharacter3);
        animeCharacterRepository.save(animeCharacter4);

    }

    /**
     * Clear characters
     */
    @AfterEach
    public void afterAllTests() {
        //Watch out with deleteAll() methods when you have other data in the test database!
        animeCharacterRepository.deleteAll();
    }

    private
    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenCharacter_whenGetCharactersByAnimeId_thenReturnJsonCharacters() throws Exception {

        List<AnimeCharacter> characterList = new ArrayList<>();
        characterList.add(animeCharacter1);
        characterList.add(animeCharacter4);

        mockMvc.perform(get("/characters/anime/{animeId}", 1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].animeId", is(1)))
                .andExpect(jsonPath("$.[0].name", is("Test1")))
                .andExpect(jsonPath("$.[0].gender", is(0)))
                .andExpect(jsonPath("$.[0].birthday", is("12/08")))
                .andExpect(jsonPath("$.[1].animeId", is(1)))
                .andExpect(jsonPath("$.[1].name", is("Test4")))
                .andExpect(jsonPath("$.[1].gender", is(1)))
                .andExpect(jsonPath("$.[1].birthday", is("14/10")));
    }

    @Test
    public void givenCharacter_whenGetcharactersByName_thenReturnJsonCharacters() throws Exception {

        List<AnimeCharacter> characterList = new ArrayList<>();
        characterList.add(animeCharacter1);

        mockMvc.perform(get("/characters/{name}", "Test1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.animeId", is(1)))
                .andExpect(jsonPath("$.name", is("Test1")))
                .andExpect(jsonPath("$.gender", is(0)))
                .andExpect(jsonPath("$.birthday", is("12/08")));
    }

    @Test
    public void givenCharacter_whenGetCharactersByGender_thenReturnJsonCharacter() throws Exception {

        List<AnimeCharacter> animeCharacterList = new ArrayList<>();
        animeCharacterList.add(animeCharacter2);
        animeCharacterList.add(animeCharacter4);

        mockMvc.perform(get("/characters/gender/{gender}",1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].animeId",is(2)))
                .andExpect(jsonPath("$.[0].name",is("Test2")))
                .andExpect(jsonPath("$.[0].gender",is(1)))
                .andExpect(jsonPath("$.[0].birthday",is("12/08")))
                .andExpect(jsonPath("$.[1].animeId",is(1)))
                .andExpect(jsonPath("$.[1].name",is("Test4")))
                .andExpect(jsonPath("$.[1].gender",is(1)))
                .andExpect(jsonPath("$.[1].birthday",is("14/10")));
    }

    @Test
    public void whenPostCharacter_thenReturnJsonCharacter() throws Exception {
        AnimeCharacter animeCharacter5 = new AnimeCharacter(3,"Test5",1,"15/05");

        mockMvc.perform(post("/characters")
                .content(mapper.writeValueAsString(animeCharacter5))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.animeId",is(3)))
                .andExpect(jsonPath("$.name",is("Test5")))
                .andExpect(jsonPath("$.gender",is(1)))
                .andExpect(jsonPath("$.birthday",is("15/05")));
    }

    @Test
    public void givencharacter_whenPutcharacter_thenReturnJsoncharacter() throws Exception {

        AnimeCharacter updatedcharacter = new AnimeCharacter(3,"updateTest",1,"16/05");
        updatedcharacter.setId(animeCharacter3.getId());

        mockMvc.perform(put("/characters")
                .content(mapper.writeValueAsString(updatedcharacter))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.animeId",is(3)))
                .andExpect(jsonPath("$.name",is("updateTest")))
                .andExpect(jsonPath("$.gender",is(1)))
                .andExpect(jsonPath("$.birthday",is("16/05")));
    }

    @Test
    public void givencharacter_whenDeletecharacter_thenStatusOk() throws Exception {

        mockMvc.perform(delete("/characters/{id}", animeCharacter3.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNocharacter_whenDeletecharacter_thenStatusNotFound() throws Exception {

        mockMvc.perform(delete("/characters/{id}", "notFound888")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
