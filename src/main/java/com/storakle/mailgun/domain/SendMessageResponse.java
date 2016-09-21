package com.storakle.mailgun.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SendMessageResponse
{
    @JsonProperty(value = JsonConstants.MESSAGE)
    private String message;

    @JsonProperty(value = JsonConstants.ID)
    private String id;
}
