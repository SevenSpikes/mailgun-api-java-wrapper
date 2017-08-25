package com.storakle.mailgun.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EventPaging
{
    @JsonProperty(value = JsonConstants.NEXT_PAGE)
    private String next;

    @JsonProperty(value = JsonConstants.PREVIOUS_PAGE)
    private String previous;

    @JsonProperty(value = JsonConstants.LAST_PAGE)
    private String last;

    @JsonProperty(value = JsonConstants.FIRST_PAGE)
    private String first;
}
