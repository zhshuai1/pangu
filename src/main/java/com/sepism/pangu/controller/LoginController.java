package com.sepism.pangu.controller;

import lombok.extern.log4j.Log4j2;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Log4j2
public class LoginController {

    @Autowired
    private SessionFactory sessionFactory;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @Transactional
    public String login(Model model) {
//        Query query = sessionFactory.getCurrentSession().createQuery("FROM User as user where user.nickName = ?");
//        //query.setParameter(0,)
//        List<User> users = query.list();
//        log.info(new Gson().toJson(users));
        return "test";
    }
}
