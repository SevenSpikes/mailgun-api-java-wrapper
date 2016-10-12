package com.storakle.mailgun;

import com.storakle.mailgun.domain.*;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface MailgunApiClient
{
    @RequestLine("POST /{domain}/messages")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    SendMessageResponse sendMessage(@Param("domain") String domain, @Param("from") String from, @Param("to") String to, @Param("subject") String subject,
                                    @Param("text") String text, @Param("html") String html, @Param("o:tracking") String tracking,
                                    @Param("o:tracking-clicks") String trackingClicks, @Param("o:tracking-opens") String trackingOpens,
                                    @Param("o:campaign") String campaign, @Param("o:deliverytime") String deliveryTime,
                                    @Param("o:dkim") String dkim, @Param("o:tag") String tag, @Param("cc") String cc,
                                    @Param("bcc") String bcc, @Param("recipient-variables") String recipientVariables);

    @RequestLine("POST /{domain}/webhooks")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    ResponseMessage createWebhook(@Param("domain") String domain, @Param("id") String id, @Param("url") String url);

    @RequestLine("POST /{domain}/webhooks/{id}")
    ResponseMessage deleteWbhook(@Param("domain") String domain, @Param("id") String id);

    @RequestLine("GET /{domain}/campaigns")
    @Headers("Content-Type: application/json")
    CampaignList getCampaigns(@Param("domain") String domain, @Param("limit") int limit);

    @RequestLine("GET /{domain}/campaigns/{id}")
    Campaign getCampaign(@Param("domain") String domain, @Param("id") String id);

    @RequestLine("POST /{domain}/campaigns")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    ResponseMessage createCampaign(@Param("domain") String domain, @Param("id") String id, @Param("name") String name);

    @RequestLine("DELETE /{domain}/campaigns/{id}")
    ResponseMessage deleteCampaign(@Param("domain") String domain, @Param("id") String id);


    @RequestLine("GET /domains/{domain}")
    Domain getDomain(@Param("domain") String domain);

    @RequestLine("POST /domains")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Domain createDomain(@Param("name") String name, @Param("smtp_password") String smtp_password,
                        @Param("wildcard") String wildcard);

    @RequestLine("DELETE /domains/{domain}")
    ResponseMessage deleteDomain(@Param("domain") String domain);

    @RequestLine("GET /domains")
    DomainsList getDomains();

    // This should be here, because the webhooks url structure is different than the other requests
    // and the domain has to be passed separately.
    @RequestLine("GET domains/{domain}/webhooks")
    @Headers("Content-Type: application/json")
    WebhooksList getWebhooks(@Param("domain") String domain);

//    @RequestLine("GET /campaigns/{id}/stats")
//    ResponseMessage getCampaignStats(@Param("id") String id, @Param("groupby") String groupBy);
//
//    @RequestLine("GET /campaigns/{id}/clicks")
//    ResponseMessage getCampaignClicks(@Param("id") String id, @Param("groupby") String groupBy, @Param("limit") int limit,
//                                      @Param("page") String page);
//
//    @RequestLine("GET /campaigns/{id}/opens")
//    ResponseMessage getCampaignOpens(@Param("id") String id, @Param("groupby") String groupBy, @Param("limit") int limit,
//                                      @Param("page") String page);
//
//    @RequestLine("GET /campaigns/{id}/unsubscribes")
//    ResponseMessage getCampaignUnsubscribes(@Param("id") String id, @Param("groupby") String groupBy, @Param("limit") int limit,
//                                      @Param("page") String page);
//
//    @RequestLine("GET /campaigns/{id}/complaints")
//    ResponseMessage getCampaignComplaints(@Param("id") String id, @Param("groupby") String groupBy, @Param("limit") int limit,
//                                            @Param("page") int page);
}
