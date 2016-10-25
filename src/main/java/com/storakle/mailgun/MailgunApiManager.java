package com.storakle.mailgun;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.storakle.mailgun.domain.Message;
import com.storakle.mailgun.domain.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MailgunApiManager
{
    private MailgunApiClient mailgunApiClient;
    private String apiKey;
    private String domainName;
    private MailgunApiFactory _mailgunApiFactory = new MailgunApiFactory();

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
        if(Strings.isNullOrEmpty(apiKey))
        {
            throw new RuntimeException("The MailgunApiClient cannot be created, because one of the apiKey or domainName " +
                    "parameters is null or empty.");
        }

        if(mailgunApiClient == null)
        {
            mailgunApiClient = _mailgunApiFactory.createApiClient(apiKey);
        }

        return mailgunApiClient;
    }

    public SendMessageResponse sendMessage(Message message)
    {
        String formattedDate = null;

        if(message.getDeliveryTime() != null)
        {
            formattedDate = message.getDeliveryTime().format(FORMATTER);
        }

        List<String> recipients = message.getToList();

        List<List<String>> batchLists = Lists.partition(recipients, 1000);

        List<SendMessageResponse> responses = new ArrayList<>();

        for (List<String> recipientsBatch : batchLists)
        {
            List<String> rv = new ArrayList<>();
            int id = 0;
            for (String recipientEmail : recipientsBatch)
            {
                id++;
                rv.add("\"" + recipientEmail + "\": {\"id\": " + id + "}");
            }

            String joined = String.join(", ", rv);

            // recipient-variables='{"bob@example.com": {"first":"Bob", "id":1}, "alice@example.com": {"first":"Alice", "id": 2}}'
            String recipientVariables = "{"+joined+"}";

            String recipientsListString = String.join(", ", recipientsBatch);

            SendMessageResponse response = getMailgunApiClient().sendMessage(domainName, message.getFrom(), recipientsListString, message.getSubject(),
                    message.getText(), message.getHtml(), message.getTracking(),
                    message.getTrackingClicks(), message.getTrackingOpens(),
                    message.getCampaign(), formattedDate, message.getDkim(), message.getTag(), message.getCc(),
                    message.getBcc(), recipientVariables);

            responses.add(response);
        }
        return null;

//        // recipient-variables='{"bob@example.com": {"first":"Bob", "id":1}, "alice@example.com": {"first":"Alice", "id": 2}}'
//        String recipientVariables = "{}";
//
//        return getMailgunApiClient().sendMessage(domainName, message.getFrom(), message.getTo(), message.getSubject(),
//                message.getText(), message.getHtml(), message.getTracking(),
//                message.getTrackingClicks(), message.getTrackingOpens(),
//                message.getCampaign(), formattedDate, message.getDkim(), message.getTag(), message.getCc(),
//                message.getBcc(), recipientVariables);
    }

    public Domain createDomain(DomainContent domainContent)
    {
        Domain domain = getMailgunApiClient().createDomain(domainContent.getName(), domainContent.getSmtpPassword(), domainContent.getWildcard());

        if(domain != null && !Strings.isNullOrEmpty(domain.getDomainContent().getName()))
        {
            domainName = domainContent.getName();
        }

        return domain;
    }

    public Domain getDomain(String domainName)
    {
        return getMailgunApiClient().getDomain(domainName);
    }

    public ResponseMessage deleteDomain(String domainName)
    {
        return getMailgunApiClient().deleteDomain(domainName);
    }

    public DomainsList getDomains()
    {
        return getMailgunApiClient().getDomains();
    }

    public WebhooksList getWebhooks()
    {
        return getMailgunApiClient().getWebhooks(domainName);
    }

    public ResponseMessage createWebhook(WebhookType webhookType, String url)
    {
        return getMailgunApiClient().createWebhook(domainName, webhookType.toString().toLowerCase(), url);
    }

    public ResponseMessage deleteWebhook(String id)
    {
        return getMailgunApiClient().deleteWbhook(domainName, id);
    }

    public CampaignList getCampaigns(Integer limit)
    {
        int campaignsLimit = ApiDefaultParameters.limit;
        if(limit != null && limit > 0)
        {
            campaignsLimit = limit;
        }

        return getMailgunApiClient().getCampaigns(domainName, campaignsLimit);
    }

    public Campaign getCampaign(String id)
    {
        return getMailgunApiClient().getCampaign(domainName, id);
    }

    public ResponseMessage deleteCampaign(String id)
    {
        return getMailgunApiClient().deleteCampaign(domainName, id);
    }

    public ResponseMessage createCampaign(Campaign campaign)
    {
        return getMailgunApiClient().createCampaign(domainName, campaign.getId(), campaign.getName());
    }

    public ResponseMessage unsubscribeRecipient(String emailAddress, String tag)
    {
        return getMailgunApiClient().unsubscribeRecipient(domainName, emailAddress, tag);
    }
}
