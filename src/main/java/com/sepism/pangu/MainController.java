package com.sepism.pangu;

import com.sepism.pangu.constant.RequestAttribute;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;


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


//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    @Transactional
//    public String login(Model model) {
//        Session session = sessionFactory.getCurrentSession();
//        List<User> users = session.createQuery("FROM User").list();
//        log.info(new Gson().toJson(users));
//        return "login";
//    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(HttpServletRequest request) {
        boolean loggedIn = (Boolean) request.getAttribute(RequestAttribute.LOGGED_IN);
        if (loggedIn) {
            return "test";
        } else {
            return "login";
        }

    }
}

