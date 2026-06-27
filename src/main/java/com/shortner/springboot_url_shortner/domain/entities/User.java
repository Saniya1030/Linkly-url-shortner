package com.shortner.springboot_url_shortner.domain.entities;

import jakarta.persistence.*;
import org.springframework.context.support.BeanDefinitionDsl;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BeanDefinitionDsl.Role role;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public User() {}

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return  name;
    }
    public  void setName(String name)
    {
        this.name=name;
    }
}

