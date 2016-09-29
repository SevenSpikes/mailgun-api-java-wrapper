package com.storakle.mailgun;

import com.google.common.base.Strings;
import com.storakle.mailgun.builder.MessageBuilder;
import com.storakle.mailgun.domain.*;

import java.time.format.DateTimeFormatter;

public class MailgunApiManager
{
    private MailgunApiClient mailgunApiClient;
    private MailgunDomainApiClient mailgunDomainApiClient;
    private String apiKey;
    private String domainName;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z");

    public MailgunApiManager(String apiKey, String domainName)
    {
        this.apiKey = apiKey;
        this.domainName = domainName;
    }

    public MailgunApiManager(String apiKey)
    {
        this.apiKey = apiKey;
    }

    private MailgunApiClient getMailgunApiClient()
    {
        if(Strings.isNullOrEmpty(apiKey) || Strings.isNullOrEmpty(domainName))
        {
            throw new RuntimeException("The MailgunApiClient cannot be created, because one of the apiKey or domainName " +
                    "parameters is null or empty.");
        }

        if(mailgunApiClient == null)
        {
            mailgunApiClient = MailgunApiFactory.createApiClient(apiKey, domainName);
        }

        return mailgunApiClient;
    }

    private MailgunDomainApiClient getMailgunDomainApiClient()
    {
        if(Strings.isNullOrEmpty(apiKey))
        {
            throw new RuntimeException("The MailgunDomainApiClient cannot be created, because the apiKey " +
                    "parameter is null or empty.");
        }

        if(mailgunDomainApiClient == null)
        {
            mailgunDomainApiClient = MailgunApiFactory.createDomainApiClient(apiKey);
        }

        return mailgunDomainApiClient;
    }

    public SendMessageResponse sendMessage(MessageBuilder messageBuilder)
    {
        String formattedDate = null;

        if(messageBuilder.getDeliveryTime() != null)
        {
            formattedDate = messageBuilder.getDeliveryTime().format(FORMATTER);
        }

        return getMailgunApiClient().sendMessage(messageBuilder.getFrom(), messageBuilder.getTo(), messageBuilder.getSubject(),
                messageBuilder.getText(), messageBuilder.getHtml(), messageBuilder.getTracking(),
                messageBuilder.getTrackingClicks(), messageBuilder.getTrackingOpens(),
                messageBuilder.getCampaign(), formattedDate, messageBuilder.getDkim(), messageBuilder.getTag(), messageBuilder.getCc(),
                messageBuilder.getBcc());
    }

    public Domain createDomain(DomainContent domainContent)
    {
        Domain domain = getMailgunDomainApiClient().createDomain(domainContent.getName(), domainContent.getSmtpPassword(), domainContent.getWildcard());

        if(domain != null && !Strings.isNullOrEmpty(domain.getDomainContent().getName()))
        {
            domainName = domainContent.getName();
        }

        return domain;
    }

    public Domain getDomain(String domainName)
    {
        return getMailgunDomainApiClient().getDomain(domainName);
    }

    public ResponseMessage deleteDomain(String domainName)
    {
        return getMailgunDomainApiClient().deleteDomain(domainName);
    }

    public DomainsList getDomains()
    {
        return getMailgunDomainApiClient().getDomains();
    }

    public WebhooksList getWebhooks()
    {
        return getMailgunDomainApiClient().getWebhooks(domainName);
    }

    public ResponseMessage createWebhook(WebhookType webhookType, String url)
    {
        return getMailgunApiClient().createWebhook(webhookType.toString().toLowerCase(), url);
    }

    public ResponseMessage deleteWebhook(String id)
    {
        return getMailgunApiClient().deleteWbhook(id);
    }

    public CampaignList getCampaigns(Integer limit)
    {
        int campaignsLimit = ApiDefaultParameters.limit;
        if(limit != null && limit > 0)
        {
            campaignsLimit = limit;
        }

        return getMailgunApiClient().getCampaigns(campaignsLimit);
    }

    public Campaign getCampaign(String id)
    {
        return getMailgunApiClient().getCampaign(id);
    }

    public ResponseMessage deleteCampaign(String id)
    {
        return getMailgunApiClient().deleteCampaign(id);
    }

    public ResponseMessage createCampaign(Campaign campaign)
    {
        return getMailgunApiClient().createCampaign(campaign.getId(), campaign.getName());
    }
}
