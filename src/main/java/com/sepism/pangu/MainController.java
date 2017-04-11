package com.sepism.pangu;

import com.google.gson.Gson;
import com.sepism.pangu.model.user.User;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.EventLogger;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.message.StructuredDataMessage;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;
import java.util.UUID;


@Setter
@Controller
@SessionAttributes("merchantId")
@Log4j2
public class MainController {
    @Autowired
    private SessionFactory sessionFactory;

    @RequestMapping(value = "/say", method = RequestMethod.GET)
    public String sayHello2(@RequestParam("name") String id, Model model) {
        log.error("The value of name is {}", id);
        model.addAttribute("url", "world");
        return "test";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @Transactional
    public String login(Model model) {
        Session session = sessionFactory.getCurrentSession();
        List<User> users = session.createQuery("FROM com.sepism.pangu.model.user.User").list();
        log.info(new Gson().toJson(users));
        return "login";
    }
}

