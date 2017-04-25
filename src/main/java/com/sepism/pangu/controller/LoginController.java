package com.sepism.pangu.controller;

import com.sepism.pangu.constant.CookieName;
import com.sepism.pangu.model.authentication.Session;
import com.sepism.pangu.model.repository.SessionRepository;
import com.sepism.pangu.model.repository.UserRepository;
import com.sepism.pangu.model.user.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@Log4j2
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String userName, @RequestParam String password, HttpServletResponse response,
                        Model model) {
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
        response.addCookie(userCookie);
        response.addCookie(tokenCookie);
        return "test";

    }
}
