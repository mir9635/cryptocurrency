package com.example.cryptocurrency.repository;

import com.example.cryptocurrency.entity.CurrencyEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CurrencyRepo extends CrudRepository<CurrencyEntity, Long> {

    CurrencyEntity findBySymbol(String symbol);

    List<CurrencyEntity> findAll();
}
