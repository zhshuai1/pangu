package com.sepism.pangu.tool;

import com.sepism.pangu.model.answer.QuestionAnswer;
import com.sepism.pangu.model.answer.QuestionnaireAnswer;
import com.sepism.pangu.model.questionnaire.Questionnaire;
import com.sepism.pangu.model.repository.QuestionnaireAnswerRepository;
import com.sepism.pangu.model.repository.QuestionnaireHotRepositoryRedis;
import com.sepism.pangu.model.repository.QuestionnaireRepository;
import com.sepism.pangu.model.repository.ReportHotRepositoryRedis;
import com.sepism.pangu.processor.VoteUpdateProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public class QuestionnaireAnswerLoader {
    @Transactional
    public static void main(String[] args) {
        ApplicationContext context = new FileSystemXmlApplicationContext
                ("./src/main/webapp/WEB-INF/persistence-context.xml");
        QuestionnaireAnswerRepository questionnaireAnswerRepository =
                context.getBean("questionnaireAnswerRepository", QuestionnaireAnswerRepository.class);
        QuestionnaireRepository questionnaireRepository =
                context.getBean("questionnaireRepository", QuestionnaireRepository.class);

        long questionnaireId = 1001l;
        updateOneQuestionnaire(questionnaireAnswerRepository, questionnaireRepository, questionnaireId);
        updateHot(questionnaireId);
    }

    private static void updateOneQuestionnaire(QuestionnaireAnswerRepository questionnaireAnswerRepository,
                                               QuestionnaireRepository questionnaireRepository, long questionnaireId) {
        VoteUpdateProcessor voteUpdateProcessor = new VoteUpdateProcessor();
        List<QuestionnaireAnswer> questionnaireAnswers = questionnaireAnswerRepository
                .findByQuestionnaireIdAndCurrent(questionnaireId, true);

        Questionnaire questionnaire = questionnaireRepository.findOne(questionnaireId);
        List<QuestionAnswer> questionAnswers = new ArrayList<>();
        questionnaireAnswers.stream().forEach(qna -> questionAnswers.addAll(qna.getQuestionAnswers()));
        voteUpdateProcessor.updateVotesFullQuantity(questionAnswers, questionnaire.getQuestions(), questionnaireAnswers.size());
    }

    private static void updateHot(long questionnaireId) {
        new ReportHotRepositoryRedis().updateHot(questionnaireId);
        new QuestionnaireHotRepositoryRedis().updateHot(questionnaireId);
    }
}
