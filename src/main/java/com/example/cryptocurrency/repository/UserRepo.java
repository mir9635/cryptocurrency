package com.example.cryptocurrency.repository;

import com.example.cryptocurrency.entity.CurrencyEntity;
import com.example.cryptocurrency.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepo extends CrudRepository<UserEntity, Long> {
    List<UserEntity> findAll();
}
