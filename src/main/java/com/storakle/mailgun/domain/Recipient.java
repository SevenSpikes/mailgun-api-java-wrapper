package com.storakle.mailgun.domain;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Recipient
{
    private String email;
    private Map<String, String> variables = new HashMap<>();

    public Recipient setRecipient(String recipient)
    {
        email = recipient;
        return this;
    }

    public Recipient addVariable(String key, String value)
    {
        if(!variables.containsKey(key))
        {
            variables.put(key, value);
        }

        return this;
    }
}
