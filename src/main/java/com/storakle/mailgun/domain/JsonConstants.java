package com.storakle.mailgun.domain;

public final class JsonConstants
{
    // DOMAIN
    public static final String DOMAIN = "domain";
    public static final String SMTP_LOGIN = "smtp_login";
    public static final String SMTP_PASSWORD = "smtp_password";
    public static final String NAME = "name";
    public static final String WILDCARD = "wildcard";
    public static final String SPAM_ACTION = "spam_action";
    public static final String STATE = "state";
    public static final String CREATED_AT = "created_at";
    public static final String RECEIVING_DNS_RECORDS = "receiving_dns_records";
    public static final String SENDING_DNS_RECORDS = "sending_dns_records";
    public static final String TOTAL_COUNT = "total_count";
    public static final String ITEMS = "items";

    // DNS RECORD
    public static final String PRIORITY = "priority";
    public static final String RECORD_TYPE = "record_type";
    public static final String VALID = "valid";
    public static final String VALUE = "value";

    public static final String MESSAGE = "message";

    // MESSAGE
    public static final String FROM = "from";
    public static final String TO = "to";
    public static final String CC = "cc";
    public static final String BCC = "bcc";
    public static final String SUBJECT = "subject";
    public static final String TEXT = "text";
    public static final String HTML = "html";
    public static final String TEST_MODE = "o:testmode";
    public static final String DKMI = "o:dkim";
    public static final String TRACKING = "o:tracking";
    public static final String TRACKING_CLICKS = "o:tracking-clicks";
    public static final String TRACKING_OPENS = "o:tracking-opens";
    public static final String TAG = "o:tag";
    public static final String CAMPAIGN = "o:campaign";
    public static final String DELIVERY_TIME = "o:deliverytime";

    // CAMPAIGN
    public static final String DELIVERED_COUNT = "delivered_count";
    public static final String CLICKED_COUNT = "clicked_count";
    public static final String OPENED_COUNT = "opened_count";
    public static final String SUMITTED_COUNT = "submitted_count";
    public static final String UNSUBSCRIBED_COUNT = "unsubscribed_count";
    public static final String BOUNCED_COUNT = "bounced_count";
    public static final String COMPLAINED_COUNT = "complained_count";
    public static final String DROPPED_COUNT = "dropped_count";
    public static final String ID = "id";

    // WEBHOOK
    public static final String URL = "url";
    public static final String BOUNCE = "bounce";
    public static final String DELIVER = "deliver";
    public static final String DROP = "drop";
    public static final String SPAM = "spam";
    public static final String UNSUBSCRIBE = "unsubscribe";
    public static final String CLICK = "click";
    public static final String OPEN = "open";
    public static final String WEBHOOKS = "webhooks";
}
