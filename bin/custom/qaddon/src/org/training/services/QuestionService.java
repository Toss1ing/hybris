package org.training.services;

import de.hybris.platform.core.model.product.ProductModel;
import org.training.model.QuestionModel;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface QuestionService {

    Map<ProductModel, List<QuestionModel>> getNewQuestionsGroupedByProduct(Date lastJobRunTime);

}
