package com.hackwiz.pragati.enums;

import lombok.Getter;

@Getter
public enum Skill {

    CARPENTER("Carpenter", 100.0f),
    PLUMBER("Plumber", 100.0f);

    private String name;
    private float rate;

    Skill(String name, float rate) {
        this.name = name;
        this.rate = rate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRate(Integer id) {
        this.rate = id;
    }

}
