package com.storakle.mailgun.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.storakle.mailgun.jackson.FlexDateDeserializer;
import lombok.Data;

import java.util.Date;

@Data
public class Campaign
{
    @JsonProperty(value = JsonConstants.NAME)
    private String name;

    @JsonProperty(value = JsonConstants.ID)
    private String id;

    @JsonProperty(value = JsonConstants.CREATED_AT)
    @JsonDeserialize(using = FlexDateDeserializer.class)
    private Date createdAt;

    @JsonProperty(value = JsonConstants.DELIVERED_COUNT)
    private int deliveredCount;

    @JsonProperty(value = JsonConstants.CLICKED_COUNT)
    private int clickedCount;

    @JsonProperty(value = JsonConstants.OPENED_COUNT)
    private int openedCount;

    @JsonProperty(value = JsonConstants.SUMITTED_COUNT)
    private int submittedCount;

    @JsonProperty(value = JsonConstants.UNSUBSCRIBED_COUNT)
    private int unsubscribedCount;

    @JsonProperty(value = JsonConstants.BOUNCED_COUNT)
    private int bouncedCount;

    @JsonProperty(value = JsonConstants.COMPLAINED_COUNT)
    private int complainedCount;

    @JsonProperty(value = JsonConstants.DROPPED_COUNT)
    private int droppedCount;
}
