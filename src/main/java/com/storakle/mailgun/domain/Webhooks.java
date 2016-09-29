package com.storakle.mailgun.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Webhooks
{
    @JsonProperty(value = JsonConstants.BOUNCE)
    private Webhook bounce;

    @JsonProperty(value = JsonConstants.DELIVER)
    private Webhook deliver;

    @JsonProperty(value = JsonConstants.DROP)
    private Webhook drop;

    @JsonProperty(value = JsonConstants.SPAM)
    private Webhook spam;

    @JsonProperty(value = JsonConstants.UNSUBSCRIBE)
    private Webhook unsubscribe;

    @JsonProperty(value = JsonConstants.CLICK)
    private Webhook click;

    @JsonProperty(value = JsonConstants.OPEN)
    private Webhook open;
}
