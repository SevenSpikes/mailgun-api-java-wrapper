package com.storakle.mailgun.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DnsRecord
{
    @JsonProperty(value = JsonConstants.PRIORITY)
    private String priority;

    @JsonProperty(value = JsonConstants.RECORD_TYPE)
    private String recordType;

    @JsonProperty(value = JsonConstants.VALID)
    private String valid;

    @JsonProperty(value = JsonConstants.VALUE)
    private String value;

    @JsonProperty(value = JsonConstants.NAME)
    private String name;
}
