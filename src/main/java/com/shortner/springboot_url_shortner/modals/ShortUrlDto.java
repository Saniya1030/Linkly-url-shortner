package com.shortner.springboot_url_shortner.modals;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

public record ShortUrlDto (Long id, String shortKey, String originalUrl, Boolean isPrivate, Instant expiresAt,
                           UserDto createdBy,Long clickCount,
                           Instant createdAt) implements Serializable{

}
