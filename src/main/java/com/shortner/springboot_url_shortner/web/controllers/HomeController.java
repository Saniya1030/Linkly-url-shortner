package com.shortner.springboot_url_shortner.web.controllers;


import com.shortner.springboot_url_shortner.ApplicationProperties;
import com.shortner.springboot_url_shortner.dtos.CreateShortUrlForm;
import com.shortner.springboot_url_shortner.modals.CreateShortUrlCmd;
import com.shortner.springboot_url_shortner.modals.ShortUrlDto;
import com.shortner.springboot_url_shortner.services.ShortUrlService;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class HomeController {


    private final ShortUrlService shortUrlService;
    private final ApplicationProperties properties;


    public HomeController(
            ShortUrlService shortUrlService,
            ApplicationProperties properties
    ){

        this.shortUrlService = shortUrlService;
        this.properties = properties;

    }



    @PostMapping("/shorten")
    public ResponseEntity<ShortUrlDto> createShortUrl(
            @RequestBody CreateShortUrlForm form
    ){


        CreateShortUrlCmd cmd =
                new CreateShortUrlCmd(form.originalUrl());


        ShortUrlDto shortUrlDto =
                shortUrlService.createShortUrl(cmd);



        String shortUrl =
                properties.baseUrl()
                        + "/s/"
                        + shortUrlDto.shortKey();


        return ResponseEntity.ok(shortUrlDto);

    }


}