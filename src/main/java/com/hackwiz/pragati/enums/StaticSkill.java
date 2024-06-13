package com.hackwiz.pragati.enums;

import lombok.Getter;

@Getter
public enum StaticSkill {

    CARPENTER("Carpenter", 300.0f, "https://storage.googleapis.com/staging.hackmee-hack-wizards-2-0-0622.appspot.com/skill_image_stock/carpenter2.jpg"),
    PLUMBER("Plumber", 300.0f, "https://storage.googleapis.com/staging.hackmee-hack-wizards-2-0-0622.appspot.com/skill_image_stock/plumber.jpg"),
    DELIVERY_AGENT("Delivery Agent", 100.0f, "https://storage.googleapis.com/staging.hackmee-hack-wizards-2-0-0622.appspot.com/skill_image_stock/delivery_agent.jpg"),
    ELECTRICIAN("Electrician", 200.0f, "https://storage.googleapis.com/staging.hackmee-hack-wizards-2-0-0622.appspot.com/skill_image_stock/electrician.jpg"),
    SECURITY_GUARD("Security Guard", 500.0f, "https://storage.googleapis.com/staging.hackmee-hack-wizards-2-0-0622.appspot.com/skill_image_stock/security_guard.jpg"),
    COOK("Cook", 240.0f, "https://storage.googleapis.com/staging.hackmee-hack-wizards-2-0-0622.appspot.com/skill_image_stock/cook.jpg"),
    CLEANER("Cleaner", 200.0f, "https://storage.googleapis.com/staging.hackmee-hack-wizards-2-0-0622.appspot.com/skill_image_stock/cleaner.jpg"),
    PAINTER("Painter", 300.0f, "https://storage.googleapis.com/staging.hackmee-hack-wizards-2-0-0622.appspot.com/skill_image_stock/painter.jpg");


    private String name;
    private float rate;
    private String image;

    StaticSkill(String name, float rate, String image) {
        this.name = name;
        this.rate = rate;
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRate(Integer id) {
        this.rate = id;
    }

}
