package com.storakle.mailgun.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class EventItem
{
    @JsonProperty(value = JsonConstants.EVENT)
    private EventType event;

    @JsonProperty(value = JsonConstants.TAGS)
    private ArrayList<String> tags;

    @JsonProperty(value = JsonConstants.TIMESTAMP)
    private long timestamp;

    @JsonProperty(value = JsonConstants.RECIPIENT)
    private String recipient;
}
