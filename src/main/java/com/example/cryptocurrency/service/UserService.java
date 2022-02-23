package com.example.cryptocurrency.service;

import com.example.cryptocurrency.entity.CurrencyEntity;
import com.example.cryptocurrency.entity.UserEntity;
import com.example.cryptocurrency.model.User;
import com.example.cryptocurrency.repository.CurrencyRepo;
import com.example.cryptocurrency.repository.UserRepo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

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
@EnableScheduling
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CurrencyRepo currencyRepo;


    private static HttpURLConnection connection;
    private static final Logger logger = Logger.getGlobal();

    @PutMapping
    public User createUser(UserEntity entity) {
        CurrencyEntity currency = currencyRepo.findBySymbol(entity.getSymbol());
        entity.setComplete(true);
        entity.setSymbol(currency.getSymbol());
        entity.setSave_price(currency.getPrice_usd());
        return User.toUser(userRepo.save(entity));
    }

    public List<UserEntity> getAll() {
        return userRepo.findAll();
    }


    @Scheduled(fixedRate = 60000)
    public void complete() {
        List<CurrencyEntity> list = currencyRepo.findAll();
        for (CurrencyEntity currency : list) {
            currency.setPrice_usd(strJSON(currency.getId_number()));
            currencyRepo.save(currency);
        }
        if (!getAll().isEmpty()) {
            for (UserEntity user : getAll()) {
                CurrencyEntity currency = currencyRepo.findBySymbol(user.getSymbol());
                if (user.getComplete()) {
                    try {
                        System.out.println(user.getSave_price() + " " + currency.getPrice_usd());
                        double percent = round(parsePrice(user.getSave_price(), currency.getPrice_usd()));
                        if (percent > 1. || percent < -1) {
                            user.setComplete(false);
                            userRepo.save(user);
                            logger.warning("Код валюты: " + user.getSymbol() + " Имя пользователя: " + user.getName() + " Процент изменения цены: " + percent);
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }


    public String strJSON(String id) {
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        try {
            URL url = new URL("https://api.coinlore.net/api/ticker/?id=80&id=90&id=" + id);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();

            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
            return parse(responseContent.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return null;
    }

    public static String parse(String responseBody) {
        JSONArray cryptocurrencys = new JSONArray(responseBody);
        String price_usd = "";
        JSONObject cryptocurrency = cryptocurrencys.getJSONObject(0);
        price_usd = cryptocurrency.getString("price_usd");
        return price_usd;
    }

    public static Double parsePrice(String user, String currency) {
        try {
            double userUsd = Double.parseDouble(user);
            double currencyUsd = Double.parseDouble(currency);
            double value = currencyUsd * 100 / userUsd - 100;

            return value;
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public static double round(double value) {

        long factor = (long) Math.pow(10, 2);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
