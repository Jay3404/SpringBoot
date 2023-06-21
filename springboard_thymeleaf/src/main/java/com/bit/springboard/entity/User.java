package com.bit.springboard.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "T_USER")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String userId;
    private String userPw;
    private String userName;
    private String userEmail;
    private String userTel;
    private LocalDateTime userRegdate = LocalDateTime.now();


}
