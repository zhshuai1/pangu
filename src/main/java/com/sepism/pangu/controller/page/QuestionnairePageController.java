package com.sepism.pangu.controller.page;


import com.google.gson.Gson;
import com.sepism.pangu.constant.CookieName;
import com.sepism.pangu.constant.ErrorCode;
import com.sepism.pangu.constant.ExtraDataName;
import com.sepism.pangu.constant.RequestAttribute;
import com.sepism.pangu.model.answer.QuestionAnswer;
import com.sepism.pangu.model.answer.QuestionnaireAnswer;
import com.sepism.pangu.model.handler.Response;
import com.sepism.pangu.model.questionnaire.Question;
import com.sepism.pangu.model.questionnaire.Questionnaire;
import com.sepism.pangu.model.repository.QuestionAnswerRepository;
import com.sepism.pangu.model.repository.QuestionRepository;
import com.sepism.pangu.model.repository.QuestionnaireAnswerRepository;
import com.sepism.pangu.model.repository.QuestionnaireRepository;
import com.sepism.pangu.validate.QuestionAnswerValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.net.URLEncoder;
import java.util.*;

@Controller
@Log4j2
public class QuestionnairePageController {
    private static final Gson GSON = new Gson();
    private static final String QUESTIONNAIRE_ID = "questionnaireId";
    @Autowired
    private QuestionnaireRepository questionnaireRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionAnswerValidator questionAnswerValidator;
    @Autowired
    private QuestionnaireAnswerRepository questionnaireAnswerRepository;
    @Autowired
    private QuestionAnswerRepository questionAnswerRepository;

    @RequestMapping(path = "/questionnairePage/{id}", method = RequestMethod.GET)
    public String getQuestionnairePage(@PathVariable String id, HttpServletRequest request, Model model) {
        log.info("user is accessing the questionnaire submission page.");
        if (!(boolean) request.getAttribute(RequestAttribute.LOGGED_IN)) {
            log.info("The user did not login yet, will redirect to login page");
            return "redirect:/login?redirectUrl=" + URLEncoder.encode("/questionnairePage/" + id);
        }
        model.addAttribute("id", id);
        return "questionnairePage";
    }

    @RequestMapping(path = "/submit-questionnaire",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @Transactional
    public String submitQuestionnaire(@RequestBody Map<String, String> answer, HttpServletRequest request) {
        log.info("User submit a questionnaire answer {}", answer);
        Map<String, String> extraData = new HashMap<>();

        if (!(boolean) request.getAttribute(RequestAttribute.LOGGED_IN)) {
            // For redirectUrl, the frontend will do th url encoding.
            extraData.put(ExtraDataName.REDIRECT_URL, "/questionnairePage/" + answer.get(QUESTIONNAIRE_ID));
            log.info("The user did not login yet, will redirect to login page");
            return Response.builder().errorCode(ErrorCode.NOT_LOGGIN).errorMessage("You did not login yet!")
                    .extraData(extraData).build().serialize();
        }

        try {
            long userId = Long.parseLong((String) request.getAttribute(CookieName.USER));
            long questionnaireId = Long.parseLong(answer.get(QUESTIONNAIRE_ID));
            answer.remove(QUESTIONNAIRE_ID);
            Questionnaire questionnaire = questionnaireRepository.findOne(questionnaireId);
            if (null == questionnaire) {
                String errorMessage = String.format("Could not find the questionnaire for Id: [%d]", questionnaireId);
                log.warn(errorMessage);
                return Response.builder().errorCode(ErrorCode.INVALID_VALUE).errorMessage(errorMessage).build().serialize();
            }
            List<Question> questions = questionnaire.getQuestions();
            Set<Long> questionIds = new HashSet<>();
            questions.stream().forEach(question -> questionIds.add(question.getId()));
            for (Map.Entry<String, String> entry : answer.entrySet()) {
                long key = Long.parseLong(entry.getKey());
                if (!questionIds.contains(key)) {
                    String errorMessage = String.format("The question [%d] does not belong to the questionnaire [%d]",
                            key, questionnaireId);
                    log.warn(errorMessage);
                    return Response.builder().errorCode(ErrorCode.INVALID_VALUE).errorMessage(errorMessage)
                            .build().serialize();
                }
            }

            QuestionnaireAnswer questionnaireAnswer = QuestionnaireAnswer.builder().questionnaireId(questionnaireId)
                    .creationDate(new Date()).userId(userId).build();
            questionnaireAnswerRepository.save(questionnaireAnswer);
            long questionnaireAnswerId = questionnaireAnswer.getId();
            log.info("Persist the questionnaire answer {} successfully.", questionnaireAnswer);

            // Here will use a second loop to check the validness and save the answer
            List<Long> failedQuestions = new ArrayList<>(answer.size());
            for (Map.Entry<String, String> entry : answer.entrySet()) {
                long key = Long.parseLong(entry.getKey());
                try {
                    questionAnswerValidator.validate(key, entry.getValue());
                    QuestionAnswer questionAnswer = QuestionAnswer.builder()
                            .answer(entry.getValue())
                            .questionId(key)
                            .questionnaireAnswerId(questionnaireAnswerId)
                            .creationDate(new Date())
                            .questionnaireId(questionnaireId)
                            .userId(userId).build();
                    questionAnswerRepository.save(questionAnswer);
                } catch (Exception e) {
                    log.info("Failed to validate and persist the question answer {}", entry, e);
                    failedQuestions.add(key);
                }
            }
        } catch (Exception e) {
            log.error("Exception encountered when submit the questionnaire answers.", e);
        }
        return Response.builder().errorCode(ErrorCode.SUCCESS).errorMessage("Success").build().serialize();
    }
}
