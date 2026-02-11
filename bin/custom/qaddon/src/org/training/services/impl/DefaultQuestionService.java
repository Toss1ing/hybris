package org.training.services.impl;

import de.hybris.platform.core.model.product.ProductModel;
import org.training.daos.QuestionDao;
import org.training.model.QuestionModel;
import org.training.services.QuestionService;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DefaultQuestionService implements QuestionService {

    private final QuestionDao questionDao;

    public DefaultQuestionService(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public Map<ProductModel, List<QuestionModel>> getNewQuestionsGroupedByProduct(Date lastJobRunTime) {
        List<QuestionModel> newQuestions = questionDao.findQuestionsCreatedAfter(lastJobRunTime);

        return newQuestions.stream()
                .filter(q -> q.getProduct() != null)
                .collect(Collectors.groupingBy(QuestionModel::getProduct));
    }
}
