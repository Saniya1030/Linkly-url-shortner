package com.shortner.springboot_url_shortner.web.controllers;

import com.shortner.springboot_url_shortner.ApplicationProperties;
import com.shortner.springboot_url_shortner.dtos.CreateShortUrlForm;
import com.shortner.springboot_url_shortner.exceptions.ShortUrlNotFoundException;
import com.shortner.springboot_url_shortner.modals.CreateShortUrlCmd;
import com.shortner.springboot_url_shortner.modals.ShortUrlDto;
import com.shortner.springboot_url_shortner.services.ShortUrlService;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
public class HomeController {

    private final ShortUrlService shortUrlService;
    private final ApplicationProperties properties;

    public HomeController(ShortUrlService shortUrlService,
                          ApplicationProperties properties) {
        this.shortUrlService = shortUrlService;
        this.properties = properties;
    }

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute(
                "publicShortUrls",
                shortUrlService.findAllPublicShortUrls()
        );
        model.addAttribute(
                "createShortUrlForm",
                new CreateShortUrlForm("")
        );
        model.addAttribute("baseUrl", properties.baseUrl());

        return "index";
    }

    @PostMapping("/short-urls")
    public String createShortUrl(
            @ModelAttribute("createShortUrlForm")
            @Valid CreateShortUrlForm form,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute(
                    "publicShortUrls",
                    shortUrlService.findAllPublicShortUrls()
            );
            model.addAttribute("baseUrl", properties.baseUrl());
            return "index";
        }

        try {
            CreateShortUrlCmd cmd =
                    new CreateShortUrlCmd(form.originalUrl());
            ShortUrlDto shortUrlDto =
                    shortUrlService.createShortUrl(cmd);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Short URL created successfully: "
                            + properties.baseUrl()
                            + "/s/"
                            + shortUrlDto.shortKey()
            );
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    e.getMessage()
            );
        }

        return "redirect:/";
    }

    @GetMapping("/s/{shortKey}")
    public String redirectToOriginalUrl(
            @PathVariable String shortKey) {

        return shortUrlService.accessShortUrl(shortKey)
                .map(dto -> "redirect:" + dto.originalUrl())
                .orElseThrow(() ->
                        new ShortUrlNotFoundException(
                                "Invalid short key: " + shortKey
                        )
                );
    }
}