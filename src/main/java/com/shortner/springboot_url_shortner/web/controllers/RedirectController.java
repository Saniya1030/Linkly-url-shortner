package com.shortner.springboot_url_shortner.web.controllers;


import com.shortner.springboot_url_shortner.exceptions.ShortUrlNotFoundException;
import com.shortner.springboot_url_shortner.services.ShortUrlService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@Controller
public class RedirectController {


    private final ShortUrlService shortUrlService;


    public RedirectController(
            ShortUrlService shortUrlService
    ){
        this.shortUrlService = shortUrlService;
    }



    @GetMapping("/s/{shortKey}")
    public String redirectToOriginalUrl(
            @PathVariable String shortKey
    ){


        return shortUrlService.accessShortUrl(shortKey)

                .map(dto ->
                        "redirect:" + dto.originalUrl()
                )

                .orElseThrow(() ->
                        new ShortUrlNotFoundException(
                                "Invalid short key: " + shortKey
                        )
                );

    }

}