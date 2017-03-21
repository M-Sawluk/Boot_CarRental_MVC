package com.michal.springboot.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import com.michal.springboot.domain.Subscriber;
import com.michal.springboot.exception.ConfirmationExpiredException;
import com.michal.springboot.exception.ConfirmationFailedException;
import freemarker.template.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.apache.commons.codec.digest.DigestUtils;


@Service
public class MailingListService {

    private SubscriberService subscriberService;

    private JavaMailSender javaMailSender;

    private Configuration fMConfiguration;

    public MailingListService(SubscriberService subscriberService, JavaMailSender javaMailSender, Configuration fMConfiguration) {
        this.subscriberService = subscriberService;
        this.javaMailSender = javaMailSender;
        this.fMConfiguration = fMConfiguration;
    }

    private static final long ONE_DAY_IN_MS = 24 * 60 * 60 * 1000;

    private static final Logger log = LoggerFactory.getLogger(MailingListService.class);

    @Value("${mailingListServiceProps.confirmSubscriptionUrl}")
    private String confirmSubscriptionUrl;

    @Value("${mailingListServiceProps.confirmationKey}")
    private String confirmationKey;

    public Subscriber getSubscriber(long id) {

        return subscriberService.getSubscriber(id);
    }

    @Async
    public void addSubscriber(Subscriber subscriber) {

        log.info("Adding unconfirmed subscriber: " + subscriber);

        subscriberService.addSubscriber(subscriber);

        sendConfirmation(subscriber);

    }

    public void confirmSubscriber(long subscriberId, String digest) throws ConfirmationFailedException {

        Subscriber subscriber = getSubscriber(subscriberId);

        checkTimestamp(subscriber.getDateCreated().getTime());

        String expectedDigest = generateSubscriptionDigest(subscriber);

        if (!digest.equals(expectedDigest)) {
            throw new ConfirmationFailedException("Bad digest");
        }

        log.info("Confirming subscriber: " + subscriber);

        subscriber.setConfirmed(true);

        subscriberService.updateSubsriber(subscriber);

    }

    private void sendConfirmation(Subscriber subscriber) {

        log.info("Sending subscription confirm to : " + subscriber.getEmail());

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        String digest = generateSubscriptionDigest(subscriber);
        String url = confirmSubscriptionUrl + "?s=" + subscriber.getId() + "&amp;d=" + digest;

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("user", subscriber.getFirstName());
        map.put("url", url);

        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setSubject("CarRental-Lublin Subscription");
            mimeMessageHelper.setFrom("noreplay@gmail.com");
            mimeMessageHelper.setTo(subscriber.getEmail());
            mimeMessageHelper.setSentDate(new Date());
            mimeMessageHelper.setText(getEmailContent(map), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getEmailContent(Map<String, Object> map) {

        StringBuffer content = new StringBuffer();
        log.info(map.toString());

        try {
            content.append(FreeMarkerTemplateUtils
                    .processTemplateIntoString(fMConfiguration.
                            getTemplate("subscription-template.txt"), map));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    private String generateSubscriptionDigest(Subscriber subscriber) {
        return DigestUtils.sha1Hex(subscriber.getId() + ":" + confirmationKey);
    }

    private static void checkTimestamp(long timestamp) throws ConfirmationExpiredException {

        long now = System.currentTimeMillis();
        if (now - timestamp > ONE_DAY_IN_MS) {
            throw new ConfirmationExpiredException();
        }
    }

}
