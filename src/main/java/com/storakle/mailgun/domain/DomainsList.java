package com.storakle.mailgun.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DomainsList
{
    @JsonProperty(value = JsonConstants.TOTAL_COUNT)
    private Integer totalItemsCount;

    @JsonProperty(value = JsonConstants.ITEMS)
    private List<DomainContent> items;
}
