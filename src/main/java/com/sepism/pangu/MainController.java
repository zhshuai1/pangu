package com.sepism.pangu;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@SessionAttributes("merchantId")
public class MainController {

    @RequestMapping(value = "/say", method = RequestMethod.GET)
    public String sayHello2(@RequestParam("name") String id, Model model) {
        System.out.println(id + "2");
        model.addAttribute("url", "world");
        return "test";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Map<String, String> model) {
        System.out.println("hello");
        model.put("url", "456");
        return "index";
    }

    @RequestMapping(value = "/sessionTest", method = RequestMethod.GET)
    public String sessionTest(@ModelAttribute("merchantId") String merchantId) {
        System.out.println(merchantId);
        return "sessionTest";
    }
}