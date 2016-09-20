package com.storakle.mailgun;

import com.storakle.mailgun.domain.*;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface MailgunDomainApiClient
{
    int MAXIMUM_RETURNED_RESULTS = 250;

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
}
