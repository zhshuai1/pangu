package com.sepism.pangu.controller.page;


import com.sepism.pangu.constant.*;
import com.sepism.pangu.constant.RequestAttribute;
import com.sepism.pangu.model.answer.QuestionAnswer;
import com.sepism.pangu.model.answer.QuestionnaireAnswer;
import com.sepism.pangu.model.handler.Response;
import com.sepism.pangu.model.questionnaire.Question;
import com.sepism.pangu.model.questionnaire.Questionnaire;
import com.sepism.pangu.model.repository.QuestionnaireAnswerRepository;
import com.sepism.pangu.model.repository.QuestionnaireRepository;
import com.sepism.pangu.util.DateUtil;
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
import java.util.stream.Collectors;

@Controller
@Log4j2
public class QuestionnairePageController {
    private static final String QUESTIONNAIRE_ID = "questionnaireId";
    private static final long DAYS = GlobalConstant.DEFAULT_UPDATE_INTERVAL / 1000 / 3600 / 24;
    @Autowired
    private QuestionnaireRepository questionnaireRepository;
    @Autowired
    private QuestionAnswerValidator questionAnswerValidator;
    @Autowired
    private QuestionnaireAnswerRepository questionnaireAnswerRepository;

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
    public String submitQuestionnaire(@RequestBody Map<String, String> answers, HttpServletRequest request) {
        log.info("User submit a questionnaire answer {}", answers);
        Map<String, String> extraData = new HashMap<>();

        if (!(boolean) request.getAttribute(RequestAttribute.LOGGED_IN)) {
            // For redirectUrl, the frontend will do th url encoding.
            extraData.put(ExtraDataName.REDIRECT_URL, "/questionnairePage/" + answers.get(QUESTIONNAIRE_ID));
            log.info("The user did not login yet, will redirect to login page.");
            return Response.builder().errorCode(ErrorCode.NOT_LOGGIN).errorMessage("You did not login yet!")
                    .extraData(extraData).build().serialize();
        }

        // load the questions of the questionnaire from db
        try {
            long userId = Long.parseLong((String) request.getAttribute(CookieName.USER));
            long questionnaireId = Long.parseLong(answers.get(QUESTIONNAIRE_ID));
            Questionnaire questionnaire = questionnaireRepository.findOne(questionnaireId);
            if (null == questionnaire) {
                String errorMessage = String.format("Could not find the questionnaire for Id: [%d]", questionnaireId);
                log.warn(errorMessage);
                return Response.builder().errorCode(ErrorCode.INVALID_VALUE).errorMessage(errorMessage).build().serialize();
            }
            List<Question> questions = questionnaire.getQuestions();
            for (Question question : questions) {
                String answer = answers.get(String.valueOf(question.getId()));
                try {
                    questionAnswerValidator.validate(question, answer);
                } catch (Exception e) {
                    String errorMessage = String.format("Failed to validate the answer [{}] for question [{}]",
                            answer, question.getId());
                    log.warn(errorMessage, e);
                    return Response.builder().errorCode(ErrorCode.INVALID_VALUE).errorMessage(errorMessage)
                            .build().serialize();
                }
            }

            Set<Long> questionIds = questions.stream().map(q -> q.getId()).collect(Collectors.toSet());

            List<QuestionnaireAnswer> existedQuestionnaireAnswers = questionnaireAnswerRepository
                    .findByQuestionnaireIdAndUserIdAndCurrent(questionnaireId, userId, true);

            if (existedQuestionnaireAnswers.size() > 1) {
                log.warn("There should be only one answer is marked as current, but there are more than one: {}",
                        existedQuestionnaireAnswers);
            }
            // If the user did not answer the questionnaire before or answer the questionnaire
            // DEFAULT_UPDATE_INTERVAL ago, will create new records
            if ((existedQuestionnaireAnswers.size() == 0) || (DateUtil.diff(new Date(), existedQuestionnaireAnswers
                    .get(0).getCreationDate()) > GlobalConstant.DEFAULT_UPDATE_INTERVAL)) {
                if (existedQuestionnaireAnswers.size() > 0) {
                    existedQuestionnaireAnswers.stream().forEach(qna -> {
                        qna.setCurrent(false);
                        qna.setLastUpdateTime(new Date());
                        questionnaireAnswerRepository.save(qna);
                    });
                    log.info("The user answered the questionnaire {} days ago, will create a new answer.", DAYS);
                } else {
                    log.info("It's first time for user to answer this questionnaire, will create a new answer.");
                }

                QuestionnaireAnswer questionnaireAnswer = QuestionnaireAnswer.builder().questionnaireId(questionnaireId)
                        .creationDate(new Date()).lastUpdateTime(new Date()).userId(userId).current(true).build();

                List<QuestionAnswer> questionAnswers = new ArrayList<>(questions.size());
                for (Question question : questions) {
                    long questionId = question.getId();
                    String answer = answers.get(String.valueOf(questionId));
                    QuestionAnswer questionAnswer = QuestionAnswer.builder()
                            .type(question.getType())
                            .answer(answer)
                            .questionId(questionId)
                            .questionnaireId(questionnaireId)
                            .questionnaireAnswer(questionnaireAnswer)
                            .userId(userId)
                            .creationDate(new Date())
                            .lastUpdateTime(new Date()).build();
                    questionAnswers.add(questionAnswer);
                }
                log.info("The questionAnswers after parse is: {}", questionAnswers);
                questionnaireAnswer.setQuestionAnswers(questionAnswers);
                questionnaireAnswerRepository.save(questionnaireAnswer);
                log.info("The questionnaireAnswer has been created and saved successfully, the id is: {}",
                        questionnaireAnswer.getId());
            } else {
                log.info("The user answered the questionnaire in {} days, will only update the answer.", DAYS);

                QuestionnaireAnswer questionnaireAnswer = existedQuestionnaireAnswers.get(0);
                questionnaireAnswer.setLastUpdateTime(new Date());
                Set<Long> newQuestions = new HashSet<>(questionIds);

                List<QuestionAnswer> questionAnswers = questionnaireAnswer.getQuestionAnswers();
                for (QuestionAnswer questionAnswer : questionAnswers) {
                    long questionId = questionAnswer.getQuestionId();
                    String answer = answers.get(String.valueOf(questionId));
                    newQuestions.remove(questionId);
                    questionAnswer.setAnswer(answer);
                    questionAnswer.setLastUpdateTime(new Date());
                }
                log.info("The newly added questions are: {}", newQuestions);
                for (Question question : questions) {
                    if (!newQuestions.contains(question.getId())) {
                        continue;
                    }
                    long newQuestionId = question.getId();
                    String answer = answers.get(String.valueOf(newQuestionId));
                    QuestionAnswer questionAnswer = QuestionAnswer.builder()
                            .type(question.getType())
                            .answer(answer)
                            .questionId(newQuestionId)
                            .questionnaireId(questionnaireId)
                            .questionnaireAnswer(questionnaireAnswer)
                            .userId(userId)
                            .creationDate(new Date())
                            .lastUpdateTime(new Date()).build();
                    questionAnswers.add(questionAnswer);
                }
                questionnaireAnswerRepository.save(questionnaireAnswer);
                log.info("The questionnaireAnswer has been updated successfully, the id is: {}",
                        questionnaireAnswer.getId());
            }
            log.info("Successfully updated the questionnaireAnswer for questionnaire {}", questionnaireId);
        } catch (Exception e) {
            log.error("Exception encountered when submit the questionnaire answers.", e);
            return Response.builder().errorCode(ErrorCode.INTERNAL_ERROR).errorMessage("Unknown issues occurred.")
                    .build().serialize();
        }
        return Response.builder().errorCode(ErrorCode.SUCCESS).errorMessage("Success").build().serialize();
    }
}
