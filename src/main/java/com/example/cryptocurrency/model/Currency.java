package com.example.cryptocurrency.model;

import com.example.cryptocurrency.entity.CurrencyEntity;

public class Currency {
    private String symbol;
    private String price_usd;
    private String id_number;


    public static Currency toCurrency(CurrencyEntity entity) {
        Currency currency = new Currency();
        currency.setSymbol(entity.getSymbol());
        currency.setPrice_usd(entity.getPrice_usd());
        currency.setId_number(entity.getId_number());
        return currency;
    }

    public Currency() {
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPrice_usd() {
        return price_usd;
    }

    public void setPrice_usd(String price_usd) {
        this.price_usd = price_usd;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }
}
