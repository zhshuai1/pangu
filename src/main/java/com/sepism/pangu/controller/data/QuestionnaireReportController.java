package com.sepism.pangu.controller.data;

import com.google.gson.Gson;
import com.sepism.pangu.model.answer.QuestionnaireReport;
import com.sepism.pangu.model.repository.QuestionnaireReportRepositoryRedis;
import com.sepism.pangu.model.repository.ReportHotRepositoryRedis;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

@Controller
@Log4j2
public class QuestionnaireReportController {
    private static final Gson GSON = new Gson();
    @Autowired
    private QuestionnaireReportRepositoryRedis questionnaireReportRepositoryRedis;

    @Autowired
    private ReportHotRepositoryRedis reportHotRepositoryRedis;

    @RequestMapping(path = "/data/reports/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getQuestionnaireReport(@PathVariable String id) {
        log.info("User is requesting to view report {}.", id);
        QuestionnaireReport questionnaireReport = null;
        try {
            long questionnaireId = Long.parseLong(id);
            questionnaireReport = questionnaireReportRepositoryRedis.findOne(questionnaireId);
            if (!CollectionUtils.isEmpty(questionnaireReport.getQuestionReports())) {
                reportHotRepositoryRedis.incrReadHotBy(questionnaireId, 1);
            }
        } catch (Exception e) {
            log.warn("Failed to retrieve and update hot for report {}", id, e);
        }
        return GSON.toJson(questionnaireReport);
    }

    @RequestMapping(path = "/data/reports", method = RequestMethod.GET)
    @ResponseBody
    public String getHotQuestionnaireReports(@RequestParam(required = false) Integer pageNumber,
                                             @RequestParam(required = false) Integer pageSize) {
        log.info("The user is requesting the report with page {} and size {}", pageNumber, pageSize);
        pageNumber = null == pageNumber ? 0 : pageNumber;
        pageSize = null == pageSize ? 0 : pageSize;
        pageSize = pageSize > 30 ? 30 : pageSize;
        long start = pageSize * pageNumber;
        String result = GSON.toJson(reportHotRepositoryRedis.findIdsByRankRange(start, start + pageSize - 1));

        return result;
    }
}
