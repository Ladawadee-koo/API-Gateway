package com.example.API_Gateway.repository;

import com.example.API_Gateway.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {

}
