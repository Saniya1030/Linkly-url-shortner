package com.shortner.springboot_url_shortner.services;

import com.shortner.springboot_url_shortner.ApplicationProperties;
import com.shortner.springboot_url_shortner.domain.entities.ShortUrl;
import com.shortner.springboot_url_shortner.modals.CreateShortUrlCmd;
import com.shortner.springboot_url_shortner.modals.ShortUrlDto;
import com.shortner.springboot_url_shortner.repositories.ShortUrlRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;
    private final EntityMapper entityMapper;
    private final ApplicationProperties properties;

    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int SHORT_KEY_LENGTH = 6;
    private static final SecureRandom RANDOM = new SecureRandom();

    public ShortUrlService(ShortUrlRepository shortUrlRepository,
                           EntityMapper entityMapper,
                           ApplicationProperties properties) {
        this.shortUrlRepository = shortUrlRepository;
        this.entityMapper = entityMapper;
        this.properties = properties;
    }

    public List<ShortUrlDto> findAllPublicShortUrls() {
        return shortUrlRepository
                .findByIsPrivateFalseOrderByCreatedAtDesc()
                .stream()
                .map(entityMapper::toShortUrlDto)
                .toList();
    }

    public ShortUrlDto createShortUrl(CreateShortUrlCmd cmd) {

        String originalUrl = normalizeUrl(cmd.originalUrl());

        var shortKey = generateUniqueShortKey();
        var shortUrl = new ShortUrl();
        shortUrl.setOriginalUrl(originalUrl);
        shortUrl.setShortKey(shortKey);
        shortUrl.setCreatedBy(null); // later: authenticated user
        shortUrl.setPrivate(false);
        shortUrl.setCreatedAt(LocalDateTime.now());
        shortUrl.setExpiresAt(
                LocalDateTime.now().plusDays(properties.defaultExpiryInDays())
        );

        shortUrlRepository.save(shortUrl);
        return entityMapper.toShortUrlDto(shortUrl);
    }

    private String generateUniqueShortKey() {
        String shortKey;
        do {
            shortKey = generateRandomShortKey();
        } while (shortUrlRepository.existsByShortKey(shortKey));
        return shortKey;
    }

    private static String generateRandomShortKey() {
        StringBuilder sb = new StringBuilder(SHORT_KEY_LENGTH);
        for (int i = 0; i < SHORT_KEY_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(
                    RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    private String normalizeUrl(String url) {
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("Original URL cannot be empty");
        }
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            return "https://" + url;
        }
        return url;
    }

    public Optional<ShortUrlDto> accessShortUrl(String shortKey) {

        Optional<ShortUrl> shortUrlOptional =
                shortUrlRepository.findByShortKey(shortKey);

        if (shortUrlOptional.isEmpty()) {
            return Optional.empty();
        }

        ShortUrl shortUrl = shortUrlOptional.get();

        if (shortUrl.getExpiresAt() != null &&
                shortUrl.getExpiresAt().isBefore(LocalDateTime.now())) {
            return Optional.empty();
        }

        return Optional.of(entityMapper.toShortUrlDto(shortUrl));
    }
}

    // private String generateShortKey() {
        // simple + safe for now
       // return UUID.randomUUID()
         //       .toString()
           //     .substring(0, 8); }

//logic- we are using a random utility to
// generate a random 6 digit alpha numeric code.
