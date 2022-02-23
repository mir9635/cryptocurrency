package com.example.cryptocurrency.service;

import com.example.cryptocurrency.entity.CurrencyEntity;
import com.example.cryptocurrency.entity.UserEntity;
import com.example.cryptocurrency.exception.InvalidCurrencyCode;
import com.example.cryptocurrency.repository.CurrencyRepo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepo currencyRepo;

    public CurrencyEntity getOne(String symbol) throws InvalidCurrencyCode {
        CurrencyEntity currency = currencyRepo.findBySymbol(symbol);
        if (currency == null) {
            throw new InvalidCurrencyCode("Неправильный код валюты");
        }
        return currency;
    }

    public void create() {
        CurrencyEntity currency1 = new CurrencyEntity();
        currency1.setId_number("80");
        currency1.setSymbol("ETH");
        currency1.setPrice_usd("2893.49");
        currencyRepo.save(currency1);

        CurrencyEntity currency2 = new CurrencyEntity();
        currency2.setId_number("90");
        currency2.setSymbol("BTC");
        currency2.setPrice_usd("42027.50");
        currencyRepo.save(currency2);

        CurrencyEntity currency3 = new CurrencyEntity();
        currency3.setId_number("48543");
        currency3.setSymbol("SOL");
        currency3.setPrice_usd("92.08");
        currencyRepo.save(currency3);


    }

    public List<CurrencyEntity> getAll() {
        return currencyRepo.findAll();
    }


}
