package com.example.character;

import com.example.character.model.AnimeCharacter;
import com.example.character.repository.AnimeCharacterRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AnimeCharacterControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnimeCharacterRepository animeCharacterRepository;

    private ObjectMapper mapper = new ObjectMapper();


    /**
     * Test of getAllCharacters.
     */
    @Test
    public void givenCharacter_whenGetCharactersByAnimeId_thenReturnJsonCharacters() throws Exception {
        AnimeCharacter animeCharacter = new AnimeCharacter(1,"Test",1,"12/08");
        AnimeCharacter animeCharacter2 = new AnimeCharacter(1,"Test2",0,"14/03");


        List<AnimeCharacter> animeCharacterList = new ArrayList<>();
        animeCharacterList.add(animeCharacter);
        animeCharacterList.add(animeCharacter2);

        given(animeCharacterRepository.findCharactersByAnimeId(1)).willReturn(animeCharacterList);

        mockMvc.perform(get("/characters/anime/{animeId}",1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].animeId",is(1)))
                .andExpect(jsonPath("$.[0].name",is("Test")))
                .andExpect(jsonPath("$.[0].gender",is(1)))
                .andExpect(jsonPath("$.[0].birthday",is("12/08")))
                .andExpect(jsonPath("$.[1].animeId",is(1)))
                .andExpect(jsonPath("$.[1].name",is("Test2")))
                .andExpect(jsonPath("$.[1].gender",is(0)))
                .andExpect(jsonPath("$.[1].birthday",is("14/03")));
    }


    /**
     * Test of getCharacterByName.
     */
    @Test
    public void givenCharacter_whenGetCharacterByName_thenReturnJsonCharacter() throws Exception {
        AnimeCharacter animeCharacter = new AnimeCharacter(3,"Test",1,"12/08");

        given(animeCharacterRepository.findAnimeCharacterByNameEquals("Test")).willReturn(animeCharacter);

        mockMvc.perform(get("/characters/{name}","Test"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.animeId",is(3)))
                .andExpect(jsonPath("$.name",is("Test")))
                .andExpect(jsonPath("$.gender",is(1)))
                .andExpect(jsonPath("$.birthday",is("12/08")));
    }

    /**
     * Test of getCharacterByName.
     */
    @Test
    public void givenCharacter_whenGetCharactersByGender_thenReturnJsonCharacter() throws Exception {
        AnimeCharacter animeCharacter = new AnimeCharacter(1,"Test",1,"12/08");
        AnimeCharacter animeCharacter2 = new AnimeCharacter(2,"Test2",1,"14/03");


        List<AnimeCharacter> animeCharacterList = new ArrayList<>();
        animeCharacterList.add(animeCharacter);
        animeCharacterList.add(animeCharacter2);

        given(animeCharacterRepository.findCharactersByGender(1)).willReturn(animeCharacterList);

        mockMvc.perform(get("/characters/gender/{gender}",1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].animeId",is(1)))
                .andExpect(jsonPath("$.[0].name",is("Test")))
                .andExpect(jsonPath("$.[0].gender",is(1)))
                .andExpect(jsonPath("$.[0].birthday",is("12/08")))
                .andExpect(jsonPath("$.[1].animeId",is(2)))
                .andExpect(jsonPath("$.[1].name",is("Test2")))
                .andExpect(jsonPath("$.[1].gender",is(1)))
                .andExpect(jsonPath("$.[1].birthday",is("14/03")));
    }

    /**
     * Test of postCharacter.
     */

    @Test
    public void whenPostCharacter_thenReturnJsonCharacter() throws Exception{
        AnimeCharacter animeCharacter = new AnimeCharacter(1,"Test",1,"12/08");

        mockMvc.perform(post("/characters")
                .content(mapper.writeValueAsString(animeCharacter))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.animeId",is(1)))
                .andExpect(jsonPath("$.name",is("Test")))
                .andExpect(jsonPath("$.gender",is(1)))
                .andExpect(jsonPath("$.birthday",is("12/08")));
    }

    /**
     * Test of putCharacter.
     */
    @Test
    public void givenReview_whenPutReview_thenReturnJsonReview() throws Exception{
        AnimeCharacter animeCharacter = new AnimeCharacter(1,"Test",1,"12/08");

        given(animeCharacterRepository.findCharacterById(animeCharacter.getId())).willReturn(animeCharacter);

        AnimeCharacter updatedAnimeCharacter = animeCharacter;
        updatedAnimeCharacter.setAnimeId(0);
        updatedAnimeCharacter.setName("Test2");
        updatedAnimeCharacter.setGender(0);
        updatedAnimeCharacter.setBirthday("14/10");


        mockMvc.perform(put("/characters")
                .content(mapper.writeValueAsString(updatedAnimeCharacter))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.animeId",is(0)))
                .andExpect(jsonPath("$.name",is("Test2")))
                .andExpect(jsonPath("$.gender",is(0)))
                .andExpect(jsonPath("$.birthday",is("14/10")));
    }

    /**
     * Test of deleteCharacter.
     */
    @Test
    public void givenCharacter_whenDeleteCharacter_thenStatusOk() throws Exception{
        AnimeCharacter animeCharacter = new AnimeCharacter(1,"Test",1,"12/08");


        given(animeCharacterRepository.findCharacterById("1")).willReturn(animeCharacter);

        mockMvc.perform(delete("/characters/{id}","1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    /**
     * Test of deleteCharacter when no Character given.
     */
    @Test
    public void givenNoCharacter_whenDeleteCharacter_thenStatusNotFound() throws Exception{

        given(animeCharacterRepository.findCharacterById("NotFound888")).willReturn(null);

        mockMvc.perform(delete("/characters/{id}","NotFound888")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
