package com.storakle.mailgun.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseMessage
{
    @JsonProperty(value = JsonConstants.MESSAGE)
    private String message;
}
