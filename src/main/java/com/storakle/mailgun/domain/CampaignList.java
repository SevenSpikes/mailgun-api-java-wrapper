package com.storakle.mailgun.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CampaignList
{
    @JsonProperty(value = JsonConstants.TOTAL_COUNT)
    private Integer totalItemsCount;

    @JsonProperty(value = JsonConstants.ITEMS)
    private List<Campaign> campaigns;
}
