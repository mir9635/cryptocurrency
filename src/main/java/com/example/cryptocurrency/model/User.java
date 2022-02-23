package com.example.cryptocurrency.model;

import com.example.cryptocurrency.entity.UserEntity;
import net.bytebuddy.implementation.bind.annotation.Default;

public class User {
    private String name;
    private String symbol;
    private String save_price;
    private Boolean complete = true;

    public static User toUser(UserEntity entity) {
        User user = new User();
        user.setName(entity.getName());
        user.setSymbol(entity.getSymbol());
        user.setSave_price(entity.getSave_price());
        return user;
    }

    public User() {
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSave_price() {
        return save_price;
    }

    public void setSave_price(String save_price) {
        this.save_price = save_price;
    }
}
