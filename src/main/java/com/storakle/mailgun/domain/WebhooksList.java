package com.storakle.mailgun.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WebhooksList
{
    @JsonProperty(value = JsonConstants.WEBHOOKS)
    private Webhooks webhooks;
}
