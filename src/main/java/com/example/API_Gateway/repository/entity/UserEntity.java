package com.example.API_Gateway.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "user")
public class UserEntity {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

}
