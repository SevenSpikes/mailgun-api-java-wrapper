package com.storakle.mailgun;

import feign.Feign;
import feign.RequestInterceptor;
import feign.auth.BasicAuthRequestInterceptor;
import feign.form.FormEncoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

import java.util.ArrayList;

public class MailgunApiFactory
{
    private final String mailgunApiUrl = "https://api.mailgun.net/v3";

    public MailgunApiClient createApiClient(String apiKey)
    {
        // Prepare the request interceptors
        ArrayList<RequestInterceptor> requestInterceptors = new ArrayList<>();
        requestInterceptors.add(new BasicAuthRequestInterceptor("api", apiKey));

        return Feign.builder()
                .decoder(new JacksonDecoder())
                .encoder(new FormEncoder(new JacksonEncoder()))
                .requestInterceptors(requestInterceptors)
//                .logger(new Logger.JavaLogger().appendToFile("http.log"))
//                .logLevel(Logger.Level.FULL)
                .target(MailgunApiClient.class, mailgunApiUrl);
    }
}
