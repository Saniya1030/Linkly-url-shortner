package com.shortner.springboot_url_shortner.services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWarDeployment;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class UrlExistenceValidator {
    private static final Logger log= LoggerFactory.getLogger(UrlExistenceValidator.class);
    public static boolean isUrlExists(String urlString){
        try{
            log.debug("Checking if url exixts:{}",urlString);
            URL url =new URI(urlString).toURL();
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("Head");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode=connection.getResponseCode();
            return (responseCode>=200 && responseCode<400);
        } catch (Exception e){
            log.error("error while checking URL:{}",urlString,e);
            return  false;
        }
    }
}
