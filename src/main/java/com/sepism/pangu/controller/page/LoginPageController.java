package com.sepism.pangu.controller.page;

import com.sepism.pangu.constant.CookieName;
import com.sepism.pangu.constant.GlobalConstant;
import com.sepism.pangu.model.authentication.Session;
import com.sepism.pangu.model.repository.SessionRepositoryRedis;
import com.sepism.pangu.model.repository.UserRepository;
import com.sepism.pangu.model.user.User;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@Log4j2
public class LoginPageController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepositoryRedis sessionRepository;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String userName, @RequestParam String password, @RequestParam
            String redirectUrl, HttpServletResponse response) {
        log.info("User {} is accessing the login page.", userName);

        List<User> users = userRepository.findByNickNameOrEmailOrPhoneNumber(userName, userName, userName);
        if (CollectionUtils.isEmpty(users) || !users.get(0).getPassword().equals(password)) {
            return "login";
        }
        User user = users.get(0);
        String token = UUID.randomUUID().toString();
        Session session = Session.builder().id(user.getId()).token(token).lastAccessTime(new Date()).build();
        sessionRepository.save(session);
        Cookie userCookie = new Cookie(CookieName.USER, String.valueOf(user.getId()));
        Cookie tokenCookie = new Cookie(CookieName.TOKEN, token);
        tokenCookie.setMaxAge(GlobalConstant.COOKIE_EXPIRED_TIME);
        response.addCookie(userCookie);
        response.addCookie(tokenCookie);
        if (StringUtils.isEmpty(redirectUrl)) {
            log.info("No redirectUrl in the request, will redirect to index page.");
            return "redirect:/";
        }
        String url = URLDecoder.decode(redirectUrl);
        log.info("Direct the user to the url [{}]", url);
        return "redirect:" + url;

    }
}
