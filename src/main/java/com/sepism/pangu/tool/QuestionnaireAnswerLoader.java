package com.sepism.pangu.tool;

import com.sepism.pangu.model.answer.QuestionAnswer;
import com.sepism.pangu.model.answer.QuestionnaireAnswer;
import com.sepism.pangu.model.questionnaire.Questionnaire;
import com.sepism.pangu.model.repository.QuestionnaireAnswerRepository;
import com.sepism.pangu.model.repository.QuestionnaireRepository;
import com.sepism.pangu.processor.VoteUpdateProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public class QuestionnaireAnswerLoader {
    @Transactional
    public static void main(String[] args) {
        VoteUpdateProcessor voteUpdateProcessor = new VoteUpdateProcessor();
        ApplicationContext context = new FileSystemXmlApplicationContext
                ("./src/main/webapp/WEB-INF/persistence-context.xml");
        QuestionnaireAnswerRepository questionnaireAnswerRepository =
                context.getBean("questionnaireAnswerRepository", QuestionnaireAnswerRepository.class);
        QuestionnaireRepository questionnaireRepository =
                context.getBean("questionnaireRepository", QuestionnaireRepository.class);
        List<QuestionnaireAnswer> questionnaireAnswers = questionnaireAnswerRepository
                .findByQuestionnaireIdAndCurrent(1000l, true, new PageRequest(0, 1000)).getContent();
        Questionnaire questionnaire = questionnaireRepository.findOne(1000l);
        List<QuestionAnswer> questionAnswers = new ArrayList<>();
        questionnaireAnswers.stream().forEach(qna -> questionAnswers.addAll(qna.getQuestionAnswers()));
        voteUpdateProcessor.updateVotesFullQuantity(questionAnswers, questionnaire.getQuestions());

    }
}
