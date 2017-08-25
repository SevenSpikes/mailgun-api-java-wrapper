package com.storakle.mailgun.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class EventList
{
    @JsonProperty(value = JsonConstants.ITEMS)
    private List<EventItem> items;

    @JsonProperty(value = JsonConstants.PAGING)
    private EventPaging pages;
}
