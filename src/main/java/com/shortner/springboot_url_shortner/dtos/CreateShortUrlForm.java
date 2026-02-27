package com.shortner.springboot_url_shortner.dtos;
import  jakarta.validation.constraints.NotBlank;
public record CreateShortUrlForm(
        @NotBlank (message="original url is required") String originalUrl)
{


}
