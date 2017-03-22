package com.michal.springboot.service;

import java.util.*;

import com.michal.springboot.domain.User;
import com.michal.springboot.exception.ConfirmationExpiredException;
import com.michal.springboot.exception.ConfirmationFailedException;
import com.michal.springboot.forms.UserStatus;
import com.michal.springboot.repository.RoleRepository;
import com.michal.springboot.repository.UserRepository;

import freemarker.template.Configuration;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;

@Service
public class UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private JavaMailSender javaMailSender;

    private BCryptPasswordEncoder bcryptEncoder;

    private Configuration fMConfiguration;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       JavaMailSender javaMailSender, BCryptPasswordEncoder bcryptEncoder,
                       Configuration fMConfiguration) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.javaMailSender = javaMailSender;
        this.bcryptEncoder = bcryptEncoder;
        this.fMConfiguration = fMConfiguration;
    }

    private static final long ONE_DAY_IN_MS = 24 * 60 * 60 * 1000;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Value("${mailingListServiceProps.confirmAccountUrl}")
    private String confirmAccountUrl;

    @Value("${mailingListServiceProps.confirmationAccountKey}")
    private String confirmationAccountKey;

    public void saveUser(User user) {

        String bCrypPassword = bcryptEncoder.encode(user.getPassword());

        user.setPassword(bCrypPassword);
        user.setPassword1(bCrypPassword);

        userRepository.save(user);

        sendConfirmation(user);
    }

    public User findByUserName(String userName) {

        return userRepository.findByUsername(userName);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public List<User> getUserList(int page) {
        return (List<User>) userRepository.findAll(new PageRequest(page, 10));
    }

    public void deleteUser(String name) {
        userRepository.delete(findByUserName(name));
    }

    public void changeUserRole(User user) {

        if (user.getRoles().stream().anyMatch(r -> r.getRoleName().equals("ADMIN_ROLE"))) {
            user.getRoles().clear();
            user.setRoles(Arrays.asList(roleRepository.findByRoleName("USER_ROLE")));
        } else {
            user.setRoles(Arrays.asList(roleRepository.findByRoleName("ADMIN_ROLE")));
        }
        userRepository.save(user);
    }

    public User getUser(long id) {
        return userRepository.findOne(id);
    }

    public void confirmUser(long userId, String digest) throws ConfirmationFailedException {

        User user = getUser(userId);

        checkTimestamp(user.getDateCreated().getTime());

        String expectedDigest = generateAccountDigest(user);
        if (!digest.equals(expectedDigest)) {
            throw new ConfirmationFailedException("Errore");
        }

        log.info("Activating account: " + user);

        user.setStatus(UserStatus.ACTIVE);

        user.setPassword1(user.getPassword());

        updateUser(user);
    }

    public void sendConfirmation(User user) {
        log.info("Sending account confirm to : " + user.getUsername());


        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        String digest = generateAccountDigest(user);
        String url = confirmAccountUrl + "?s=" + user.getId() + "&amp;d=" + digest;

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("user", user.getUsername());
        map.put("url", url);

        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setSubject("CarRental-Lublin Account Activation Email");
            mimeMessageHelper.setFrom("noreplay@gmail.com");
            mimeMessageHelper.setTo(user.getEmail());
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
                            getTemplate("email-template.txt"), map));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    private String generateAccountDigest(User user) {
        return DigestUtils.sha1Hex(user.getId() + ":" + confirmationAccountKey);
    }

    private static void checkTimestamp(long timestamp) throws ConfirmationExpiredException {

        long now = System.currentTimeMillis();
        if (now - timestamp > ONE_DAY_IN_MS) {
            throw new ConfirmationExpiredException();
        }
    }

    public void blockUser(User user) {

        if (user.getStatus() == UserStatus.ACTIVE) {
            user.setStatus(UserStatus.INACTIVE);
        } else {
            user.setStatus(UserStatus.ACTIVE);
        }

        updateUser(user);
    }

    boolean isUserEnabled(Long id) {

        if (userRepository.findOne(id).getStatus() == UserStatus.ACTIVE) {
            log.info("true");
            return true;
        } else {
            log.info("false");
            return false;
        }
    }
}
