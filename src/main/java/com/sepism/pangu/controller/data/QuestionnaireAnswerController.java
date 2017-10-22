package com.sepism.pangu.controller.data;

import com.sepism.pangu.constant.ErrorCode;
import com.sepism.pangu.model.handler.Response;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Log4j2
public class QuestionnaireAnswerController {

    @RequestMapping(path = "/data/questionnaires", method = RequestMethod.POST, consumes = MediaType
            .APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String submitQuestionnaire(@RequestBody String questionnaireAnswer) {

        return new Response(ErrorCode.SUCCESS).serialize();
    }
}
