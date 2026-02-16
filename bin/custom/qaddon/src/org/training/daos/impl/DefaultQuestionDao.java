package org.training.daos.impl;

import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.training.daos.QuestionDao;
import org.training.model.QuestionModel;

import java.util.Date;
import java.util.List;

public class DefaultQuestionDao implements QuestionDao {

    private final FlexibleSearchService flexibleSearchService;

    private static final String LAST_RUN_ATTRIBUTE = "lastRun";

    private static final String QUERY_FIND_NEW_QUESTIONS =
            "SELECT {q:" + QuestionModel.PK + "} " +
                    "FROM {" + QuestionModel._TYPECODE + " AS q} " +
                    "WHERE {q:" + QuestionModel.CREATIONTIME + "} > ?lastRun " +
                    "ORDER BY {q:" + QuestionModel.CREATIONTIME + "} ASC";

    public DefaultQuestionDao(FlexibleSearchService flexibleSearchService) {
        this.flexibleSearchService = flexibleSearchService;
    }

    @Override
    public List<QuestionModel> findQuestionsCreatedAfter(Date lastRunJobTime) {
        FlexibleSearchQuery query = new FlexibleSearchQuery(QUERY_FIND_NEW_QUESTIONS);

        query.addQueryParameter(
                LAST_RUN_ATTRIBUTE,
                lastRunJobTime == null ? new Date(0) : lastRunJobTime
        );

        return flexibleSearchService.<QuestionModel>search(query).getResult();
    }
}
