package com.shortner.springboot_url_shortner.repositories;

import java.util.List;
import java.util.Optional;

import com.shortner.springboot_url_shortner.domain.entities.ShortUrl;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    List<ShortUrl> findByIsPrivateFalseOrderByCreatedAtDesc();

    boolean existsByShortKey(String shortKey);//if exists returs true otherwise false
    Optional<ShortUrl>findByShortKey (String shortKey);
}
