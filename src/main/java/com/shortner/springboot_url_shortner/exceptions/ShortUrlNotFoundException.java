package com.shortner.springboot_url_shortner.exceptions;

public class ShortUrlNotFoundException  extends  RuntimeException{
    public ShortUrlNotFoundException(String message){
        super(message);
    }
}
