package com.storakle.mailgun;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Strings;
import com.storakle.mailgun.domain.*;

import java.time.format.DateTimeFormatter;
import java.util.*;

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

    private String getMailgunApiUrl()
    {
        return _mailgunApiFactory.getMailgunApiUrl();
    }

    public SendMessageResponse sendMessage(Message message, String recipientEmail)
    {
        List<Recipient> recipients = new ArrayList<>();

        recipients.add(new Recipient().setRecipient(recipientEmail));

        List<SendMessageResponse> responses = sendMessage(message, recipients);

        return responses.get(0);
    }

    public List<SendMessageResponse> sendMessage(Message message, List<Recipient> recipients)
    {
        List<SendMessageResponse> responses = new ArrayList<>();

        // Create a batch of a 1000 email addresses. This is done because the Mailgin API has a limit of a 1000 emails
        // per batch.
        List<Map<String, Recipient>> batchLists = partitionUnique(recipients, 950);// Lists.partition(recipients, 950);

        ObjectMapper objectMapper = new ObjectMapper();

        for (Map<String, Recipient> recipientsBatch : batchLists)
        {
            // We need the recipient emails, so that we know to which addresses the email should be send.
            List<String> recipientEmails = new ArrayList<>();

            ObjectNode recipientsObject = objectMapper.createObjectNode();

            int i = 0;
            for (Recipient recipient : recipientsBatch.values())
            {
                // Prepare the recipient variables object.
                ObjectNode recipientVariablesObject = objectMapper.createObjectNode();
                recipient.getVariables().forEach((key, value) -> recipientVariablesObject.put(key, value));

                if(!recipientVariablesObject.has("id"))
                {
                    recipientVariablesObject.put("id", i + 1);
                }

                recipientEmails.add(recipient.getEmail());

                recipientsObject.set(recipient.getEmail(), recipientVariablesObject);
            }

            // recipient-variables='{"bob@example.com": {"first":"Bob", "id":1}, "alice@example.com": {"first":"Alice", "id": 2}}'
            String recipientVariables = "";
            try
            {
                recipientVariables = objectMapper.writeValueAsString(recipientsObject);
            }
            catch (JsonProcessingException e)
            {
                e.printStackTrace();
            }

            String recipientsListString = String.join(", ", recipientEmails);

            SendMessageResponse response;

            String formattedDate = null;

            if(message.getDeliveryTime() != null)
            {
                formattedDate = message.getDeliveryTime().format(FORMATTER);
            }

            if (message.hasAttachment())
            {
                response = getMailgunApiClient().sendMessageWithAttachment(domainName, message.getFrom(), message.getReplyTo(),
                        recipientsListString, message.getSubject(),
                        message.getText(), message.getHtml(), message.getTracking(),
                        message.getTrackingClicks(), message.getTrackingOpens(),
                        message.getCampaign(), formattedDate, message.getDkim(), message.getTag(), message.getCc(),
                        message.getBcc(), recipientVariables, message.getAttachment());
            }
            else
            {
                response = getMailgunApiClient().sendMessage(domainName, message.getFrom(), message.getReplyTo(),
                        recipientsListString, message.getSubject(),
                        message.getText(), message.getHtml(), message.getTracking(),
                        message.getTrackingClicks(), message.getTrackingOpens(),
                        message.getCampaign(), formattedDate, message.getDkim(), message.getTag(), message.getCc(),
                        message.getBcc(), recipientVariables);
            }

            responses.add(response);
        }

        return responses;
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
        return getMailgunApiClient().deleteWebhook(domainName, id);
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

    public ResponseMessage verifyDomain(String domainName)
    {
        return getMailgunApiClient().verifyDomain(domainName);
    }

    public List<Map<String, Recipient>> partitionUnique(List<Recipient> recipientsList, Integer partitionSize)
    {
        List<Map<String, Recipient>> uniqueRecipientsPartitions = new ArrayList<>();

        List<Recipient> remainingRecipients = new ArrayList<>(recipientsList);

        while (remainingRecipients.size() > 0)
        {
            Map<String, Recipient> partition = new TreeMap<>();

            ListIterator<Recipient> iterator = remainingRecipients.listIterator();

            while (iterator.hasNext())
            {
                if (partition.size() < partitionSize)
                {
                    Recipient recipient = iterator.next();
                    if (!partition.containsKey(recipient.getEmail()))
                    {
                        partition.put(recipient.getEmail(), recipient);
                        iterator.remove();
                    }
                }
                else
                {
                    break;
                }
            }

            uniqueRecipientsPartitions.add(partition);
        }

        return uniqueRecipientsPartitions;
    }

    public EventList getEvents(long begin, long end, String ascending, Integer limit, String event)
    {
        return getMailgunApiClient().getEvents(domainName, begin, end, ascending, limit, event);
    }

    public EventList getEventsByPageToken(String urlWithToken)
    {
        // The urlWithToken that is returned by the mailgiun API contains the whole url.
        // It should be split in order to get the token and make a request with it.
        String token = null;
        String mailgunApiUrl = getMailgunApiUrl();

        String splitKey = mailgunApiUrl + "/" + domainName + "/events/";

        String[] splitUrlAndToken = urlWithToken.split(splitKey);

        if (splitUrlAndToken.length > 1)
        {
            token = splitUrlAndToken[1];
        }

        if (!Strings.isNullOrEmpty(token))
        {
            return getMailgunApiClient().getEventsByPageToken(domainName, token);
        }

        return null;
    }
}
