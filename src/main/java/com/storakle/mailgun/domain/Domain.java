package com.storakle.mailgun.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Domain
{
    @JsonProperty(value = JsonConstants.DOMAIN)
    private DomainContent domainContent;

    @JsonProperty(value = JsonConstants.RECEIVING_DNS_RECORDS)
    private List<DnsRecord> receivingDnsRecords;

    @JsonProperty(value = JsonConstants.SENDING_DNS_RECORDS)
    private List<DnsRecord> sendingDnsRecords;

    @JsonProperty(value = JsonConstants.MESSAGE)
    private String message;
}
