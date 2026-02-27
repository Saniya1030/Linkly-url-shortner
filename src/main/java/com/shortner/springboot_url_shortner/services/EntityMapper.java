package com.shortner.springboot_url_shortner.services;

import com.shortner.springboot_url_shortner.domain.entities.ShortUrl;
import com.shortner.springboot_url_shortner.domain.entities.User;
import com.shortner.springboot_url_shortner.modals.ShortUrlDto;
import com.shortner.springboot_url_shortner.modals.UserDto;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
public class EntityMapper {

    public ShortUrlDto toShortUrlDto(ShortUrl shortUrl) {

        UserDto userDto = null;
        if (shortUrl.getCreatedBy() != null) {
            userDto = toUserDto(shortUrl.getCreatedBy());
        }

        return new ShortUrlDto(
                shortUrl.getId(),
                shortUrl.getShortKey(),
                shortUrl.getOriginalUrl(),
                shortUrl.getIsPrivate(),

                // LocalDateTime -> Instant
                shortUrl.getExpiresAt() == null ? null :
                        shortUrl.getExpiresAt()
                                .atZone(ZoneId.systemDefault())
                                .toInstant(),

                userDto,
                shortUrl.getClickCount(),

                // LocalDateTime -> Instant
                shortUrl.getCreatedAt()
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
        );
    }

    public UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getName());
    }
}
