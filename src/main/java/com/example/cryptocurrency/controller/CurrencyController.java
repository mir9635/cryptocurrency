package com.example.cryptocurrency.controller;

import com.example.cryptocurrency.entity.CurrencyEntity;
import com.example.cryptocurrency.exception.InvalidCurrencyCode;
import com.example.cryptocurrency.repository.CurrencyRepo;
import com.example.cryptocurrency.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/currencies")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    //получение списка валют
    @GetMapping
    public ResponseEntity getAllCurrency() {
        try {
            if (currencyService.getAll().isEmpty()) currencyService.create();
            return ResponseEntity.ok(currencyService.getAll().stream().map(e -> e.getSymbol().toString()).collect(Collectors.joining(" ")));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    //получение цены по коду валюты
    @GetMapping("/symbol")
    public ResponseEntity getOneCurrency(@RequestParam String symbol) {
        try {
            return ResponseEntity.ok(currencyService.getOne(symbol).getPrice_usd());
        } catch (InvalidCurrencyCode e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }


}
