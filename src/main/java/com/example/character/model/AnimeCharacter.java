package com.example.character.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



public class AnimeCharacter {

    @Id
    private String id;

    private String animeName;

    private String name;

    private int gender;

    private String birthday;

    public AnimeCharacter() {
    }

    public AnimeCharacter(String animeName, String name, int gender, String birthday) {
        this.animeName = animeName;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getanimeName() {
        return animeName;
    }

    public void setanimeName(String animeName) {
        this.animeName = animeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
