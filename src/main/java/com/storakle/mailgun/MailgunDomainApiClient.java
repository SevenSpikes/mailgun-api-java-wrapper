package com.storakle.mailgun;

import com.storakle.mailgun.domain.*;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface MailgunDomainApiClient
{
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
}
