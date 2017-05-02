package com.storakle.mailgun.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.storakle.mailgun.jackson.FlexDateDeserializer;
import lombok.Data;

import java.util.Date;

@Data
public class DomainContent
{
    @JsonProperty(value = JsonConstants.NAME)
    private String name;

    @JsonProperty(value = JsonConstants.SMTP_LOGIN)
    private String smtpLogin;

    @JsonProperty(value = JsonConstants.SMTP_PASSWORD)
    private String smtpPassword;

    @JsonProperty(value = JsonConstants.WILDCARD)
    private Boolean wildcard;

    @JsonProperty(value = JsonConstants.SPAM_ACTION)
    private String spamAction;

    @JsonProperty(value = JsonConstants.STATE)
    private String state;

    @JsonProperty(value = JsonConstants.CREATED_AT)
    @JsonDeserialize(using = FlexDateDeserializer.class)
    private Date createdAt;
}
