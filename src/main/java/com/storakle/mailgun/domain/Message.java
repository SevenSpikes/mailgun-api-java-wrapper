package com.storakle.mailgun.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message
{
    private String attachment;

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

    private List<String> ccList = new ArrayList<>();

    private List<String> bccList = new ArrayList<>();

    private List<String> tagList = new ArrayList<>();

    @Getter
    @JsonProperty(value = JsonConstants.FROM)
    private String from;

    @Getter
    @JsonProperty(value = JsonConstants.REPLY_TO)
    private String replyTo;

    @JsonProperty(value = JsonConstants.CC)
    private String cc;

    @JsonProperty(value = JsonConstants.BCC)
    private String bcc;

    @Getter(AccessLevel.NONE)
    private String tag;

    @Getter
    @JsonProperty(value = JsonConstants.SUBJECT)
    private String subject;

    @Getter
    @JsonProperty(value = JsonConstants.TEXT)
    private String text;

    @Getter
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

    @Getter
    @JsonProperty(value = JsonConstants.DELIVERY_TIME)
    private ZonedDateTime deliveryTime;

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

    public Boolean hasAttachment()
    {
        if (!Strings.isNullOrEmpty(attachment))
        {
            File attachmentFile = new File(attachment);

            return attachmentFile.exists() && !attachmentFile.isDirectory();
        }
        return false;
    }

    public File getAttachment()
    {
        File attachmentFile = new File(attachment);

        if (attachmentFile.exists() && !attachmentFile.isDirectory())
        {
            return attachmentFile;
        }

        return null;
    }

    public Message addCc(String... ccValues)
    {
        addListValueInternal(ccValues, ccList);

        return this;
    }

    public Message addBcc(String... bccValues)
    {
        addListValueInternal(bccValues, bccList);

        return this;
    }

    public Message addTag(String... tag) {
        addListValueInternal(tag, tagList);

        return this;
    }

    public Message setAttachment(String attachment) {
        this.attachment = attachment;

        return this;
    }

    public Message setFrom(String from)
    {
        this.from = from;

        return this;
    }

    public Message setReplyTo(String replyTo)
    {
        this.replyTo = replyTo;

        return this;
    }

    public Message setSubject(String subject)
    {
        this.subject = subject;

        return this;
    }

    public Message setText(String text)
    {
        this.text = text;

        return this;
    }

    public Message setHtml(String html)
    {
        this.html = html;

        return this;
    }

    public Message setCampaign(String campaign)
    {
        this.campaign = campaign;

        return this;
    }

    public String getCampaign()
    {
        return this.campaign;
    }

    public Message setTracking(Tracking tracking)
    {
        this.tracking = tracking;

        return this;
    }

    public Message setTrackingClicks(TrackingClicks trackingClicks)
    {
        this.trackingClicks = trackingClicks;

        return this;
    }

    public Message setTrackingOpens(TrackingOpens trackingOpens)
    {
        this.trackingOpens = trackingOpens;

        return this;
    }

    public Message setDeliveryTime(ZonedDateTime deliveryTime)
    {
        this.deliveryTime = deliveryTime;

        return this;
    }

    public Message setTestMode(TestMode testMode)
    {
        this.testMode = testMode;

        return this;
    }

    public Message setDkim(Dkim dkim)
    {
        this.dkim = dkim;

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
            for (String value : provideValues)
            {
                if (StringUtils.isNotBlank(value) && !internalList.contains(value))
                {
                    internalList.add(value);
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
