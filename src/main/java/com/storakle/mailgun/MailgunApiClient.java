package com.storakle.mailgun;

import com.storakle.mailgun.domain.ResponseMessage;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface MailgunApiClient
{
    @RequestLine("POST /messages")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    ResponseMessage sendMessage(@Param("from") String from, @Param("to") String to, @Param("subject") String subject,
                                @Param("text") String text, @Param("html") String html, @Param("o:tracking") String tracking,
                                @Param("o:tracking-clicks") String trackingClicks, @Param("o:tracking-opens") String trackingOpens,
                                @Param("o:campaign") String campaign, @Param("o:deliverytime") String deliveryTime,
                                @Param("o:dkim") String dkim, @Param("o:tag") String tag, @Param("cc") String cc,
                                @Param("bcc") String bcc);
}
