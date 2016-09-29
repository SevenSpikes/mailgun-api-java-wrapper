package com.storakle.mailgun;

import com.storakle.mailgun.domain.*;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface MailgunApiClient
{
    @RequestLine("POST /messages")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    SendMessageResponse sendMessage(@Param("from") String from, @Param("to") String to, @Param("subject") String subject,
                                    @Param("text") String text, @Param("html") String html, @Param("o:tracking") String tracking,
                                    @Param("o:tracking-clicks") String trackingClicks, @Param("o:tracking-opens") String trackingOpens,
                                    @Param("o:campaign") String campaign, @Param("o:deliverytime") String deliveryTime,
                                    @Param("o:dkim") String dkim, @Param("o:tag") String tag, @Param("cc") String cc,
                                    @Param("bcc") String bcc);

    @RequestLine("POST /webhooks")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    ResponseMessage createWebhook(@Param("id") String id, @Param("url") String url);

    @RequestLine("POST /webhooks/{id}")
    ResponseMessage deleteWbhook(@Param("id") String id);

    @RequestLine("GET /campaigns")
    @Headers("Content-Type: application/json")
    CampaignList getCampaigns(@Param("limit") int limit);

    @RequestLine("GET /campaigns/{id}")
    Campaign getCampaign(@Param("id") String id);

    @RequestLine("POST /campaigns")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    ResponseMessage createCampaign(@Param("id") String id, @Param("name") String name);

    @RequestLine("DELETE /campaigns/{id}")
    ResponseMessage deleteCampaign(@Param("id") String id);

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
