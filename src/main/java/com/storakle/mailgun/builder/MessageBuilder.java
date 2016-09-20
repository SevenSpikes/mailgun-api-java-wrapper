package com.storakle.mailgun.builder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.storakle.mailgun.domain.JsonConstants;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.assertj.core.util.Strings;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageBuilder
{
    public enum TestMode
    {
        YES
    }

    public enum Tracking
    {
        YES,
        NO
    }

    public enum TrackingOpens
    {
        YES,
        NO
    }

    public enum TrackingClicks
    {
        YES,
        NO,
        HTMONLY
    }

    public enum Dkim
    {
        YES,
        NO
    }

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private List<String> toList = new ArrayList<>();
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private List<String> ccList = new ArrayList<>();
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private List<String> bccList = new ArrayList<>();
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private List<String> tagList = new ArrayList<>();

    @JsonProperty(value = JsonConstants.FROM)
    private String from;

    @JsonProperty(value = JsonConstants.TO)
    @Getter(AccessLevel.NONE)
    private String to;

    @JsonProperty(value = JsonConstants.CC)
    @Getter(AccessLevel.NONE)
    private String cc;

    @JsonProperty(value = JsonConstants.BCC)
    @Getter(AccessLevel.NONE)
    private String bcc;

    @Getter(AccessLevel.NONE)
    private String tag;

    @JsonProperty(value = JsonConstants.SUBJECT)
    private String subject;

    @JsonProperty(value = JsonConstants.TEXT)
    private String text;

    @JsonProperty(value = JsonConstants.HTML)
    private String html;

    @JsonProperty(value = JsonConstants.CAMPAIGN)
    private String campaign;

    @JsonProperty(value = JsonConstants.TEST_MODE)
    private TestMode testMode;

    @JsonProperty(value = JsonConstants.DKMI)
    private Dkim dkim;

    @JsonProperty(value = JsonConstants.TRACKING)
    private Tracking tracking;

    @JsonProperty(value = JsonConstants.TRACKING_CLICKS)
    private TrackingClicks trackingClicks;

    @JsonProperty(value = JsonConstants.TRACKING_OPENS)
    private TrackingOpens trackingOpens;

    @JsonProperty(value = JsonConstants.DELIVERY_TIME)
    private ZonedDateTime deliveryTime;

    public String getTo()
    {
        return getJoinedString(toList);
    }

    public String getCc()
    {
        return getJoinedString(ccList);
    }

    public String getBcc()
    {
        return getJoinedString(bccList);
    }

    public String getTag()
    {
        return getJoinedString(tagList);
    }

    public MessageBuilder addTo(String... toValues)
    {
        addListValueInternal(toValues, toList);

        return this;
    }

    public MessageBuilder addCc(String... ccValues)
    {
        addListValueInternal(ccValues, ccList);

        return this;
    }

    public MessageBuilder addBcc(String... bccValues)
    {
        addListValueInternal(bccValues, bccList);

        return this;
    }

    public MessageBuilder addTag(String... tag) {
        addListValueInternal(tag, tagList);

        return this;
    }

    public MessageBuilder setFrom(String from)
    {
        this.from = from;

        return this;
    }

    public MessageBuilder setSubject(String subject)
    {
        this.subject = subject;

        return this;
    }

    public MessageBuilder setText(String text)
    {
        this.text = text;

        return this;
    }

    public MessageBuilder setHtml(String html)
    {
        this.html = html;

        return this;
    }

    public String getTestMode()
    {
        return testMode != null ? testMode.toString().toLowerCase() : null;
    }

    public String getDkim()
    {
        return dkim != null ? dkim.toString().toLowerCase() : null;
    }

    public String getTracking()
    {
        return tracking != null ? tracking.toString().toLowerCase() : null;
    }

    public String getTrackingClicks()
    {
        return trackingClicks != null ? trackingClicks.toString().toLowerCase() : null;
    }

    public String getTrackingOpens()
    {
        return trackingOpens != null ? trackingOpens.toString().toLowerCase() : null;
    }

    private void addListValueInternal(String[] provideValues, List<String> internalList)
    {
        if (provideValues != null)
        {
            for (String email : provideValues)
            {
                if (StringUtils.isNotBlank(email) && !internalList.contains(email))
                {
                    internalList.add(email);
                }
            }
        }
    }

    public String getJoinedString(List<String> list) {
        StringBuilder sb = new StringBuilder("");

        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i != list.size() - 1) sb.append(", ");
        }

        String result = null;
        String sbResult = sb.toString();

        if(!Strings.isNullOrEmpty(sbResult))
        {
            result = sbResult;
        }

        return result;
    }
}
