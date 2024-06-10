package com.kulesza.rafal.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(Types.CHAR)
    private UUID id;

    private String username;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(Types.VARCHAR)
    private Gender gender;

    private Integer age;

    @CreationTimestamp
    private Instant createdAt;

    public User() {
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
