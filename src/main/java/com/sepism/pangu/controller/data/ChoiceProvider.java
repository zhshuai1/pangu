package com.sepism.pangu.controller.data;

import com.google.gson.Gson;
import com.sepism.pangu.model.repository.ChoiceRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@Log4j2
public class ChoiceProvider {
    public static final Gson GSON = new Gson();
    @Autowired
    private ChoiceRepository choiceRepository;

    @RequestMapping(path = "/data/choices/{parentId}", method = RequestMethod.GET, produces = MediaType
            .APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getChoices(@PathVariable long parentId) {
        return GSON.toJson(choiceRepository.findByParent(parentId));

    }
}
